package com.suzu.test.ime.config

import android.content.Context

/**
 * 保存“命中后使用系统分享选择器”的目标应用包名白名单。
 */
object ShareWhitelistConfig {
    private const val SP_NAME = "app_settings"
    private const val KEY_PACKAGES = "share_chooser_whitelist_packages"

    fun getPackages(context: Context): Set<String> =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getStringSet(KEY_PACKAGES, emptySet())
            .orEmpty()
            .mapNotNull { it.trim().takeIf(String::isNotEmpty) }
            .toSet()

    fun isWhitelisted(context: Context, packageName: String): Boolean =
        packageName in getPackages(context)

    fun setWhitelisted(context: Context, packageName: String, enabled: Boolean) {
        val normalized = packageName.trim()
        if (normalized.isEmpty()) return

        val packages = getPackages(context).toMutableSet()
        if (enabled) {
            packages.add(normalized)
        } else {
            packages.remove(normalized)
        }

        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putStringSet(KEY_PACKAGES, packages)
            .apply()
    }
}
