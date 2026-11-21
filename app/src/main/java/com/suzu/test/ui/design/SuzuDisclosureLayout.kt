package com.suzu.test.ui.design

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.PathInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.suzu.test.R
import kotlin.math.abs

/** An App-only surface with a header and a clipped, animated [SuzuRevealBody]. */
class SuzuDisclosureLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private lateinit var body: SuzuRevealBody
    private var animator: ValueAnimator? = null
    private var trigger: View? = null
    private var indicator: View? = null
    private var collapsedAction: CharSequence? = null
    private var expandedAction: CharSequence? = null

    var isExpanded: Boolean = false
        private set

    init {
        orientation = VERTICAL
        if (background == null) setBackgroundResource(R.drawable.bg_suzu_section)
        clipToOutline = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        check(childCount == 2 && getChildAt(1) is SuzuRevealBody) {
            "SuzuDisclosureLayout requires a header followed by a SuzuRevealBody"
        }
        body = getChildAt(1) as SuzuRevealBody
        applyProgress(0f)
        body.isVisible = false
    }

    /** Binds presentation, leaving the click action and persistence to the screen. */
    fun bindIndicator(
        trigger: View,
        indicator: View,
        collapsedAction: CharSequence? = null,
        expandedAction: CharSequence? = null
    ) {
        this.trigger = trigger
        this.indicator = indicator
        this.collapsedAction = collapsedAction
        this.expandedAction = expandedAction
        indicator.rotation = 180f * body.progress
        updateTrigger()
    }

    fun setExpanded(expanded: Boolean, animate: Boolean = true) {
        if (isExpanded == expanded) return
        isExpanded = expanded
        updateTrigger()
        cancelAnimation()

        body.importantForAccessibility = if (expanded) IMPORTANT_FOR_ACCESSIBILITY_AUTO
            else IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
        val target = if (expanded) 1f else 0f
        val start = body.progress
        if (!animate || !isLaidOut || !ValueAnimator.areAnimatorsEnabled() || target == start) {
            finishExpansion()
        } else {
            body.isVisible = true
            val animation = ValueAnimator.ofFloat(start, target).apply {
                duration = (220 * abs(target - start)).toLong().coerceAtLeast(70L)
                interpolator = PathInterpolator(0.2f, 0f, 0f, 1f)
                addUpdateListener { applyProgress(it.animatedValue as Float) }
                doOnEnd {
                    if (animator === it) {
                        animator = null
                        finishExpansion()
                    }
                }
            }
            animator = animation
            animation.start()
        }
    }

    override fun onDetachedFromWindow() {
        cancelAnimation()
        finishExpansion()
        super.onDetachedFromWindow()
    }

    private fun cancelAnimation() {
        val previous = animator
        animator = null
        previous?.cancel()
    }

    private fun finishExpansion() {
        applyProgress(if (isExpanded) 1f else 0f)
        body.isVisible = isExpanded
    }

    private fun applyProgress(progress: Float) {
        body.progress = progress
        body.alpha = 0.55f + 0.45f * progress
        indicator?.rotation = 180f * progress
    }

    private fun updateTrigger() {
        val view = trigger ?: return
        val action = if (isExpanded) expandedAction else collapsedAction
        if (action != null) view.contentDescription = action
        ViewCompat.setStateDescription(view, context.getText(
            if (isExpanded) R.string.suzu_disclosure_expanded else R.string.suzu_disclosure_collapsed
        ))
    }
}
