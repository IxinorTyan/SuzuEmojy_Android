package com.suzu.test.ime.theme

import android.content.Context

data class KeyboardTheme(
    val isDark: Boolean,
    val rootBg: Int,
    val tabBarBg: Int,
    val gridBg: Int,
    val tabTextSelected: Int,
    val tabTextUnselected: Int,
    val tabSelectedBg: Int,
    val tabPressedBg: Int,
    val tabIndicator: Int,
    val iconColor: Int,
    val emptyHintText: Int,
    val popupBg: Int,
    val popupStroke: Int,
    val itemBorderColor: Int,
    val itemBgColor: Int
) {
    companion object {
        const val SP_NAME = "app_settings"
        const val KEY_THEME = "keyboard_theme"
        const val THEME_DARK = "dark"
        const val THEME_LIGHT = "light"

        val DarkTheme = KeyboardTheme(
            isDark = true,
            rootBg = 0xFF2B2B2B.toInt(),
            tabBarBg = 0xFF252525.toInt(),
            gridBg = 0xFF202020.toInt(),
            tabTextSelected = 0xFFFFFFFF.toInt(),
            tabTextUnselected = 0xFFCCCCCC.toInt(),
            tabSelectedBg = 0xFF3D3D3D.toInt(),
            tabPressedBg = 0x33FFFFFF.toInt(),
            tabIndicator = 0xFF0078D4.toInt(),
            iconColor = 0xFFCCCCCC.toInt(),
            emptyHintText = 0xFF888888.toInt(),
            popupBg = 0xD9000000.toInt(),
            popupStroke = 0x33FFFFFF.toInt(),
            itemBorderColor = 0xFF333333.toInt(),
            itemBgColor = 0xFF151515.toInt()
        )

        val LightTheme = KeyboardTheme(
            isDark = false,
            rootBg = 0xFFFFFFFF.toInt(),
            tabBarBg = 0xFFF0F0F0.toInt(),
            gridBg = 0xFFFAFAFA.toInt(),
            tabTextSelected = 0xFF000000.toInt(),
            tabTextUnselected = 0xFF666666.toInt(),
            tabSelectedBg = 0xFFE2E2E2.toInt(),
            tabPressedBg = 0x1A000000.toInt(),
            tabIndicator = 0xFF0078D4.toInt(),
            iconColor = 0xFF555555.toInt(),
            emptyHintText = 0xFF999999.toInt(),
            popupBg = 0xF0FFFFFF.toInt(),
            popupStroke = 0x33000000.toInt(),
            itemBorderColor = 0xFFE0E0E0.toInt(),
            itemBgColor = 0xFFF5F5F5.toInt()
        )

        fun current(context: Context): KeyboardTheme {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val themeKey = sp.getString(KEY_THEME, THEME_DARK) ?: THEME_DARK
            return if (themeKey == THEME_LIGHT) LightTheme else DarkTheme
        }

        fun setTheme(context: Context, themeKey: String) {
            val validKey = if (themeKey == THEME_LIGHT) THEME_LIGHT else THEME_DARK
            context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_THEME, validKey)
                .apply()
        }
    }
}
