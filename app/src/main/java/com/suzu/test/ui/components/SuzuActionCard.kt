package com.suzu.test.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.suzu.test.R
import com.suzu.test.databinding.ViewSuzuActionBinding

/** A themed, focusable navigation row for exchange actions. */
class SuzuActionCard @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {
    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alpha = if (enabled) 1f else 0.55f
    }

    init {
        val binding = ViewSuzuActionBinding.inflate(LayoutInflater.from(context), this, true)
        val values = context.obtainStyledAttributes(attrs, R.styleable.SuzuActionCard)
        try {
            val title = values.getText(R.styleable.SuzuActionCard_actionTitle)
            val detail = values.getText(R.styleable.SuzuActionCard_actionDetail)
            binding.actionTitle.text = title
            binding.actionDetail.text = detail
            binding.actionDetail.isVisible = !detail.isNullOrEmpty()
            binding.actionIcon.setImageResource(values.getResourceId(R.styleable.SuzuActionCard_actionIcon, 0))
        } finally { values.recycle() }
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.ui_accent_container))
        isClickable = true
        isFocusable = true
    }
}
