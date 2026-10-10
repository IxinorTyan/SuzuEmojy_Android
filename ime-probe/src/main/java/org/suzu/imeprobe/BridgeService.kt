package org.suzu.imeprobe

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

/** Signature-protected experiment protocol: 1=probe session, 2=submit fixed private command. */
class BridgeService : Service() {
    private val messenger = Messenger(Handler(Looper.getMainLooper()) { message ->
        val result = Bundle()
        try {
            check(packageManager.getPackagesForUid(message.sendingUid)?.contains("com.tencent.qqpinyin.suzu") == true) {
                "调用方不是主应用"
            }
            val ime = checkNotNull(ProbeIme.instance) { "实验 IME 尚未启动" }
            val data = message.data
            val target = data.getString("target").orEmpty()
            when (message.what) {
                1 -> result.putString("session", ime.probe(target))
                2 -> result.putBoolean("accepted", ime.submit(
                    target, data.getString("session").orEmpty(),
                    Uri.parse(data.getString("uri").orEmpty()), data.getString("event").orEmpty()
                ))
                else -> error("未知操作")
            }
            result.putBoolean("ok", true)
        } catch (e: Exception) {
            result.putString("error", e.message ?: e.javaClass.simpleName)
            Log.w("SuzuImeProbe", "桥接拒绝：${e.message}")
        }
        try {
            message.replyTo?.send(Message.obtain(null, message.what).apply {
                arg1 = message.arg1
                data = result
            })
        } catch (e: Exception) { Log.w("SuzuImeProbe", "回执失败：${e.message}") }
        true
    })
    override fun onBind(intent: Intent?): IBinder = messenger.binder
}
