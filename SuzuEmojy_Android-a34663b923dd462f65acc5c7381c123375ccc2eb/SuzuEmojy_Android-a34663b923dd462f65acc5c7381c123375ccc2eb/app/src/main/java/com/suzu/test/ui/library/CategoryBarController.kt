package com.suzu.test.ui.library

import android.content.Context
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CategoryBarController(
    private val context: Context,
    private val container: LinearLayout,
    private val categoryBarScrollView: HorizontalScrollView,
    private val dropdownButton: ImageButton,
    private val dropdownPanel: ScrollView,
    private val dropdownGrid: GridLayout,
    private val scope: CoroutineScope,
    private val isSortingMode: () -> Boolean,
    private val onAllLongClick: () -> Unit,
    private val onCategoryReorderClick: () -> Unit,
    private val onCategorySelected: (selection: String) -> Unit,
    private val onEnterSortingMode: () -> Unit
) {

    companion object {
        private const val ALL_SELECTION = "ALL"
        private const val DEFAULT_DROPDOWN_COLUMN_COUNT = 4
        private const val MIN_DROPDOWN_COLUMN_COUNT = 3
        private const val MAX_DROPDOWN_COLUMN_COUNT = 8
    }

    var currentSelection: String = ALL_SELECTION
        private set

    private var currentCategories: List<CategoryEntity> = emptyList()
    private var dropdownColumnCount = DEFAULT_DROPDOWN_COLUMN_COUNT
    private val categoryChipViews = mutableMapOf<String, View>()
    var isDropdownExpanded: Boolean = false
        private set

    private val resourcesDir = File(context.filesDir, "resources")
    private val dialogHelper = CategoryDialogHelper(context, scope, onEnterSortingMode)

    init {
        dropdownButton.setOnClickListener {
            if (isSortingMode()) return@setOnClickListener
            setDropdownExpanded(!isDropdownExpanded)
        }
        dropdownPanel.visibility = View.GONE
        dropdownButton.rotation = 0f
        setupDropdownScaleGesture()
    }

    fun getCategoryName(id: Long): String? {
        return currentCategories.firstOrNull { it.id == id }?.name
    }

    fun render(categories: List<CategoryEntity>) {
        val categoriesChanged = currentCategories != categories
        currentCategories = categories

        if (categoriesChanged || categoryChipViews.isEmpty()) {
            renderCategoryBar()
            renderDropdownGrid()
        } else {
            updateCategoryBarSelection()
            updateDropdownGridSelection()
        }
    }

    fun closeDropdown() {
        if (isDropdownExpanded) {
            setDropdownExpanded(false)
        }
    }

    private fun renderCategoryBar() {
        container.removeAllViews()
        categoryChipViews.clear()

        val isAllSelected = currentSelection == ALL_SELECTION
        val allChip = createSpecialChip(R.drawable.ic_tab_all, isAllSelected) {
            selectCategory(ALL_SELECTION)
        }
        allChip.setOnLongClickListener {
            onAllLongClick()
            true
        }
        container.addView(allChip)
        categoryChipViews[ALL_SELECTION] = allChip

        for (category in currentCategories) {
            val selection = category.id.toString()
            val chipView = createCategoryChip(category, currentSelection == selection) {
                selectCategory(selection)
            }
            chipView.setOnLongClickListener {
                if (isSortingMode()) {
                    onCategoryReorderClick()
                } else {
                    dialogHelper.showCategoryMenuDialog(category) {
                        if (currentSelection == selection) {
                            selectCategory(ALL_SELECTION)
                        }
                    }
                }
                true
            }
            container.addView(chipView)
            categoryChipViews[selection] = chipView
        }

        container.addView(createAddChip {
            dialogHelper.showCreateCategoryDialog()
        })
    }

    private fun renderDropdownGrid() {
        dropdownGrid.removeAllViews()
        dropdownGrid.columnCount = dropdownColumnCount

        addDropdownItem(
            selection = ALL_SELECTION,
            name = "全部",
            icon = DropdownIcon.Special(R.drawable.ic_tab_all),
            isSelected = currentSelection == ALL_SELECTION
        )

        for (category in currentCategories) {
            addDropdownItem(
                selection = category.id.toString(),
                name = category.name,
                icon = DropdownIcon.Category(category),
                isSelected = currentSelection == category.id.toString()
            )
        }
    }

    private fun addDropdownItem(
        selection: String,
        name: String,
        icon: DropdownIcon,
        isSelected: Boolean
    ) {
        val item = LayoutInflater.from(context)
            .inflate(R.layout.item_category_dropdown_grid, dropdownGrid, false)

        item.isSelected = isSelected
        item.tag = selection
        item.setOnClickListener {
            if (isSortingMode()) return@setOnClickListener
            selectCategory(selection)
        }

        val itemParams = GridLayout.LayoutParams().apply {
            width = 0
            height = GridLayout.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(
                GridLayout.UNDEFINED,
                1,
                1f
            )
            setMargins(2, 2, 2, 2)
        }
        item.layoutParams = itemParams

        item.findViewById<TextView>(R.id.tvCategoryName).text = name
        bindDropdownIcon(item, icon)
        resizeDropdownItem(item)
        dropdownGrid.addView(item)
    }

    private fun bindDropdownIcon(item: View, icon: DropdownIcon) {
        val imageView = item.findViewById<ImageView>(R.id.ivThumb)
        val textView = item.findViewById<TextView>(R.id.tvTextThumb)

        when (icon) {
            is DropdownIcon.Special -> {
                imageView.visibility = View.VISIBLE
                textView.visibility = View.GONE
                imageView.setImageResource(icon.resourceId)
            }

            is DropdownIcon.Category -> {
                when (val result = CategoryIconResolver.resolve(icon.category.iconPath)) {
                    is CategoryIconResult.Default -> {
                        imageView.visibility = View.VISIBLE
                        textView.visibility = View.GONE
                        imageView.setImageResource(R.drawable.ic_category_default)
                    }

                    is CategoryIconResult.Text -> {
                        imageView.visibility = View.GONE
                        textView.visibility = View.VISIBLE
                        textView.text = result.content
                    }

                    is CategoryIconResult.Resource -> {
                        imageView.visibility = View.VISIBLE
                        textView.visibility = View.GONE
                        loadResourceIcon(imageView, result.resourceId)
                    }
                }
            }
        }
    }

    private fun selectCategory(selection: String) {
        if (currentSelection == selection) {
            return
        }

        currentSelection = selection
        updateCategoryBarSelection()
        updateDropdownGridSelection()
        scrollCategoryBarToSelection(selection)
        onCategorySelected(selection)
    }

    private fun updateCategoryBarSelection() {
        categoryChipViews.forEach { (selection, view) ->
            view.isSelected = selection == currentSelection
        }
    }

    private fun updateDropdownGridSelection() {
        for (index in 0 until dropdownGrid.childCount) {
            val item = dropdownGrid.getChildAt(index)
            val selection = item.tag as? String
            if (selection != null) {
                item.isSelected = selection == currentSelection
            }
        }
    }

    private fun scrollCategoryBarToSelection(selection: String) {
        categoryChipViews[selection]?.post {
            val chip = categoryChipViews[selection] ?: return@post
            val viewportLeft = categoryBarScrollView.scrollX
            val viewportRight = viewportLeft + categoryBarScrollView.width
            val chipLeft = chip.left
            val chipRight = chip.right

            if (chipLeft < viewportLeft || chipRight > viewportRight) {
                val targetLeft = chip.left - (categoryBarScrollView.width - chip.width) / 2
                categoryBarScrollView.smoothScrollTo(targetLeft.coerceAtLeast(0), 0)
            }
        }
    }

    private fun resizeDropdownItem(item: View) {
        val thumb = item.findViewById<View>(R.id.ivThumb)
        val frame = thumb.parent as? View ?: return
        val sizeDp = when (dropdownColumnCount) {
            3 -> 64
            4 -> 52
            5 -> 44
            6 -> 38
            7 -> 34
            else -> 30
        }
        val sizePx = (sizeDp * context.resources.displayMetrics.density).toInt()
        frame.layoutParams = frame.layoutParams.apply {
            width = sizePx
            height = sizePx
        }
    }

    private fun setupDropdownScaleGesture() {
        var changedInGesture = false
        val detector = ScaleGestureDetector(
            context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                    changedInGesture = false
                    return isDropdownExpanded
                }

                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    if (changedInGesture || !isDropdownExpanded) return false

                    val newCount = when {
                        detector.scaleFactor > 1.15f -> dropdownColumnCount - 1
                        detector.scaleFactor < 0.85f -> dropdownColumnCount + 1
                        else -> return false
                    }.coerceIn(MIN_DROPDOWN_COLUMN_COUNT, MAX_DROPDOWN_COLUMN_COUNT)

                    if (newCount != dropdownColumnCount) {
                        dropdownColumnCount = newCount
                        changedInGesture = true
                        renderDropdownGrid()
                    }
                    return true
                }
            }
        )

        dropdownPanel.setOnTouchListener { _, event ->
            if (event.pointerCount > 1) {
                detector.onTouchEvent(event)
            }
            false
        }
    }

    private fun setDropdownExpanded(expanded: Boolean) {
        isDropdownExpanded = expanded
        dropdownPanel.visibility = if (expanded) View.VISIBLE else View.GONE
        dropdownButton.rotation = if (expanded) 180f else 0f

        if (expanded) {
            renderDropdownGrid()
        }
    }

    private fun createSpecialChip(
        iconResId: Int,
        isSelected: Boolean,
        onClick: () -> Unit
    ): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_category_chip, container, false)
        val iconView = view.findViewById<ImageView>(R.id.ivChipIcon)
        iconView.visibility = View.VISIBLE
        iconView.setImageResource(iconResId)
        view.isSelected = isSelected
        view.setOnClickListener { onClick() }
        return view
    }

    private fun createAddChip(onClick: () -> Unit): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_category_chip, container, false)
        val textView = view.findViewById<TextView>(R.id.tvChipText)
        textView.visibility = View.VISIBLE
        textView.text = "+"
        textView.textSize = 20f
        view.setOnClickListener { onClick() }
        return view
    }

    private fun createCategoryChip(
        category: CategoryEntity,
        isSelected: Boolean,
        onClick: () -> Unit
    ): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_category_chip, container, false)
        val iconView = view.findViewById<ImageView>(R.id.ivChipIcon)
        val textView = view.findViewById<TextView>(R.id.tvChipText)
        view.isSelected = isSelected

        when (val result = CategoryIconResolver.resolve(category.iconPath)) {
            is CategoryIconResult.Default -> {
                iconView.visibility = View.VISIBLE
                textView.visibility = View.GONE
                iconView.setImageResource(R.drawable.ic_category_default)
            }

            is CategoryIconResult.Text -> {
                iconView.visibility = View.GONE
                textView.visibility = View.VISIBLE
                textView.text = result.content
            }

            is CategoryIconResult.Resource -> {
                iconView.visibility = View.VISIBLE
                textView.visibility = View.GONE
                loadResourceIcon(iconView, result.resourceId)
            }
        }

        view.setOnClickListener { onClick() }
        return view
    }

    private fun loadResourceIcon(imageView: ImageView, resourceId: Long) {
        scope.launch {
            val resource = withContext(Dispatchers.IO) {
                DatabaseProvider.getDatabase(context).resourceDao().getById(resourceId)
            }
            val file = resource?.let { File(resourcesDir, it.filename) }

            if (file != null && file.exists()) {
                Glide.with(context)
                    .asBitmap()
                    .load(file)
                    .centerCrop()
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_category_default)
            }
        }
    }

    private sealed class DropdownIcon {
        data class Special(val resourceId: Int) : DropdownIcon()
        data class Category(val category: CategoryEntity) : DropdownIcon()
    }
}
