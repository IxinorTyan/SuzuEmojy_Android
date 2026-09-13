package com.suzu.test.ui.picker

import android.content.Context
import android.net.Uri
import com.suzu.test.log.TestLog
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.util.UUID

object PickerResultStore {
    private const val MODULE = "PickerResultStore"
    private const val RESULT_DIR_NAME = "picker_results"

    fun clearResidualFiles(context: Context) {
        try {
            val dir = File(context.cacheDir, RESULT_DIR_NAME)
            if (dir.exists() && dir.isDirectory) {
                dir.listFiles()?.forEach { file ->
                    file.delete()
                }
            }
        } catch (e: Exception) {
            TestLog.e(MODULE, "清理历史残留文件异常: ${e.message}", e)
        }
    }

    fun saveResultUris(context: Context, uris: Collection<Uri>): String? {
        val token = UUID.randomUUID().toString()
        val dir = File(context.cacheDir, RESULT_DIR_NAME)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "$token.txt")
        return try {
            PrintWriter(FileWriter(file)).use { writer ->
                for (uri in uris) {
                    writer.println(uri.toString())
                }
            }
            token
        } catch (e: Exception) {
            TestLog.e(MODULE, "保存选择结果失败: ${e.message}", e)
            file.delete()
            null
        }
    }

    fun consumeResultUris(context: Context, token: String?): List<Uri> {
        if (token.isNullOrBlank()) return emptyList()
        val dir = File(context.cacheDir, RESULT_DIR_NAME)
        val file = File(dir, "$token.txt")
        if (!file.exists()) {
            TestLog.w(MODULE, "选择结果文件不存在: ${file.absolutePath}")
            return emptyList()
        }

        val result = mutableListOf<Uri>()
        try {
            BufferedReader(FileReader(file)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val trimmed = line.trim()
                    if (trimmed.isNotEmpty()) {
                        result.add(Uri.parse(trimmed))
                    }
                    line = reader.readLine()
                }
            }
        } catch (e: Exception) {
            TestLog.e(MODULE, "读取选择结果失败: ${e.message}", e)
        } finally {
            file.delete()
        }
        return result
    }
}
