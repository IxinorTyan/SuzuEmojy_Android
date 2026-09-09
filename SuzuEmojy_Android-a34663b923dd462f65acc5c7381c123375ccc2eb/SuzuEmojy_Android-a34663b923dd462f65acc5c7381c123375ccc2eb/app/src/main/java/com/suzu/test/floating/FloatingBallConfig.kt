package com.suzu.test.floating

import android.content.Context

object FloatingBallConfig {
    const val SP_NAME = "app_settings"
    const val KEY_BALL_SIZE_DP = "floating_ball_size_dp"
    const val KEY_BALL_ALPHA = "floating_ball_alpha"

    const val DEFAULT_BALL_SIZE_DP = 56
    const val MIN_BALL_SIZE_DP = 40
    const val MAX_BALL_SIZE_DP = 80

    const val DEFAULT_BALL_ALPHA = 80
    const val MIN_BALL_ALPHA = 30
    const val MAX_BALL_ALPHA = 100

    const val KEY_ANIM_DURATION_MS = "floating_ball_anim_duration_ms"
    const val DEFAULT_ANIM_DURATION_MS = 100
    const val MIN_ANIM_DURATION_MS = 0
    const val MAX_ANIM_DURATION_MS = 200

    const val KEY_BALL_ENABLED = "floating_ball_enabled"
    const val KEY_SHOW_ONLY_WITH_IME = "floating_ball_show_only_with_ime"
    const val KEY_IMAGE_RESOURCE_ID = "floating_ball_image_resource_id"
    const val KEY_NOTIFY_WHEN_A11Y_DISABLED = "notify_when_a11y_disabled"
    const val KEY_BALL_POS_X = "floating_ball_pos_x"
    const val KEY_BALL_POS_Y = "floating_ball_pos_y"
    const val DEFAULT_BALL_POS_X = 100
    const val DEFAULT_BALL_POS_Y = 300

    const val KEY_BALL_SHAPE = "floating_ball_shape"
    const val SHAPE_CIRCLE = 0
    const val SHAPE_ROUNDED_RECT = 1
    const val SHAPE_BORDERLESS = 2
    const val DEFAULT_BALL_SHAPE = SHAPE_CIRCLE

    fun getBallShape(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_BALL_SHAPE, DEFAULT_BALL_SHAPE)
            .coerceIn(SHAPE_CIRCLE, SHAPE_BORDERLESS)
    }

    fun setBallShape(context: Context, shape: Int) {
        val clamped = shape.coerceIn(SHAPE_CIRCLE, SHAPE_BORDERLESS)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_BALL_SHAPE, clamped)
            .apply()
    }

    fun isBallEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_BALL_ENABLED, false)
    }

    fun setBallEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_BALL_ENABLED, enabled)
            .apply()
    }

    fun getImageResourceId(context: Context): Long? {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val id = sp.getLong(KEY_IMAGE_RESOURCE_ID, -1L)
        return if (id > 0) id else null
    }

    fun setImageResourceId(context: Context, id: Long?) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        if (id != null && id > 0) {
            sp.edit().putLong(KEY_IMAGE_RESOURCE_ID, id).apply()
        } else {
            sp.edit().remove(KEY_IMAGE_RESOURCE_ID).apply()
        }
    }

    fun isShowOnlyWithImeEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_SHOW_ONLY_WITH_IME, true)
    }

    fun setShowOnlyWithImeEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SHOW_ONLY_WITH_IME, enabled)
            .apply()
    }

    fun shouldNotifyWhenA11yDisabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_NOTIFY_WHEN_A11Y_DISABLED, true)
    }

    fun setNotifyWhenA11yDisabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_NOTIFY_WHEN_A11Y_DISABLED, enabled)
            .apply()
    }

    fun getSizeDp(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_BALL_SIZE_DP, DEFAULT_BALL_SIZE_DP)
            .coerceIn(MIN_BALL_SIZE_DP, MAX_BALL_SIZE_DP)
    }

    fun setSizeDp(context: Context, sizeDp: Int) {
        val clamped = sizeDp.coerceIn(MIN_BALL_SIZE_DP, MAX_BALL_SIZE_DP)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_BALL_SIZE_DP, clamped)
            .apply()
    }

    fun getAlphaPercent(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_BALL_ALPHA, DEFAULT_BALL_ALPHA)
            .coerceIn(MIN_BALL_ALPHA, MAX_BALL_ALPHA)
    }

    fun setAlphaPercent(context: Context, alphaPct: Int) {
        val clamped = alphaPct.coerceIn(MIN_BALL_ALPHA, MAX_BALL_ALPHA)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_BALL_ALPHA, clamped)
            .apply()
    }

    fun getAnimDurationMs(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(KEY_ANIM_DURATION_MS, DEFAULT_ANIM_DURATION_MS)
            .coerceIn(MIN_ANIM_DURATION_MS, MAX_ANIM_DURATION_MS)
    }

    fun setAnimDurationMs(context: Context, durationMs: Int) {
        val clamped = durationMs.coerceIn(MIN_ANIM_DURATION_MS, MAX_ANIM_DURATION_MS)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_ANIM_DURATION_MS, clamped)
            .apply()
    }

    fun getBallPosition(context: Context): Pair<Int, Int> {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val x = sp.getInt(KEY_BALL_POS_X, DEFAULT_BALL_POS_X)
        val y = sp.getInt(KEY_BALL_POS_Y, DEFAULT_BALL_POS_Y)
        return Pair(x, y)
    }

    fun saveBallPosition(context: Context, x: Int, y: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_BALL_POS_X, x)
            .putInt(KEY_BALL_POS_Y, y)
            .apply()
    }
}
