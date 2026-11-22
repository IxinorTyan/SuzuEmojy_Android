package com.suzu.test.resource.lansync

import android.content.Context
import android.net.Uri
import androidx.room.withTransaction
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.LanSyncStateEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.resource.PackageIcons
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import com.suzu.test.resource.hash.ResourceHasher
import com.suzu.test.resource.importpkg.ReceivedPackageValidation
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.UUID
import java.util.zip.ZipFile

/** The wire coordinator only sees portable metadata. Room IDs and filenames stay here. */
internal class LanSyncAndroidLibrary(private val context: Context, private val db: SuzuDatabase) {
    private val state = db.lanSyncStateDao()
    private val resources = db.resourceDao()
    private val categories = db.categoryDao()
    private val relations = db.resourceCategoryDao()
    private val resourceDir = File(context.filesDir, "resources")
    private val whitespace = Regex("[\\u0009-\\u000D\\u0085\\p{Z}]+")

    internal data class Capture(
        val revision: String?, val library: SyncLibrary, val categories: Map<String, CategoryEntity>,
        val resources: Map<String, ResourceEntity>, val relations: List<ResourceCategoryEntity>, val icons: Map<String, File>
    )

    suspend fun device(): String = db.withTransaction {
        state.read("device") ?: newId().also { put("device", it) }
    }

    suspend fun capture(device: String, peer: String): Capture = db.withTransaction { read(device, peer) }

    private suspend fun read(device: String, peer: String): Capture {
        val revision = state.read("revision:$peer")
        val bindings = state.read("bindings:$peer")?.let(::JSONObject)
        val categoryRows = linkedMapOf<String, CategoryEntity>()
        val iconFiles = mutableMapOf<String, File>()
        val portableCategories = categories.getAllCategories().map { category ->
            val id = bindings?.optString(category.id.toString())?.takeIf(String::isNotBlank)
                ?: digest("$device:${category.id}".toByteArray()).take(32)
            categoryRows[id] = category
            val icon = when (val parsed = CategoryIconResolver.resolve(category.iconPath)) {
                CategoryIconResult.Default -> null
                is CategoryIconResult.Text -> SyncIcon("text", parsed.content)
                else -> {
                    val source = PackageIcons.sourceFile(context, db, category.iconPath)
                        ?: throw IOException("收藏夹图标无法读取：${category.name}")
                    val path = "icons/${iconDigest(source)}.${source.extension.lowercase()}"
                    iconFiles[path] = source
                    SyncIcon("image", path)
                }
            }
            SyncCategory(id, category.name, icon)
        }
        val ids = categoryRows.entries.associate { it.value.id to it.key }
        val links = relations.getAllRelationsForSync()
        val byResource = links.groupBy { it.resourceId }
        val resourceRows = resources.getAllResourcesOrderedList().associateBy { it.syncKey }
        val portableResources = resourceRows.values.map { resource ->
            currentCoroutineContext().ensureActive()
            val file = resourceFile(resource.filename)
            if (!file.isFile || file.length() !in 1..(48L shl 20) || file.length() != resource.byteSize ||
                file.inputStream().use { ResourceHasher.fileMd5(it) } != resource.fileMd5)
                throw IOException("表情文件缺失、损坏或超出大小限制：${resource.filename}")
            val tags = whitespace.split(resource.keywords).filter(String::isNotEmpty).distinct().sorted()
            SyncResource(resource.syncKey, tags, byResource[resource.id].orEmpty().map { ids.getValue(it.categoryId) }.sorted())
        }
        return Capture(revision, SyncLibrary(portableCategories, portableResources).validate(), categoryRows, resourceRows, links, iconFiles)
    }

    suspend fun export(file: File, send: SyncAssets) {
        ResourcePackageExportService(context, db).exportForSync(Uri.fromFile(file),
            ResourcePackageExportService.SyncAssets(send.keys, send.icons))
        ZipFile(file).use { ReceivedPackageValidation.checkEntries(it) }
    }

    /** Prepare and validate all files before either device mutates its library. */
    suspend fun <T> withPrepared(
        before: Capture, target: SyncLibrary, incoming: File?, block: suspend (Prepared) -> T
    ): T {
        val assets = SyncAssets.needed(before.library, target)
        val additions = linkedMapOf<String, ResourceEntity>()
        val iconPaths = mutableMapOf<String, String>()
        val created = mutableListOf<File>()
        val prepared = Prepared(additions, iconPaths)
        try {
            val staged = mutableMapOf<String, JSONObject>()
            if (incoming != null) ZipFile(incoming).use { zip ->
                ReceivedPackageValidation.checkEntries(zip)
                val manifest = JSONObject(metadata(zip, "manifest.json"))
                require(manifest.getInt("format_version") == 1) { "收到不支持的资源包。" }
                val catalog = JSONObject(metadata(zip, "catalog.json"))
                val warnings = mutableListOf<String>()
                ReceivedPackageValidation.checkAssets(zip, catalog, warnings)
                require(warnings.isEmpty()) { "同步图标无效。" }
                val images = catalog.getJSONArray("resources")
                val groups = catalog.getJSONArray("categories")
                val specs = (0 until images.length()).map(images::getJSONObject).associateBy { it.getString("sync_key") }
                for (i in 0 until groups.length()) {
                    val icon = groups.getJSONObject(i).optJSONObject("icon") ?: continue
                    if (icon.optString("type") == "image") {
                        val path = icon.getString("asset_path")
                        require(syncIconAsset.matches(path) && path.startsWith("icons/${icon.getString("asset_sha256")}.")) {
                            "图标哈希或路径无效。"
                        }
                        staged[path] = icon
                    }
                }
                require(specs.keys == assets.keys && staged.keys == assets.icons) { "收到的资源包与同步清单不符。" }
                val importer = ResourceImportService(context, db)
                for ((key, spec) in specs) {
                    currentCoroutineContext().ensureActive()
                    val path = spec.getString("asset_path")
                    val bytes = ReceivedPackageValidation.readAsset(zip, zip.getEntry(path) ?: error("同步图片缺失。"))
                    val content = importer.prepareSyncAsset(bytes, key)
                    check(resourceDir.isDirectory || resourceDir.mkdirs()) { "无法创建资源目录。" }
                    val file = File.createTempFile("sync-", if (content.isAnimated) ".gif" else ".png", resourceDir)
                    created.add(file)
                    FileOutputStream(file).use { it.write(content.bytes); it.fd.sync() }
                    additions[key] = ResourceEntity(filename = file.name, format = content.format,
                        isAnimated = content.isAnimated, syncKey = key, pixelMd5 = content.pixelMd5, fileMd5 = content.fileMd5,
                        width = content.width, height = content.height, byteSize = content.bytes.size.toLong(),
                        createdAt = spec.optLong("created_at", System.currentTimeMillis()))
                }
                for ((asset, icon) in staged) {
                    val path = checkNotNull(PackageIcons.restore(context, zip, icon, created))
                    verifyIcon(File(context.filesDir, path.removePrefix("file:")), asset)
                    iconPaths[asset] = path
                }
            } else require(!assets.any) { "缺少同步资源包。" }

            for (asset in target.categories.mapNotNull { it.icon?.takeIf { i -> i.type == "image" }?.value }.distinct()) {
                if (asset in iconPaths) continue
                val source = before.icons.getValue(asset)
                val relative = "category_icons/${asset.substringAfter('/')}"
                val targetFile = File(context.filesDir, relative)
                if (!targetFile.exists()) {
                    check(targetFile.parentFile!!.isDirectory || targetFile.parentFile!!.mkdirs())
                    // Copy resource-backed icons so a later resource deletion cannot break a category.
                    created.add(targetFile)
                    source.copyTo(targetFile)
                }
                verifyIcon(targetFile, asset)
                iconPaths[asset] = "file:$relative"
            }
            return block(prepared)
        } finally {
            if (!prepared.committed) created.forEach { it.delete() }
        }
    }

    internal class Prepared(val resources: Map<String, ResourceEntity>, val icons: Map<String, String>) {
        var committed = false
    }

    suspend fun apply(device: String, peer: String, revision: String, before: Capture, target: SyncLibrary,
                      bindings: Map<String, String>, prepared: Prepared) = withContext(NonCancellable) {
        val removed = db.withTransaction {
            val current = read(device, peer)
            // Usage changes are local; do not undo them or treat them as a reconciliation conflict.
            if (current.revision != before.revision || current.library != before.library ||
                current.resources.mapValues { it.value.filename } != before.resources.mapValues { it.value.filename } ||
                current.categories.mapValues { it.value.id } != before.categories.mapValues { it.value.id } ||
                current.relations != before.relations)
                throw IOException("同步期间手机图库已修改，请重试。")
            val targetsByLocal = target.categories.mapNotNull { c -> bindings[c.id]?.let { it to c } }.toMap()
            for ((id, row) in current.categories) {
                val next = targetsByLocal[id]
                if (next == null) categories.deleteCategoryById(row.id)
                else if (next.name != row.name)
                    categories.updateCategory(row.copy(name = "__suzu_sync_${newId()}"))
            }
            val localBindings = JSONObject()
            val ids = target.categories.mapIndexed { index, category ->
                val previous = bindings[category.id]?.let(current.categories::getValue)
                val icon = when (val i = category.icon) {
                    null -> null
                    else -> if (i.type == "text") "text:${i.value}" else prepared.icons.getValue(i.value)
                }
                val row = (previous ?: CategoryEntity(name = category.name)).copy(name = category.name, sortOrder = index, iconPath = icon)
                val localId = if (previous == null) categories.insertCategory(row) else row.id.also { categories.updateCategory(row) }
                localBindings.put(localId.toString(), category.id)
                category.id to localId
            }.toMap()
            val keys = target.resources.map { it.key }.toSet()
            val deleting = current.resources.filterKeys { it !in keys }.values.toList()
            deleting.forEach { resources.deleteById(it.id) }

            val currentLinks = current.relations.groupBy { it.resourceId }
            val nextSort = current.relations.groupBy { it.categoryId }.mapValues { (_, rows) -> rows.maxOf { it.sortOrder } }.toMutableMap()
            for ((index, item) in target.resources.withIndex()) {
                val old = current.resources[item.key]
                val row = (old ?: prepared.resources.getValue(item.key)).copy(sortOrder = index, keywords = item.keywords.joinToString(" "))
                val id = if (old == null) resources.insert(row) else old.id.also { if (row != old) resources.update(row) }
                val wanted = item.categories.map(ids::getValue).toSet()
                val existing = currentLinks[id].orEmpty().map { it.categoryId }.toSet()
                for (category in existing - wanted) relations.removeResourceFromCategory(id, category)
                for (category in wanted - existing) {
                    val sort = (nextSort[category] ?: -1) + 1
                    nextSort[category] = sort
                    relations.addResourceToCategory(ResourceCategoryEntity(id, category, sortOrder = sort))
                }
            }
            put("bindings:$peer", localBindings.toString())
            put("revision:$peer", revision)
            deleting
        }
        prepared.committed = true
        // Room is the source of truth. A crash here cannot reintroduce deleted rows.
        if (removed.isNotEmpty()) {
            val trash = File(context.filesDir, "lan-sync-trash/$revision")
            if (trash.isDirectory || trash.mkdirs())
                removed.forEach { resourceFile(it.filename).renameTo(File(trash, it.filename)) }
        }
    }

    private suspend fun put(key: String, value: String) = state.write(LanSyncStateEntity(key, value))

    private fun resourceFile(filename: String): File {
        require(filename.isNotBlank() && '/' !in filename && '\\' !in filename && filename !in setOf(".", ".."))
        return File(resourceDir, filename)
    }

    private fun metadata(zip: ZipFile, entry: String): String =
        ReceivedPackageValidation.readMetadata(zip, zip.getEntry(entry) ?: error("资源包缺少 $entry")).toString(Charsets.UTF_8)

    private fun verifyIcon(file: File, asset: String) {
        require(asset.startsWith("icons/${iconDigest(file)}.")) { "收藏夹图标校验失败。" }
    }

    private fun iconDigest(file: File): String {
        if (!file.isFile || file.length() !in 1..(48L shl 20)) throw IOException("同步图标不可读取或超出大小限制。")
        val sha = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buffer = ByteArray(65_536)
            while (true) {
                val count = input.read(buffer)
                if (count < 0) break
                sha.update(buffer, 0, count)
            }
        }
        return hex(sha.digest())
    }

    private fun digest(bytes: ByteArray) = hex(MessageDigest.getInstance("SHA-256").digest(bytes))
    private fun hex(bytes: ByteArray) = bytes.joinToString("") { "%02x".format(it) }
    private fun newId() = UUID.randomUUID().toString().replace("-", "")
}
