package com.suzu.test.util

import android.content.ComponentName
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
     * 获取对应厂商的主组件及备用组件 ComponentName 列表
     */
    private fun getComponentCandidates(): List<ComponentName> {
        val manufacturer = Build.MANUFACTURER?.uppercase() ?: ""
        val list = mutableListOf<ComponentName>()

        when {
            // 小米 / Redmi
            manufacturer.contains("XIAOMI") || manufacturer.contains("REDMI") -> {
                // 主组件：小米权限/关联启动管理页
                list.add(ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity"))
                list.add(ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"))
                // 备用组件
                list.add(ComponentName("com.miui.securitycenter", "com.miui.securityscan.MainActivity"))
            }

            // 华为 / 荣耀
            manufacturer.contains("HUAWEI") || manufacturer.contains("HONOR") -> {
                // 华为自启动/关联启动/应用启动管理
                list.add(ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"))
                list.add(ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"))
                list.add(ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"))
                // 荣耀 MagicOS 专属
                list.add(ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity"))
                list.add(ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.optimize.bootstart.BootStartActivity"))
            }

            // OPPO / 一加 / realme (ColorOS 系列)
            manufacturer.contains("OPPO") || manufacturer.contains("ONEPLUS") || manufacturer.contains("REALME") -> {
                // 主组件：自启动与关联启动
                list.add(ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"))
                list.add(ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity"))
                // 备用组件
                list.add(ComponentName("com.oplus.safecenter", "com.oplus.safecenter.permission.startup.StartupAppListActivity"))
                list.add(ComponentName("com.oplus.safecenter", "com.oplus.safecenter.startupapp.StartupAppListActivity"))
                list.add(ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionTopActivity"))
            }

            // vivo / iQOO (FuntouchOS / OriginOS)
            manufacturer.contains("VIVO") || manufacturer.contains("IQOO") -> {
                // 主组件：后台高耗电与关联启动
                list.add(ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"))
                list.add(ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"))
                // 备用组件
                list.add(ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"))
                list.add(ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity"))
            }
        }

        return list
    }

    /**
     * 执行三级回退跳转：
     * 1. 厂商专属页 (显式 ComponentName)
     * 2. ACTION_APPLICATION_DETAILS_SETTINGS (package uri)
     * 3. 失败时返回 false (由外部在行内展示文字指引)
     */
    fun openAutoStartSettings(context: Context): Boolean {
        // 第一级：尝试厂商专属显式 Intent
        val candidates = getComponentCandidates()
        for (component in candidates) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                    TestLog.i(MODULE, "成功跳转厂商自启动页: $component")
                    return true
                }
            } catch (e: Exception) {
                TestLog.w(MODULE, "尝试跳转 $component 失败: ${e.message}")
            }
        }

        // 第二级：回退到应用详情页
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            TestLog.i(MODULE, "回退到系统应用详情页")
            return true
        } catch (e: Exception) {
            TestLog.e(MODULE, "跳转应用详情页失败: ${e.message}", e)
        }

        // 第三级：全部失败
        return false
    }
}
