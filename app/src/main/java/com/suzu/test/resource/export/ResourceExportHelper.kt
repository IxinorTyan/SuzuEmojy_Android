package com.suzu.test.resource.export

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.suzu.test.databinding.DialogExportProgressBinding
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean

object ResourceExportHelper {
    private const val MODULE = "ResourceExportHelper"
    private const val BASE_FOLDER = "Pictures/SuzuEmojy"

    fun sanitizeFolderName(name: String?): String {
        if (name.isNullOrBlank()) return "default"
        val filtered = name.replace(Regex("[\\\\/:*?\"<>|]"), "_").trim()
        return if (filtered.isEmpty()) "default" else filtered
    }

    private fun mimeOf(resource: ResourceEntity): String {
        return when {
            "GIF".equals(resource.format, ignoreCase = true) -> "image/gif"
            "PNG".equals(resource.format, ignoreCase = true) -> "image/png"
            else -> "image/*"
        }
    }

    fun exportResources(
        context: Context,
        scope: CoroutineScope,
        resources: List<ResourceEntity>,
        subFolder: String? = null,
        onComplete: ((exportedCount: Int, isCancelled: Boolean) -> Unit)? = null
    ) {
        if (resources.isEmpty()) {
            Toast.makeText(context, "没有可导出的表情", Toast.LENGTH_SHORT).show()
            onComplete?.invoke(0, false)
            return
        }

        val total = resources.size
        val isCancelled = AtomicBoolean(false)
        val resourcesDir = File(context.filesDir, "resources")

        val relativePath = if (subFolder.isNullOrBlank()) {
            BASE_FOLDER
        } else {
            "$BASE_FOLDER/${sanitizeFolderName(subFolder)}"
        }

        val binding = DialogExportProgressBinding.inflate(LayoutInflater.from(context))
        binding.progressBarExport.max = total
        binding.progressBarExport.progress = 0
        binding.tvProgressMessage.text = "正在导出 0/$total"

        val dialog = AlertDialog.Builder(context)
            .setTitle("导出表情")
            .setView(binding.root)
            .setCancelable(false)
            .setNegativeButton("取消") { _, _ ->
                isCancelled.set(true)
            }
            .create()

        dialog.show()

        scope.launch {
            var successCount = 0

            withContext(Dispatchers.IO) {
                for ((index, res) in resources.withIndex()) {
                    if (isCancelled.get()) {
                        break
                    }

                    val sourceFile = File(resourcesDir, res.filename)
                    if (sourceFile.exists()) {
                        val ok = writeSingleToMediaStore(context, sourceFile, res, relativePath)
                        if (ok) {
                            successCount++
                        }
                    } else {
                        TestLog.e(MODULE, "源文件不存在: ${sourceFile.absolutePath}")
                    }

                    val currentDone = index + 1
                    withContext(Dispatchers.Main) {
                        binding.progressBarExport.progress = currentDone
                        binding.tvProgressMessage.text = "正在导出 $currentDone/$total"
                    }

                    if (isCancelled.get()) {
                        break
                    }
                }
            }

            dialog.dismiss()

            val cancelled = isCancelled.get()
            if (cancelled) {
                Toast.makeText(context, "已导出 $successCount 张（已取消）", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "已导出 $successCount 张表情到相册 $relativePath", Toast.LENGTH_LONG).show()
            }

            onComplete?.invoke(successCount, cancelled)
        }
    }

    private fun writeSingleToMediaStore(
        context: Context,
        sourceFile: File,
        resource: ResourceEntity,
        relativePath: String
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            writeMediaStoreQ(context, sourceFile, resource, relativePath)
        } else {
            writeMediaStoreLegacy(context, sourceFile, relativePath)
        }
    }

    private fun writeMediaStoreQ(
        context: Context,
        sourceFile: File,
        resource: ResourceEntity,
        relativePath: String
    ): Boolean {
        val resolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, resource.filename)
            put(MediaStore.Images.Media.MIME_TYPE, mimeOf(resource))
            put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val primaryUri = try {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } catch (e: Exception) {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        var insertedUri: Uri? = null
        return try {
            insertedUri = resolver.insert(primaryUri, values)
                ?: resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            if (insertedUri == null) {
                TestLog.e(MODULE, "insert MediaStore 返回 null")
                return false
            }

            resolver.openOutputStream(insertedUri)?.use { out ->
                sourceFile.inputStream().use { input ->
                    input.copyTo(out)
                }
                out.flush()
            }

            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(insertedUri, values, null, null)
            true
        } catch (e: Exception) {
            TestLog.e(MODULE, "写入 MediaStore 失败 (${resource.filename}): ${e.message}", e)
            if (insertedUri != null) {
                try {
                    resolver.delete(insertedUri, null, null)
                } catch (delEx: Exception) {
                    TestLog.e(MODULE, "清理未完成的 URI 失败: ${delEx.message}")
                }
            }
            false
        }
    }

    private fun writeMediaStoreLegacy(
        context: Context,
        sourceFile: File,
        relativePath: String
    ): Boolean {
        return try {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val subPath = relativePath.removePrefix("Pictures/").removePrefix("Pictures")
            val targetDir = File(picturesDir, subPath)
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }

            var targetFile = File(targetDir, sourceFile.name)
            if (targetFile.exists()) {
                val nameWithoutExt = sourceFile.nameWithoutExtension
                val ext = sourceFile.extension.let { if (it.isEmpty()) "" else ".$it" }
                var copyIdx = 1
                while (targetFile.exists()) {
                    targetFile = File(targetDir, "$nameWithoutExt($copyIdx)$ext")
                    copyIdx++
                }
            }

            FileOutputStream(targetFile).use { out ->
                sourceFile.inputStream().use { input ->
                    input.copyTo(out)
                }
                out.flush()
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(targetFile.absolutePath),
                null,
                null
            )
            true
        } catch (e: Exception) {
            TestLog.e(MODULE, "Legacy 写入相册失败: ${e.message}", e)
            false
        }
    }
}
