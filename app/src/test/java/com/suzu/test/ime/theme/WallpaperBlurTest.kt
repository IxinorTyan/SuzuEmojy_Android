package com.suzu.test.ime.theme

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WallpaperBlurTest {
    @Test fun flatColorsStayFlatAtClampedEdges() = runBlocking {
        val flat = IntArray(15) { 0xFF1976D2.toInt() }
        val pixels = flat.copyOf()
        WallpaperBlur.blurPixels(pixels, 5, 3, 4)
        assertArrayEquals(flat, pixels)
    }

    @Test fun blursBothAxesOfANonSquareImage() = runBlocking {
        val pixels = IntArray(35) { 0xFF000000.toInt() }
        val center = 2 * 7 + 3
        pixels[center] = -1
        WallpaperBlur.blurPixels(pixels, 7, 5, 1)
        val intensity = pixels[center] and 255
        assertTrue(intensity in 1..254)
        assertTrue((pixels[center - 1] and 255) in 1 until intensity)
        assertEquals(pixels[center - 1], pixels[center + 1])
        assertEquals(pixels[center - 1], pixels[center - 7])
        assertEquals(pixels[center - 7], pixels[center + 7])
        assertEquals(0xFF000000.toInt(), pixels[0])
    }

    @Test fun transparentPixelsDoNotDarkenColorFringes() = runBlocking {
        val pixels = intArrayOf(0, 0xFFFF0000.toInt(), 0)
        WallpaperBlur.blurPixels(pixels, 3, 1, 1)
        for (pixel in pixels) {
            assertTrue((pixel ushr 24) in 1..254)
            assertEquals(0xFF0000, pixel and 0xFFFFFF)
        }
    }
}
