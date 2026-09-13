package com.suzu.test.log

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

object TestLog {
    private const val TAG = "TestPoC"
    private const val MAX_LOG_LINES = 300
    private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    private val memoryLogs = CopyOnWriteArrayList<String>()
    private val listeners = CopyOnWriteArrayList<(String) -> Unit>()
    private val fileExecutor = Executors.newSingleThreadExecutor()
    private var logFile: File? = null

    fun init(context: Context) {
        if (logFile == null) {
            val dir = File(context.cacheDir, "logs")
            if (!dir.exists()) {
                dir.mkdirs()
            }
            logFile = File(dir, "test_poc.log")
            val file = logFile ?: return
            fileExecutor.execute {
                loadAndTrimLogFile(file)
            }
        }
    }

    fun i(module: String, message: String) {
        log("INFO", module, message)
    }

    fun w(module: String, message: String) {
        log("WARN", module, message)
    }

    fun e(module: String, message: String, throwable: Throwable? = null) {
        val extra = if (throwable != null) "\n" + Log.getStackTraceString(throwable) else ""
        log("ERROR", module, message + extra)
    }

    private fun log(level: String, module: String, content: String) {
        val time = timeFormat.format(Date())
        val formatted = "[$time][$module] $content"

        // 1. Logcat 输出
        when (level) {
            "ERROR" -> Log.e(TAG, formatted)
            "WARN" -> Log.w(TAG, formatted)
            else -> Log.i(TAG, formatted)
        }

        // 2. 内存列表 (仅保留最近 300 行)
        memoryLogs.add(formatted)
        while (memoryLogs.size > MAX_LOG_LINES) {
            memoryLogs.removeAt(0)
        }

        // 3. 通知实时监听器 (UI展示)
        for (listener in listeners) {
            try {
                listener.invoke(formatted)
            } catch (e: Exception) {
                // ignore listener errors
            }
        }

        // 4. 异步持久化到本地文件
        logFile?.let { file ->
            fileExecutor.execute {
                try {
                    FileWriter(file, true).use { writer ->
                        writer.write(formatted + "\n")
                    }
                    trimLogFile(file)
                } catch (e: Exception) {
                    Log.e(TAG, "写入日志文件异常", e)
                }
            }
        }
    }

    fun addListener(listener: (String) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (String) -> Unit) {
        listeners.remove(listener)
    }

    fun getAllLogs(): String {
        return memoryLogs.joinToString("\n")
    }

    fun clear() {
        memoryLogs.clear()
        fileExecutor.execute {
            try {
                logFile?.delete()
            } catch (e: Exception) {
                // ignore
            }
        }
    }

    private fun loadAndTrimLogFile(file: File) {
        try {
            if (!file.exists()) return
            val lines = file.readLines().takeLast(MAX_LOG_LINES)
            memoryLogs.clear()
            memoryLogs.addAll(lines)
            if (file.readLines().size > MAX_LOG_LINES) {
                file.writeText(lines.joinToString("\n") + "\n")
            }
        } catch (e: Exception) {
            Log.e(TAG, "加载或清理日志文件异常", e)
        }
    }

    private fun trimLogFile(file: File) {
        try {
            if (!file.exists()) return
            val lines = file.readLines()
            if (lines.size > MAX_LOG_LINES) {
                file.writeText(lines.takeLast(MAX_LOG_LINES).joinToString("\n") + "\n")
            }
        } catch (e: Exception) {
            Log.e(TAG, "清理日志文件异常", e)
        }
    }
}
