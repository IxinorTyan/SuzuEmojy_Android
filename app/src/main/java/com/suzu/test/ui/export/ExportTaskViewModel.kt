package com.suzu.test.ui.export

import android.app.Application
import android.net.Uri
import android.provider.DocumentsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.resource.exportpkg.PackageExportScope
import com.suzu.test.resource.exportpkg.PackageExportStage
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ExportTaskState(
    val running: Boolean = false,
    val message: String = "",
    val summary: String = "",
    val progress: Int = 0,
    val total: Int = 100
)

class ExportTaskViewModel(application: Application, private val savedState: SavedStateHandle) : AndroidViewModel(application) {
    private val app = application
    private val mutableState = MutableStateFlow(ExportTaskState(message =
        if (savedState.get<Boolean>("running") == true) "上次导出已中断，请重新导出；原保存位置可能留有未完成的文件" else ""))
    val state = mutableState.asStateFlow()
    private var job: Job? = null

    fun export(uri: Uri, suggestedName: String, categories: List<Long>, scope: PackageExportScope) {
        if (state.value.running) return
        savedState["running"] = true
        mutableState.value = ExportTaskState(running = true, message = "正在准备导出…")
        job = viewModelScope.launch {
            try {
                val (result, displayName) = withContext(Dispatchers.IO) {
                    val name = queryName(uri) ?: "$suggestedName.zip"
                    val packageName = if (name.endsWith(".zip", true)) name.dropLast(4) else name
                    ResourcePackageExportService(app, DatabaseProvider.getDatabase(app)).exportToZip(
                        uri, packageName, categories, scope
                    ) { stage, progress, total ->
                        val verb = if (stage == PackageExportStage.PACKING) "准备" else "写入"
                        mutableState.update { it.copy(message = "正在$verb: $progress / $total", progress = progress, total = total.coerceAtLeast(1)) }
                    } to name
                }
                val warnings = result.manifest.optJSONArray("warnings")?.length() ?: 0
                mutableState.update { it.copy(message = "导出完成：$displayName",
                    summary = "导出 ${result.exportedCount} 张，跳过 ${result.skippedCount} 张" + if (warnings > 0) "，$warnings 个图标未导出" else "") }
            } catch (e: CancellationException) {
                mutableState.update { it.copy(message = "正在取消并清理未完成文件…") }
                val cleaned = cleanup(uri)
                mutableState.update { it.copy(message = "导出已取消", summary = if (cleaned) "" else "未能清理未完成文件，请在保存位置手动删除") }
                throw e
            } catch (e: Exception) {
                val cleaned = cleanup(uri)
                mutableState.update { it.copy(message = "导出失败", summary = (e.message ?: "无法写入文件") +
                    if (cleaned) "" else "\n未能清理未完成文件，请在保存位置手动删除") }
            } finally {
                savedState["running"] = false
                mutableState.update { it.copy(running = false) }
            }
        }
    }

    fun cancel() { job?.cancel() }
    suspend fun cancelAndJoin() { job?.cancel(); job?.join() }

    private suspend fun cleanup(uri: Uri): Boolean = withContext(NonCancellable + Dispatchers.IO) {
        try { DocumentsContract.deleteDocument(app.contentResolver, uri) } catch (_: Exception) { false }
    }

    private fun queryName(uri: Uri): String? = try {
        app.contentResolver.query(uri, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null)?.use {
            if (it.moveToFirst()) it.getString(0)?.takeIf(String::isNotBlank) else null
        }
    } catch (_: Exception) { null }
}
