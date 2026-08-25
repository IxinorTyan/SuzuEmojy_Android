package com.suzu.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.resource.hash.ResourceHasher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HashParityTest {

    private data class Baseline(
        val filename: String,
        val width: Int,
        val height: Int,
        val byteCount: Int,
        val expectedMd5: String
    )

    companion object {
        private val BASELINES = listOf(
            Baseline(
                filename = "20260702121038188211.png",
                width = 285,
                height = 286,
                byteCount = 326040,
                expectedMd5 = "92b93896345dc57b4b461983afe3ac48"
            ),
            Baseline(
                filename = "20260702121038215660.png",
                width = 268,
                height = 269,
                byteCount = 288368,
                expectedMd5 = "d5499ed1398cc8cc2f346db3cce16da6"
            ),
            Baseline(
                filename = "20260702121038248736.png",
                width = 267,
                height = 267,
                byteCount = 285156,
                expectedMd5 = "426cac601783a517be01ee830c5e940c"
            )
        )
    }

    @Test
    fun testPixelHashParity() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val assetManager = context.assets

        for (baseline in BASELINES) {
            val assetPath = "hashtest/${baseline.filename}"
            val bytes = assetManager.open(assetPath).use { it.readBytes() }

            val bitmap = ResourceHasher.decodeRgba(bytes)
            assertNotNull("Bitmap 解码不能为 null: ${baseline.filename}", bitmap)
            bitmap!!

            assertEquals("宽度断言失败: ${baseline.filename}", baseline.width, bitmap.width)
            assertEquals("高度断言失败: ${baseline.filename}", baseline.height, bitmap.height)
            assertEquals(
                "像素字节大小断言失败: ${baseline.filename}",
                baseline.byteCount,
                bitmap.width * bitmap.height * 4
            )

            val calculatedMd5 = ResourceHasher.pixelMd5(bitmap)
            assertEquals(
                "PIL 像素 MD5 基准硬断言失败: ${baseline.filename}",
                baseline.expectedMd5,
                calculatedMd5
            )
        }
    }
}
