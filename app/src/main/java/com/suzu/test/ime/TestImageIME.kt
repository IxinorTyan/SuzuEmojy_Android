package com.suzu.test.ime

import android.content.ClipboardManager
import android.content.ClipData
import android.content.ComponentName
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.viewpager2.widget.ViewPager2
import com.suzu.test.BuildConfig
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.databinding.ViewImeKeyboardBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.RecentHistoryEntity
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.config.ShareWhitelistConfig
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.diag.DebugSendTestConfig
import com.suzu.test.ime.diag.EditorInfoDumper
import com.suzu.test.ime.diag.ImageSendDiagnostics
import com.suzu.test.ime.sender.ImageSender
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.ThemeApplier
import com.suzu.test.ime.ui.CategoryPagerAdapter
import com.suzu.test.ime.ui.ImeTabDropdownController
import com.suzu.test.ime.ui.KeyboardTabBar
import com.suzu.test.ime.ui.preview.ImagePreviewPopup
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class TestImageIME : InputMethodService() {

    companion object {
        const val MODULE = "SuzuEmojy"
        private const val ANDROIDX_CORE_VERSION = "1.12.0"
        private val H1B_DIRECT_FAMILY = setOf(
            "com.tencent.mobileqq",
            "com.tencent.tim",
            "com.tencent.qqlite",
            "com.tencent.mm"
        )

        @Volatile
        var instance: TestImageIME? = null
            private set

        fun isShowing(): Boolean = instance?.isImeShowing == true
    }

    private var binding: ViewImeKeyboardBinding? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var dataSource: KeyboardDataSource
    private lateinit var imageSender: ImageSender
    private var tabBar: KeyboardTabBar? = null
    private var imeTabDropdown: ImeTabDropdownController? = null
    private val dbExecutor = Executors.newSingleThreadExecutor()
    private var categoryPagerAdapter: CategoryPagerAdapter? = null
    private var previewPopup: ImagePreviewPopup? = null
    private var isImeShowing: Boolean = false
    private var imeWindowVisible = false

    fun isShowingFor(targetPackage: String): Boolean =
        imeWindowVisible && isInputViewShown && currentInputEditorInfo?.packageName == targetPackage

    fun toggleFavoritesIfShowing() {
        if (!isImeShowing || !imeWindowVisible || !isInputViewShown) return
        binding?.btnTabDropdown?.performClick()
    }

    private fun destroySearchCategory() {
        if (com.suzu.test.floating.ImeSearchStateHolder.searchQuery.value != null) {
            if (com.suzu.test.floating.ImeSearchStateHolder.isSearchLaunching()) {
                TestLog.i(MODULE, "处于搜索拉起保护期内，跳过销毁临时搜索分类")
                return
            }
            TestLog.i(MODULE, "收起自研 IME，直接销毁临时搜索分类")
            com.suzu.test.floating.ImeSearchStateHolder.clearSearch()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        TestLog.init(applicationContext)
        dataSource = KeyboardDataSource(this)
        imageSender = ImageSender(this)
        TestLog.i(MODULE, "onCreate: SuzuEmojy 启动 (AppId: ${BuildConfig.APPLICATION_ID}, authority: ${BuildConfig.FILE_PROVIDER_AUTHORITY}, androidx.core: $ANDROIDX_CORE_VERSION)")
        triggerAutoCacheClean()
    }

    private fun triggerAutoCacheClean() {
        serviceScope.launch(Dispatchers.IO) {
            val result = com.suzu.test.storage.CacheCleanManager.cleanExpired(applicationContext)
            TestLog.i(MODULE, "IME 自动清理完成: 删除了 ${result.deletedCount} 个过期暂存文件, 释放 ${com.suzu.test.storage.CacheCleanManager.formatSize(result.freedBytes)}")
        }
    }

    override fun onCreateInputView(): View {
        TestLog.i(MODULE, "onCreateInputView: 创建 SuzuEmojy 键盘界面")
        tabBar?.destroy()
        tabBar = null
        val viewBinding = ViewImeKeyboardBinding.inflate(layoutInflater)
        binding = viewBinding

        setupSendDiagnostics(viewBinding)
        setupDebugSendSwitch(viewBinding)

        previewPopup?.dismiss()
        previewPopup = ImagePreviewPopup(this)

        val pagerAdapter = CategoryPagerAdapter(
            context = this,
            scope = serviceScope,
            dataSource = dataSource,
            onItemClick = { item -> onDirectSendClick(item) },
            onItemLongClick = { item, anchor ->
                previewPopup?.show(anchor, item)
            },
            onGridScrolled = {
                previewPopup?.dismiss()
            }
        )
        categoryPagerAdapter = pagerAdapter

        viewBinding.vpCategoryPager.adapter = pagerAdapter
        // 关键：预加载前后各 1 页，拖动时 0ms 顺畅露边，绝不白屏
        viewBinding.vpCategoryPager.offscreenPageLimit = 1
        var updatingTabs = false
        viewBinding.vpCategoryPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    previewPopup?.dismiss()
                }
            }

            override fun onPageSelected(position: Int) {
                if (updatingTabs || tabBar?.hasLoadedCategories() != true ||
                    binding !== viewBinding || position != viewBinding.vpCategoryPager.currentItem) return
                val tabKey = pagerAdapter.getTabKey(position) ?: return
                if (tabBar?.currentTab != tabKey) {
                    tabBar?.selectTab(tabKey, notify = false)
                    imeTabDropdown?.setSelectedTab(tabKey)
                }
            }
        })

        applyKeyboardConfigLayout()
        applyTheme()

        viewBinding.btnExit.setOnClickListener { exitAndRestoreIme() }

        imeTabDropdown?.destroy()
        imeTabDropdown = null
        tabBar?.destroy()

        val tb = KeyboardTabBar(
            context = this,
            container = viewBinding.llTabBarContainer,
            scope = serviceScope,
            onTabSelected = { tabKey ->
                imeTabDropdown?.setSelectedTab(tabKey)
                val pos = pagerAdapter.getPositionForTab(tabKey)
                if (pos >= 0 && viewBinding.vpCategoryPager.currentItem != pos) {
                    // 点击 Tab 时平滑横向滚动至对应分类
                    viewBinding.vpCategoryPager.setCurrentItem(pos, true)
                }
            }
        )
        tb.onTabsStructureChanged = { tabs, selectedTab ->
            updatingTabs = true
            try {
                pagerAdapter.setTabs(tabs)
                val pos = pagerAdapter.getPositionForTab(selectedTab)
                if (pos >= 0 && viewBinding.vpCategoryPager.currentItem != pos) {
                    viewBinding.vpCategoryPager.setCurrentItem(pos, false)
                }
                imeTabDropdown?.setSelectedTab(selectedTab)
            } finally {
                updatingTabs = false
            }
        }
        tabBar = tb

        val dropdown = ImeTabDropdownController(
            context = this,
            button = viewBinding.btnTabDropdown,
            panel = viewBinding.svImeTabDropdown,
            grid = viewBinding.glImeTabDropdown,
            scope = serviceScope,
            tabSizeDpProvider = { tb.getTabSizeDp() },
            onTabSelected = { tabKey -> tb.selectTab(tabKey) }
        )
        imeTabDropdown = dropdown
        dropdown.refreshTheme()

        val initialEffectiveTab = tb.getEffectiveTab()
        dropdown.setSelectedTab(initialEffectiveTab)
        tb.start()
        dropdown.start()

        val initialPos = pagerAdapter.getPositionForTab(initialEffectiveTab)
        if (initialPos >= 0) {
            viewBinding.vpCategoryPager.setCurrentItem(initialPos, false)
        }

        return viewBinding.root
    }

    private fun onDirectSendClick(item: ImageItem) {
        previewPopup?.dismiss()
        val targetPkg = currentInputEditorInfo?.packageName
        if (targetPkg == null) {
            TestLog.e(MODULE, "发送失败: targetPkg 为空 (未检测到输入目标)")
            return
        }

        TestLog.i(MODULE, "========== [点击直发] targetPkg=$targetPkg, item=${item.displayName} ==========")

        val onSuccessCallback: () -> Unit = { updateUsageStatsInBackground(item) }

        if (ShareWhitelistConfig.isWhitelisted(this, targetPkg)) {
            TestLog.i(MODULE, "智能路由 -> 命中分享白名单 ($targetPkg)，拉起系统分享选择器")
            imageSender.executeSystemChooser(
                item = item,
                targetPkg = targetPkg,
                editorInfoPackage = targetPkg,
                onSuccess = onSuccessCallback
            )
        } else if (targetPkg in H1B_DIRECT_FAMILY) {
            TestLog.i(MODULE, "智能路由 -> 命中 H1β 直发家族 ($targetPkg)，走 H1β 私有协议直发")
            imageSender.executeH1b(
                item = item,
                targetPkg = targetPkg,
                editorInfoPackage = targetPkg,
                icProvider = { currentInputConnection },
                onSuccess = onSuccessCallback
            )
        } else {
            TestLog.i(MODULE, "智能路由 -> 非 H1β 直发目标 ($targetPkg)，走剪贴板注入与标准粘贴")
            imageSender.executeE(
                item = item,
                targetPkg = targetPkg,
                editorInfoPackage = targetPkg,
                icProvider = { currentInputConnection },
                onSuccess = onSuccessCallback
            )
        }
    }

    private fun setupDebugSendSwitch(viewBinding: ViewImeKeyboardBinding) {
        if (!BuildConfig.DEBUG) return
        val testSwitch = viewBinding.switchDebugSkipProvider
        testSwitch.isChecked =
            DebugSendTestConfig.isSkipProviderDiagnosticAndCleanupEnabled(this)
        testSwitch.setOnCheckedChangeListener { _, enabled ->
            DebugSendTestConfig.setSkipProviderDiagnosticAndCleanupEnabled(this, enabled)
            TestLog.i(
                MODULE,
                "DEBUG发送测试开关：模拟 Provider 未发生及未清理 = $enabled"
            )
        }
    }

    private fun setupSendDiagnostics(viewBinding: ViewImeKeyboardBinding) {
        if (!BuildConfig.DEBUG) return
        viewBinding.llSendDiagnostic.visibility = View.VISIBLE
        val refresh = {
            viewBinding.tvSendDiagnosticContent.text =
                ImageSendDiagnostics.recent().joinToString("\n\n") { it.summary() }
        }
        val listener: () -> Unit = { viewBinding.root.post(refresh) }
        ImageSendDiagnostics.addListener(listener)
        viewBinding.btnSendDiagnosticToggle.setOnClickListener {
            val expanded = viewBinding.svSendDiagnostic.visibility == View.VISIBLE
            viewBinding.svSendDiagnostic.visibility = if (expanded) View.GONE else View.VISIBLE
            viewBinding.btnSendDiagnosticToggle.text = if (expanded) "查看" else "收起"
            refresh()
        }
        viewBinding.btnSendDiagnosticCopy.setOnClickListener {
            val text = ImageSendDiagnostics.recent().joinToString("\n\n") { it.summary() }
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("SuzuEmojy_SendDiagnostics", text))
        }
        refresh()
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        TestLog.i(MODULE, "onStartInput: pkg=${attribute?.packageName}, inputType=${attribute?.inputType}, restarting=$restarting")
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        isImeShowing = true
        if (TestAccessibilityService.instance?.isImeSwitchPending() != true) {
            com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
        }
        TestLog.i(MODULE, "==================== onStartInputView (restarting=$restarting) ====================")

        // 进程内直连信号：0ms 秒级通知悬浮球 IME 已可见
        TestAccessibilityService.notifyImeLifecycle(true)

        applyKeyboardConfigLayout()
        applyTheme()

        // 保留旧版仅在输入视图启动后请求显示的行为。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestShowSelf(0)
        }

        EditorInfoDumper.dump(this, info)

        syncCurrentTabPosition()
    }

    override fun onWindowShown() {
        super.onWindowShown()
        imeWindowVisible = true
        isImeShowing = true
        if (TestAccessibilityService.instance?.isImeSwitchPending() != true) {
            com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
        }

        // 窗口生命周期信号比无障碍窗口列表更及时，避免窗口列表残留导致状态误判。
        TestAccessibilityService.notifyImeLifecycle(true)

        syncCurrentTabPosition()
    }

    private fun syncCurrentTabPosition() {
        val targetTab = tabBar?.getEffectiveTab() ?: "ALL"
        val adapter = categoryPagerAdapter ?: return
        val pos = adapter.getPositionForTab(targetTab)
        if (pos >= 0 && binding?.vpCategoryPager?.currentItem != pos) {
            binding?.vpCategoryPager?.setCurrentItem(pos, false)
        }
    }

    override fun onWindowHidden() {
        tabBar?.saveNavigationState()
        super.onWindowHidden()
        imeWindowVisible = false
        isImeShowing = false
        TestAccessibilityService.notifyImeLifecycle(false)
    }

    fun applyKeyboardConfigLayout() {
        val targetSpanCount = KeyboardConfig.getSpanCount(this)
        categoryPagerAdapter?.updateSpanCount(targetSpanCount)
        TestLog.i(MODULE, "应用键盘列数配置: spanCount=$targetSpanCount")

        val heightDp = KeyboardConfig.getGridHeightDp(this)
        val heightPx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            heightDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        binding?.flGridContainer?.let { container ->
            val lp = container.layoutParams
            if (lp != null && lp.height != heightPx) {
                lp.height = heightPx
                container.layoutParams = lp
                container.requestLayout()
                TestLog.i(MODULE, "应用键盘网格高度配置: heightDp=$heightDp, heightPx=$heightPx")
            }
        }

        val tabSizeDp = KeyboardConfig.getTabIconSizeDp(this)
        val tabSizePx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            tabSizeDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        val buttonPaddingPx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            8f,
            resources.displayMetrics
        ).toInt()

        val showTabDropdown = KeyboardConfig.isTabDropdownEnabled(this)
        binding?.btnTabDropdown?.let { dropdownBtn ->
            dropdownBtn.visibility = if (showTabDropdown) View.VISIBLE else View.GONE
            dropdownBtn.setImageResource(KeyboardConfig.getDropdownIconRes(this))
            configureHeaderButton(dropdownBtn, tabSizePx, buttonPaddingPx)
        }
        if (!showTabDropdown) {
            imeTabDropdown?.close()
        }

        val showExitBtn = KeyboardConfig.isExitButtonEnabled(this)
        binding?.btnExit?.let { exitBtn ->
            exitBtn.visibility = if (showExitBtn) View.VISIBLE else View.GONE
            configureHeaderButton(exitBtn, tabSizePx, buttonPaddingPx)
        }

        applyDropdownPosition()

        binding?.llTabHeaderBar?.let { headerBar ->
            val lp = headerBar.layoutParams
            if (lp != null && lp.height != tabSizePx) {
                lp.height = tabSizePx
                headerBar.layoutParams = lp
                headerBar.requestLayout()
            }
        }
    }

    private fun applyDropdownPosition() {
        val currentBinding = binding ?: return
        val headerBar = currentBinding.llTabHeaderBar
        val dropdownBtn = currentBinding.btnTabDropdown
        val exitBtn = currentBinding.btnExit

        val currentIndex = headerBar.indexOfChild(dropdownBtn)
        val isRight = KeyboardConfig.getDropdownPosition(this) == KeyboardConfig.DROPDOWN_POSITION_RIGHT

        if (isRight) {
            val exitIndex = headerBar.indexOfChild(exitBtn)
            if (exitIndex != -1 && currentIndex != exitIndex - 1) {
                headerBar.removeView(dropdownBtn)
                val newExitIndex = headerBar.indexOfChild(exitBtn)
                headerBar.addView(dropdownBtn, newExitIndex)
                TestLog.i(MODULE, "调整展开收藏夹按钮位置为右上角(收起左侧)")
            }
        } else {
            if (currentIndex != 0) {
                headerBar.removeView(dropdownBtn)
                headerBar.addView(dropdownBtn, 0)
                TestLog.i(MODULE, "调整展开收藏夹按钮位置为左上角")
            }
        }
    }

    private fun configureHeaderButton(
        button: android.widget.ImageButton,
        sizePx: Int,
        paddingPx: Int
    ) {
        val lp = button.layoutParams ?: return
        var needsLayout = false
        if (lp.width != sizePx || lp.height != sizePx) {
            lp.width = sizePx
            lp.height = sizePx
            button.layoutParams = lp
            needsLayout = true
        }
        button.minimumWidth = 0
        button.minimumHeight = 0
        button.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
        button.scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE
        if (needsLayout) {
            button.requestLayout()
        }
    }

    private fun applyTheme() {
        val theme = KeyboardTheme.current(this)
        binding?.let { b ->
            ThemeApplier.applyTo(b, theme)
            tabBar?.refreshTheme()
            imeTabDropdown?.refreshTheme()
            categoryPagerAdapter?.notifyThemeChanged()
            TestLog.i(MODULE, "应用键盘主题配置: isDark=${theme.isDark}")
        }
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        tabBar?.saveNavigationState()
        previewPopup?.dismiss()
        imeTabDropdown?.close()
        isImeShowing = false
        imeWindowVisible = false
        TestAccessibilityService.notifyImeLifecycle(false)
        super.onFinishInputView(finishingInput)
        if (TestAccessibilityService.instance?.isImeSwitchPending() != true) {
            restorePreviousKeyboard("onFinishInputView(finishing=$finishingInput)")
        }
    }

    override fun onFinishInput() {
        tabBar?.saveNavigationState()
        previewPopup?.dismiss()
        isImeShowing = false
        imeWindowVisible = false
        TestAccessibilityService.notifyImeLifecycle(false)
        super.onFinishInput()
        if (TestAccessibilityService.instance?.isImeSwitchPending() != true) {
            restorePreviousKeyboard("onFinishInput")
        }
    }

    private fun isSelfIme(imeId: String?): Boolean {
        if (imeId.isNullOrEmpty()) return false
        val fullId = packageName + "/" + TestImageIME::class.java.name
        val shortId = ComponentName(packageName, TestImageIME::class.java.name).flattenToShortString()
        val realId = TestAccessibilityService.instance?.findTestImeId()
        return imeId == fullId || imeId == shortId || (realId != null && imeId == realId) || imeId.startsWith("$packageName/")
    }

    /**
     * 统一恢复先前输入法入口
     */
    fun restorePreviousKeyboard(reason: String = ""): Boolean {
        tabBar?.saveNavigationState()
        TestAccessibilityService.instance?.cancelImeSwitch("恢复原输入法: $reason")
        TestLog.i(MODULE, ">>> 执行恢复原输入法 ($reason)")
        isImeShowing = false
        com.suzu.test.floating.ImeSearchStateHolder.clearSearch()

        val currentIme = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )
        if (!currentIme.isNullOrEmpty() && !isSelfIme(currentIme)) {
            TestLog.i(MODULE, "restorePreviousKeyboard: 当前默认输入法已非自研 IME ($currentIme)，跳过恢复")
            return true
        }

        val service = TestAccessibilityService.instance
        if (service != null && TestAccessibilityService.isAlive()) {
            val restored = service.restorePreviousIme()
            TestLog.i(MODULE, "restorePreviousKeyboard: 无障碍服务 restorePreviousIme 返回值 = $restored")
            if (restored) return true
        }

        TestLog.i(MODULE, "restorePreviousKeyboard: 无障碍服务不可用或切回失败，尝试原生 switchToPreviousInputMethod 兜底...")
        val switched = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            switchToPreviousInputMethod()
        } else {
            @Suppress("DEPRECATION")
            switchToPreviousInputMethod()
        }
        TestLog.i(MODULE, "restorePreviousKeyboard: switchToPreviousInputMethod 返回值 = $switched")
        return switched
    }

    private fun updateUsageStatsInBackground(item: ImageItem) {
        if (item !is ImageItem.SuzuResource) return
        dbExecutor.execute {
            try {
                val db = DatabaseProvider.getDatabase(this)
                val now = System.currentTimeMillis()
                val resourceDao = db.resourceDao()
                val sp = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
                val recentLimit = sp.getInt("recent_history_limit", 40).coerceIn(1, 100)

                kotlinx.coroutines.runBlocking {
                    val entity = resourceDao.getById(item.id)
                    if (entity != null) {
                        resourceDao.update(entity.copy(lastUsedAt = now, useCount = entity.useCount + 1))
                    }
                    db.recentDao().recordUsageAndTrim(
                        RecentHistoryEntity(resourceId = item.id, usedAt = now),
                        recentLimit
                    )
                }
                TestLog.i(MODULE, "已在后台更新资源使用频次并按上限($recentLimit)淘汰记录: ID=${item.id}")
                // 通知 RECENT 页面刷新，确保下一次滑到 RECENT 时展示最新历史
                serviceScope.launch(Dispatchers.Main) {
                    categoryPagerAdapter?.refreshTab("RECENT")
                }
            } catch (e: Exception) {
                TestLog.e(MODULE, "更新资源使用记录异常: ${e.message}", e)
            }
        }
    }

    fun exitAndRestoreIme() {
        TestLog.i(MODULE, ">>> 触发 [退出] 恢复原输入法")
        restorePreviousKeyboard("退出按钮")
    }

    override fun onDestroy() {
        tabBar?.saveNavigationState()
        imeWindowVisible = false
        if (instance === this) {
            instance = null
        }
        isImeShowing = false
        destroySearchCategory()
        previewPopup?.dismiss()
        previewPopup = null
        super.onDestroy()
        TestLog.i(MODULE, "onDestroy: SuzuEmojy 销毁")
        imeTabDropdown?.destroy()
        imeTabDropdown = null
        tabBar?.destroy()
        tabBar = null
        categoryPagerAdapter?.clear()
        categoryPagerAdapter = null
        serviceScope.cancel()
        imageSender.destroy()
        dbExecutor.shutdown()
        binding = null
    }
}
