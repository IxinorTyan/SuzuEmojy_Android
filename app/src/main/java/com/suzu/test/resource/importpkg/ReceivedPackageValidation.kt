package com.suzu.test.resource.importpkg

import android.graphics.BitmapFactory
import com.suzu.test.resource.lansync.LanSyncClient
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import org.json.JSONObject
import java.io.IOException
import java.io.DataInputStream
import java.security.MessageDigest
import java.util.Locale
import java.util.zip.ZipFile
import java.util.zip.ZipEntry

/** Stricter bounds and per-asset integrity checks for packages arriving over the network. */
internal object ReceivedPackageValidation {
    private const val MAX_METADATA = 16L * 1024 * 1024
    private const val MAX_ASSET = 48L * 1024 * 1024
    private const val MAX_PIXELS = 16_000_000L
    private const val MAX_ENTRIES = 40_000
    private val safeAsset = Regex("assets/[a-zA-Z0-9_-]+\\.(png|gif)")
    private val sha256 = Regex("[a-f0-9A-F]{64}")
    private val md5 = Regex("[a-f0-9]{32}")

    fun readMetadata(zip: ZipFile, entry: ZipEntry): ByteArray = readExactEntry(zip, entry, MAX_METADATA)

    fun readAsset(zip: ZipFile, entry: ZipEntry): ByteArray = readExactEntry(zip, entry, MAX_ASSET)

    private fun readExactEntry(zip: ZipFile, entry: ZipEntry, limit: Long): ByteArray {
        if (entry.size !in 1..limit || entry.isDirectory) throw IOException("资源包条目大小无效：${entry.name}")
        return DataInputStream(zip.getInputStream(entry)).use { input ->
            ByteArray(entry.size.toInt()).also {
                input.readFully(it)
                if (input.read() != -1) throw IOException("资源包条目大小不符：${entry.name}")
            }
        }
    }

    fun checkEntries(zip: ZipFile, availableForStreaming: Long? = null) {
        if (zip.size() !in 2..MAX_ENTRIES) throw IOException("资源包的条目数量超出限制。")
        val names = HashSet<String>()
        var total = 0L
        for (entry in zip.entries()) {
            val path = entry.name.removeSuffix("/")
            if (path.length > 256 || path.contains('\\') || path.contains(':') ||
                path.split('/').any { it.isEmpty() || it == "." || it == ".." } ||
                !names.add(path.lowercase(Locale.ROOT))) throw IOException("资源包路径无效或重复。")
            val limit = if (path == "manifest.json" || path == "catalog.json") MAX_METADATA
                else if (availableForStreaming != null && safeAsset.matches(path)) Long.MAX_VALUE else MAX_ASSET
            if (entry.size !in 0..limit) throw IOException("资源包条目大小超出限制：$path")
            if (entry.size > (availableForStreaming ?: LanSyncClient.MAX_PACKAGE) - total)
                throw IOException(if (availableForStreaming == null) "资源包展开后超过 2 GiB。" else "手机暂存空间不足。")
            total += entry.size
        }
    }

    suspend fun checkAssets(zip: ZipFile, catalog: JSONObject, warnings: MutableList<String>) {
        val resources = catalog.getJSONArray("resources")
        val categories = catalog.getJSONArray("categories")
        if (resources.length() + categories.length() > MAX_ENTRIES) throw IOException("资源包目录超出限制。")
        val seen = HashSet<String>()
        val seenKeys = HashSet<String>()
        val checkedIcons = HashMap<String, Boolean>()
        val buffer = ByteArray(65_536)
        val job = currentCoroutineContext()
        for (i in 0 until resources.length()) {
            job.ensureActive()
            val metadata = resources.getJSONObject(i)
            val path = metadata.getString("asset_path")
            val length = metadata.getLong("asset_size")
            val digest = metadata.getString("asset_sha256")
            val fileMd5 = metadata.getString("file_md5")
            val pixelMd5 = metadata.optString("pixel_md5")
            val key = if (path.endsWith(".png")) "p:$pixelMd5" else "f:$fileMd5"
            val entry = zip.getEntry(path)
            if (!safeAsset.matches(path) || !seen.add(path) || !sha256.matches(digest) || entry == null || entry.isDirectory ||
                entry.size != length || length !in 1..MAX_ASSET || !md5.matches(fileMd5) ||
                (path.endsWith(".png") && !md5.matches(pixelMd5)) || key != metadata.getString("sync_key") || !seenKeys.add(key)) {
                throw IOException("资源包清单或图片大小无效：$path")
            }
            val hash = MessageDigest.getInstance("SHA-256")
            val legacyHash = MessageDigest.getInstance("MD5")
            zip.getInputStream(entry).use { input ->
                var remaining = length
                while (remaining > 0) {
                    job.ensureActive()
                    val count = input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
                    if (count <= 0) throw IOException("资源包图片不完整：$path")
                    hash.update(buffer, 0, count)
                    legacyHash.update(buffer, 0, count)
                    remaining -= count
                }
                if (input.read() != -1) throw IOException("资源包图片大小不符：$path")
            }
            if (!MessageDigest.isEqual(decodeHex(digest), hash.digest()) ||
                !MessageDigest.isEqual(decodeHex(fileMd5), legacyHash.digest())) throw IOException("资源包图片哈希校验失败：$path")
            checkDimensions(zip, path)
        }
        // A broken optional icon still uses the existing fallback, but do not decode an oversized image.
        for (i in 0 until categories.length()) {
            job.ensureActive()
            val category = categories.getJSONObject(i)
            val icon = category.optJSONObject("icon") ?: continue
            if (icon.optString("type") != "image") continue
            val path = icon.optString("asset_path")
            val valid = checkedIcons.getOrPut(path) {
                try {
                    val entry = zip.getEntry(path)
                    if (!path.startsWith("icons/") || entry == null || entry.isDirectory || entry.size !in 1..MAX_ASSET) false
                    else {
                        zip.getInputStream(entry).use { input ->
                            var remaining = entry.size
                            while (remaining > 0) {
                                job.ensureActive()
                                val count = input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
                                if (count <= 0) throw IOException("图标大小不符")
                                remaining -= count
                            }
                            if (input.read() != -1) throw IOException("图标大小不符")
                        }
                        checkDimensions(zip, path)
                        true
                    }
                } catch (_: IOException) { false }
            }
            if (!valid) {
                warnings.add("${category.optString("name")}：图标无效或尺寸超出限制")
                category.remove("icon")
            }
        }
    }

    private fun decodeHex(value: String) = ByteArray(value.length / 2) { value.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

    internal data class StreamedSpec(val path: String, val key: String, val size: Long, val sha: String, val fileMd5: String)

    internal fun streamSpec(zip: ZipFile, row: JSONObject): StreamedSpec {
        val path = row.getString("asset_path")
        val size = row.getLong("asset_size")
        val digest = row.getString("asset_sha256")
        val fileMd5 = row.getString("file_md5")
        val pixelMd5 = row.optString("pixel_md5")
        val key = if (path.endsWith(".png")) "p:$pixelMd5" else "f:$fileMd5"
        val entry = zip.getEntry(path)
        if (!safeAsset.matches(path) || !sha256.matches(digest) || entry == null || entry.isDirectory ||
            entry.size != size || size <= 0 || !md5.matches(fileMd5) ||
            (path.endsWith(".png") && !md5.matches(pixelMd5)) || key != row.getString("sync_key"))
            throw IOException("资源包清单或图片大小无效：$path")
        return StreamedSpec(path, key, size, digest, fileMd5)
    }

    /** Stream a v1 original to disk; false denotes content failure, not a disk write failure. */
    internal suspend fun stageAsset(zip: ZipFile, spec: StreamedSpec, output: java.io.OutputStream): Boolean {
        val job = currentCoroutineContext()
        val sha = MessageDigest.getInstance("SHA-256")
        val legacy = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(65_536)
        zip.getInputStream(checkNotNull(zip.getEntry(spec.path))).use { input ->
            var remaining = spec.size
            while (remaining > 0) {
                job.ensureActive()
                val count = try { input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt()) }
                    catch (_: java.util.zip.ZipException) { return false }
                if (count <= 0) return false
                output.write(buffer, 0, count)
                sha.update(buffer, 0, count)
                legacy.update(buffer, 0, count)
                remaining -= count
            }
            if (input.read() != -1) return false
        }
        return MessageDigest.isEqual(decodeHex(spec.sha), sha.digest()) &&
            MessageDigest.isEqual(decodeHex(spec.fileMd5), legacy.digest())
    }

    private fun checkDimensions(zip: ZipFile, path: String) {
        val entry = zip.getEntry(path) ?: throw IOException("资源包图片不存在：$path")
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        zip.getInputStream(entry).use { BitmapFactory.decodeStream(it, null, bounds) }
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0 || bounds.outWidth.toLong() * bounds.outHeight > MAX_PIXELS) {
            throw IOException("资源包图片无效或尺寸超出限制：$path")
        }
    }
}
