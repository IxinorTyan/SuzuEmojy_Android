package com.suzu.test.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.suzu.test.log.TestLog

object AutoStartHelper {

    private const val MODULE = "AutoStartHelper"

    /**
     * 判断当前机型/厂商是否支持自启动设置跳转（若为原生/Pixel/三星等无自启动概念的厂商，返回 false 以便隐藏整行）
     */
    fun isAutoStartSupported(): Boolean {
        val manufacturer = Build.MANUFACTURER?.uppercase() ?: ""
        return when {
            manufacturer.contains("XIAOMI") || manufacturer.contains("REDMI") -> true
            manufacturer.contains("HUAWEI") || manufacturer.contains("HONOR") -> true
            manufacturer.contains("OPPO") || manufacturer.contains("ONEPLUS") || manufacturer.contains("REALME") -> true
            manufacturer.contains("VIVO") || manufacturer.contains("IQOO") -> true
            else -> false
        }
    }

    /**
     * 打开当前应用的系统详情页。
     *
     * 不再跳转厂商私有的自启动/关联启动页面，避免不同系统版本或机型
     * 的组件变化导致进入错误页面。
     */
    fun openAutoStartSettings(context: Context): Boolean {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            TestLog.i(MODULE, "成功跳转当前应用详情页: ${context.packageName}")
            return true
        } catch (e: Exception) {
            TestLog.e(MODULE, "跳转当前应用详情页失败: ${e.message}", e)
        }

        // 第三级：全部失败
        return false
    }
}
