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

    fun isAccessibilityServiceRunning(): Boolean {
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
