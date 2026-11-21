package com.suzu.test.ime.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.suzu.test.ime.config.KeyboardConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runInterruptible
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object KeyboardWallpaper {
    const val MAX_BLUR = 40
    const val MAX_BRIGHTNESS = 100
    private const val MAX_IMAGE_EDGE = 1280
    private const val KEY_BLUR = "keyboard_wallpaper_blur"
    private const val KEY_BRIGHTNESS = "keyboard_wallpaper_brightness"
    private const val IMAGE_NAME = "ime-wallpaper"

    data class Tone(val blur: Int, val brightness: Int)
    data class Frame(val bitmap: Bitmap, val brightness: Int)

    private val imageChanges = MutableStateFlow(0L)
    private val toneChanges = MutableStateFlow(0L)

    private fun prefs(context: Context) =
        context.getSharedPreferences(KeyboardConfig.SP_NAME, Context.MODE_PRIVATE)

    fun tone(context: Context): Tone = prefs(context).let {
        Tone(
            it.getInt(KEY_BLUR, 0).coerceIn(0, MAX_BLUR),
            it.getInt(KEY_BRIGHTNESS, 0).coerceIn(-MAX_BRIGHTNESS, MAX_BRIGHTNESS)
        )
    }

    fun setBlur(context: Context, value: Int) = setTone(context, KEY_BLUR, value.coerceIn(0, MAX_BLUR))

    fun setBrightness(context: Context, value: Int) =
        setTone(context, KEY_BRIGHTNESS, value.coerceIn(-MAX_BRIGHTNESS, MAX_BRIGHTNESS))

    private fun setTone(context: Context, key: String, value: Int) {
        prefs(context).edit().putInt(key, value).apply()
        toneChanges.update { it + 1 }
    }

    private fun file(context: Context) = File(context.filesDir, IMAGE_NAME)

    suspend fun replace(context: Context, uri: Uri) {
        val requests = Glide.with(context.applicationContext)
        // Glide samples large photos and applies EXIF orientation even on Android 8.
        val request = requests.asBitmap().load(uri)
            .downsample(DownsampleStrategy.AT_MOST)
            .disallowHardwareConfig()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .submit(MAX_IMAGE_EDGE, MAX_IMAGE_EDGE)
        try {
            val bitmap = runInterruptible(Dispatchers.IO) { request.get() }
            withContext(Dispatchers.IO) {
                val target = file(context)
                val temporary = File.createTempFile(IMAGE_NAME, ".tmp", target.parentFile)
                try {
                    temporary.outputStream().use { output ->
                        val format = if (bitmap.hasAlpha()) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
                        if (!bitmap.compress(format, 92, output)) throw IOException("Wallpaper encoding failed")
                    }
                    Files.move(temporary.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
                    imageChanges.update { it + 1 }
                } finally {
                    temporary.delete()
                }
            }
        } finally {
            requests.clear(request)
        }
    }

    suspend fun remove(context: Context) = withContext(Dispatchers.IO) {
        Files.deleteIfExists(file(context).toPath())
        imageChanges.update { it + 1 }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun frames(context: Context): Flow<Frame?> {
        val app = context.applicationContext
        val source = imageChanges.mapLatest {
            withContext(Dispatchers.IO) {
                try {
                    file(app).inputStream().use { BitmapFactory.decodeStream(it) }
                } catch (_: IOException) {
                    null
                }
            }
        }
        val tone = toneChanges.map { tone(app) }.distinctUntilChanged()
        val blur = tone.map { it.blur }.distinctUntilChanged()
        val brightness = tone.map { it.brightness }.distinctUntilChanged()
        val blurred = combine(source, blur, ::Pair).mapLatest { (bitmap, amount) ->
            if (bitmap == null || amount == 0) bitmap
            else withContext(Dispatchers.Default) { WallpaperBlur.apply(bitmap, amount) }
        }
        return combine(blurred, brightness) { bitmap, amount -> bitmap?.let { Frame(it, amount) } }
            .distinctUntilChanged()
    }
}
