package org.suzu.imeprobe

import android.content.ComponentName
import android.inputmethodservice.InputMethodService
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.UUID

class ProbeIme : InputMethodService() {
    companion object { var instance: ProbeIme? = null; private set }
    private var session = ""
    private var status: TextView? = null

    override fun onCreate() { super.onCreate(); instance = this }
    override fun onEvaluateFullscreenMode() = false

    override fun onCreateInputView(): View = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(16, 16, 16, 16)
        setBackgroundColor(0xffe8edf7.toInt())
        addView(TextView(this@ProbeIme).apply {
            text = "独立实验 IME · org.suzu.imeprobe\n从主应用测试悬浮窗选图（发送到当前 QQ 聊天）"
            setTextColor(0xff18253b.toInt())
        })
        status = TextView(this@ProbeIme).also { addView(it) }
        addView(Button(this@ProbeIme).apply {
            text = "切回其他输入法"
            setOnClickListener { getSystemService(InputMethodManager::class.java).showInputMethodPicker() }
        })
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        session = UUID.randomUUID().toString()
        Log.i("SuzuImeProbe", "start session=$session target=${attribute?.packageName}")
    }

    override fun onFinishInput() {
        session = ""
        Log.i("SuzuImeProbe", "finish input")
        super.onFinishInput()
    }

    override fun onDestroy() { instance = null; super.onDestroy() }

    private fun verify(target: String) {
        val selected = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        check(ComponentName.unflattenFromString(selected.orEmpty()) == ComponentName(this, ProbeIme::class.java)) {
            "当前默认 IME 不是实验 IME"
        }
        check(target == "com.tencent.mobileqq") { "最小实验仅接受 QQ" }
        check(currentInputStarted && session.isNotEmpty() && currentInputConnection != null &&
            currentInputEditorInfo?.packageName == target) { "没有对应 QQ 输入会话，请先点击聊天输入框" }
    }

    fun probe(target: String): String { verify(target); return session }

    fun submit(target: String, expectedSession: String, uri: Uri, event: String): Boolean {
        verify(target)
        check(expectedSession == session) { "输入会话已变化，取消发送" }
        check(uri.scheme == "content" && uri.authority == "com.tencent.qqpinyin.suzu.provider") {
            "仅接受主应用的图片 URI"
        }
        val ic = requireNotNull(currentInputConnection)
        val command = "com.sogou.inputmethod.exp.commit"
        ic.beginBatchEdit()
        val accepted = try {
            ic.performPrivateCommand(command, Bundle().apply { putParcelable("EXP_PATH_URI", uri) })
        } finally { ic.endBatchEdit() }
        Log.i("SuzuImeProbe", "event=$event session=$session ime=$packageName target=$target " +
            "command=$command uri=$uri accepted=$accepted")
        status?.text = "命令返回 $accepted（不代表图片已发送）"
        return accepted
    }
}
