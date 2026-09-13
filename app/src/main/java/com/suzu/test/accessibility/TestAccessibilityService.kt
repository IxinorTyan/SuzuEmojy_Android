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

    // 旧版精髓：无障碍通道自行缓存上次可见性，仅真实跃迁时才通知悬浮球
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
        refreshDefaultImePackage()
        observeBallConfig()
        syncBallState()
        syncImeStateFromWindows()
        TestLog.i(MODULE, "onServiceConnected: SuzuEmojy 辅助切换服务已就绪 (输入法快速切换 + IME/前台双信号分发)")
    }

    private fun observeBallConfig() {
        val sp = getSharedPreferences(FloatingBallConfig.SP_NAME, Context.MODE_PRIVATE)
        ballConfigListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
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

            // 有效 IME 边界同时供边缘手势跟随。
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
            syncImeStateFromWindows()
        }

        logImeDiag(event)
    }

    override fun onInterrupt() {
        pendingShareCard = null
        mainHandler.removeCallbacks(shareCardTimeoutRunnable)
        mainHandler.removeCallbacks(shareCardRetryRunnable)
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
            TestLog.i(MODULE, "当前系统默认输入法已是自研 IME ($currentIme)，跳过覆盖 previous_ime_id")
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
     * 静默切换到 TestImageIME 并确保键盘成功拉起展示
     */
    fun switchToTestImeAndEnsureShown(): Boolean {
        TestLog.i(MODULE, ">>> 执行 switchToTestImeAndEnsureShown")
        lastImeVisible = false
        val result = switchToTestIme()

        mainHandler.postDelayed({
            if (lastImeVisible != true) {
                TestLog.i(MODULE, "切换 IME 后+120ms 检测到键盘尚未可见，尝试触发输入框点击拉起")
                focusAndClickActiveInputNode()
            } else {
                TestLog.i(MODULE, "切换 IME 后+120ms 检测到键盘已成功可见")
            }
        }, 120L)

        // [实验 A] 禁用 +300ms 兜底点击，隔离其对 IME 动画的干扰
        // mainHandler.postDelayed({
        //     if (lastImeVisible != true) {
        //         TestLog.i(MODULE, "切换 IME 后+300ms 检测到键盘仍未可见，执行兜底点击拉起")
        //         focusAndClickActiveInputNode()
        //     }
        // }, 300L)

        return result
    }

    fun focusAndClickActiveInputNode(): Boolean {
        try {
            val root = rootInActiveWindow ?: return false
            TestLog.i(MODULE, "focusAndClickActiveInputNode: 查找前台输入节点 (pkg=${root.packageName})")

            val focusedNode = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
            if (focusedNode != null) {
                TestLog.i(MODULE, "命中已有输入焦点的节点 [${focusedNode.className}], 执行点击拉起键盘")
                val clicked = focusedNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    || clickNodeOrClickableParent(focusedNode)
                focusedNode.recycle()
                root.recycle()
                return clicked
            }

            val editableNode = findFirstEditableNode(root)
            if (editableNode != null) {
                TestLog.i(MODULE, "命中前台可编辑节点 [${editableNode.className}], 执行聚焦与点击拉起键盘")
                editableNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                val clicked = editableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    || clickNodeOrClickableParent(editableNode)
                if (editableNode !== root) {
                    editableNode.recycle()
                }
                root.recycle()
                return clicked
            }

            root.recycle()
            TestLog.w(MODULE, "focusAndClickActiveInputNode: 前台活动窗口未找到可点击的输入节点")
        } catch (e: Exception) {
            TestLog.w(MODULE, "focusAndClickActiveInputNode 异常: ${e.message}")
        }
        return false
    }

    private fun findFirstEditableNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.isEditable || node.className?.contains("EditText", ignoreCase = true) == true) {
            return node
        }
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstEditableNode(child)
            if (found != null) {
                return found
            }
            child.recycle()
        }
        return null
    }

    /**
     * 静默恢复到原输入法 (previous_ime_id)
     */
    fun restorePreviousIme(): Boolean {
        TestLog.i(MODULE, "<<< 开始执行 restorePreviousIme")
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val enabledList = imm?.enabledInputMethodList ?: emptyList()
        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        var prevImeId = sp.getString(KEY_PREV_IME, null)

        val isInvalid = prevImeId.isNullOrEmpty() ||
                isSelfIme(prevImeId) ||
                enabledList.none { it.id == prevImeId }

        if (isInvalid) {
            val candidate = enabledList.firstOrNull { !isSelfIme(it.id) }
            prevImeId = candidate?.id
            if (!prevImeId.isNullOrEmpty()) {
                sp.edit().putString(KEY_PREV_IME, prevImeId).apply()
                TestLog.i(MODULE, "原记录无效或为自身，自动纠偏选定恢复目标 IME: $prevImeId")
            }
        }

        if (prevImeId.isNullOrEmpty()) {
            TestLog.e(MODULE, "无法切回：未找到可用的非自研输入法")
            return false
        }

        val currentIme = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        TestLog.i(MODULE, "当前 DEFAULT_INPUT_METHOD = $currentIme, 准备恢复至 previous_ime_id = $prevImeId")

        var switchResult = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            switchResult = softKeyboardController.switchToInputMethod(prevImeId)
            TestLog.i(MODULE, "softKeyboardController.switchToInputMethod 返回值 = $switchResult")
        } else {
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
