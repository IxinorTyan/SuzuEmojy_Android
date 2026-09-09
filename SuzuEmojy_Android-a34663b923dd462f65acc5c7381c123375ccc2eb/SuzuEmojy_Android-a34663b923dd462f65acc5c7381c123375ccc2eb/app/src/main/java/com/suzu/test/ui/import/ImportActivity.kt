package com.suzu.test.ui.import

import android.Manifest
import android.app.Activity
import android.app.RecoverableSecurityException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.withTransaction
import com.suzu.test.databinding.ActivityImportBinding
import com.suzu.test.databinding.DialogImportFinishCleanupBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.importpkg.PackageImportStage
import com.suzu.test.storage.CacheCleanManager
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import com.suzu.test.ui.picker.MediaPickerActivity
import com.suzu.test.ui.picker.PickerResultStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImportActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ImportActivity"
        const val EXTRA_TARGET_CATEGORY_ID = "extra_target_category_id"
        private const val PREFS_NAME = "import_prefs"
        private const val KEY_CLEAN_ORIGINALS_CHECKED = "clean_originals_checked"
    }

    private var presetCategoryId: Long? = null
    private var presetCategoryName: String? = null

    private lateinit var binding: ActivityImportBinding
    private lateinit var database: SuzuDatabase
    private lateinit var importService: ResourceImportService
    private lateinit var packageImportService: ResourcePackageImportService
    private val viewModel: ImportViewModel by viewModels()
    private var activeImportJob: Job? = null

    // 运行期暂存当前 API 29 / MediaStore 单项/批量处理时的进度
    private var currentDeletingUriIndex = 0
    private var deletedSuccessCount = 0
    private var currentCleaningBatch = listOf<Uri>()
    private var currentUnhandledCount = 0

    // API 30+ 批量移入回收站 / API 29 单项删除授权 Launcher
    private val intentSenderLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val success = (result.resultCode == Activity.RESULT_OK)
            val cleanedCount = if (success) currentCleaningBatch.size else 0
            val failCount = if (success) 0 else currentCleaningBatch.size
            val totalUnhandled = currentUnhandledCount + failCount
            notifyCleanupFinished(cleanedCount, totalUnhandled)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            if (result.resultCode == Activity.RESULT_OK) {
                deletedSuccessCount++
            }
            // 继续处理下一个
            currentDeletingUriIndex++
            processNextApi29Delete()
        }
    }

    // API 26-28 申请 WRITE_EXTERNAL_STORAGE 权限 Launcher
    private val writePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            executeDirectDelete(currentCleaningBatch, currentUnhandledCount)
        } else {
            notifyCleanupFinished(0, currentUnhandledCount + currentCleaningBatch.size)
        }
    }

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

    private val openZipLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            handlePackageZipSelected(uri)
        } else {
            binding.tvProgress.text = "未选择资源包"
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        handlePermissionResult()
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            // 先取消导入，待其 finally 完成后再清理，避免导入任务在清理后重新写入暂存。
            val importJob = activeImportJob
            activeImportJob = null
            importJob?.cancel()

            ImportResultHolder.clear()

            val cleanup = {
                kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
                    CacheCleanManager.cleanImportPreviewFiles(applicationContext)
                    CacheCleanManager.cleanImportTempZipFiles(applicationContext)
                }
            }

            if (importJob != null) {
                importJob.invokeOnCompletion { cleanup() }
            } else {
                cleanup()
            }
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.getDatabase(this)
        importService = ResourceImportService(this, database)
        packageImportService = ResourcePackageImportService(this, database, importService)

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

        binding.btnImportFromPackage.setOnClickListener {
            launchPackagePicker()
        }

        binding.tvViewDetailAction.setOnClickListener {
            val intent = Intent(this, ImportResultDetailActivity::class.java)
            startActivity(intent)
        }

        // 页面重建后如果之前有待弹出的对话框，恢复弹出
        val pendingSummary = viewModel.lastImportSummary
        if (!viewModel.isDialogShown && pendingSummary != null) {
            showCleanupDialog(
                viewModel.pendingCleanableUris,
                viewModel.unhandledCount,
                pendingSummary
            )
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

    private fun launchPackagePicker() {
        openZipLauncher.launch(arrayOf("application/zip"))
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
            val (imageUris, rawFolderName) = try {
                withContext(Dispatchers.IO) {
                    val uris = scanDirectChildImages(treeUri)
                    val name = getFolderName(treeUri)
                    uris to name
                }
            } catch (e: Exception) {
                TestLog.e(MODULE, "扫描文件夹失败: ${e.message}", e)
                null to null
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

            val folderName = rawFolderName?.trim().takeIf { !it.isNullOrEmpty() }
            val isReserved = folderName == "全部" || folderName == "ALL" || folderName == "最近"
            val validFolderName = if (isReserved) null else folderName

            val presetName = presetCategoryName

            val message = when {
                !presetName.isNullOrBlank() && !validFolderName.isNullOrBlank() && presetName != validFolderName -> {
                    "找到 ${imageUris.size} 张图片，确认导入并归入「$presetName」和「$validFolderName」两个分类？"
                }
                !validFolderName.isNullOrBlank() -> {
                    "找到 ${imageUris.size} 张图片，确认导入并归入分类「$validFolderName」？"
                }
                !presetName.isNullOrBlank() -> {
                    "找到 ${imageUris.size} 张图片，确认导入到「$presetName」？"
                }
                else -> {
                    "找到 ${imageUris.size} 张图片，确认导入？"
                }
            }

            AlertDialog.Builder(this@ImportActivity)
                .setTitle("确认导入")
                .setMessage(message)
                .setPositiveButton("确认") { _, _ ->
                    startImportProcess(imageUris, folderCategoryName = validFolderName)
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
        binding.btnImportFromPackage.isEnabled = enabled
    }

    private fun handlePackageZipSelected(uri: Uri) {
        setButtonsEnabled(false)
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.progressBar.isIndeterminate = true
        binding.tvProgress.text = "准备导入 zip 资源包..."
        binding.tvSummary.text = ""
        activeImportJob = lifecycleScope.launch {
            try {
                val result = packageImportService.importFromZip(uri) { stage, progress, total ->
                    runOnUiThread {
                        if (isFinishing || isDestroyed) return@runOnUiThread
                        binding.progressBar.visibility = android.view.View.VISIBLE
                        when (stage) {
                            PackageImportStage.COPYING -> {
                                if (total > 0) {
                                    binding.progressBar.isIndeterminate = false
                                    binding.progressBar.max = 100
                                    binding.progressBar.progress = (progress * 100 / total).toInt()
                                    binding.tvProgress.text = "正在读取资源包: ${progress * 100 / total}%"
                                } else {
                                    binding.progressBar.isIndeterminate = true
                                    binding.tvProgress.text = "正在读取资源包..."
                                }
                            }
                            PackageImportStage.PARSING -> {
                                binding.progressBar.isIndeterminate = true
                                binding.tvProgress.text = "正在解析配置..."
                            }
                            PackageImportStage.IMPORTING -> {
                                binding.progressBar.isIndeterminate = false
                                binding.progressBar.max = total.toInt()
                                binding.progressBar.progress = progress.toInt()
                                binding.tvProgress.text = "正在导入: $progress / $total"
                            }
                        }
                    }
                }
                val aggregateCards = buildAggregateCards(result.records)
                ImportResultHolder.records = result.records
                ImportResultHolder.aggregateCards = aggregateCards
                ImportResultHolder.zipPreviewFilePaths = result.records
                    .mapNotNull { it.previewFilePath }
                    .distinct()
                if (aggregateCards.isEmpty()) {
                    clearZipPreviewFiles()
                }
                binding.tvProgress.text = "zip 资源包导入完成"
                binding.tvSummary.text = "新增 ${result.summary.successCount} 张，重复 ${result.summary.duplicateCount} 张，失败 ${result.summary.failCount} 张"
                binding.tvViewDetailAction.visibility = if (aggregateCards.isNotEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            } catch (e: CancellationException) {
                // 页面退出时取消导入，不能当作普通失败继续处理。
                throw e
            } catch (e: Exception) {
                TestLog.e(MODULE, "资源包导入失败: ${e.message}", e)
                clearZipPreviewFiles()
                binding.tvProgress.text = "zip 资源包导入失败"
                Toast.makeText(this@ImportActivity, e.message ?: "资源包导入失败", Toast.LENGTH_LONG).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                setButtonsEnabled(true)
            }
        }
    }

    private fun startImportProcess(uris: List<Uri>, folderCategoryName: String? = null) {
        // 普通导入不会使用 zip 预览；清掉已失效的上一批预览，防止长期累积。
        lifecycleScope.launch(Dispatchers.IO) {
            CacheCleanManager.cleanImportPreviewFiles(this@ImportActivity)
        }
        setButtonsEnabled(false)
        binding.tvSummary.text = ""

        activeImportJob = lifecycleScope.launch {
            // 先将源 URI 去重，防止同一个 URI 重复进入统计
            val distinctSourceUris = uris.distinct()
            val total = distinctSourceUris.size
            var successCount = 0
            var duplicateCount = 0
            var failCount = 0
            val processedResourceIds = mutableListOf<Long>()
            val eligibleUris = mutableListOf<Uri>()
            val itemRecords = mutableListOf<ImportItemRecord>()

            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.progressBar.isIndeterminate = false
            binding.progressBar.max = total
            binding.progressBar.progress = 0

            try {
                val (attachedCategoryNames, presetDeleted) = withContext(Dispatchers.IO) {
                    distinctSourceUris.forEachIndexed { index, uri ->
                        withContext(Dispatchers.Main) {
                            binding.progressBar.progress = index
                            binding.tvProgress.text = "正在导入 ${index + 1}/$total"
                        }

                        try {
                            val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
                            if (bytes == null || bytes.isEmpty()) {
                                TestLog.e(MODULE, "读取 URI 失败或文件为空: $uri")
                                failCount++
                                itemRecords.add(
                                    ImportItemRecord(
                                        sourceUri = uri,
                                        type = ImportItemType.FAILED,
                                        syncKey = null,
                                        resourceId = null,
                                        filename = null,
                                        existingResourceId = null,
                                        existingFilename = null,
                                        failReason = ImportFailReason.READ_FAILED
                                    )
                                )
                                return@forEachIndexed
                            }

                            val result = importService.import(bytes)
                            processedResourceIds.add(result.resourceId)
                            eligibleUris.add(uri)
                            if (result.isDuplicate) {
                                duplicateCount++
                                itemRecords.add(
                                    ImportItemRecord(
                                        sourceUri = uri,
                                        type = ImportItemType.DUPLICATE,
                                        syncKey = result.syncKey,
                                        resourceId = result.resourceId,
                                        filename = result.filename,
                                        existingResourceId = result.existingResourceId,
                                        existingFilename = result.existingFilename,
                                        failReason = null
                                    )
                                )
                            } else {
                                successCount++
                                itemRecords.add(
                                    ImportItemRecord(
                                        sourceUri = uri,
                                        type = ImportItemType.NEW_ADDED,
                                        syncKey = result.syncKey,
                                        resourceId = result.resourceId,
                                        filename = result.filename,
                                        existingResourceId = null,
                                        existingFilename = null,
                                        failReason = null
                                    )
                                )
                            }
                        } catch (e: CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            TestLog.e(MODULE, "导入异常 (URI=$uri): ${e.message}", e)
                            failCount++
                            val reason = when {
                                e.message?.contains("Animated WebP", ignoreCase = true) == true -> ImportFailReason.UNSUPPORTED_ANIM_WEBP
                                e.message?.contains("Unsupported format", ignoreCase = true) == true -> ImportFailReason.UNSUPPORTED_OTHER
                                e.message?.contains("Failed to decode", ignoreCase = true) == true || e.message?.contains("decode", ignoreCase = true) == true -> ImportFailReason.DECODE_FAILED
                                else -> ImportFailReason.UNKNOWN
                            }
                            itemRecords.add(
                                ImportItemRecord(
                                    sourceUri = uri,
                                    type = ImportItemType.FAILED,
                                    syncKey = null,
                                    resourceId = null,
                                    filename = null,
                                    existingResourceId = null,
                                    existingFilename = null,
                                    failReason = reason
                                )
                            )
                        }
                    }

                    val distinctIds = processedResourceIds.distinct()
                    val attachedNames = mutableListOf<String>()
                    var isPresetDeleted = false

                    if (distinctIds.isNotEmpty()) {
                        database.withTransaction {
                            val targetCatId = presetCategoryId
                            var resolvedPresetCatId: Long? = null
                            if (targetCatId != null) {
                                val category = database.categoryDao().getCategoryById(targetCatId)
                                if (category != null) {
                                    resolvedPresetCatId = category.id
                                    attachedNames.add(category.name)
                                } else {
                                    isPresetDeleted = true
                                    TestLog.w(MODULE, "预设分类已被删除 (ID=$targetCatId)，跳过关联预设分类操作")
                                }
                            }

                            var resolvedFolderCatId: Long? = null
                            if (!folderCategoryName.isNullOrBlank()) {
                                val existingFolderCat = database.categoryDao().getCategoryByName(folderCategoryName)
                                val fCatId = if (existingFolderCat != null) {
                                    existingFolderCat.id
                                } else {
                                    val maxSort = database.categoryDao().getMaxSortOrder() ?: 0
                                    database.categoryDao().insertCategory(CategoryEntity(name = folderCategoryName, sortOrder = maxSort + 1))
                                }
                                resolvedFolderCatId = fCatId
                                if (!attachedNames.contains(folderCategoryName)) {
                                    attachedNames.add(folderCategoryName)
                                }
                            }

                            val targetCatIds = listOfNotNull(resolvedPresetCatId, resolvedFolderCatId).distinct()
                            for (catId in targetCatIds) {
                                try {
                                    database.resourceCategoryDao().addResourcesToCategoryBatch(distinctIds, catId)
                                    TestLog.i(MODULE, "已将 ${distinctIds.size} 张表情关联到分类 (ID=$catId)")
                                } catch (e: Exception) {
                                    TestLog.e(MODULE, "关联分类异常 (ID=$catId): ${e.message}", e)
                                }
                            }
                        }
                    }
                    attachedNames to isPresetDeleted
                }

                binding.tvProgress.text = "导入流程已完成"
                val summaryText = when {
                    presetDeleted && attachedCategoryNames.isEmpty() -> {
                        "新增 $successCount 张，重复 $duplicateCount 张，失败 $failCount 张（目标分类已不存在，仅导入资源库）"
                    }
                    attachedCategoryNames.isNotEmpty() -> {
                        val catText = attachedCategoryNames.joinToString("") { "「$it」" }
                        "新增 $successCount 张，重复 $duplicateCount 张（已归入$catText），失败 $failCount 张"
                    }
                    else -> {
                        "成功导入 $successCount 张，重复跳过 $duplicateCount 张，失败 $failCount 张"
                    }
                }
                binding.tvSummary.text = summaryText

                // 构建聚合卡片数据
                val aggregateCards = buildAggregateCards(itemRecords)
                ImportResultHolder.records = itemRecords
                ImportResultHolder.aggregateCards = aggregateCards

                // 若有重复或失败，展示查看详情入口
                if (aggregateCards.isNotEmpty()) {
                    binding.tvViewDetailAction.visibility = android.view.View.VISIBLE
                } else {
                    binding.tvViewDetailAction.visibility = android.view.View.GONE
                }

                // 导入结束后，分流待清理 URI 并弹窗提示
                handlePostImportCleanup(eligibleUris, successCount, duplicateCount, failCount)
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                setButtonsEnabled(true)
            }
        }
    }

    private fun handlePostImportCleanup(
        eligibleUris: List<Uri>,
        successCount: Int,
        duplicateCount: Int,
        failCount: Int
    ) {
        val distinctEligible = eligibleUris.distinct()
        val cleanableUris = mutableListOf<Uri>()
        var unhandledCount = 0

        for (uri in distinctEligible) {
            if (isCleanableMediaStoreUri(uri)) {
                cleanableUris.add(uri)
            } else {
                unhandledCount++
            }
        }

        val summary = ImportSummary(successCount, duplicateCount, failCount)
        viewModel.setPendingCleanup(cleanableUris, unhandledCount, summary)

        showCleanupDialog(cleanableUris, unhandledCount, summary)
    }

    private fun buildAggregateCards(records: List<ImportItemRecord>): List<ImportAggregateCard> {
        val cards = mutableListOf<ImportAggregateCard>()

        // 1. 重复项聚合：按 syncKey 分组 (排除 FAILED 项)
        val validSyncGroups = records
            .filter { it.type != ImportItemType.FAILED && !it.syncKey.isNullOrBlank() }
            .groupBy { it.syncKey!! }

        for ((syncKey, group) in validSyncGroups) {
            val duplicateRecords = group.filter { it.type == ImportItemType.DUPLICATE }
            val newAddedRecords = group.filter { it.type == ImportItemType.NEW_ADDED }

            // 仅当存在重复项时才生成重复聚合卡片
            if (duplicateRecords.isNotEmpty()) {
                val hasNewAdded = newAddedRecords.isNotEmpty()
                if (hasNewAdded) {
                    // 类型 B: 本批次内部重复 (库中原本没有)
                    val newAddedRecord = newAddedRecords.first()
                    cards.add(
                        ImportAggregateCard.DuplicateCard(
                            isTypeA = false,
                            count = group.size,
                            mainFilename = newAddedRecord.filename,
                            mainUri = newAddedRecord.sourceUri,
                            mainPreviewFilePath = newAddedRecord.previewFilePath,
                            itemUris = group.map { it.sourceUri },
                            itemPreviewFilePaths = group.map { it.previewFilePath },
                            syncKey = syncKey
                        )
                    )
                } else {
                    // 类型 A: 库中已有
                    val firstDup = duplicateRecords.first()
                    cards.add(
                        ImportAggregateCard.DuplicateCard(
                            isTypeA = true,
                            count = duplicateRecords.size,
                            mainFilename = firstDup.existingFilename,
                            mainUri = firstDup.sourceUri,
                            mainPreviewFilePath = firstDup.previewFilePath,
                            itemUris = duplicateRecords.map { it.sourceUri },
                            itemPreviewFilePaths = duplicateRecords.map { it.previewFilePath },
                            syncKey = syncKey
                        )
                    )
                }
            }
        }

        // 2. 失败项聚合：按 failReason 分组
        val failedRecords = records.filter { it.type == ImportItemType.FAILED && it.failReason != null }
        val failGroups = failedRecords.groupBy { it.failReason!! }

        for ((reason, group) in failGroups) {
            cards.add(
                ImportAggregateCard.FailureCard(
                    reason = reason,
                    count = group.size,
                    itemUris = group.map { it.sourceUri }
                )
            )
        }

        return cards
    }

    private fun isCleanableMediaStoreUri(uri: Uri): Boolean {
        // 自建选择器的 MediaStore URI: authority == "media" 且 path 不含 "/picker"
        // 排除 Photo Picker (path 包含 /picker 或 authority 为 photopicker)
        // 排除 SAF / Document URI
        val auth = uri.authority ?: return false
        val path = uri.path ?: ""
        return (auth == "media" || auth == "com.android.providers.media") && !path.contains("/picker", ignoreCase = true)
    }

    private fun showCleanupDialog(
        cleanableUris: List<Uri>,
        unhandledCount: Int,
        summary: ImportSummary
    ) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val defaultChecked = prefs.getBoolean(KEY_CLEAN_ORIGINALS_CHECKED, false)

        val dialogBinding = DialogImportFinishCleanupBinding.inflate(LayoutInflater.from(this))
        dialogBinding.tvImportStats.text = "成功 ${summary.successCount} 张 · 重复 ${summary.duplicateCount} 张 · 失败 ${summary.failCount} 张"

        val checkboxText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            "清理本地原图 (移入回收站，30 天内可在图库回收站恢复)"
        } else {
            "清理本地原图 (永久删除，不可恢复)"
        }
        dialogBinding.cbCleanOriginals.text = checkboxText
        dialogBinding.cbCleanOriginals.isChecked = defaultChecked

        val dialog = AlertDialog.Builder(this)
            .setTitle("导入完成")
            .setView(dialogBinding.root)
            .setPositiveButton("完成") { _, _ ->
                val isChecked = dialogBinding.cbCleanOriginals.isChecked
                prefs.edit().putBoolean(KEY_CLEAN_ORIGINALS_CHECKED, isChecked).apply()

                viewModel.isDialogShown = true
                if (isChecked) {
                    startCleanupExecution(cleanableUris, unhandledCount)
                } else {
                    viewModel.clearCleanupState()
                }
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun startCleanupExecution(cleanableUris: List<Uri>, unhandledCount: Int) {
        if (cleanableUris.isEmpty()) {
            notifyCleanupFinished(0, unhandledCount)
            return
        }

        currentCleaningBatch = cleanableUris
        currentUnhandledCount = unhandledCount

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // API 30+ 移入回收站
                try {
                    val pendingIntent = MediaStore.createTrashRequest(contentResolver, cleanableUris, true)
                    val request = IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                    intentSenderLauncher.launch(request)
                } catch (e: Exception) {
                    TestLog.e(MODULE, "createTrashRequest 失败: ${e.message}", e)
                    notifyCleanupFinished(0, unhandledCount + cleanableUris.size)
                }
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                // API 29 逐条处理 RecoverableSecurityException
                currentDeletingUriIndex = 0
                deletedSuccessCount = 0
                processNextApi29Delete()
            }
            else -> {
                // API 26-28 检查 WRITE_EXTERNAL_STORAGE
                val hasWrite = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                if (hasWrite) {
                    executeDirectDelete(cleanableUris, unhandledCount)
                } else {
                    writePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun processNextApi29Delete() {
        if (currentDeletingUriIndex >= currentCleaningBatch.size) {
            val totalCleaned = deletedSuccessCount
            val totalUnhandled = currentUnhandledCount + (currentCleaningBatch.size - deletedSuccessCount)
            notifyCleanupFinished(totalCleaned, totalUnhandled)
            return
        }

        val uri = currentCleaningBatch[currentDeletingUriIndex]
        try {
            val rows = contentResolver.delete(uri, null, null)
            if (rows > 0) {
                deletedSuccessCount++
            }
            currentDeletingUriIndex++
            processNextApi29Delete()
        } catch (recoverable: RecoverableSecurityException) {
            val intentSender = recoverable.userAction.actionIntent.intentSender
            val request = IntentSenderRequest.Builder(intentSender).build()
            intentSenderLauncher.launch(request)
        } catch (e: SecurityException) {
            TestLog.w(MODULE, "API 29 删除无权限 (URI=$uri): ${e.message}")
            currentDeletingUriIndex++
            processNextApi29Delete()
        } catch (e: Exception) {
            TestLog.e(MODULE, "API 29 删除异常 (URI=$uri): ${e.message}", e)
            currentDeletingUriIndex++
            processNextApi29Delete()
        }
    }

    private fun executeDirectDelete(cleanableUris: List<Uri>, unhandledCount: Int) {
        lifecycleScope.launch {
            var success = 0
            var failed = 0
            withContext(Dispatchers.IO) {
                for (uri in cleanableUris) {
                    try {
                        val rows = contentResolver.delete(uri, null, null)
                        if (rows > 0) {
                            success++
                        } else {
                            failed++
                        }
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "直接删除异常 (URI=$uri): ${e.message}", e)
                        failed++
                    }
                }
            }
            notifyCleanupFinished(success, unhandledCount + failed)
        }
    }

    private fun clearZipPreviewFiles() {
        val paths = ImportResultHolder.zipPreviewFilePaths
        ImportResultHolder.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            CacheCleanManager.cleanImportPreviewFiles(this@ImportActivity, paths)
        }
    }

    private fun notifyCleanupFinished(cleanedCount: Int, unhandledCount: Int) {
        viewModel.clearCleanupState()
        val message = if (unhandledCount > 0) {
            "已清理 $cleanedCount 张原图；$unhandledCount 张来自其他应用或文件管理器，需在系统文件管理中手动处理"
        } else {
            "已清理 $cleanedCount 张原图"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
