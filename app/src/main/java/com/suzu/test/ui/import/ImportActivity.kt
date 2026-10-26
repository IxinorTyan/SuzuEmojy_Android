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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.activity.OnBackPressedCallback
import com.suzu.test.databinding.ActivityImportBinding
import com.suzu.test.databinding.DialogImportFinishCleanupBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.log.TestLog
import com.suzu.test.storage.CacheCleanManager
import com.suzu.test.ui.picker.MediaPickerActivity
import com.suzu.test.ui.picker.PickerResultStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImportActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ImportActivity"
        private const val MAX_FOLDER_PREVIEW_CATEGORIES = 30
        const val EXTRA_TARGET_CATEGORY_ID = "extra_target_category_id"
        private const val PREFS_NAME = "import_prefs"
        private const val KEY_CLEAN_ORIGINALS_CHECKED = "clean_originals_checked"
        private const val WEB_SHARE_URL = "https://suzuemojy-share.pages.dev/"
    }

    private var presetCategoryId: Long? = null
    private var presetCategoryName: String? = null

    private lateinit var binding: ActivityImportBinding
    private lateinit var database: SuzuDatabase
    private val viewModel: ImportViewModel by viewModels()
    private val taskModel: ImportTaskViewModel by viewModels()
    private var renderedResult: ImportTaskResult? = null

    // 运行期暂存当前 API 29 / MediaStore 单项/批量处理时的进度
    private var currentDeletingUriIndex: Int
        get() = viewModel.currentDeletingUriIndex
        set(value) { viewModel.currentDeletingUriIndex = value }
    private var deletedSuccessCount: Int
        get() = viewModel.deletedSuccessCount
        set(value) { viewModel.deletedSuccessCount = value }
    private var currentCleaningBatch: List<Uri>
        get() = viewModel.currentCleaningBatch
        set(value) { viewModel.currentCleaningBatch = value }
    private var currentUnhandledCount: Int
        get() = viewModel.currentUnhandledCount
        set(value) { viewModel.currentUnhandledCount = value }

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
        folderDialog?.dismiss()
        cleanupDialog?.dismiss()
        if (isFinishing && !taskModel.state.value.running) {
            ImportResultHolder.clear()
            val app = applicationContext
            kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
                CacheCleanManager.cleanImportPreviewFiles(app)
                CacheCleanManager.cleanImportTempZipFiles(app)
            }
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseProvider.getDatabase(this)

        val targetCatId = intent.getLongExtra(EXTRA_TARGET_CATEGORY_ID, -1L)
        if (targetCatId != -1L) {
            presetCategoryId = targetCatId
            loadPresetCategoryInfo(targetCatId)
        }

        binding.btnBack.setOnClickListener { requestExit() }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = requestExit()
        })
        observeImportTask()

        binding.btnOpenWebShare.setOnClickListener {
            openUrl(WEB_SHARE_URL)
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

        binding.tvProgress.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: android.text.Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.layoutStatusSection.visibility = android.view.View.VISIBLE
                }
            }
        })

        binding.tvSummary.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: android.text.Editable?) {
                val hasText = !s.isNullOrBlank()
                binding.tvSummary.visibility = if (hasText) android.view.View.VISIBLE else android.view.View.GONE
                if (hasText) {
                    binding.layoutStatusSection.visibility = android.view.View.VISIBLE
                }
            }
        })

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

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Exception) {
            Toast.makeText(this, "无法打开浏览器", Toast.LENGTH_SHORT).show()
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
                binding.tvTitle.text = "导入到「${category.name}」"
                binding.tvSubtitle.text = "导入的表情将自动添加至此分类"
            } else {
                presetCategoryId = null
            }
        }
    }

    private var folderDialog: AlertDialog? = null
    private var cleanupDialog: AlertDialog? = null

    private fun handleDocumentTreeSelected(treeUri: Uri) {
        taskModel.scanFolder(treeUri)
    }

    private fun showFolderConfirmation(selection: FolderImportSelection) {
        if (folderDialog != null) return
        val visibleCategories = selection.categories.take(MAX_FOLDER_PREVIEW_CATEGORIES)
        val categoryPreview = visibleCategories.joinToString("\n") { category ->
            val action = when {
                category.name == null -> "仅导入资源库"
                category.alreadyExists -> "合并到已有分类"
                else -> "新建分类"
            }
            "${category.name ?: "不创建分类"}：${category.count} 张（$action）"
        }.let { preview ->
            if (selection.categories.size > visibleCategories.size) {
                "$preview\n...另有 ${selection.categories.size - visibleCategories.size} 个分类"
            } else preview
        }
        val message = buildString {
            append("找到 ${selection.items.size} 张图片。")
            if (categoryPreview.isNotEmpty()) {
                append("\n\n分类预览：\n")
                append(categoryPreview)
            }
            append("\n\n按一级子文件夹创建分类，包含其下所有层级的图片；当前文件夹中的图片归入同名分类。空文件夹不创建分类。")
            if (!presetCategoryName.isNullOrBlank()) {
                append("\n同时添加到「$presetCategoryName」分类。")
            }
            if (selection.unreadableDirectories.isNotEmpty()) {
                append("\n\n")
                append(formatUnreadableDirectories(selection.unreadableDirectories))
            }
            append("\n\n确认导入吗？")
        }
        folderDialog = AlertDialog.Builder(this)
            .setTitle("确认导入")
            .setMessage(message)
            .setPositiveButton("确认") { _, _ ->
                taskModel.clearFolderSelection()
                taskModel.importImages(selection.items, presetCategoryId, selection.unreadableDirectories)
            }
            .setNegativeButton("取消") { _, _ -> taskModel.clearFolderSelection() }
            .setOnCancelListener { taskModel.clearFolderSelection() }
            .create().also { it.show() }
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        binding.btnPickImages.isEnabled = enabled
        binding.btnPickFromFiles.isEnabled = enabled
        binding.btnPickFromFolder.isEnabled = enabled
        binding.btnImportFromPackage.isEnabled = enabled
    }

    private fun handlePackageZipSelected(uri: Uri) {
        taskModel.importZip(uri)
    }

    private fun startImportProcess(uris: List<Uri>) {
        val items = uris.distinct().map { FolderImportItem(it, null) }
        taskModel.importImages(items, presetCategoryId)
    }

    private fun observeImportTask() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    taskModel.state.collect { state ->
                        setButtonsEnabled(!state.running && state.folderSelection == null)
                        if (state.folderSelection != null && !state.running) showFolderConfirmation(state.folderSelection)
                        else { folderDialog?.dismiss(); folderDialog = null }
                        binding.progressBar.visibility = if (state.running) android.view.View.VISIBLE else android.view.View.GONE
                        binding.progressBar.isIndeterminate = state.progress == null
                        binding.progressBar.max = state.total
                        binding.progressBar.progress = state.progress ?: 0
                        if (state.message.isNotEmpty()) {
                            binding.tvProgress.text = state.message
                            binding.tvSummary.text = state.summary
                        }
                        val result = state.result
                        if (!state.running && result != null) {
                            if (renderedResult !== result) {
                                renderedResult = result
                                val cards = buildAggregateCards(result.records)
                                ImportResultHolder.records = result.records
                                ImportResultHolder.aggregateCards = cards
                                ImportResultHolder.zipPreviewFilePaths = result.records.mapNotNull { it.previewFilePath }.distinct()
                                binding.tvViewDetailAction.visibility = if (cards.isEmpty()) android.view.View.GONE else android.view.View.VISIBLE
                            }
                            if (!taskModel.cleanupHandled && result.cleanupSummary != null) {
                                taskModel.cleanupHandled = true
                                val summary = result.cleanupSummary
                                handlePostImportCleanup(result.eligibleUris, summary.successCount, summary.duplicateCount, summary.failCount)
                            }
                        } else if (state.running) {
                            binding.tvViewDetailAction.visibility = android.view.View.GONE
                        }
                    }
                } finally {
                    folderDialog?.dismiss()
                    folderDialog = null
                }
            }
        }
    }

    private fun requestExit() {
        if (!taskModel.state.value.running) { finish(); return }
        AlertDialog.Builder(this)
            .setTitle("取消导入并退出？")
            .setMessage("已导入的图片会保留，原图不会清理。切到其他应用无需取消，返回这里可以继续查看进度。")
            .setNegativeButton("继续导入", null)
            .setPositiveButton("取消并退出") { _, _ ->
                lifecycleScope.launch {
                    binding.tvProgress.text = "正在取消，请稍候…"
                    taskModel.cancelAndJoin()
                    finish()
                }
            }.show()
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

        cleanupDialog?.dismiss()
        cleanupDialog = dialog
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
