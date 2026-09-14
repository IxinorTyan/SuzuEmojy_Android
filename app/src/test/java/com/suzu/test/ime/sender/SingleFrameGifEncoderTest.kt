package com.suzu.test.ime.sender

import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayOutputStream


class SingleFrameGifEncoderTest {
    // Android's Kotlin compile classpath excludes java.desktop; use the host JDK
    // ImageIO via reflection so the production code needs no desktop dependency.
    private class Decoded(val image: Any) {
        private val type = Class.forName("java.awt.image.BufferedImage")
        val width get() = type.getMethod("getWidth").invoke(image) as Int
        val height get() = type.getMethod("getHeight").invoke(image) as Int
        fun color(x: Int, y: Int) = type.getMethod("getRGB", Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType).invoke(image, x, y) as Int
    }

    private fun decode(bytes: ByteArray): Decoded {
        val io = Class.forName("javax.imageio.ImageIO")
        val readerType = Class.forName("javax.imageio.ImageReader")
        val input = io.getMethod("createImageInputStream", Any::class.java)
            .invoke(null, bytes.inputStream())
        val readers = io.getMethod("getImageReadersByFormatName", String::class.java)
            .invoke(null, "gif") as Iterator<*>
        val reader = readers.next()
        try {
            readerType.getMethod("setInput", Any::class.java).invoke(reader, input)
            assertEquals(1, readerType.getMethod("getNumImages", Boolean::class.javaPrimitiveType)
                .invoke(reader, true))
            return Decoded(readerType.getMethod("read", Int::class.javaPrimitiveType).invoke(reader, 0))
        } finally {
            readerType.getMethod("dispose").invoke(reader)
            (input as java.io.Closeable).close()
        }
    }

    private fun encode(width: Int, height: Int, pixels: IntArray, transparent: Boolean = false): ByteArray {
        val output = ByteArrayOutputStream()
        val encoder = PngToGifConverter.SingleFrameGifEncoder()
        if (transparent) encoder.setTransparent(0xff00ff)
        assertTrue(encoder.start(output, width, height))
        assertTrue(encoder.addFrame(pixels))
        assertTrue(encoder.finish())
        return output.toByteArray()
    }

    @Test fun smallImagesDecodeAsExactlyOneFrameWithCorrectColor() {
        for (size in listOf(1, 2, 10, 32, 128)) {
            val bytes = encode(size, size, IntArray(size * size) { 0xffff0000.toInt() })
            assertEquals("GIF89a", String(bytes, 0, 6, Charsets.US_ASCII))
            val decoded = decode(bytes)
            assertEquals(size, decoded.width)
            assertEquals(size, decoded.height)
            val color = decoded.color(0, 0)
            assertTrue((color ushr 16 and 255) > 230)
            assertTrue((color ushr 8 and 255) < 25)
            assertTrue((color and 255) < 25)
        }
    }

    @Test fun transparencyDoesNotEraseOpaquePixelsOfTheSameColor() {
        val pixels = IntArray(64 * 64) { if (it % 2 == 0) 0x00ff00ff else 0xffff00ff.toInt() }
        val decoded = decode(encode(64, 64, pixels, true))
        for (x in 0 until 64) {
            assertEquals(if (x % 2 == 0) 0 else 255, decoded.color(x, 0) ushr 24)
        }
    }

    @Test fun largeNoisyImageDecodesAcrossLzwDictionaryResets() {
        val random = java.util.Random(42)
        val bytes = encode(512, 512, IntArray(512 * 512) { random.nextInt() or 0xff000000.toInt() })
        val decoded = decode(bytes)
        assertEquals(512, decoded.width)
        assertEquals(512, decoded.height)
    }
}
