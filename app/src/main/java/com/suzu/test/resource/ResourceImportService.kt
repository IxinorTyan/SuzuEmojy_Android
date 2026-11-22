package com.suzu.test.resource

import android.content.Context
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
        val sourceBytes: ByteArray? = null
    )

    suspend fun import(bytes: ByteArray): ImportResult = importPrepared(prepare(bytes))

    internal suspend fun importSynced(bytes: ByteArray, expectedKey: String): ImportResult {
        val prepared = prepare(bytes)
        require(prepared.syncKey == expectedKey) { "资源的 sync_key 校验失败" }
        return importPrepared(prepared, recordDuplicateUse = false)
    }

    internal suspend fun prepare(bytes: ByteArray): PreparedResource = prepare(bytes, checkDuplicate = true)

    internal suspend fun prepareSyncAsset(bytes: ByteArray, expectedKey: String): Content =
        prepare(bytes, checkDuplicate = false).also {
            require(it.syncKey == expectedKey) { "资源的 sync_key 校验失败" }
        }.content ?: error("资源没有可同步的内容")

    private suspend fun prepare(bytes: ByteArray, checkDuplicate: Boolean): PreparedResource {
        currentCoroutineContext().ensureActive()
        if (ResourceHasher.isGif(bytes)) {
            return when (val clean = ResourceCleaner.passthroughGif(bytes)) {
                is CleanResult.GifImage -> {
                    val key = "f:${clean.md5}"
                    if (checkDuplicate && database.resourceDao().getBySyncKey(key) != null) {
                        PreparedResource(key, null, bytes)
                    } else PreparedResource(key, Content(
                        clean.gifBytes, "GIF", true, null, clean.md5, clean.width, clean.height
                    ))
                }
                is CleanResult.UnsupportedFormat -> throw IllegalArgumentException("Unsupported format: ${clean.reason}")
                else -> error("Expected GIF")
            }
        }
        if (ResourceCleaner.isAnimatedWebP(bytes)) {
            throw IllegalArgumentException("Unsupported format: Animated WebP is not supported in v1")
        }
        val decoded = ResourceCleaner.decodeStatic(bytes)
            ?: throw IllegalArgumentException("Unsupported format: Failed to decode image bytes into RGBA Bitmap")
        return decoded.use {
            currentCoroutineContext().ensureActive()
            val key = "p:${it.md5}"
            // Pixel identity is enough to reject duplicates; do not encode unused PNGs.
            if (checkDuplicate && database.resourceDao().getBySyncKey(key) != null) {
                PreparedResource(key, null, bytes)
            } else {
                val clean = it.encode()
                PreparedResource(key, Content(
                    clean.pngBytes, "PNG", false, clean.md5, ResourceHasher.fileMd5(clean.pngBytes),
                    clean.width, clean.height
                ))
            }
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
            prepared = prepare(checkNotNull(prepared.sourceBytes), checkDuplicate = false)
        }
    }
}
