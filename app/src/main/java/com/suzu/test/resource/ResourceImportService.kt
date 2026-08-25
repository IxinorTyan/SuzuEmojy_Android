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
import java.io.File
import java.io.FileOutputStream

data class ImportResult(
    val resourceId: Long,
    val filename: String,
    val isDuplicate: Boolean
)

class ResourceImportService(
    private val context: Context,
    private val database: SuzuDatabase,
    private val targetDir: File = File(context.filesDir, "resources")
) {

    companion object {
        private const val MODULE = "ResourceImportService"
    }

    private val resourcesDir: File
        get() {
            if (!targetDir.exists()) targetDir.mkdirs()
            return targetDir
        }

    suspend fun import(bytes: ByteArray): ImportResult {
        // a. 嗅探类型与清洗
        val isGif = ResourceHasher.isGif(bytes)
        val cleanResult = if (isGif) {
            ResourceCleaner.passthroughGif(bytes)
        } else {
            ResourceCleaner.sanitizeStatic(bytes)
        }

        val tuple = when (cleanResult) {
            is CleanResult.StaticImage -> TupleData(
                bytes = cleanResult.pngBytes,
                format = "PNG",
                isAnimated = false,
                syncKey = "p:${cleanResult.md5}",
                pixelMd5 = cleanResult.md5,
                fileMd5 = ResourceHasher.fileMd5(cleanResult.pngBytes),
                width = cleanResult.width,
                height = cleanResult.height
            )
            is CleanResult.GifImage -> TupleData(
                bytes = cleanResult.gifBytes,
                format = "GIF",
                isAnimated = true,
                syncKey = "f:${cleanResult.md5}",
                pixelMd5 = null,
                fileMd5 = cleanResult.md5,
                width = 0,
                height = 0
            )
            is CleanResult.UnsupportedFormat -> {
                TestLog.e(MODULE, "import 失败: 不支持的格式 (${cleanResult.reason})")
                throw IllegalArgumentException("Unsupported format: ${cleanResult.reason}")
            }
        }
        val (finalBytes, format, isAnimated, syncKey, pixelMd5, fileMd5, width, height) = tuple

        val resourceDao = database.resourceDao()

        // b. 查 DB 判断是否已存在
        val existing = resourceDao.getBySyncKey(syncKey)

        val minSort = resourceDao.getMinSortOrder() ?: 0
        val newSortOrder = minSort - 1

        // c. 已存在 → 提升至最前 (更新 sortOrder 及 lastUsedAt)，返回 isDuplicate = true (不写文件)
        if (existing != null) {
            val now = System.currentTimeMillis()
            resourceDao.update(existing.copy(sortOrder = newSortOrder, lastUsedAt = now, useCount = existing.useCount + 1))
            TestLog.i(MODULE, "import 去重命中: syncKey=$syncKey, 已提升现有资源 ID=${existing.id}")
            return ImportResult(existing.id, existing.filename, isDuplicate = true)
        }

        // d. 不存在 → 生成文件名并写入私有目录
        val filename = FilenameGenerator.generate(if (isAnimated) ".gif" else ".png")
        val targetFile = File(resourcesDir, filename)
        FileOutputStream(targetFile).use { it.write(finalBytes); it.flush() }

        // e. 回读校验文件存在、大小一致与 MD5 一致
        if (!targetFile.exists() || targetFile.length() != finalBytes.size.toLong()) {
            targetFile.delete()
            throw IllegalStateException("文件落盘或大小校验失败: ${targetFile.absolutePath}")
        }
        val writtenMd5 = ResourceHasher.fileMd5(targetFile.readBytes())
        if (writtenMd5 != fileMd5) {
            targetFile.delete()
            throw IllegalStateException("落盘文件 MD5 校验不匹配: expected $fileMd5, actual $writtenMd5")
        }

        // f. 事务插入 resources 行
        val now = System.currentTimeMillis()
        val entity = ResourceEntity(
            filename = filename,
            format = format,
            isAnimated = isAnimated,
            syncKey = syncKey,
            pixelMd5 = pixelMd5,
            fileMd5 = fileMd5,
            width = width,
            height = height,
            byteSize = finalBytes.size.toLong(),
            qualityScore = 0.0,
            useCount = 0,
            lastUsedAt = now,
            createdAt = now,
            keywords = "",
            sortOrder = newSortOrder
        )

        val insertedId = database.withTransaction {
            resourceDao.insert(entity)
        }

        TestLog.i(MODULE, "import 成功入库: ID=$insertedId, filename=$filename, syncKey=$syncKey")
        return ImportResult(
            resourceId = insertedId,
            filename = filename,
            isDuplicate = false
        )
    }

    private data class TupleData(
        val bytes: ByteArray,
        val format: String,
        val isAnimated: Boolean,
        val syncKey: String,
        val pixelMd5: String?,
        val fileMd5: String,
        val width: Int,
        val height: Int
    )
}
