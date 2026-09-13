package com.suzu.test.resource.clean

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.suzu.test.log.TestLog
import com.suzu.test.resource.hash.ResourceHasher
import java.io.ByteArrayOutputStream

sealed class CleanResult {
    data class StaticImage(
        val pngBytes: ByteArray,
        val width: Int,
        val height: Int,
        val md5: String
    ) : CleanResult()

    data class GifImage(
        val gifBytes: ByteArray,
        val width: Int,
        val height: Int,
        val md5: String
    ) : CleanResult()

    data class UnsupportedFormat(val reason: String) : CleanResult()
}

object ResourceCleaner {

    private const val MODULE = "ResourceCleaner"

    fun sanitizeStatic(bytes: ByteArray): CleanResult {
        if (isAnimatedWebP(bytes)) {
            TestLog.w(MODULE, "sanitizeStatic: 拒绝处理动态 WebP (v1 不支持动态转码)")
            return CleanResult.UnsupportedFormat("Animated WebP is not supported in v1")
        }

        val bitmap = ResourceHasher.decodeRgba(bytes)
            ?: return CleanResult.UnsupportedFormat("Failed to decode image bytes into RGBA Bitmap")

        val pixelMd5 = ResourceHasher.pixelMd5(bitmap)
        val width = bitmap.width
        val height = bitmap.height

        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        val pngBytes = outStream.toByteArray()

        TestLog.i(MODULE, "sanitizeStatic: 成功清洗静态图 (尺寸 ${width}x${height}, MD5=$pixelMd5, PNG大小=${pngBytes.size})")

        return CleanResult.StaticImage(
            pngBytes = pngBytes,
            width = width,
            height = height,
            md5 = pixelMd5
        )
    }

    fun passthroughGif(bytes: ByteArray): CleanResult {
        if (!ResourceHasher.isGif(bytes)) {
            TestLog.w(MODULE, "passthroughGif: 输入非合法 GIF 魔数")
            return CleanResult.UnsupportedFormat("Invalid GIF header")
        }
        val bounds = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) {
            TestLog.w(MODULE, "passthroughGif: 无法读取 GIF 尺寸")
            return CleanResult.UnsupportedFormat("Failed to read GIF dimensions")
        }

        val fileMd5 = ResourceHasher.fileMd5(bytes)
        TestLog.i(
            MODULE,
            "passthroughGif: GIF 直通成功 (尺寸 ${bounds.outWidth}x${bounds.outHeight}, 大小=${bytes.size}, MD5=$fileMd5)"
        )
        return CleanResult.GifImage(
            gifBytes = bytes,
            width = bounds.outWidth,
            height = bounds.outHeight,
            md5 = fileMd5
        )
    }

    fun isAnimatedWebP(bytes: ByteArray): Boolean {
        if (bytes.size < 30) return false
        val riff = String(bytes, 0, 4, Charsets.US_ASCII)
        val webp = String(bytes, 8, 4, Charsets.US_ASCII)
        if (riff != "RIFF" || webp != "WEBP") return false

        val searchLimit = minOf(bytes.size - 4, 1024)
        for (i in 12 until searchLimit) {
            if (bytes[i] == 'A'.code.toByte() &&
                bytes[i + 1] == 'N'.code.toByte() &&
                bytes[i + 2] == 'I'.code.toByte() &&
                bytes[i + 3] == 'M'.code.toByte()
            ) {
                return true
            }
        }
        return false
    }
}
