package com.suzu.test.ui.library

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.hypot

class LibraryDragTouchListener(
    context: Context,
    private val isSortingMode: () -> Boolean,
    private val onStartDrag: (RecyclerView.ViewHolder) -> Unit
) : RecyclerView.OnItemTouchListener {

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val handler = Handler(Looper.getMainLooper())

    private var downX = 0f
    private var downY = 0f
    private var targetViewHolder: RecyclerView.ViewHolder? = null

    private val dragRunnable = Runnable {
        val vh = targetViewHolder
        if (vh != null && isSortingMode()) {
            vh.itemView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            onStartDrag(vh)
        }
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (!isSortingMode()) {
            cancelTimer()
            return false
        }

        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    targetViewHolder = rv.getChildViewHolder(child)
                    handler.postDelayed(dragRunnable, 1000)
                } else {
                    targetViewHolder = null
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dist = hypot(e.x - downX, e.y - downY)
                if (dist > touchSlop) {
                    cancelTimer()
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 第二根或更多手指按下时，立即取消起拖
                cancelTimer()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                cancelTimer()
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (!isSortingMode()) {
            cancelTimer()
            return
        }
        when (e.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                val dist = hypot(e.x - downX, e.y - downY)
                if (dist > touchSlop) {
                    cancelTimer()
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                cancelTimer()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                cancelTimer()
            }
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (disallowIntercept) {
            cancelTimer()
        }
    }

    fun cancelTimer() {
        handler.removeCallbacks(dragRunnable)
        targetViewHolder = null
    }
}
