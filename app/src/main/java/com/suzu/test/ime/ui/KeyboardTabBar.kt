package com.suzu.test.ime.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.ThemeApplier
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class KeyboardTabBar(
    private val context: Context,
    private val container: LinearLayout,
    private val scope: CoroutineScope,
    private val onTabSelected: (tabKey: String) -> Unit
) {

    companion object {
        private const val SP_NAME = "ime_prefs"
        private const val KEY_LAST_TAB = "last_selected_tab"
        private const val KEY_TAB_SCROLL_X = "tab_scroll_x"
        private const val TAB_ICON_SCALE = 0.72f
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    var currentTab: String = (prefs.getString(KEY_LAST_TAB, "RECENT") ?: "RECENT").let {
        if (it == "SEARCH") "RECENT" else it
    }
        private set

    private var lastNotifiedTab: String = currentTab
    private var observeJob: Job? = null
    private var searchJob: Job? = null
    private var cachedCategories: List<CategoryEntity>? = null
    private var lastStructureKey: String? = null
    private var pendingScrollX: Int? = prefs.getInt(KEY_TAB_SCROLL_X, 0)
    private var renderVersion = 0
    private var destroyed = false
    private val tabViewMap = mutableMapOf<String, View>()
    private val resourcesDir = File(context.filesDir, "resources")
    private val thumbnailPreloader = com.suzu.test.ui.view.CategoryThumbnailPreloader(
        context, scope, com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC
    )

    var onTabsStructureChanged: ((tabs: List<String>, selectedTab: String) -> Unit)? = null

    fun hasLoadedCategories(): Boolean = cachedCategories != null

    fun saveNavigationState() {
        if (!hasLoadedCategories() || currentTab == "SEARCH") return
        val scrollX = pendingScrollX ?: (container.parent as? android.widget.HorizontalScrollView)?.scrollX ?: 0
        prefs.edit().putString(KEY_LAST_TAB, currentTab)
            .putInt(KEY_TAB_SCROLL_X, scrollX).apply()
    }

    fun orderedTabs(): List<String> = buildList {
        if (!com.suzu.test.floating.ImeSearchStateHolder.searchQuery.value.isNullOrBlank()) add("SEARCH")
        if (KeyboardConfig.isRecentTabEnabled(context)) add("RECENT")
        if (KeyboardConfig.isAllTabEnabled(context)) add("ALL")
        cachedCategories.orEmpty().forEach { add("cat:${it.id}") }
    }

    fun selectAdjacent(step: Int) {
        val next = com.suzu.test.ui.view.adjacentCategory(orderedTabs(), getEffectiveTab(), step) ?: return
        selectTab(next)
    }

    fun start() {
        observeJob?.cancel()
        observeJob = scope.launch {
            val db = DatabaseProvider.getDatabase(context)
            db.categoryDao().getAllCategoriesFlow().collect { categories ->
                withContext(Dispatchers.Main) {
                    cachedCategories = categories
                    val structureKey = buildStructureKey(categories)
                    val isStructureChanged = structureKey != lastStructureKey
                    lastStructureKey = structureKey

                    val newEffectiveTab = getEffectiveTab()
                    if (currentTab == "SEARCH" && newEffectiveTab != "SEARCH") {
                        pendingScrollX = prefs.getInt(KEY_TAB_SCROLL_X, 0)
                    }
                    currentTab = newEffectiveTab
                    if (newEffectiveTab != lastNotifiedTab) {
                        if (newEffectiveTab != "SEARCH") {
                            prefs.edit().putString(KEY_LAST_TAB, newEffectiveTab).apply()
                        }
                        lastNotifiedTab = newEffectiveTab
                        onTabSelected(newEffectiveTab)
                    }

                    if (isStructureChanged) {
                        render()
                        onTabsStructureChanged?.invoke(orderedTabs(), newEffectiveTab)
                    } else {
                        updateSelectionState(newEffectiveTab)
                    }
                }
            }
        }

        searchJob?.cancel()
        searchJob = scope.launch {
            com.suzu.test.floating.ImeSearchStateHolder.searchQuery.collectLatest { query ->
                withContext(Dispatchers.Main) {
                    if (!query.isNullOrBlank()) {
                        saveNavigationState()
                        currentTab = "SEARCH"
                        lastNotifiedTab = "SEARCH"
                        render()
                        if (hasLoadedCategories()) onTabsStructureChanged?.invoke(orderedTabs(), "SEARCH")
                        onTabSelected("SEARCH")
                        scrollTabToSelection("SEARCH")
                    } else {
                        if (currentTab == "SEARCH") {
                            currentTab = prefs.getString(KEY_LAST_TAB, "RECENT") ?: "RECENT"
                            pendingScrollX = prefs.getInt(KEY_TAB_SCROLL_X, 0)
                            val fallback = getEffectiveTab()
                            currentTab = fallback
                            lastNotifiedTab = fallback
                            if (fallback != "SEARCH") {
                                prefs.edit().putString(KEY_LAST_TAB, fallback).apply()
                            }
                            render()
                            if (hasLoadedCategories()) onTabsStructureChanged?.invoke(orderedTabs(), fallback)
                            onTabSelected(fallback)
                        } else {
                            render()
                            if (hasLoadedCategories()) onTabsStructureChanged?.invoke(orderedTabs(), currentTab)
                        }
                    }
                }
            }
        }
    }

    private fun buildStructureKey(categories: List<CategoryEntity>): String {
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val showAll = KeyboardConfig.isAllTabEnabled(context)
        val searchQuery = com.suzu.test.floating.ImeSearchStateHolder.searchQuery.value ?: ""
        val sb = StringBuilder("s:$searchQuery|r:$showRecent|a:$showAll|")
        for (cat in categories) {
            sb.append("${cat.id}_${cat.name}_${cat.sortOrder}_${cat.iconPath}|")
        }
        return sb.toString()
    }

    fun refreshTheme() {
        render()
    }

    fun getEffectiveTab(): String {
        val hasSearch = !com.suzu.test.floating.ImeSearchStateHolder.searchQuery.value.isNullOrBlank()
        if (!hasLoadedCategories() && hasSearch && currentTab == "SEARCH") return "SEARCH"
        return restoredImeTab(
            currentTab,
            prefs.getString(KEY_LAST_TAB, "RECENT") ?: "RECENT",
            if (hasLoadedCategories()) orderedTabs() else null
        )
    }

    private fun render() {
        // 不用尚未加载的临时标签列表覆盖之前的收藏夹和滚动位置。
        if (!hasLoadedCategories() || destroyed) return
        thumbnailPreloader.warm(orderedTabs(), getEffectiveTab())
        val scrollView = container.parent as? android.widget.HorizontalScrollView
        val savedScrollX = pendingScrollX ?: scrollView?.scrollX ?: 0
        pendingScrollX = savedScrollX
        val version = ++renderVersion

        container.removeAllViews()
        tabViewMap.clear()

        val theme = KeyboardTheme.current(context)
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val showAll = KeyboardConfig.isAllTabEnabled(context)
        val hasSearch = !com.suzu.test.floating.ImeSearchStateHolder.searchQuery.value.isNullOrBlank()
        val effectiveTab = getEffectiveTab()

        // 0. [搜索结果]（若存在，新开出一个收藏夹在第一个）
        if (hasSearch) {
            val searchView = createSpecialTabView(R.drawable.ic_search_24, effectiveTab == "SEARCH", theme) {
                selectTab("SEARCH")
            }
            tabViewMap["SEARCH"] = searchView
            container.addView(searchView)
        }

        // 1. [常用]
        if (showRecent) {
            val view = createSpecialTabView(R.drawable.ic_tab_recent, effectiveTab == "RECENT", theme) {
                selectTab("RECENT")
            }
            tabViewMap["RECENT"] = view
            container.addView(view)
        }

        // 2. [全部]
        if (showAll) {
            val allView = createSpecialTabView(R.drawable.ic_tab_all, effectiveTab == "ALL", theme) {
                selectTab("ALL")
            }
            tabViewMap["ALL"] = allView
            container.addView(allView)
        }

        // 3. 真实分类
        val categories = cachedCategories ?: emptyList()
        for (cat in categories) {
            val tabKey = "cat:${cat.id}"
            val isSelected = effectiveTab == tabKey
            val catView = createCategoryTabView(cat, isSelected, theme) {
                selectTab(tabKey)
            }
            tabViewMap[tabKey] = catView
            container.addView(catView)
        }

        scrollView?.doOnNextLayout {
            if (destroyed || version != renderVersion) return@doOnNextLayout
            scrollView.scrollTo(savedScrollX, 0)
            pendingScrollX = null
        }
    }

    private fun updateSelectionState(effectiveTab: String): Boolean {
        if (!tabViewMap.containsKey(effectiveTab)) {
            render()
            return true
        }

        val theme = KeyboardTheme.current(context)
        tabViewMap.forEach { (key, view) ->
            val isSelected = key == effectiveTab
            view.isSelected = isSelected
            val tvName = view.findViewById<TextView?>(R.id.tvTabName)
            if (tvName != null && tvName.visibility == View.VISIBLE) {
                tvName.setTextColor(if (isSelected) theme.tabTextSelected else theme.tabTextUnselected)
            }
        }
        return true
    }

    fun selectTab(tabKey: String, notify: Boolean = true) {
        if (currentTab == tabKey && lastNotifiedTab == tabKey) {
            scrollTabToSelection(tabKey)
            return
        }
        currentTab = tabKey
        lastNotifiedTab = tabKey
        if (tabKey != "SEARCH") {
            prefs.edit().putString(KEY_LAST_TAB, tabKey).apply()
        }
        updateSelectionState(tabKey)
        scrollTabToSelection(tabKey)
        if (notify) {
            onTabSelected(tabKey)
        }
        thumbnailPreloader.warm(orderedTabs(), tabKey)
    }

    private fun scrollTabToSelection(tabKey: String) {
        val scrollView = container.parent as? android.widget.HorizontalScrollView ?: return
        val tab = tabViewMap[tabKey] ?: return

        tab.post {
            if (destroyed || currentTab != tabKey || tabViewMap[tabKey] !== tab) return@post
            val viewportLeft = scrollView.scrollX
            val viewportRight = viewportLeft + scrollView.width
            val tabLeft = tab.left
            val tabRight = tab.right

            if (tabLeft < viewportLeft || tabRight > viewportRight) {
                val targetLeft = tabLeft - (scrollView.width - tab.width) / 2
                scrollView.smoothScrollTo(targetLeft.coerceAtLeast(0), 0)
            }
        }
    }

    private fun createSpecialTabView(iconResId: Int, isSelected: Boolean, theme: KeyboardTheme, onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_keyboard_tab, container, false)
        applyTabDimensions(view)

        val ivIcon = view.findViewById<ImageView>(R.id.ivTabIcon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(iconResId)
        ivIcon.imageTintList = ColorStateList.valueOf(theme.iconColor)

        view.background = ThemeApplier.createTabBackground(theme, context.resources.displayMetrics.density)
        view.isSelected = isSelected
        view.setOnClickListener { onClick() }
        return view
    }

    private fun createCategoryTabView(category: CategoryEntity, isSelected: Boolean, theme: KeyboardTheme, onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_keyboard_tab, container, false)
        applyTabDimensions(view)

        val ivIcon = view.findViewById<ImageView>(R.id.ivTabIcon)
        val tvName = view.findViewById<TextView>(R.id.tvTabName)

        view.background = ThemeApplier.createTabBackground(theme, context.resources.displayMetrics.density)
        view.isSelected = isSelected

        when (val result = CategoryIconResolver.resolve(category.iconPath)) {
            is CategoryIconResult.Text -> {
                ivIcon.visibility = View.GONE
                tvName.visibility = View.VISIBLE
                tvName.text = result.content
                tvName.setTextColor(if (isSelected) theme.tabTextSelected else theme.tabTextUnselected)
            }
            is CategoryIconResult.ImageFile -> {
                ivIcon.visibility = View.VISIBLE
                tvName.visibility = View.GONE
                ivIcon.imageTintList = null
                Glide.with(ivIcon).asBitmap()
                    .load(File(ivIcon.context.filesDir, result.relativePath))
                    .error(R.drawable.ic_category_default).centerCrop().into(ivIcon)
            }
            is CategoryIconResult.Resource -> {
                ivIcon.visibility = View.VISIBLE
                tvName.visibility = View.GONE
                ivIcon.imageTintList = null
                loadResourceIcon(ivIcon, result.resourceId, theme)
            }
            else -> {
                ivIcon.visibility = View.VISIBLE
                tvName.visibility = View.GONE
                ivIcon.setImageResource(R.drawable.ic_category_default)
                ivIcon.imageTintList = ColorStateList.valueOf(theme.iconColor)
            }
        }

        view.setOnClickListener { onClick() }
        return view
    }

    private fun applyTabDimensions(view: View) {
        val tabSizeDp = KeyboardConfig.getTabIconSizeDp(context)
        val tabSizePx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            tabSizeDp.toFloat(),
            context.resources.displayMetrics
        ).toInt()

        val lp = view.layoutParams ?: LinearLayout.LayoutParams(tabSizePx, tabSizePx)
        lp.width = tabSizePx
        lp.height = tabSizePx
        view.layoutParams = lp

        val iconSizePx = (tabSizePx * TAB_ICON_SCALE).toInt()
        val ivIcon = view.findViewById<ImageView?>(R.id.ivTabIcon)
        ivIcon?.let {
            val iconLp = it.layoutParams
            if (iconLp != null) {
                iconLp.width = iconSizePx
                iconLp.height = iconSizePx
                it.layoutParams = iconLp
            }
        }

        val tvName = view.findViewById<TextView?>(R.id.tvTabName)
        tvName?.let {
            val scaledTextSizeSp = 18f * (tabSizeDp.toFloat() / KeyboardConfig.DEFAULT_TAB_ICON_SIZE_DP)
            it.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, scaledTextSizeSp)
        }
    }

    private fun loadResourceIcon(imageView: ImageView, resourceId: Long, theme: KeyboardTheme) {
        scope.launch {
            val resource = withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(context)
                db.resourceDao().getById(resourceId)
            }
            val file = if (resource != null) File(resourcesDir, resource.filename) else null
            if (file != null && file.exists()) {
                imageView.imageTintList = null
                Glide.with(context)
                    .asBitmap()
                    .load(file)
                    .centerCrop()
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_category_default)
                imageView.imageTintList = ColorStateList.valueOf(theme.iconColor)
            }
        }
    }

    fun getTabSizeDp(): Int = KeyboardConfig.getTabIconSizeDp(context)

    fun destroy() {
        saveNavigationState()
        destroyed = true
        thumbnailPreloader.cancel()
        observeJob?.cancel()
        observeJob = null
        searchJob?.cancel()
        searchJob = null
    }
}
