package org.suzu.imeprobe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class SetupActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val body = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 64, 32, 32)
        }
        body.addView(TextView(this).apply {
            textSize = 18f
            text = "独立 IME 最小实验\n\n包名：org.suzu.imeprobe\n\n" +
                "1. 启用此实验输入法。\n2. 在 QQ 测试聊天中选择此输入法。\n" +
                "3. 使用主应用的独立测试悬浮窗选图。\n\n" +
                "图片来自主应用，本 IME 只递交原特化命令。无文字键盘、无网络权限。\n"
        })
        body.addView(Button(this).apply {
            text = "启用实验输入法"
            setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
        })
        body.addView(Button(this).apply {
            text = "选择输入法"
            setOnClickListener { getSystemService(InputMethodManager::class.java).showInputMethodPicker() }
        })
        setContentView(body)
    }
}
