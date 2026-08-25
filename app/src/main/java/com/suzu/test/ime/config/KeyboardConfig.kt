package com.suzu.test.ime.config

import android.content.Context

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
}
