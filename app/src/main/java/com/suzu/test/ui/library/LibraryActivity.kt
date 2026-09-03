package com.suzu.test.ui.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.activity.OnBackPressedCallback
import com.suzu.test.resource.export.ResourceExportHelper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.withTransaction
import com.suzu.test.databinding.ActivityLibraryBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.KeywordUtils
import com.suzu.test.resource.delete.ResourceDeleteHelper
import com.suzu.test.ui.picker.SlideSelectionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.min

class LibraryActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "LibraryActivity"
        private const val KEY_LIBRARY_SPAN_COUNT = "library_span_count"
        private const val DEFAULT_SPAN_COUNT = 4
        private const val MIN_SPAN_COUNT = 3
        private const val MAX_SPAN_COUNT = 8
    }

    private lateinit var binding: ActivityLibraryBinding
    private lateinit var database: SuzuDatabase
    private val viewModel: LibraryViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LibraryViewModel(database) as T
            }
        }
    }
    private lateinit var adapter: LibraryAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var categoryController: CategoryBarController
    private lateinit var dragHelper: LibraryDragHelper
    private lateinit var resourcesDir: File
    private var searchDebounceJob: Job? = null

    private var isSelectionMode: Boolean = false
    private var isSortingMode: Boolean = false
    private val selectedIds: MutableSet<Long> = mutableSetOf()
    private var currentDisplayedItems: List<ResourceEntity> = emptyList()
    private lateinit var slideHelper: SlideSelectionHelper
    private lateinit var dragTouchListener: LibraryDragTouchListener

    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data!!
            val isDeleted = data.getBooleanExtra(ResourceDetailActivity.EXTRA_DELETED, false)
            val targetPos = data.getIntExtra(ResourceDetailActivity.EXTRA_CURRENT_POSITION, 0)
            val deletedIdx = data.getIntExtra(ResourceDetailActivity.EXTRA_DELETED_INDEX, -1)

            if (isDeleted && deletedIdx >= 0) {
                val currentCount = adapter.itemCount
                val safePos = deletedIdx.coerceIn(0, (currentCount - 1).coerceAtLeast(0))
                gridLayoutManager.scrollToPositionWithOffset(safePos, 0)
            } else {
                gridLayoutManager.scrollToPositionWithOffset(targetPos, 0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.getDatabase(this)
        resourcesDir = File(filesDir, "resources").apply { if (!exists()) mkdirs() }

        val sp = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val savedSpan = sp.getInt(KEY_LIBRARY_SPAN_COUNT, DEFAULT_SPAN_COUNT)
            .coerceIn(MIN_SPAN_COUNT, MAX_SPAN_COUNT)

        gridLayoutManager = GridLayoutManager(this, savedSpan)
        binding.rvLibraryGrid.layoutManager = gridLayoutManager

        adapter = LibraryAdapter(
            resourcesDir = resourcesDir,
            onItemClick = { resource ->
                if (!isSelectionMode && !isSortingMode) {
                    openDetailActivity(resource)
                }
            },
            onItemSelectToggle = { resource ->
                if (!isSortingMode) {
                    toggleItemSelection(resource.id)
                }
            },
            onItemLongClick = { resource ->
                if (!isSelectionMode && !isSortingMode) {
                    showResourceActionDialog(resource)
                }
            }
        )
        binding.rvLibraryGrid.adapter = adapter

        categoryController = CategoryBarController(
            context = this,
            container = binding.llCategoryContainer,
            categoryBarScrollView = binding.svCategoryBar,
            dropdownButton = binding.btnCategoryDropdown,
            dropdownPanel = binding.svCategoryDropdown,
            dropdownGrid = binding.glCategoryDropdown,
            scope = lifecycleScope,
            isSortingMode = { isSortingMode },
            onAllLongClick = {
                enterSortingMode()
            },
            onCategoryReorderClick = {
                showCategoryReorderBottomSheet()
            },
            onCategorySelected = { selection ->
                switchCategoryView(selection)
            },
            onEnterSortingMode = {
                enterSortingMode()
            }
        )

        dragHelper = LibraryDragHelper(
            scope = lifecycleScope,
            adapter = adapter,
            isDragAllowed = { isSortingMode && viewModel.searchQuery.value.isBlank() && !viewModel.filterState.value.isActive },
            isAllSelected = { categoryController.currentSelection == "ALL" },
            getSelectedCategoryId = { categoryController.currentSelection.toLongOrNull() }
        )
        dragHelper.attachToRecyclerView(binding.rvLibraryGrid)

        dragTouchListener = LibraryDragTouchListener(
            context = this,
            isSortingMode = { isSortingMode && viewModel.searchQuery.value.isBlank() && !viewModel.filterState.value.isActive }
        ) { viewHolder ->
            dragHelper.startDrag(viewHolder)
        }
        binding.rvLibraryGrid.addOnItemTouchListener(dragTouchListener)

        setupSlideSelection()
        setupScaleGesture()
        setupTopBar()
        setupImportButton()
        setupFilterButton()
        setupBatchActionBar()
        setupSearchBox()
        setupBackPressedHandler()
        observeCategories()
        observeViewModel()
        switchCategoryView("ALL")
    }

    private fun setupImportButton() {
        binding.btnImport.setOnClickListener {
            if (isSelectionMode || isSortingMode) return@setOnClickListener
            val currentSelection = categoryController.currentSelection
            val intent = Intent(this, com.suzu.test.ui.import.ImportActivity::class.java).apply {
                if (currentSelection != "ALL") {
                    val catId = currentSelection.toLongOrNull()
                    if (catId != null) {
                        putExtra(com.suzu.test.ui.import.ImportActivity.EXTRA_TARGET_CATEGORY_ID, catId)
                    }
                }
            }
            startActivity(intent)
        }
    }

    private fun setupFilterButton() {
        binding.btnFilter.setOnClickListener {
            if (isSortingMode) return@setOnClickListener
            val bottomSheet = LibraryFilterBottomSheet(
                context = this,
                initialState = viewModel.filterState.value
            ) { newState ->
                viewModel.updateFilterState(newState)
            }
            bottomSheet.show()
        }
    }

    private fun enterSortingMode() {
        if (viewModel.searchQuery.value.isNotBlank() || viewModel.filterState.value.isActive) {
            Toast.makeText(this, "请清除搜索或筛选后再调整位置", Toast.LENGTH_SHORT).show()
            return
        }

        categoryController.closeDropdown()
        binding.root.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS)
        if (isSelectionMode) {
            setSelectionMode(false)
        }
        setSortingMode(true)
    }

    private fun showCategoryReorderBottomSheet() {
        lifecycleScope.launch {
            val categories = withContext(Dispatchers.IO) {
                database.categoryDao().getAllCategories()
            }
            if (categories.isEmpty()) {
                Toast.makeText(this@LibraryActivity, "暂无自定义分类可排序", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val bottomSheet = CategoryReorderBottomSheet(this@LibraryActivity, lifecycleScope, categories) {
                switchCategoryView(categoryController.currentSelection)
            }
            bottomSheet.show()
        }
    }

    private fun setSortingMode(enabled: Boolean) {
        if (isSortingMode == enabled) return
        isSortingMode = enabled

        if (enabled) {
            categoryController.closeDropdown()
            if (isSelectionMode) {
                setSelectionMode(false)
            }
            viewModel.clearFilterState()
            binding.flFilterContainer.visibility = View.GONE
            binding.btnImport.visibility = View.GONE
            binding.btnToggleSelectMode.visibility = View.GONE
            binding.tvSortingModeHint.visibility = View.VISIBLE
        } else {
            dragTouchListener.cancelTimer()
            binding.flFilterContainer.visibility = View.VISIBLE
            binding.btnImport.visibility = if (!isSelectionMode) View.VISIBLE else View.GONE
            binding.btnToggleSelectMode.visibility = View.VISIBLE
            binding.tvSortingModeHint.visibility = View.GONE
        }
    }

    private fun setupScaleGesture() {
        var spanChangedInThisGesture = false
        val scaleDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                spanChangedInThisGesture = false
                return true
            }

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                if (spanChangedInThisGesture) return false

                val factor = detector.scaleFactor
                val currentSpan = gridLayoutManager.spanCount

                if (factor > 1.15f && currentSpan > MIN_SPAN_COUNT) {
                    changeSpanCount(currentSpan - 1)
                    spanChangedInThisGesture = true
                    return true
                } else if (factor < 0.85f && currentSpan < MAX_SPAN_COUNT) {
                    changeSpanCount(currentSpan + 1)
                    spanChangedInThisGesture = true
                    return true
                }
                return false
            }
        })

        binding.rvLibraryGrid.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (isSortingMode) {
                    // 排序模式：在事件入口层直接放行，完全不交给 scaleDetector 处理
                    return false
                }
                if (e.pointerCount > 1) {
                    scaleDetector.onTouchEvent(e)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                if (isSortingMode) {
                    return
                }
                if (e.pointerCount > 1) {
                    scaleDetector.onTouchEvent(e)
                }
            }
        })
    }

    private fun changeSpanCount(newSpan: Int) {
        val clampedSpan = newSpan.coerceIn(MIN_SPAN_COUNT, MAX_SPAN_COUNT)
        if (clampedSpan == gridLayoutManager.spanCount) return

        val firstPos = gridLayoutManager.findFirstVisibleItemPosition()
        gridLayoutManager.spanCount = clampedSpan
        getSharedPreferences("app_settings", Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_LIBRARY_SPAN_COUNT, clampedSpan)
            .apply()

        if (firstPos >= 0) {
            gridLayoutManager.scrollToPositionWithOffset(firstPos, 0)
        }
        TestLog.i(MODULE, "已缩放网格列数: spanCount=$clampedSpan")
    }

    private fun openDetailActivity(resource: ResourceEntity) {
        val allIds = currentDisplayedItems.map { it.id }.toLongArray()
        val index = currentDisplayedItems.indexOfFirst { it.id == resource.id }.coerceAtLeast(0)
        val intent = Intent(this, ResourceDetailActivity::class.java).apply {
            putExtra(ResourceDetailActivity.EXTRA_RESOURCE_IDS, allIds)
            putExtra(ResourceDetailActivity.EXTRA_START_POSITION, index)
        }
        detailLauncher.launch(intent)
    }

    private fun setupBackPressedHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isSortingMode) {
                    setSortingMode(false)
                } else if (isSelectionMode) {
                    setSelectionMode(false)
                } else if (categoryController.isDropdownExpanded) {
                    categoryController.closeDropdown()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupTopBar() {
        binding.btnBack.setOnClickListener {
            if (isSortingMode) {
                setSortingMode(false)
            } else if (isSelectionMode) {
                setSelectionMode(false)
            } else if (categoryController.isDropdownExpanded) {
                categoryController.closeDropdown()
            } else {
                finish()
            }
        }

        binding.btnToggleSelectMode.setOnClickListener {
            if (isSortingMode) {
                setSortingMode(false)
            } else {
                setSelectionMode(!isSelectionMode)
            }
        }
    }

    private fun setupSlideSelection() {
        slideHelper = SlideSelectionHelper(
            context = this,
            recyclerView = binding.rvLibraryGrid,
            isEnabled = { isSelectionMode && !isSortingMode }
        ) { start, end, isSelecting ->
            for (pos in start..end) {
                if (pos in 0 until currentDisplayedItems.size) {
                    val item = currentDisplayedItems[pos]
                    if (isSelecting) {
                        selectedIds.add(item.id)
                    } else {
                        selectedIds.remove(item.id)
                    }
                }
            }
            adapter.setSelectionState(isSelectionMode, selectedIds)
            updateBatchActionBar()
        }

        slideHelper.setItemStateProvider { pos ->
            if (pos in 0 until currentDisplayedItems.size) {
                selectedIds.contains(currentDisplayedItems[pos].id)
            } else {
                false
            }
        }
        binding.rvLibraryGrid.addOnItemTouchListener(slideHelper)
    }

    private fun setSelectionMode(enabled: Boolean) {
        if (isSelectionMode == enabled) return
        isSelectionMode = enabled

        if (enabled && isSortingMode) {
            setSortingMode(false)
        }

        if (!enabled) {
            if (::slideHelper.isInitialized) {
                slideHelper.cleanup()
            }
            selectedIds.clear()
        }

        binding.btnToggleSelectMode.isSelected = enabled

        binding.btnImport.visibility = if (!enabled && !isSortingMode) View.VISIBLE else View.GONE
        binding.llBatchActionBar.visibility = if (enabled) View.VISIBLE else View.GONE

        adapter.setSelectionState(isSelectionMode, selectedIds)
        updateBatchActionBar()
    }

    private fun toggleItemSelection(resourceId: Long) {
        if (selectedIds.contains(resourceId)) {
            selectedIds.remove(resourceId)
        } else {
            selectedIds.add(resourceId)
        }
        adapter.setSelectionState(isSelectionMode, selectedIds)
        updateBatchActionBar()
    }

    private fun updateBatchActionBar() {
        val count = selectedIds.size
        binding.tvSelectedCount.text = "已选 ${count} 张"

        val hasSelection = count > 0
        binding.btnBatchOperation.isEnabled = hasSelection
        binding.btnBatchDelete.isEnabled = hasSelection

        val visibleIds = currentDisplayedItems.map { it.id }
        val isAllVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { selectedIds.contains(it) }
        binding.btnBatchSelectAll.text = if (isAllVisibleSelected) "取消全选" else "全选"
    }

    private fun setupBatchActionBar() {
        binding.btnBatchSelectAll.setOnClickListener {
            val visibleIds = currentDisplayedItems.map { it.id }
            if (visibleIds.isEmpty()) return@setOnClickListener

            val isAllVisibleSelected = visibleIds.all { selectedIds.contains(it) }
            if (isAllVisibleSelected) {
                selectedIds.removeAll(visibleIds.toSet())
            } else {
                selectedIds.addAll(visibleIds)
            }
            adapter.setSelectionState(isSelectionMode, selectedIds)
            updateBatchActionBar()
        }

        binding.btnBatchOperation.setOnClickListener { view ->
            if (selectedIds.isEmpty()) return@setOnClickListener
            showBatchOperationMenu(view)
        }

        binding.btnBatchDelete.setOnClickListener {
            showBatchDeleteConfirmDialog()
        }
    }

    private fun showBatchOperationMenu(anchorView: View) {
        val popup = PopupMenu(this, anchorView)
        popup.menu.add(0, 1, 0, "置顶")
        popup.menu.add(0, 2, 1, "关键词")
        popup.menu.add(0, 3, 2, "分类")
        popup.menu.add(0, 4, 3, "导出")

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> handleBatchMoveToFront()
                2 -> showBatchKeywordsDialog()
                3 -> showBatchCategoryDialog()
                4 -> handleBatchExport()
            }
            true
        }
        popup.show()
    }

    private fun handleBatchMoveToFront() {
        if (selectedIds.isEmpty()) return
        val currentSelection = categoryController.currentSelection
        val orderedSelectedIds = currentDisplayedItems.filter { selectedIds.contains(it.id) }.map { it.id } +
                selectedIds.filter { id -> currentDisplayedItems.none { it.id == id } }

        lifecycleScope.launch {
            val count = withContext(Dispatchers.IO) {
                if (currentSelection == "ALL") {
                    database.resourceDao().moveResourcesToFront(orderedSelectedIds)
                } else {
                    val catId = currentSelection.toLongOrNull() ?: 0L
                    database.resourceCategoryDao().moveResourcesToFrontInCategory(catId, orderedSelectedIds)
                }
            }
            Toast.makeText(this@LibraryActivity, "已将 $count 张表情移动到最前", Toast.LENGTH_SHORT).show()
            setSelectionMode(false)
        }
    }

    private fun handleBatchExport() {
        if (selectedIds.isEmpty()) return
        val targetIds = selectedIds.toList()

        lifecycleScope.launch {
            val targets = withContext(Dispatchers.IO) {
                database.resourceDao().getResourcesByIds(targetIds)
            }
            ResourceExportHelper.exportResources(
                context = this@LibraryActivity,
                scope = lifecycleScope,
                resources = targets,
                subFolder = null
            ) { _, _ ->
                setSelectionMode(false)
            }
        }
    }

    private fun showBatchKeywordsDialog() {
        if (selectedIds.isEmpty()) return
        val targetIds = selectedIds.toList()

        val editText = EditText(this).apply {
            hint = "多个关键词用空格分隔"
            setPadding(40, 30, 40, 30)
        }

        AlertDialog.Builder(this)
            .setTitle("批量设置关键词")
            .setMessage("已选择 ${targetIds.size} 张图片，请输入关键词：")
            .setView(editText)
            .setPositiveButton("追加") { _, _ ->
                val input = editText.text.toString()
                if (input.isNotBlank()) {
                    lifecycleScope.launch {
                        val changed = withContext(Dispatchers.IO) {
                            database.resourceDao().batchAppendKeywords(targetIds, input)
                        }
                        Toast.makeText(this@LibraryActivity, "已为 $changed 张表情追加关键词", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNeutralButton("移除") { _, _ ->
                val input = editText.text.toString()
                if (input.isNotBlank()) {
                    lifecycleScope.launch {
                        val changed = withContext(Dispatchers.IO) {
                            database.resourceDao().batchRemoveKeywords(targetIds, input)
                        }
                        Toast.makeText(this@LibraryActivity, "已从 $changed 张表情中移除关键词", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showBatchCategoryDialog() {
        if (selectedIds.isEmpty()) return
        val targetIds = selectedIds.toList()
        val currentSelection = categoryController.currentSelection
        val isSpecificCategory = currentSelection != "ALL"
        val currentCatId = currentSelection.toLongOrNull() ?: 0L

        lifecycleScope.launch {
            val allCats: List<CategoryEntity> = withContext(Dispatchers.IO) {
                database.categoryDao().getAllCategories()
            }
            if (allCats.isEmpty()) {
                Toast.makeText(this@LibraryActivity, "暂无可用分类，请先在分类栏新建分类", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val options = mutableListOf<String>()
            options.add("添加到分类...")
            if (isSpecificCategory) {
                val catName = withContext(Dispatchers.IO) {
                    database.categoryDao().getCategoryById(currentCatId)?.name ?: "当前分类"
                }
                options.add("从当前分类「$catName」移出")
            }

            AlertDialog.Builder(this@LibraryActivity)
                .setTitle("批量分类 (${targetIds.size} 项)")
                .setItems(options.toTypedArray()) { _, which ->
                    when (options[which]) {
                        "添加到分类..." -> showSelectTargetCategoryDialog(targetIds, allCats)
                        else -> executeBatchRemoveFromCurrentCategory(targetIds, currentCatId)
                    }
                }
                .setNegativeButton("取消", null)
                .show()
        }
    }

    private fun showSelectTargetCategoryDialog(targetIds: List<Long>, allCats: List<CategoryEntity>) {
        val catNames = allCats.map { it.name }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("选择要添加到的目标分类")
            .setItems(catNames) { _, which ->
                val targetCat = allCats[which]
                lifecycleScope.launch {
                    val addedCount = withContext(Dispatchers.IO) {
                        database.resourceCategoryDao().addResourcesToCategoryBatch(targetIds, targetCat.id)
                    }
                    Toast.makeText(
                        this@LibraryActivity,
                        "成功将 $addedCount 张表情加入「${targetCat.name}」",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun executeBatchRemoveFromCurrentCategory(targetIds: List<Long>, currentCatId: Long) {
        lifecycleScope.launch {
            val removedCount = withContext(Dispatchers.IO) {
                database.resourceCategoryDao().removeResourcesFromCategoryBatch(currentCatId, targetIds)
            }
            Toast.makeText(this@LibraryActivity, "已从该分类移出 $removedCount 张表情", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBatchDeleteConfirmDialog() {
        if (selectedIds.isEmpty()) return
        val count = selectedIds.size

        AlertDialog.Builder(this)
            .setTitle("批量删除确认")
            .setMessage("将永久删除 $count 张图片，此操作不可恢复。\n确定继续吗？")
            .setPositiveButton("删除") { _, _ ->
                val targetIds = selectedIds.toList()
                lifecycleScope.launch {
                    val targets = withContext(Dispatchers.IO) {
                        database.resourceDao().getResourcesByIds(targetIds)
                    }
                    ResourceDeleteHelper.deleteResources(
                        context = this@LibraryActivity,
                        scope = lifecycleScope,
                        database = database,
                        resources = targets
                    ) { result ->
                        val message = if (result.isCancelled) {
                            "已删除 ${result.deletedCount} 张图片（已取消，失败 ${result.failedCount} 张）"
                        } else {
                            "已成功删除 ${result.deletedCount} 张图片" +
                                if (result.failedCount > 0) "，失败 ${result.failedCount} 张" else ""
                        }
                        Toast.makeText(this@LibraryActivity, message, Toast.LENGTH_SHORT).show()
                        setSelectionMode(false)
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun setupSearchBox() {
        binding.etSearchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounceJob?.cancel()
                searchDebounceJob = lifecycleScope.launch {
                    delay(250)
                    val query = s?.toString()?.trim() ?: ""
                    viewModel.updateSearchQuery(query)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            database.categoryDao().getAllCategoriesFlow().collectLatest { categories ->
                categoryController.render(categories)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.filterState.collectLatest { filter ->
                        val count = filter.activeFilterCount
                        if (count > 0) {
                            binding.tvFilterBadge.text = count.toString()
                            binding.tvFilterBadge.visibility = View.VISIBLE
                        } else {
                            binding.tvFilterBadge.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.displayedItems.collectLatest { list ->
                        currentDisplayedItems = list
                        adapter.submitList(list)
                        dragHelper.updateItems(list)

                        val selection = viewModel.categorySelection.value
                        val query = viewModel.searchQuery.value
                        val filter = viewModel.filterState.value

                        val title = if (selection == "ALL") {
                            "资源库"
                        } else {
                            val catId = selection.toLongOrNull()
                            if (catId != null) {
                                categoryController.getCategoryName(catId) ?: "分类"
                            } else {
                                "分类"
                            }
                        }
                        binding.tvTitle.text = title
                        binding.tvCount.text = "共 ${list.size} 张"

                        binding.tvEmptyHint.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                        binding.tvEmptyHint.text = when {
                            list.isNotEmpty() -> ""
                            filter.isActive -> "没有符合筛选条件的表情"
                            query.isNotBlank() -> "没有找到匹配「$query」的表情"
                            selection == "ALL" -> "资源库为空，请先导入"
                            else -> "该分类暂无图片"
                        }
                        if (isSelectionMode) {
                            updateBatchActionBar()
                        }
                    }
                }
            }
        }
    }

    private fun switchCategoryView(selection: String) {
        viewModel.selectCategory(selection)
    }

    private fun showResourceActionDialog(resource: ResourceEntity) {
        lifecycleScope.launch {
            val selection = categoryController.currentSelection
            val catName = if (selection != "ALL") {
                withContext(Dispatchers.IO) { database.categoryDao().getCategoryById(selection.toLongOrNull() ?: 0L)?.name }
            } else null

            val actions = mutableListOf<String>()
            actions.add("编辑关键词")
            actions.add("移动到最前")
            actions.add("调整位置")
            actions.add("分类")
            actions.add("导出")
            if (catName != null) {
                actions.add("设为「$catName」的图标")
            }
            actions.add("删除")

            AlertDialog.Builder(this@LibraryActivity)
                .setTitle(resource.filename)
                .setItems(actions.toTypedArray()) { _, which ->
                    when (actions[which]) {
                        "编辑关键词" -> showEditKeywordsDialog(resource)
                        "移动到最前" -> moveSingleResourceToFront(resource, selection)
                        "调整位置" -> enterSortingMode()
                        "分类" -> showCategoryAssignmentDialog(resource)
                        "导出" -> ResourceExportHelper.exportResources(
                            context = this@LibraryActivity,
                            scope = lifecycleScope,
                            resources = listOf(resource),
                            subFolder = null
                        )
                        "删除" -> showDeleteConfirmDialog(resource)
                        else -> selection.toLongOrNull()?.let { setResourceAsCategoryIcon(it, resource.id) }
                    }
                }.show()
        }
    }

    private fun moveSingleResourceToFront(resource: ResourceEntity, selection: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (selection == "ALL") {
                    database.resourceDao().moveResourcesToFront(listOf(resource.id))
                } else {
                    val catId = selection.toLongOrNull() ?: 0L
                    database.resourceCategoryDao().moveResourcesToFrontInCategory(catId, listOf(resource.id))
                }
            }
            Toast.makeText(this@LibraryActivity, "已将此表情移动到最前", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditKeywordsDialog(resource: ResourceEntity) {
        val editText = EditText(this).apply {
            setText(resource.keywords)
            setSelection(text.length)
            hint = "多个关键词用空格分隔"
            setPadding(40, 30, 40, 30)
        }

        AlertDialog.Builder(this)
            .setTitle("编辑关键词")
            .setMessage("输入关键词（多个词用空格分隔）：")
            .setView(editText)
            .setPositiveButton("保存") { _, _ ->
                val normalized = KeywordUtils.normalize(editText.text.toString())
                lifecycleScope.launch(Dispatchers.IO) {
                    database.resourceDao().updateKeywords(resource.id, normalized)
                    TestLog.i(MODULE, "已更新资源关键词: ID=${resource.id}, keywords='$normalized'")
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun setResourceAsCategoryIcon(catId: Long, resId: Long) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val cat = database.categoryDao().getCategoryById(catId)
                if (cat != null) database.categoryDao().updateCategory(cat.copy(iconPath = "res:$resId"))
            }
        }
    }

    private fun showCategoryAssignmentDialog(resource: ResourceEntity) {
        lifecycleScope.launch {
            val allCats: List<CategoryEntity> = withContext(Dispatchers.IO) {
                database.categoryDao().getAllCategories()
            }
            if (allCats.isEmpty()) {
                AlertDialog.Builder(this@LibraryActivity)
                    .setTitle("分类")
                    .setMessage("暂无任何自定义分类，请先在顶部「+」创建分类")
                    .setPositiveButton("确定", null)
                    .show()
                return@launch
            }

            showMultiChoiceCategoryDialog(resource.id, allCats)
        }
    }

    private suspend fun showMultiChoiceCategoryDialog(resourceId: Long, allCats: List<CategoryEntity>) {
        val currentAssignedCatIds = withContext(Dispatchers.IO) {
            database.resourceCategoryDao().getCategoryIdsByResourceId(resourceId).toSet()
        }
        val catNames = allCats.map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(allCats.size) { i -> currentAssignedCatIds.contains(allCats[i].id) }

        AlertDialog.Builder(this@LibraryActivity)
            .setTitle("归类表情")
            .setMultiChoiceItems(catNames, checkedItems) { _, which, isChecked -> checkedItems[which] = isChecked }
            .setPositiveButton("保存") { _, _ -> saveCategoryAssignments(resourceId, allCats, checkedItems) }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun saveCategoryAssignments(resourceId: Long, allCats: List<CategoryEntity>, checked: BooleanArray) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val rcDao = database.resourceCategoryDao()
                val currentIds = rcDao.getCategoryIdsByResourceId(resourceId).toSet()
                database.withTransaction {
                    for (i in allCats.indices) {
                        val catId = allCats[i].id
                        val isChecked = checked[i]
                        if (isChecked && !currentIds.contains(catId)) {
                            val maxSort = rcDao.getMaxSortOrderForCategory(catId) ?: 0
                            rcDao.addResourceToCategory(ResourceCategoryEntity(resourceId, catId, maxSort + 1))
                        } else if (!isChecked && currentIds.contains(catId)) {
                            rcDao.removeResourceFromCategory(resourceId, catId)
                        }
                    }
                }
            }
        }
    }

    private fun showDeleteConfirmDialog(resource: ResourceEntity) {
        AlertDialog.Builder(this)
            .setTitle("删除资源")
            .setMessage("确定彻底删除此表情包吗？(将同步清理数据库与本地文件)")
            .setPositiveButton("删除") { _, _ -> deleteResource(resource) }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun deleteResource(resource: ResourceEntity) {
        ResourceDeleteHelper.deleteResources(
            context = this,
            scope = lifecycleScope,
            database = database,
            resources = listOf(resource)
        ) { result ->
            if (result.deletedCount > 0) {
                Toast.makeText(this, "已删除此表情", Toast.LENGTH_SHORT).show()
            } else if (result.isCancelled) {
                Toast.makeText(this, "已取消删除", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (::slideHelper.isInitialized) {
            slideHelper.cleanup()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::slideHelper.isInitialized) {
            slideHelper.cleanup()
        }
    }
}
