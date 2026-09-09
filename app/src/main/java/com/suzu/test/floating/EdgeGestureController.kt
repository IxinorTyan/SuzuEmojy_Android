package com.suzu.test.floating

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.suzu.test.log.TestLog
import kotlin.math.abs

/**
 * 屏幕左右边缘的四个手势区域。
 *
 * 上半区域位于 IME 上方，下半区域覆盖 IME 左右边缘，两者分别应用安全距离。
 * 四个区域分别控制启用状态、宽度和触发距离。
 */
class EdgeGestureController(
    private val context: Context,
    private val onGesture: (side: Side, direction: Direction) -> Unit
) {
    enum class Side { LEFT, RIGHT }
    enum class Direction { UP, DOWN, LEFT, RIGHT }

    private enum class Placement { UPPER, LOWER }

    private enum class Region(
        val side: Side,
        val placement: Placement
    ) {
        LEFT_UPPER(Side.LEFT, Placement.UPPER),
        LEFT_LOWER(Side.LEFT, Placement.LOWER),
        RIGHT_UPPER(Side.RIGHT, Placement.UPPER),
        RIGHT_LOWER(Side.RIGHT, Placement.LOWER)
    }

    internal data class RegionBounds(val y: Int, val height: Int)

    companion object {
        private const val MODULE = "EdgeGestureController"
        private const val EDGE_VIEW_TAG = "suzu_edge_gesture"
        private const val REGION_ALPHA = 76
        private const val VERTICAL_DIRECTION_RATIO = 1.2f

        internal fun resolveDirection(
            dx: Float,
            dy: Float,
            triggerDistance: Float
        ): Direction? {
            val vertical = abs(dx) * VERTICAL_DIRECTION_RATIO <= abs(dy)
            return if (vertical) {
                if (abs(dy) < triggerDistance) null
                else if (dy < 0f) Direction.UP else Direction.DOWN
            } else {
                if (abs(dx) < triggerDistance) null
                else if (dx < 0f) Direction.LEFT else Direction.RIGHT
            }
        }

        internal fun isDirectionSupported(side: Side, direction: Direction): Boolean =
            direction == Direction.UP ||
                direction == Direction.DOWN ||
                (side == Side.LEFT && direction == Direction.RIGHT) ||
                (side == Side.RIGHT && direction == Direction.LEFT)

        internal fun resolveRegionBounds(
            screenHeight: Int,
            imeTopPx: Int?,
            safetyDistancePx: Int,
            lower: Boolean,
            preview: Boolean = false
        ): RegionBounds {
            val safeScreenHeight = screenHeight.coerceAtLeast(1)
            val divider = imeTopPx
                ?.coerceIn(1, safeScreenHeight)
                ?: if (preview) {
                    (safeScreenHeight * 2 / 3).coerceAtLeast(1)
                } else {
                    safeScreenHeight
                }

            return if (lower) {
                val top = (divider + safetyDistancePx)
                    .coerceIn(0, safeScreenHeight - 1)
                RegionBounds(
                    y = top,
                    height = safeScreenHeight - top
                )
            } else {
                RegionBounds(
                    y = 0,
                    height = (divider - safetyDistancePx).coerceIn(1, safeScreenHeight)
                )
            }
        }
    }

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val views = mutableMapOf<Region, RegionView>()
    private val params = mutableMapOf<Region, WindowManager.LayoutParams>()
    private var preferenceListener: SharedPreferences.OnSharedPreferenceChangeListener? = null
    private var attached = false
    private var imeVisible = false
    private var imeTopPx: Int? = null

    fun attach() {
        if (attached || !Settings.canDrawOverlays(context)) return
        attached = true
        observeConfig()
        refreshViews()
    }

    fun detach() {
        if (!attached) return
        attached = false
        preferenceListener?.let {
            context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(it)
        }
        preferenceListener = null
        removeViews()
    }

    /**
     * 保留给悬浮球控制器的兼容回调。
     * 边缘手势不依赖前台应用信息。
     */
    fun onForegroundAppChanged(packageName: String?) = Unit

    fun onImeVisibilityChanged(visible: Boolean) {
        imeVisible = visible
        if (!visible) imeTopPx = null
        refreshViews()
    }

    fun onImeBoundsChanged(topPx: Int?) {
        imeTopPx = topPx?.takeIf { it > 0 }
        refreshViews()
    }

    private fun observeConfig() {
        val observedKeys = setOf(
            FloatingBallConfig.KEY_FLOATING_MASTER_ENABLED,
            FloatingBallConfig.KEY_EDGE_GESTURE_ENABLED,
            FloatingBallConfig.KEY_EDGE_LEFT_ENABLED,
            FloatingBallConfig.KEY_EDGE_RIGHT_ENABLED,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_ENABLED,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_ENABLED,
            FloatingBallConfig.KEY_SHOW_EDGE_REGION,
            FloatingBallConfig.KEY_EDGE_LEFT_UP_ENABLED,
            FloatingBallConfig.KEY_EDGE_LEFT_DOWN_ENABLED,
            FloatingBallConfig.KEY_EDGE_LEFT_RIGHT_ENABLED,
            FloatingBallConfig.KEY_EDGE_RIGHT_UP_ENABLED,
            FloatingBallConfig.KEY_EDGE_RIGHT_DOWN_ENABLED,
            FloatingBallConfig.KEY_EDGE_RIGHT_LEFT_ENABLED,
            FloatingBallConfig.KEY_EDGE_WIDTH_DP,
            FloatingBallConfig.KEY_EDGE_TRIGGER_DISTANCE_DP,
            FloatingBallConfig.KEY_EDGE_KEYBOARD_SAFETY_DISTANCE_PX,
            FloatingBallConfig.KEY_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_WIDTH_DP,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_DISTANCE_DP,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_WIDTH_DP,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_DISTANCE_DP,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_WIDTH_DP,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_DISTANCE_DP,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_WIDTH_DP,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_DISTANCE_DP
        )
        val sp = context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        preferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key in observedKeys) refreshViews()
        }
        sp.registerOnSharedPreferenceChangeListener(preferenceListener)
    }

    private fun isRegionEnabled(region: Region): Boolean = when (region) {
        Region.LEFT_UPPER -> FloatingBallConfig.isEdgeLeftEnabled(context)
        Region.LEFT_LOWER -> FloatingBallConfig.isEdgeLeftLowerEnabled(context)
        Region.RIGHT_UPPER -> FloatingBallConfig.isEdgeRightEnabled(context)
        Region.RIGHT_LOWER -> FloatingBallConfig.isEdgeRightLowerEnabled(context)
    }

    private fun regionWidthKey(region: Region): String = when (region) {
        Region.LEFT_UPPER -> FloatingBallConfig.KEY_EDGE_LEFT_UPPER_WIDTH_DP
        Region.LEFT_LOWER -> FloatingBallConfig.KEY_EDGE_LEFT_LOWER_WIDTH_DP
        Region.RIGHT_UPPER -> FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_WIDTH_DP
        Region.RIGHT_LOWER -> FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_WIDTH_DP
    }

    private fun regionDistanceKey(region: Region): String = when (region) {
        Region.LEFT_UPPER -> FloatingBallConfig.KEY_EDGE_LEFT_UPPER_DISTANCE_DP
        Region.LEFT_LOWER -> FloatingBallConfig.KEY_EDGE_LEFT_LOWER_DISTANCE_DP
        Region.RIGHT_UPPER -> FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_DISTANCE_DP
        Region.RIGHT_LOWER -> FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_DISTANCE_DP
    }

    private fun shouldShow(region: Region): Boolean {
        if (!FloatingBallConfig.isFloatingMasterEnabled(context)) return false
        if (!FloatingBallConfig.isEdgeGestureEnabled(context)) return false
        val preview = FloatingBallConfig.isShowEdgeRegionEnabled(context)
        return (preview || imeVisible) && isRegionEnabled(region)
    }

    /**
     * “显示判定区域”仅用于预览。只有 IME 可见且手势总开关开启时才接收触摸。
     */
    private fun shouldReceiveTouches(region: Region): Boolean =
        imeVisible &&
            FloatingBallConfig.isFloatingMasterEnabled(context) &&
            FloatingBallConfig.isEdgeGestureEnabled(context) &&
            isRegionEnabled(region)

    private fun refreshViews() {
        if (!attached) return
        val type = resolveWindowType()

        Region.entries.forEach { region ->
            if (shouldShow(region)) {
                if (views[region] == null) {
                    val layoutParams = createParams(type, region)
                    val view = RegionView(context, region)
                    try {
                        windowManager.addView(view, layoutParams)
                        views[region] = view
                        params[region] = layoutParams
                        TestLog.i(MODULE, "已挂载边缘手势区域: $region")
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "挂载边缘手势区域失败 ($region): ${e.message}", e)
                    }
                }
            } else {
                removeView(region)
            }
        }

        if (views.isNotEmpty()) updateViewState()
    }

    private fun resolveWindowType(): Int {
        if (context is AccessibilityService &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1
        ) {
            return WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
    }

    private fun createParams(
        type: Int,
        region: Region
    ): WindowManager.LayoutParams {
        val widthPx = getRegionWidthPx(region)
        val bounds = getRegionBounds(region)
        return WindowManager.LayoutParams(
            widthPx,
            bounds.height,
            type,
            baseFlags(),
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = getRegionX(region, widthPx)
            y = bounds.y
            title = "${EDGE_VIEW_TAG}_${region.name.lowercase()}"
        }
    }

    private fun baseFlags(): Int =
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL

    private fun getScreenWidth(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.width
        }

    private fun getScreenHeight(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.height()
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.height
        }

    private fun getRegionWidthPx(region: Region): Int =
        (
            FloatingBallConfig.getEdgeRegionWidthDp(context, regionWidthKey(region)) *
                context.resources.displayMetrics.density
            ).toInt().coerceAtLeast(1)

    private fun getRegionX(region: Region, widthPx: Int): Int =
        if (region.side == Side.LEFT) {
            0
        } else {
            (getScreenWidth() - widthPx).coerceAtLeast(0)
        }

    private fun getRegionBounds(region: Region): RegionBounds {
        val lower = region.placement == Placement.LOWER
        val safetyDistancePx = if (lower) {
            FloatingBallConfig.getEdgeLowerKeyboardSafetyDistancePx(context)
        } else {
            FloatingBallConfig.getEdgeKeyboardSafetyDistancePx(context)
        }
        return resolveRegionBounds(
            screenHeight = getScreenHeight(),
            imeTopPx = imeTopPx,
            safetyDistancePx = safetyDistancePx,
            lower = lower,
            preview = !imeVisible && FloatingBallConfig.isShowEdgeRegionEnabled(context)
        )
    }

    private fun updateViewState() {
        Region.entries.forEach { region ->
            val view = views[region] ?: return@forEach
            val layoutParams = params[region] ?: return@forEach
            val widthPx = getRegionWidthPx(region)
            val bounds = getRegionBounds(region)
            val touchable = shouldReceiveTouches(region)

            layoutParams.width = widthPx
            layoutParams.height = bounds.height
            layoutParams.x = getRegionX(region, widthPx)
            layoutParams.y = bounds.y
            setTouchable(layoutParams, touchable)

            view.setTouchEnabled(touchable)
            view.setDisplayState()
            updateViewLayout(view, layoutParams)
        }
    }

    private fun setTouchable(params: WindowManager.LayoutParams, touchable: Boolean) {
        params.flags = if (touchable) {
            baseFlags()
        } else {
            baseFlags() or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        }
    }

    private fun updateViewLayout(view: View, params: WindowManager.LayoutParams) {
        try {
            windowManager.updateViewLayout(view, params)
        } catch (e: Exception) {
            TestLog.w(MODULE, "更新边缘手势区域失败: ${e.message}")
        }
    }

    private fun removeView(region: Region) {
        views.remove(region)?.let {
            try {
                windowManager.removeView(it)
                TestLog.i(MODULE, "已移除边缘手势区域: $region")
            } catch (_: Exception) {
                // 视图可能已由系统移除。
            }
        }
        params.remove(region)
    }

    private fun removeViews() {
        Region.entries.forEach(::removeView)
    }

    private inner class RegionView(
        context: Context,
        private val region: Region
    ) : View(context) {
        private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = dp(1f)
        }
        private val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = dp(2f)
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
        private val arrowPath = Path()
        private var touchEnabled = false
        private var touchRecognizer: TouchRecognizer? = null

        init {
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
            setBackgroundColor(Color.TRANSPARENT)
            isClickable = false
            isFocusable = false
        }

        fun setTouchEnabled(enabled: Boolean) {
            if (touchEnabled == enabled) return
            touchEnabled = enabled
            touchRecognizer = if (enabled) TouchRecognizer(region) else null
            setOnTouchListener(touchRecognizer)
        }

        fun setDisplayState() {
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            if (!FloatingBallConfig.isShowEdgeRegionEnabled(context)) return

            val enabled = FloatingBallConfig.isEdgeGestureEnabled(context)
            val isUpper = region.placement == Placement.UPPER
            fillPaint.color = if (isUpper) {
                Color.argb(if (enabled) REGION_ALPHA else REGION_ALPHA / 2, 33, 150, 243)
            } else {
                Color.argb(if (enabled) REGION_ALPHA else REGION_ALPHA / 2, 0, 150, 136)
            }
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), fillPaint)

            linePaint.color = Color.argb(if (enabled) 210 else 110, 20, 100, 140)
            val edgeX = if (region.side == Side.LEFT) width.toFloat() - dp(1f) else dp(1f)
            canvas.drawLine(edgeX, 0f, edgeX, height.toFloat(), linePaint)

            val inwardDirection =
                if (region.side == Side.LEFT) Direction.RIGHT else Direction.LEFT
            drawVerticalArrow(canvas, height * 0.25f, isDirectionEnabled(region.side, Direction.UP), true)
            drawHorizontalArrow(
                canvas,
                height * 0.50f,
                isDirectionEnabled(region.side, inwardDirection)
            )
            drawVerticalArrow(
                canvas,
                height * 0.75f,
                isDirectionEnabled(region.side, Direction.DOWN),
                false
            )
        }

        private fun drawVerticalArrow(canvas: Canvas, centerY: Float, enabled: Boolean, up: Boolean) {
            arrowPaint.color = Color.argb(if (enabled) 210 else 80, 10, 80, 180)
            arrowPath.reset()
            val centerX = width / 2f
            val half = minOf(dp(14f), (height / 10f).coerceAtLeast(dp(4f)))
            val direction = if (up) -1f else 1f
            val startY = centerY - direction * half
            val endY = centerY + direction * half
            arrowPath.moveTo(centerX, startY)
            arrowPath.lineTo(centerX, endY)
            arrowPath.moveTo(centerX, endY)
            arrowPath.lineTo(centerX - dp(5f), endY - direction * dp(7f))
            arrowPath.moveTo(centerX, endY)
            arrowPath.lineTo(centerX + dp(5f), endY - direction * dp(7f))
            canvas.drawPath(arrowPath, arrowPaint)
        }

        private fun drawHorizontalArrow(canvas: Canvas, centerY: Float, enabled: Boolean) {
            arrowPaint.color = Color.argb(if (enabled) 210 else 80, 10, 80, 180)
            arrowPath.reset()
            val direction = if (region.side == Side.LEFT) 1f else -1f
            val centerX = width / 2f
            val half = (width / 2f - dp(3f)).coerceAtLeast(dp(3f))
            val startX = centerX - direction * half
            val endX = centerX + direction * half
            arrowPath.moveTo(startX, centerY)
            arrowPath.lineTo(endX, centerY)
            arrowPath.moveTo(endX, centerY)
            arrowPath.lineTo(endX - direction * dp(5f), centerY - dp(5f))
            arrowPath.moveTo(endX, centerY)
            arrowPath.lineTo(endX - direction * dp(5f), centerY + dp(5f))
            canvas.drawPath(arrowPath, arrowPaint)
        }
    }

    private inner class TouchRecognizer(
        private val region: Region
    ) : View.OnTouchListener {
        private var downX = 0f
        private var downY = 0f
        private var tracking = false

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.rawX
                    downY = event.rawY
                    tracking = true
                    return true
                }

                MotionEvent.ACTION_MOVE -> return tracking

                MotionEvent.ACTION_UP -> {
                    if (!tracking) return false
                    tracking = false

                    val dx = event.rawX - downX
                    val dy = event.rawY - downY
                    val triggerDistance =
                        FloatingBallConfig.getEdgeRegionTriggerDistanceDp(
                            context,
                            regionDistanceKey(region)
                        ) * context.resources.displayMetrics.density
                    val direction = resolveDirection(dx, dy, triggerDistance)

                    if (direction != null &&
                        isDirectionSupported(region.side, direction) &&
                        isDirectionEnabled(region.side, direction)
                    ) {
                        onGesture(region.side, direction)
                    }
                    return true
                }

                MotionEvent.ACTION_CANCEL -> {
                    tracking = false
                    return true
                }
            }
            return true
        }
    }

    private fun isDirectionEnabled(side: Side, direction: Direction): Boolean {
        val key = when (side) {
            Side.LEFT -> when (direction) {
                Direction.UP -> FloatingBallConfig.KEY_EDGE_LEFT_UP_ENABLED
                Direction.DOWN -> FloatingBallConfig.KEY_EDGE_LEFT_DOWN_ENABLED
                Direction.RIGHT -> FloatingBallConfig.KEY_EDGE_LEFT_RIGHT_ENABLED
                Direction.LEFT -> return false
            }

            Side.RIGHT -> when (direction) {
                Direction.UP -> FloatingBallConfig.KEY_EDGE_RIGHT_UP_ENABLED
                Direction.DOWN -> FloatingBallConfig.KEY_EDGE_RIGHT_DOWN_ENABLED
                Direction.LEFT -> FloatingBallConfig.KEY_EDGE_RIGHT_LEFT_ENABLED
                Direction.RIGHT -> return false
            }
        }
        return FloatingBallConfig.isEdgeGestureDirectionEnabled(context, key)
    }

    private fun dp(value: Float): Float =
        value * context.resources.displayMetrics.density
}
