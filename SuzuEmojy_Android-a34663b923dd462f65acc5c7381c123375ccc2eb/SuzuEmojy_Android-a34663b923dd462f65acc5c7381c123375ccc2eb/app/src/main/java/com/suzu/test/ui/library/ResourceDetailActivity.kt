package com.suzu.test.ui.library

import android.content.ClipData
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.withTransaction
import androidx.core.content.FileProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.suzu.test.BuildConfig
import com.suzu.test.databinding.ActivityResourceDetailBinding
import com.suzu.test.databinding.ItemDetailImageBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.KeywordUtils
import com.suzu.test.resource.delete.ResourceDeleteHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResourceDetailActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ResourceDetailActivity"
        const val EXTRA_RESOURCE_IDS = "extra_resource_ids"
        const val EXTRA_START_POSITION = "extra_start_position"
        const val EXTRA_CURRENT_POSITION = "extra_current_position"
        const val EXTRA_DELETED = "extra_deleted"
        const val EXTRA_DELETED_INDEX = "extra_deleted_index"
    }

    private lateinit var binding: ActivityResourceDetailBinding
    private lateinit var database: SuzuDatabase
    private lateinit var resourcesDir: File

    private val items = mutableListOf<ResourceEntity>()
    private var currentPosition: Int = 0
    private var detailAdapter: DetailPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResourceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.getDatabase(this)
        resourcesDir = File(filesDir, "resources").apply { if (!exists()) mkdirs() }

        val ids = intent.getLongArrayExtra(EXTRA_RESOURCE_IDS) ?: longArrayOf()
        val startPos = intent.getIntExtra(EXTRA_START_POSITION, 0)
        currentPosition = startPos

        setupViews()
        loadData(ids, startPos)
    }

    private fun setupViews() {
        binding.btnDetailBack.setOnClickListener {
            finishWithPositionResult()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishWithPositionResult()
            }
        })

        binding.tvDetailKeywords.setOnClickListener {
            val res = getCurrentResource() ?: return@setOnClickListener
            showEditKeywordsDialog(res)
        }

        binding.tvDetailCategories.setOnClickListener {
            val res = getCurrentResource() ?: return@setOnClickListener
            showEditCategoriesDialog(res)
        }

        binding.btnDetailDelete.setOnClickListener {
            val res = getCurrentResource() ?: return@setOnClickListener
            showDeleteConfirmDialog(res)
        }

        binding.btnDetailShare.setOnClickListener {
            shareCurrentResource()
        }
    }

    private fun shareCurrentResource() {
        val resource = getCurrentResource() ?: return
        val file = File(resourcesDir, resource.filename)

        if (!file.isFile) {
            Toast.makeText(this, "图片文件不存在，无法分享", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val uri = FileProvider.getUriForFile(
                this,
                BuildConfig.FILE_PROVIDER_AUTHORITY,
                file
            )
            val mimeType = contentResolver.getType(uri)
                ?: when {
                    resource.isAnimated || resource.format.equals("gif", ignoreCase = true) ->
                        "image/gif"
                    resource.format.equals("webp", ignoreCase = true) ->
                        "image/webp"
                    resource.format.equals("jpg", ignoreCase = true) ||
                        resource.format.equals("jpeg", ignoreCase = true) ->
                        "image/jpeg"
                    else -> "image/png"
                }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                clipData = ClipData.newRawUri("suzu_resource_image", uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "分享图片"))
        } catch (e: Exception) {
            TestLog.e(MODULE, "拉起系统分享选择器失败: ${e.message}", e)
            Toast.makeText(this, "无法分享图片", Toast.LENGTH_SHORT).show()
        }
    }

    private fun finishWithPositionResult() {
        val intent = Intent().apply {
            putExtra(EXTRA_CURRENT_POSITION, currentPosition)
            putExtra(EXTRA_DELETED, false)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getCurrentResource(): ResourceEntity? {
        return if (currentPosition in items.indices) items[currentPosition] else null
    }

    private fun loadData(ids: LongArray, startPos: Int) {
        lifecycleScope.launch {
            val ordered = withContext(Dispatchers.IO) {
                val idList = ids.toList()
                val targets = database.resourceDao().getResourcesByIds(idList)
                val map = targets.associateBy { it.id }
                idList.mapNotNull { map[it] }
            }

            if (ordered.isEmpty()) {
                Toast.makeText(this@ResourceDetailActivity, "未找到表情数据", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            items.clear()
            items.addAll(ordered)

            val safeStartPos = startPos.coerceIn(0, items.size - 1)
            currentPosition = safeStartPos

            detailAdapter = DetailPagerAdapter(resourcesDir, items)
            binding.vpDetailPager.adapter = detailAdapter
            binding.vpDetailPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentPosition = position
                    updateUIForPosition(position)
                }
            })

            binding.vpDetailPager.setCurrentItem(safeStartPos, false)
            updateUIForPosition(safeStartPos)
        }
    }

    private fun updateUIForPosition(position: Int) {
        if (position !in items.indices) return
        val res = items[position]

        binding.tvPositionIndicator.text = "${position + 1} / ${items.size}"

        // 关键词
        val kw = res.keywords.trim()
        binding.tvDetailKeywords.text = if (kw.isNotEmpty()) kw else "暂无关键词 (点击添加)"

        // 异步加载分类
        lifecycleScope.launch {
            val cats = withContext(Dispatchers.IO) {
                database.resourceCategoryDao().getCategoriesForResource(res.id)
            }
            if (currentPosition == position) {
                binding.tvDetailCategories.text = if (cats.isNotEmpty()) {
                    cats.joinToString("、") { it.name }
                } else {
                    "未分类 (点击归类)"
                }
            }
        }

        // 元数据
        binding.tvMetaResolution.text = if (res.width > 0 && res.height > 0) {
            "尺寸: ${res.width} × ${res.height}"
        } else {
            "尺寸: 读取中"
        }
        if (res.width <= 0 || res.height <= 0) {
            lifecycleScope.launch {
                val dimensions = withContext(Dispatchers.IO) {
                    val file = File(resourcesDir, res.filename)
                    if (!file.exists()) {
                        null
                    } else {
                        val options = BitmapFactory.Options().apply {
                            inJustDecodeBounds = true
                        }
                        BitmapFactory.decodeFile(file.absolutePath, options)
                        if (options.outWidth > 0 && options.outHeight > 0) {
                            options.outWidth to options.outHeight
                        } else {
                            null
                        }
                    }
                }
                if (currentPosition == position && dimensions != null) {
                    val (width, height) = dimensions
                    binding.tvMetaResolution.text = "尺寸: $width × $height"
                    val updated = res.copy(width = width, height = height)
                    items[position] = updated
                    withContext(Dispatchers.IO) {
                        database.resourceDao().update(updated)
                    }
                } else if (currentPosition == position) {
                    binding.tvMetaResolution.text = "尺寸: 未知"
                }
            }
        }
        binding.tvMetaSize.text = "大小: ${formatFileSize(res.byteSize)}"
        binding.tvMetaFormat.text = "格式: ${res.format} ${if (res.isAnimated) "(动图)" else "(静态)"}"

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        binding.tvMetaImportTime.text = "导入时间: ${sdf.format(Date(res.createdAt))}"
    }

    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes >= 1024 * 1024 -> String.format(Locale.getDefault(), "%.2f MB", bytes / (1024f * 1024f))
            bytes >= 1024 -> String.format(Locale.getDefault(), "%.1f KB", bytes / 1024f)
            else -> "$bytes B"
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
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        database.resourceDao().updateKeywords(resource.id, normalized)
                    }
                    val updated = resource.copy(keywords = normalized)
                    if (currentPosition in items.indices && items[currentPosition].id == resource.id) {
                        items[currentPosition] = updated
                        updateUIForPosition(currentPosition)
                    }
                    Toast.makeText(this@ResourceDetailActivity, "关键词已保存", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showEditCategoriesDialog(resource: ResourceEntity) {
        lifecycleScope.launch {
            val allCats: List<CategoryEntity> = withContext(Dispatchers.IO) {
                database.categoryDao().getAllCategories()
            }
            if (allCats.isEmpty()) {
                Toast.makeText(this@ResourceDetailActivity, "暂无分类，请先在资源库创建分类", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val currentAssignedCatIds = withContext(Dispatchers.IO) {
                database.resourceCategoryDao().getCategoryIdsByResourceId(resource.id).toSet()
            }

            val catNames = allCats.map { it.name }.toTypedArray()
            val checkedItems = BooleanArray(allCats.size) { i -> currentAssignedCatIds.contains(allCats[i].id) }

            AlertDialog.Builder(this@ResourceDetailActivity)
                .setTitle("归类表情")
                .setMultiChoiceItems(catNames, checkedItems) { _, which, isChecked -> checkedItems[which] = isChecked }
                .setPositiveButton("保存") { _, _ ->
                    saveCategoryChanges(resource.id, allCats, checkedItems)
                }
                .setNegativeButton("取消", null)
                .show()
        }
    }

    private fun saveCategoryChanges(resourceId: Long, allCats: List<CategoryEntity>, checked: BooleanArray) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val rcDao = database.resourceCategoryDao()
                val currentIds = rcDao.getCategoryIdsByResourceId(resourceId).toSet()
                val now = System.currentTimeMillis()

                database.withTransaction {
                    for (i in allCats.indices) {
                        val catId = allCats[i].id
                        val isChecked = checked[i]
                        if (isChecked && !currentIds.contains(catId)) {
                            val minSort = rcDao.getMinSortOrderForCategory(catId) ?: 0
                            rcDao.addResourceToCategory(ResourceCategoryEntity(
                                resourceId = resourceId,
                                categoryId = catId,
                                sortOrder = minSort - 1,
                                addedAt = now
                            ))
                        } else if (!isChecked && currentIds.contains(catId)) {
                            rcDao.removeResourceFromCategory(resourceId, catId)
                        }
                    }
                }
            }
            updateUIForPosition(currentPosition)
            Toast.makeText(this@ResourceDetailActivity, "分类设置已保存", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmDialog(resource: ResourceEntity) {
        AlertDialog.Builder(this)
            .setTitle("删除确认")
            .setMessage("确定永久删除此表情包吗？(将同步清理数据库与本地文件)")
            .setPositiveButton("删除") { _, _ ->
                executeDelete(resource)
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun executeDelete(resource: ResourceEntity) {
        val deletedIdx = currentPosition
        ResourceDeleteHelper.deleteResources(
            context = this,
            scope = lifecycleScope,
            database = database,
            resources = listOf(resource),
            title = "删除表情"
        ) { result ->
            if (result.isCancelled || result.failedCount > 0 || result.deletedCount != 1) {
                Toast.makeText(this, "删除失败，原表情已保留", Toast.LENGTH_SHORT).show()
                return@deleteResources
            }

            TestLog.i(MODULE, "成功删除表情: ID=${resource.id}, file=${resource.filename}")
            Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show()

            val intent = Intent().apply {
                putExtra(EXTRA_DELETED, true)
                putExtra(EXTRA_DELETED_INDEX, deletedIdx)
                putExtra(EXTRA_CURRENT_POSITION, deletedIdx)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onDestroy() {
        // 清理 ViewPager2 图片解码，防止后台继续解码 GIF 耗电
        detailAdapter?.clearAllGlide(binding.vpDetailPager)
        super.onDestroy()
    }

    private class DetailPagerAdapter(
        private val resourcesDir: File,
        private val list: List<ResourceEntity>
    ) : RecyclerView.Adapter<DetailPagerAdapter.DetailViewHolder>() {

        private val activeHolders = mutableSetOf<DetailViewHolder>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
            val binding = ItemDetailImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return DetailViewHolder(binding)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            activeHolders.add(holder)
            holder.bind(resourcesDir, list[position])
        }

        override fun onViewRecycled(holder: DetailViewHolder) {
            activeHolders.remove(holder)
            Glide.with(holder.itemView.context).clear(holder.binding.ivDetailImage)
            super.onViewRecycled(holder)
        }

        fun clearAllGlide(viewPager: ViewPager2) {
            for (holder in activeHolders) {
                try {
                    Glide.with(holder.itemView.context).clear(holder.binding.ivDetailImage)
                } catch (e: Exception) {
                    // ignore
                }
            }
            activeHolders.clear()
        }

        override fun getItemCount(): Int = list.size

        class DetailViewHolder(val binding: ItemDetailImageBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(resourcesDir: File, item: ResourceEntity) {
                val file = File(resourcesDir, item.filename)
                Glide.with(itemView.context)
                    .load(file)
                    .fitCenter()
                    .into(binding.ivDetailImage)
            }
        }
    }
}
