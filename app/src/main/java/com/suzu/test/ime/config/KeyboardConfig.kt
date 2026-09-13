package com.suzu.test.ime.config

import android.content.Context
import com.suzu.test.R

object KeyboardConfig {
    const val SP_NAME = "app_settings"
    const val KEY_GRID_HEIGHT_DP = "keyboard_grid_height_dp"
    const val KEY_SPAN_COUNT = "keyboard_span_count"

    const val DEFAULT_GRID_HEIGHT_DP = 190
    const val MIN_GRID_HEIGHT_DP = 120
    const val MAX_GRID_HEIGHT_DP = 400

    const val DEFAULT_SPAN_COUNT = 4
    const val MIN_SPAN_COUNT = 3
    const val MAX_SPAN_COUNT = 8

    const val KEY_TAB_ICON_SIZE_DP = "keyboard_tab_icon_size_dp"
    const val DEFAULT_TAB_ICON_SIZE_DP = 56
    const val MIN_TAB_ICON_SIZE_DP = 36
    const val MAX_TAB_ICON_SIZE_DP = 64

    const val KEY_SHOW_RECENT_TAB = "keyboard_show_recent_tab"
    const val DEFAULT_SHOW_RECENT_TAB = true

    const val KEY_SHOW_ALL_TAB = "keyboard_show_all_tab"
    const val DEFAULT_SHOW_ALL_TAB = true

    const val KEY_DROPDOWN_ICON_STYLE = "keyboard_dropdown_icon_style"
    const val DROPDOWN_ICON_ARROW = "arrow"
    const val DROPDOWN_ICON_MENU = "menu"
    const val DROPDOWN_ICON_MORE = "more"
    const val DEFAULT_DROPDOWN_ICON_STYLE = DROPDOWN_ICON_ARROW

    const val KEY_DROPDOWN_POSITION = "keyboard_dropdown_position"
    const val DROPDOWN_POSITION_LEFT = "left"
    const val DROPDOWN_POSITION_RIGHT = "right"
    const val DEFAULT_DROPDOWN_POSITION = DROPDOWN_POSITION_LEFT

    fun getGridHeightDp(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_GRID_HEIGHT_DP, DEFAULT_GRID_HEIGHT_DP)
            .coerceIn(MIN_GRID_HEIGHT_DP, MAX_GRID_HEIGHT_DP)
    }

    fun setGridHeightDp(context: Context, heightDp: Int) {
        val clamped = heightDp.coerceIn(MIN_GRID_HEIGHT_DP, MAX_GRID_HEIGHT_DP)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_GRID_HEIGHT_DP, clamped)
            .apply()
    }

    fun getSpanCount(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_SPAN_COUNT, DEFAULT_SPAN_COUNT)
            .coerceIn(MIN_SPAN_COUNT, MAX_SPAN_COUNT)
    }

    fun setSpanCount(context: Context, spanCount: Int) {
        val clamped = spanCount.coerceIn(MIN_SPAN_COUNT, MAX_SPAN_COUNT)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_SPAN_COUNT, clamped)
            .apply()
    }

    fun getTabIconSizeDp(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_TAB_ICON_SIZE_DP, DEFAULT_TAB_ICON_SIZE_DP)
            .coerceIn(MIN_TAB_ICON_SIZE_DP, MAX_TAB_ICON_SIZE_DP)
    }

    fun setTabIconSizeDp(context: Context, sizeDp: Int) {
        val clamped = sizeDp.coerceIn(MIN_TAB_ICON_SIZE_DP, MAX_TAB_ICON_SIZE_DP)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_TAB_ICON_SIZE_DP, clamped)
            .apply()
    }

    fun isRecentTabEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_SHOW_RECENT_TAB, DEFAULT_SHOW_RECENT_TAB)
    }

    fun setRecentTabEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SHOW_RECENT_TAB, enabled)
            .apply()
    }

    fun isAllTabEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_SHOW_ALL_TAB, DEFAULT_SHOW_ALL_TAB)
    }

    fun setAllTabEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SHOW_ALL_TAB, enabled)
            .apply()
    }

    fun getDropdownIconStyle(context: Context): String {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(KEY_DROPDOWN_ICON_STYLE, DEFAULT_DROPDOWN_ICON_STYLE)
            ?: DEFAULT_DROPDOWN_ICON_STYLE
    }

    fun setDropdownIconStyle(context: Context, style: String) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_DROPDOWN_ICON_STYLE, style)
            .apply()
    }

    fun getDropdownPosition(context: Context): String {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(KEY_DROPDOWN_POSITION, DEFAULT_DROPDOWN_POSITION)
            ?: DEFAULT_DROPDOWN_POSITION
    }

    fun setDropdownPosition(context: Context, position: String) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_DROPDOWN_POSITION, position)
            .apply()
    }

    fun getDropdownIconRes(context: Context): Int {
        return when (getDropdownIconStyle(context)) {
            DROPDOWN_ICON_MENU -> R.drawable.ic_tab_dropdown_menu
            DROPDOWN_ICON_MORE -> R.drawable.ic_tab_dropdown_more
            else -> R.drawable.ic_keyboard_expand_24
        }
    }
}
