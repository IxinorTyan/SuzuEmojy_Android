package com.suzu.test.resource.clean

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.suzu.test.log.TestLog
import com.suzu.test.resource.hash.ResourceHasher
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import kotlin.math.round

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
    internal const val MAX_SIDE = 720

    /** Owns the decoded pixels until hashing and optional PNG encoding have finished. */
    class DecodedStaticImage internal constructor(
        private val bitmap: Bitmap, val md5: String, private val png: ByteArray? = null
    ) : AutoCloseable {
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        internal val wasScaled: Boolean get() = png != null

        fun encode(): CleanResult.StaticImage {
            return CleanResult.StaticImage(png ?: encodePng(bitmap), width, height, md5)
        }

        internal fun matches(file: File): Boolean {
            val other = ResourceHasher.decodeRgba(file) ?: return false
            try {
                if (width != other.width || height != other.height) return false
                val left = ByteBuffer.allocate(width * height * 4)
                val right = ByteBuffer.allocate(left.capacity())
                bitmap.copyPixelsToBuffer(left)
                other.copyPixelsToBuffer(right)
                return left.array().contentEquals(right.array())
            } finally { other.recycle() }
        }

        override fun close() = bitmap.recycle()
    }

    fun decodeStatic(bytes: ByteArray, normalize: Boolean = false): DecodedStaticImage? {
        val png = if (normalize) scaledPng(bytes) else null
        val bitmap = ResourceHasher.decodeRgba(png ?: bytes) ?: return null
        return try {
            DecodedStaticImage(bitmap, ResourceHasher.pixelMd5(bitmap), png)
        } catch (e: Throwable) {
            bitmap.recycle()
            throw e
        }
    }

    private fun scaledPng(bytes: ByteArray): ByteArray? {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
        val side = maxOf(bounds.outWidth, bounds.outHeight)
        if (side <= MAX_SIDE) return null
        val scale = MAX_SIDE.toDouble() / side
        val width = round(bounds.outWidth * scale).toInt().coerceAtLeast(1)
        val height = round(bounds.outHeight * scale).toInt().coerceAtLeast(1)
        // Filter premultiplied pixels, then decode the final PNG for its straight-RGBA key.
        val options = BitmapFactory.Options().apply {
            inSampleSize = Integer.highestOneBit(side / (2 * MAX_SIDE)).coerceAtLeast(1)
        }
        val source = requireNotNull(BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)) { "Unable to resize image" }
        try {
            val scaled = Bitmap.createScaledBitmap(source, width, height, true)
            return try { encodePng(scaled) } finally { if (scaled !== source) scaled.recycle() }
        } finally { source.recycle() }
    }

    private fun encodePng(bitmap: Bitmap): ByteArray {
        val output = ByteArrayOutputStream()
        check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) { "PNG encoding failed" }
        return output.toByteArray()
    }

    internal fun isSingleFrameGif(bytes: ByteArray): Boolean {
        if (bytes.size < 13 || !ResourceHasher.isGif(bytes)) return false
        var pos = 13
        var frames = 0
        fun skip(size: Int): Boolean {
            if (size > bytes.size - pos) return false
            pos += size
            return true
        }
        fun table(flags: Int) = (flags and 128) == 0 || skip(3 * (2 shl (flags and 7)))
        fun blocks(): Boolean {
            while (pos < bytes.size) {
                val size = bytes[pos++].toInt() and 255
                if (size == 0) return true
                if (!skip(size)) return false
            }
            return false
        }
        if (!table(bytes[10].toInt())) return false
        while (pos < bytes.size) {
            when (bytes[pos++].toInt()) {
                0x3b -> return frames == 1
                0x21 -> if (!skip(1) || !blocks()) return false
                0x2c -> {
                    if (++frames > 1 || !skip(9) || !table(bytes[pos - 1].toInt()) || !skip(1) || !blocks()) return false
                }
                else -> return false
            }
        }
        return false
    }

    internal fun isAnimatedPng(bytes: ByteArray): Boolean {
        if (bytes.size < 8 || !bytes.copyOf(8).contentEquals(byteArrayOf(-119, 80, 78, 71, 13, 10, 26, 10))) return false
        val input = ByteBuffer.wrap(bytes)
        input.position(8)
        while (input.remaining() >= 12) {
            val size = input.int
            val type = input.int
            if (size < 0 || size > input.remaining() - 4 || type == 0x49444154) return false // IDAT
            if (type == 0x6163544c && size == 8) return true // acTL
            input.position(input.position() + size + 4)
        }
        return false
    }

    fun sanitizeStatic(bytes: ByteArray): CleanResult {
        if (isAnimatedWebP(bytes)) {
            TestLog.w(MODULE, "sanitizeStatic: 拒绝处理动态 WebP (v1 不支持动态转码)")
            return CleanResult.UnsupportedFormat("Animated WebP is not supported in v1")
        }

        val decoded = decodeStatic(bytes)
            ?: return CleanResult.UnsupportedFormat("Failed to decode image bytes into RGBA Bitmap")
        return decoded.use { it.encode() }
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
