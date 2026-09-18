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
        imeLifecycleHiddenUntil = if (visible) {
            0L
        } else {
            System.currentTimeMillis() + 200L
        }
        lastImeVisible = visible
        mainHandler.post {
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
    private var ballController: FloatingBallController? = null

    // Independent from the legacy foreground hint used by IME and edge gestures.
    var verifiedBallForeground: String? = null
        private set
    private var ballForegroundRetry = 0
    private var screenReceiverRegistered = false
    private val ballForegroundRecheck = Runnable { refreshBallForeground() }
    private val ballScreenReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_OFF) {
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
        refreshBallForeground()
    }

    private fun refreshBallForeground() {
        if (!BallAppWhitelist.isEnabled(this)) {
            publishBallForeground(null)
            return
        }
        val power = getSystemService(android.os.PowerManager::class.java)
        val keyguard = getSystemService(android.app.KeyguardManager::class.java)
        if (!power.isInteractive || keyguard.isKeyguardLocked) {
            publishBallForeground(null)
            return
        }
        val snapshots = try {
            windows.mapNotNull { window ->
                val bounds = android.graphics.Rect()
                window.getBoundsInScreen(bounds)
                if (bounds.isEmpty) return@mapNotNull null
                val root = window.root
                val pkg = try { root?.packageName?.toString() } finally {
                    @Suppress("DEPRECATION")
                    root?.recycle()
                }
                val kind = when (window.type) {
                    AccessibilityWindowInfo.TYPE_APPLICATION -> BallWindowSnapshot.Kind.APPLICATION
                    AccessibilityWindowInfo.TYPE_INPUT_METHOD -> BallWindowSnapshot.Kind.IME
                    else -> if (pkg == packageName) BallWindowSnapshot.Kind.OWN_OVERLAY
                        else BallWindowSnapshot.Kind.OTHER
                }
                BallWindowSnapshot(kind, pkg, window.isFocused, window.isActive)
            }
        } catch (_: Exception) {
            emptyList()
        }
        val pkg = BallForegroundResolver.resolve(snapshots, verifiedBallForeground)
        publishBallForeground(pkg)
        // Only bounded retries after an event; never background polling.
        if (pkg == null && ballForegroundRetry < 2) {
            ballForegroundRetry++
            mainHandler.postDelayed(ballForegroundRecheck, 120L * ballForegroundRetry)
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
        syncImeStateFromWindows()
        requestBallForegroundRefresh()
        TestLog.i(MODULE, "onServiceConnected: SuzuEmojy 辅助切换服务已就绪 (输入法快速切换 + IME/前台双信号分发)")
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

    private fun syncImeStateFromWindows() {
        val winList = try { windows } catch (e: Exception) { null }
        val screenHeight = getScreenHeight()
        if (winList.isNullOrEmpty()) {
            // 窗口列表为空仅代表此刻检测不可用（如 IME 切换间隙），
            // 不强制上报 false，配合悬浮球侧 fail-open 避免切换间隙误隐藏。
            imeDetectionAvailable = false
        } else {
            imeDetectionAvailable = true
            // 不能仅凭 TYPE_INPUT_METHOD 判断可见：
            // 部分系统在键盘收起后仍会暂时保留 IME 窗口对象，
            // 但其边界已经为空、过小或完全位于屏幕外。
            // 只有存在有效屏幕可见区域的 IME 窗口时，才认为键盘真正展开。
            val imeRects = winList
                .asSequence()
                .filter { it.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD }
                .mapNotNull { window ->
                    try {
                        android.graphics.Rect().also { window.getBoundsInScreen(it) }
                    } catch (_: Exception) {
                        null
                    }
                }
                .toList()

            val windowVisible = imeRects.any { rect -> isImeWindowValid(rect, screenHeight) }
            val lifecycleHidden = System.currentTimeMillis() < imeLifecycleHiddenUntil
            val visible = windowVisible && !lifecycleHidden
            if (visible != lastImeVisible) {
                lastImeVisible = visible
                mainHandler.post {
                    ballController?.onImeVisibilityChanged(visible)
                }
            }

            val imeTop = imeRects
                .asSequence()
                .filter { rect -> isImeWindowValid(rect, screenHeight) }
                .minByOrNull { it.top }
                ?.top

            mainHandler.post {
                ballController?.onImeBoundsChanged(imeTop)
            }

            if (foregroundAppPackage == null) {
                val focusedWin = winList.firstOrNull { it.isFocused || it.isActive }
                val rootNode = try { focusedWin?.root } catch (e: Exception) { null }
                try {
                    val pkg = rootNode?.packageName?.toString()
                    if (!pkg.isNullOrEmpty() && pkg != "com.android.systemui") {
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
            requestBallForegroundRefresh()
            syncImeStateFromWindows()
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
        mainHandler.removeCallbacks(ballForegroundRecheck)
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
        mainHandler.removeCallbacks(ballForegroundRecheck)
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
        val ownImeId = findTestImeId()
        val fullId = packageName + "/" + TestImageIME::class.java.name
        val shortId = ComponentName(packageName, TestImageIME::class.java.name).flattenToShortString()
        return imeId == ownImeId || imeId == fullId || imeId == shortId || imeId.startsWith("$packageName/")
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
    private var switchCheck: Runnable? = null

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
        switchAttempt?.cancel()
        switchAttempt = null
        switchCheck?.let { mainHandler.removeCallbacks(it) }
        switchCheck = null
        TestLog.i(MODULE, "结束 IME 显示请求: $reason")
    }

    /** Search passes the chat window captured before the overlay took focus. */
    fun switchToTestImeAndEnsureShown(target: InputTarget? = captureInputTarget()): Boolean {
        cancelImeSwitch("新请求")
        if (target == null) {
            TestLog.w(MODULE, "无法切入 IME: 未记录目标输入窗口")
            com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
            return false
        }
        val attempt = com.suzu.test.ime.ImeSwitchAttempt(android.os.SystemClock.uptimeMillis())
        switchAttempt = attempt
        val initialIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        var switched = switchToTestIme()
        if (!switched) {
            cancelImeSwitch("系统未接受切换请求")
            com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
            return false
        }
        var ownImeObserved = false
        val check = object : Runnable {
            override fun run() {
                if (switchAttempt !== attempt || attempt.cancelled) return
                val now = android.os.SystemClock.uptimeMillis()
                val current = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
                val own = isSelfIme(current)
                if (own) ownImeObserved = true
                if (!own && (ownImeObserved || current != initialIme)) {
                    cancelImeSwitch("用户已选择其他输入法")
                    com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
                    return
                }
                val focusedWindow = windows.firstOrNull { it.isFocused }
                val root = focusedWindow?.root
                var focused: AccessibilityNodeInfo? = null
                try {
                    val matches = root != null && root.windowId == target.windowId &&
                        root.packageName?.toString() == target.packageName
                    if (matches) focused = root?.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
                    val ready = matches && focused?.isEditable == true && focused?.isVisibleToUser == true
                    val visible = own && ready && TestImageIME.instance?.isShowingFor(target.packageName) == true
                    if (attempt.stable(now, visible)) {
                        cancelImeSwitch("目标 IME 已稳定显示")
                        com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
                        return
                    }
                    if (attempt.expired(now)) {
                        TestLog.w(MODULE, "IME 显示超时: target=$target default=$current focusReady=$ready visible=$visible")
                        cancelImeSwitch("超过 1000ms")
                        com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
                        return
                    }
                    if (attempt.takeClick(now, switched && own && ready, visible)) {
                        val clicked = focused?.performAction(AccessibilityNodeInfo.ACTION_CLICK) == true
                        TestLog.i(MODULE, "目标输入框补救点击: accepted=$clicked，继续验证显示")
                    }
                } finally {
                    if (focused !== root) focused?.recycle()
                    root?.recycle()
                }
                mainHandler.postDelayed(this, 50L)
            }
        }
        switchCheck = check
        mainHandler.post(check)
        return true // Request queued; completion is verified asynchronously.
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
}
