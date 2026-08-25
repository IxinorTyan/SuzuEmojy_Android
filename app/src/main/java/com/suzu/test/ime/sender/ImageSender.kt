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
    private data class ActiveGrant(val uri: Uri, val targetPkg: String?, val file: File, val createTime: Long)
    private val normalGrantTracker = ConcurrentHashMap<Uri, ActiveGrant>()

    fun executeH1b(item: ImageItem, targetPkg: String, icProvider: () -> InputConnection?, onSuccess: () -> Unit) {
        val actionName = "com.sogou.inputmethod.exp.commit"
        TestLog.i(MODULE, "========== [执行 H1β 直发] targetPkg=$targetPkg, file=${item.displayName} ==========")
        actionExecutor.execute {
            try {
                val (contentUri, _) = prepareValidatedUri(item, targetPkg)
                val bundle = Bundle().apply { putParcelable("EXP_PATH_URI", contentUri) }
                mainHandler.post {
                    val ic = icProvider() ?: return@post TestLog.e(MODULE, "[H1β] 失败: InputConnection 为 null")
                    try {
                        ic.beginBatchEdit()
                        val accepted = ic.performPrivateCommand(actionName, bundle)
                        ic.endBatchEdit()
                        TestLog.i(MODULE, "★ [H1β] performPrivateCommand(\"$actionName\") accepted=$accepted")
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
                val (contentUri, _) = prepareValidatedUri(item, targetPkg)
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val originalClip = try { clipboard?.primaryClip } catch (e: Exception) { null }
                clipboard?.setPrimaryClip(ClipData.newUri(context.contentResolver, "suzu_ime_image", contentUri))

                mainHandler.post {
                    val ic = icProvider() ?: return@post TestLog.e(MODULE, "发送失败: InputConnection 为 null")
                    val accepted = ic.performContextMenuAction(android.R.id.paste)
                    TestLog.i(MODULE, "performContextMenuAction(paste) accepted=$accepted")
                }
                onSuccess()
                mainHandler.postDelayed({ restoreClipboardIfUnchanged(clipboard, originalClip, contentUri) }, 1000)
            } catch (e: Exception) {
                TestLog.e(MODULE, "发送异常: ${e.message}", e)
            }
        }
    }

    private fun prepareValidatedUri(item: ImageItem, targetPkg: String): Pair<Uri, File> {
        val sendDir = File(context.cacheDir, "send").apply { if (!exists()) mkdirs() }
        val ext = if (item.mimeType == "image/gif") ".gif" else ".png"
        val safeName = if (item.displayName.contains(".")) item.displayName else "${item.displayName}$ext"
        val destFile = File(sendDir, "send_${System.currentTimeMillis()}_$safeName")

        when (item) {
            is ImageItem.AssetSample -> context.assets.open(item.assetFileName).use { input ->
                FileOutputStream(destFile).use { input.copyTo(it) }
            }
            is ImageItem.MediaStoreImage -> context.contentResolver.openInputStream(item.uri)?.use { input ->
                FileOutputStream(destFile).use { input.copyTo(it) }
            } ?: throw IllegalStateException("无法打开图库输入流: ${item.uri}")
            is ImageItem.SuzuResource -> item.file.inputStream().use { input ->
                FileOutputStream(destFile).use { input.copyTo(it) }
            }
        }

        val authority = BuildConfig.FILE_PROVIDER_AUTHORITY
        val contentUri = FileProvider.getUriForFile(context, authority, destFile)
        try {
            context.grantUriPermission(targetPkg, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            normalGrantTracker[contentUri] = ActiveGrant(contentUri, targetPkg, destFile, System.currentTimeMillis())
            scheduleNormalGrantCleanup(contentUri, 60000L)
        } catch (e: Exception) {
            TestLog.e(MODULE, "grantUriPermission 失败: ${e.message}", e)
        }
        return Pair(contentUri, destFile)
    }

    private fun scheduleNormalGrantCleanup(uri: Uri, delayMs: Long) {
        mainHandler.postDelayed({
            val grant = normalGrantTracker.remove(uri) ?: return@postDelayed
            try {
                grant.targetPkg?.let { context.revokeUriPermission(grant.uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) }
                if (grant.file.exists()) grant.file.delete()
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
