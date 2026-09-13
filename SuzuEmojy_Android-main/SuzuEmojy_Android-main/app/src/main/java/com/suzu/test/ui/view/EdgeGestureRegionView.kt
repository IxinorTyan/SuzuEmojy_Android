package com.suzu.test.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 设置页中的四个边缘手势判定区域示意图。
 *
 * 上半区位于模拟 IME 上方，下半区覆盖模拟 IME 两侧，两者分别应用安全距离。
 * 仅负责绘制，不创建悬浮窗，也不参与实际触摸判定。
 */
class EdgeGestureRegionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private data class RegionState(
        val widthDp: Int = 24,
        val triggerDistanceDp: Int = 80,
        val enabled: Boolean = true
    )

    private val regionPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1f)
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = dp(9f)
        textAlign = Paint.Align.CENTER
    }
    private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val arrowPath = Path()

    private var leftUpper = RegionState()
    private var leftLower = RegionState()
    private var rightUpper = RegionState()
    private var rightLower = RegionState()
    private var upperSafetyDistancePx = 0
    private var lowerSafetyDistancePx = 0
    private var leftUpEnabled = false
    private var leftDownEnabled = false
    private var leftRightEnabled = false
    private var rightUpEnabled = false
    private var rightDownEnabled = false
    private var rightLeftEnabled = false
    private var gestureEnabled = false

    init {
        isClickable = false
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun update(
        leftUpperWidthDp: Int,
        leftUpperDistanceDp: Int,
        leftUpperEnabled: Boolean,
        leftLowerWidthDp: Int,
        leftLowerDistanceDp: Int,
        leftLowerEnabled: Boolean,
        rightUpperWidthDp: Int,
        rightUpperDistanceDp: Int,
        rightUpperEnabled: Boolean,
        rightLowerWidthDp: Int,
        rightLowerDistanceDp: Int,
        rightLowerEnabled: Boolean,
        upperSafetyDistancePx: Int,
        lowerSafetyDistancePx: Int,
        gestureEnabled: Boolean,
        leftUpEnabled: Boolean,
        leftDownEnabled: Boolean,
        leftRightEnabled: Boolean,
        rightUpEnabled: Boolean,
        rightDownEnabled: Boolean,
        rightLeftEnabled: Boolean
    ) {
        leftUpper = RegionState(leftUpperWidthDp, leftUpperDistanceDp, leftUpperEnabled)
        leftLower = RegionState(leftLowerWidthDp, leftLowerDistanceDp, leftLowerEnabled)
        rightUpper = RegionState(rightUpperWidthDp, rightUpperDistanceDp, rightUpperEnabled)
        rightLower = RegionState(rightLowerWidthDp, rightLowerDistanceDp, rightLowerEnabled)
        this.upperSafetyDistancePx = upperSafetyDistancePx
        this.lowerSafetyDistancePx = lowerSafetyDistancePx
        this.gestureEnabled = gestureEnabled
        this.leftUpEnabled = leftUpEnabled
        this.leftDownEnabled = leftDownEnabled
        this.leftRightEnabled = leftRightEnabled
        this.rightUpEnabled = rightUpEnabled
        this.rightDownEnabled = rightDownEnabled
        this.rightLeftEnabled = rightLeftEnabled
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width <= 0 || height <= 0) return

        val imeTop = height * 0.66f
        val upperSafetyPreview =
            (upperSafetyDistancePx / resources.displayMetrics.density * 0.22f)
                .coerceIn(0f, imeTop * 0.35f)
        val lowerSafetyPreview =
            (lowerSafetyDistancePx / resources.displayMetrics.density * 0.22f)
                .coerceIn(0f, (height - imeTop) * 0.35f)
        val upperBottom = (imeTop - upperSafetyPreview).coerceAtLeast(dp(12f))
        val lowerTop = (imeTop + lowerSafetyPreview)
            .coerceAtMost(height - dp(12f))

        drawImeArea(canvas, imeTop)
        drawRegion(canvas, leftUpper, true, true, 0f, upperBottom)
        drawRegion(canvas, leftLower, true, false, lowerTop, height.toFloat())
        drawRegion(canvas, rightUpper, false, true, 0f, upperBottom)
        drawRegion(canvas, rightLower, false, false, lowerTop, height.toFloat())

        linePaint.color = Color.argb(150, 80, 80, 80)
        linePaint.strokeWidth = dp(1f)
        linePaint.pathEffect = DashPathEffect(floatArrayOf(dp(4f), dp(3f)), 0f)
        canvas.drawLine(0f, imeTop, width.toFloat(), imeTop, linePaint)
        linePaint.pathEffect = null

        textPaint.color = Color.argb(210, 70, 70, 70)
        textPaint.textSize = dp(9f)
        canvas.drawText("IME", width / 2f, imeTop + dp(13f), textPaint)

        if (upperSafetyPreview > 0f) {
            regionPaint.color = Color.argb(25, 255, 152, 0)
            canvas.drawRect(0f, upperBottom, width.toFloat(), imeTop, regionPaint)
            textPaint.color = Color.argb(190, 160, 90, 0)
            textPaint.textSize = dp(8f)
            canvas.drawText(
                "上半安全区域 ${upperSafetyDistancePx}px",
                width / 2f,
                imeTop - dp(4f),
                textPaint
            )
        }

        if (lowerSafetyPreview > 0f) {
            regionPaint.color = Color.argb(25, 255, 152, 0)
            canvas.drawRect(0f, imeTop, width.toFloat(), lowerTop, regionPaint)
            textPaint.color = Color.argb(190, 160, 90, 0)
            textPaint.textSize = dp(8f)
            canvas.drawText(
                "下半安全区域 ${lowerSafetyDistancePx}px",
                width / 2f,
                lowerTop - dp(4f),
                textPaint
            )
        }
    }

    private fun drawImeArea(canvas: Canvas, imeTop: Float) {
        regionPaint.color = Color.argb(22, 90, 90, 90)
        canvas.drawRect(0f, imeTop, width.toFloat(), height.toFloat(), regionPaint)
    }

    private fun drawRegion(
        canvas: Canvas,
        state: RegionState,
        isLeft: Boolean,
        isUpper: Boolean,
        top: Float,
        bottom: Float
    ) {
        if (!state.enabled || bottom <= top) return

        val screenWidthDp = resources.displayMetrics.widthPixels /
            resources.displayMetrics.density
        val regionWidth = (width * state.widthDp / screenWidthDp)
            .coerceIn(dp(6f), width / 3f)
        val left = if (isLeft) 0f else width - regionWidth
        val right = if (isLeft) regionWidth else width.toFloat()
        val alpha = if (gestureEnabled) 66 else 28

        regionPaint.color = when {
            isUpper && isLeft -> Color.argb(alpha, 33, 150, 243)
            isUpper -> Color.argb(alpha, 156, 39, 176)
            isLeft -> Color.argb(alpha, 0, 150, 136)
            else -> Color.argb(alpha, 255, 112, 67)
        }
        canvas.drawRect(left, top, right, bottom, regionPaint)

        linePaint.color = Color.argb(if (gestureEnabled) 165 else 80, 55, 75, 95)
        linePaint.strokeWidth = dp(1f)
        val innerEdge = if (isLeft) right else left
        canvas.drawLine(innerEdge, top, innerEdge, bottom, linePaint)

        val centerX = (left + right) / 2f
        val availableHeight = bottom - top
        val inwardEnabled = if (isLeft) leftRightEnabled else rightLeftEnabled
        val upEnabled = if (isLeft) leftUpEnabled else rightUpEnabled
        val downEnabled = if (isLeft) leftDownEnabled else rightDownEnabled

        if (availableHeight >= dp(45f)) {
            drawVerticalArrow(canvas, centerX, top + availableHeight * 0.27f, upEnabled, true)
            drawHorizontalArrow(
                canvas,
                centerX,
                top + availableHeight * 0.50f,
                inwardEnabled,
                isLeft,
                regionWidth
            )
            drawVerticalArrow(canvas, centerX, top + availableHeight * 0.73f, downEnabled, false)
        }

        textPaint.color = Color.argb(if (gestureEnabled) 215 else 120, 45, 45, 45)
        textPaint.textSize = dp(8f)
        val labelX = if (isLeft) {
            (right + dp(24f)).coerceAtMost(width / 2f - dp(18f))
        } else {
            (left - dp(24f)).coerceAtLeast(width / 2f + dp(18f))
        }
        val labelY = if (isUpper) {
            (bottom - dp(5f)).coerceAtLeast(top + dp(9f))
        } else {
            (top + dp(24f)).coerceAtMost(bottom - dp(5f))
        }
        canvas.drawText("${state.widthDp}/${state.triggerDistanceDp}dp", labelX, labelY, textPaint)

        drawTriggerDistance(canvas, state.triggerDistanceDp, isLeft, top, bottom)
    }

    private fun drawTriggerDistance(
        canvas: Canvas,
        triggerDistanceDp: Int,
        isLeft: Boolean,
        top: Float,
        bottom: Float
    ) {
        val regionHeight = bottom - top
        val length = min(regionHeight * 0.38f, dp(triggerDistanceDp * 0.45f))
        if (length < dp(3f)) return

        val x = if (isLeft) width * 0.43f else width * 0.57f
        val centerY = (top + bottom) / 2f
        linePaint.color = Color.argb(if (gestureEnabled) 130 else 65, 80, 80, 80)
        linePaint.strokeWidth = dp(1f)
        linePaint.pathEffect = DashPathEffect(floatArrayOf(dp(3f), dp(3f)), 0f)
        canvas.drawLine(x, centerY - length / 2f, x, centerY + length / 2f, linePaint)
        linePaint.pathEffect = null
    }

    private fun drawVerticalArrow(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        enabled: Boolean,
        up: Boolean
    ) {
        arrowPaint.color =
            if (enabled) Color.argb(220, 30, 30, 30) else Color.argb(75, 70, 70, 70)
        arrowPaint.strokeWidth = dp(if (enabled) 2f else 1f)
        val direction = if (up) -1f else 1f
        val halfLength = dp(6f)
        val startY = centerY - direction * halfLength
        val endY = centerY + direction * halfLength

        arrowPath.reset()
        arrowPath.moveTo(centerX, startY)
        arrowPath.lineTo(centerX, endY)
        arrowPath.moveTo(centerX, endY)
        arrowPath.lineTo(centerX - dp(3f), endY - direction * dp(4f))
        arrowPath.moveTo(centerX, endY)
        arrowPath.lineTo(centerX + dp(3f), endY - direction * dp(4f))
        canvas.drawPath(arrowPath, arrowPaint)
    }

    private fun drawHorizontalArrow(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        enabled: Boolean,
        pointsRight: Boolean,
        regionWidth: Float
    ) {
        arrowPaint.color =
            if (enabled) Color.argb(220, 30, 30, 30) else Color.argb(75, 70, 70, 70)
        arrowPaint.strokeWidth = dp(if (enabled) 2f else 1f)
        val direction = if (pointsRight) 1f else -1f
        val halfLength = min(dp(7f), (regionWidth / 2f - dp(2f)).coerceAtLeast(dp(3f)))
        val startX = centerX - direction * halfLength
        val endX = centerX + direction * halfLength

        arrowPath.reset()
        arrowPath.moveTo(startX, centerY)
        arrowPath.lineTo(endX, centerY)
        arrowPath.moveTo(endX, centerY)
        arrowPath.lineTo(endX - direction * dp(4f), centerY - dp(3f))
        arrowPath.moveTo(endX, centerY)
        arrowPath.lineTo(endX - direction * dp(4f), centerY + dp(3f))
        canvas.drawPath(arrowPath, arrowPaint)
    }

    private fun dp(value: Float): Float =
        value * resources.displayMetrics.density
}
