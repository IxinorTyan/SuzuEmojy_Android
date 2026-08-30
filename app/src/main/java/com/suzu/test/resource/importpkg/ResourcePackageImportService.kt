package com.suzu.test.resource.importpkg

import android.content.Context
import android.net.Uri
import androidx.room.withTransaction
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.ui.import.ImportFailReason
import com.suzu.test.ui.import.ImportItemRecord
import com.suzu.test.ui.import.ImportItemType
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.LinkedHashSet
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

enum class PackageImportStage {
    COPYING,
    PARSING,
    IMPORTING
}

data class PackageImportSummary(
    val successCount: Int,
    val duplicateCount: Int,
    val failCount: Int,
    val attachedCategoryNames: List<String>
)

data class PackageImportExecutionResult(
    val records: List<ImportItemRecord>,
    val summary: PackageImportSummary
)

class ResourcePackageImportService(
    private val context: Context,
    private val database: SuzuDatabase,
    private val resourceImportService: ResourceImportService
) {

    companion object {
        private const val MODULE = "ResourcePackageImport"
    }

    suspend fun importFromZip(
        packageUri: Uri,
        onProgress: (stage: PackageImportStage, progress: Long, total: Long) -> Unit
    ): PackageImportExecutionResult = withContext(Dispatchers.IO) {
        val cacheDir = context.cacheDir
        
        // 每次导入开始前，清理上一次残留的临时文件
        val oldTempFiles = cacheDir.listFiles { _, name -> name.startsWith("import_tmp_") }
        oldTempFiles?.forEach {
            try {
                it.delete()
            } catch (e: Exception) {
                TestLog.w(MODULE, "清理残留临时文件失败: ${it.name}, ${e.message}")
            }
        }

        // 检查包大小与可用空间
        var packageSize: Long = -1L
        context.contentResolver.query(packageUri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    packageSize = cursor.getLong(sizeIndex)
                }
            }
        }

        if (packageSize > 0) {
            val usableSpace = cacheDir.usableSpace
            if (usableSpace < packageSize) {
                throw IllegalStateException("存储空间不足，无法导入该资源包")
            }
        }

        val tempZipFile = File(cacheDir, "import_tmp_${System.currentTimeMillis()}.zip")
        try {
            // 阶段一：拷贝临时文件
            context.contentResolver.openInputStream(packageUri)?.use { input ->
                tempZipFile.outputStream().use { output ->
                    val buffer = ByteArray(8192)
                    var bytesCopied = 0L
                    var bytes = input.read(buffer)
                    while (bytes >= 0) {
                        output.write(buffer, 0, bytes)
                        bytesCopied += bytes
                        onProgress(PackageImportStage.COPYING, bytesCopied, packageSize)
                        bytes = input.read(buffer)
                    }
                }
            } ?: throw IllegalArgumentException("无法读取 zip 文件")

            // 阶段二：解析 manifest 与 catalog
            onProgress(PackageImportStage.PARSING, 0L, 0L)

            ZipFile(tempZipFile).use { zipFile ->
                val manifestEntry = zipFile.getEntry("manifest.json")
                    ?: throw IllegalArgumentException("资源包缺少 manifest.json")
                val catalogEntry = zipFile.getEntry("catalog.json")
                    ?: throw IllegalArgumentException("资源包缺少 catalog.json")

                val manifestJson = zipFile.getInputStream(manifestEntry).use { it.readBytes() }
                val catalogJson = zipFile.getInputStream(catalogEntry).use { it.readBytes() }

                val manifest = JSONObject(manifestJson.decodeToString())
                val formatVersion = manifest.optInt("format_version", -1)
                if (formatVersion != 1) {
                    throw IllegalArgumentException("不支持的资源包版本: $formatVersion")
                }

                val catalog = JSONObject(catalogJson.decodeToString())
                val categories = parseCategories(catalog.optJSONArray("categories"))
                val categoryRefToName = categories.associate { it.ref to it.name }
                val resources = parseResources(catalog.optJSONArray("resources"))

                if (resources.isEmpty()) {
                    throw IllegalArgumentException("资源包中没有可导入的资源")
                }

                val records = mutableListOf<ImportItemRecord>()
                val categoryNameToResourceIds = linkedMapOf<String, MutableSet<Long>>()
                val touchedCategoryNames = LinkedHashSet<String>()

                var successCount = 0
                var duplicateCount = 0
                var failCount = 0

                val totalCount = resources.size.toLong()

                for ((index, resource) in resources.withIndex()) {
                    // 阶段三：逐项导入资源
                    onProgress(PackageImportStage.IMPORTING, index.toLong(), totalCount)

                    val assetEntry = zipFile.getEntry(resource.assetPath)
                    if (assetEntry == null) {
                        failCount++
                        records.add(
                            ImportItemRecord(
                                sourceUri = packageUri,
                                type = ImportItemType.FAILED,
                                syncKey = resource.syncKey,
                                resourceId = null,
                                filename = resource.displayName,
                                existingResourceId = null,
                                existingFilename = null,
                                failReason = ImportFailReason.READ_FAILED,
                                previewFilePath = null
                            )
                        )
                        continue
                    }

                    val assetBytes = zipFile.getInputStream(assetEntry).use { it.readBytes() }
                    if (assetBytes.isEmpty()) {
                        failCount++
                        records.add(
                            ImportItemRecord(
                                sourceUri = packageUri,
                                type = ImportItemType.FAILED,
                                syncKey = resource.syncKey,
                                resourceId = null,
                                filename = resource.displayName,
                                existingResourceId = null,
                                existingFilename = null,
                                failReason = ImportFailReason.READ_FAILED,
                                previewFilePath = null
                            )
                        )
                        continue
                    }

                    val previewFilePath = createPreviewTempFile(resource, assetBytes)

                    try {
                        val result = resourceImportService.import(assetBytes)
                        if (result.isDuplicate) {
                            duplicateCount++
                            records.add(
                                ImportItemRecord(
                                    sourceUri = packageUri,
                                    type = ImportItemType.DUPLICATE,
                                    syncKey = result.syncKey,
                                    resourceId = result.resourceId,
                                    filename = result.filename,
                                    existingResourceId = result.existingResourceId,
                                    existingFilename = result.existingFilename,
                                    failReason = null,
                                    previewFilePath = previewFilePath
                                )
                            )
                        } else {
                            successCount++
                            records.add(
                                ImportItemRecord(
                                    sourceUri = packageUri,
                                    type = ImportItemType.NEW_ADDED,
                                    syncKey = result.syncKey,
                                    resourceId = result.resourceId,
                                    filename = result.filename,
                                    existingResourceId = null,
                                    existingFilename = null,
                                    failReason = null,
                                    previewFilePath = previewFilePath
                                )
                            )
                        }

                        val resourceId = result.resourceId
                        val categoryNames = resource.categoryRefs
                            .mapNotNull { categoryRefToName[it] }
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }

                        for (categoryName in categoryNames) {
                            touchedCategoryNames.add(categoryName)
                            categoryNameToResourceIds
                                .getOrPut(categoryName) { linkedSetOf() }
                                .add(resourceId)
                        }
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "导入资源失败: asset=${resource.assetPath}, error=${e.message}", e)
                        failCount++
                        val reason = when {
                            e.message?.contains("Unsupported format", ignoreCase = true) == true -> ImportFailReason.UNSUPPORTED_OTHER
                            e.message?.contains("decode", ignoreCase = true) == true -> ImportFailReason.DECODE_FAILED
                            else -> ImportFailReason.UNKNOWN
                        }
                        records.add(
                            ImportItemRecord(
                                sourceUri = packageUri,
                                type = ImportItemType.FAILED,
                                syncKey = resource.syncKey,
                                resourceId = null,
                                filename = resource.displayName,
                                existingResourceId = null,
                                existingFilename = null,
                                failReason = reason,
                                previewFilePath = previewFilePath
                            )
                        )
                    }
                }

                // 导入彻底完成时，报告最后的进度 100%
                onProgress(PackageImportStage.IMPORTING, totalCount, totalCount)

                attachCategories(categoryNameToResourceIds)

                PackageImportExecutionResult(
                    records = records,
                    summary = PackageImportSummary(
                        successCount = successCount,
                        duplicateCount = duplicateCount,
                        failCount = failCount,
                        attachedCategoryNames = touchedCategoryNames.toList()
                    )
                )
            }
        } finally {
            try {
                if (tempZipFile.exists()) {
                    tempZipFile.delete()
                }
            } catch (e: Exception) {
                TestLog.w(MODULE, "删除临时 zip 文件失败: ${e.message}")
            }
        }
    }

    private fun createPreviewTempFile(resource: CatalogResource, assetBytes: ByteArray): String? {
        return try {
            val previewDir = File(context.cacheDir, "import_previews").apply { mkdirs() }
            val suffix = resource.assetPath.substringAfterLast('.', "").takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""
            val safeBase = (resource.syncKey.ifBlank { resource.displayName.ifBlank { resource.assetPath.substringAfterLast('/') } })
                .replace(Regex("""[^\w\-.]+"""), "_")
                .ifBlank { "preview" }
            val previewFile = File(previewDir, "${safeBase}_${System.currentTimeMillis()}$suffix")
            previewFile.writeBytes(assetBytes)
            previewFile.absolutePath
        } catch (e: Exception) {
            TestLog.w(MODULE, "创建预览临时文件失败: ${resource.assetPath}, ${e.message}")
            null
        }
    }

    private suspend fun attachCategories(categoryNameToResourceIds: Map<String, Set<Long>>) {
        if (categoryNameToResourceIds.isEmpty()) return

        database.withTransaction {
            val categoryDao = database.categoryDao()
            val resourceCategoryDao = database.resourceCategoryDao()

            for ((categoryName, resourceIds) in categoryNameToResourceIds) {
                if (resourceIds.isEmpty()) continue
                val existing = categoryDao.getCategoryByName(categoryName)
                val categoryId = if (existing != null) {
                    existing.id
                } else {
                    val maxSort = categoryDao.getMaxSortOrder() ?: 0
                    categoryDao.insertCategory(
                        CategoryEntity(
                            name = categoryName,
                            sortOrder = maxSort + 1
                        )
                    )
                }

                val now = System.currentTimeMillis()
                var minSort = resourceCategoryDao.getMinSortOrderForCategory(categoryId) ?: 0
                for (resourceId in resourceIds.distinct()) {
                    minSort--
                    resourceCategoryDao.addResourceToCategory(
                        ResourceCategoryEntity(
                            resourceId = resourceId,
                            categoryId = categoryId,
                            sortOrder = minSort,
                            addedAt = now
                        )
                    )
                }
            }
        }
    }

    private fun readZipEntries(zipBytes: ByteArray): Map<String, ByteArray> {
        val entries = linkedMapOf<String, ByteArray>()
        ZipInputStream(zipBytes.inputStream()).use { zis ->
            var entry: ZipEntry? = zis.nextEntry
            while (entry != null) {
                if (!entry.isDirectory) {
                    entries[entry.name] = zis.readAllBytesCompat()
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
        return entries
    }

    private fun InputStream.readAllBytesCompat(): ByteArray {
        val output = ByteArrayOutputStream()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) {
            val read = read(buffer)
            if (read <= 0) break
            output.write(buffer, 0, read)
        }
        return output.toByteArray()
    }

    private fun parseCategories(array: JSONArray?): List<CatalogCategory> {
        if (array == null) return emptyList()
        val result = mutableListOf<CatalogCategory>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            val ref = obj.optInt("ref", -1)
            val name = obj.optString("name").trim()
            if (ref > 0 && name.isNotEmpty()) {
                result.add(CatalogCategory(ref = ref, name = name))
            }
        }
        return result
    }

    private fun parseResources(array: JSONArray?): List<CatalogResource> {
        if (array == null) return emptyList()
        val result = mutableListOf<CatalogResource>()
        for (i in 0 until array.length()) {
            val obj = array.optJSONObject(i) ?: continue
            val syncKey = obj.optString("sync_key").trim()
            val assetPath = obj.optString("asset_path").trim()
            val displayName = obj.optString("display_name").trim().ifEmpty { assetPath.substringAfterLast('/') }
            val categoryRefsArray = obj.optJSONArray("category_refs")
            val refs = mutableListOf<Int>()
            if (categoryRefsArray != null) {
                for (j in 0 until categoryRefsArray.length()) {
                    val ref = categoryRefsArray.optInt(j, -1)
                    if (ref > 0) refs.add(ref)
                }
            }
            if (assetPath.isNotEmpty()) {
                result.add(
                    CatalogResource(
                        syncKey = syncKey,
                        assetPath = assetPath,
                        displayName = displayName,
                        categoryRefs = refs
                    )
                )
            }
        }
        return result
    }

    private data class CatalogCategory(
        val ref: Int,
        val name: String
    )

    private data class CatalogResource(
        val syncKey: String,
        val assetPath: String,
        val displayName: String,
        val categoryRefs: List<Int>
    )
}
