package com.suzu.test.ui.import

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.suzu.test.databinding.ActivityImportBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.ui.picker.MediaPickerActivity
import com.suzu.test.ui.picker.PickerResultStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImportActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ImportActivity"
        const val EXTRA_TARGET_CATEGORY_ID = "extra_target_category_id"
    }

    private var presetCategoryId: Long? = null
    private var presetCategoryName: String? = null

    private lateinit var binding: ActivityImportBinding
    private lateinit var database: SuzuDatabase
    private lateinit var importService: ResourceImportService

    private val customPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val token = result.data?.getStringExtra(MediaPickerActivity.EXTRA_RESULT_TOKEN)
            val uris = PickerResultStore.consumeResultUris(this, token)
            if (uris.isNotEmpty()) {
                startImportProcess(uris)
            } else {
                binding.tvProgress.text = "未选择任何图片"
            }
        }
    }

    private val systemPhotoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(50)
    ) { uris ->
        if (uris.isNotEmpty()) {
            startImportProcess(uris)
        } else {
            binding.tvProgress.text = "未选择任何图片"
        }
    }

    private val systemDocumentPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (!uris.isNullOrEmpty()) {
            startImportProcess(uris)
        } else {
            binding.tvProgress.text = "未选择任何图片"
        }
    }

    private val openDocumentTreeLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { treeUri ->
        if (treeUri != null) {
            handleDocumentTreeSelected(treeUri)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        handlePermissionResult()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.getDatabase(this)
        importService = ResourceImportService(this, database)

        val targetCatId = intent.getLongExtra(EXTRA_TARGET_CATEGORY_ID, -1L)
        if (targetCatId != -1L) {
            presetCategoryId = targetCatId
            loadPresetCategoryInfo(targetCatId)
        }

        binding.btnPickImages.setOnClickListener {
            checkAndLaunchPicker()
        }

        binding.btnPickFromFiles.setOnClickListener {
            launchSystemFilePicker()
        }

        binding.btnPickFromFolder.setOnClickListener {
            openDocumentTreeLauncher.launch(null)
        }
    }

    private fun launchSystemFilePicker() {
        systemDocumentPickerLauncher.launch(
            arrayOf(
                "image/*",
                "image/png",
                "image/jpeg",
                "image/webp",
                "image/gif"
            )
        )
    }

    private fun checkAndLaunchPicker() {
        if (hasFullMediaPermission()) {
            launchCustomPicker()
        } else {
            requestMediaPermissions()
        }
    }

    private fun hasFullMediaPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestMediaPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions)
    }

    private fun handlePermissionResult() {
        if (hasFullMediaPermission()) {
            launchCustomPicker()
        } else {
            fallbackToSystemPicker()
        }
    }

    private fun launchCustomPicker() {
        PickerResultStore.clearResidualFiles(this)
        val intent = Intent(this, MediaPickerActivity::class.java)
        customPickerLauncher.launch(intent)
    }

    private fun fallbackToSystemPicker() {
        Toast.makeText(
            this,
            "已使用系统选择器，授予完整相册权限可使用滑动多选与删除原图功能",
            Toast.LENGTH_LONG
        ).show()

        systemPhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private fun loadPresetCategoryInfo(catId: Long) {
        lifecycleScope.launch {
            val category = withContext(Dispatchers.IO) {
                database.categoryDao().getCategoryById(catId)
            }
            if (category != null) {
                presetCategoryName = category.name
                val titleView = binding.root.getChildAt(0) as? android.widget.TextView
                titleView?.text = "导入表情到「${category.name}」"
            } else {
                presetCategoryId = null
            }
        }
    }

    private fun handleDocumentTreeSelected(treeUri: Uri) {
        setButtonsEnabled(false)
        binding.tvProgress.text = "正在扫描文件夹..."
        binding.tvSummary.text = ""

        lifecycleScope.launch {
            val imageUris = try {
                withContext(Dispatchers.IO) {
                    scanDirectChildImages(treeUri)
                }
            } catch (e: Exception) {
                TestLog.e(MODULE, "扫描文件夹失败: ${e.message}", e)
                null
            }

            if (imageUris == null) {
                Toast.makeText(this@ImportActivity, "无法读取该文件夹", Toast.LENGTH_SHORT).show()
                binding.tvProgress.text = "扫描失败"
                setButtonsEnabled(true)
                return@launch
            }

            if (imageUris.isEmpty()) {
                Toast.makeText(this@ImportActivity, "未找到可导入的图片", Toast.LENGTH_SHORT).show()
                binding.tvProgress.text = "未找到可导入的图片"
                setButtonsEnabled(true)
                return@launch
            }

            val targetName = presetCategoryName
            val message = if (!targetName.isNullOrBlank()) {
                "找到 ${imageUris.size} 张图片，确认导入到「$targetName」？"
            } else {
                "找到 ${imageUris.size} 张图片，确认导入？"
            }

            AlertDialog.Builder(this@ImportActivity)
                .setTitle("确认导入")
                .setMessage(message)
                .setPositiveButton("确认") { _, _ ->
                    startImportProcess(imageUris)
                }
                .setNegativeButton("取消") { _, _ ->
                    binding.tvProgress.text = "已取消导入"
                    setButtonsEnabled(true)
                }
                .setOnCancelListener {
                    binding.tvProgress.text = "已取消导入"
                    setButtonsEnabled(true)
                }
                .show()
        }
    }

    private fun getFolderName(treeUri: Uri): String? {
        return try {
            val treeDocId = DocumentsContract.getTreeDocumentId(treeUri)
            val docUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, treeDocId)
            val projection = arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            val cursor = contentResolver.query(docUri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                    if (nameIndex != -1) it.getString(nameIndex) else null
                } else null
            }
        } catch (e: Exception) {
            TestLog.w(MODULE, "获取文件夹名称失败: ${e.message}")
            null
        }
    }

    private fun scanDirectChildImages(treeUri: Uri): List<Uri> {
        val result = mutableListOf<Uri>()
        val docId = DocumentsContract.getTreeDocumentId(treeUri)
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, docId)

        val projection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME
        )

        val supportedExtensions = setOf("png", "jpg", "jpeg", "gif", "webp", "bmp")

        val cursor = contentResolver.query(childrenUri, projection, null, null, null)
        cursor?.use {
            val idIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
            val mimeIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE)
            val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)

            while (it.moveToNext()) {
                val childDocId = if (idIndex != -1) it.getString(idIndex) else null ?: continue
                val mimeType = if (mimeIndex != -1) it.getString(mimeIndex) else null
                val displayName = if (nameIndex != -1) it.getString(nameIndex) else null

                // 硬性排除子文件夹
                if (mimeType == DocumentsContract.Document.MIME_TYPE_DIR) {
                    continue
                }

                val ext = displayName?.substringAfterLast('.', "")?.lowercase() ?: ""
                val isImageMime = mimeType?.startsWith("image/", ignoreCase = true) == true
                val isSupportedExt = ext in supportedExtensions

                if (isImageMime || isSupportedExt) {
                    val documentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, childDocId)
                    result.add(documentUri)
                }
            }
        } ?: throw IllegalStateException("查询返回 null Cursor")

        return result
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        binding.btnPickImages.isEnabled = enabled
        binding.btnPickFromFiles.isEnabled = enabled
        binding.btnPickFromFolder.isEnabled = enabled
    }

    private fun startImportProcess(uris: List<Uri>) {
        setButtonsEnabled(false)
        binding.tvSummary.text = ""

        lifecycleScope.launch {
            val total = uris.size
            var successCount = 0
            var duplicateCount = 0
            var failCount = 0
            val processedResourceIds = mutableListOf<Long>()

            val (actualAttachedCategoryName, categoryDeleted) = withContext(Dispatchers.IO) {
                uris.forEachIndexed { index, uri ->
                    withContext(Dispatchers.Main) {
                        binding.tvProgress.text = "正在导入 ${index + 1}/$total"
                    }

                    try {
                        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
                        if (bytes == null || bytes.isEmpty()) {
                            TestLog.e(MODULE, "读取 URI 失败或文件为空: $uri")
                            failCount++
                            return@forEachIndexed
                        }

                        val result = importService.import(bytes)
                        processedResourceIds.add(result.resourceId)
                        if (result.isDuplicate) {
                            duplicateCount++
                        } else {
                            successCount++
                        }
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "导入异常 (URI=$uri): ${e.message}", e)
                        failCount++
                    }
                }

                // 细则 1: 先 await addResourcesToCategoryBatch 完成再返回
                val targetCatId = presetCategoryId
                val distinctIds = processedResourceIds.distinct()
                var attachedCatName: String? = null
                var isDeleted = false

                if (targetCatId != null && distinctIds.isNotEmpty()) {
                    try {
                        val category = database.categoryDao().getCategoryById(targetCatId)
                        if (category != null) {
                            attachedCatName = category.name
                            database.resourceCategoryDao().addResourcesToCategoryBatch(distinctIds, targetCatId)
                            TestLog.i(MODULE, "已将 ${distinctIds.size} 张表情关联到分类「${category.name}」(ID=$targetCatId)")
                        } else {
                            isDeleted = true
                            TestLog.w(MODULE, "目标分类已被删除 (ID=$targetCatId)，跳过关联分类操作")
                        }
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "关联分类异常: ${e.message}", e)
                    }
                }
                attachedCatName to isDeleted
            }

            binding.tvProgress.text = "导入流程已完成"
            val summaryText = when {
                categoryDeleted -> {
                    "新增 $successCount 张，重复 $duplicateCount 张，失败 $failCount 张（目标分类已不存在，仅导入资源库）"
                }
                actualAttachedCategoryName != null -> {
                    "新增 $successCount 张，重复 $duplicateCount 张（已置顶并加入「$actualAttachedCategoryName」），失败 $failCount 张"
                }
                else -> {
                    "成功导入 $successCount 张，重复跳过 $duplicateCount 张，失败 $failCount 张"
                }
            }
            binding.tvSummary.text = summaryText
            setButtonsEnabled(true)
        }
    }
}
