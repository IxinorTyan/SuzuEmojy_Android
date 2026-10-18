package com.suzu.test.ui.import

import android.app.Application
import android.net.Uri
import android.provider.DocumentsContract
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.log.TestLog
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.importpkg.PackageImportStage
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import com.suzu.test.storage.CacheCleanManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ImportTaskState(
    val running: Boolean = false,
    val message: String = "",
    val summary: String = "",
    val progress: Int? = null,
    val total: Int = 100,
    val result: ImportTaskResult? = null,
    val folderSelection: FolderImportSelection? = null
)

data class FolderImportSelection(val uris: List<Uri>, val categoryName: String?)

data class ImportTaskResult(
    val records: List<ImportItemRecord>,
    val eligibleUris: List<Uri> = emptyList(),
    val cleanupSummary: ImportSummary? = null
)

/** Tasks keep only application context, so rotating the screen never retains an old Activity. */
class ImportTaskViewModel(application: Application, private val savedState: SavedStateHandle) : AndroidViewModel(application) {
    private val app = application
    private val database = DatabaseProvider.getDatabase(app)
    private val importService = ResourceImportService(app, database)
    private val packageService = ResourcePackageImportService(app, database, importService)
    private val mutableState = MutableStateFlow(ImportTaskState(message =
        if (savedState.get<Boolean>("running") == true) "上次导入已中断，已导入的图片保留，可重新选择文件继续去重导入" else ""))
    val state = mutableState.asStateFlow()
    var cleanupHandled = false
    private var job: Job? = null

    private fun updateProgress(message: String, progress: Int? = null, total: Int = 100) {
        mutableState.update { it.copy(message = message, progress = progress, total = total.coerceAtLeast(1)) }
    }

    private fun start(clearPreviews: Boolean = true, block: suspend () -> Unit) {
        if (state.value.running) return
        cleanupHandled = false
        savedState["running"] = true
        mutableState.value = ImportTaskState(running = true, message = "准备导入…")
        job = viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (clearPreviews) CacheCleanManager.cleanImportPreviewFiles(app)
                    block()
                }
            } catch (e: CancellationException) {
                mutableState.update { it.copy(message = "导入已取消，已导入的图片保留，原图未清理") }
                throw e
            } catch (e: Exception) {
                TestLog.e("ImportTask", "导入失败", e)
                mutableState.update { it.copy(message = "导入失败，已导入的图片保留", summary = listOf(it.summary, e.message ?: "无法读取文件").filter { text -> text.isNotBlank() }.joinToString("\n")) }
            } finally {
                savedState["running"] = false
                mutableState.update { it.copy(running = false) }
            }
        }
    }

    suspend fun cancelAndJoin() { job?.cancel(); job?.join() }

    fun scanFolder(uri: Uri) = start(clearPreviews = false) {
        updateProgress("正在扫描文件夹…")
        val uris = scanDirectChildImages(uri)
        val name = getFolderName(uri)?.trim()?.takeIf { it.isNotEmpty() && it !in setOf("全部", "ALL", "最近") }
        mutableState.update { it.copy(message = if (uris.isEmpty()) "未找到可导入的图片" else "请选择是否导入",
            folderSelection = if (uris.isEmpty()) null else FolderImportSelection(uris, name)) }
    }

    fun clearFolderSelection() {
        mutableState.update { it.copy(folderSelection = null, message = "已取消文件夹选择") }
    }

    fun importZip(uri: Uri) = start {
        val result = packageService.importFromZip(uri) { stage, progress, total ->
            when (stage) {
                PackageImportStage.COPYING -> if (total > 0) {
                    val percent = (progress * 100 / total).toInt()
                    updateProgress("正在读取资源包: $percent%", percent)
                } else updateProgress("正在读取资源包…")
                PackageImportStage.PARSING -> updateProgress("正在解析配置…")
                PackageImportStage.IMPORTING -> updateProgress("正在导入: $progress / $total", progress.toInt(), total.toInt())
            }
        }
        val summary = result.summary
        mutableState.update { it.copy(
            message = "zip 资源包导入完成",
            summary = "新增 ${summary.successCount} 张，重复 ${summary.duplicateCount} 张，失败 ${summary.failCount} 张" +
                if (summary.warnings.isEmpty()) "" else "\n" + summary.warnings.joinToString("\n"),
            result = ImportTaskResult(result.records)
        ) }
    }

    fun importImages(uris: List<Uri>, presetCategoryId: Long?, folderCategoryName: String?) = start {
        val distinctSourceUris = uris.distinct()
        val total = distinctSourceUris.size
        var successCount = 0
        var duplicateCount = 0
        var failCount = 0
        val processedResourceIds = mutableListOf<Long>()
        val eligibleUris = mutableListOf<Uri>()
        val itemRecords = mutableListOf<ImportItemRecord>()
        try {
            val (attachedCategoryNames, presetDeleted) = withContext(Dispatchers.IO) {
                distinctSourceUris.forEachIndexed { index, uri ->
                    currentCoroutineContext().ensureActive()
                    updateProgress("正在导入 ${index + 1}/$total", index, total)

                    try {
                        val bytes = app.contentResolver.openInputStream(uri)?.use { input ->
                            val output = java.io.ByteArrayOutputStream()
                            val buffer = ByteArray(64 * 1024)
                            while (true) {
                                currentCoroutineContext().ensureActive()
                                val count = input.read(buffer)
                                if (count < 0) break
                                output.write(buffer, 0, count)
                            }
                            output.toByteArray()
                        }
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
                            } catch (e: CancellationException) {
                                throw e
                            } catch (e: Exception) {
                                TestLog.e(MODULE, "关联分类异常 (ID=$catId): ${e.message}", e)
                            }
                        }
                    }
                }
                attachedNames to isPresetDeleted
            }

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

            mutableState.update { it.copy(message = "导入流程已完成", summary = summaryText,
                result = ImportTaskResult(itemRecords, eligibleUris, ImportSummary(successCount, duplicateCount, failCount))) }
        } finally {
            // Preserve completed counts even on cancellation, without offering to delete source images.
            mutableState.update { if (it.result == null) it.copy(summary = "已新增 $successCount 张，重复 $duplicateCount 张，失败 $failCount 张") else it }
        }
    }

    private fun getFolderName(treeUri: Uri): String? {
        return try {
            val treeDocId = DocumentsContract.getTreeDocumentId(treeUri)
            val docUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, treeDocId)
            val projection = arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            val cursor = app.contentResolver.query(docUri, projection, null, null, null)
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

    private suspend fun scanDirectChildImages(treeUri: Uri): List<Uri> {
        val result = mutableListOf<Uri>()
        val docId = DocumentsContract.getTreeDocumentId(treeUri)
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, docId)

        val projection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME
        )

        val supportedExtensions = setOf("png", "jpg", "jpeg", "gif", "webp", "bmp")

        val cursor = app.contentResolver.query(childrenUri, projection, null, null, null)
        cursor?.use {
            val idIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
            val mimeIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE)
            val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)

            while (it.moveToNext()) {
                currentCoroutineContext().ensureActive()
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


    companion object { private const val MODULE = "ImportTask" }
}
