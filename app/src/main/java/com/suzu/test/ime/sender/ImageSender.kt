package com.suzu.test.ime.sender

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputConnection
import androidx.core.content.FileProvider
import com.suzu.test.BuildConfig
import com.suzu.test.ime.ImageItem
import com.suzu.test.log.TestLog
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

class ImageSender(private val context: Context) {

    companion object {
        private const val MODULE = "ImageSender"
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private val actionExecutor = Executors.newSingleThreadExecutor()
    private data class PreparedResult(val uri: Uri, val file: File, val mimeType: String)
    private data class ActiveGrant(val uri: Uri, val targetPkg: String?, val file: File, val createTime: Long)
    private val normalGrantTracker = ConcurrentHashMap<Uri, ActiveGrant>()

    fun executeH1b(item: ImageItem, targetPkg: String, icProvider: () -> InputConnection?, onSuccess: () -> Unit) {
        val actionName = "com.sogou.inputmethod.exp.commit"
        TestLog.i(MODULE, "========== [执行 H1β 直发] targetPkg=$targetPkg, file=${item.displayName} ==========")
        actionExecutor.execute {
            try {
                val result = prepareValidatedUri(item, targetPkg)
                val bundle = Bundle().apply { putParcelable("EXP_PATH_URI", result.uri) }
                mainHandler.post {
                    val ic = icProvider() ?: return@post TestLog.e(MODULE, "[H1β] 失败: InputConnection 为 null")
                    try {
                        ic.beginBatchEdit()
                        val accepted = ic.performPrivateCommand(actionName, bundle)
                        ic.endBatchEdit()
                        TestLog.i(MODULE, "★ [H1β] performPrivateCommand(\"$actionName\") accepted=$accepted, mimeType=${result.mimeType}")
                        if (accepted) onSuccess()
                    } catch (e: Exception) {
                        TestLog.e(MODULE, "[H1β] 异常: ${e.message}", e)
                    }
                }
            } catch (e: Exception) {
                TestLog.e(MODULE, "[H1β] 准备异常: ${e.message}", e)
            }
        }
    }

    fun executeE(item: ImageItem, targetPkg: String, icProvider: () -> InputConnection?, onSuccess: () -> Unit) {
        TestLog.i(MODULE, "========== [执行剪贴板注入与粘贴 (E)] targetPkg=$targetPkg ==========")
        actionExecutor.execute {
            try {
                val result = prepareValidatedUri(item, targetPkg)
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val originalClip = try { clipboard?.primaryClip } catch (e: Exception) { null }

                val clipDescription = android.content.ClipDescription(
                    "suzu_ime_image",
                    arrayOf(result.mimeType)
                )
                val clipData = ClipData(
                    clipDescription,
                    ClipData.Item(result.uri)
                )
                clipboard?.setPrimaryClip(clipData)

                mainHandler.post {
                    val ic = icProvider() ?: return@post TestLog.e(MODULE, "发送失败: InputConnection 为 null")
                    val accepted = ic.performContextMenuAction(android.R.id.paste)
                    TestLog.i(MODULE, "performContextMenuAction(paste) accepted=$accepted, mimeType=${result.mimeType}")
                }
                onSuccess()
                mainHandler.postDelayed({ restoreClipboardIfUnchanged(clipboard, originalClip, result.uri) }, 1000)
            } catch (e: Exception) {
                TestLog.e(MODULE, "发送异常: ${e.message}", e)
            }
        }
    }

    private fun prepareValidatedUri(item: ImageItem, targetPkg: String): PreparedResult {
        val sendDir = File(context.cacheDir, "send").apply { if (!exists()) mkdirs() }
        val sp = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val convertEnabled = sp.getBoolean("convert_png_to_gif_on_send", true)

        val isOriginalGif = item.mimeType == "image/gif" ||
                (item is ImageItem.SuzuResource && item.format.equals("gif", ignoreCase = true))

        var actualFile: File? = null

        if (convertEnabled && !isOriginalGif) {
            // PNG 转单帧 GIF 转换分支
            val deterministicKey = when (item) {
                is ImageItem.SuzuResource -> item.file.name.substringBeforeLast(".")
                is ImageItem.AssetSample -> "asset_${item.assetFileName.substringBeforeLast(".")}"
                is ImageItem.MediaStoreImage -> "media_${item.id}_${item.dateModified}"
            }
            val cachedGifFile = File(sendDir, "png2gif_${deterministicKey}.gif")

            // 1. 缓存命中校验：非空且头部为 GIF8
            if (PngToGifConverter.isValidGif(cachedGifFile)) {
                TestLog.i(MODULE, "命中确定性 GIF 缓存，直接复用: ${cachedGifFile.name}")
                actualFile = cachedGifFile
            } else {
                if (cachedGifFile.exists()) {
                    TestLog.w(MODULE, "确定性缓存文件损坏/不完整，删除重编: ${cachedGifFile.name}")
                    cachedGifFile.delete()
                }

                val inputStreamProvider: () -> java.io.InputStream? = {
                    when (item) {
                        is ImageItem.AssetSample -> context.assets.open(item.assetFileName)
                        is ImageItem.MediaStoreImage -> context.contentResolver.openInputStream(item.uri)
                        is ImageItem.SuzuResource -> item.file.inputStream()
                    }
                }

                val converted = PngToGifConverter.convertPngToGif(inputStreamProvider, cachedGifFile)
                if (converted && PngToGifConverter.isValidGif(cachedGifFile)) {
                    actualFile = cachedGifFile
                } else {
                    TestLog.w(MODULE, "PNG 转单帧 GIF 失败或降级，走原 PNG 复制发送")
                }
            }
        }

        val finalFile: File
        val isCachedGif: Boolean

        if (actualFile != null) {
            finalFile = actualFile
            isCachedGif = true
        } else {
            // 原始链路：无转换或降级复制
            isCachedGif = false
            val ext = if (isOriginalGif) ".gif" else ".png"
            val safeName = if (item.displayName.contains(".")) item.displayName else "${item.displayName}$ext"
            finalFile = File(sendDir, "send_${System.currentTimeMillis()}_$safeName")

            when (item) {
                is ImageItem.AssetSample -> context.assets.open(item.assetFileName).use { input ->
                    FileOutputStream(finalFile).use { input.copyTo(it) }
                }
                is ImageItem.MediaStoreImage -> context.contentResolver.openInputStream(item.uri)?.use { input ->
                    FileOutputStream(finalFile).use { input.copyTo(it) }
                } ?: throw IllegalStateException("无法打开图库输入流: ${item.uri}")
                is ImageItem.SuzuResource -> item.file.inputStream().use { input ->
                    FileOutputStream(finalFile).use { input.copyTo(it) }
                }
            }
        }

        val authority = BuildConfig.FILE_PROVIDER_AUTHORITY
        val contentUri = FileProvider.getUriForFile(context, authority, finalFile)
        val finalMimeType = if (isCachedGif || isOriginalGif) "image/gif" else "image/png"

        try {
            context.grantUriPermission(targetPkg, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            normalGrantTracker[contentUri] = ActiveGrant(contentUri, targetPkg, finalFile, System.currentTimeMillis())
            // 确定性命名的复用文件仅撤销权限，不删除文件；非复用临时文件才延迟删除
            scheduleNormalGrantCleanup(contentUri, 60000L, deleteFileOnCleanup = !isCachedGif)
        } catch (e: Exception) {
            TestLog.e(MODULE, "grantUriPermission 失败: ${e.message}", e)
        }
        return PreparedResult(contentUri, finalFile, finalMimeType)
    }

    private fun scheduleNormalGrantCleanup(uri: Uri, delayMs: Long, deleteFileOnCleanup: Boolean = true) {
        mainHandler.postDelayed({
            val grant = normalGrantTracker.remove(uri) ?: return@postDelayed
            try {
                grant.targetPkg?.let { context.revokeUriPermission(grant.uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) }
                if (deleteFileOnCleanup && grant.file.exists()) {
                    grant.file.delete()
                }
            } catch (ignored: Exception) {}
        }, delayMs)
    }

    private fun restoreClipboardIfUnchanged(clipboard: ClipboardManager?, originalClip: ClipData?, contentUri: Uri) {
        try {
            val currentClip = clipboard?.primaryClip
            val currentUri = currentClip?.let { if (it.itemCount > 0) it.getItemAt(0).uri else null }
            if (currentUri == contentUri && originalClip != null) {
                clipboard.setPrimaryClip(originalClip)
                TestLog.i(MODULE, "已成功恢复原剪贴板")
            }
        } catch (e: Exception) {
            TestLog.w(MODULE, "恢复剪贴板过程异常: ${e.message}")
        }
    }

    fun destroy() {
        actionExecutor.shutdown()
    }
}
