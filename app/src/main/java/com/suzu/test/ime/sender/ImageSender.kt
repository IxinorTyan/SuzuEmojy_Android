package com.suzu.test.ime.sender

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ClipDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.content.pm.PackageManager
import android.view.inputmethod.InputConnection
import androidx.core.content.FileProvider
import com.suzu.test.BuildConfig
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.ime.ImageItem
import com.suzu.test.ime.diag.DebugSendTestConfig
import com.suzu.test.ime.diag.ImageSendDiagnostics
import com.suzu.test.log.TestLog
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

class ImageSender(private val context: Context) {
    companion object {
        private const val MODULE = "ImageSender"
        private const val READ_FLAG = Intent.FLAG_GRANT_READ_URI_PERMISSION
        private const val FALLBACK_CHECK_DELAY_MS = 100L
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private val actionExecutor = Executors.newSingleThreadExecutor()
    private data class PreparedResult(
        val uri: Uri,
        val file: File,
        val mimeType: String,
        val diagnostic: ImageSendDiagnostics.Record,
        val deleteFileOnCleanup: Boolean
    )
    private data class ActiveGrant(
        val uri: Uri,
        val targetPkg: String?,
        val file: File,
        val createTime: Long,
        val eventId: String
    )
    private val normalGrantTracker = ConcurrentHashMap<Uri, ActiveGrant>()
    private val fallbackLaunchedEvents = ConcurrentHashMap.newKeySet<String>()

    /**
     * 白名单目标不走目标应用专用协议，直接交给系统分享选择器。
     */
    fun executeSystemChooser(
        item: ImageItem,
        targetPkg: String,
        editorInfoPackage: String? = targetPkg,
        onSuccess: () -> Unit
    ) {
        actionExecutor.execute {
            var prepared: PreparedResult? = null
            try {
                prepared = prepareValidatedUri(item, targetPkg, editorInfoPackage, "系统分享选择器")
                val result = prepared
                mainHandler.post {
                    launchImageShare(
                        prepared = result,
                        record = result.diagnostic,
                        targetPkg = targetPkg,
                        useSystemChooser = true,
                        onSuccess = onSuccess
                    )
                }
            } catch (e: Exception) {
                prepared?.diagnostic?.let {
                    it.commandException = exceptionText(e)
                    ImageSendDiagnostics.update(it)
                }
                TestLog.e(MODULE, "系统分享选择器准备异常: ${e.message}", e)
            }
        }
    }

    /**
     * Tim 部分版本对私有输入法命令会返回 accepted，但不会真正打开
     * “发送给好友”页面，因此直接使用包定向的标准图片分享 Intent。
     */
    fun executeDirectShare(
        item: ImageItem,
        targetPkg: String,
        editorInfoPackage: String? = targetPkg,
        onSuccess: () -> Unit
    ) {
        actionExecutor.execute {
            var prepared: PreparedResult? = null
            try {
                prepared = prepareValidatedUri(item, targetPkg, editorInfoPackage, "标准图片分享")
                val result = prepared
                mainHandler.post {
                    launchImageShare(
                        prepared = result,
                        record = result.diagnostic,
                        targetPkg = targetPkg,
                        onSuccess = onSuccess
                    )
                }
            } catch (e: Exception) {
                prepared?.diagnostic?.let {
                    it.commandException = exceptionText(e)
                    ImageSendDiagnostics.update(it)
                }
                TestLog.e(MODULE, "标准图片分享准备异常: ${e.message}", e)
            }
        }
    }

    fun executeH1b(
        item: ImageItem,
        targetPkg: String,
        editorInfoPackage: String? = targetPkg,
        icProvider: () -> InputConnection?,
        onSuccess: () -> Unit
    ) {
        val actionName = "com.sogou.inputmethod.exp.commit"
        actionExecutor.execute {
            var result: PreparedResult? = null
            try {
                result = prepareValidatedUri(item, targetPkg, editorInfoPackage, "H1β")
                val prepared = result
                val record = prepared.diagnostic
                record.branch = "H1β 直发"
                record.commandName = actionName
                record.bundleUriMatches = true
                ImageSendDiagnostics.update(record)
                mainHandler.post {
                    val ic = icProvider()
                    record.inputConnection = if (ic == null) "NULL" else "正常"
                    if (ic == null) {
                        record.commandException = "InputConnection 为 null"
                        ImageSendDiagnostics.update(record)
                        TestLog.e(MODULE, "[${record.eventId}] [H1β] 失败: InputConnection 为 null，转入通道B")
                        launchImageShare(
                            prepared = prepared,
                            record = record,
                            targetPkg = targetPkg,
                            onSuccess = onSuccess
                        )
                        return@post
                    }
                    try {
                        ic.beginBatchEdit()
                        val commandBundle = Bundle().apply {
                            putParcelable("EXP_PATH_URI", prepared.uri)
                        }
                        val bundleUri = commandBundle.getParcelable<Uri>("EXP_PATH_URI")
                        record.bundleUriMatches = bundleUri == prepared.uri
                        val commandAccepted = ic.performPrivateCommand(actionName, commandBundle)
                        val accepted = if (
                            BuildConfig.DEBUG &&
                            DebugSendTestConfig.isSkipProviderDiagnosticAndCleanupEnabled(context)
                        ) {
                            TestLog.i(
                                MODULE,
                                "[$record.eventId] DEBUG测试开关已开启，模拟 H1β 失败并转入通道B"
                            )
                            false
                        } else {
                            commandAccepted
                        }
                        ic.endBatchEdit()
                        record.commandResult = accepted
                        ImageSendDiagnostics.update(record)
                        TestLog.i(MODULE, "[$record.eventId] performPrivateCommand accepted=$accepted")
                        if (accepted) {
                            onSuccess()
                        } else {
                            launchImageShare(
                                prepared = prepared,
                                record = record,
                                targetPkg = targetPkg,
                                onSuccess = onSuccess
                            )
                        }
                    } catch (e: Exception) {
                        record.commandException = exceptionText(e)
                        ImageSendDiagnostics.update(record)
                        TestLog.e(
                            MODULE,
                            "[${record.eventId}] [H1β] 异常: ${e.message}，转入通道B",
                            e
                        )
                        launchImageShare(
                            prepared = prepared,
                            record = record,
                            targetPkg = targetPkg,
                            onSuccess = onSuccess
                        )
                    }
                    schedulePostCommandChecks(record)
                }
            } catch (e: Exception) {
                result?.diagnostic?.let {
                    it.commandException = exceptionText(e)
                    ImageSendDiagnostics.update(it)
                }
                TestLog.e(MODULE, "[H1β] 准备异常: ${e.message}", e)
            }
        }
    }

    fun executeE(
        item: ImageItem,
        targetPkg: String,
        editorInfoPackage: String? = targetPkg,
        icProvider: () -> InputConnection?,
        onSuccess: () -> Unit
    ) {
        actionExecutor.execute {
            var prepared: PreparedResult? = null
            try {
                prepared = prepareValidatedUri(item, targetPkg, editorInfoPackage, "剪贴板 E")
                val result = prepared
                val record = result.diagnostic
                record.branch = "剪贴板注入与标准粘贴"
                record.commandName = "performContextMenuAction(android.R.id.paste)"
                record.bundleUriMatches = true
                ImageSendDiagnostics.update(record)

                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val originalClip = try { clipboard?.primaryClip } catch (_: Exception) { null }
                val clipData = ClipData(
                    ClipDescription("suzu_ime_image", arrayOf(result.mimeType)),
                    ClipData.Item(result.uri)
                )
                try {
                    clipboard?.setPrimaryClip(clipData)
                } catch (e: Exception) {
                    record.clipboardSetException = exceptionText(e)
                }

                mainHandler.post {
                    val ic = icProvider()
                    record.inputConnection = if (ic == null) "NULL" else "正常"
                    if (ic == null) {
                        record.pasteException = "InputConnection 为 null"
                        ImageSendDiagnostics.update(record)
                    } else {
                        try {
                            record.pasteResult = ic.performContextMenuAction(android.R.id.paste)
                        } catch (e: Exception) {
                            record.pasteException = exceptionText(e)
                        }
                        ImageSendDiagnostics.update(record)
                    }
                    schedulePostCommandChecks(record)
                }
                // 保持原有行为：不论粘贴返回值如何，仍无条件回调成功。
                onSuccess()
                mainHandler.postDelayed({ restoreClipboardIfUnchanged(clipboard, originalClip, result.uri) }, 1000)
            } catch (e: Exception) {
                prepared?.diagnostic?.let {
                    it.pasteException = exceptionText(e)
                    ImageSendDiagnostics.update(it)
                }
                TestLog.e(MODULE, "发送异常: ${e.message}", e)
            }
        }
    }

    private fun launchImageShare(
        prepared: PreparedResult,
        record: ImageSendDiagnostics.Record,
        targetPkg: String,
        useSystemChooser: Boolean = false,
        onSuccess: () -> Unit
    ) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = prepared.mimeType
            putExtra(Intent.EXTRA_STREAM, prepared.uri)
            clipData = ClipData.newRawUri("suzu_image", prepared.uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            val packageManager = context.packageManager
            val preferredComponent = preferredShareComponent(targetPkg)
            val preferredIntent = preferredComponent?.let { component ->
                Intent(shareIntent).apply { setComponent(component) }
            }
            val targetedIntent = when {
                preferredIntent != null &&
                    preferredIntent.resolveActivity(packageManager) != null -> preferredIntent
                else -> Intent(shareIntent).apply { setPackage(targetPkg) }
            }
            val baseIntent = if (useSystemChooser) shareIntent else targetedIntent
            val resolved = baseIntent.resolveActivity(packageManager)
                ?: throw IllegalStateException("没有可处理图片分享的 Activity: $targetPkg")
            val launchIntent = if (useSystemChooser) {
                Intent.createChooser(baseIntent, "选择分享应用").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            } else {
                baseIntent
            }

            if (!useSystemChooser) {
                TestAccessibilityService.instance?.armShareCardAutomation(targetPkg)
            }
            context.startActivity(launchIntent)
            val entry = resolved.className
            record.branch = if (useSystemChooser) {
                "系统分享选择器"
            } else {
                "H1β 直发 -> 通道B 发送给朋友/好友"
            }
            record.commandName = if (useSystemChooser) {
                "Intent.createChooser(ACTION_SEND)"
            } else {
                "Intent.ACTION_SEND(component=$entry)"
            }
            ImageSendDiagnostics.update(record)
            TestLog.i(
                MODULE,
                if (useSystemChooser) {
                    "[${record.eventId}] 已拉起系统分享选择器"
                } else {
                    "[$record.eventId] H1β 降级：已拉起目标应用好友分享入口 " +
                        "($targetPkg/$entry)"
                }
            )
            onSuccess()
        } catch (e: Exception) {
            record.branch = "H1β 直发 -> 通道B 发送给朋友/好友（失败）"
            record.commandName = "Intent.ACTION_SEND(targetPackage=$targetPkg)"
            record.commandException = exceptionText(e)
            ImageSendDiagnostics.update(record)
            TestLog.e(MODULE, "[$record.eventId] 拉起目标应用好友分享入口失败: ${e.message}", e)
        }
    }

    /**
     * 目标应用的分享卡片入口不是 Android 公开 API，必须保留包定向 ACTION_SEND
     * 作为兼容性兜底。显式入口可使常见版本直接进入“发送给朋友/好友”。
     */
    private fun preferredShareComponent(targetPkg: String): ComponentName? =
        when (targetPkg) {
            "com.tencent.mm" ->
                ComponentName(targetPkg, "com.tencent.mm.ui.tools.ShareImgUI")
            "com.tencent.mobileqq" ->
                ComponentName(targetPkg, "com.tencent.mobileqq.activity.JumpActivity")
            // Tim 的 JumpActivity 只是通用跳板，显式启动会停留在错误卡片。
            // 让 Tim 自己按 ACTION_SEND 解析到正确的图片分享流程，
            // 再由无障碍服务点击“发送给好友”。
            "com.tencent.tim" -> null
            "com.tencent.qqlite" ->
                ComponentName(targetPkg, "com.tencent.qqlite.activity.JumpActivity")
            else -> null
        }

    private fun prepareValidatedUri(
        item: ImageItem,
        targetPkg: String,
        editorInfoPackage: String?,
        source: String
    ): PreparedResult {
        val sendDir = File(context.cacheDir, "send").apply { if (!exists()) mkdirs() }
        val placeholder = File(sendDir, "pending_${System.currentTimeMillis()}.tmp")
        val record = ImageSendDiagnostics.newRecord(editorInfoPackage, targetPkg, item, source, placeholder)
        record.inputConnection = "未检查"
        ImageSendDiagnostics.add(record)

        val sp = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val convertEnabled = sp.getBoolean("convert_png_to_gif_on_send", true)
        val isOriginalGif = item.mimeType == "image/gif" ||
            (item is ImageItem.SuzuResource && item.format.equals("gif", ignoreCase = true))
        var actualFile: File? = null

        if (convertEnabled && !isOriginalGif) {
            val deterministicKey = when (item) {
                is ImageItem.SuzuResource -> item.file.name.substringBeforeLast(".")
                is ImageItem.AssetSample -> "asset_${item.assetFileName.substringBeforeLast(".")}"
                is ImageItem.MediaStoreImage -> "media_${item.id}_${item.dateModified}"
            }
            val cachedGifFile = File(sendDir, "png2gif_${deterministicKey}.gif")
            if (PngToGifConverter.isValidGif(cachedGifFile)) {
                actualFile = cachedGifFile
            } else {
                if (cachedGifFile.exists()) cachedGifFile.delete()
                val inputStreamProvider: () -> java.io.InputStream? = {
                    when (item) {
                        is ImageItem.AssetSample -> context.assets.open(item.assetFileName)
                        is ImageItem.MediaStoreImage -> context.contentResolver.openInputStream(item.uri)
                        is ImageItem.SuzuResource -> item.file.inputStream()
                    }
                }
                if (PngToGifConverter.convertPngToGif(inputStreamProvider, cachedGifFile) &&
                    PngToGifConverter.isValidGif(cachedGifFile)
                ) actualFile = cachedGifFile
            }
        }

        val finalFile: File
        val isCachedGif: Boolean
        val deleteFileOnCleanup: Boolean
        if (actualFile != null) {
            finalFile = actualFile
            isCachedGif = true
            // png2gif_* 位于 cache/send，是可清理的发送中间文件；
            // 资源库源文件只在下面的 SuzuResource 分支中标记为不可删除。
            deleteFileOnCleanup = true
        } else if (item is ImageItem.SuzuResource) {
            // 资源库文件已经位于 filesDir/resources/，直接通过 FileProvider 授权读取，
            // 不再复制到 cache/send；源文件严禁交给发送清理逻辑删除。
            finalFile = item.file
            isCachedGif = false
            deleteFileOnCleanup = false
        } else {
            isCachedGif = false
            deleteFileOnCleanup = true
            val ext = if (isOriginalGif) ".gif" else ".png"
            val safeName = if (item.displayName.contains(".")) item.displayName else "${item.displayName}$ext"
            finalFile = File(sendDir, "send_${System.currentTimeMillis()}_$safeName")
            when (item) {
                is ImageItem.AssetSample -> context.assets.open(item.assetFileName).use { input ->
                    FileOutputStream(finalFile).use { input.copyTo(it) }
                }
                is ImageItem.MediaStoreImage -> context.contentResolver.openInputStream(item.uri)?.use { input ->
                    FileOutputStream(finalFile).use { output -> input.copyTo(output) }
                } ?: throw IllegalStateException("无法打开图库输入流: ${item.uri}")
                is ImageItem.SuzuResource -> error("SuzuResource 不应进入发送副本分支")
            }
        }

        record.filePath = finalFile.absolutePath
        record.fileExists = finalFile.exists()
        record.fileLength = finalFile.length()
        record.fileExtension = finalFile.extension
        record.detectedMime = if (isCachedGif || isOriginalGif) "image/gif" else "image/png"

        val uri = FileProvider.getUriForFile(context, BuildConfig.FILE_PROVIDER_AUTHORITY, finalFile)
        record.uri = uri.toString()
        record.authority = uri.authority.orEmpty()
        ImageSendDiagnostics.associate(uri, record.eventId)

        try {
            context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                record.selfStatSize = pfd.statSize
                record.selfOpen = "SUCCESS"
            }
            record.providerMimeType = context.contentResolver.getType(uri)
        } catch (e: Exception) {
            record.selfOpen = "FAILED"
            record.selfExceptionType = e::class.java.name
            record.selfExceptionMessage = e.message
            record.selfStack = android.util.Log.getStackTraceString(e)
            TestLog.e(MODULE, "[$record.eventId] SELF_URI_READ_FAILED", e)
        }
        queryUidAndGrant(record, uri, targetPkg, finalFile, deleteFileOnCleanup)
        return PreparedResult(uri, finalFile, record.detectedMime, record, deleteFileOnCleanup)
    }

    private fun queryUidAndGrant(
        record: ImageSendDiagnostics.Record,
        uri: Uri,
        targetPkg: String,
        file: File,
        deleteFileOnCleanup: Boolean
    ) {
        try {
            record.targetUid = if (android.os.Build.VERSION.SDK_INT >= 33) {
                context.packageManager.getPackageUid(targetPkg, android.content.pm.PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION") context.packageManager.getPackageUid(targetPkg, 0)
            }
            record.uidResult = "SUCCESS"
        } catch (e: Exception) {
            record.uidResult = "FAILED"
            record.uidExceptionType = e::class.java.name
            record.uidExceptionMessage = e.message
            record.uidExceptionStack = android.util.Log.getStackTraceString(e)
        }
        record.permissionBefore = checkPermission(uri, record.targetUid)
        try {
            context.grantUriPermission(targetPkg, uri, READ_FLAG)
            record.grantCall = "SUCCESS"
            normalGrantTracker[uri] = ActiveGrant(uri, targetPkg, file, System.currentTimeMillis(), record.eventId)
            scheduleNormalGrantCleanup(uri, 60000L, deleteFileOnCleanup)
        } catch (e: Exception) {
            record.grantCall = "FAILED"
            record.grantException = exceptionText(e)
        }
        record.permissionAfter = checkPermission(uri, record.targetUid)
        ImageSendDiagnostics.update(record)
    }

    private fun checkPermission(uri: Uri, uid: Int?): String {
        if (uid == null) return "UNKNOWN"
        return when (context.checkUriPermission(uri, -1, uid, READ_FLAG)) {
            PackageManager.PERMISSION_GRANTED -> "GRANTED"
            PackageManager.PERMISSION_DENIED -> "DENIED"
            else -> "UNKNOWN"
        }
    }

    private fun schedulePostCommandChecks(record: ImageSendDiagnostics.Record) {
        // H1β 返回 true 也不代表目标应用已经真正读取了 URI。100ms 内未看到
        // Provider openFile 且未发生清理时，立即转入通道 B，避免等待数秒。
        mainHandler.postDelayed({ checkFallbackImmediately(record) }, FALLBACK_CHECK_DELAY_MS)

        // 保留较晚的诊断采样，供日志排查使用；5 秒检查也作为兜底。
        mainHandler.postDelayed({ updateTimedCheck(record, false) }, 1000L)
        mainHandler.postDelayed({ updateTimedCheck(record, true) }, 5000L)
    }

    private fun checkFallbackImmediately(record: ImageSendDiagnostics.Record) {
        if (!shouldFallback(record) || !fallbackLaunchedEvents.add(record.eventId)) return

        TestLog.w(
            MODULE,
            "[${record.eventId}] ${FALLBACK_CHECK_DELAY_MS}ms 内未检测到 Provider openFile 且未发生清理，转入通道B"
        )
        val file = File(record.filePath)
        launchImageShare(
            prepared = PreparedResult(
                uri = Uri.parse(record.uri),
                file = file,
                mimeType = record.detectedMime.ifBlank { "image/png" },
                diagnostic = record,
                deleteFileOnCleanup = false
            ),
            record = record,
            targetPkg = record.targetPackage.orEmpty()
        ) {
            // H1β 已执行过成功回调，这里不重复更新使用次数。
        }
    }

    private fun shouldFallback(record: ImageSendDiagnostics.Record): Boolean =
        record.commandResult == true &&
            record.providerOpen == "未发生" &&
            record.revokeOrDelete.isBlank()

    private fun updateTimedCheck(record: ImageSendDiagnostics.Record, fiveSeconds: Boolean) {
        val state = checkPermission(Uri.parse(record.uri), record.targetUid)
        val file = File(record.filePath)
        if (fiveSeconds) {
            record.permissionAfter5s = state
            record.fileExistsAfter5s = file.exists()
            record.fileLengthAfter5s = file.length()
        } else {
            record.permissionAfter1s = state
            record.fileExistsAfter1s = file.exists()
            record.fileLengthAfter1s = file.length()
        }
        ImageSendDiagnostics.update(record)

        if (fiveSeconds && shouldFallback(record) && fallbackLaunchedEvents.add(record.eventId)) {
            TestLog.w(
                MODULE,
                "[${record.eventId}] 5秒后仍未检测到 Provider openFile，自动转入通道B"
            )
            val prepared = PreparedResult(
                uri = Uri.parse(record.uri),
                file = file,
                mimeType = record.detectedMime.ifBlank { "image/png" },
                diagnostic = record,
                deleteFileOnCleanup = false
            )
            launchImageShare(
                prepared = prepared,
                record = record,
                targetPkg = record.targetPackage.orEmpty()
            ) {
                // H1β 已执行过成功回调，这里不重复更新使用次数。
            }
        }
    }

    private fun scheduleNormalGrantCleanup(uri: Uri, delayMs: Long, deleteFileOnCleanup: Boolean) {
        if (BuildConfig.DEBUG &&
            DebugSendTestConfig.isSkipProviderDiagnosticAndCleanupEnabled(context)
        ) {
            TestLog.i(MODULE, "[$uri] DEBUG测试开关已开启，跳过撤销授权及临时文件清理")
            return
        }
        mainHandler.postDelayed({
            val grant = normalGrantTracker.remove(uri) ?: return@postDelayed
            val beforeExists = grant.file.exists()
            val beforeLength = grant.file.length()
            try {
                grant.targetPkg?.let { context.revokeUriPermission(grant.uri, READ_FLAG) }
                ImageSendDiagnostics.recordCleanup(
                    eventId = grant.eventId,
                    uri = grant.uri,
                    file = grant.file,
                    reason = "60秒定时清理：revokeUriPermission",
                    beforeExists = beforeExists,
                    beforeLength = beforeLength,
                    result = "REVOKE_SUCCESS"
                )
                if (deleteFileOnCleanup && grant.file.exists()) {
                    val deleteBeforeExists = grant.file.exists()
                    val deleteBeforeLength = grant.file.length()
                    val deleted = grant.file.delete()
                    ImageSendDiagnostics.recordCleanup(
                        eventId = grant.eventId,
                        uri = grant.uri,
                        file = grant.file,
                        reason = "60秒定时清理：临时文件 delete()",
                        beforeExists = deleteBeforeExists,
                        beforeLength = deleteBeforeLength,
                        result = if (deleted) "DELETE_SUCCESS" else "DELETE_FAILED"
                    )
                }
            } catch (e: Exception) {
                val record = grantForUri(grant.uri)
                if (record != null) {
                    record.revokeOrDelete =
                        "${record.revokeOrDelete}\n" +
                            "eventId=${grant.eventId}, uri=${grant.uri}, filePath=${grant.file.absolutePath}, " +
                            "time=${System.currentTimeMillis()}, reason=60秒定时清理异常, " +
                            "exception=${exceptionText(e)}"
                    ImageSendDiagnostics.update(record)
                }
            }
        }, delayMs)
    }

    private fun grantForUri(uri: Uri): ImageSendDiagnostics.Record? =
        ImageSendDiagnostics.recordForUri(uri)

    private fun restoreClipboardIfUnchanged(clipboard: ClipboardManager?, originalClip: ClipData?, contentUri: Uri) {
        try {
            val currentClip = clipboard?.primaryClip
            val currentUri = currentClip?.let { if (it.itemCount > 0) it.getItemAt(0).uri else null }
            if (currentUri == contentUri && originalClip != null) clipboard.setPrimaryClip(originalClip)
        } catch (e: Exception) {
            TestLog.w(MODULE, "恢复剪贴板过程异常: ${e.message}")
        }
    }

    private fun exceptionText(e: Exception): String =
        "${e::class.java.name}: ${e.message}\n${android.util.Log.getStackTraceString(e)}"

    fun destroy() {
        fallbackLaunchedEvents.clear()
        actionExecutor.shutdown()
    }
}
