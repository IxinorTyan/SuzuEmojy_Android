package com.suzu.test.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityWindowInfo
import android.view.inputmethod.InputMethodManager
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.floating.FloatingBallController
import com.suzu.test.ime.TestImageIME
import com.suzu.test.log.TestLog

class TestAccessibilityService : AccessibilityService() {

    companion object {
        private const val MODULE = "AccessibilityService"
        private const val SP_NAME = "test_poc"
        private const val KEY_PREV_IME = "previous_ime_id"

        @Volatile
        var instance: TestAccessibilityService? = null
            private set

        fun isAlive(): Boolean {
            return instance != null
        }
    }

    @Volatile
    var foregroundAppPackage: String? = null
        private set

    @Volatile
    var imeDetectionAvailable: Boolean = true
        private set

    @Volatile
    private var cachedDefaultImePackage: String? = null

    @Volatile
    private var lastImeVisible: Boolean = false

    private val IME_DIAG = false // 自测开关（已关闭诊断）
    private var lastDiagTime = 0L

    private fun logImeDiag(event: AccessibilityEvent) {
        if (!IME_DIAG) return
        // 只在窗口结构变化时诊断，忽略高频事件
        val t = event.eventType
        if (t != AccessibilityEvent.TYPE_WINDOWS_CHANGED &&
            t != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        // 节流：最快 500ms 一次
        val now = System.currentTimeMillis()
        if (now - lastDiagTime < 500) return
        lastDiagTime = now

        val winList = try { windows } catch (e: Exception) { null }
        val hasIme = winList?.any { it.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD } == true
        val winTypes = winList?.joinToString { it.type.toString() } ?: "null"
        TestLog.i("ImeDiag",
            "evt=$t srcPkg=${event.packageName} lastApp=$foregroundAppPackage | winCount=${winList?.size ?: -1} " +
            "winTypes=[$winTypes] hasImeWindow=$hasIme")
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private var ballController: FloatingBallController? = null
    private var ballConfigListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        refreshDefaultImePackage()
        observeBallConfig()
        syncBallState()
        syncImeStateFromWindows()
        TestLog.i(MODULE, "onServiceConnected: SuzuEmojy 辅助切换服务已就绪 (输入法快速切换 + IME/前台双信号分发)")
    }

    private fun observeBallConfig() {
        val sp = getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        ballConfigListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == FloatingBallConfig.KEY_BALL_ENABLED) {
                mainHandler.post { syncBallState() }
            }
        }
        sp.registerOnSharedPreferenceChangeListener(ballConfigListener)
    }

    fun syncBallState() {
        val enabled = FloatingBallConfig.isBallEnabled(this)
        val canDraw = Settings.canDrawOverlays(this)
        TestLog.i(MODULE, "syncBallState: enabled=$enabled, canDrawOverlays=$canDraw")
        if (enabled && canDraw) {
            if (ballController == null) {
                ballController = FloatingBallController(this)
            }
            ballController?.attach()
        } else {
            ballController?.detach()
            ballController = null
        }
    }

    private fun syncImeStateFromWindows() {
        val winList = try { windows } catch (e: Exception) { null }
        if (winList.isNullOrEmpty()) {
            imeDetectionAvailable = false
        } else {
            imeDetectionAvailable = true
            val visible = winList.any { it.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD }
            if (visible != lastImeVisible) {
                lastImeVisible = visible
                TestLog.i(MODULE, "IME 窗口可见性信号: visible=$visible")
                mainHandler.post {
                    ballController?.onImeVisibilityChanged(visible)
                }
            }

            if (foregroundAppPackage == null) {
                val focusedWin = winList.firstOrNull { it.isFocused || it.isActive }
                val rootNode = try { focusedWin?.root } catch (e: Exception) { null }
                try {
                    val pkg = rootNode?.packageName?.toString()
                    if (!pkg.isNullOrEmpty() && pkg != "com.android.systemui" && pkg != cachedDefaultImePackage) {
                        foregroundAppPackage = pkg
                        TestLog.i(MODULE, "初始化补齐前台应用包名: $pkg")
                        mainHandler.post {
                            ballController?.onForegroundAppChanged(pkg)
                        }
                    }
                } finally {
                    @Suppress("DEPRECATION")
                    rootNode?.recycle()
                }
            }
        }
    }

    fun isImeVisibleNow(): Boolean {
        val winList = try { windows } catch (e: Exception) { null }
        if (winList.isNullOrEmpty()) {
            imeDetectionAvailable = false
            return true // fail-open
        }
        imeDetectionAvailable = true
        return winList.any { it.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD }
    }

    private fun refreshDefaultImePackage() {
        try {
            val currentIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            cachedDefaultImePackage = currentIme?.substringBefore("/")
        } catch (e: Exception) {
            // ignore
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        // 信号一：前台应用包名（含自身 App，但排除 IME 与瞬态窗口）
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val pkg = event.packageName?.toString()
            val cls = event.className?.toString() ?: ""
            val isImePkg = !cachedDefaultImePackage.isNullOrEmpty() && pkg == cachedDefaultImePackage
            val isTransientOrImeCls = cls.contains("InputMethod", ignoreCase = true) ||
                    cls.contains("SoftInputWindow", ignoreCase = true) ||
                    cls.contains("PopupWindow", ignoreCase = true)

            if (!pkg.isNullOrEmpty() && pkg != "com.android.systemui" && !isImePkg && !isTransientOrImeCls) {
                if (pkg != foregroundAppPackage) {
                    foregroundAppPackage = pkg
                    TestLog.i(MODULE, "前台应用变更信号: $pkg")
                    mainHandler.post {
                        ballController?.onForegroundAppChanged(pkg)
                    }
                }
            }
        }

        // 信号二：IME 可见性（自家表情 IME 同样计入）
        if (event.eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED || event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            syncImeStateFromWindows()
        }

        logImeDiag(event)
    }

    override fun onInterrupt() {
        TestLog.i(MODULE, "onInterrupt: 服务被中断")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        TestLog.i(MODULE, "onUnbind: 服务解绑")
        instance = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        ballConfigListener?.let {
            getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(it)
        }
        ballConfigListener = null
        ballController?.detach()
        ballController = null
        instance = null
        TestLog.i(MODULE, "onDestroy: 服务销毁")
        super.onDestroy()
    }

    /**
     * 准确解析本 App 对应的真实 IME Component ID (如 com.tencent.qqpinyin.suzu/com.suzu.test.ime.TestImageIME)
     */
    fun findTestImeId(): String {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val ownIme = imm?.inputMethodList?.firstOrNull {
            it.serviceInfo.packageName == packageName &&
                    it.serviceInfo.name == TestImageIME::class.java.name
        }
        return ownIme?.id ?: ComponentName(packageName, TestImageIME::class.java.name).flattenToShortString()
    }

    fun isTestImeEnabled(imeId: String): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return false
        val enabledList = imm.enabledInputMethodList
        return enabledList.any { it.id == imeId }
    }

    /**
     * 静默切换到 TestImageIME
     */
    fun switchToTestIme(): Boolean {
        TestLog.i(MODULE, ">>> 开始执行 switchToTestIme")
        val currentIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        TestLog.i(MODULE, "当前系统 DEFAULT_INPUT_METHOD = $currentIme")

        val testImeId = findTestImeId()
        TestLog.i(MODULE, "解析出本App的真实 IME ID = $testImeId")

        if (currentIme != testImeId) {
            val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sp.edit().putString(KEY_PREV_IME, currentIme).apply()
            TestLog.i(MODULE, "记录原输入法 previous_ime_id = $currentIme 到 SharedPreferences")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!isTestImeEnabled(testImeId)) {
                softKeyboardController.setInputMethodEnabled(testImeId, true)
            }
        }

        var switchResult = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            switchResult = softKeyboardController.switchToInputMethod(testImeId)
            TestLog.i(MODULE, "softKeyboardController.switchToInputMethod 返回值 = $switchResult")
        } else {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showInputMethodPicker()
        }

        refreshDefaultImePackage()

        mainHandler.postDelayed({
            refreshDefaultImePackage()
            val verifyIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            TestLog.i(MODULE, "[切换验证+200ms] 实际生效 DEFAULT_INPUT_METHOD = $verifyIme")
        }, 200)

        return switchResult
    }

    /**
     * 静默恢复到原输入法 (previous_ime_id)
     */
    fun restorePreviousIme(): Boolean {
        TestLog.i(MODULE, "<<< 开始执行 restorePreviousIme")
        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val prevImeId = sp.getString(KEY_PREV_IME, null)

        if (prevImeId.isNullOrEmpty()) {
            TestLog.e(MODULE, "无法切回：SharedPreferences 中未找到 previous_ime_id")
            return false
        }

        val currentIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        TestLog.i(MODULE, "当前 DEFAULT_INPUT_METHOD = $currentIme, 准备恢复至 previous_ime_id = $prevImeId")

        var switchResult = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            switchResult = softKeyboardController.switchToInputMethod(prevImeId)
            TestLog.i(MODULE, "softKeyboardController.switchToInputMethod 返回值 = $switchResult")
        } else {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showInputMethodPicker()
        }

        refreshDefaultImePackage()

        mainHandler.postDelayed({
            refreshDefaultImePackage()
            val verifyIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            TestLog.i(MODULE, "[切回验证+200ms] 实际生效 DEFAULT_INPUT_METHOD = $verifyIme")
        }, 200)

        return switchResult
    }
}
