package com.suzu.test.ime.sender

import java.io.File

/** Each experimental send owns its file/URI; cleanup never touches a source or conversion cache. */
internal object SendSnapshot {
    fun create(source: File, directory: File): File {
        require(source.isFile && source.length() > 0) { "发送源文件不存在或为空" }
        check(directory.isDirectory || directory.mkdirs()) { "无法创建发送副本目录" }
        val extension = source.extension.takeIf { it.matches(Regex("[A-Za-z0-9]{1,8}")) } ?: "bin"
        val snapshot = File.createTempFile("overlay_", ".$extension", directory)
        try {
            source.inputStream().use { input -> snapshot.outputStream().use { input.copyTo(it) } }
            check(snapshot.length() == source.length()) { "发送副本不完整" }
            return snapshot
        } catch (e: Exception) {
            snapshot.delete()
            throw e
        }
    }
}
