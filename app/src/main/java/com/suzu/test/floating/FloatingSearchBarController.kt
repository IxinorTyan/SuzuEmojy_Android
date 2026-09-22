package com.suzu.test.floating

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.databinding.LayoutFloatingSearchBarBinding
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.log.TestLog
import kotlinx.coroutines.*
import android.database.ContentObserver
import android.os.SystemClock

class FloatingSearchBarController(private val context: Context) {

    companion object {
        private const val MODULE = "FloatingSearchBar"
        internal fun resolveHeight(topPx: Int?, offset: Int, cardHeight: Int, screenHeight: Int): Int {
            val available = (screenHeight - offset).coerceAtLeast(cardHeight)
            return if (topPx != null && topPx > offset + cardHeight) {
                (topPx - offset).coerceAtMost(available)
            } else cardHeight
        }
    }

    private var windowManager: WindowManager? = null
    private var searchView: View? = null
    private var binding: LayoutFloatingSearchBarBinding? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private var isShowing: Boolean = false
    private var isAttached: Boolean = false
    private var imeGuardUntil: Long = 0L
    private var lastImeVisible: Boolean = false
    private var currentImeTop: Int? = null
    private var pendingImeTop: Int? = null
    private var imeBoundsUpdatePosted = false
    private var searchTarget: TestAccessibilityService.InputTarget? = null
    private var showGeneration = 0L
    private val launchState = SearchLaunchState()
    private val launchScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var launchJob: Job? = null
    private var observingIme = false
    private val preparationTimeout = Runnable {
        if (launchState.phase == SearchLaunchState.Phase.PREPARING ||
            launchState.phase == SearchLaunchState.Phase.WAITING_TEXT_IME) {
            TestLog.w(MODULE, "搜索准备超时，取消本轮请求")
            hide(false)
        }
    }
    private val imeObserver = object : ContentObserver(mainHandler) {
        override fun onChange(selfChange: Boolean) {
            val current = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            if (launchState.shouldYieldToOwnIme() && TestAccessibilityService.instance?.isSelfIme(current) == true) {
                onSelfImeShown()
            }
        }
    }

    private val imeBoundsUpdateRunnable = Runnable {
        imeBoundsUpdatePosted = false
        currentImeTop = pendingImeTop
        if (isShowing) updateWindowHeightForIme(currentImeTop)
    }

    private val delayedHideRunnable = Runnable {
        if (isShowing && !lastImeVisible && SystemClock.uptimeMillis() >= imeGuardUntil) {
            TestLog.i(MODULE, "已确认键盘收起且度过切换保护期，自动隐藏搜索框")
            hide()
        }
    }

    fun attach() {
        if (isAttached) return
        if (!Settings.canDrawOverlays(context)) return

        try {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            val themedContext = androidx.appcompat.view.ContextThemeWrapper(context, com.suzu.test.R.style.Theme_Test)
            val inflater = LayoutInflater.from(themedContext)
            val b = LayoutFloatingSearchBarBinding.inflate(inflater)
            binding = b
            searchView = b.root

        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val topOffset = getTopMarginPx()

        // 初始状态：不可聚焦、不可触碰、完全透明
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            x = 0
            y = topOffset
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        }
        layoutParams = params

        b.root.alpha = 0f
        params.alpha = 0f
        b.viewDismissArea.visibility = View.GONE
        setupListeners(b)

        windowManager?.addView(b.root, params)
        isAttached = true
        TestLog.i(MODULE, "顶部搜索框挂载完成, y=$topOffset")
    } catch (e: Exception) {
        TestLog.e(MODULE, "挂载搜索框失败: ${e.message}", e)
    }
}

    private fun getTopMarginPx(): Int {
        val marginDp = FloatingBallConfig.getSearchBarTopMarginDp(context)
        val density = context.resources.displayMetrics.density
        return (marginDp * density).toInt()
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

    private fun setupListeners(b: LayoutFloatingSearchBarBinding) {
        b.viewDismissArea.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                TestLog.i(MODULE, "点击搜索框下方外围区域，取消聚焦并自动隐藏")
                hide()
                true
            } else {
                false
            }
        }

        b.flSearchCardContainer.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val hitRect = android.graphics.Rect()
                b.llSearchCard.getHitRect(hitRect)
                if (!hitRect.contains(event.x.toInt(), event.y.toInt())) {
                    TestLog.i(MODULE, "点击搜索卡片外围边距，取消聚焦并自动隐藏")
                    hide()
                    return@setOnTouchListener true
                }
            }
            false
        }

        b.btnDoSearch.setOnClickListener {
            performSearch()
        }

        b.btnClearSearch.visibility = View.VISIBLE
        b.btnClearSearch.setOnClickListener {
            if (b.etSearchInput.text.isNullOrEmpty()) {
                hide()
            } else {
                b.etSearchInput.setText("")
            }
        }

        b.etSearchInput.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                hide()
                true
            } else if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                performSearch()
                true
            } else {
                false
            }
        }

        b.etSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 始终显示清空/关闭按钮，有字清空，无字关闭
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        b.etSearchInput.setOnEditorActionListener { _, actionId, event ->
            val isSearchAction = actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO ||
                    actionId == EditorInfo.IME_ACTION_SEND
            val isEnterKey = (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            val isUnspecifiedEnter = actionId == EditorInfo.IME_ACTION_UNSPECIFIED && (event == null || event.keyCode == KeyEvent.KEYCODE_ENTER)

            if (isSearchAction || isEnterKey || isUnspecifiedEnter) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    fun applyTheme() {
        val b = binding ?: return
        val theme = KeyboardTheme.current(context)
        val density = context.resources.displayMetrics.density

        val cardBg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f * density
            setColor(if (theme.isDark) 0xFF2A2A2A.toInt() else 0xFFFFFFFF.toInt())
            setStroke((1.5f * density).toInt(), if (theme.isDark) 0x44FFFFFF.toInt() else 0x26000000.toInt())
        }
        b.llSearchCard.background = cardBg

        b.etSearchInput.setTextColor(theme.tabTextSelected)
        b.etSearchInput.setHintTextColor(theme.emptyHintText)

        b.btnDoSearch.imageTintList = ColorStateList.valueOf(theme.iconColor)
        b.btnClearSearch.imageTintList = ColorStateList.valueOf(theme.emptyHintText)
    }

    fun show() {
        if (isShowing || launchState.phase != SearchLaunchState.Phase.IDLE) return
        val token = launchState.begin(SystemClock.uptimeMillis())
        mainHandler.removeCallbacks(preparationTimeout)
        mainHandler.postDelayed(preparationTimeout, 2500L)
        launchJob = launchScope.launch {
            try {
                val service = TestAccessibilityService.instance
                service?.cancelImeSwitch("打开搜索框")
                val started = SystemClock.uptimeMillis()
                val target = withContext(Dispatchers.IO) { service?.captureInputTarget() }
                TestLog.i(MODULE, "搜索准备 pid=${android.os.Process.myPid()} token=$token targetElapsed=${SystemClock.uptimeMillis() - started}ms")
                if (!launchState.accepts(token)) return@launch
                if (launchState.expired(SystemClock.uptimeMillis())) { launchState.close(); return@launch }
                searchTarget = target
                val current = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
                if (service?.isSelfIme(current) == true) {
                    launchState.waiting()
                    val restored = withContext(Dispatchers.IO) { service.requestTextImeForSearch() }
                    if (!restored) { launchState.close(); return@launch }
                    while (service.isSelfIme(Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD))) {
                        if (launchState.expired(SystemClock.uptimeMillis())) {
                            TestLog.w(MODULE, "搜索启动等待文本输入法超时")
                            launchState.close()
                            return@launch
                        }
                        delay(50L)
                    }
                }
                if (launchState.accepts(token)) showPrepared()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                TestLog.e(MODULE, "搜索启动失败", e)
                if (isShowing) hide(false) else launchState.close()
            }
        }
    }

    private fun showPrepared() {
        val started = android.os.SystemClock.uptimeMillis()
        TestLog.i(MODULE, "搜索唤起开始 pid=${android.os.Process.myPid()} generation=${showGeneration + 1}")
        val service = TestAccessibilityService.instance
        service?.cancelImeSwitch("打开搜索框")
        if (!isAttached) {
            attach()
        }
        if (!isAttached) { launchState.close(); return }
        val view = searchView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return

        isShowing = true
        mainHandler.removeCallbacks(preparationTimeout)
        launchState.activate()
        context.contentResolver.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.DEFAULT_INPUT_METHOD), false, imeObserver)
        observingIme = true
        val generation = ++showGeneration
        // 设定保护锁：给焦点切换留足 1200ms，期间忽略输入法瞬态隐藏
        imeGuardUntil = SystemClock.uptimeMillis() + 1200L
        mainHandler.removeCallbacks(delayedHideRunnable)
        applyTheme()

        // 检查当前输入法：如果正是我们自研的表情键盘，唤醒搜索框时切回原来的文本键盘供输入
        val accessibility = TestAccessibilityService.instance
        val currentIme = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        val isCurrentOwnIme = accessibility != null && accessibility.isSelfIme(currentIme)

        if (isCurrentOwnIme) { hide(false); return }

        ImeSearchStateHolder.clearSearch()
        binding?.etSearchInput?.setText("")

        // 允许获焦，设置非模态，但外围半透明/空白区域点击可快速收起
        binding?.viewDismissArea?.visibility = View.VISIBLE
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE or
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        view.alpha = 1f
        params.alpha = 1f
        params.y = getTopMarginPx()
        updateWindowHeightForIme(currentImeTop)

        try {
            wm.updateViewLayout(view, params)
        } catch (e: Exception) {
            TestLog.e(MODULE, "updateViewLayout 失败: ${e.message}", e)
            hide(false)
            return
        }

        val showSoftInputDelay = if (isCurrentOwnIme) 200L else 80L
        binding?.etSearchInput?.let { et ->
            et.requestFocus()
            mainHandler.postDelayed({
                if (!isShowing || generation != showGeneration || !et.hasFocus()) return@postDelayed
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
            }, showSoftInputDelay)
        }

        TestLog.i(MODULE, "顶部搜索框已展开 (generation=$generation, elapsed=${android.os.SystemClock.uptimeMillis() - started}ms, isCurrentOwnIme=$isCurrentOwnIme, imeGuardUntil=$imeGuardUntil)")
    }

    fun hide(hideKeyboard: Boolean = true) {
        mainHandler.removeCallbacks(preparationTimeout)
        launchState.close()
        launchJob?.cancel()
        launchJob = null
        if (observingIme) {
            context.contentResolver.unregisterContentObserver(imeObserver)
            observingIme = false
        }
        if (!isShowing) return
        isShowing = false
        showGeneration++
        mainHandler.removeCallbacks(delayedHideRunnable)
        mainHandler.removeCallbacks(imeBoundsUpdateRunnable)
        imeBoundsUpdatePosted = false

        binding?.viewDismissArea?.visibility = View.GONE

        val view = searchView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return

        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        view.alpha = 0f
        params.alpha = 0f

        try {
            wm.updateViewLayout(view, params)
        } catch (e: Exception) {
            TestLog.e(MODULE, "hide updateViewLayout 失败: ${e.message}", e)
            detach()
        }

        binding?.etSearchInput?.let { et ->
            et.clearFocus()
            if (hideKeyboard) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(et.windowToken, 0)
            }
        }

        TestLog.i(MODULE, "顶部搜索框已自动收起隐藏 (hideKeyboard=$hideKeyboard)")
    }

    private fun performSearch() {
        val b = binding ?: return
        val query = b.etSearchInput.text.toString().trim()
        if (query.isEmpty()) {
            hide()
            return
        }

        val scope = FloatingBallConfig.getSearchBarScope(context)
        TestLog.i(MODULE, "发起搜索: query='$query', scope=$scope")
        ImeSearchStateHolder.setSearchQuery(query)

        // 1. 回车/点击搜索后，检索悬浮窗立即自动收起隐藏，但不主动关闭软键盘以避免输入会话中断
        hide(hideKeyboard = false)

        // Use the original chat window, and wait for its editor to regain focus.
        val accessibility = TestAccessibilityService.instance
        if (accessibility != null) {
            accessibility.switchToTestImeAndEnsureShown(searchTarget)
        } else {
            ImeSearchStateHolder.clearSearch()
            TestLog.w(MODULE, "无障碍服务不可用，无法自动拉起自研 IME")
        }
    }

    fun onImeVisibilityChanged(visible: Boolean) {
        lastImeVisible = visible
        if (visible) {
            // IME 显示中，取消任何待执行的隐藏任务
            mainHandler.removeCallbacks(delayedHideRunnable)
        } else {
            if (isShowing) {
                val remaining = (imeGuardUntil - SystemClock.uptimeMillis()).coerceAtLeast(0L)
                val delay = maxOf(remaining, 350L)
                mainHandler.removeCallbacks(delayedHideRunnable)
                mainHandler.postDelayed(delayedHideRunnable, delay)
                TestLog.i(MODULE, "收到 IME 隐藏信号，已延期 ${delay}ms 防抖评估隐藏")
            }
        }
    }

    fun onSelfImeShown() {
        if (!launchState.shouldYieldToOwnIme()) return
        val current = Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        if (TestAccessibilityService.instance?.isSelfIme(current) != true) return
        TestLog.i(MODULE, "自研 IME 接管输入，关闭搜索浮窗")
        hide(hideKeyboard = false)
        TestAccessibilityService.instance?.switchToTestImeAndEnsureShown(searchTarget)
    }

    fun onImeBoundsChanged(topPx: Int?) {
        currentImeTop = topPx
        pendingImeTop = topPx
        if (!isShowing || imeBoundsUpdatePosted) return
        imeBoundsUpdatePosted = true
        // IME insets can generate many callbacks per frame. Apply only the latest one.
        mainHandler.postDelayed(imeBoundsUpdateRunnable, 16L)
    }

    private fun updateWindowHeightForIme(topPx: Int?) {
        val view = searchView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return

        val density = context.resources.displayMetrics.density
        val topOffset = getTopMarginPx()
        val cardHeight = (64 * density).toInt()
        val screenHeight = context.resources.displayMetrics.heightPixels

        val maxTopOffset = maxOf(0, screenHeight - cardHeight)
        val clampedTopOffset = minOf(topOffset, maxTopOffset)

        // Unknown or off-screen IME bounds must not enlarge the touch interceptor.
        val targetHeight = resolveHeight(topPx, clampedTopOffset, cardHeight, screenHeight)

        if (params.height != targetHeight || params.y != clampedTopOffset) {
            params.height = targetHeight
            params.y = clampedTopOffset
            try {
                wm.updateViewLayout(view, params)
                TestLog.i(MODULE, "根据 IME 边界调整搜索窗: height=$targetHeight, y=$clampedTopOffset (topPx=$topPx)")
            } catch (e: Exception) {
                TestLog.e(MODULE, "updateWindowHeightForIme 失败: ${e.message}")
            }
        }
    }

    fun updatePosition() {
        val view = searchView ?: return
        val params = layoutParams ?: return
        val wm = windowManager ?: return

        val topOffset = getTopMarginPx()
        if (isShowing) {
            updateWindowHeightForIme(currentImeTop)
        } else {
            if (params.y != topOffset) {
                params.y = topOffset
                try {
                    wm.updateViewLayout(view, params)
                    TestLog.i(MODULE, "更新未展示态搜索框位置: y=$topOffset")
                } catch (e: Exception) {
                    TestLog.e(MODULE, "updatePosition updateViewLayout 失败: ${e.message}")
                }
            }
        }
    }

    fun detach() {
        mainHandler.removeCallbacks(preparationTimeout)
        launchState.close()
        launchJob?.cancel()
        if (observingIme) {
            context.contentResolver.unregisterContentObserver(imeObserver)
            observingIme = false
        }
        if (!isAttached) return
        showGeneration++
        isAttached = false
        isShowing = false
        mainHandler.removeCallbacks(delayedHideRunnable)
        mainHandler.removeCallbacks(imeBoundsUpdateRunnable)
        imeBoundsUpdatePosted = false
        mainHandler.removeCallbacksAndMessages(null)
        searchView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                TestLog.w(MODULE, "removeView 异常: ")
            }
        }
        searchView = null
        binding = null
        layoutParams = null
        windowManager = null
    }
}
