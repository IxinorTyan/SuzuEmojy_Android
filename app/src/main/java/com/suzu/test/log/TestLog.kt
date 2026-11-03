package com.suzu.test.log

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.ArrayDeque
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object TestLog {
    private const val TAG = "TestPoC"
    private const val MAX_LOG_LINES = 300
    private val timeFormat = ThreadLocal.withInitial { SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()) }
    private val logLock = Any()
    private val memoryLogs = ArrayDeque<String>()
    private val pendingLogs = ArrayDeque<String>()
    private val listeners = CopyOnWriteArrayList<(String) -> Unit>()
    private val fileExecutor = Executors.newSingleThreadScheduledExecutor()
    private var flushScheduled = false
    @Volatile private var logFile: File? = null

    fun init(context: Context) = synchronized(logLock) {
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
        val time = timeFormat.get()!!.format(Date())
        val formatted = "[$time][$module] $content"

        // 1. Logcat 输出
        when (level) {
            "ERROR" -> Log.e(TAG, formatted)
            "WARN" -> Log.w(TAG, formatted)
            else -> Log.i(TAG, formatted)
        }

        // 2. 内存列表 (仅保留最近 300 行)
        synchronized(logLock) {
            memoryLogs.addLast(formatted)
            while (memoryLogs.size > MAX_LOG_LINES) memoryLogs.removeFirst()
            if (logFile != null) {
                pendingLogs.addLast(formatted)
                // The on-disk log also retains only the latest entries. Bound pending memory
                // even when storage cannot keep up with a burst of image-processing logs.
                while (pendingLogs.size > MAX_LOG_LINES) pendingLogs.removeFirst()
                if (!flushScheduled) {
                    flushScheduled = true
                    fileExecutor.schedule({ flushPendingLogs() }, 250, TimeUnit.MILLISECONDS)
                }
            }
        }

        // 3. 通知实时监听器 (UI展示)
        for (listener in listeners) {
            try {
                listener.invoke(formatted)
            } catch (e: Exception) {
                // ignore listener errors
            }
        }

    }

    private fun flushPendingLogs() {
        val batch = synchronized(logLock) {
            val lines = pendingLogs.toList()
            pendingLogs.clear()
            flushScheduled = false
            lines
        }
        val file = logFile ?: return
        if (batch.isEmpty()) return
        try {
            // A cache cleanup may have removed the directory while this process is alive.
            file.parentFile?.mkdirs()
            FileWriter(file, true).use { it.write(batch.joinToString("\n", postfix = "\n")) }
            trimLogFile(file)
        } catch (e: Exception) {
            Log.e(TAG, "写入日志文件异常", e)
        }
    }

    fun addListener(listener: (String) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (String) -> Unit) {
        listeners.remove(listener)
    }

    fun getAllLogs(): String {
        return synchronized(logLock) { memoryLogs.joinToString("\n") }
    }

    fun clear() = synchronized(logLock) {
        memoryLogs.clear()
        pendingLogs.clear()
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
            val savedLines = file.readLines()
            synchronized(logLock) {
                val merged = savedLines.takeLast(MAX_LOG_LINES) + memoryLogs.toList()
                memoryLogs.clear()
                memoryLogs.addAll(merged.takeLast(MAX_LOG_LINES))
            }
            if (savedLines.size > MAX_LOG_LINES) {
                file.writeText(savedLines.takeLast(MAX_LOG_LINES).joinToString("\n") + "\n")
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
