package com.suzu.test.ui.lansync

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import com.suzu.test.resource.importpkg.PackageImportStage
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import com.suzu.test.resource.lansync.LanPairing
import com.suzu.test.resource.lansync.LanSyncClient
import com.suzu.test.resource.lansync.LanTransfer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.io.IOException

internal enum class LanSyncPhase { ENTRY, BUSY, VERIFY, CONNECTED, OFFER }
internal data class LanSyncState(
    val phase: LanSyncPhase, val status: String = "", val verification: String? = null,
    val incoming: LanTransfer? = null, val progress: Int? = null
)

class LanSyncViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private val mutable = MutableStateFlow(LanSyncState(LanSyncPhase.ENTRY))
    internal val state = mutable.asStateFlow()
    private var client: LanSyncClient? = null
    private var operation: Job? = null
    private var pairingExpiry: Job? = null
    private var queued: LanTransfer? = null
    private var permission: CompletableDeferred<Boolean>? = null

    internal fun manual(code: String, address: String) = startPair { LanPairing.manual(code, address) }
    internal fun scan(contents: String) = startPair { LanPairing.qr(contents) }

    private fun startPair(makePairing: () -> LanPairing) {
        if (mutable.value.phase != LanSyncPhase.ENTRY) return
        val pairing = try { makePairing() }
        catch (e: IllegalArgumentException) {
            mutable.value = LanSyncState(LanSyncPhase.ENTRY, e.message.orEmpty()); return
        }
        val connection = LanSyncClient().also { client = it }
        run(connection, if (pairing.addresses.isEmpty()) "正在查找电脑…" else "正在连接电脑…") {
            val verification = withContext(Dispatchers.IO) { connection.pair(pairing) }
            mutable.value = LanSyncState(LanSyncPhase.VERIFY, "请核对电脑上的校验码，并在两端确认。", verification)
            pairingExpiry = viewModelScope.launch {
                delay(59_000)
                if (client === connection && mutable.value.phase == LanSyncPhase.VERIFY) disconnect("连接已超时，请重新配对。")
            }
        }
    }

    internal fun confirm() {
        val connection = client?.takeIf { mutable.value.phase == LanSyncPhase.VERIFY } ?: return
        pairingExpiry?.cancel()
        run(connection, "等待电脑确认…", preserveCode = true) {
            withContext(Dispatchers.IO) { connection.confirm() }
            connected(connection)
        }
    }

    internal fun request(transfer: LanTransfer) {
        if (mutable.value.phase != LanSyncPhase.CONNECTED || queued != null) return
        queued = transfer
        report("等待电脑确认传输…")
    }

    internal fun answer(allow: Boolean) { permission?.complete(allow) }

    private suspend fun connected(connection: LanSyncClient) {
        mutable.value = LanSyncState(LanSyncPhase.CONNECTED, "已连接")
        var idleSince = System.nanoTime()
        while (true) {
            val requested = queued.also { queued = null }
            val offer = withContext(Dispatchers.IO) { connection.poll(requested) }
            if (offer == null) {
                if (System.nanoTime() - idleSince > 590_000_000_000L) throw IOException("连接已超时，请重新配对。")
                delay(400)
                continue
            }
            // The computer arbitrates simultaneous requests. Any displaced phone request needs a new tap.
            queued = null
            val verdict = if (offer.fromComputer) {
                val response = CompletableDeferred<Boolean>()
                permission = response
                mutable.value = LanSyncState(LanSyncPhase.OFFER, when (offer.transfer) {
                    LanTransfer.DOWNLOAD -> "电脑请求发送资源包到手机。"
                    LanTransfer.UPLOAD -> "电脑请求接收手机的资源包。"
                }, incoming = offer.transfer)
                val allowed = try { withTimeoutOrNull(55_000) { response.await() } == true }
                finally { permission = null }
                withContext(Dispatchers.IO) { connection.writeStatus(if (allowed) 1 else 0) }
                allowed
            } else {
                report("等待电脑确认传输…")
                withContext(Dispatchers.IO) { connection.readDecision() }
            }
            val message = if (verdict) transfer(connection, offer.transfer)
                else if (offer.fromComputer) "已拒绝传输。" else "电脑未同意传输。"
            mutable.value = LanSyncState(LanSyncPhase.CONNECTED, message)
            idleSince = System.nanoTime()
        }
    }

    private suspend fun upload(connection: LanSyncClient, file: File): String {
        report("正在导出资源包…")
        val db = DatabaseProvider.getDatabase(app)
        val exported = ResourcePackageExportService(app, db).exportToZip(Uri.fromFile(file), "SuzuEmojy") { _, count, total ->
            report("正在导出：$count / $total")
        }
        val receipt = connection.upload(file) { pct -> report("正在发送：$pct%", pct) }
        val skipped = if (exported.skippedCount > 0) "；手机导出时跳过 ${exported.skippedCount} 项" else ""
        return (when (receipt) {
            1 -> "电脑已导入"
            2 -> "电脑已导入，部分项目失败"
            else -> "电脑导入失败"
        }) + skipped
    }

    private suspend fun download(connection: LanSyncClient, file: File): String {
        report("正在等待电脑导出…")
        connection.download(file) { pct -> report("正在接收：$pct%", pct) }
        val summary = try {
            report("正在校验并导入…")
            val db = DatabaseProvider.getDatabase(app)
            ResourcePackageImportService(app, db, ResourceImportService(app, db)).importReceivedZip(file) { stage, count, total ->
                if (stage == PackageImportStage.IMPORTING) report("正在导入：$count / $total")
            }.summary
        } catch (e: Exception) {
            if (e !is CancellationException) runCatching { connection.writeStatus(0) }
            throw e
        }
        val warnings = if (summary.warnings.isEmpty()) "" else "；${summary.warnings.size} 个图标未恢复"
        val message = "手机导入完成：新增 ${summary.successCount}，重复 ${summary.duplicateCount}，失败 ${summary.failCount}$warnings"
        try { connection.writeStatus(if (summary.failCount > 0 || summary.warnings.isNotEmpty()) 2 else 1) }
        catch (_: java.io.IOException) { throw java.io.IOException("$message；未能向电脑回报结果。") }
        return message
    }

    private suspend fun transfer(connection: LanSyncClient, direction: LanTransfer): String = withContext(Dispatchers.IO) {
        val file = File.createTempFile("suzu-lan-", ".zip", app.cacheDir)
        try { if (direction == LanTransfer.DOWNLOAD) download(connection, file) else upload(connection, file) }
        finally { file.delete() }
    }

    private fun report(status: String, progress: Int? = null) {
        mutable.value = LanSyncState(LanSyncPhase.BUSY, status, progress = progress)
    }

    private fun run(connection: LanSyncClient, status: String, preserveCode: Boolean = false, block: suspend () -> Unit) {
        mutable.value = LanSyncState(LanSyncPhase.BUSY, status, if (preserveCode) mutable.value.verification else null)
        operation = viewModelScope.launch {
            try { block() }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) {
                if (client === connection) {
                    client = null
                    queued = null
                    withContext(Dispatchers.IO) { connection.close() }
                    mutable.value = LanSyncState(LanSyncPhase.ENTRY, e.message ?: "局域网连接中断。")
                }
            }
        }
    }

    internal fun disconnect(status: String = "连接已关闭。") {
        val old = client ?: return
        client = null
        queued = null
        pairingExpiry?.cancel()
        val finishing = operation
        finishing?.cancel()
        report("正在关闭连接…")
        viewModelScope.launch {
            withContext(Dispatchers.IO) { old.close() }
            finishing?.join()
            mutable.value = LanSyncState(LanSyncPhase.ENTRY, status)
        }
    }

    override fun onCleared() { client?.close() }
}
