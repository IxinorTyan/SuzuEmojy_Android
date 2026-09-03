package com.suzu.test.ime.diag

import android.content.Context

/**
 * Debug-only switches used to reproduce image-send compatibility failures.
 *
 * The value is persisted so it remains enabled when the IME view is recreated.
 */
object DebugSendTestConfig {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_SKIP_PROVIDER_DIAGNOSTIC_AND_CLEANUP =
        "debug_skip_provider_diagnostic_and_cleanup"

    fun isSkipProviderDiagnosticAndCleanupEnabled(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_SKIP_PROVIDER_DIAGNOSTIC_AND_CLEANUP, false)

    fun setSkipProviderDiagnosticAndCleanupEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SKIP_PROVIDER_DIAGNOSTIC_AND_CLEANUP, enabled)
            .apply()
    }
}
