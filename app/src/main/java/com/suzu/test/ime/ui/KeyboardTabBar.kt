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

    private var observeJob: Job? = null
    private var cachedCategories: List<CategoryEntity> = emptyList()
    private val resourcesDir = File(context.filesDir, "resources")

    fun start() {
        observeJob = scope.launch {
            val db = DatabaseProvider.getDatabase(context)
            db.categoryDao().getAllCategoriesFlow().collectLatest { categories ->
                withContext(Dispatchers.Main) {
                    cachedCategories = categories
                    render()
                }
            }
        }
    }

    fun refreshTheme() {
        render()
    }

    fun getEffectiveTab(): String {
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val availableTabs = mutableListOf<String>()
        if (showRecent) {
            availableTabs.add("RECENT")
        }
        availableTabs.add("ALL")
        for (cat in cachedCategories) {
            availableTabs.add("cat:${cat.id}")
        }

        return if (currentTab in availableTabs) {
            currentTab
        } else {
            "ALL"
        }
    }

    private fun render() {
        container.removeAllViews()
        val theme = KeyboardTheme.current(context)
        val showRecent = KeyboardConfig.isRecentTabEnabled(context)
        val effectiveTab = getEffectiveTab()

        // 1. [常用]
        if (showRecent) {
            container.addView(createSpecialTabView(R.drawable.ic_tab_recent, effectiveTab == "RECENT", theme) {
                selectTab("RECENT")
            })
        }

        // 2. [全部]
        container.addView(createSpecialTabView(R.drawable.ic_tab_all, effectiveTab == "ALL", theme) {
            selectTab("ALL")
        })

        // 3. 真实分类
        for (cat in cachedCategories) {
            val tabKey = "cat:${cat.id}"
            val isSelected = effectiveTab == tabKey
            container.addView(createCategoryTabView(cat, isSelected, theme) {
                selectTab(tabKey)
            })
        }
    }

    private fun selectTab(tabKey: String) {
        currentTab = tabKey
        prefs.edit().putString(KEY_LAST_TAB, tabKey).apply()
        render()
        onTabSelected(tabKey)
    }

    private fun createSpecialTabView(iconResId: Int, isSelected: Boolean, theme: KeyboardTheme, onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_keyboard_tab, container, false)
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

    fun destroy() {
        observeJob?.cancel()
        observeJob = null
    }
}
