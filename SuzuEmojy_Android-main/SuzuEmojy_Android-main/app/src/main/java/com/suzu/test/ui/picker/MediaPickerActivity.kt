package com.suzu.test.ui.picker

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.databinding.ActivityMediaPickerBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.log.TestLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections
import java.util.LinkedHashSet

class MediaPickerActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "MediaPickerActivity"
        const val EXTRA_RESULT_TOKEN = "extra_result_token"
        private const val PAGE_SIZE = 200
        private const val PRELOAD_THRESHOLD = 40
    }

    private lateinit var binding: ActivityMediaPickerBinding
    private lateinit var adapter: MediaPickerAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var slideHelper: SlideSelectionHelper

    private val selectedUris = LinkedHashSet<Uri>()
    private val selectedOrderMap = HashMap<Uri, Int>()
    private var likelyImportedSizes = emptySet<Long>()

    private var currentBucketId: String? = null
    private var bucketList = mutableListOf<PickerBucket>()
    private var snapshotTimestampSec: Long = 0L

    private val currentDisplayItems = mutableListOf<PickerMediaItem>()
    private var lastLoadedDateAdded: Long? = null
    private var lastLoadedId: Long? = null
    private var hasMoreData = true
    private var isLoadingPage = false
    private var loadPageJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        snapshotTimestampSec = System.currentTimeMillis() / 1000L

        setupUI()
        loadInitialData()
    }

    private fun setupUI() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvCancel.setOnClickListener {
            finish()
        }

        layoutManager = GridLayoutManager(this, 4)
        binding.rvMedia.layoutManager = layoutManager

        adapter = MediaPickerAdapter { item, pos ->
            toggleItemSelection(item, pos)
        }
        binding.rvMedia.adapter = adapter
        binding.rvMedia.itemAnimator = null

        slideHelper = SlideSelectionHelper(this, binding.rvMedia, isEnabled = { true }) { start, end, isSelecting ->
            for (pos in start..end) {
                if (pos in 0 until currentDisplayItems.size) {
                    val item = currentDisplayItems[pos]
                    setItemSelection(item, pos, isSelecting)
                }
            }
        }
        slideHelper.setItemStateProvider { pos ->
            if (pos in 0 until currentDisplayItems.size) {
                selectedUris.contains(currentDisplayItems[pos].uri)
            } else {
                false
            }
        }
        binding.rvMedia.addOnItemTouchListener(slideHelper)

        binding.rvMedia.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoadingPage && hasMoreData && totalItemCount > 0 &&
                    lastVisibleItemPosition + PRELOAD_THRESHOLD >= totalItemCount
                ) {
                    loadNextPage()
                }
            }
        })

        binding.btnSelectAll.setOnClickListener {
            handleSelectAllOrDeselect()
        }

        binding.btnConfirmImport.setOnClickListener {
            confirmSelection()
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            withContext(Dispatchers.IO) {
                try {
                    val db = DatabaseProvider.getDatabase(this@MediaPickerActivity)
                    val sizes = db.resourceDao().getAllByteSizes()
                    likelyImportedSizes = sizes.toSet()
                } catch (e: Exception) {
                    TestLog.e(MODULE, "读取资源库大小集合失败: ${e.message}", e)
                }

                loadBucketsInternal()
            }

            setupBucketSpinner()
            loadBucketData(null)
        }
    }

    private fun loadBucketsInternal() {
        val buckets = mutableListOf<PickerBucket>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        val selection = "${MediaStore.Images.Media.MIME_TYPE} LIKE 'image/%' AND ${MediaStore.Images.Media.DATE_ADDED} <= ?"
        val selectionArgs = arrayOf(snapshotTimestampSec.toString())

        var totalCount = 0
        val bucketCountMap = mutableMapOf<String, Pair<String, Int>>()

        try {
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )?.use { cursor ->
                val bucketIdCol = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
                val bucketNameCol = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    totalCount++
                    val bId = if (bucketIdCol != -1) cursor.getString(bucketIdCol) ?: "unknown" else "unknown"
                    val bName = if (bucketNameCol != -1) cursor.getString(bucketNameCol) ?: "未知相册" else "未知相册"

                    val current = bucketCountMap[bId]
                    if (current == null) {
                        bucketCountMap[bId] = Pair(bName, 1)
                    } else {
                        bucketCountMap[bId] = Pair(current.first, current.second + 1)
                    }
                }
            }
        } catch (e: Exception) {
            TestLog.e(MODULE, "扫描相册分组失败: ${e.message}", e)
        }

        buckets.add(PickerBucket(null, "全部图片", totalCount))
        bucketCountMap.forEach { (bId, pair) ->
            buckets.add(PickerBucket(bId, pair.first, pair.second))
        }

        bucketList.clear()
        bucketList.addAll(buckets)
    }

    private fun setupBucketSpinner() {
        val spinnerLabels = bucketList.map { "${it.displayName} (${it.count})" }
        val spinnerAdapter = ArrayAdapter(this, com.suzu.test.R.layout.item_picker_spinner_selected, spinnerLabels)
        spinnerAdapter.setDropDownViewResource(com.suzu.test.R.layout.item_picker_spinner_dropdown)
        binding.spinnerBucket.adapter = spinnerAdapter

        binding.spinnerBucket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position in 0 until bucketList.size) {
                    val selectedBucket = bucketList[position]
                    if (selectedBucket.bucketId != currentBucketId || (currentBucketId == null && position != 0)) {
                        loadBucketData(selectedBucket.bucketId)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadBucketData(bucketId: String?) {
        loadPageJob?.cancel()
        currentBucketId = bucketId
        currentDisplayItems.clear()
        adapter.submitList(emptyList())
        lastLoadedDateAdded = null
        lastLoadedId = null
        hasMoreData = true
        isLoadingPage = false

        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmptyState.visibility = View.GONE
        updateBottomBar()

        loadNextPage()
    }

    private fun loadNextPage() {
        if (isLoadingPage || !hasMoreData) return
        isLoadingPage = true

        loadPageJob = lifecycleScope.launch {
            val bucketId = currentBucketId
            val cursorDate = lastLoadedDateAdded
            val cursorId = lastLoadedId

            val pageItems = withContext(Dispatchers.IO) {
                fetchPage(bucketId, cursorDate, cursorId)
            }

            if (!isActive) return@launch

            binding.progressBar.visibility = View.GONE
            if (pageItems.isEmpty() && currentDisplayItems.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.tvEmptyState.visibility = View.GONE
            }

            if (pageItems.size < PAGE_SIZE) {
                hasMoreData = false
            }

            if (pageItems.isNotEmpty()) {
                val lastItem = pageItems.last()
                lastLoadedDateAdded = lastItem.dateAdded
                lastLoadedId = lastItem.id

                currentDisplayItems.addAll(pageItems)
                adapter.submitList(currentDisplayItems.toList())
            }

            isLoadingPage = false
            updateBottomBar()
        }
    }

    private fun fetchPage(bucketId: String?, cursorDate: Long?, cursorId: Long?): List<PickerMediaItem> {
        val items = mutableListOf<PickerMediaItem>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE
        )

        val whereConditions = mutableListOf<String>()
        val args = mutableListOf<String>()

        whereConditions.add("${MediaStore.Images.Media.MIME_TYPE} LIKE 'image/%'")
        whereConditions.add("${MediaStore.Images.Media.DATE_ADDED} <= ?")
        args.add(snapshotTimestampSec.toString())

        if (bucketId != null) {
            whereConditions.add("${MediaStore.Images.Media.BUCKET_ID} = ?")
            args.add(bucketId)
        }

        if (cursorDate != null && cursorId != null) {
            whereConditions.add("(${MediaStore.Images.Media.DATE_ADDED} < ? OR (${MediaStore.Images.Media.DATE_ADDED} = ? AND ${MediaStore.Images.Media._ID} < ?))")
            args.add(cursorDate.toString())
            args.add(cursorDate.toString())
            args.add(cursorId.toString())
        }

        val selection = whereConditions.joinToString(" AND ")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC, ${MediaStore.Images.Media._ID} DESC"

        val cursor: Cursor? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val queryArgs = Bundle().apply {
                putString(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION, selection)
                putStringArray(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, args.toTypedArray())
                putString(android.content.ContentResolver.QUERY_ARG_SQL_SORT_ORDER, sortOrder)
                putInt(android.content.ContentResolver.QUERY_ARG_LIMIT, PAGE_SIZE)
            }
            try {
                contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, queryArgs, null)
            } catch (e: Exception) {
                TestLog.e(MODULE, "query page error: ${e.message}", e)
                null
            }
        } else {
            try {
                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    args.toTypedArray(),
                    "$sortOrder LIMIT $PAGE_SIZE"
                )
            } catch (e: Exception) {
                TestLog.e(MODULE, "query page error: ${e.message}", e)
                null
            }
        }

        cursor?.use { c ->
            val idCol = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameCol = c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val bIdCol = c.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
            val bNameCol = c.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val dateCol = c.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
            val mimeCol = c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
            val sizeCol = c.getColumnIndex(MediaStore.Images.Media.SIZE)

            while (c.moveToNext()) {
                val id = c.getLong(idCol)
                val name = if (nameCol != -1) c.getString(nameCol) ?: "" else ""
                val bId = if (bIdCol != -1) c.getString(bIdCol) else null
                val bName = if (bNameCol != -1) c.getString(bNameCol) else null
                val date = if (dateCol != -1) c.getLong(dateCol) else 0L
                val mime = if (mimeCol != -1) c.getString(mimeCol) ?: "image/*" else "image/*"
                val size = if (sizeCol != -1) c.getLong(sizeCol) else 0L

                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val isLikelyImported = likelyImportedSizes.contains(size)

                items.add(
                    PickerMediaItem(
                        id = id,
                        uri = contentUri,
                        displayName = name,
                        bucketId = bId,
                        bucketDisplayName = bName,
                        dateAdded = date,
                        mimeType = mime,
                        size = size,
                        isLikelyImported = isLikelyImported
                    )
                )
            }
        }

        return items
    }

    private fun toggleItemSelection(item: PickerMediaItem, position: Int) {
        val currentlySelected = selectedUris.contains(item.uri)
        setItemSelection(item, position, !currentlySelected)
    }

    private fun setItemSelection(item: PickerMediaItem, position: Int, isSelect: Boolean) {
        val wasSelected = selectedUris.contains(item.uri)
        if (wasSelected == isSelect) return

        if (isSelect) {
            selectedUris.add(item.uri)
            selectedOrderMap[item.uri] = selectedUris.size
            adapter.updateSelectionState(selectedOrderMap, selectedUris.size)
            adapter.notifyItemChanged(position, MediaPickerAdapter.PAYLOAD_SELECTION)
        } else {
            val removedOrder = selectedOrderMap.remove(item.uri) ?: 0
            selectedUris.remove(item.uri)

            recalculateOrderMap()
            adapter.updateSelectionState(selectedOrderMap, selectedUris.size)
            adapter.notifyItemChanged(position, MediaPickerAdapter.PAYLOAD_SELECTION)

            if (selectedUris.size <= 200) {
                notifyAffectedItems(removedOrder)
            }
        }

        updateBottomBar()
    }

    private fun recalculateOrderMap() {
        selectedOrderMap.clear()
        var index = 1
        for (uri in selectedUris) {
            selectedOrderMap[uri] = index++
        }
    }

    private fun notifyAffectedItems(removedOrder: Int) {
        val firstPos = layoutManager.findFirstVisibleItemPosition()
        val lastPos = layoutManager.findLastVisibleItemPosition()
        if (firstPos == RecyclerView.NO_POSITION || lastPos == RecyclerView.NO_POSITION) return

        for (pos in firstPos..lastPos) {
            if (pos in 0 until currentDisplayItems.size) {
                val itm = currentDisplayItems[pos]
                val currentOrder = selectedOrderMap[itm.uri]
                if (currentOrder != null && currentOrder >= removedOrder) {
                    adapter.notifyItemChanged(pos, MediaPickerAdapter.PAYLOAD_SELECTION)
                }
            }
        }
    }

    private fun handleSelectAllOrDeselect() {
        val bucketId = currentBucketId
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE

            val allBucketUris = withContext(Dispatchers.IO) {
                queryAllUrisForBucket(bucketId)
            }

            val isAllSelected = allBucketUris.isNotEmpty() && allBucketUris.all { selectedUris.contains(it) }

            if (isAllSelected) {
                selectedUris.removeAll(allBucketUris.toSet())
            } else {
                selectedUris.addAll(allBucketUris)
            }

            recalculateOrderMap()
            adapter.updateSelectionState(selectedOrderMap, selectedUris.size)

            val firstPos = layoutManager.findFirstVisibleItemPosition()
            val lastPos = layoutManager.findLastVisibleItemPosition()
            if (firstPos != RecyclerView.NO_POSITION && lastPos != RecyclerView.NO_POSITION) {
                for (pos in firstPos..lastPos) {
                    adapter.notifyItemChanged(pos, MediaPickerAdapter.PAYLOAD_SELECTION)
                }
            }

            binding.progressBar.visibility = View.GONE
            updateBottomBar()
        }
    }

    private fun queryAllUrisForBucket(bucketId: String?): List<Uri> {
        val uris = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val whereConditions = mutableListOf<String>()
        val args = mutableListOf<String>()

        whereConditions.add("${MediaStore.Images.Media.MIME_TYPE} LIKE 'image/%'")
        whereConditions.add("${MediaStore.Images.Media.DATE_ADDED} <= ?")
        args.add(snapshotTimestampSec.toString())

        if (bucketId != null) {
            whereConditions.add("${MediaStore.Images.Media.BUCKET_ID} = ?")
            args.add(bucketId)
        }

        val selection = whereConditions.joinToString(" AND ")
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC, ${MediaStore.Images.Media._ID} DESC"

        try {
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                args.toTypedArray(),
                sortOrder
            )?.use { c ->
                val idCol = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (c.moveToNext()) {
                    val id = c.getLong(idCol)
                    uris.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id))
                }
            }
        } catch (e: Exception) {
            TestLog.e(MODULE, "查询当前相册所有URI失败: ${e.message}", e)
        }

        return uris
    }

    private fun updateBottomBar() {
        val count = selectedUris.size
        binding.tvSelectedCount.text = "已选 $count 张"
        binding.btnConfirmImport.text = "导入 ($count)"
        binding.btnConfirmImport.isEnabled = count > 0

        val currentBucketTotal = bucketList.firstOrNull { it.bucketId == currentBucketId }?.count ?: 0
        if (currentBucketTotal > 0 && currentDisplayItems.isNotEmpty() && currentDisplayItems.all { selectedUris.contains(it.uri) }) {
            binding.btnSelectAll.text = "取消全选"
        } else {
            binding.btnSelectAll.text = "全选当前相册"
        }
    }

    private fun confirmSelection() {
        if (selectedUris.isEmpty()) return

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val token = withContext(Dispatchers.IO) {
                PickerResultStore.saveResultUris(this@MediaPickerActivity, selectedUris)
            }
            binding.progressBar.visibility = View.GONE

            if (token != null) {
                val intent = Intent().apply {
                    putExtra(EXTRA_RESULT_TOKEN, token)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@MediaPickerActivity, "传递选图结果失败，请重试", Toast.LENGTH_SHORT).show()
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
        loadPageJob?.cancel()
        if (::slideHelper.isInitialized) {
            slideHelper.cleanup()
        }
    }
}
