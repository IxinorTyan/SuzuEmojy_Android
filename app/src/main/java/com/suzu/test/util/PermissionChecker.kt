package com.suzu.test.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.suzu.test.accessibility.TestAccessibilityService

object PermissionChecker {

    fun isImeEnabled(context: Context): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val enabledImes = imm.enabledInputMethodList
        return enabledImes.any { it.packageName == context.packageName }
    }

    @Deprecated("Use AccessibilityStateMonitor or isAccessibilityServiceEnabled(context)", ReplaceWith("isAccessibilityServiceEnabled(context)"))
    fun isAccessibilityServiceRunning(): Boolean {
        return TestAccessibilityService.isAlive()
    }

    fun isAccessibilityServiceConnected(): Boolean {
        return TestAccessibilityService.isAlive()
    }

    fun isAccessibilityServiceEnabled(context: Context): Boolean {
        val targetComponent = android.content.ComponentName(context, TestAccessibilityService::class.java)

        // 1. Settings.Secure 校验 (跨厂商/跨版本最稳健权威)
        try {
            val settingValue = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (!settingValue.isNullOrEmpty()) {
                val splitter = android.text.TextUtils.SimpleStringSplitter(':')
                splitter.setString(settingValue)
                while (splitter.hasNext()) {
                    val componentStr = splitter.next()
                    val enabledComponent = android.content.ComponentName.unflattenFromString(componentStr)
                    if (enabledComponent != null && enabledComponent == targetComponent) {
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            // fallback
        }

        // 2. AccessibilityManager 校验 (比较完整 ComponentName)
        try {
            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? android.view.accessibility.AccessibilityManager
            val enabledServices = am?.getEnabledAccessibilityServiceList(android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            if (enabledServices?.any { info ->
                    val si = info.resolveInfo?.serviceInfo
                    si != null && android.content.ComponentName(si.packageName, si.name) == targetComponent
                } == true) {
                return true
            }
        } catch (e: Exception) {
            // fallback
        }

        // 3. 内存 instance 兜底
        return TestAccessibilityService.isAlive()
    }

    fun hasOverlayPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun hasStoragePermission(context: Context): Boolean {
        val perm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }

    fun getStoragePermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
