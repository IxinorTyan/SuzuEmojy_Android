package com.suzu.test.resource.hash

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.suzu.test.log.TestLog
import java.nio.ByteBuffer
import java.security.MessageDigest

object ResourceHasher {

    private const val MODULE = "ResourceHasher"

    fun decodeRgba(bytes: ByteArray): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inPremultiplied = false
            inPreferredConfig = Bitmap.Config.ARGB_8888
            inScaled = false
        }
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
        if (bitmap == null) {
            TestLog.w(MODULE, "decodeRgba: 解码失败, bytes.size=${bytes.size}")
        } else {
            TestLog.i(MODULE, "decodeRgba: 成功解码尺寸 ${bitmap.width}x${bitmap.height}")
        }
        return bitmap
    }

    fun pixelMd5(bitmap: Bitmap): String {
        val width = bitmap.width
        val height = bitmap.height
        val byteCount = width * height * 4
        val byteBuffer = ByteBuffer.allocate(byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        val pixelBytes = byteBuffer.array()
        val md5 = fileMd5(pixelBytes)
        TestLog.i(MODULE, "pixelMd5: 计算像素 MD5: $md5 (尺寸 ${width}x${height}, 字节数 $byteCount)")
        return md5
    }

    fun fileMd5(bytes: ByteArray): String {
        val md5Digest = MessageDigest.getInstance("MD5")
        val hashBytes = md5Digest.digest(bytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun isGif(bytes: ByteArray): Boolean {
        if (bytes.size < 6) return false
        val header = String(bytes, 0, 6, Charsets.US_ASCII)
        val isGif = header == "GIF87a" || header == "GIF89a"
        TestLog.i(MODULE, "isGif: 魔数检查结果=$isGif (header=$header)")
        return isGif
    }
}
