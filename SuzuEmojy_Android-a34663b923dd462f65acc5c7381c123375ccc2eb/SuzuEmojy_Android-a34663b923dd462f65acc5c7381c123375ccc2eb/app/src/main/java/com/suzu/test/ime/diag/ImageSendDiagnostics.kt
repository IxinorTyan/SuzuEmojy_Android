package com.suzu.test.ime.diag

import android.net.Uri
import java.io.File
import java.util.ArrayDeque
import java.util.concurrent.atomic.AtomicLong

/**
 * 发送诊断仅保存在进程内；不保存图片内容、Token 或其他敏感数据。
 */
object ImageSendDiagnostics {
    private val sequence = AtomicLong(0)
    private val lock = Any()
    private val records = ArrayDeque<Record>()
    private val uriEvents = HashMap<String, String>()
    private val listeners = mutableSetOf<() -> Unit>()

    data class Record(
        val eventId: String,
        val timestamp: Long,
        var targetPackage: String?,
        var targetUid: Int? = null,
        var uidResult: String = "FAILED",
        var uidExceptionType: String? = null,
        var uidExceptionMessage: String? = null,
        var uidExceptionStack: String? = null,
        var editorInfoPackage: String? = null,
        var inputConnection: String = "NULL",
        var resourceType: String = "",
        var source: String = "",
        var filePath: String = "",
        var fileExists: Boolean = false,
        var fileLength: Long = 0,
        var fileExtension: String = "",
        var detectedMime: String = "",
        var uri: String = "",
        var authority: String = "",
        var selfOpen: String = "FAILED",
        var selfStatSize: Long? = null,
        var providerMimeType: String? = null,
        var selfExceptionType: String? = null,
        var selfExceptionMessage: String? = null,
        var selfStack: String? = null,
        var permissionBefore: String = "UNKNOWN",
        var grantCall: String = "FAILED",
        var grantException: String? = null,
        var permissionAfter: String = "UNKNOWN",
        var permissionAfter1s: String = "UNKNOWN",
        var permissionAfter5s: String = "UNKNOWN",
        var fileExistsAfter1s: Boolean? = null,
        var fileExistsAfter5s: Boolean? = null,
        var fileLengthAfter1s: Long? = null,
        var fileLengthAfter5s: Long? = null,
        var branch: String = "",
        var commandName: String = "",
        var bundleUriMatches: Boolean = false,
        var commandResult: Boolean? = null,
        var commandException: String? = null,
        var clipboardSetException: String? = null,
        var pasteResult: Boolean? = null,
        var pasteException: String? = null,
        var providerOpen: String = "未发生",
        var revokeOrDelete: String = ""
    ) {
        fun summary(): String = buildString {
            appendLine("事件 ID：$eventId")
            appendLine("时间：$timestamp")
            appendLine("目标包：${targetPackage ?: "无"}")
            appendLine("IC：$inputConnection")
            appendLine("资源：$resourceType / $source")
            appendLine("临时文件：${if (fileExists) "正常" else "异常"}，大小 $fileLength B")
            appendLine("IME 自读 URI：${if (selfOpen == "SUCCESS") "成功" else "失败"}${if (selfOpen != "SUCCESS") "（SELF_URI_READ_FAILED）" else ""}")
            appendLine("目标 UID：${targetUid ?: "无法获取"}（$uidResult）")
            if (uidExceptionType != null) {
                appendLine("UID 异常：$uidExceptionType：$uidExceptionMessage")
                appendLine("UID 堆栈：$uidExceptionStack")
            }
            appendLine("grant 调用：$grantCall${grantException?.let { "（$it）" } ?: ""}")
            appendLine("授权前：$permissionBefore")
            appendLine("授权后：$permissionAfter")
            appendLine("1 秒后授权：$permissionAfter1s")
            appendLine("5 秒后授权：$permissionAfter5s")
            appendLine("命令返回：${commandResult ?: "未执行"}")
            appendLine("Provider openFile：$providerOpen")
            appendLine("撤销或删除：${revokeOrDelete.ifBlank { "未发生" }}")
            appendLine("分支：$branch，命令：$commandName")
            appendLine("URI：$uri")
            appendLine("Bundle URI 严格相同：$bundleUriMatches")
            if (clipboardSetException != null) appendLine("setPrimaryClip 异常：$clipboardSetException")
            if (pasteResult != null) appendLine("paste 返回：$pasteResult")
            if (pasteException != null) appendLine("paste 异常：$pasteException")
        }
    }

    fun newRecord(editorInfoPackage: String?, targetPackage: String?, item: Any, source: String, file: File): Record {
        val id = "${System.currentTimeMillis()}-${sequence.incrementAndGet()}"
        val resourceType = item::class.java.simpleName
        return Record(id, System.currentTimeMillis(), targetPackage, editorInfoPackage = editorInfoPackage,
            resourceType = resourceType, source = source, filePath = file.absolutePath,
            fileExists = file.exists(), fileLength = file.length(),
            fileExtension = file.extension, detectedMime = "")
    }

    fun add(record: Record) = update(record)
    fun update(record: Record) {
        synchronized(lock) {
            if (!records.contains(record)) {
                records.addFirst(record)
                while (records.size > 10) records.removeLast()
            }
        }
        notifyChanged()
    }

    fun associate(uri: Uri, eventId: String) {
        synchronized(lock) { uriEvents[uri.toString()] = eventId }
    }

    fun recordForUri(uri: Uri): Record? = synchronized(lock) {
        val id = uriEvents[uri.toString()] ?: uri.lastPathSegment?.substringBefore("_")
        records.firstOrNull { it.eventId == id }
    }

    fun recordForEvent(eventId: String): Record? = synchronized(lock) {
        records.firstOrNull { it.eventId == eventId }
    }

    fun recordForFile(filePath: String): Record? = synchronized(lock) {
        records.firstOrNull { it.filePath == filePath }
    }

    fun recordCleanup(
        eventId: String?,
        uri: Uri?,
        file: File,
        reason: String,
        beforeExists: Boolean,
        beforeLength: Long,
        result: String
    ) {
        val record = eventId?.let { recordForEvent(it) } ?: recordForFile(file.absolutePath)
        val message = buildString {
            append("eventId=${record?.eventId ?: eventId ?: "UNKNOWN"}")
            append(", uri=${uri ?: record?.uri ?: "UNKNOWN"}")
            append(", filePath=${file.absolutePath}")
            append(", time=${System.currentTimeMillis()}")
            append(", reason=$reason")
            append(", deleteBefore=$beforeExists/$beforeLength")
            append(", deleteAfter=${file.exists()}/${file.length()}")
            append(", result=$result")
        }
        if (record != null) {
            record.revokeOrDelete = if (record.revokeOrDelete.isBlank()) {
                message
            } else {
                "${record.revokeOrDelete}\n$message"
            }
            update(record)
        }
    }

    fun recent(): List<Record> = synchronized(lock) { records.toList() }
    fun latest(): Record? = synchronized(lock) { records.firstOrNull() }
    fun addListener(listener: () -> Unit) { synchronized(lock) { listeners.add(listener) } }
    fun removeListener(listener: () -> Unit) { synchronized(lock) { listeners.remove(listener) } }
    private fun notifyChanged() { synchronized(lock) { listeners.toList() }.forEach { it() } }
}
