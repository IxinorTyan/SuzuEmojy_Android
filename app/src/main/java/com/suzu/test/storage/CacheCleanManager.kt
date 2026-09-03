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
     * 手动清理: send/ 过期文件 + Glide clearDiskCache()。
     * freedBytes 为清理前后 cacheDir 总量差。
     */
    suspend fun cleanAll(context: Context, maxAgeMs: Long = DEFAULT_MAX_AGE_MS): CleanResult = withContext(Dispatchers.IO) {
        val beforeSize = calculateDirectorySize(context.cacheDir)

        // 1. send/ 过期文件清理
        val sendDir = File(context.cacheDir, "send")
        var deleted = 0
        var failed = 0
        val now = System.currentTimeMillis()

        if (sendDir.exists() && sendDir.isDirectory) {
            val files = sendDir.listFiles() ?: emptyArray()
            for (file in files) {
                if (file.isFile) {
                    val age = now - file.lastModified()
                    if (age > maxAgeMs) {
                        val beforeExists = file.exists()
                        val beforeLength = file.length()
                        val deletedNow = file.delete()
                        if (deletedNow) {
                            deleted++
                        } else {
                            failed++
                        }
                        val record = ImageSendDiagnostics.recordForFile(file.absolutePath)
                        ImageSendDiagnostics.recordCleanup(
                            eventId = record?.eventId,
                            uri = record?.uri?.takeIf { it.isNotBlank() }?.let(Uri::parse),
                            file = file,
                            reason = "缓存清理 cleanAll（超过${maxAgeMs / 1000}秒）",
                            beforeExists = beforeExists,
                            beforeLength = beforeLength,
                            result = if (deletedNow) "SUCCESS" else "FAILED"
                        )
                    }
                }
            }
        }

        // 2. 清理 Glide 磁盘缓存 (后台线程调用)
        try {
            Glide.get(context.applicationContext).clearDiskCache()
        } catch (e: Exception) {
            TestLog.e(MODULE, "Glide clearDiskCache 异常: ${e.message}", e)
        }

        val afterSize = calculateDirectorySize(context.cacheDir)
        val freed = (beforeSize - afterSize).coerceAtLeast(0L)

        TestLog.i(MODULE, "cleanAll 完成: send删除 $deleted 个, 失败 $failed 个, 清理前 ${formatSize(beforeSize)}, 清理后 ${formatSize(afterSize)}, 释放 ${formatSize(freed)}")
        CleanResult(freed, deleted, failed)
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
