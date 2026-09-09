package com.suzu.test.ime.ui

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.ThemeApplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ImeTabDropdownController(
    private val context: Context,
    private val button: ImageButton,
    private val panel: ScrollView,
    private val grid: GridLayout,
    private val scope: CoroutineScope,
    private val tabSizeDpProvider: () -> Int,
    private val onTabSelected: (String) -> Unit
) {

    companion object {
        private const val RECENT = "RECENT"
        private const val ALL = "ALL"
        private const val MIN_COLUMN_COUNT = 3
    }

    private val resourcesDir = File(context.filesDir, "resources")
    private var observeJob: Job? = null
    private var categories: List<CategoryEntity> = emptyList()
    private var selectedTab = ALL

    init {
        panel.visibility = View.GONE
        button.setOnClickListener { setExpanded(panel.visibility != View.VISIBLE) }
        grid.addOnLayoutChangeListener { _, left, _, right, _, oldLeft, _, oldRight, _ ->
            if (right - left != oldRight - oldLeft && panel.visibility == View.VISIBLE) {
                applyColumnCountAndHeight()
            }
        }
    }

    fun start() {
        observeJob?.cancel()
        observeJob = scope.launch {
            DatabaseProvider.getDatabase(context).categoryDao().getAllCategoriesFlow()
                .collectLatest { newCategories ->
                    withContext(Dispatchers.Main) {
                        categories = newCategories
                        render()
                    }
                }
        }
    }

    fun setSelectedTab(tabKey: String) {
        selectedTab = tabKey
        updateSelection()
    }

    fun setExpanded(expanded: Boolean) {
        panel.visibility = if (expanded) View.VISIBLE else View.GONE
        button.setImageResource(R.drawable.ic_keyboard_expand_24)
        button.contentDescription = "展开收藏夹"
        if (expanded) render()
    }

    fun close() {
        setExpanded(false)
    }

    private fun render() {
        grid.removeAllViews()
        grid.columnCount = calculateColumnCount()
        val theme = KeyboardTheme.current(context)

        if (KeyboardConfig.isRecentTabEnabled(context)) {
            addTab(
                key = RECENT,
                icon = R.drawable.ic_tab_recent,
                theme = theme
            )
        }
        addTab(key = ALL, icon = R.drawable.ic_tab_all, theme = theme)

        categories.forEach { category ->
            addCategoryTab(category, theme)
        }

        applyColumnCountAndHeight()
    }

    private fun applyColumnCountAndHeight() {
        val targetColumnCount = calculateColumnCount()
        if (grid.columnCount != targetColumnCount) {
            grid.columnCount = targetColumnCount
        }
        updatePanelHeight()
    }

    private fun calculateColumnCount(): Int {
        // 下拉菜单的列数必须根据实际可用宽度计算，不能固定上限为 8 列。
        // 当收藏夹图标尺寸较小时，固定列数会导致整行右侧留下空白。
        val gridWidth = grid.width.takeIf { it > 0 }
            ?: grid.measuredWidth.takeIf { it > 0 }
            ?: context.resources.displayMetrics.widthPixels
        val contentWidth = (gridWidth - grid.paddingLeft - grid.paddingRight).coerceAtLeast(1)
        val minItemWidth = dp(tabSizeDpProvider()).coerceAtLeast(1) + dp(4)
        return (contentWidth / minItemWidth)
            .coerceAtLeast(MIN_COLUMN_COUNT)
            .coerceAtMost(contentWidth.coerceAtLeast(1))
    }

    private fun updatePanelHeight() {
        val columnCount = grid.columnCount.coerceAtLeast(1)
        val itemCount = grid.childCount
        val rowCount = ((itemCount + columnCount - 1) / columnCount).coerceIn(1, 3)

        val itemSize = dp(tabSizeDpProvider())
        val itemSpacing = dp(4)
        val gridPadding = dp(4)
        val height = gridPadding + rowCount * (itemSize + itemSpacing)

        panel.layoutParams = panel.layoutParams.apply {
            this.height = height
        }
        panel.requestLayout()
    }

    private fun addTab(key: String, icon: Int, theme: KeyboardTheme) {
        val item = createItem(theme)
        val iconView = item.findViewById<ImageView>(R.id.ivTabIcon)
        iconView.visibility = View.VISIBLE
        iconView.setImageResource(icon)
        iconView.imageTintList = ColorStateList.valueOf(theme.iconColor)
        item.tag = key
        item.isSelected = selectedTab == key
        item.setOnClickListener { selectAndClose(key) }
        grid.addView(item)
    }

    private fun addCategoryTab(category: CategoryEntity, theme: KeyboardTheme) {
        val item = createItem(theme)
        val iconView = item.findViewById<ImageView>(R.id.ivTabIcon)
        val textView = item.findViewById<TextView>(R.id.tvTabName)

        when (val result = CategoryIconResolver.resolve(category.iconPath)) {
            is CategoryIconResult.Text -> {
                iconView.visibility = View.GONE
                textView.visibility = View.VISIBLE
                textView.text = result.content
                textView.setTextColor(if (selectedTab == "cat:${category.id}") {
                    theme.tabTextSelected
                } else {
                    theme.tabTextUnselected
                })
            }

            is CategoryIconResult.Resource -> {
                iconView.visibility = View.VISIBLE
                textView.visibility = View.GONE
                loadResourceIcon(iconView, result.resourceId, theme)
            }

            else -> {
                iconView.visibility = View.VISIBLE
                textView.visibility = View.GONE
                iconView.setImageResource(R.drawable.ic_category_default)
                iconView.imageTintList = ColorStateList.valueOf(theme.iconColor)
            }
        }

        item.tag = "cat:${category.id}"
        item.isSelected = selectedTab == item.tag
        item.setOnClickListener { selectAndClose("cat:${category.id}") }
        grid.addView(item)
    }

    private fun createItem(theme: KeyboardTheme): View {
        val item = LayoutInflater.from(context)
            .inflate(R.layout.item_keyboard_tab, grid, false)
        val size = dp(tabSizeDpProvider())
        item.layoutParams = GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED, 1f),
            GridLayout.spec(GridLayout.UNDEFINED, 1f)
        ).apply {
            // 宽度交给 GridLayout 按列权重均分，确保最后一列也填满可用宽度。
            width = 0
            height = size
            setMargins(dp(2), dp(2), dp(2), dp(2))
        }
        item.background = ThemeApplier.createTabBackground(
            theme,
            context.resources.displayMetrics.density
        )
        item.isSelected = false
        item.findViewById<TextView>(R.id.tvTabName).visibility = View.GONE
        item.findViewById<ImageView>(R.id.ivTabIcon).let { icon ->
            // 下拉项的图标与栏位尺寸保持比例，避免小栏位仍使用固定 28dp
            // 导致内容挤压并影响 GridLayout 的测量和换行表现。
            val iconSize = (size * 0.5f).toInt().coerceAtLeast(dp(1))
            icon.layoutParams = icon.layoutParams.apply {
                width = iconSize
                height = iconSize
            }
        }
        return item
    }

    private fun loadResourceIcon(imageView: ImageView, resourceId: Long, theme: KeyboardTheme) {
        scope.launch {
            val resource = withContext(Dispatchers.IO) {
                DatabaseProvider.getDatabase(context).resourceDao().getById(resourceId)
            }
            val file = resource?.let { File(resourcesDir, it.filename) }
            if (file != null && file.exists()) {
                imageView.imageTintList = null
                Glide.with(context).asBitmap().load(file).centerCrop().into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_category_default)
                imageView.imageTintList = ColorStateList.valueOf(theme.iconColor)
            }
        }
    }

    private fun selectAndClose(tabKey: String) {
        setExpanded(false)
        selectedTab = tabKey
        updateSelection()
        onTabSelected(tabKey)
    }

    private fun updateSelection() {
        for (index in 0 until grid.childCount) {
            val item = grid.getChildAt(index)
            item.isSelected = item.tag == selectedTab
        }
    }

    private fun dp(value: Int): Int {
        return (value * context.resources.displayMetrics.density).toInt()
    }

    fun refreshTheme() {
        applyButtonTheme()
        if (panel.visibility == View.VISIBLE) render()
    }

    private fun applyButtonTheme() {
        val theme = KeyboardTheme.current(context)
        button.imageTintList = ColorStateList.valueOf(theme.iconColor)
        button.setPadding(dp(8), dp(8), dp(8), dp(8))
        button.scaleType = ImageView.ScaleType.CENTER_INSIDE
        button.background = ThemeApplier.createTabBackground(
            theme,
            context.resources.displayMetrics.density
        )
    }

    fun destroy() {
        close()
        observeJob?.cancel()
        observeJob = null
        grid.removeAllViews()
    }
}
