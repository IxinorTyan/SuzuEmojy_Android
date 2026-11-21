package com.suzu.test.ime.theme

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.graphics.ColorUtils
import kotlin.math.abs

internal class WallpaperDrawable(
    private val frame: KeyboardWallpaper.Frame,
    baseColor: Int
) : Drawable() {
    private val bitmapPaint = Paint(Paint.FILTER_BITMAP_FLAG)
    private val basePaint = Paint().apply { color = baseColor }
    private val veil = Paint().apply {
        color = ColorUtils.setAlphaComponent(
            if (frame.brightness < 0) Color.BLACK else Color.WHITE,
            abs(frame.brightness) * 255 / KeyboardWallpaper.MAX_BRIGHTNESS
        )
    }
    private val destination = RectF()
    private val viewport = RectF()
    private var layerAlpha = 255

    override fun onBoundsChange(bounds: Rect) {
        viewport.set(bounds)
        val bitmap = frame.bitmap
        val scale = maxOf(bounds.width().toFloat() / bitmap.width, bounds.height().toFloat() / bitmap.height)
        val halfWidth = bitmap.width * scale / 2
        val halfHeight = bitmap.height * scale / 2
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()
        destination.set(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight)
    }

    override fun draw(canvas: Canvas) {
        val save = if (layerAlpha == 255) canvas.save() else canvas.saveLayerAlpha(viewport, layerAlpha)
        canvas.clipRect(bounds)
        canvas.drawRect(viewport, basePaint)
        canvas.drawBitmap(frame.bitmap, null, destination, bitmapPaint)
        if (veil.alpha != 0) canvas.drawRect(viewport, veil)
        canvas.restoreToCount(save)
    }

    override fun setAlpha(alpha: Int) {
        layerAlpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        bitmapPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    @Deprecated("Deprecated by Android")
    override fun getOpacity(): Int = if (layerAlpha == 255) PixelFormat.OPAQUE else PixelFormat.TRANSLUCENT
}
