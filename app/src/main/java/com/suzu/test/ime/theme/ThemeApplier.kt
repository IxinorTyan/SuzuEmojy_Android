package com.suzu.test.ime.theme

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.view.Gravity
import androidx.core.graphics.ColorUtils
import com.suzu.test.databinding.ViewImeKeyboardBinding

object ThemeApplier {

    fun applyTo(binding: ViewImeKeyboardBinding, theme: KeyboardTheme, wallpaper: KeyboardWallpaper.Frame?) {
        applyWallpaper(binding, theme, wallpaper)
        binding.svImeTabDropdown.background = createPopupBackground(theme)
        binding.glImeTabDropdown.setBackgroundColor(theme.popupBg)

        binding.llSendDiagnostic.setBackgroundColor(theme.rootBg)
        binding.tvSendDiagnosticTitle.setTextColor(theme.tabTextSelected)
        binding.switchDebugSkipProvider.setTextColor(theme.tabTextSelected)
        binding.tvSendDiagnosticContent.setTextColor(theme.tabTextUnselected)

        val headerButtonBackground = createTabBackground(theme)
        binding.btnTabDropdown.imageTintList = ColorStateList.valueOf(theme.iconColor)
        binding.btnTabDropdown.background = headerButtonBackground
        binding.btnExit.imageTintList = ColorStateList.valueOf(theme.iconColor)
        binding.btnExit.background = createTabBackground(theme)
    }

    fun applyWallpaper(binding: ViewImeKeyboardBinding, theme: KeyboardTheme, wallpaper: KeyboardWallpaper.Frame?) {
        if (wallpaper == null) {
            binding.root.setBackgroundColor(theme.rootBg)
            binding.llTabHeaderBar.setBackgroundColor(theme.tabBarBg)
            binding.flGridContainer.setBackgroundColor(theme.gridBg)
        } else {
            binding.root.background = WallpaperDrawable(wallpaper, theme.gridBg)
            // Keep the controls legible without hiding the image behind the category bar.
            binding.llTabHeaderBar.setBackgroundColor(ColorUtils.setAlphaComponent(theme.tabBarBg, 200))
            binding.flGridContainer.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun createPopupBackground(
        theme: KeyboardTheme,
        density: Float = 2.0f
    ): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(theme.popupBg)
            setStroke((1 * density).toInt().coerceAtLeast(1), theme.popupStroke)
        }
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
