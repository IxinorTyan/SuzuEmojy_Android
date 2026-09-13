package com.suzu.test.ui.picker

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class SlideSelectionHelper(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val isEnabled: () -> Boolean = { true },
    private val onSelectionRangeChanged: (start: Int, end: Int, isSelecting: Boolean) -> Unit
) : RecyclerView.OnItemTouchListener {

    private val edgeScrollThresholdPx = (60 * context.resources.displayMetrics.density).toInt()
    private val scrollSpeedPx = (14 * context.resources.displayMetrics.density).toInt()
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val longPressTimeout = ViewConfiguration.getLongPressTimeout().toLong()

    private var isSliding = false
    private var isLongPressActivated = false
    private var startPosition = RecyclerView.NO_POSITION
    private var lastHandledPosition = RecyclerView.NO_POSITION
    private var targetSelectedState = true

    private var downX = 0f
    private var downY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    private val originalStates = HashMap<Int, Boolean>()

    private val handler = Handler(Looper.getMainLooper())
    private var isAutoScrolling = false

    private var getItemSelectionState: ((position: Int) -> Boolean)? = null

    fun setItemStateProvider(provider: (position: Int) -> Boolean) {
        getItemSelectionState = provider
    }

    private val longPressRunnable = Runnable {
        if (!isEnabled() || startPosition == RecyclerView.NO_POSITION) return@Runnable

        isLongPressActivated = true
        isSliding = true
        recyclerView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        recyclerView.parent?.requestDisallowInterceptTouchEvent(true)

        // 切换起始项自身状态
        if (!originalStates.containsKey(startPosition)) {
            val original = getItemSelectionState?.invoke(startPosition) ?: false
            originalStates[startPosition] = original
        }
        onSelectionRangeChanged(startPosition, startPosition, targetSelectedState)
        lastHandledPosition = startPosition

        checkEdgeScroll()
    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (!isSliding) {
                stopAutoScroll()
                return
            }

            val rvRect = Rect()
            recyclerView.getGlobalVisibleRect(rvRect)

            val y = lastTouchY
            var scrollDelta = 0

            if (y < edgeScrollThresholdPx) {
                scrollDelta = -scrollSpeedPx
            } else if (y > recyclerView.height - edgeScrollThresholdPx) {
                scrollDelta = scrollSpeedPx
            }

            if (scrollDelta != 0) {
                recyclerView.scrollBy(0, scrollDelta)
                updateSelectionUnderTouch()
                handler.postDelayed(this, 16)
            } else {
                stopAutoScroll()
            }
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (!isEnabled()) {
            cancelLongPressTimer()
            resetState()
            return false
        }

        lastTouchX = e.x
        lastTouchY = e.y

        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
                isLongPressActivated = false
                isSliding = false
                originalStates.clear()

                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val pos = rv.getChildAdapterPosition(child)
                    if (pos != RecyclerView.NO_POSITION) {
                        startPosition = pos
                        lastHandledPosition = pos
                        val isSelected = getItemSelectionState?.invoke(pos) ?: false
                        targetSelectedState = !isSelected
                        handler.postDelayed(longPressRunnable, longPressTimeout)
                    } else {
                        startPosition = RecyclerView.NO_POSITION
                    }
                } else {
                    startPosition = RecyclerView.NO_POSITION
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isLongPressActivated) {
                    val dist = hypot(e.x - downX, e.y - downY)
                    if (dist > touchSlop) {
                        // 手指滑移超出 touchSlop 但尚未达到长按时间，判定为普通滚动，取消长按
                        cancelLongPressTimer()
                    }
                } else {
                    // 已长按激活，直接拦截后续事件
                    return true
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 多指触碰，取消长按起滑
                cancelLongPressTimer()
                if (!isSliding) {
                    resetState()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                cancelLongPressTimer()
                if (isSliding) {
                    resetState()
                    return true
                }
                resetState()
            }
        }
        return isSliding
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (!isEnabled()) {
            cancelLongPressTimer()
            resetState()
            return
        }

        lastTouchX = e.x
        lastTouchY = e.y

        when (e.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                if (isSliding) {
                    updateSelectionUnderTouch()
                    checkEdgeScroll()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                cancelLongPressTimer()
                resetState()
            }
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (disallowIntercept && !isSliding) {
            cancelLongPressTimer()
        }
    }

    private fun cancelLongPressTimer() {
        handler.removeCallbacks(longPressRunnable)
    }

    private fun updateSelectionUnderTouch() {
        val child = recyclerView.findChildViewUnder(lastTouchX, lastTouchY)
        if (child != null) {
            val currentPos = recyclerView.getChildAdapterPosition(child)
            if (currentPos != RecyclerView.NO_POSITION && currentPos != lastHandledPosition) {
                processSlide(startPosition, currentPos)
            }
        }
    }

    private fun processSlide(start: Int, current: Int) {
        if (start == RecyclerView.NO_POSITION || current == RecyclerView.NO_POSITION) return

        val low = min(start, current)
        val high = max(start, current)

        val previousLow = if (lastHandledPosition != RecyclerView.NO_POSITION) min(start, lastHandledPosition) else low
        val previousHigh = if (lastHandledPosition != RecyclerView.NO_POSITION) max(start, lastHandledPosition) else high

        val minBound = min(low, previousLow)
        val maxBound = max(high, previousHigh)

        for (i in minBound..maxBound) {
            if (i in low..high) {
                if (!originalStates.containsKey(i)) {
                    val original = getItemSelectionState?.invoke(i) ?: false
                    originalStates[i] = original
                }
                onSelectionRangeChanged(i, i, targetSelectedState)
            } else {
                if (originalStates.containsKey(i)) {
                    val original = originalStates[i] ?: false
                    onSelectionRangeChanged(i, i, original)
                }
            }
        }

        lastHandledPosition = current
    }

    private fun checkEdgeScroll() {
        if (lastTouchY < edgeScrollThresholdPx || lastTouchY > recyclerView.height - edgeScrollThresholdPx) {
            if (!isAutoScrolling) {
                isAutoScrolling = true
                handler.post(autoScrollRunnable)
            }
        } else {
            stopAutoScroll()
        }
    }

    private fun stopAutoScroll() {
        if (isAutoScrolling) {
            isAutoScrolling = false
            handler.removeCallbacks(autoScrollRunnable)
        }
    }

    fun cleanup() {
        cancelLongPressTimer()
        stopAutoScroll()
        resetState()
    }

    private fun resetState() {
        cancelLongPressTimer()
        stopAutoScroll()
        isSliding = false
        isLongPressActivated = false
        startPosition = RecyclerView.NO_POSITION
        lastHandledPosition = RecyclerView.NO_POSITION
        originalStates.clear()
        recyclerView.parent?.requestDisallowInterceptTouchEvent(false)
    }
}
