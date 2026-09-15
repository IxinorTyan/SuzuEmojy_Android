package com.suzu.test.resource.similar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.io.File
import java.nio.ByteBuffer
import com.bumptech.glide.Glide
import com.bumptech.glide.gifdecoder.GifDecoder
import com.bumptech.glide.gifdecoder.GifHeaderParser
import com.bumptech.glide.gifdecoder.StandardGifDecoder
import com.bumptech.glide.load.resource.gif.GifBitmapProvider

data class HashedResource(val resource: ResourceEntity, val hash: Long,
    val frameHashes: List<Long> = emptyList(), val durationMs: Long = 0)
data class SimilarityScan(val items: List<HashedResource>, val failed: Int)

/** Disposable, versioned local cache; intentionally outside the resource/sync database. */
private class FingerprintCache(context: Context) : SQLiteOpenHelper(
    context, File(context.cacheDir, "similarity-v1.db").absolutePath, null, 2
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE fingerprints (resource_id INTEGER PRIMARY KEY, signature TEXT NOT NULL, hash INTEGER NOT NULL, frames TEXT NOT NULL DEFAULT '', duration INTEGER NOT NULL DEFAULT 0)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE fingerprints ADD COLUMN frames TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE fingerprints ADD COLUMN duration INTEGER NOT NULL DEFAULT 0")
        }
    }
}

object SimilarityScanner {
    suspend fun scan(context: Context, resources: List<ResourceEntity>, progress: (Int) -> Unit): SimilarityScan {
        val items = mutableListOf<HashedResource>()
        var failed = 0
        FingerprintCache(context).use { cache ->
            val db = cache.writableDatabase
            resources.forEachIndexed { index, resource ->
                currentCoroutineContext().ensureActive()
                val file = File(context.filesDir, "resources/${resource.filename}")
                try {
                    check(file.isFile) { "Missing resource" }
                    val signature = "${resource.syncKey}:${resource.fileMd5}:${file.length()}:${file.lastModified()}" +
                        if (resource.isAnimated) ":animation-v1" else ""
                    val cached = db.query("fingerprints", arrayOf("hash", "frames", "duration"), "resource_id=? AND signature=?",
                        arrayOf(resource.id.toString(), signature), null, null, null).use {
                        if (it.moveToFirst()) {
                            val frames = it.getString(1).split(',').filter(String::isNotEmpty).mapNotNull(String::toLongOrNull)
                            if (resource.isAnimated && frames.size != AnimationSimilarity.SAMPLE_COUNT) null
                            else HashedResource(resource, it.getLong(0), frames, it.getLong(2))
                        } else null
                    }
                    val fingerprint = cached ?: (if (resource.isAnimated) decodeAnimation(context, file, resource, progress, index)
                        else HashedResource(resource, decode(file))).also { value ->
                        db.insertWithOnConflict("fingerprints", null, ContentValues().apply {
                            put("resource_id", resource.id)
                            put("signature", signature)
                            put("hash", value.hash)
                            put("frames", value.frameHashes.joinToString(","))
                            put("duration", value.durationMs)
                        }, SQLiteDatabase.CONFLICT_REPLACE)
                    }
                    items.add(fingerprint)
                } catch (e: java.io.IOException) {
                    failed++
                } catch (e: IllegalStateException) {
                    failed++
                } catch (e: IllegalArgumentException) {
                    failed++
                }
                progress(index + 1)
            }
        }
        return SimilarityScan(items, failed)
    }

    private suspend fun decodeAnimation(context: Context, file: File, resource: ResourceEntity,
        progress: (Int) -> Unit, index: Int): HashedResource {
        val bytes = file.readBytes()
        val parser = GifHeaderParser()
        val header = try { parser.setData(bytes).parseHeader() } finally { parser.clear() }
        check(header.status == GifDecoder.STATUS_OK && header.numFrames > 0) { "Invalid GIF" }
        var sampleSize = 1
        while (maxOf(header.width, header.height) / sampleSize > 512) sampleSize *= 2
        val glide = Glide.get(context)
        val provider = GifBitmapProvider(glide.bitmapPool, glide.arrayPool)
        val decoder = StandardGifDecoder(provider, header, ByteBuffer.wrap(bytes), sampleSize)
        try {
            val delays = List(header.numFrames) { decoder.getDelay(it).coerceAtLeast(20) }
            val targets = AnimationSimilarity.sampleFrames(delays)
            val hashes = mutableListOf<Long>()
            for (frame in 0 until header.numFrames) {
                currentCoroutineContext().ensureActive()
                decoder.advance()
                // Decode intermediate frames too: GIF disposal/compositing depends on them.
                val bitmap = decoder.nextFrame ?: error("Cannot decode GIF frame")
                try {
                    check(decoder.status == GifDecoder.STATUS_OK) { "Incomplete GIF frame" }
                    if (frame in targets) {
                        val hash = hashBitmap(bitmap)
                        repeat(targets.count { it == frame }) { hashes.add(hash) }
                    }
                } finally { provider.release(bitmap) }
                progress(index)
            }
            return HashedResource(resource, hashes.first(), hashes, delays.sumOf { it.toLong() })
        } finally { decoder.clear() }
    }

    private fun decode(file: File): Long {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(file.absolutePath, bounds)
        check(bounds.outWidth > 0 && bounds.outHeight > 0) { "Invalid image" }
        var sample = 1
        // Bound decoded memory even for exceptionally long or tall images.
        while (maxOf(bounds.outWidth, bounds.outHeight) / sample > 512) sample *= 2
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, BitmapFactory.Options().apply {
            inSampleSize = sample
            inScaled = false
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }) ?: error("Cannot decode image")
        try { return hashBitmap(bitmap) } finally { bitmap.recycle() }
    }

    private fun hashBitmap(bitmap: Bitmap): Long {
            val small = Bitmap.createScaledBitmap(bitmap, PerceptualHash.SIZE, PerceptualHash.SIZE, true)
            try {
                val pixels = IntArray(PerceptualHash.SIZE * PerceptualHash.SIZE)
                small.getPixels(pixels, 0, PerceptualHash.SIZE, 0, 0, PerceptualHash.SIZE, PerceptualHash.SIZE)
                val luminance = DoubleArray(pixels.size) { index ->
                    val pixel = pixels[index]
                    val alpha = (pixel ushr 24) / 255.0
                    val gray = 0.299 * ((pixel ushr 16) and 255) +
                        0.587 * ((pixel ushr 8) and 255) + 0.114 * (pixel and 255)
                    // All transparent images are compared over the same white background.
                    gray * alpha + 255.0 * (1.0 - alpha)
                }
                return PerceptualHash.compute(luminance)
            } finally {
                if (small !== bitmap) small.recycle()
            }
    }
}
