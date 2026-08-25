package com.suzu.test.ime.theme

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.view.Gravity
import com.suzu.test.databinding.ViewImeKeyboardBinding

object ThemeApplier {

    fun applyTo(binding: ViewImeKeyboardBinding, theme: KeyboardTheme) {
        val root = binding.root
        root.setBackgroundColor(theme.rootBg)

        binding.hsvTabBar.parent?.let { parentView ->
            if (parentView is android.view.View) {
                parentView.setBackgroundColor(theme.tabBarBg)
            }
        }

        binding.flGridContainer.setBackgroundColor(theme.gridBg)
        binding.tvEmptyLibraryHint.setTextColor(theme.emptyHintText)

        binding.btnExit.imageTintList = ColorStateList.valueOf(theme.iconColor)
        binding.btnExit.background = createTabBackground(theme)
    }

    fun createTabBackground(theme: KeyboardTheme, density: Float = 2.0f): StateListDrawable {
        val radiusPx = 8 * density
        val indicatorHeightPx = (3 * density).toInt()

        val selectedBase = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(theme.tabSelectedBg)
            cornerRadius = radiusPx
        }
        val selectedIndicator = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setSize(-1, indicatorHeightPx)
            setColor(theme.tabIndicator)
        }
        val selectedLayer = LayerDrawable(arrayOf(selectedBase, selectedIndicator)).apply {
            setLayerGravity(1, Gravity.BOTTOM)
        }

        val pressedDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(theme.tabPressedBg)
            cornerRadius = radiusPx
        }

        val normalDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(0x00000000)
            cornerRadius = radiusPx
        }

        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), selectedLayer)
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(), normalDrawable)
        }
    }
}
