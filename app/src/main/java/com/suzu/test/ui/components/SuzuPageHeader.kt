package com.suzu.test.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.suzu.test.R
import com.suzu.test.databinding.ViewSuzuPageHeaderBinding

class SuzuPageHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private val binding = ViewSuzuPageHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val values = context.obtainStyledAttributes(attrs, R.styleable.SuzuPageHeader)
        try { binding.pageTitle.text = values.getText(R.styleable.SuzuPageHeader_pageTitle) }
        finally { values.recycle() }
    }

    fun onBack(action: () -> Unit) { binding.pageBack.setOnClickListener { action() } }
}
