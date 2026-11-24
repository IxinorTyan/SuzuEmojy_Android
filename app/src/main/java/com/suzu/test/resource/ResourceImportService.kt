package com.suzu.test.resource

import android.content.Context
import android.graphics.BitmapFactory
import androidx.room.withTransaction
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.clean.CleanResult
import com.suzu.test.resource.clean.ResourceCleaner
import com.suzu.test.resource.hash.ResourceHasher
import com.suzu.test.resource.name.FilenameGenerator
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.File

data class ImportResult(
    val resourceId: Long,
    val filename: String,
    val isDuplicate: Boolean,
    val syncKey: String = "",
    val existingResourceId: Long? = null,
    val existingFilename: String? = null
)

class ResourceImportService(
    private val context: Context,
    private val database: SuzuDatabase,
    private val targetDir: File = File(context.filesDir, "resources")
) {
    companion object {
        private const val MODULE = "ResourceImportService"
    }

    internal data class Content(
        val bytes: ByteArray,
        val format: String,
        val isAnimated: Boolean,
        val pixelMd5: String?,
        val fileMd5: String,
        val width: Int,
        val height: Int
    )

    internal data class PreparedResource(
        val syncKey: String,
        val content: Content?,
        // Retain the source only for a duplicate: it may be deleted before the writer reaches it.
        val sourceBytes: ByteArray? = null,
        val local: Boolean = false,
        val verified: ResourceEntity? = null
    )

    suspend fun import(bytes: ByteArray): ImportResult = importPrepared(prepare(bytes))

    internal suspend fun importSynced(bytes: ByteArray, expectedKey: String): ImportResult {
        val prepared = prepare(bytes, checkDuplicate = true, local = false)
        require(prepared.syncKey == expectedKey) { "资源的 sync_key 校验失败" }
        return importPrepared(prepared, recordDuplicateUse = false)
    }

    internal suspend fun prepare(bytes: ByteArray): PreparedResource = prepare(bytes, checkDuplicate = true, local = true)

    internal suspend fun prepareSyncAsset(bytes: ByteArray, expectedKey: String): Content =
        prepare(bytes, checkDuplicate = false, local = false).also {
            require(it.syncKey == expectedKey) { "资源的 sync_key 校验失败" }
        }.content ?: error("资源没有可同步的内容")

    private suspend fun prepare(bytes: ByteArray, checkDuplicate: Boolean, local: Boolean): PreparedResource {
        currentCoroutineContext().ensureActive()
        if (ResourceHasher.isGif(bytes) && (!local || !ResourceCleaner.isSingleFrameGif(bytes))) {
            return when (val clean = ResourceCleaner.passthroughGif(bytes)) {
                is CleanResult.GifImage -> {
                    val key = "f:${clean.md5}"
                    val existing = if (checkDuplicate) database.resourceDao().getBySyncKey(key) else null
                    if (existing != null) {
                        if (local) require(matchesBytes(File(targetDir, existing.filename), bytes)) { "GIF identity collision" }
                        PreparedResource(key, null, bytes, local, existing)
                    } else PreparedResource(key, Content(
                        clean.gifBytes, "GIF", true, null, clean.md5, clean.width, clean.height
                    ), local = local)
                }
                is CleanResult.UnsupportedFormat -> throw IllegalArgumentException("Unsupported format: ${clean.reason}")
                else -> error("Expected GIF")
            }
        }
        if (ResourceCleaner.isAnimatedWebP(bytes) || (local && ResourceCleaner.isAnimatedPng(bytes))) {
            throw IllegalArgumentException("Unsupported animated image format")
        }
        val decoded = ResourceCleaner.decodeStatic(bytes, normalize = local)
            ?: throw IllegalArgumentException("Unsupported format: Failed to decode image bytes into RGBA Bitmap")
        return decoded.use {
            currentCoroutineContext().ensureActive()
            val key = "p:${it.md5}"
            val existing = if (checkDuplicate) database.resourceDao().getBySyncKey(key) else null
            if (existing != null) {
                if (local) require(it.matches(File(targetDir, existing.filename))) { "PNG identity collision" }
                PreparedResource(key, null, bytes, local, existing)
            } else {
                // During migration (or when a paired library must keep a wire identity),
                // the same source may still be stored at its original dimensions.
                if (local && checkDuplicate && (it.wasScaled || ResourceHasher.isGif(bytes))) findUnscaled(bytes, key)?.let { old ->
                    return PreparedResource(old.syncKey, null, bytes, local, old)
                }
                val clean = it.encode()
                PreparedResource(key, Content(
                    clean.pngBytes, "PNG", false, clean.md5, ResourceHasher.fileMd5(clean.pngBytes),
                    clean.width, clean.height
                ), local = local)
            }
        }
    }

    private suspend fun findUnscaled(bytes: ByteArray, normalized: String): ResourceEntity? {
        val dao = database.resourceDao()
        if (ResourceHasher.isGif(bytes)) {
            val old = dao.getBySyncKey("f:${ResourceHasher.fileMd5(bytes)}") ?: return null
            return old.takeIf { matchesBytes(File(targetDir, it.filename), bytes) }
        }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        if (bounds.outWidth.toLong() * bounds.outHeight > Runtime.getRuntime().maxMemory() / 24 ||
            !dao.hasDimensions(bounds.outWidth, bounds.outHeight)) return null
        return ResourceCleaner.decodeStatic(bytes)?.use { original ->
            val key = "p:${original.md5}"
            if (key == normalized) return null
            dao.getBySyncKey(key)?.takeIf { original.matches(File(targetDir, it.filename)) }
        }
    }

    /** The callback may write related DB rows; all rows commit or roll back together. */
    internal suspend fun importPrepared(
        initial: PreparedResource,
        recordDuplicateUse: Boolean = true,
        onImported: suspend (ImportResult) -> Unit = {}
    ): ImportResult {
        var prepared = initial
        while (true) {
            currentCoroutineContext().ensureActive()
            val content = prepared.content
            var stagedFile: File? = null
            var keepFile = false
            try {
                // Decode, encode and verify outside the DB transaction.
                if (content != null) {
                    if (!targetDir.isDirectory) targetDir.mkdirs()
                    check(targetDir.isDirectory) { "无法创建资源目录" }
                    stagedFile = File(targetDir, FilenameGenerator.generate(if (content.isAnimated) ".gif" else ".png"))
                    stagedFile.outputStream().use { it.write(content.bytes) }
                    check(stagedFile.length() == content.bytes.size.toLong()) { "文件落盘或大小校验失败" }
                    val writtenMd5 = stagedFile.inputStream().use { ResourceHasher.fileMd5(it) }
                    check(writtenMd5 == content.fileMd5) { "落盘文件 MD5 校验不匹配" }
                }
                currentCoroutineContext().ensureActive()
                val result = withContext(NonCancellable) {
                    database.withTransaction {
                        val dao = database.resourceDao()
                        // Preparation may run ahead. Recheck identity under the write transaction.
                        val existing = dao.getBySyncKey(prepared.syncKey)
                        if (existing == null && content == null) return@withTransaction null
                        if (prepared.local && existing != null) {
                            val verified = prepared.verified
                            if (content == null) {
                                if (verified == null || verified.id != existing.id || verified.filename != existing.filename ||
                                    verified.fileMd5 != existing.fileMd5) return@withTransaction null
                            } else {
                                val file = File(targetDir, existing.filename)
                                val equal = if (content.isAnimated) matchesBytes(file, content.bytes) else
                                    ResourceCleaner.decodeStatic(content.bytes)?.use { it.matches(file) } == true
                                require(equal) { "Resource identity collision" }
                            }
                        }
                        val sortOrder = (dao.getMinSortOrder() ?: 0) - 1
                        val now = System.currentTimeMillis()
                        val imported = if (existing != null) {
                            if (recordDuplicateUse) dao.update(existing.copy(sortOrder = sortOrder, lastUsedAt = now, useCount = existing.useCount + 1))
                            ImportResult(existing.id, existing.filename, true, prepared.syncKey, existing.id, existing.filename)
                        } else {
                            checkNotNull(content)
                            val filename = checkNotNull(stagedFile).name
                            val id = dao.insert(ResourceEntity(
                                filename = filename,
                                format = content.format,
                                isAnimated = content.isAnimated,
                                syncKey = prepared.syncKey,
                                pixelMd5 = content.pixelMd5,
                                fileMd5 = content.fileMd5,
                                width = content.width,
                                height = content.height,
                                byteSize = content.bytes.size.toLong(),
                                qualityScore = 0.0,
                                useCount = 0,
                                lastUsedAt = now,
                                createdAt = now,
                                keywords = "",
                                sortOrder = sortOrder
                            ))
                            ImportResult(id, filename, false, prepared.syncKey)
                        }
                        onImported(imported)
                        imported
                    }.also { keepFile = it != null && !it.isDuplicate }
                }
                if (result != null) {
                    TestLog.i(MODULE, "import ${if (result.isDuplicate) "去重命中" else "成功入库"}: ID=${result.resourceId}, syncKey=${result.syncKey}")
                    return result
                }
            } finally {
                if (!keepFile) stagedFile?.delete()
            }
            // A duplicate was deleted after preparation. Rebuild outside the transaction.
            prepared = prepare(checkNotNull(prepared.sourceBytes), checkDuplicate = false, local = prepared.local)
        }
    }

    private fun matchesBytes(file: File, bytes: ByteArray): Boolean {
        if (!file.isFile || file.length() != bytes.size.toLong()) return false
        return file.inputStream().buffered().use { stream -> bytes.all { (it.toInt() and 255) == stream.read() } }
    }
}
