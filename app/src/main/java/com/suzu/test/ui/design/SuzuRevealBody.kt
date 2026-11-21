package com.suzu.test.ui.design

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.suzu.test.R
import kotlin.math.roundToInt

/** Measures children at their open size, exposing only the animated part under the header. */
class SuzuRevealBody @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val maxHeight: Int
    private val maxScreenFraction: Float

    internal var progress = 0f
        set(value) {
            if (field == value) return
            field = value
            requestLayout()
        }

    init {
        val values = context.obtainStyledAttributes(attrs, R.styleable.SuzuRevealBody)
        try {
            maxHeight = values.getDimensionPixelSize(R.styleable.SuzuRevealBody_suzuRevealMaxHeight, Int.MAX_VALUE)
            maxScreenFraction = values.getFloat(R.styleable.SuzuRevealBody_suzuRevealMaxScreenFraction, 0f)
        } finally {
            values.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightLimit = if (maxScreenFraction > 0f && resources.configuration.screenHeightDp > 0) {
            val screenMax = resources.configuration.screenHeightDp * resources.displayMetrics.density * maxScreenFraction
            minOf(maxHeight, screenMax.roundToInt())
        } else maxHeight
        val bodySpec = if (heightLimit < Int.MAX_VALUE) {
            val available = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
                heightLimit
            } else minOf(heightLimit, MeasureSpec.getSize(heightMeasureSpec))
            MeasureSpec.makeMeasureSpec(available, MeasureSpec.AT_MOST)
        } else heightMeasureSpec
        super.onMeasure(widthMeasureSpec, bodySpec)
        setMeasuredDimension(measuredWidthAndState, (measuredHeight * progress).roundToInt())
    }
}
