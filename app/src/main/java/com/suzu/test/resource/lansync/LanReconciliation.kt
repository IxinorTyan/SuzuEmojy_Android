package com.suzu.test.resource.lansync

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import org.json.JSONObject
import org.json.JSONArray
import java.io.File
import java.io.IOException

internal class LanReconciliation(private val library: LanSyncAndroidLibrary, private val cacheDir: File) {
    suspend fun run(client: LanSyncClient, report: (String, Int?) -> Unit): String = coroutineScope {
        val streaming = client.streamingSync
        report("正在读取两端图库…", null)
        val peer = client.readMessage().getString("id")
        val device = library.device()
        require(syncId.matches(peer) && peer != device) { "电脑同步设备标识无效或与手机重复。" }
        client.writeMessage(JSONObject().put("id", device))
        val before = library.capture(device, peer)
        if (!streaming && before.skipped.isNotEmpty()) throw IOException("图库含无法校验的表情。请更新电脑后再同步，以便安全跳过。")
        client.writeMessage(JSONObject().put("revision", before.revision ?: JSONObject.NULL).put("library", before.library.json())
            .apply { if (streaming) put("skipped", JSONArray(before.skipped)) })
        val reply = client.readMessage()
        if (!reply.isNull("error")) throw IOException(reply.getString("error"))
        val revision = reply.getString("revision")
        require(syncId.matches(revision)) { "电脑同步记录无效。" }
        val target = SyncLibrary.read(reply.getJSONObject("library"))
        val hold = if (streaming) readKeys(reply.getJSONArray("hold")).toMutableSet() else mutableSetOf()
        require(hold.containsAll(before.skipped) && target.resources.none { it.key in hold }) { "同步跳过清单无效。" }
        val bindingsJson = reply.getJSONObject("bindings")
        require(bindingsJson.length() <= target.categories.size) { "收藏夹同步映射无效。" }
        val bindings = bindingsJson.keys().asSequence().associateWith(bindingsJson::getString)
        val categories = target.categories.map { it.id }.toSet()
        require(bindings.keys.all { it in categories } && bindings.values.toSet().size == bindings.size &&
            bindings.values.all { it in before.categories }) { "收藏夹同步映射无效。" }
        val receive = SyncAssets.needed(before.library, target)
        val send = SyncAssets.read(reply.getJSONObject("send"))
        require(send.within(before.library) && send.within(target)) { "电脑请求的同步资源不在清单中。" }

        var inbound: File? = null
        var outbound: File? = null
        var skippedCount = 0
        try {
            val upload = if (send.any) {
                val file = File.createTempFile("suzu-lan-", ".zip", cacheDir)
                outbound = file
                if (!receive.any) report("正在打包要同步的资源…", null)
                async(Dispatchers.IO) { runCatching {
                    library.export(file, send, streaming)
                    file
                } }
            } else null
            try {
            if (receive.any) {
                inbound = File.createTempFile("suzu-lan-", ".zip", cacheDir)
                client.receivePackage(inbound, streaming) { pct -> report("正在接收：$pct%", pct) }
            }
            if (upload != null) {
                if (!upload.isCompleted) report("正在打包要同步的资源…", null)
                client.sendPackage(upload.await().getOrThrow(), streaming) { pct -> report("正在发送：$pct%", pct) }
            }
            } finally {
                // Do not remove a ZIP while the worker could still be writing it.
                upload?.cancel()
                kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) { upload?.join() }
            }
            report("正在校验同步资源…", null)
            library.withPrepared(before, target, inbound, streaming) { prepared ->
                currentCoroutineContext().ensureActive()
                val approved = if (streaming) {
                    client.writeMessage(JSONObject().put("skipped", JSONArray(prepared.rejected)))
                    val validation = client.readMessage()
                    val rejected = readKeys(validation.getJSONArray("skipped"))
                    require(rejected.all { it in receive.keys || it in send.keys } && rejected.containsAll(prepared.rejected)) {
                        "电脑的资源校验结果无效。"
                    }
                    skippedCount = validation.getInt("count")
                    require(skippedCount >= before.skipped.size + rejected.size) { "电脑的资源校验结果无效。" }
                    hold.addAll(rejected)
                    target.copy(resources = target.resources.filterNot { it.key in hold })
                } else {
                    client.writeStatus(1)
                    target
                }
                if (!client.readDecision()) throw IOException("电脑未能应用同步，手机图库未更改。")
                report("正在应用同步…", null)
                try {
                    library.apply(device, peer, revision, before, approved, bindings, prepared, hold)
                } catch (e: Exception) {
                    runCatching { client.writeStatus(0) }
                    throw IOException("电脑已应用，手机未能应用；可重新同步。${e.message.orEmpty()}", e)
                }
                try { client.writeStatus(1) }
                catch (e: IOException) { throw IOException("两端均已应用，未能向电脑回报。", e) }
            }
            if (skippedCount == 0) "两端同步完成。" else "两端同步完成，跳过 $skippedCount 个无法校验的表情。"
        } finally {
            inbound?.delete()
            outbound?.delete()
        }
    }

    private fun readKeys(array: JSONArray): Set<String> {
        require(array.length() <= 35_000) { "同步跳过清单无效。" }
        return (0 until array.length()).map { array.getString(it) }.toSet().also {
            require(it.size == array.length() && it.all(syncKey::matches)) { "同步跳过清单无效。" }
        }
    }
}
