package com.suzu.test.ui.library

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.databinding.ItemLibraryCategoryPageBinding
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.abs

class LibraryPagerAdapter(
    private val context: Context,
    private val scope: CoroutineScope,
    private val viewModel: LibraryViewModel,
    private val resourcesDir: File,
    private val onItemClick: (ResourceEntity) -> Unit,
    private val onItemSelectToggle: (ResourceEntity) -> Unit,
    private val onItemLongClick: (ResourceEntity) -> Unit,
    private val onPageListUpdated: (selection: String, list: List<ResourceEntity>) -> Unit
) : RecyclerView.Adapter<LibraryPagerAdapter.PageViewHolder>() {

    private val selections = mutableListOf<String>()
    var spanCount: Int = 4
        private set

    var isSelectionMode: Boolean = false
        private set

    var selectedIds: Set<Long> = emptySet()
        private set

    private val activeHolders = mutableSetOf<PageViewHolder>()
    private val holderMap = mutableMapOf<Int, PageViewHolder>()

    fun setSelections(newSelections: List<String>) {
        if (selections != newSelections) {
            selections.clear()
            selections.addAll(newSelections)
            notifyDataSetChanged()
        }
    }

    fun getSelections(): List<String> = selections.toList()

    fun getSelection(position: Int): String? = selections.getOrNull(position)

    fun getPositionForSelection(selection: String): Int = selections.indexOf(selection)

    fun updateSpanCount(newSpan: Int) {
        if (spanCount == newSpan) return
        spanCount = newSpan
        activeHolders.forEach { it.updateSpan(newSpan) }
    }

    fun setSelectionState(isSelectionMode: Boolean, selectedIds: Set<Long>) {
        this.isSelectionMode = isSelectionMode
        this.selectedIds = selectedIds
        activeHolders.forEach { it.adapter.setSelectionState(isSelectionMode, selectedIds) }
    }

    fun getViewHolderForPosition(position: Int): PageViewHolder? = holderMap[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ItemLibraryCategoryPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = PageViewHolder(binding)
        activeHolders.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holderMap[position] = holder
        val selection = selections[position]
        holder.bind(selection, position)
    }

    override fun getItemCount(): Int = selections.size

    override fun onViewRecycled(holder: PageViewHolder) {
        super.onViewRecycled(holder)
        holderMap.values.remove(holder)
        holder.unbind()
    }

    fun clear() {
        activeHolders.clear()
        holderMap.clear()
        selections.clear()
    }

    inner class PageViewHolder(
        val binding: ItemLibraryCategoryPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentSelection: String? = null
            private set

        var currentPosition: Int = -1
            private set

        var displayedItems: List<ResourceEntity> = emptyList()
            private set

        val gridLayoutManager = GridLayoutManager(context, spanCount)
        val adapter = LibraryAdapter(
            resourcesDir = resourcesDir,
            onItemClick = onItemClick,
            onItemSelectToggle = onItemSelectToggle,
            onItemLongClick = onItemLongClick
        )

        private var collectJob: Job? = null
        private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        private var downX = 0f
        private var downY = 0f
        private var directionDetermined = false

        init {
            binding.rvPageGrid.layoutManager = gridLayoutManager
            binding.rvPageGrid.adapter = adapter
            adapter.setSelectionState(isSelectionMode, selectedIds)

            binding.rvPageGrid.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            downX = e.x
                            downY = e.y
                            directionDetermined = false
                            rv.parent?.requestDisallowInterceptTouchEvent(false)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (!directionDetermined) {
                                val dx = abs(e.x - downX)
                                val dy = abs(e.y - downY)
                                if (dx > touchSlop || dy > touchSlop) {
                                    directionDetermined = true
                                    if (dy > dx) {
                                        // 垂直滚动图片网格：阻止外层 ViewPager2 拦截
                                        rv.parent?.requestDisallowInterceptTouchEvent(true)
                                    } else {
                                        // 水平滑动切页：允许外层 ViewPager2 拦截翻页
                                        rv.parent?.requestDisallowInterceptTouchEvent(false)
                                    }
                                }
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            directionDetermined = false
                            rv.parent?.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    return false
                }
            })
        }

        fun updateSpan(span: Int) {
            if (gridLayoutManager.spanCount != span) {
                val firstPos = gridLayoutManager.findFirstVisibleItemPosition()
                gridLayoutManager.spanCount = span
                if (firstPos >= 0) {
                    gridLayoutManager.scrollToPositionWithOffset(firstPos, 0)
                }
            }
        }

        fun bind(selection: String, position: Int) {
            currentSelection = selection
            currentPosition = position
            updateSpan(spanCount)
            adapter.setSelectionState(isSelectionMode, selectedIds)

            collectJob?.cancel()
            collectJob = scope.launch {
                viewModel.getCategoryFlow(selection).collectLatest { list ->
                    displayedItems = list
                    withContext(Dispatchers.Main) {
                        if (currentSelection == selection) {
                            adapter.submitList(list)
                            updateEmptyHint(selection, list)
                            onPageListUpdated(selection, list)
                        }
                    }
                }
            }
        }

        private fun updateEmptyHint(selection: String, list: List<ResourceEntity>) {
            val filter = viewModel.filterState.value
            val query = viewModel.searchQuery.value

            if (list.isEmpty()) {
                binding.tvPageEmptyHint.visibility = View.VISIBLE
                binding.tvPageEmptyHint.text = when {
                    filter.isActive -> "没有符合筛选条件的表情"
                    query.isNotBlank() -> "没有找到匹配「$query」的表情"
                    selection == "ALL" -> "资源库为空，请先导入"
                    else -> "该分类暂无图片"
                }
            } else {
                binding.tvPageEmptyHint.visibility = View.GONE
            }
        }

        fun unbind() {
            collectJob?.cancel()
            collectJob = null
            currentSelection = null
            currentPosition = -1
        }
    }
}
