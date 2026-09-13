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

    // 总开关
    const val KEY_FLOATING_MASTER_ENABLED = "floating_master_enabled"
    // 悬浮球开关
    const val KEY_BALL_ENABLED = "floating_ball_enabled"
    // 悬浮窗(边缘手势)开关
    const val KEY_EDGE_GESTURE_ENABLED = "edge_gesture_enabled"
    // 悬浮窗左右侧独立开关
    const val KEY_EDGE_LEFT_ENABLED = "edge_gesture_left_enabled"
    const val KEY_EDGE_RIGHT_ENABLED = "edge_gesture_right_enabled"
    const val KEY_EDGE_LEFT_LOWER_ENABLED = "edge_gesture_left_lower_enabled"
    const val KEY_EDGE_RIGHT_LOWER_ENABLED = "edge_gesture_right_lower_enabled"

    const val KEY_SHOW_ONLY_WITH_IME = "floating_ball_show_only_with_ime"
    const val KEY_IMAGE_RESOURCE_ID = "floating_ball_image_resource_id"
    const val KEY_NOTIFY_WHEN_A11Y_DISABLED = "notify_when_a11y_disabled"
    const val KEY_BALL_POS_X = "floating_ball_pos_x"
    const val KEY_BALL_POS_Y = "floating_ball_pos_y"
    const val DEFAULT_BALL_POS_X = 100
    const val DEFAULT_BALL_POS_Y = 300

    const val KEY_BALL_SHAPE = "floating_ball_shape"

    const val KEY_SHOW_EDGE_REGION = "show_edge_gesture_region"
    const val KEY_EDGE_LEFT_UP_ENABLED = "edge_gesture_left_up_enabled"
    const val KEY_EDGE_LEFT_DOWN_ENABLED = "edge_gesture_left_down_enabled"
    const val KEY_EDGE_LEFT_RIGHT_ENABLED = "edge_gesture_left_right_enabled"
    const val KEY_EDGE_RIGHT_UP_ENABLED = "edge_gesture_right_up_enabled"
    const val KEY_EDGE_RIGHT_DOWN_ENABLED = "edge_gesture_right_down_enabled"
    const val KEY_EDGE_RIGHT_LEFT_ENABLED = "edge_gesture_right_left_enabled"

    // 边缘手势动作自定义
    const val KEY_EDGE_LEFT_UP_ACTION = "edge_gesture_left_up_action"
    const val KEY_EDGE_LEFT_DOWN_ACTION = "edge_gesture_left_down_action"
    const val KEY_EDGE_LEFT_RIGHT_ACTION = "edge_gesture_left_right_action"
    const val KEY_EDGE_RIGHT_UP_ACTION = "edge_gesture_right_up_action"
    const val KEY_EDGE_RIGHT_DOWN_ACTION = "edge_gesture_right_down_action"
    const val KEY_EDGE_RIGHT_LEFT_ACTION = "edge_gesture_right_left_action"

    enum class EdgeGestureAction(val id: Int, val title: String) {
        NONE(0, "关闭"),
        SWITCH_KEYBOARD(1, "切换键盘"),
        OPEN_SEARCH(2, "唤起搜索框");

        companion object {
            fun fromId(id: Int): EdgeGestureAction =
                entries.firstOrNull { it.id == id } ?: SWITCH_KEYBOARD
        }
    }
    const val KEY_EDGE_WIDTH_DP = "edge_gesture_width_dp"
    const val KEY_EDGE_TRIGGER_DISTANCE_DP = "edge_gesture_trigger_distance_dp"
    const val KEY_EDGE_KEYBOARD_SAFETY_DISTANCE_PX = "edge_gesture_keyboard_safety_distance_px"
    const val KEY_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX =
        "edge_gesture_lower_keyboard_safety_distance_px"
    const val KEY_EDGE_LEFT_UPPER_WIDTH_DP = "edge_gesture_left_upper_width_dp"
    const val KEY_EDGE_LEFT_UPPER_DISTANCE_DP = "edge_gesture_left_upper_distance_dp"
    const val KEY_EDGE_RIGHT_UPPER_WIDTH_DP = "edge_gesture_right_upper_width_dp"
    const val KEY_EDGE_RIGHT_UPPER_DISTANCE_DP = "edge_gesture_right_upper_distance_dp"
    const val KEY_EDGE_LEFT_LOWER_WIDTH_DP = "edge_gesture_left_lower_width_dp"
    const val KEY_EDGE_LEFT_LOWER_DISTANCE_DP = "edge_gesture_left_lower_distance_dp"
    const val KEY_EDGE_RIGHT_LOWER_WIDTH_DP = "edge_gesture_right_lower_width_dp"
    const val KEY_EDGE_RIGHT_LOWER_DISTANCE_DP = "edge_gesture_right_lower_distance_dp"

    const val DEFAULT_EDGE_WIDTH_DP = 24
    const val DEFAULT_EDGE_UPPER_WIDTH_DP = 32
    const val DEFAULT_EDGE_LOWER_WIDTH_DP = 12
    const val MIN_EDGE_WIDTH_DP = 12
    const val MAX_EDGE_WIDTH_DP = 64
    const val DEFAULT_EDGE_TRIGGER_DISTANCE_DP = 80
    const val MIN_EDGE_TRIGGER_DISTANCE_DP = 48
    const val MAX_EDGE_TRIGGER_DISTANCE_DP = 200

    const val DEFAULT_EDGE_KEYBOARD_SAFETY_DISTANCE_PX = 150
    const val MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX = 0
    const val MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX = 1000
    const val DEFAULT_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX = 150
    const val MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX = 0
    const val MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX = 1000
    const val KEY_SEARCH_BAR_TOP_MARGIN_DP = "floating_search_bar_top_margin_dp"
    const val MIN_SEARCH_BAR_TOP_MARGIN_DP = 0
    const val MAX_SEARCH_BAR_TOP_MARGIN_DP = 1000
    const val DEFAULT_SEARCH_BAR_TOP_MARGIN_FALLBACK_DP = 40

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

    /**
     * 悬浮窗功能总开关 (默认关闭)
     */
    fun isFloatingMasterEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_FLOATING_MASTER_ENABLED, false)
    }

    fun setFloatingMasterEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_FLOATING_MASTER_ENABLED, enabled)
            .apply()
    }

    /**
     * 悬浮窗是否应挂载服务 (总开关开启且至少有一个子功能开启)
     */
    fun isAnyFloatingFeatureEnabled(context: Context): Boolean {
        if (!isFloatingMasterEnabled(context)) return false
        if (isBallEnabled(context)) return true
        if (isEdgeGestureEnabled(context) &&
            (isEdgeLeftEnabled(context) || isEdgeRightEnabled(context) ||
                isEdgeLeftLowerEnabled(context) || isEdgeRightLowerEnabled(context))
        ) return true
        return false
    }

    /**
     * 悬浮球独立开关 (默认关闭)
     */
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

    /**
     * 悬浮球实际生效判定
     */
    fun isBallActive(context: Context): Boolean =
        isFloatingMasterEnabled(context) && isBallEnabled(context)

    /**
     * 悬浮窗(边缘手势)独立开关 (默认关闭)
     */
    fun isEdgeGestureEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_EDGE_GESTURE_ENABLED, false)
    }

    fun setEdgeGestureEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_EDGE_GESTURE_ENABLED, enabled)
            .apply()
    }

    /**
     * 左侧悬浮窗独立开关 (默认关闭)
     */
    fun isEdgeLeftEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_EDGE_LEFT_ENABLED, false)
    }

    fun setEdgeLeftEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_EDGE_LEFT_ENABLED, enabled)
            .apply()
    }

    /**
     * 右侧悬浮窗独立开关 (默认关闭)
     */
    fun isEdgeRightEnabled(context: Context): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_EDGE_RIGHT_ENABLED, false)
    }

    fun setEdgeRightEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_EDGE_RIGHT_ENABLED, enabled)
            .apply()
    }

    fun isEdgeLeftLowerEnabled(context: Context): Boolean =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_EDGE_LEFT_LOWER_ENABLED, false)

    fun setEdgeLeftLowerEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_EDGE_LEFT_LOWER_ENABLED, enabled).apply()
    }

    fun isEdgeRightLowerEnabled(context: Context): Boolean =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_EDGE_RIGHT_LOWER_ENABLED, false)

    fun setEdgeRightLowerEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_EDGE_RIGHT_LOWER_ENABLED, enabled).apply()
    }

    /**
     * 边缘手势实际生效判定
     */
    fun isEdgeGestureActive(context: Context): Boolean =
        isFloatingMasterEnabled(context) && isEdgeGestureEnabled(context) &&
            (isEdgeLeftEnabled(context) || isEdgeRightEnabled(context) ||
                isEdgeLeftLowerEnabled(context) || isEdgeRightLowerEnabled(context))

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

    fun isShowEdgeRegionEnabled(context: Context): Boolean =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_SHOW_EDGE_REGION, false)

    fun setShowEdgeRegionEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_SHOW_EDGE_REGION, enabled).apply()
    }

    fun getLegacyEnableKey(actionKey: String): String = when (actionKey) {
        KEY_EDGE_LEFT_UP_ACTION -> KEY_EDGE_LEFT_UP_ENABLED
        KEY_EDGE_LEFT_DOWN_ACTION -> KEY_EDGE_LEFT_DOWN_ENABLED
        KEY_EDGE_LEFT_RIGHT_ACTION -> KEY_EDGE_LEFT_RIGHT_ENABLED
        KEY_EDGE_RIGHT_UP_ACTION -> KEY_EDGE_RIGHT_UP_ENABLED
        KEY_EDGE_RIGHT_DOWN_ACTION -> KEY_EDGE_RIGHT_DOWN_ENABLED
        KEY_EDGE_RIGHT_LEFT_ACTION -> KEY_EDGE_RIGHT_LEFT_ENABLED
        else -> actionKey
    }

    fun getActionKeyFromLegacy(legacyKey: String): String = when (legacyKey) {
        KEY_EDGE_LEFT_UP_ENABLED -> KEY_EDGE_LEFT_UP_ACTION
        KEY_EDGE_LEFT_DOWN_ENABLED -> KEY_EDGE_LEFT_DOWN_ACTION
        KEY_EDGE_LEFT_RIGHT_ENABLED -> KEY_EDGE_LEFT_RIGHT_ACTION
        KEY_EDGE_RIGHT_UP_ENABLED -> KEY_EDGE_RIGHT_UP_ACTION
        KEY_EDGE_RIGHT_DOWN_ENABLED -> KEY_EDGE_RIGHT_DOWN_ACTION
        KEY_EDGE_RIGHT_LEFT_ENABLED -> KEY_EDGE_RIGHT_LEFT_ACTION
        else -> legacyKey
    }

    fun getEdgeGestureAction(context: Context, actionKey: String): EdgeGestureAction {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        if (sp.contains(actionKey)) {
            val id = sp.getInt(actionKey, EdgeGestureAction.SWITCH_KEYBOARD.id)
            return EdgeGestureAction.fromId(id)
        }
        val legacyKey = getLegacyEnableKey(actionKey)
        val legacyEnabled = sp.getBoolean(legacyKey, true)
        return if (legacyEnabled) EdgeGestureAction.SWITCH_KEYBOARD else EdgeGestureAction.NONE
    }

    fun setEdgeGestureAction(context: Context, actionKey: String, action: EdgeGestureAction) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val legacyKey = getLegacyEnableKey(actionKey)
        sp.edit()
            .putInt(actionKey, action.id)
            .putBoolean(legacyKey, action != EdgeGestureAction.NONE)
            .apply()
    }

    fun getEdgeGestureAction(
        context: Context,
        side: EdgeGestureController.Side,
        direction: EdgeGestureController.Direction
    ): EdgeGestureAction {
        val actionKey = when (side) {
            EdgeGestureController.Side.LEFT -> when (direction) {
                EdgeGestureController.Direction.UP -> KEY_EDGE_LEFT_UP_ACTION
                EdgeGestureController.Direction.DOWN -> KEY_EDGE_LEFT_DOWN_ACTION
                EdgeGestureController.Direction.RIGHT,
                EdgeGestureController.Direction.LEFT -> KEY_EDGE_LEFT_RIGHT_ACTION
            }
            EdgeGestureController.Side.RIGHT -> when (direction) {
                EdgeGestureController.Direction.UP -> KEY_EDGE_RIGHT_UP_ACTION
                EdgeGestureController.Direction.DOWN -> KEY_EDGE_RIGHT_DOWN_ACTION
                EdgeGestureController.Direction.LEFT,
                EdgeGestureController.Direction.RIGHT -> KEY_EDGE_RIGHT_LEFT_ACTION
            }
        }
        return getEdgeGestureAction(context, actionKey)
    }

    fun isEdgeGestureDirectionEnabled(context: Context, key: String): Boolean {
        val actionKey = if (key.endsWith("_action")) key else getActionKeyFromLegacy(key)
        return getEdgeGestureAction(context, actionKey) != EdgeGestureAction.NONE
    }

    fun setEdgeGestureDirectionEnabled(context: Context, key: String, enabled: Boolean) {
        val actionKey = if (key.endsWith("_action")) key else getActionKeyFromLegacy(key)
        val action = if (enabled) EdgeGestureAction.SWITCH_KEYBOARD else EdgeGestureAction.NONE
        setEdgeGestureAction(context, actionKey, action)
    }

    fun getEdgeWidthDp(context: Context): Int =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_EDGE_WIDTH_DP, DEFAULT_EDGE_WIDTH_DP)
            .coerceIn(MIN_EDGE_WIDTH_DP, MAX_EDGE_WIDTH_DP)

    fun setEdgeWidthDp(context: Context, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_EDGE_WIDTH_DP, value.coerceIn(MIN_EDGE_WIDTH_DP, MAX_EDGE_WIDTH_DP)).apply()
    }

    fun getEdgeTriggerDistanceDp(context: Context): Int =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_EDGE_TRIGGER_DISTANCE_DP, DEFAULT_EDGE_TRIGGER_DISTANCE_DP)
            .coerceIn(MIN_EDGE_TRIGGER_DISTANCE_DP, MAX_EDGE_TRIGGER_DISTANCE_DP)

    fun setEdgeTriggerDistanceDp(context: Context, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putInt(
                KEY_EDGE_TRIGGER_DISTANCE_DP,
                value.coerceIn(MIN_EDGE_TRIGGER_DISTANCE_DP, MAX_EDGE_TRIGGER_DISTANCE_DP)
            ).apply()
    }

    fun getEdgeKeyboardSafetyDistancePx(context: Context): Int =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_EDGE_KEYBOARD_SAFETY_DISTANCE_PX, DEFAULT_EDGE_KEYBOARD_SAFETY_DISTANCE_PX)
            .coerceIn(MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX, MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX)

    fun setEdgeKeyboardSafetyDistancePx(context: Context, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putInt(
                KEY_EDGE_KEYBOARD_SAFETY_DISTANCE_PX,
                value.coerceIn(MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX, MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX)
            ).apply()
    }

    fun getEdgeLowerKeyboardSafetyDistancePx(context: Context): Int =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getInt(
                KEY_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX,
                DEFAULT_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
            )
            .coerceIn(
                MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX,
                MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
            )

    fun setEdgeLowerKeyboardSafetyDistancePx(context: Context, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putInt(
                KEY_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX,
                value.coerceIn(
                    MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX,
                    MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
                )
            ).apply()
    }

    fun getEdgeRegionWidthDp(context: Context, key: String): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val fallback = when (key) {
            KEY_EDGE_LEFT_UPPER_WIDTH_DP, KEY_EDGE_RIGHT_UPPER_WIDTH_DP ->
                DEFAULT_EDGE_UPPER_WIDTH_DP
            KEY_EDGE_LEFT_LOWER_WIDTH_DP, KEY_EDGE_RIGHT_LOWER_WIDTH_DP ->
                DEFAULT_EDGE_LOWER_WIDTH_DP
            else -> getEdgeWidthDp(context)
        }
        return sp.getInt(key, fallback).coerceIn(MIN_EDGE_WIDTH_DP, MAX_EDGE_WIDTH_DP)
    }

    fun setEdgeRegionWidthDp(context: Context, key: String, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
            .putInt(key, value.coerceIn(MIN_EDGE_WIDTH_DP, MAX_EDGE_WIDTH_DP)).apply()
    }

    fun getEdgeRegionTriggerDistanceDp(context: Context, key: String): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val fallback = getEdgeTriggerDistanceDp(context)
        return sp.getInt(key, fallback)
            .coerceIn(MIN_EDGE_TRIGGER_DISTANCE_DP, MAX_EDGE_TRIGGER_DISTANCE_DP)
    }

    fun setEdgeRegionTriggerDistanceDp(context: Context, key: String, value: Int) {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
            .putInt(
                key,
                value.coerceIn(MIN_EDGE_TRIGGER_DISTANCE_DP, MAX_EDGE_TRIGGER_DISTANCE_DP)
            ).apply()
    }

    fun getDefaultSearchBarTopMarginDp(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeightPx = if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
        val density = context.resources.displayMetrics.density
        val statusBarDp = if (density > 0f) (statusBarHeightPx / density).toInt() else 24
        return (statusBarDp + 6).coerceIn(MIN_SEARCH_BAR_TOP_MARGIN_DP, MAX_SEARCH_BAR_TOP_MARGIN_DP)
    }

    fun getSearchBarTopMarginDp(context: Context): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val defaultMargin = getDefaultSearchBarTopMarginDp(context)
        return sp.getInt(KEY_SEARCH_BAR_TOP_MARGIN_DP, defaultMargin)
            .coerceIn(MIN_SEARCH_BAR_TOP_MARGIN_DP, MAX_SEARCH_BAR_TOP_MARGIN_DP)
    }

    fun setSearchBarTopMarginDp(context: Context, valueDp: Int) {
        val clamped = valueDp.coerceIn(MIN_SEARCH_BAR_TOP_MARGIN_DP, MAX_SEARCH_BAR_TOP_MARGIN_DP)
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_SEARCH_BAR_TOP_MARGIN_DP, clamped)
            .apply()
    }
}
