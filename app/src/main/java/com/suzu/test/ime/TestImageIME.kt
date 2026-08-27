package com.suzu.test.ime

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.BuildConfig
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.databinding.ViewImeKeyboardBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.RecentHistoryEntity
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.diag.EditorInfoDumper
import com.suzu.test.ime.sender.ImageSender
import com.suzu.test.ime.ui.KeyboardTabBar
import com.suzu.test.ime.config.KeyboardConfig
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
        private val QQ_FAMILY = setOf("com.tencent.mobileqq", "com.tencent.tim", "com.tencent.qqlite")
    }

    private var binding: ViewImeKeyboardBinding? = null
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private lateinit var dataSource: KeyboardDataSource
    private lateinit var imageSender: ImageSender
    private var tabBar: KeyboardTabBar? = null
    private val dbExecutor = Executors.newSingleThreadExecutor()
    private lateinit var imageAdapter: ImageAdapter
    private var gridLayoutManager: GridLayoutManager? = null
    private var previewPopup: ImagePreviewPopup? = null
    private var loadImagesJob: kotlinx.coroutines.Job? = null
    private var lastLoadedTabKey: String? = null

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

        tabBar?.destroy()
        val tb = KeyboardTabBar(
            context = this,
            container = viewBinding.llTabBarContainer,
            scope = serviceScope,
            onTabSelected = { tabKey -> loadImagesForTab(tabKey) }
        )
        tabBar = tb
        val initialEffectiveTab = tb.getEffectiveTab()
        tb.start()

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

        if (targetPkg in QQ_FAMILY) {
            TestLog.i(MODULE, "智能路由 -> 命中 QQ 家族 ($targetPkg)，走 H1β 私有协议直发")
            imageSender.executeH1b(item, targetPkg, { currentInputConnection }, onSuccessCallback)
        } else {
            TestLog.i(MODULE, "智能路由 -> 命中 微信及其他社交应用 ($targetPkg)，走剪贴板注入与标准粘贴")
            imageSender.executeE(item, targetPkg, { currentInputConnection }, onSuccessCallback)
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        TestLog.i(MODULE, "==================== onStartInputView (restarting=$restarting) ====================")

        // 进程内直连信号：0ms 秒级通知悬浮球 IME 已可见
        com.suzu.test.floating.ImeVisibilityBus.notifyImeVisibilityChanged(true)

        applyKeyboardConfigLayout()
        applyTheme()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestShowSelf(0)
        }

        EditorInfoDumper.dump(this, info)

        val targetTab = tabBar?.getEffectiveTab() ?: "ALL"
        if (targetTab != lastLoadedTabKey) {
            loadImagesForTab(targetTab)
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

        binding?.btnExit?.let { exitBtn ->
            val lp = exitBtn.layoutParams
            if (lp != null && (lp.width != tabSizePx || lp.height != tabSizePx)) {
                lp.width = tabSizePx
                lp.height = tabSizePx
                exitBtn.layoutParams = lp
                exitBtn.requestLayout()
            }
        }

        binding?.llTabHeaderBar?.let { headerBar ->
            val lp = headerBar.layoutParams
            if (lp != null && lp.height != tabSizePx) {
                lp.height = tabSizePx
                headerBar.layoutParams = lp
                headerBar.requestLayout()
            }
        }
    }

    private fun applyTheme() {
        val theme = KeyboardTheme.current(this)
        binding?.let { b ->
            ThemeApplier.applyTo(b, theme)
            tabBar?.refreshTheme()
            imageAdapter.notifyDataSetChanged()
            TestLog.i(MODULE, "应用键盘主题配置: isDark=${theme.isDark}")
        }
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        previewPopup?.dismiss()
        lastLoadedTabKey = null
        // 进程内直连信号：通知悬浮球 IME 已隐藏
        com.suzu.test.floating.ImeVisibilityBus.notifyImeVisibilityChanged(false)
        super.onFinishInputView(finishingInput)
        TestLog.i(MODULE, "onFinishInputView: 键盘收起，自动执行静默切回原输入法...")
        autoRestorePreviousIme()
    }

    override fun onFinishInput() {
        previewPopup?.dismiss()
        lastLoadedTabKey = null
        com.suzu.test.floating.ImeVisibilityBus.notifyImeVisibilityChanged(false)
        super.onFinishInput()
        TestLog.i(MODULE, "onFinishInput: 会话结束，自动执行静默切回原输入法...")
        autoRestorePreviousIme()
    }

    private fun autoRestorePreviousIme() {
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

    private fun loadImagesForTab(tabKey: String) {
        lastLoadedTabKey = tabKey
        loadImagesJob?.cancel()
        loadImagesJob = serviceScope.launch {
            val list = dataSource.loadResources(tabKey)
            if (!isActive) return@launch
            imageAdapter.submitList(list)
            val hintView = binding?.tvEmptyLibraryHint
            if (list.isEmpty()) {
                hintView?.visibility = View.VISIBLE
                hintView?.text = when {
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
        val service = TestAccessibilityService.instance
        if (service != null && TestAccessibilityService.isAlive() && service.restorePreviousIme()) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            switchToPreviousInputMethod()
        } else {
            @Suppress("DEPRECATION")
            switchToPreviousInputMethod()
        }
    }

    override fun onDestroy() {
        previewPopup?.dismiss()
        previewPopup = null
        super.onDestroy()
        TestLog.i(MODULE, "onDestroy: SuzuEmojy 销毁")
        tabBar?.destroy()
        tabBar = null
        serviceScope.cancel()
        imageSender.destroy()
        dbExecutor.shutdown()
        binding = null
    }
}
