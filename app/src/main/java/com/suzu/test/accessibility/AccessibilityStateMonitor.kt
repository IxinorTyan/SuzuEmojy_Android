package com.suzu.test.accessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.database.ContentObserver
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.suzu.test.log.TestLog
import com.suzu.test.util.PermissionChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AccessibilityStateMonitor {

    private const val MODULE = "A11yStateMonitor"

    private val _isEnabled = MutableStateFlow(false)
    val isEnabled: StateFlow<Boolean> = _isEnabled.asStateFlow()

    private var appContext: Context? = null
    private var isRegistered = false

    private val mainHandler = Handler(Looper.getMainLooper())

    private val contentObserver = object : ContentObserver(mainHandler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            refresh()
        }
    }

    private val a11yStateListener = AccessibilityManager.AccessibilityStateChangeListener {
        refresh()
    }

    fun init(context: Context) {
        if (isRegistered) return
        val app = context.applicationContext
        appContext = app

        // 同步计算真实状态作为初始值，禁止用 false 占位防止冷启动横幅闪现
        val initialRealState = PermissionChecker.isAccessibilityServiceEnabled(app)
        _isEnabled.value = initialRealState
        isRegistered = true

        try {
            // 1. ContentObserver 监听 Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES 跨版本最权威
            app.contentResolver.registerContentObserver(
                Settings.Secure.getUriFor(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES),
                false,
                contentObserver
            )
        } catch (e: Exception) {
            TestLog.e(MODULE, "注册 ContentObserver 异常: ${e.message}", e)
        }

        try {
            val am = app.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
            // 2. 全局无障碍开关监听
            am?.addAccessibilityStateChangeListener(a11yStateListener)

            // 3. API 33+ 服务级状态监听
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                am?.addAccessibilityServicesStateChangeListener {
                    refresh()
                }
            }
        } catch (e: Exception) {
            TestLog.e(MODULE, "注册 AccessibilityManager 监听异常: ${e.message}", e)
        }

        TestLog.i(MODULE, "AccessibilityStateMonitor 初始化完成, initialRealState=$initialRealState")
    }

    fun refresh() {
        val app = appContext ?: return
        val currentState = PermissionChecker.isAccessibilityServiceEnabled(app)
        if (_isEnabled.value != currentState) {
            TestLog.i(MODULE, "无障碍状态更新: ${_isEnabled.value} -> $currentState")
            _isEnabled.value = currentState
        }
    }
}
