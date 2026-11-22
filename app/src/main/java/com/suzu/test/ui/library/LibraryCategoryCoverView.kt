package com.suzu.test.ui.library

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/** Shared icon/cover binding for the compact tab and the expanded square thumbnail. */
class LibraryCategoryCoverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val image = AppCompatImageView(context).apply {
        isDuplicateParentStateEnabled = true
        scaleType = ImageView.ScaleType.FIT_CENTER
    }
    private val text = AppCompatTextView(context).apply {
        isDuplicateParentStateEnabled = true
        gravity = Gravity.CENTER
        setSingleLine(true)
        setTextColor(AppCompatResources.getColorStateList(context, R.color.library_category_label))
    }
    private var lookup: Job? = null
    private var compactTint: ColorStateList? = null

    fun setCompactTint(color: Int) {
        compactTint = ColorStateList.valueOf(color)
        text.setTextColor(color)
    }

    init {
        setBackgroundResource(R.drawable.bg_library_category_cover)
        clipToOutline = true
        addView(image, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addView(text, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            heightMeasureSpec
        } else {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val size = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
                minOf(width, MeasureSpec.getSize(heightMeasureSpec))
            } else width
            MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, height)
    }

    fun bindSymbol(icon: Int, compact: Boolean) {
        clear()
        showSymbol(icon, compact)
    }

    fun bindCategory(iconPath: String?, compact: Boolean, scope: CoroutineScope) {
        clear()
        when (val icon = CategoryIconResolver.resolve(iconPath)) {
            CategoryIconResult.Default -> showSymbol(R.drawable.ic_category_default, compact)
            is CategoryIconResult.Text -> {
                setBackplate(!compact)
                image.isVisible = false
                text.isVisible = true
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    text, 8, if (compact) 17 else 32, 1, TypedValue.COMPLEX_UNIT_SP
                )
                text.text = icon.content
            }
            is CategoryIconResult.ImageFile -> {
                showImage(File(context.filesDir, icon.relativePath), compact)
            }
            is CategoryIconResult.Resource -> {
                showSymbol(R.drawable.ic_category_default, compact)
                lookup = scope.launch {
                    val file = withContext(Dispatchers.IO) {
                        DatabaseProvider.getDatabase(context).resourceDao().getById(icon.resourceId)
                            ?.let { File(context.filesDir, "resources/${it.filename}") }
                    }
                    if (file != null) showImage(file, compact)
                }
            }
        }
    }

    fun clear() {
        lookup?.cancel()
        lookup = null
        Glide.with(image).clear(image)
        image.setImageDrawable(null)
        text.text = ""
    }

    private fun showSymbol(icon: Int, compact: Boolean) {
        setBackplate(!compact)
        text.isVisible = false
        image.isVisible = true
        image.imageTintList = if (compact) {
            compactTint ?: AppCompatResources.getColorStateList(context, R.color.library_category_icon)
        } else {
            ColorStateList.valueOf(context.getColor(R.color.ui_accent))
        }
        image.setImageDrawable(symbol(icon, compact))
    }

    private fun showImage(file: File, compact: Boolean) {
        setBackplate(!compact)
        text.isVisible = false
        image.isVisible = true
        image.imageTintList = null
        val fallback = symbol(R.drawable.ic_category_default, compact)?.apply {
            setTint(if (compact) compactTint?.defaultColor ?: context.getColor(R.color.ui_accent) else context.getColor(R.color.ui_accent))
        }
        Glide.with(image).asBitmap().load(file).fitCenter().error(fallback).into(image)
    }

    private fun symbol(icon: Int, compact: Boolean) =
        AppCompatResources.getDrawable(context, icon)?.mutate()?.let {
            InsetDrawable(it, if (compact) 0.12f else 0.32f)
        }

    private fun setBackplate(visible: Boolean) {
        backgroundTintList = if (visible) null else ColorStateList.valueOf(Color.TRANSPARENT)
    }
}
