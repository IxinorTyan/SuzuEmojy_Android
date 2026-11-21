package com.suzu.test.ui.library

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.recyclerview.widget.RecyclerView

/** Keeps folder-grid pinch handling separate from the shared disclosure animation. */
class LibraryCategoryGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    var onSpanStep: (Int) -> Unit = {}

    private var consumesGesture = false
    private val pinch = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            private var startSpan = 0f
            private var stepped = false

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                startSpan = detector.currentSpan
                stepped = false
                return startSpan > 0f
            }

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                if (!stepped) {
                    val scale = detector.currentSpan / startSpan
                    val step = when {
                        scale > 1.15f -> -1
                        scale < 0.85f -> 1
                        else -> 0
                    }
                    if (step != 0) {
                        stepped = true
                        onSpanStep(step)
                    }
                }
                return true
            }
        }
    ).apply {
        isQuickScaleEnabled = false
        isStylusScaleEnabled = false
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) consumesGesture = false
        pinch.onTouchEvent(event)
        if (!consumesGesture && event.pointerCount > 1) {
            // Cancel a possible item press before taking over from the scrolling grid.
            val cancel = MotionEvent.obtain(event)
            cancel.action = MotionEvent.ACTION_CANCEL
            super.dispatchTouchEvent(cancel)
            cancel.recycle()
            consumesGesture = true
        }
        return consumesGesture || super.dispatchTouchEvent(event)
    }
}
