package com.suzu.test.floating

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger

/** One experiment owns one binding. Never starts or selects an IME. All methods run on main. */
class ImeProbeClient(private val context: Context) {
    companion object {
        const val PACKAGE = "org.suzu.imeprobe"
        const val IME = "org.suzu.imeprobe.ProbeIme"
    }
    private val handler = Handler(Looper.getMainLooper())
    private var bound = false
    private var remote: Messenger? = null
    private var requestId = 0
    private var pending: ((Bundle) -> Unit)? = null
    private val timeout = Runnable { fail("桥接超时；请检查实际聊天结果，不会自动重试") }
    private var connected: (() -> Unit)? = null
    private val replies = Messenger(Handler(Looper.getMainLooper()) { message ->
        if (message.arg1 == requestId) {
            val callback = pending
            pending = null
            handler.removeCallbacks(timeout)
            callback?.invoke(message.data)
        }
        true
    })
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            remote = Messenger(binder)
            handler.removeCallbacks(timeout)
            connected?.also { connected = null }?.invoke()
        }
        override fun onServiceDisconnected(name: ComponentName) { fail("实验 IME 桥接连接断开") }
        override fun onNullBinding(name: ComponentName) { fail("实验 IME 未提供桥接") }
        override fun onBindingDied(name: ComponentName) { fail("实验 IME 桥接已失效") }
    }

    fun connectAndProbe(target: String, callback: (Bundle) -> Unit) {
        pending = callback
        connected = { request(1, Bundle().apply { putString("target", target) }, callback) }
        try {
            bound = context.bindService(Intent().setComponent(ComponentName(PACKAGE, "$PACKAGE.BridgeService")),
                connection, Context.BIND_AUTO_CREATE)
            if (!bound) fail("无法绑定实验 IME，请先安装同签名的实验 APK")
            else handler.postDelayed(timeout, 5000)
        } catch (e: Exception) { fail("桥接绑定失败：${e.message}") }
    }

    fun request(operation: Int, data: Bundle, callback: (Bundle) -> Unit) {
        pending = callback
        requestId++
        try {
            checkNotNull(remote) { "桥接未连接" }.send(Message.obtain(null, operation).apply {
                arg1 = requestId
                this.data = data
                replyTo = replies
            })
            handler.postDelayed(timeout, 5000)
        } catch (e: Exception) { fail("桥接请求失败：${e.message}") }
    }

    private fun fail(reason: String) {
        val callback = pending
        close()
        callback?.invoke(Bundle().apply { putString("error", reason) })
    }

    fun close() {
        handler.removeCallbacksAndMessages(null)
        pending = null
        connected = null
        remote = null
        if (bound) { context.unbindService(connection); bound = false }
    }
}
