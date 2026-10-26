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
import kotlinx.coroutines.NonCancellable
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

data class FolderImportSelection(
    val items: List<FolderImportItem>,
    val categories: List<FolderImportCategory>,
    val unreadableDirectories: List<String> = emptyList()
)

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
                mutableState.update { it.copy(
                    message = "导入已取消，已导入的图片保留，原图未清理",
                    result = null,
                    folderSelection = null
                ) }
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
        val rootId = DocumentsContract.getTreeDocumentId(uri)
        val scan = FolderImageScanner { documentId -> queryDirectoryEntries(uri, documentId) }
            .scan(rootId, getDocumentDisplayName(uri, rootId)) { directories, images ->
                updateProgress("已扫描 $directories 个文件夹，找到 $images 张图片…")
            }
        val items = scan.images.map {
            FolderImportItem(DocumentsContract.buildDocumentUriUsingTree(uri, it.documentId), it.categoryName)
        }
        val grouped = LinkedHashMap<String?, Int>()
        items.forEach { item ->
            grouped[item.categoryName] = (grouped[item.categoryName] ?: 0) + 1
        }
        val existingNames = database.categoryDao().getAllCategories().map { it.name }.toSet()
        val categories = grouped.map { (name, count) ->
            FolderImportCategory(name, count, alreadyExists = name in existingNames)
        }
        val warning = if (scan.unreadableDirectories.isNotEmpty()) {
            "（有 ${scan.unreadableDirectories.size} 个子文件夹无法读取）"
        } else ""
        val message = if (items.isEmpty()) {
            "未找到可导入的图片$warning"
        } else {
            "请选择是否导入$warning"
        }
        mutableState.update { it.copy(
            message = message,
            summary = formatUnreadableDirectories(scan.unreadableDirectories),
            folderSelection = if (items.isEmpty()) null else FolderImportSelection(
                items = items,
                categories = categories,
                unreadableDirectories = scan.unreadableDirectories
            )
        ) }
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

    fun importImages(
        items: List<FolderImportItem>,
        presetCategoryId: Long?,
        unreadableDirectories: List<String> = emptyList()
    ) = start {
        // Read a source once but preserve every category association reported for that source.
        val itemsByUri = items.groupBy { it.uri }
        val total = itemsByUri.size
        var successCount = 0
        var duplicateCount = 0
        var failCount = 0
        val attachedNames = linkedSetOf<String>()
        var presetDeleted = false
        val eligibleUris = mutableListOf<Uri>()
        val itemRecords = mutableListOf<ImportItemRecord>()
        try {
            itemsByUri.entries.forEachIndexed { index, (uri, sourceItems) ->
                currentCoroutineContext().ensureActive()
                updateProgress("正在导入 ${index + 1}/$total", index, total)
                val result = try {
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
                    if (bytes == null || bytes.isEmpty()) throw java.io.IOException("读取失败或文件为空")
                    importService.import(bytes)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    TestLog.e(MODULE, "导入异常 (URI=$uri): ${e.message}", e)
                    failCount++
                    val reason = when {
                        e is java.io.IOException || e is SecurityException -> ImportFailReason.READ_FAILED
                        e.message?.contains("Animated WebP", ignoreCase = true) == true -> ImportFailReason.UNSUPPORTED_ANIM_WEBP
                        e.message?.contains("Unsupported format", ignoreCase = true) == true -> ImportFailReason.UNSUPPORTED_OTHER
                        e.message?.contains("decode", ignoreCase = true) == true -> ImportFailReason.DECODE_FAILED
                        else -> ImportFailReason.UNKNOWN
                    }
                    itemRecords.add(ImportItemRecord(
                        sourceUri = uri, type = ImportItemType.FAILED,
                        syncKey = null, resourceId = null, filename = null,
                        existingResourceId = null, existingFilename = null, failReason = reason
                    ))
                    return@forEachIndexed
                }

                if (result.isDuplicate) duplicateCount++ else successCount++
                itemRecords.add(ImportItemRecord(
                    sourceUri = uri,
                    type = if (result.isDuplicate) ImportItemType.DUPLICATE else ImportItemType.NEW_ADDED,
                    syncKey = result.syncKey, resourceId = result.resourceId, filename = result.filename,
                    existingResourceId = result.existingResourceId,
                    existingFilename = result.existingFilename, failReason = null
                ))

                // Finish this image's short relation transaction even when the user cancels.
                // Never defer all category relations until the entire import has finished.
                withContext(NonCancellable) {
                    val (names, missingPreset) = database.withTransaction {
                        val categoryIds = linkedSetOf<Long>()
                        val names = linkedSetOf<String>()
                        val preset = presetCategoryId?.let { database.categoryDao().getCategoryById(it) }
                        if (preset != null) {
                            categoryIds.add(preset.id)
                            names.add(preset.name)
                        }
                        val folderNames = sourceItems.mapNotNull { normalizeFolderCategoryName(it.categoryName) }.distinct()
                        for (name in folderNames) {
                            val existing = database.categoryDao().getCategoryByName(name)
                            val categoryId = existing?.id ?: database.categoryDao().insertCategory(
                                CategoryEntity(
                                    name = name,
                                    sortOrder = (database.categoryDao().getMaxSortOrder() ?: 0) + 1
                                )
                            )
                            categoryIds.add(categoryId)
                            names.add(name)
                        }
                        for (categoryId in categoryIds) {
                            database.resourceCategoryDao().addResourcesToCategoryBatch(
                                listOf(result.resourceId), categoryId
                            )
                        }
                        names to (presetCategoryId != null && preset == null)
                    }
                    attachedNames.addAll(names)
                    presetDeleted = presetDeleted || missingPreset
                }
                eligibleUris.add(uri)
            }

            currentCoroutineContext().ensureActive()
            val destination = if (attachedNames.isEmpty()) "" else {
                val names = attachedNames.take(5).joinToString("") { "「$it」" }
                if (attachedNames.size > 5) "（已归入${names}等 ${attachedNames.size} 个分类）"
                else "（已归入$names）"
            }
            val presetWarning = if (presetDeleted) "\n原目标分类已不存在，已跳过该分类。" else ""
            val scanWarning = formatUnreadableDirectories(unreadableDirectories)
            val summary = "新增 $successCount 张，重复 $duplicateCount 张，失败 $failCount 张$destination" +
                presetWarning + if (scanWarning.isNotEmpty()) "\n$scanWarning" else ""
            mutableState.update { it.copy(
                message = "导入流程已完成", summary = summary, progress = total,
                result = ImportTaskResult(itemRecords, eligibleUris, ImportSummary(successCount, duplicateCount, failCount))
            ) }
        } finally {
            // No source cleanup is offered after cancellation or an interrupted category transaction.
            mutableState.update {
                if (it.result == null) it.copy(summary = "已新增 $successCount 张，重复 $duplicateCount 张，失败 $failCount 张") else it
            }
        }
    }

    private fun getDocumentDisplayName(treeUri: Uri, documentId: String): String? {
        return try {
            val documentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId)
            val projection = arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            val cursor = app.contentResolver.query(documentUri, projection, null, null, null)
            cursor?.use {
                if (!it.moveToFirst()) return@use null
                val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                if (nameIndex == -1) null else it.getString(nameIndex)
            }
        } catch (e: Exception) {
            TestLog.w(MODULE, "获取文件夹名称失败: ${e.message}")
            null
        }
    }

    private suspend fun queryDirectoryEntries(treeUri: Uri, documentId: String): List<FolderDocument> {
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, documentId)
        val projection = arrayOf(
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME
        )
        val cursor = app.contentResolver.query(childrenUri, projection, null, null, null)
            ?: throw IllegalStateException("查询返回 null Cursor")
        return cursor.use {
            val idIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
            val mimeIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE)
            val nameIndex = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            require(idIndex != -1 && mimeIndex != -1) { "文件提供程序未返回目录信息" }

            buildList {
                while (it.moveToNext()) {
                    currentCoroutineContext().ensureActive()
                    val childDocumentId = it.getString(idIndex) ?: continue
                    add(
                        FolderDocument(
                            id = childDocumentId,
                            mimeType = if (mimeIndex == -1) null else it.getString(mimeIndex),
                            name = if (nameIndex == -1) null else it.getString(nameIndex)
                        )
                    )
                }
            }
        }
    }


    companion object {
        private const val MODULE = "ImportTask"
    }
}
