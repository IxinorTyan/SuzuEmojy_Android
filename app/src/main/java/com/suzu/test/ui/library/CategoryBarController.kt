package com.suzu.test.ui.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.R
import com.suzu.test.databinding.ItemLibraryCategoryCoverBinding
import com.suzu.test.databinding.ItemLibraryCategoryTabBinding
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.ui.design.SuzuDisclosureLayout
import com.suzu.test.ui.view.CategoryThumbnailPreloader
import com.suzu.test.ui.view.adjacentCategory
import kotlinx.coroutines.CoroutineScope

class CategoryBarController(
    context: Context,
    private val container: LinearLayout,
    private val categoryBarScrollView: HorizontalScrollView,
    private val dropdownButton: View,
    dropdownArrow: View,
    private val disclosure: SuzuDisclosureLayout,
    private val dropdownGrid: LibraryCategoryGridView,
    private val scope: CoroutineScope,
    private val isSortingMode: () -> Boolean,
    private val onAllLongClick: () -> Unit,
    private val onCategoryReorderClick: () -> Unit,
    private val onCategorySelected: (selection: String) -> Unit,
    onEnterSortingMode: () -> Unit
) {
    companion object {
        private const val ALL_SELECTION = "ALL"
        private const val DEFAULT_DROPDOWN_COLUMN_COUNT = 4
        private const val MIN_DROPDOWN_COLUMN_COUNT = 3
        private const val MAX_DROPDOWN_COLUMN_COUNT = 8
    }

    private val inflater = LayoutInflater.from(context)
    private val dialogHelper = CategoryDialogHelper(context, scope, onEnterSortingMode, onCategoryReorderClick)
    private val thumbnailPreloader = CategoryThumbnailPreloader(context, scope, DiskCacheStrategy.NONE)
    private val categoryTabs = mutableMapOf<String, ItemLibraryCategoryTabBinding>()
    private var categories: List<CategoryEntity> = emptyList()
    private val dropdownLayout = GridLayoutManager(context, DEFAULT_DROPDOWN_COLUMN_COUNT)
    private val dropdownAdapter = CoverAdapter()
    private var hasManualSpanCount = false

    var currentSelection: String = ALL_SELECTION
        private set

    val isDropdownExpanded: Boolean
        get() = disclosure.isExpanded

    init {
        dropdownGrid.layoutManager = dropdownLayout
        dropdownGrid.adapter = dropdownAdapter
        dropdownGrid.itemAnimator = null
        dropdownGrid.addOnLayoutChangeListener { view, left, _, right, _, oldLeft, _, oldRight, _ ->
            if (!hasManualSpanCount && right - left != oldRight - oldLeft) {
                val widthDp = (view.width - view.paddingLeft - view.paddingRight) /
                    view.resources.displayMetrics.density
                val spans = (widthDp / 86).toInt()
                    .coerceIn(DEFAULT_DROPDOWN_COLUMN_COUNT, MAX_DROPDOWN_COLUMN_COUNT)
                if (dropdownLayout.spanCount != spans) dropdownLayout.spanCount = spans
            }
        }
        dropdownGrid.onSpanStep = { step ->
            val previous = dropdownLayout.spanCount
            val next = (previous + step).coerceIn(MIN_DROPDOWN_COLUMN_COUNT, MAX_DROPDOWN_COLUMN_COUNT)
            if (previous != next) {
                hasManualSpanCount = true
                dropdownLayout.spanCount = next
            }
        }

        disclosure.bindIndicator(
            dropdownButton,
            dropdownArrow,
            context.getText(R.string.library_expand_categories),
            context.getText(R.string.library_collapse_categories)
        )
        dropdownButton.setOnClickListener {
            if (!isSortingMode()) setDropdownExpanded(!isDropdownExpanded)
        }
        dropdownButton.setOnLongClickListener {
            onCategoryReorderClick()
            true
        }
    }

    fun getOrderedSelections(): List<String> = listOf(ALL_SELECTION) + categories.map { it.id.toString() }

    fun getCategoryName(id: Long): String? = categories.firstOrNull { it.id == id }?.name

    fun selectAdjacent(step: Int) {
        val next = adjacentCategory(getOrderedSelections(), currentSelection, step) ?: return
        closeDropdown()
        selectCategory(next)
    }

    fun render(categories: List<CategoryEntity>) {
        val changed = this.categories != categories || categoryTabs.isEmpty()
        this.categories = categories
        val selections = getOrderedSelections()
        if (currentSelection !in selections) selectCategory(ALL_SELECTION)
        thumbnailPreloader.warm(selections, currentSelection)

        if (changed) {
            val entries = listOf(FolderEntry(ALL_SELECTION, "全部", null)) +
                categories.map { FolderEntry(it.id.toString(), it.name, it) }
            renderTabs(entries)
            dropdownAdapter.submitList(entries)
            scrollCategoryBarToSelection(currentSelection)
        }
    }

    fun closeDropdown() {
        if (isDropdownExpanded) setDropdownExpanded(false)
    }

    fun destroy() {
        thumbnailPreloader.cancel()
    }

    private fun renderTabs(entries: List<FolderEntry>) {
        categoryTabs.values.forEach { it.categoryCover.clear() }
        container.removeAllViews()
        categoryTabs.clear()

        for (entry in entries) {
            val tab = ItemLibraryCategoryTabBinding.inflate(inflater, container, false)
            tab.root.contentDescription = entry.name
            tab.root.isSelected = entry.selection == currentSelection
            bindCover(tab.categoryCover, entry.category, compact = true)
            tab.root.setOnClickListener { selectCategory(entry.selection) }
            tab.root.setOnLongClickListener {
                when {
                    entry.category == null -> onAllLongClick()
                    isSortingMode() -> onCategoryReorderClick()
                    else -> dialogHelper.showCategoryMenuDialog(entry.category) {
                        if (currentSelection == entry.selection) selectCategory(ALL_SELECTION)
                    }
                }
                true
            }
            container.addView(tab.root)
            categoryTabs[entry.selection] = tab
        }

        val add = ItemLibraryCategoryTabBinding.inflate(inflater, container, false)
        add.categoryCover.bindSymbol(R.drawable.ic_library_add_category, compact = true)
        add.root.contentDescription = "新建分类"
        add.root.setOnClickListener { dialogHelper.showCreateCategoryDialog() }
        container.addView(add.root)
    }

    private fun bindCover(view: LibraryCategoryCoverView, category: CategoryEntity?, compact: Boolean) {
        if (category == null) {
            view.bindSymbol(R.drawable.ic_tab_all, compact)
        } else {
            view.bindCategory(category.iconPath, compact, scope)
        }
    }

    fun selectCategory(selection: String, notify: Boolean = true) {
        val previous = currentSelection
        if (previous == selection) {
            scrollCategoryBarToSelection(selection)
            return
        }

        currentSelection = selection
        categoryTabs[previous]?.root?.isSelected = false
        categoryTabs[selection]?.root?.isSelected = true
        dropdownAdapter.updateSelection(previous, selection)
        scrollCategoryBarToSelection(selection)
        if (notify) onCategorySelected(selection)
        thumbnailPreloader.warm(getOrderedSelections(), selection)
    }

    private fun scrollCategoryBarToSelection(selection: String) {
        categoryTabs[selection]?.root?.post {
            val tab = categoryTabs[selection]?.root ?: return@post
            val viewportLeft = categoryBarScrollView.scrollX
            val viewportWidth = categoryBarScrollView.width
            if (tab.left < viewportLeft || tab.right > viewportLeft + viewportWidth) {
                val targetLeft = tab.left - (viewportWidth - tab.width) / 2
                categoryBarScrollView.smoothScrollTo(targetLeft.coerceAtLeast(0), 0)
            }
        }
    }

    private fun setDropdownExpanded(expanded: Boolean) {
        if (expanded && !dropdownGrid.isShown) {
            val selected = dropdownAdapter.currentList.indexOfFirst { it.selection == currentSelection }
            if (selected >= 0) dropdownLayout.scrollToPosition(selected)
        }
        dropdownButton.isSelected = expanded
        disclosure.setExpanded(expanded)
    }

    private data class FolderEntry(val selection: String, val name: String, val category: CategoryEntity?)

    private object FolderDiff : DiffUtil.ItemCallback<FolderEntry>() {
        override fun areItemsTheSame(oldItem: FolderEntry, newItem: FolderEntry): Boolean =
            oldItem.selection == newItem.selection

        override fun areContentsTheSame(oldItem: FolderEntry, newItem: FolderEntry): Boolean =
            oldItem == newItem
    }

    private object SelectionChanged

    private class CoverHolder(val binding: ItemLibraryCategoryCoverBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class CoverAdapter : ListAdapter<FolderEntry, CoverHolder>(FolderDiff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoverHolder {
            return CoverHolder(ItemLibraryCategoryCoverBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: CoverHolder, position: Int) {
            val entry = getItem(position)
            val binding = holder.binding
            binding.tvCategoryName.text = entry.name
            binding.root.isSelected = entry.selection == currentSelection
            bindCover(binding.categoryCover, entry.category, compact = false)
            binding.root.setOnClickListener {
                if (!isSortingMode()) selectCategory(entry.selection)
            }
            binding.root.setOnLongClickListener {
                onCategoryReorderClick()
                true
            }
        }

        override fun onBindViewHolder(holder: CoverHolder, position: Int, payloads: MutableList<Any>) {
            if (payloads.isNotEmpty() && payloads.all { it === SelectionChanged }) {
                holder.binding.root.isSelected = getItem(position).selection == currentSelection
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }

        override fun onViewRecycled(holder: CoverHolder) {
            holder.binding.categoryCover.clear()
            super.onViewRecycled(holder)
        }

        fun updateSelection(previous: String, selection: String) {
            listOf(previous, selection).forEach { key ->
                val position = currentList.indexOfFirst { it.selection == key }
                if (position >= 0) notifyItemChanged(position, SelectionChanged)
            }
        }
    }
}
