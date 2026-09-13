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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suzu.test.BuildConfig
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.databinding.ViewImeKeyboardBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.RecentHistoryEntity
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.diag.DebugSendTestConfig
import com.suzu.test.ime.diag.EditorInfoDumper
import com.suzu.test.ime.diag.ImageSendDiagnostics
import com.suzu.test.ime.sender.ImageSender
import com.suzu.test.ime.ui.KeyboardTabBar
import com.suzu.test.ime.ui.ImeTabDropdownController
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.config.ShareWhitelistConfig
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.ThemeApplier
import com.suzu.test.ime.ui.preview.ImagePreviewPopup
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class TestImageIME : InputMethodService() {

    companion object {
        private const val MODULE = "SuzuEmojy"
        private const val ANDROIDX_CORE_VERSION = "1.12.0"
        private val H1B_DIRECT_FAMILY = setOf(
            "com.tencent.mobileqq",
            "com.tencent.tim",
            "com.tencent.qqlite",
            "com.tencent.mm"
        )
    }

    private var binding: ViewImeKeyboardBinding? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var dataSource: KeyboardDataSource
    private lateinit var imageSender: ImageSender
    private var tabBar: KeyboardTabBar? = null
    private var imeTabDropdown: ImeTabDropdownController? = null
    private val dbExecutor = Executors.newSingleThreadExecutor()
    private lateinit var imageAdapter: ImageAdapter
    private var gridLayoutManager: GridLayoutManager? = null
    private var previewPopup: ImagePreviewPopup? = null
    private var loadImagesJob: kotlinx.coroutines.Job? = null
    private var lastLoadedTabKey: String? = null
    private var isImeShowing: Boolean = false

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
        val viewBinding = ViewImeKeyboardBinding.inflate(layoutInflater)
        binding = viewBinding

        setupSendDiagnostics(viewBinding)
        setupDebugSendSwitch(viewBinding)

        previewPopup?.dismiss()
        previewPopup = ImagePreviewPopup(this)

        imageAdapter = ImageAdapter(
            onItemClick = { item -> onDirectSendClick(item) },
            onItemLongClick = { item, anchor ->
                previewPopup?.show(anchor, item)
            }
        )
        val initialSpanCount = KeyboardConfig.getSpanCount(this)
        val glm = GridLayoutManager(this, initialSpanCount)
        gridLayoutManager = glm
        viewBinding.rvImageGrid.layoutManager = glm
        viewBinding.rvImageGrid.adapter = imageAdapter
        viewBinding.rvImageGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    previewPopup?.dismiss()
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
                loadImagesForTab(tabKey)
                imeTabDropdown?.setSelectedTab(tabKey)
            }
        )
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

        loadImagesForTab(initialEffectiveTab)
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

    override fun onEvaluateFullscreenMode(): Boolean = false

    override fun onEvaluateInputViewShown(): Boolean = true

    override fun onShowInputRequested(flags: Int, configChange: Boolean): Boolean = true

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        TestLog.i(MODULE, "onStartInput: pkg=${attribute?.packageName}, inputType=${attribute?.inputType}, restarting=$restarting")
        requestShowSelf(0)
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        isImeShowing = true
        com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()
        TestLog.i(MODULE, "==================== onStartInputView (restarting=$restarting) ====================")

        // 进程内直连信号：0ms 秒级通知悬浮球 IME 已可见
        TestAccessibilityService.notifyImeLifecycle(true)

        applyKeyboardConfigLayout()
        applyTheme()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestShowSelf(0)
        }

        EditorInfoDumper.dump(this, info)

        val targetTab = tabBar?.getEffectiveTab() ?: "ALL"
        loadImagesForTab(targetTab, force = true)
    }

    override fun onWindowShown() {
        super.onWindowShown()
        isImeShowing = true
        com.suzu.test.floating.ImeSearchStateHolder.onSearchImeShown()

        // 窗口生命周期信号比无障碍窗口列表更及时，避免窗口列表残留导致状态误判。
        TestAccessibilityService.notifyImeLifecycle(true)

        val targetTab = tabBar?.getEffectiveTab() ?: "ALL"
        loadImagesForTab(targetTab, force = false)
    }

    override fun onWindowHidden() {
        super.onWindowHidden()

        if (com.suzu.test.floating.ImeSearchStateHolder.isSearchLaunching()) {
            TestLog.i(MODULE, "onWindowHidden: 处于搜索拉起保护期内，忽略瞬态隐藏信号")
            return
        }

        // 某些系统不会稳定触发 onFinishInputView，但会回调 onWindowHidden。
        TestAccessibilityService.notifyImeLifecycle(false)

        try {
            Glide.get(this).trimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        } catch (e: Exception) {
            TestLog.w(MODULE, "Glide.trimMemory 异常: ${e.message}")
        }

        val wasShowing = isImeShowing
        isImeShowing = false
        destroySearchCategory()

        if (wasShowing) {
            TestLog.i(MODULE, "onWindowHidden: 触发兜底自动切回原输入法...")
            autoRestorePreviousIme()
        }
    }

    private fun applyKeyboardConfigLayout() {
        val targetSpanCount = KeyboardConfig.getSpanCount(this)
        if (gridLayoutManager?.spanCount != targetSpanCount) {
            gridLayoutManager?.spanCount = targetSpanCount
            TestLog.i(MODULE, "应用键盘列数配置: spanCount=$targetSpanCount")
        }

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

        binding?.btnTabDropdown?.let { dropdownBtn ->
            dropdownBtn.setImageResource(KeyboardConfig.getDropdownIconRes(this))
            configureHeaderButton(dropdownBtn, tabSizePx, buttonPaddingPx)
        }

        binding?.btnExit?.let { exitBtn ->
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
            imageAdapter.notifyDataSetChanged()
            TestLog.i(MODULE, "应用键盘主题配置: isDark=${theme.isDark}")
        }
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        previewPopup?.dismiss()
        imeTabDropdown?.close()

        if (com.suzu.test.floating.ImeSearchStateHolder.isSearchLaunching()) {
            TestLog.i(MODULE, "onFinishInputView: 处于搜索拉起保护期内，忽略瞬态完成信号 (finishingInput=$finishingInput)")
            super.onFinishInputView(finishingInput)
            return
        }

        lastLoadedTabKey = null
        // 进程内直连信号：通知悬浮球 IME 已隐藏
        TestAccessibilityService.notifyImeLifecycle(false)
        super.onFinishInputView(finishingInput)
        TestLog.i(MODULE, "onFinishInputView: 键盘收起，自动执行静默切回原输入法... (finishingInput=$finishingInput)")
        if (isImeShowing) {
            isImeShowing = false
            destroySearchCategory()
        }
        autoRestorePreviousIme()
    }

    override fun onFinishInput() {
        previewPopup?.dismiss()

        if (com.suzu.test.floating.ImeSearchStateHolder.isSearchLaunching()) {
            TestLog.i(MODULE, "onFinishInput: 处于搜索拉起保护期内，忽略会话结束信号")
            super.onFinishInput()
            return
        }

        lastLoadedTabKey = null
        TestAccessibilityService.notifyImeLifecycle(false)
        super.onFinishInput()
        TestLog.i(MODULE, "onFinishInput: 会话结束，自动执行静默切回原输入法...")
        autoRestorePreviousIme()
    }

    private fun isSelfIme(imeId: String?): Boolean {
        if (imeId.isNullOrEmpty()) return false
        val fullId = packageName + "/" + TestImageIME::class.java.name
        val shortId = ComponentName(packageName, TestImageIME::class.java.name).flattenToShortString()
        val realId = TestAccessibilityService.instance?.findTestImeId()
        return imeId == fullId || imeId == shortId || (realId != null && imeId == realId) || imeId.startsWith("$packageName/")
    }

    private fun autoRestorePreviousIme() {
        // 防误切：若当前默认输入法已经不是自研 IME（说明已被切回或用户主动切换到第三方输入法），
        // 则跳过自动恢复，避免重复切换或误切造成闪烁。
        val currentIme = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )
        if (!currentIme.isNullOrEmpty() && !isSelfIme(currentIme)) {
            TestLog.i(MODULE, "autoRestorePreviousIme: 默认输入法已非自研 IME ($currentIme)，跳过自动恢复")
            return
        }

        val service = TestAccessibilityService.instance
        if (service != null && TestAccessibilityService.isAlive()) {
            service.restorePreviousIme()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                switchToPreviousInputMethod()
            } else {
                @Suppress("DEPRECATION")
                switchToPreviousInputMethod()
            }
        }
    }

    private fun loadImagesForTab(tabKey: String, force: Boolean = false) {
        if (!force && tabKey == lastLoadedTabKey && imageAdapter.itemCount > 0) {
            return
        }
        lastLoadedTabKey = tabKey
        loadImagesJob?.cancel()
        loadImagesJob = serviceScope.launch {
            val list = dataSource.loadResources(tabKey)
            if (!isActive) return@launch
            imageAdapter.submitList(list) {
                binding?.rvImageGrid?.post {
                    gridLayoutManager?.scrollToPositionWithOffset(0, 0)
                }
            }
            val hintView = binding?.tvEmptyLibraryHint
            if (list.isEmpty()) {
                hintView?.visibility = View.VISIBLE
                hintView?.text = when {
                    tabKey == "SEARCH" -> "未找到相关表情"
                    tabKey == "RECENT" -> "还没有发送记录"
                    tabKey == "ALL" -> "资源库为空，请在 App 内导入表情"
                    else -> "该分类暂无图片"
                }
            } else {
                hintView?.visibility = View.GONE
            }
        }
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
            } catch (e: Exception) {
                TestLog.e(MODULE, "更新资源使用记录异常: ${e.message}", e)
            }
        }
    }

    fun exitAndRestoreIme() {
        TestLog.i(MODULE, ">>> 触发 [退出] 恢复原输入法")
        if (isImeShowing) {
            isImeShowing = false
            destroySearchCategory()
        }
        val service = TestAccessibilityService.instance
        val restored = service != null && TestAccessibilityService.isAlive() && service.restorePreviousIme()
        if (!restored) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                switchToPreviousInputMethod()
            } else {
                @Suppress("DEPRECATION")
                switchToPreviousInputMethod()
            }
        }
        requestHideSelf(0)
    }

    override fun onDestroy() {
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
        serviceScope.cancel()
        imageSender.destroy()
        dbExecutor.shutdown()
        binding = null
    }
}
