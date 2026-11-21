package com.suzu.test.ime.theme

import android.graphics.Bitmap
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

internal object WallpaperBlur {
    private const val MAX_WORK_EDGE = 640

    suspend fun apply(source: Bitmap, amount: Int): Bitmap {
        val scale = (MAX_WORK_EDGE.toFloat() / maxOf(source.width, source.height)).coerceAtMost(1f)
        val width = (source.width * scale).roundToInt().coerceAtLeast(1)
        val height = (source.height * scale).roundToInt().coerceAtLeast(1)
        val sample = Bitmap.createScaledBitmap(source, width, height, true)
        val pixels = IntArray(width * height)
        sample.getPixels(pixels, 0, width, 0, 0, width, height)
        if (sample !== source) sample.recycle()

        val radius = (amount * minOf(width, height) / 500f).roundToInt().coerceAtLeast(1)
        blurPixels(pixels, width, height, radius)
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
    }

    internal suspend fun blurPixels(pixels: IntArray, width: Int, height: Int, radius: Int) {
        val scratch = IntArray(pixels.size)
        // Two separable box passes give soft edges without an API-31-only render effect.
        repeat(2) {
            blurRows(pixels, scratch, width, height, radius)
            blurRows(scratch, pixels, height, width, radius)
        }
    }

    // Transpose the result so the same pass can blur the other axis. Weight color by alpha
    // to avoid dark fringes on transparent images; clamp samples at the image boundary.
    private suspend fun blurRows(
        input: IntArray, output: IntArray, width: Int, height: Int, radius: Int
    ) {
        val window = 2 * radius + 1
        for (y in 0 until height) {
            coroutineContext.ensureActive()
            val row = y * width
            var alpha = 0
            var red = 0
            var green = 0
            var blue = 0
            fun accumulate(x: Int, sign: Int) {
                val color = input[row + x.coerceIn(0, width - 1)]
                val a = (color ushr 24) * sign
                alpha += a
                red += ((color ushr 16) and 255) * a
                green += ((color ushr 8) and 255) * a
                blue += (color and 255) * a
            }
            for (x in -radius..radius) accumulate(x, 1)
            for (x in 0 until width) {
                output[x * height + y] = if (alpha == 0) 0 else {
                    ((alpha / window) shl 24) or ((red / alpha) shl 16) or
                        ((green / alpha) shl 8) or (blue / alpha)
                }
                accumulate(x - radius, -1)
                accumulate(x + radius + 1, 1)
            }
        }
    }
}
