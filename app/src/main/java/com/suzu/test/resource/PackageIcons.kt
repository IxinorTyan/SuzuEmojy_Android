package com.suzu.test.resource

import android.content.Context
import android.graphics.BitmapFactory
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.SuzuDatabase
import org.json.JSONObject
import java.io.File
import java.security.MessageDigest
import java.util.zip.ZipFile

/** Icons are attachments, never resource-library entries or device-local IDs. */
object PackageIcons {
    fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256")
        .digest(bytes).joinToString("") { "%02x".format(it) }

    suspend fun sourceFile(context: Context, database: SuzuDatabase, value: String?): File? =
        when (val icon = CategoryIconResolver.resolve(value)) {
            is CategoryIconResult.Resource -> database.resourceDao().getById(icon.resourceId)
                ?.let { File(context.filesDir, "resources/${it.filename}") }
            is CategoryIconResult.ImageFile -> File(context.filesDir, icon.relativePath)
            else -> null
        }

    suspend fun isValid(context: Context, database: SuzuDatabase, value: String?): Boolean =
        when (CategoryIconResolver.resolve(value)) {
            is CategoryIconResult.Text -> true
            is CategoryIconResult.Default -> false
            else -> sourceFile(context, database, value)?.let { file ->
                runCatching { validateImage(file.readBytes()); true }.getOrDefault(false)
            } ?: false
        }

    suspend fun pack(context: Context, database: SuzuDatabase, value: String?, assets: MutableMap<String, ByteArray>): JSONObject? {
        when (val icon = CategoryIconResolver.resolve(value)) {
            is CategoryIconResult.Default -> return null
            is CategoryIconResult.Text -> return JSONObject().put("type", "text").put("text", icon.content)
            else -> Unit
        }
        val file = sourceFile(context, database, value) ?: error("图标源文件不存在")
        val bytes = file.readBytes()
        validateImage(bytes)
        val suffix = file.extension.lowercase()
        require(suffix in setOf("png", "gif", "jpg", "jpeg", "webp", "bmp")) { "不支持的图标格式" }
        val hash = sha256(bytes)
        val path = "icons/$hash.$suffix"
        assets[path] = bytes
        return JSONObject().put("type", "image").put("asset_path", path)
            .put("asset_size", bytes.size).put("asset_sha256", hash)
    }

    /** Validate before publishing; the caller removes newly created files on failure. */
    fun restore(context: Context, zip: ZipFile, icon: JSONObject?, created: MutableList<File>): String? {
        if (icon == null) return null
        return when (icon.getString("type")) {
            "default" -> null
            "text" -> "text:" + icon.getString("text").also { require(it.isNotBlank()) }
            "image" -> {
                val path = icon.getString("asset_path")
                require(path.startsWith("icons/") && !path.contains("\\") && path.split('/').none { it == ".." }) { "图标路径非法" }
                val suffix = path.substringAfterLast('.').lowercase()
                require(suffix in setOf("png", "gif", "jpg", "jpeg", "webp", "bmp")) { "不支持的图标格式" }
                val entry = zip.getEntry(path) ?: error("图标文件缺失")
                val bytes = zip.getInputStream(entry).use { it.readBytes() }
                require(bytes.size.toLong() == icon.getLong("asset_size") && sha256(bytes) == icon.getString("asset_sha256")) { "图标校验失败" }
                validateImage(bytes)
                val relativePath = "category_icons/${sha256(bytes)}.$suffix"
                val target = File(context.filesDir, relativePath)
                target.parentFile?.mkdirs()
                if (!target.exists()) {
                    created.add(target)
                    try {
                        target.writeBytes(bytes)
                    } catch (e: Exception) {
                        target.delete()
                        throw e
                    }
                }
                "file:$relativePath"
            }
            else -> error("不支持的图标类型")
        }
    }

    private fun validateImage(bytes: ByteArray) {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        require(options.outWidth > 0 && options.outHeight > 0) { "图标图片无法解码" }
    }
}
