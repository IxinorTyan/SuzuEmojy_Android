package com.suzu.test.resource.exportpkg

import android.content.Context
import android.net.Uri
import com.suzu.test.BuildConfig
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest
import java.util.UUID
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

data class PackageExportSummary(
    val manifest: JSONObject,
    val exportedCount: Int,
    val skippedCount: Int,
    val packageName: String
)

class ResourcePackageExportService(
    private val context: Context,
    private val database: SuzuDatabase
) {

    companion object {
        private const val MODULE = "ResourcePackageExport"
    }

    suspend fun exportToZip(
        targetUri: Uri,
        packageName: String,
        selectedCategoryIds: List<Long> = emptyList()
    ): PackageExportSummary {
        val packageBaseName = normalizePackageName(packageName)
        val resourcesDir = File(context.filesDir, "resources")
        val resourceDao = database.resourceDao()
        val categoryDao = database.categoryDao()
        val relationDao = database.resourceCategoryDao()

        val allCategories = categoryDao.getAllCategories()
        val exportCategories = if (selectedCategoryIds.isEmpty()) {
            allCategories
        } else {
            val selected = selectedCategoryIds.toSet()
            allCategories.filter { it.id in selected }
        }
        val categoryIdToRef = exportCategories.mapIndexed { index, category ->
            category.id to (index + 1)
        }.toMap()

        val exportedResources = mutableListOf<ExportedResource>()
        val skipped = mutableListOf<JSONObject>()
        var totalAssetBytes = 0L
        var relationsCount = 0

        val resources = if (selectedCategoryIds.isEmpty()) {
            resourceDao.getAllResourcesOrderedList()
        } else {
            val dedup = linkedMapOf<Long, ResourceEntity>()
            selectedCategoryIds.forEach { categoryId ->
                relationDao.getResourcesForCategoryList(categoryId).forEach { resource ->
                    dedup.putIfAbsent(resource.id, resource)
                }
            }
            dedup.values.sortedWith(compareBy<ResourceEntity> { it.sortOrder }.thenBy { it.id })
        }

        for (resource in resources) {
            val packed = packResource(
                resource = resource,
                resourcesDir = resourcesDir,
                categoryIdToRef = categoryIdToRef,
                relationDao = relationDao
            )
            if (packed == null) {
                skipped.add(
                    JSONObject()
                        .put("name", resource.filename)
                        .put("reason", "source file missing or unreadable")
                )
                continue
            }

            exportedResources.add(packed)
            totalAssetBytes += packed.assetSize
            relationsCount += packed.categoryRefs.size
        }

        val categoriesJson = JSONArray().apply {
            exportCategories.forEachIndexed { index, category ->
                put(
                    JSONObject()
                        .put("ref", index + 1)
                        .put("name", category.name)
                )
            }
        }

        val resourcesJson = JSONArray().apply {
            exportedResources
                .sortedWith(compareBy<ExportedResource> { it.createdAt }.thenBy { it.displayName }.thenBy { it.syncKey })
                .forEach { item ->
                    put(
                        JSONObject()
                            .put("sync_key", item.syncKey)
                            .put("asset_path", item.assetPath)
                            .put("asset_size", item.assetSize)
                            .put("asset_sha256", item.assetSha256)
                            .put("pixel_md5", item.pixelMd5)
                            .put("file_md5", item.fileMd5)
                            .put("format", item.format)
                            .put("is_animated", item.isAnimated)
                            .put("width", item.width)
                            .put("height", item.height)
                            .put("quality_score", item.qualityScore)
                            .put("keywords", JSONArray(item.keywords))
                            .put("created_at", item.createdAt)
                            .put("display_name", item.displayName)
                            .put("category_refs", JSONArray(item.categoryRefs))
                    )
                }
        }

        val manifest = JSONObject()
            .put("format_version", 1)
            .put("package_id", UUID.randomUUID().toString())
            .put("exporter", "pe")
            .put("app_version", getAppVersion())
            .put("exported_at", System.currentTimeMillis())
            .put(
                "hash_rules",
                JSONObject()
                    .put("static", "p:md5(RGBA bytes)")
                    .put("animated", "f:md5(file bytes)")
            )
            .put(
                "counts",
                JSONObject()
                    .put("resources", exportedResources.size)
                    .put("categories", exportCategories.size)
                    .put("relations", relationsCount)
                    .put("assets", exportedResources.size)
            )
            .put("total_asset_bytes", totalAssetBytes)
            .put("skipped", JSONArray(skipped))

        val catalog = JSONObject()
            .put("categories", categoriesJson)
            .put("resources", resourcesJson)

        writeZip(targetUri, manifest, catalog, exportedResources)

        TestLog.i(
            MODULE,
            "导出完成: package=$packageBaseName, resources=${exportedResources.size}, skipped=${skipped.size}"
        )

        return PackageExportSummary(
            manifest = manifest,
            exportedCount = exportedResources.size,
            skippedCount = skipped.size,
            packageName = packageBaseName
        )
    }

    private suspend fun packResource(
        resource: ResourceEntity,
        resourcesDir: File,
        categoryIdToRef: Map<Long, Int>,
        relationDao: com.suzu.test.db.dao.ResourceCategoryDao
    ): ExportedResource? {
        val sourceFile = File(resourcesDir, resource.filename)
        if (!sourceFile.exists() || !sourceFile.isFile) return null

        val bytes = runCatching { sourceFile.readBytes() }.getOrNull() ?: return null
        val syncKey = resource.syncKey.ifBlank {
            if (resource.isAnimated) {
                "f:${md5Hex(bytes)}"
            } else {
                "p:${resource.pixelMd5.orEmpty()}"
            }
        }

        val categoryRefs = relationDao.getCategoryIdsByResourceId(resource.id)
            .mapNotNull { categoryId -> categoryIdToRef[categoryId] }
            .distinct()

        return ExportedResource(
            syncKey = syncKey,
            assetPath = assetPathFor(resource),
            assetSize = bytes.size.toLong(),
            assetSha256 = sha256Hex(bytes),
            pixelMd5 = resource.pixelMd5,
            fileMd5 = resource.fileMd5,
            format = resource.format,
            isAnimated = resource.isAnimated,
            width = resource.width,
            height = resource.height,
            qualityScore = resource.qualityScore,
            keywords = parseKeywords(resource.keywords),
            createdAt = resource.createdAt,
            displayName = resource.filename,
            categoryRefs = categoryRefs,
            payload = bytes
        )
    }

    private fun writeZip(
        targetUri: Uri,
        manifest: JSONObject,
        catalog: JSONObject,
        resources: List<ExportedResource>
    ) {
        val resolver = context.contentResolver
        resolver.openOutputStream(targetUri, "w")?.use { output ->
            ZipOutputStream(output).use { zip ->
                putEntry(zip, "manifest.json", manifest.toString(2).toByteArray(Charsets.UTF_8))
                putEntry(zip, "catalog.json", catalog.toString(2).toByteArray(Charsets.UTF_8))
                resources.forEach { item ->
                    putEntry(zip, item.assetPath, item.payload)
                }
            }
        } ?: throw IllegalStateException("无法打开导出目标文件")
    }

    private fun putEntry(zip: ZipOutputStream, name: String, bytes: ByteArray) {
        val entry = ZipEntry(name)
        zip.putNextEntry(entry)
        zip.write(bytes)
        zip.closeEntry()
    }

    private fun assetPathFor(resource: ResourceEntity): String {
        return if (resource.isAnimated) {
            "assets/f-${resource.fileMd5}.gif"
        } else {
            "assets/p-${resource.pixelMd5.orEmpty()}.png"
        }
    }

    private fun getAppVersion(): String {
        return runCatching {
            if (BuildConfig.DEBUG) {
                "${BuildConfig.VERSION_NAME}-debug"
            } else {
                BuildConfig.VERSION_NAME
            }
        }.getOrDefault("unknown")
    }

    private fun normalizeName(value: String): String {
        return java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFC).trim()
    }

    private fun normalizePackageName(value: String): String {
        val raw = normalizeName(value)
        val safe = raw.replace(Regex("""[\\/:*?"<>|]"""), "_")
            .replace(Regex("""\s+"""), "_")
            .trim('_', '.', ' ')
        return if (safe.isBlank()) {
            "suzuemojy_${System.currentTimeMillis()}"
        } else {
            safe
        }
    }

    private fun parseKeywords(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split(Regex("""\s+"""))
            .map { normalizeName(it) }
            .filter { it.isNotBlank() }
            .distinct()
    }

    private fun md5Hex(bytes: ByteArray): String = digestHex("MD5", bytes)

    private fun sha256Hex(bytes: ByteArray): String = digestHex("SHA-256", bytes)

    private fun digestHex(algorithm: String, bytes: ByteArray): String {
        val digest = MessageDigest.getInstance(algorithm).digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    private data class ExportedResource(
        val syncKey: String,
        val assetPath: String,
        val assetSize: Long,
        val assetSha256: String,
        val pixelMd5: String?,
        val fileMd5: String,
        val format: String,
        val isAnimated: Boolean,
        val width: Int,
        val height: Int,
        val qualityScore: Double,
        val keywords: List<String>,
        val createdAt: Long,
        val displayName: String,
        val categoryRefs: List<Int>,
        val payload: ByteArray
    )
}
