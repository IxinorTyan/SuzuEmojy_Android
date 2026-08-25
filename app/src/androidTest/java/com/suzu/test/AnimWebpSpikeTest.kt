package com.suzu.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.WebpImage
import com.bumptech.glide.integration.webp.decoder.WebpDecoder
import com.bumptech.glide.load.resource.gif.GifBitmapProvider
import com.suzu.test.spike.AnimatedGifEncoder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

@RunWith(AndroidJUnit4::class)
class AnimWebpSpikeTest {

    companion object {
        private const val TAG = "AnimWebpSpikeTest"
        private const val OPAQUE_SAMPLE = "anim_opaque.webp"
        private const val TRANSPARENT_SAMPLE = "anim_transparent.webp"
    }

    private lateinit var context: Context
    private lateinit var outputDir: File

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        outputDir = File(context.cacheDir, "anim_spike_test_${System.currentTimeMillis()}").apply { mkdirs() }
    }

    @After
    fun tearDown() {
        if (outputDir.exists()) {
            outputDir.deleteRecursively()
        }
    }

    @Test
    fun testAnimatedWebpToGifConversion() {
        val samples = listOf(OPAQUE_SAMPLE, TRANSPARENT_SAMPLE)

        for (sampleName in samples) {
            val bytes = context.assets.open("animtest/$sampleName").use { it.readBytes() }
            val byteBuffer = ByteBuffer.wrap(bytes)

            // 使用 WebpImage + WebpDecoder 官方公开的原生解码 API 进行独立逐帧解析
            val glide = Glide.get(context)
            val bitmapPool = glide.bitmapPool
            val arrayPool = glide.arrayPool
            val bitmapProvider = GifBitmapProvider(bitmapPool, arrayPool)

            val webpImage = WebpImage.create(bytes)
            val webpDecoder = WebpDecoder(bitmapProvider, webpImage, byteBuffer, 1)

            val frameCount = webpDecoder.frameCount
            val width = webpImage.width
            val height = webpImage.height

            Log.i(TAG, "==================================================")
            Log.i(TAG, "[WebP Sample] $sampleName -> 帧数=$frameCount, 尺寸=${width}x${height}")

            // 4. 断言: 帧数 > 1 (证明确实提取了多帧动图)
            assertTrue("动态 WebP 帧数必须大于 1: $sampleName", frameCount > 1)

            val isTransparentTarget = (sampleName == TRANSPARENT_SAMPLE)
            var totalPixelCount = 0
            var rawSemiTransparentCount = 0
            var rawZeroAlphaCount = 0

            val outputFile = File(outputDir, "${sampleName}.gif")
            val encoder = AnimatedGifEncoder()
            val fos = FileOutputStream(outputFile)
            encoder.start(fos)
            encoder.setRepeat(0) // 循环播放

            if (isTransparentTarget) {
                encoder.setTransparent(0x00FF00FF) // 使用特殊粉紫色作为 1-bit 透明占位
            }

            // 逐帧公开 API 提取并编码
            webpDecoder.resetFrameIndex()
            for (i in 0 until frameCount) {
                webpDecoder.advance()
                val delay = webpDecoder.nextDelay
                val frameBitmap = webpDecoder.nextFrame
                assertNotNull("第 $i 帧 Bitmap 不能为 null: $sampleName", frameBitmap)
                frameBitmap!!

                Log.i(TAG, "  -> 帧[$i]: 尺寸=${frameBitmap.width}x${frameBitmap.height}, delay=${delay}ms")

                if (i == 0 && isTransparentTarget) {
                    val pixels = IntArray(frameBitmap.width * frameBitmap.height)
                    frameBitmap.getPixels(pixels, 0, frameBitmap.width, 0, 0, frameBitmap.width, frameBitmap.height)
                    totalPixelCount = pixels.size
                    for (pixel in pixels) {
                        val a = (pixel ushr 24) and 0xFF
                        if (a == 0) rawZeroAlphaCount++
                        else if (a < 255) rawSemiTransparentCount++
                    }
                }

                encoder.setDelay(delay)
                encoder.addFrame(frameBitmap)
            }

            encoder.finish()
            fos.close()

            // 5. 断言: 产出 GIF 文件前 6 字节必须是 GIF87a 或 GIF89a 魔数
            assertTrue("GIF 输出文件必须存在", outputFile.exists())
            val gifBytes = outputFile.readBytes()
            assertTrue("GIF 大小必须大于 0", gifBytes.isNotEmpty())
            val header = String(gifBytes, 0, 6, Charsets.US_ASCII)
            assertTrue("GIF 头部魔数必须为 GIF87a 或 GIF89a: $header", header == "GIF87a" || header == "GIF89a")

            // 6. 打印产出 GIF 的字节大小与总帧数
            Log.i(TAG, "[GIF Output] $sampleName -> GIF文件大小=${gifBytes.size} bytes, 编码总帧数=$frameCount, 魔数=$header")

            // 7. 【透明判定与边缘抗锯齿统计】
            if (isTransparentTarget) {
                val decOptions = BitmapFactory.Options().apply {
                    inPremultiplied = false
                    inPreferredConfig = Bitmap.Config.ARGB_8888
                    inScaled = false
                }
                val gifFirstFrame = BitmapFactory.decodeFile(outputFile.absolutePath, decOptions)
                assertNotNull("GIF 首帧解码不能为 null", gifFirstFrame)
                gifFirstFrame!!

                val gifPixels = IntArray(gifFirstFrame.width * gifFirstFrame.height)
                gifFirstFrame.getPixels(gifPixels, 0, gifFirstFrame.width, 0, 0, gifFirstFrame.width, gifFirstFrame.height)

                var gifZeroAlphaCount = 0
                for (p in gifPixels) {
                    val a = (p ushr 24) and 0xFF
                    if (a == 0) gifZeroAlphaCount++
                }

                val rawZeroAlphaRatio = (rawZeroAlphaCount.toDouble() / totalPixelCount) * 100
                val rawSemiRatio = (rawSemiTransparentCount.toDouble() / totalPixelCount) * 100
                val gifZeroAlphaRatio = (gifZeroAlphaCount.toDouble() / gifPixels.size) * 100
                val hasAlphaZeroInGif = gifZeroAlphaCount > 0

                Log.i(TAG, "[透明分析] 原图全透像素占比=${"%.2f".format(rawZeroAlphaRatio)}% | 原图半透明边缘占比=${"%.2f".format(rawSemiRatio)}% | 产出GIF透明像素占比=${"%.2f".format(gifZeroAlphaRatio)}%")
                Log.i(TAG, "[透明分析] 产出 GIF 是否保留了透明像素 (alpha==0): $hasAlphaZeroInGif")

                assertTrue("带透明的样本转成 GIF 后必须保留透明像素", hasAlphaZeroInGif)
            }
            Log.i(TAG, "==================================================")
        }
    }
}
