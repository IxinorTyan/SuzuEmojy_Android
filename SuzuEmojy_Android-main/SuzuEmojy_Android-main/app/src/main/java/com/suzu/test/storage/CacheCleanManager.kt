package com.suzu.test.storage

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.suzu.test.ime.diag.ImageSendDiagnostics
import com.suzu.test.log.TestLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.DecimalFormat

object CacheCleanManager {

    private const val MODULE = "CacheCleanManager"
    private const val DEFAULT_MAX_AGE_MS = 10 * 60 * 1000L // 10 分钟

    data class CleanResult(
        val freedBytes: Long,      // 释放字节数
        val deletedCount: Int,     // send/ 删除成功数
        val failedCount: Int       // send/ 删除失败数
    )

    /**
     * 递归计算整个 cacheDir 的总占用体积
     */
    suspend fun getCacheSizeBytes(context: Context): Long = withContext(Dispatchers.IO) {
        try {
            calculateDirectorySize(context.cacheDir)
        } catch (e: Exception) {
            TestLog.e(MODULE, "计算 cacheDir 大小异常: ${e.message}", e)
            0L
        }
    }

    private fun calculateDirectorySize(dir: File?): Long {
        if (dir == null || !dir.exists()) return 0L
        if (dir.isFile) return dir.length()
        var size = 0L
        val children = dir.listFiles() ?: return 0L
        for (child in children) {
            size += if (child.isDirectory) calculateDirectorySize(child) else child.length()
        }
        return size
    }

    /**
     * 仅清理 send/ 目录中超过 maxAgeMs 的过期文件。
     * freedBytes 为被删除文件的 length() 累加之和，避免全量扫 Glide 目录。
     */
    suspend fun cleanExpired(context: Context, maxAgeMs: Long = DEFAULT_MAX_AGE_MS): CleanResult = withContext(Dispatchers.IO) {
        val sendDir = File(context.cacheDir, "send")
        if (!sendDir.exists() || !sendDir.isDirectory) {
            return@withContext CleanResult(0L, 0, 0)
        }

        val now = System.currentTimeMillis()
        var freed = 0L
        var deleted = 0
        var failed = 0

        val files = sendDir.listFiles() ?: emptyArray()
        for (file in files) {
            if (file.isFile) {
                val age = now - file.lastModified()
                if (age > maxAgeMs) {
                    val beforeExists = file.exists()
                    val len = file.length()
                    val deletedNow = file.delete()
                    if (deletedNow) {
                        deleted++
                        freed += len
                    } else {
                        failed++
                    }
                    val record = ImageSendDiagnostics.recordForFile(file.absolutePath)
                    ImageSendDiagnostics.recordCleanup(
                        eventId = record?.eventId,
                        uri = record?.uri?.takeIf { it.isNotBlank() }?.let(Uri::parse),
                        file = file,
                        reason = "缓存清理 cleanExpired（超过${maxAgeMs / 1000}秒）",
                        beforeExists = beforeExists,
                        beforeLength = len,
                        result = if (deletedNow) "SUCCESS" else "FAILED"
                    )
                }
            }
        }

        TestLog.i(MODULE, "cleanExpired 完成: 删除了 $deleted 个过期文件, 失败 $failed 个, 释放 ${formatSize(freed)} (阈值: ${maxAgeMs / 1000}s)")
        CleanResult(freed, deleted, failed)
    }

    /**
     * 删除资源包导入过程中遗留的临时 ZIP。
     *
     * 只匹配 cacheDir 根目录下由导入服务生成的 import_tmp_*.zip，
     * 不递归、不删除其他缓存文件。
     */
    suspend fun cleanImportTempZipFiles(context: Context): CleanResult = withContext(Dispatchers.IO) {
        val cacheDir = context.cacheDir
        val targets = cacheDir.listFiles { file, name ->
            file == cacheDir && name.startsWith("import_tmp_") && name.endsWith(".zip")
        }?.filter { it.isFile } ?: emptyList()

        var freed = 0L
        var deleted = 0
        var failed = 0
        targets.forEach { file ->
            val length = file.length()
            if (file.delete()) {
                deleted++
                freed += length
            } else if (file.exists()) {
                failed++
            }
        }

        if (deleted > 0 || failed > 0) {
            TestLog.i(MODULE, "清理 zip 导入临时文件: 删除 $deleted 个, 失败 $failed 个, 释放 ${formatSize(freed)}")
        }
        CleanResult(freed, deleted, failed)
    }

    /**
     * 手动清理整个应用缓存。
     *
     * 手动清理的语义是尽可能清空 cacheDir，而不是沿用自动清理的过期时间阈值。
     * 资源包导入产生的 ZIP、预览图片以及 send/、Glide 等缓存都会被处理。
     * freedBytes 为清理前后 cacheDir 总量差。
     */
    suspend fun cleanAll(context: Context): CleanResult = withContext(Dispatchers.IO) {
        val beforeSize = calculateDirectorySize(context.cacheDir)

        // Glide 的磁盘缓存由 Glide 自身管理，先使用其 API 清理。
        try {
            Glide.get(context.applicationContext).clearDiskCache()
        } catch (e: Exception) {
            TestLog.e(MODULE, "Glide clearDiskCache 异常: ${e.message}", e)
        }

        // 手动清理不使用 maxAgeMs：cacheDir 下的所有文件都属于可删除缓存。
        val counters = CacheDeleteCounters()
        deleteCacheContents(context.cacheDir, counters)

        val afterSize = calculateDirectorySize(context.cacheDir)
        val freed = (beforeSize - afterSize).coerceAtLeast(0L)

        TestLog.i(
            MODULE,
            "cleanAll 完成: 删除 ${counters.deleted} 个缓存文件, 失败 ${counters.failed} 个, " +
                "清理前 ${formatSize(beforeSize)}, 清理后 ${formatSize(afterSize)}, 释放 ${formatSize(freed)}"
        )
        CleanResult(freed, counters.deleted, counters.failed)
    }

    /**
     * 删除指定的 zip 导入预览暂存文件。
     *
     * 只允许删除 cacheDir/import_previews 目录下的文件，避免调用方传入
     * 非暂存路径时误删正式资源。
     */
    suspend fun cleanImportPreviewFiles(
        context: Context,
        paths: Collection<String?> = emptyList()
    ): CleanResult = withContext(Dispatchers.IO) {
        val previewDir = File(context.cacheDir, "import_previews")
        val allowedPath = runCatching { previewDir.canonicalPath + File.separator }.getOrNull()
            ?: return@withContext CleanResult(0L, 0, 0)

        val targets = if (paths.isEmpty()) {
            previewDir.listFiles()?.filter { it.isFile } ?: emptyList()
        } else {
            paths.filterNotNull()
                .map { File(it) }
                .filter { file ->
                    runCatching {
                        file.isFile && file.canonicalPath.startsWith(allowedPath)
                    }.getOrDefault(false)
                }
        }.distinctBy { it.absolutePath }

        var freed = 0L
        var deleted = 0
        var failed = 0
        targets.forEach { file ->
            val length = file.length()
            if (file.delete()) {
                deleted++
                freed += length
            } else if (file.exists()) {
                failed++
            }
        }

        if (deleted > 0 || failed > 0) {
            TestLog.i(MODULE, "清理 zip 导入预览: 删除 $deleted 个, 失败 $failed 个, 释放 ${formatSize(freed)}")
        }
        CleanResult(freed, deleted, failed)
    }

    private class CacheDeleteCounters(
        var deleted: Int = 0,
        var failed: Int = 0
    )

    private fun deleteCacheContents(dir: File, counters: CacheDeleteCounters) {
        val children = dir.listFiles() ?: return
        for (child in children) {
            if (child.isDirectory) {
                deleteCacheContents(child, counters)
                if (child.exists() && !child.delete() && child.exists()) {
                    TestLog.w(MODULE, "删除缓存目录失败: ${child.absolutePath}")
                }
                continue
            }

            val beforeExists = child.exists()
            val beforeLength = child.length()
            val record = if (child.parentFile?.name == "send") {
                ImageSendDiagnostics.recordForFile(child.absolutePath)
            } else {
                null
            }
            val deletedNow = child.delete()

            if (deletedNow) {
                counters.deleted++
            } else if (child.exists()) {
                counters.failed++
            }

            if (record != null) {
                ImageSendDiagnostics.recordCleanup(
                    eventId = record.eventId,
                    uri = record.uri.takeIf { it.isNotBlank() }?.let(Uri::parse),
                    file = child,
                    reason = "缓存清理 cleanAll（手动清空）",
                    beforeExists = beforeExists,
                    beforeLength = beforeLength,
                    result = if (deletedNow) "SUCCESS" else "FAILED"
                )
            }
        }
    }

    fun formatSize(bytes: Long): String {
        if (bytes <= 0L) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB")
        var digitGroups = (Math.log10(bytes.toDouble()) / Math.log10(1024.0)).toInt()
        if (digitGroups >= units.size) digitGroups = units.size - 1
        val df = DecimalFormat("#,##0.##")
        return "${df.format(bytes / Math.pow(1024.0, digitGroups.toDouble()))} ${units[digitGroups]}"
    }
}
