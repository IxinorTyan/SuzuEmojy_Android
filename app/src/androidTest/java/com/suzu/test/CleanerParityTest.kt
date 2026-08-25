package com.suzu.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.resource.clean.CleanResult
import com.suzu.test.resource.clean.ResourceCleaner
import com.suzu.test.resource.hash.ResourceHasher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CleanerParityTest {

    private data class Baseline(
        val filename: String,
        val width: Int,
        val height: Int,
        val expectedMd5: String
    )

    companion object {
        private val BASELINES = listOf(
            Baseline("20260702121038188211.png", 285, 286, "92b93896345dc57b4b461983afe3ac48"),
            Baseline("20260702121038215660.png", 268, 269, "d5499ed1398cc8cc2f346db3cce16da6"),
            Baseline("20260702121038248736.png", 267, 267, "426cac601783a517be01ee830c5e940c")
        )
    }

    @Test
    fun testSanitizeStaticParityAndLosslessReEncoding() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val assetManager = context.assets

        for (baseline in BASELINES) {
            val assetPath = "hashtest/${baseline.filename}"
            val rawBytes = assetManager.open(assetPath).use { it.readBytes() }

            val cleanResult = ResourceCleaner.sanitizeStatic(rawBytes)
            assertTrue("返回结果必须为 StaticImage", cleanResult is CleanResult.StaticImage)
            val staticImg = cleanResult as CleanResult.StaticImage

            assertEquals("宽度断言失败: ${baseline.filename}", baseline.width, staticImg.width)
            assertEquals("高度断言失败: ${baseline.filename}", baseline.height, staticImg.height)
            assertEquals(
                "初次清洗后 MD5 必须与 PIL 基准一致: ${baseline.filename}",
                baseline.expectedMd5,
                staticImg.md5
            )

            val reDecodedBitmap = ResourceHasher.decodeRgba(staticImg.pngBytes)
            assertNotNull("重编码 PNG 回读解码不能为 null: ${baseline.filename}", reDecodedBitmap)
            reDecodedBitmap!!

            val recomputedMd5 = ResourceHasher.pixelMd5(reDecodedBitmap)
            assertEquals(
                "PNG 重编码后再次取像素 MD5 必须依然等于基准值(证明无损): ${baseline.filename}",
                baseline.expectedMd5,
                recomputedMd5
            )
        }
    }
}
