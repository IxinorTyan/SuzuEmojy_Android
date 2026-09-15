package com.suzu.test.ui.view

import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/** Claims only deliberate horizontal gestures; vertical scrolling and pinching stay with the grid. */
class CategorySwipeTouchListener(
    private val enabled: () -> Boolean,
    private val onStep: (Int) -> Unit
) : RecyclerView.SimpleOnItemTouchListener() {
    private var startX = 0f
    private var startY = 0f
    private var blocked = true
    private var claimed = false
    private var velocity: VelocityTracker? = null

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                reset()
                startX = e.x
                startY = e.y
                blocked = !enabled()
                if (!blocked) velocity = VelocityTracker.obtain().also { it.addMovement(e) }
            }
            MotionEvent.ACTION_POINTER_DOWN -> blocked = true
            MotionEvent.ACTION_MOVE -> {
                if (blocked || !enabled() || e.pointerCount != 1) return false
                velocity?.addMovement(e)
                val dx = abs(e.x - startX)
                val dy = abs(e.y - startY)
                val slop = ViewConfiguration.get(rv.context).scaledTouchSlop
                // Once the user starts scrolling vertically, never reinterpret that gesture.
                if (dy > slop && dy >= dx) blocked = true
                if (!blocked && dx > slop * 2 && dx > dy * 1.5f) {
                    // A long press may already have opened a preview or an action menu.
                    if (e.eventTime - e.downTime >= ViewConfiguration.getLongPressTimeout()) {
                        blocked = true
                        return false
                    }
                    claimed = true
                    rv.stopScroll()
                    rv.parent?.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> reset()
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        velocity?.addMovement(e)
        if (e.pointerCount != 1 || !enabled()) blocked = true
        when (e.actionMasked) {
            MotionEvent.ACTION_UP -> {
                velocity?.computeCurrentVelocity(1000)
                val dx = e.x - startX
                val dy = e.y - startY
                val density = rv.resources.displayMetrics.density
                val distance = maxOf(48f * density, rv.width * 0.15f)
                val vx = velocity?.xVelocity ?: 0f
                val fling = abs(dx) >= 24f * density && abs(vx) >= 600f * density && vx * dx > 0
                val step = if (claimed && !blocked && abs(dx) > abs(dy) * 1.5f &&
                    (abs(dx) >= distance || fling)) {
                    if (dx < 0) 1 else -1
                } else 0
                rv.parent?.requestDisallowInterceptTouchEvent(false)
                reset()
                if (step != 0) onStep(step)
            }
            MotionEvent.ACTION_CANCEL -> {
                rv.parent?.requestDisallowInterceptTouchEvent(false)
                reset()
            }
        }
    }

    private fun reset() {
        velocity?.recycle()
        velocity = null
        blocked = true
        claimed = false
    }
}
