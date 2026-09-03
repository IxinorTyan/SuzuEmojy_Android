package com.suzu.test.resource.delete

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.room.withTransaction
import com.suzu.test.databinding.DialogExportProgressBinding
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 分批异步删除资源，避免巨量资源删除时长时间占用一个事务或一次性执行大量文件操作。
 *
 * 取消只在当前批次完成后生效，保证不会中断 Room 事务。
 */
object ResourceDeleteHelper {
    private const val MODULE = "ResourceDeleteHelper"
    private const val BATCH_SIZE = 50

    data class DeleteResult(
        val deletedCount: Int,
        val failedCount: Int,
        val isCancelled: Boolean
    )

    private data class BatchDeleteResult(
        val deletedCount: Int,
        val failedCount: Int
    )

    fun deleteResources(
        context: Context,
        scope: CoroutineScope,
        database: SuzuDatabase,
        resources: List<ResourceEntity>,
        title: String = "删除表情",
        onComplete: (DeleteResult) -> Unit
    ): Job {
        if (resources.isEmpty()) {
            return scope.launch {
                onComplete(DeleteResult(0, 0, false))
            }
        }

        val appContext = context.applicationContext
        val resourcesDir = File(appContext.filesDir, "resources")
        val total = resources.size
        val cancelled = AtomicBoolean(false)

        val binding = DialogExportProgressBinding.inflate(LayoutInflater.from(context)).apply {
            progressBarExport.max = total
            progressBarExport.progress = 0
            tvProgressMessage.text = "正在删除 0/$total"
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(binding.root)
            .setCancelable(false)
            .setNegativeButton("取消", null)
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { button ->
            if (cancelled.compareAndSet(false, true)) {
                button.isEnabled = false
                (button as? android.widget.TextView)?.text = "取消中…"
                binding.tvProgressMessage.text = "正在完成当前批次，请稍候…"
            }
        }

        return scope.launch {
            var deletedCount = 0
            var failedCount = 0
            var processedCount = 0

            try {
                for (batch in resources.chunked(BATCH_SIZE)) {
                    if (cancelled.get()) break

                    val batchResult = withContext(Dispatchers.IO) {
                        try {
                            val ids = batch.map { it.id }
                            val affected = database.withTransaction {
                                database.resourceDao().deleteByIds(ids)
                            }

                            if (affected != batch.size) {
                                TestLog.w(
                                    MODULE,
                                    "删除批次记录数不一致: 请求=${batch.size}, 实际=$affected"
                                )
                                BatchDeleteResult(
                                    deletedCount = affected,
                                    failedCount = batch.size - affected
                                )
                            } else {
                                // 数据库事务成功后再删除物理文件，避免事务失败导致文件丢失。
                                var fileDeleteFailures = 0
                                batch.forEach { resource ->
                                    val file = File(resourcesDir, resource.filename)
                                    if (file.exists() && !file.delete()) {
                                        fileDeleteFailures++
                                        TestLog.w(
                                            MODULE,
                                            "删除物理文件失败: ${file.absolutePath}"
                                        )
                                    }
                                }
                                BatchDeleteResult(
                                    deletedCount = affected,
                                    failedCount = fileDeleteFailures
                                )
                            }
                        } catch (e: Exception) {
                            TestLog.e(MODULE, "删除批次失败: ${e.message}", e)
                            BatchDeleteResult(
                                deletedCount = 0,
                                failedCount = batch.size
                            )
                        }
                    }

                    deletedCount += batchResult.deletedCount
                    failedCount += batchResult.failedCount
                    processedCount += batch.size

                    // 当前协程运行在主线程；分批完成后更新一次 UI，并让出主线程处理 Room Flow 刷新。
                    binding.progressBarExport.progress = processedCount
                    binding.tvProgressMessage.text = "正在删除 $processedCount/$total"
                    kotlinx.coroutines.yield()
                }
            } catch (e: CancellationException) {
                cancelled.set(true)
                throw e
            } finally {
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }

            onComplete(DeleteResult(deletedCount, failedCount, cancelled.get() || processedCount < total))
        }
    }
}
