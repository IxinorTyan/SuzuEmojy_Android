package com.suzu.test.resource.lansync

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import org.json.JSONObject
import java.io.File
import java.io.IOException

internal class LanReconciliation(private val library: LanSyncAndroidLibrary, private val cacheDir: File) {
    suspend fun run(client: LanSyncClient, report: (String, Int?) -> Unit): String {
        report("正在读取两端图库…", null)
        val peer = client.readMessage().getString("id")
        val device = library.device()
        require(syncId.matches(peer) && peer != device) { "电脑同步设备标识无效或与手机重复。" }
        client.writeMessage(JSONObject().put("id", device))
        val before = library.capture(device, peer)
        client.writeMessage(JSONObject().put("revision", before.revision ?: JSONObject.NULL).put("library", before.library.json()))
        val reply = client.readMessage()
        if (!reply.isNull("error")) throw IOException(reply.getString("error"))
        val revision = reply.getString("revision")
        require(syncId.matches(revision)) { "电脑同步记录无效。" }
        val target = SyncLibrary.read(reply.getJSONObject("library"))
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
        try {
            if (receive.any) {
                inbound = File.createTempFile("suzu-lan-", ".zip", cacheDir)
                client.receivePackage(inbound) { pct -> report("正在接收：$pct%", pct) }
            }
            if (send.any) {
                report("正在打包要同步的资源…", null)
                outbound = File.createTempFile("suzu-lan-", ".zip", cacheDir)
                library.export(outbound, send)
                client.sendPackage(outbound) { pct -> report("正在发送：$pct%", pct) }
            }
            report("正在校验同步资源…", null)
            library.withPrepared(before, target, inbound) { prepared ->
                currentCoroutineContext().ensureActive()
                client.writeStatus(1)
                if (!client.readDecision()) throw IOException("电脑未能应用同步，手机图库未更改。")
                report("正在应用同步…", null)
                try {
                    library.apply(device, peer, revision, before, target, bindings, prepared)
                } catch (e: Exception) {
                    runCatching { client.writeStatus(0) }
                    throw IOException("电脑已应用，手机未能应用；可重新同步。${e.message.orEmpty()}", e)
                }
                try { client.writeStatus(1) }
                catch (e: IOException) { throw IOException("两端均已应用，未能向电脑回报。", e) }
            }
            return "两端同步完成。"
        } finally {
            inbound?.delete()
            outbound?.delete()
        }
    }
}
