package com.suzu.test

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.ime.sender.PngToGifConverter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.io.File

@RunWith(AndroidJUnit4::class)
class PngToGifConverterTest {
    @Test fun pngRoundTripAndTiming() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        for (size in listOf(1, 32, 256, 512, 1000)) {
            val pixels = IntArray(size * size) { i ->
                if (i % 7 == 0) 0 else 0xff000000.toInt() or
                    ((i % size * 255 / size) shl 16) or ((i / size * 255 / size) shl 8)
            }
            val bitmap = Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
            val png = ByteArrayOutputStream().also { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }.toByteArray()
            bitmap.recycle()
            val output = File.createTempFile("gif_roundtrip_", ".gif", context.cacheDir)
            try {
                val started = System.nanoTime()
                assertTrue(PngToGifConverter.convertPngToGif({ png.inputStream() }, output))
                val elapsedMs = (System.nanoTime() - started) / 1_000_000
                assertTrue(PngToGifConverter.isValidGif(output))
                val decoded = BitmapFactory.decodeFile(output.path)!!
                assertEquals(size, decoded.width)
                assertEquals(size, decoded.height)
                assertEquals(0, decoded.getPixel(0, 0) ushr 24)
                if (size > 1) assertEquals(255, decoded.getPixel(1, 0) ushr 24)
                decoded.recycle()
                Log.i("GifRoundTripTest", "size=$size elapsedMs=$elapsedMs bytes=${output.length()}")
                // A recognizable magic prefix is not a complete GIF.
                output.writeText("GIF89a;")
                assertFalse(PngToGifConverter.isValidGif(output))
            } finally { output.delete() }
        }
    }
}
