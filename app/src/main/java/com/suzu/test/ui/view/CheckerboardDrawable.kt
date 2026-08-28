package com.suzu.test.ui.view

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable

/**
 * 棋盘格背景 Drawable，用于无边框透明贴图预览
 */
class CheckerboardDrawable(
    private val cellSizePx: Int = 24,
    color1: Int = 0xFFFFFFFF.toInt(),
    color2: Int = 0xFFE0E0E0.toInt()
) : Drawable() {

    private val paint1 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = color1 }
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = color2 }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val width = bounds.width()
        val height = bounds.height()
        if (width <= 0 || height <= 0) return

        val cols = (width + cellSizePx - 1) / cellSizePx
        val rows = (height + cellSizePx - 1) / cellSizePx

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val paint = if ((r + c) % 2 == 0) paint1 else paint2
                val left = bounds.left + c * cellSizePx
                val top = bounds.top + r * cellSizePx
                val right = (left + cellSizePx).coerceAtMost(bounds.right)
                val bottom = (top + cellSizePx).coerceAtMost(bounds.bottom)
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        paint1.alpha = alpha
        paint2.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint1.colorFilter = colorFilter
        paint2.colorFilter = colorFilter
        invalidateSelf()
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.OPAQUE
}
