package com.suzu.test.ime.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.databinding.ItemImeCategoryPageBinding
import com.suzu.test.ime.ImageAdapter
import com.suzu.test.ime.ImageItem
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.ThemeApplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs

class CategoryPagerAdapter(
    private val context: Context,
    private val scope: CoroutineScope,
    private val dataSource: KeyboardDataSource,
    private val onItemClick: (ImageItem) -> Unit,
    private val onItemLongClick: (ImageItem, View) -> Unit,
    private val onGridScrolled: () -> Unit
) : RecyclerView.Adapter<CategoryPagerAdapter.PageViewHolder>() {

    private val tabs = mutableListOf<String>()
    private var spanCount: Int = KeyboardConfig.getSpanCount(context)
    private val pageDataCache = ConcurrentHashMap<String, List<ImageItem>>()
    private val activeHolders = mutableSetOf<PageViewHolder>()

    fun setTabs(newTabs: List<String>) {
        if (tabs != newTabs) {
            tabs.clear()
            tabs.addAll(newTabs)
            notifyDataSetChanged()
        }
    }

    fun getTabs(): List<String> = tabs.toList()

    fun getTabKey(position: Int): String? = tabs.getOrNull(position)

    fun getPositionForTab(tabKey: String): Int = tabs.indexOf(tabKey)

    fun updateSpanCount(newSpanCount: Int) {
        if (spanCount == newSpanCount) return
        spanCount = newSpanCount
        activeHolders.forEach { holder ->
            holder.updateSpanCount(newSpanCount)
        }
    }

    fun refreshTab(tabKey: String) {
        pageDataCache.remove(tabKey)
        activeHolders.forEach { holder ->
            if (holder.currentTabKey == tabKey) {
                holder.loadData(tabKey, force = true)
            }
        }
    }

    fun notifyThemeChanged() {
        activeHolders.forEach { holder ->
            holder.applyTheme()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ItemImeCategoryPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = PageViewHolder(binding)
        activeHolders.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val tabKey = tabs[position]
        holder.bind(tabKey)
    }

    override fun getItemCount(): Int = tabs.size

    override fun onViewRecycled(holder: PageViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    fun clear() {
        activeHolders.clear()
        pageDataCache.clear()
        tabs.clear()
    }

    inner class PageViewHolder(
        val binding: ItemImeCategoryPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var currentTabKey: String? = null
            private set

        private var loadJob: Job? = null
        private val gridLayoutManager = GridLayoutManager(context, spanCount)
        private val imageAdapter = ImageAdapter(
            onItemClick = onItemClick,
            onItemLongClick = onItemLongClick
        )

        private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        private var downX = 0f
        private var downY = 0f
        private var gestureDirectionDetermined = false

        init {
            binding.rvPageGrid.layoutManager = gridLayoutManager
            binding.rvPageGrid.adapter = imageAdapter

            binding.rvPageGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                        onGridScrolled()
                    }
                }
            })

            // 智能手势协调：解决垂直网格滚动与水平 ViewPager2 切页的手势竞争
            binding.rvPageGrid.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            downX = e.x
                            downY = e.y
                            gestureDirectionDetermined = false
                            // 默认允许外层 ViewPager2 监听触摸
                            rv.parent?.requestDisallowInterceptTouchEvent(false)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (!gestureDirectionDetermined) {
                                val dx = abs(e.x - downX)
                                val dy = abs(e.y - downY)
                                if (dx > touchSlop || dy > touchSlop) {
                                    gestureDirectionDetermined = true
                                    if (dy > dx) {
                                        // 垂直滑动：由内部网格全权接管，外层 ViewPager2 严禁拦截
                                        rv.parent?.requestDisallowInterceptTouchEvent(true)
                                    } else {
                                        // 水平滑动：交给外层 ViewPager2 处理横向翻页
                                        rv.parent?.requestDisallowInterceptTouchEvent(false)
                                    }
                                }
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            gestureDirectionDetermined = false
                            rv.parent?.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    return false
                }
            })
        }

        fun updateSpanCount(count: Int) {
            if (gridLayoutManager.spanCount != count) {
                gridLayoutManager.spanCount = count
            }
        }

        fun applyTheme() {
            val theme = KeyboardTheme.current(itemView.context)
            binding.tvPageEmptyHint.setTextColor(theme.emptyHintText)
            imageAdapter.notifyDataSetChanged()
        }

        fun bind(tabKey: String) {
            currentTabKey = tabKey
            updateSpanCount(spanCount)
            applyTheme()

            // 先尝试使用缓存数据，达到 0ms 瞬间上屏，彻底杜绝翻页时白屏
            val cached = pageDataCache[tabKey]
            if (cached != null) {
                imageAdapter.submitList(cached)
                updateEmptyHint(tabKey, cached.isEmpty())
            }

            // 无论是否有缓存，都在后台轻量请求最新数据刷新
            loadData(tabKey, force = (cached == null))
        }

        fun loadData(tabKey: String, force: Boolean) {
            loadJob?.cancel()
            loadJob = scope.launch {
                val list = dataSource.loadResources(tabKey)
                if (!isActive) return@launch

                pageDataCache[tabKey] = list
                withContext(Dispatchers.Main) {
                    if (currentTabKey == tabKey) {
                        imageAdapter.submitList(list)
                        updateEmptyHint(tabKey, list.isEmpty())
                        if (force) {
                            binding.rvPageGrid.scrollToPosition(0)
                        }
                    }
                }
            }
        }

        private fun updateEmptyHint(tabKey: String, isEmpty: Boolean) {
            if (isEmpty) {
                binding.tvPageEmptyHint.visibility = View.VISIBLE
                binding.tvPageEmptyHint.text = when (tabKey) {
                    "SEARCH" -> "未找到相关表情"
                    "RECENT" -> "还没有发送记录"
                    "ALL" -> "资源库为空，请在 App 内导入表情"
                    else -> "该分类暂无图片"
                }
            } else {
                binding.tvPageEmptyHint.visibility = View.GONE
            }
        }

        fun unbind() {
            loadJob?.cancel()
            loadJob = null
            currentTabKey = null
        }
    }
}
