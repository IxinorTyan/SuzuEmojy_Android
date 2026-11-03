package com.suzu.test.ui.import

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.PowerManager
import com.suzu.test.log.TestLog
import com.suzu.test.resource.clean.CleanResult
import com.suzu.test.resource.clean.ResourceCleaner
import com.suzu.test.resource.hash.ResourceHasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.ByteArrayOutputStream

/** Short, per-import image benchmark; never inserts or writes a benchmark image to disk. */
internal class ImportPerformanceProbe(context: Context) {
    private val activityManager = context.getSystemService(ActivityManager::class.java)
    private val powerManager = context.getSystemService(PowerManager::class.java)
    private val runtime = Runtime.getRuntime()
    private val preparationBudget = ImportPreparationBudget((runtime.maxMemory() / 3).coerceAtLeast(1))
    private var checkedAt = Long.MIN_VALUE
    private var cachedLimit = 1
    var selectedParallelism: Int = 1
        private set

    fun currentLimit(): Int {
        val now = System.nanoTime()
        if (checkedAt == Long.MIN_VALUE || now - checkedAt >= 500_000_000L) {
            cachedLimit = try {
                val memory = ActivityManager.MemoryInfo()
                activityManager?.getMemoryInfo(memory)
                val thermal = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                    (powerManager?.currentThermalStatus ?: PowerManager.THERMAL_STATUS_NONE) >= PowerManager.THERMAL_STATUS_MODERATE
                ImportParallelismPolicy.limit(
                    processors = runtime.availableProcessors(),
                    heapLimitBytes = runtime.maxMemory(),
                    heapHeadroomBytes = runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory()),
                    lowRam = activityManager?.isLowRamDevice != false,
                    lowMemory = memory.lowMemory,
                    thermallyConstrained = thermal
                )
            } catch (e: RuntimeException) {
                // A missing/unavailable vendor service must not make import fail.
                1
            }
            checkedAt = now
        }
        return minOf(selectedParallelism, cachedLimit)
    }

    suspend fun measure(imageCount: Int): Int = withContext(Dispatchers.IO) {
        currentLimit()
        val maxParallelism = cachedLimit
        if (imageCount < 32 || maxParallelism == 1) return@withContext 1
        val timings = mutableListOf<ImportProbeTiming>()
        val startedAt = System.nanoTime()
        // A soft wall-time budget: cancellation is checked between native operations.
        // One decode/encode already in progress must finish before its bitmap is recycled.
        try {
            withTimeoutOrNull(500) {
                val source = createSample()
                runTrial(source, parallelism = 1, operations = 1) // JIT/native warm-up; not scored.
                for (parallelism in listOf(1, 2, 4).filter { it <= maxParallelism }) {
                    currentCoroutineContext().ensureActive()
                    if (System.nanoTime() - startedAt >= 350_000_000L) break
                    val startedTrial = System.nanoTime()
                    runTrial(source, parallelism, operations = 8)
                    timings.add(ImportProbeTiming(parallelism, System.nanoTime() - startedTrial))
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            TestLog.w("ImportTask", "图片处理测评跳过，使用单路导入: ${e.message}")
            return@withContext 1
        }
        selectedParallelism = ImportParallelismPolicy.choose(timings, maxParallelism)
        checkedAt = Long.MIN_VALUE
        selectedParallelism = currentLimit()
        TestLog.i("ImportTask", "图片处理测评 ${(System.nanoTime() - startedAt) / 1_000_000}ms: " +
            "${timings.joinToString { "${it.parallelism}路=${it.durationNanos / 1_000_000}ms" }}, 并发=$selectedParallelism")
        selectedParallelism
    }

    suspend fun <T> withImageMemory(bytes: ByteArray, prepare: suspend () -> T): T {
        val estimate = if (ResourceHasher.isGif(bytes)) {
            bytes.size.toLong() * 2
        } else {
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
            // Bitmap, pixel hashing buffer, PNG encoding buffers and source copies.
            val pixels = bounds.outWidth.coerceAtLeast(0).toLong() * bounds.outHeight.coerceAtLeast(0)
            pixels.coerceAtMost(runtime.maxMemory() / 24) * 24 + bytes.size.toLong() * 3
        }
        return preparationBudget.withReservation(estimate, prepare)
    }

    private suspend fun runTrial(source: ByteArray, parallelism: Int, operations: Int) = coroutineScope {
        List(minOf(parallelism, operations)) { worker ->
            async(Dispatchers.Default) {
                for (index in worker until operations step parallelism) {
                    currentCoroutineContext().ensureActive()
                    val clean = ResourceCleaner.sanitizeStatic(source) as CleanResult.StaticImage
                    ResourceHasher.fileMd5(clean.pngBytes)
                }
            }
        }.awaitAll()
        Unit
    }

    private fun createSample(): ByteArray {
        val size = 192
        val pixels = IntArray(size * size) { index ->
            val x = index % size
            val y = index / size
            // Deterministic mixture of flat blocks, gradients and fine detail.
            val detail = (x * 73 xor y * 151) and 31
            0xff000000.toInt() or (((x / 24 * 32 + detail) and 255) shl 16) or
                (((y + detail) and 255) shl 8) or ((x + y) and 255)
        }
        val bitmap = Bitmap.createBitmap(pixels, size, size, Bitmap.Config.ARGB_8888)
        return try {
            val output = ByteArrayOutputStream()
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)) { "测评图片编码失败" }
            output.toByteArray()
        } finally {
            bitmap.recycle()
        }
    }
}
