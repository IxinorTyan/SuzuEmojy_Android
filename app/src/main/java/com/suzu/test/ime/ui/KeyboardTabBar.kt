package com.suzu.test.ime.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.content.res.ColorStateList
import android.widget.TextView
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
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    var currentTab: String = prefs.getString(KEY_LAST_TAB, "RECENT") ?: "RECENT"
        private set

    private var lastNotifiedTab: String = currentTab
    private var observeJob: Job? = null
    private var cachedCategories: List<CategoryEntity>? = null
    private var lastStructureKey: String? = null
    private val tabViewMap = mutableMapOf<String, View>()
    private val resourcesDir = File(context.filesDir, "resources")

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
                    if (newEffectiveTab != lastNotifiedTab) {
                        currentTab = newEffectiveTab
                        prefs.edit().putString(KEY_LAST_TAB, newEffectiveTab).apply()
                        lastNotifiedTab = newEffectiveTab
                        onTabSelected(newEffectiveTab)
                    }

                    if (isStructureChanged) {
                        render()
                    } else {
                        updateSelectionState(newEffectiveTab)
                        scrollTabToSelection(newEffectiveTab)
                    }
                }
            }
        }
    }

    private fun buildStructureKey(categories: List<CategoryEntity>): String {
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val sb = StringBuilder("r:$showRecent|")
        for (cat in categories) {
            sb.append("${cat.id}_${cat.name}_${cat.sortOrder}_${cat.iconPath}|")
        }
        return sb.toString()
    }

    fun refreshTheme() {
        render()
    }

    fun getEffectiveTab(): String {
        val categories = cachedCategories
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)

        // 未加载完成时：直接返回 currentTab 保留用户意图，若为 RECENT 但 showRecent=false 则回退 "ALL"
        if (categories == null) {
            return if (!showRecent && currentTab == "RECENT") "ALL" else currentTab
        }

        // 加载完成后：严格校验合法性
        val availableTabs = mutableListOf<String>()
        if (showRecent) {
            availableTabs.add("RECENT")
        }
        availableTabs.add("ALL")
        for (cat in categories) {
            availableTabs.add("cat:${cat.id}")
        }

        return if (currentTab in availableTabs) {
            currentTab
        } else {
            "ALL"
        }
    }

    private fun render() {
        val scrollView = container.parent as? android.widget.HorizontalScrollView
        val savedScrollX = scrollView?.scrollX ?: 0

        container.removeAllViews()
        tabViewMap.clear()

        val theme = KeyboardTheme.current(context)
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val effectiveTab = getEffectiveTab()

        // 1. [常用]
        if (showRecent) {
            val view = createSpecialTabView(R.drawable.ic_tab_recent, effectiveTab == "RECENT", theme) {
                selectTab("RECENT")
            }
            tabViewMap["RECENT"] = view
            container.addView(view)
        }

        // 2. [全部]
        val allView = createSpecialTabView(R.drawable.ic_tab_all, effectiveTab == "ALL", theme) {
            selectTab("ALL")
        }
        tabViewMap["ALL"] = allView
        container.addView(allView)

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

        scrollView?.post {
            scrollView.scrollTo(savedScrollX, 0)
            scrollTabToSelection(effectiveTab)
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

    fun selectTab(tabKey: String) {
        if (currentTab == tabKey && lastNotifiedTab == tabKey) {
            scrollTabToSelection(tabKey)
            return
        }
        currentTab = tabKey
        lastNotifiedTab = tabKey
        prefs.edit().putString(KEY_LAST_TAB, tabKey).apply()
        updateSelectionState(tabKey)
        scrollTabToSelection(tabKey)
        onTabSelected(tabKey)
    }

    private fun scrollTabToSelection(tabKey: String) {
        val scrollView = container.parent as? android.widget.HorizontalScrollView ?: return
        val tab = tabViewMap[tabKey] ?: return

        tab.post {
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

        val iconSizePx = (tabSizePx * 0.5f).toInt()
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
        observeJob?.cancel()
        observeJob = null
    }
}
