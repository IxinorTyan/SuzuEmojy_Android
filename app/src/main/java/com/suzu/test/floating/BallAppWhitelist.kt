package com.suzu.test.floating

import android.content.Context

/** Separate keys from the retired app filter, which older versions explicitly delete. */
object BallAppWhitelist {
    const val KEY_ENABLED = "ball_app_whitelist_v1_enabled"
    const val KEY_PACKAGES = "ball_app_whitelist_v1_packages"

    fun isEnabled(context: Context): Boolean = preferences(context).getBoolean(KEY_ENABLED, false)

    fun packages(context: Context): Set<String> =
        preferences(context).getStringSet(KEY_PACKAGES, emptySet()).orEmpty() + context.packageName

    fun setEnabled(context: Context, enabled: Boolean) {
        preferences(context).edit().putBoolean(KEY_ENABLED, enabled).apply()
    }

    fun savePackages(context: Context, packages: Set<String>) {
        preferences(context).edit().putStringSet(KEY_PACKAGES, packages + context.packageName).apply()
    }

    private fun preferences(context: Context) =
        context.getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
}

/** Unknown foreground never grants access. Existing visibility rules run after this gate. */
internal object BallAppAccessPolicy {
    fun allows(enabled: Boolean, packages: Set<String>, foreground: String?): Boolean =
        !enabled || (!foreground.isNullOrBlank() && foreground in packages)
}
