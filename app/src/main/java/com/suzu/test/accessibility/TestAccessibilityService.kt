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
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import android.view.inputmethod.InputMethodManager
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.floating.FloatingBallController
import com.suzu.test.floating.BallAppWhitelist
import com.suzu.test.ime.TestImageIME
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.SystemClock

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

        /**
         * 由自家 IME 生命周期回调提供更及时的窗口状态。
         * 收起后短时间内忽略无障碍窗口列表中的残留 IME 窗口。
         */
        fun notifyImeLifecycle(visible: Boolean) {
            instance?.onImeLifecycleChanged(visible)
            com.suzu.test.floating.ImeVisibilityBus.notifyImeVisibilityChanged(visible)
        }
    }

    @Volatile
    var foregroundAppPackage: String? = null
        private set

    @Volatile
    var imeDetectionAvailable: Boolean = true
        private set

    private var lastImeVisible: Boolean? = null

    // 自家 IME 已明确隐藏后的短暂保护期，避免无障碍窗口列表残留立即覆盖为 true
    @Volatile
    private var imeLifecycleHiddenUntil: Long = 0L

    @Volatile
    private var cachedDefaultImePackage: String? = null

    private fun onImeLifecycleChanged(visible: Boolean) {
        windowEpoch++
        requestWindowStateSync()
        imeLifecycleHiddenUntil = if (visible) {
            0L
        } else {
            SystemClock.uptimeMillis() + 200L
        }
        lastImeVisible = visible
        mainHandler.post {
            if (visible && TestImageIME.isShowing()) ballController?.onSelfImeShown()
            ballController?.onImeVisibilityChanged(visible)
        }
    }

    private val IME_DIAG = false
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
    private val windowScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var windowEpoch = 0L
    private var windowScanBusy = false
    private var windowScanDirty = false
    private var previousVerifiedHost: String? = null
    private var ballController: FloatingBallController? = null

    // Independent from the legacy foreground hint used by IME and edge gestures.
    var verifiedBallForeground: String? = null
        private set
    private var ballForegroundRetry = 0
    private var windowStateSyncPosted = false
    private val windowStateSyncRunnable = Runnable {
        windowStateSyncPosted = false
        scanWindowState()
    }
    private var screenReceiverRegistered = false
    private val ballForegroundRecheck = Runnable { requestWindowStateSync() }
    private val ballScreenReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                windowEpoch++
                previousVerifiedHost = null
                mainHandler.removeCallbacks(ballForegroundRecheck)
                publishBallForeground(null)
            } else {
                requestBallForegroundRefresh()
            }
        }
    }

    private fun publishBallForeground(pkg: String?) {
        if (verifiedBallForeground == pkg) return
        verifiedBallForeground = pkg
        TestLog.i(MODULE, "悬浮球白名单窗口核验: ${pkg ?: "未知或受限窗口"}")
        ballController?.onVerifiedForegroundChanged()
    }

    private fun requestBallForegroundRefresh() {
        mainHandler.removeCallbacks(ballForegroundRecheck)
        ballForegroundRetry = 0
        windowEpoch++
        previousVerifiedHost = verifiedBallForeground ?: previousVerifiedHost
        publishBallForeground(null)
        requestWindowStateSync()
    }

    /** One in-flight scan and one coalesced follow-up; never queue one scan per event. */
    private fun requestWindowStateSync() {
        windowScanDirty = true
        if (windowScanBusy) return
        if (windowStateSyncPosted) return
        windowStateSyncPosted = true
        mainHandler.postDelayed(windowStateSyncRunnable, 50L)
    }

    private data class WindowSnapshot(
        val id: Int, val type: Int, val bounds: android.graphics.Rect,
        val focused: Boolean, val active: Boolean, val pkg: String?
    )

    private fun screenUnlocked(): Boolean =
        getSystemService(android.os.PowerManager::class.java).isInteractive &&
            !getSystemService(android.app.KeyguardManager::class.java).isKeyguardLocked

    private fun scanWindowState() {
        if (windowScanBusy || instance !== this) return
        windowScanBusy = true
        windowScanDirty = false
        val epoch = windowEpoch
        val whitelist = BallAppWhitelist.isEnabled(this)
        val needsPackage = whitelist || foregroundAppPackage == null
        val started = SystemClock.uptimeMillis()
        windowScope.launch {
            try {
                // A root query can wait for another ViewRoot on this very main thread.
                // Keep every AccessibilityNodeInfo on the worker; publish values only.
                val snapshot = withContext(Dispatchers.IO) {
                    try {
                        windows.map { window ->
                            val rect = android.graphics.Rect().also { window.getBoundsInScreen(it) }
                            val pkg = if (needsPackage && !rect.isEmpty &&
                                window.type != AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                                val root = window.root
                                try { root?.packageName?.toString() } finally { root?.recycle() }
                            } else null
                            WindowSnapshot(window.id, window.type, rect, window.isFocused, window.isActive, pkg)
                        }
                    } catch (e: Exception) {
                        TestLog.w(MODULE, "窗口快照读取失败: ${e.message}")
                        emptyList()
                    }
                }
                if (epoch != windowEpoch || instance !== this@TestAccessibilityService) return@launch
                applyWindowSnapshot(snapshot, whitelist)
            } finally {
                windowScanBusy = false
                val elapsed = SystemClock.uptimeMillis() - started
                if (elapsed >= 100L) TestLog.w(MODULE, "窗口快照耗时=${elapsed}ms epoch=$epoch pid=${android.os.Process.myPid()}")
                if (windowScanDirty && instance === this@TestAccessibilityService) requestWindowStateSync()
            }
        }
    }

    private fun applyWindowSnapshot(snapshot: List<WindowSnapshot>, whitelist: Boolean) {
        if (whitelist && screenUnlocked()) {
            val balls = snapshot.filter { !it.bounds.isEmpty }.map { window ->
                val kind = when (window.type) {
                    AccessibilityWindowInfo.TYPE_APPLICATION -> BallWindowSnapshot.Kind.APPLICATION
                    AccessibilityWindowInfo.TYPE_INPUT_METHOD -> BallWindowSnapshot.Kind.IME
                    else -> if (window.pkg == packageName) BallWindowSnapshot.Kind.OWN_OVERLAY
                        else BallWindowSnapshot.Kind.OTHER
                }
                BallWindowSnapshot(kind, window.pkg, window.focused, window.active)
            }
            val host = BallForegroundResolver.resolve(balls, previousVerifiedHost)
            previousVerifiedHost = host
            publishBallForeground(host)
            if (host == null && ballForegroundRetry < 2) {
                ballForegroundRetry++
                mainHandler.removeCallbacks(ballForegroundRecheck)
                mainHandler.postDelayed(ballForegroundRecheck, 120L * ballForegroundRetry)
            }
        } else {
            previousVerifiedHost = null
            publishBallForeground(null)
        }
        imeDetectionAvailable = snapshot.isNotEmpty()
        if (snapshot.isEmpty()) return
        val screenHeight = getScreenHeight()
        val imeTop = snapshot.filter {
            it.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD && isImeWindowValid(it.bounds, screenHeight)
        }.minOfOrNull { it.bounds.top }
        val visible = imeTop != null && SystemClock.uptimeMillis() >= imeLifecycleHiddenUntil
        if (lastImeVisible != visible) {
            lastImeVisible = visible
            ballController?.onImeVisibilityChanged(visible)
        }
        ballController?.onImeBoundsChanged(if (visible) imeTop else null)
        if (foregroundAppPackage == null) {
            val pkg = snapshot.firstOrNull { it.focused || it.active }?.pkg
            if (!pkg.isNullOrEmpty() && pkg != "com.android.systemui") {
                foregroundAppPackage = pkg
                ballController?.onForegroundAppChanged(pkg)
            }
        }
    }

    private data class PendingShareCard(
        val targetPackage: String,
        val expiresAt: Long
    )

    @Volatile
    private var pendingShareCard: PendingShareCard? = null

    private val shareCardTimeoutRunnable = Runnable {
        pendingShareCard = null
    }
    private var shareCardRetryCount = 0
    private val shareCardRetryRunnable = object : Runnable {
        override fun run() {
            if (pendingShareCard == null) return
            if (trySelectPendingShareCard()) return
            if (++shareCardRetryCount < 24) {
                mainHandler.postDelayed(this, 350L)
            } else {
                pendingShareCard = null
                TestLog.w(MODULE, "分享卡片自动选择超时：未找到“发送给好友”入口")
            }
        }
    }
    private var ballConfigListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        verifiedBallForeground = null
        val screenFilter = android.content.IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        if (!screenReceiverRegistered) {
            androidx.core.content.ContextCompat.registerReceiver(
                this, ballScreenReceiver, screenFilter,
                androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
            )
            screenReceiverRegistered = true
        }
        refreshDefaultImePackage()
        observeBallConfig()
        syncBallState()
        requestWindowStateSync()
        requestBallForegroundRefresh()
        TestLog.i(MODULE, "onServiceConnected: pid=${android.os.Process.myPid()} uptime=${SystemClock.uptimeMillis()}")
    }

    private fun observeBallConfig() {
        val sp = getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        ballConfigListener?.let { sp.unregisterOnSharedPreferenceChangeListener(it) }
        ballConfigListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == BallAppWhitelist.KEY_ENABLED || key == BallAppWhitelist.KEY_PACKAGES) {
                requestBallForegroundRefresh()
            }
            if (key == FloatingBallConfig.KEY_FLOATING_MASTER_ENABLED ||
                key == FloatingBallConfig.KEY_BALL_ENABLED ||
                key == FloatingBallConfig.KEY_EDGE_GESTURE_ENABLED ||
                key == FloatingBallConfig.KEY_EDGE_LEFT_ENABLED ||
                key == FloatingBallConfig.KEY_EDGE_LEFT_LOWER_ENABLED ||
                key == FloatingBallConfig.KEY_EDGE_RIGHT_ENABLED ||
                key == FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_ENABLED
            ) {
                mainHandler.post { syncBallState() }
            }
        }
        sp.registerOnSharedPreferenceChangeListener(ballConfigListener)
    }

    fun syncBallState() {
        val enabled = FloatingBallConfig.isAnyFloatingFeatureEnabled(this)
        val canDraw = Settings.canDrawOverlays(this)
        TestLog.i(MODULE, "syncBallState: anyFloatingEnabled=$enabled, canDrawOverlays=$canDraw")
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

    fun showSearchBar() {
        ballController?.showSearchBar()
    }

    private fun getScreenHeight(): Int {
        val wm = getSystemService(Context.WINDOW_SERVICE) as? android.view.WindowManager ?: return 0
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            wm.currentWindowMetrics.bounds.height()
        } else {
            @Suppress("DEPRECATION")
            wm.defaultDisplay.height
        }
    }

    private fun isImeWindowValid(rect: android.graphics.Rect, screenHeight: Int): Boolean {
        if (rect.isEmpty) return false
        if (rect.height() <= 100 || rect.width() <= 100) return false
        if (screenHeight > 0) {
            val visibleTop = maxOf(rect.top, 0)
            val visibleBottom = minOf(rect.bottom, screenHeight)
            val visibleHeight = visibleBottom - visibleTop
            if (visibleHeight <= 100) return false
        }
        return true
    }

    private fun refreshDefaultImePackage() {
        try {
            val currentIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            cachedDefaultImePackage = currentIme?.substringBefore("/")
        } catch (e: Exception) {
            // ignore
        }
    }

    /**
     * 登记一次通道 B 的卡片自动选择。
     *
     * 只在短时间内生效，并且只匹配当前目标应用的窗口；完成一次点击后立即失效，
     * 防止服务对用户之后正常看到的同名按钮产生误触。
     */
    fun armShareCardAutomation(targetPackage: String) {
        if (targetPackage.isBlank()) return
        pendingShareCard = PendingShareCard(
            targetPackage = targetPackage,
            expiresAt = System.currentTimeMillis() + 8_000L
        )
        shareCardRetryCount = 0
        mainHandler.removeCallbacks(shareCardTimeoutRunnable)
        mainHandler.removeCallbacks(shareCardRetryRunnable)
        mainHandler.postDelayed(shareCardTimeoutRunnable, 8_000L)
        mainHandler.post(shareCardRetryRunnable)
        TestLog.i(MODULE, "已登记通道B卡片自动选择: targetPackage=$targetPackage")
    }

    private fun tryAutoSelectShareCard(event: AccessibilityEvent) {
        val pending = pendingShareCard ?: return
        if (event.packageName?.toString() != pending.targetPackage) return
        trySelectPendingShareCard()
    }

    private fun trySelectPendingShareCard(): Boolean {
        val pending = pendingShareCard ?: return false
        if (System.currentTimeMillis() >= pending.expiresAt) {
            pendingShareCard = null
            return false
        }

        val root = try { rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val labels = when (pending.targetPackage) {
            "com.tencent.mm" -> setOf("发送给朋友", "Send to Friends")
            else -> setOf(
                "发送给好友", "发送给朋友", "分享给好友", "发给好友",
                "Send to Friends", "Send to a Friend"
            )
        }

        val candidate = findShareCardNode(root, labels) ?: return false
        if (!clickNodeOrClickableParent(candidate)) return false

        pendingShareCard = null
        mainHandler.removeCallbacks(shareCardTimeoutRunnable)
        mainHandler.removeCallbacks(shareCardRetryRunnable)
        TestLog.i(
            MODULE,
            "已自动选择通道B发送卡片: package=${pending.targetPackage}, text=${candidate.text}"
        )
        return true
    }

    private fun findShareCardNode(
        root: AccessibilityNodeInfo,
        labels: Set<String>
    ): AccessibilityNodeInfo? {
        for (label in labels) {
            val nodes = try { root.findAccessibilityNodeInfosByText(label) } catch (_: Exception) { emptyList() }
            val exact = nodes.firstOrNull {
                it.text?.toString()?.trim() == label ||
                    it.contentDescription?.toString()?.trim() == label
            }
            if (exact != null) return exact
        }
        return null
    }

    private fun clickNodeOrClickableParent(node: AccessibilityNodeInfo): Boolean {
        var current: AccessibilityNodeInfo? = node
        repeat(6) {
            val candidate = current ?: return@repeat
            if (candidate.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true
            }
            current = candidate.parent
        }
        return false
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        tryAutoSelectShareCard(event)

        // 信号一：前台应用包名（含自身 App，但排除 IME 与瞬态窗口）
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val pkg = event.packageName?.toString()
            val cls = event.className?.toString() ?: ""
            val isTransientOrImeCls = cls.contains("InputMethod", ignoreCase = true) ||
                    cls.contains("SoftInputWindow", ignoreCase = true) ||
                    cls.contains("PopupWindow", ignoreCase = true) ||
                    cls == TestImageIME::class.java.name

            if (!pkg.isNullOrEmpty() && pkg != "com.android.systemui" && !isTransientOrImeCls) {
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
            windowEpoch++
            // Our own show/hide also emits window events. Keep the last verified
            // result until the next snapshot, or showing the ball hides it again.
            requestWindowStateSync()
        }

        logImeDiag(event)
    }

    override fun onInterrupt() {
        cancelImeSwitch("服务停止")
        pendingShareCard = null
        mainHandler.removeCallbacks(shareCardTimeoutRunnable)
        mainHandler.removeCallbacks(shareCardRetryRunnable)
        TestLog.i(MODULE, "onInterrupt: 服务被中断")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        windowEpoch++
        windowScope.coroutineContext.cancelChildren()
        mainHandler.removeCallbacks(ballForegroundRecheck)
        mainHandler.removeCallbacks(windowStateSyncRunnable)
        windowStateSyncPosted = false
        publishBallForeground(null)
        ballController?.detach()
        ballController = null
        ballConfigListener?.let {
            getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(it)
        }
        ballConfigListener = null
        if (screenReceiverRegistered) {
            unregisterReceiver(ballScreenReceiver)
            screenReceiverRegistered = false
        }
        cancelImeSwitch("服务停止")
        TestLog.i(MODULE, "onUnbind: 服务解绑")
        instance = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        windowEpoch++
        windowScope.cancel()
        mainHandler.removeCallbacks(ballForegroundRecheck)
        mainHandler.removeCallbacks(windowStateSyncRunnable)
        windowStateSyncPosted = false
        if (screenReceiverRegistered) {
            unregisterReceiver(ballScreenReceiver)
            screenReceiverRegistered = false
        }
        verifiedBallForeground = null
        cancelImeSwitch("服务停止")
        ballConfigListener?.let {
            getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(it)
        }
        ballConfigListener = null
        ballController?.detach()
        ballController = null
        pendingShareCard = null
        mainHandler.removeCallbacks(shareCardTimeoutRunnable)
        mainHandler.removeCallbacks(shareCardRetryRunnable)
        instance = null
        TestLog.i(MODULE, "onDestroy: 服务销毁")
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        TestLog.i(MODULE, "onConfigurationChanged: 屏幕配置/方向改变，重新校准悬浮球位置")
        ballController?.onScreenConfigurationChanged()
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

    fun isSelfIme(imeId: String?): Boolean {
        if (imeId.isNullOrEmpty()) return false
        val fullId = packageName + "/" + TestImageIME::class.java.name
        val shortId = ComponentName(packageName, TestImageIME::class.java.name).flattenToShortString()
        return imeId == fullId || imeId == shortId || imeId.startsWith("$packageName/")
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

        if (!currentIme.isNullOrEmpty() && !isSelfIme(currentIme)) {
            val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sp.edit().putString(KEY_PREV_IME, currentIme).apply()
            TestLog.i(MODULE, "记录原输入法 previous_ime_id = $currentIme 到 SharedPreferences")
        } else if (isSelfIme(currentIme)) {
            TestLog.i(MODULE, "当前系统默认输入法已是自研 IME ($currentIme)，保留已有 previous_ime_id，跳过覆盖")
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

    data class InputTarget(val windowId: Int, val packageName: String)

    private var switchAttempt: com.suzu.test.ime.ImeSwitchAttempt? = null
    private var switchJob: Job? = null
    private var switchTimeout: Runnable? = null

    fun captureInputTarget(): InputTarget? {
        val targetWindow = windows.firstOrNull {
            it.type == AccessibilityWindowInfo.TYPE_APPLICATION && it.isFocused
        } ?: return null
        val root = targetWindow.root ?: return null
        try {
            val pkg = root.packageName?.toString() ?: return null
            return InputTarget(targetWindow.id, pkg)
        } finally { root.recycle() }
    }

    fun isImeSwitchPending(): Boolean = switchAttempt != null

    fun cancelImeSwitch(reason: String) {
        switchTimeout?.let { mainHandler.removeCallbacks(it) }
        switchTimeout = null
        switchAttempt?.cancel()
        switchAttempt = null
        switchJob?.cancel()
        switchJob = null
        TestLog.i(MODULE, "结束 IME 显示请求: $reason")
    }

    /** Search passes the chat window captured before the overlay took focus. */
    fun switchToTestImeAndEnsureShown(target: InputTarget?): Boolean {
        cancelImeSwitch("新请求")
        if (target == null) {
            com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
            TestLog.w(MODULE, "无法恢复宿主输入焦点：缺少目标窗口")
            return false
        }
        val attempt = com.suzu.test.ime.ImeSwitchAttempt(SystemClock.uptimeMillis())
        switchAttempt = attempt
        val timeout = Runnable {
            if (switchAttempt === attempt) {
                cancelImeSwitch("IME 交接超过 2500ms")
                com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
            }
        }
        switchTimeout = timeout
        mainHandler.postDelayed(timeout, 2500L)
        switchJob = windowScope.launch {
            try {
                val initialIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
                val switched = withContext(Dispatchers.IO) { switchToTestIme() }
                if (!switched) {
                    com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
                    return@launch
                }
                var ownObserved = false
                while (switchAttempt === attempt && !attempt.cancelled && !attempt.expired(SystemClock.uptimeMillis())) {
                    val current = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
                    val own = isSelfIme(current)
                    if (!own && (ownObserved || current != initialIme)) {
                        com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
                        return@launch
                    }
                    if (own) ownObserved = true
                    val ready = withContext(Dispatchers.IO) { targetEditorReady(target) }
                    if (switchAttempt !== attempt || attempt.cancelled) return@launch
                    val now = SystemClock.uptimeMillis()
                    val visible = own && ready && TestImageIME.instance?.isShowingFor(target.packageName) == true
                    if (attempt.stable(now, visible)) return@launch
                    if (attempt.takeClick(now, own && ready, visible)) {
                        withContext(Dispatchers.IO) {
                            // Re-check focus on the worker just before touching the editor.
                            if (!attempt.cancelled) targetEditorReady(target, attempt)
                        }
                    }
                    kotlinx.coroutines.delay(50L)
                }
                TestLog.w(MODULE, "IME 交接超时: target=$target elapsed=${SystemClock.uptimeMillis() - attempt.startedAt}ms")
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                TestLog.e(MODULE, "IME 交接失败", e)
                com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
            } finally {
                if (switchAttempt === attempt) {
                    mainHandler.removeCallbacks(timeout)
                    switchTimeout = null
                    attempt.cancel()
                    switchAttempt = null
                    switchJob = null
                    com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
                }
            }
        }
        return true
    }

    private fun targetEditorReady(target: InputTarget, clickAttempt: com.suzu.test.ime.ImeSwitchAttempt? = null): Boolean {
        val window = windows.firstOrNull { it.isFocused && it.id == target.windowId } ?: return false
        val root = window.root ?: return false
        var editor: AccessibilityNodeInfo? = null
        try {
            if (root.packageName?.toString() != target.packageName) return false
            editor = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
            val ready = editor?.isEditable == true && editor?.isVisibleToUser == true
            if (ready && clickAttempt != null && !clickAttempt.cancelled &&
                !clickAttempt.expired(SystemClock.uptimeMillis()) &&
                isSelfIme(Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD))) {
                editor?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            return ready
        } finally {
            if (editor !== root) editor?.recycle()
            root.recycle()
        }
    }

    /**
     * 静默恢复到原输入法 (previous_ime_id)
     */
    fun restorePreviousIme(): Boolean {
        cancelImeSwitch("恢复原输入法")
        TestLog.i(MODULE, "<<< 开始执行 restorePreviousIme")
        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val prevImeId = sp.getString(KEY_PREV_IME, null)

        if (prevImeId.isNullOrEmpty() || isSelfIme(prevImeId)) {
            TestLog.e(MODULE, "无法切回：SharedPreferences 中未找到有效的 previous_ime_id (prevImeId=$prevImeId)")
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

    /** Called on the search worker, before the overlay takes focus. */
    fun requestTextImeForSearch(): Boolean {
        val previous = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(KEY_PREV_IME, null)
        if (previous.isNullOrEmpty() || isSelfIme(previous) || Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return false
        return softKeyboardController.switchToInputMethod(previous)
    }
}
