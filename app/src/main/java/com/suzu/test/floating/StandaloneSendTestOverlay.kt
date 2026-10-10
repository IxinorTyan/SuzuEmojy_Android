package com.suzu.test.floating

import android.graphics.Color
import android.graphics.PixelFormat
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.accessibility.TestAccessibilityService
import com.suzu.test.ime.ImageAdapter
import com.suzu.test.ime.ImageItem
import com.suzu.test.ime.TestImageIME
import com.suzu.test.ime.data.ResourceGridLoader
import com.suzu.test.ime.sender.ImageSender
import com.suzu.test.log.TestLog

/** A manually enabled, non-focusable experiment, independent of the production overlays. */
class StandaloneSendTestOverlay(private val service: TestAccessibilityService) {
    private val wm = service.getSystemService(WindowManager::class.java)
    private val loader = ResourceGridLoader(service)
    private val sender = ImageSender(service)
    private var closed = false
    private val attempts = OverlaySendAttempt()
    private var expanded = false
    private var attached = false
    private val root = LinearLayout(service).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(8), dp(4), dp(8), dp(4))
        setBackgroundColor(Color.rgb(238, 242, 255))
        elevation = dp(12).toFloat()
    }
    private val status = TextView(service).apply {
        setTextColor(Color.rgb(25, 35, 60))
        textSize = 12f
        maxLines = 7
        movementMethod = android.text.method.ScrollingMovementMethod.getInstance()
        text = "先在 QQ 开启我们的表情 IME，再点图作对照；随后切换其他输入法再试。两次都调用我们自己的原特化发送，不自动切换、不粘贴、不分享。"
    }
    private val toggle = Button(service).apply {
        text = "发送测试 · 展开图库"
        setOnClickListener { setExpanded(!expanded) }
    }
    private val adapter = ImageAdapter({ attemptOwnImeSend(it) }, { _, _ -> })
    private val grid = RecyclerView(service).apply {
        layoutManager = GridLayoutManager(service, 4)
        adapter = this@StandaloneSendTestOverlay.adapter
    }
    private val params = WindowManager.LayoutParams(
        dp(230), WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.TOP or Gravity.END
        x = dp(8)
        y = dp(100)
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
    }

    init {
        val header = LinearLayout(service)
        header.addView(toggle, LinearLayout.LayoutParams(0, dp(48), 1f))
        header.addView(Button(service).apply {
            text = "×"
            contentDescription = "关闭独立发送测试悬浮窗"
            setOnClickListener { service.hideStandaloneSendTest() }
        }, LinearLayout.LayoutParams(dp(48), dp(48)))
        root.addView(header)
        root.addView(status)
        root.addView(grid, LinearLayout.LayoutParams(-1, dp(200)))
        status.visibility = View.GONE
        grid.visibility = View.GONE
    }

    fun show() {
        wm.addView(root, params)
        attached = true
        loader.loadResources { items ->
            if (!closed) {
                adapter.submitList(items)
                if (items.isEmpty()) status.text = "图库为空，请先导入表情。"
            }
        }
    }

    private fun setExpanded(value: Boolean) {
        expanded = value
        params.width = if (value) {
            (service.resources.displayMetrics.widthPixels - dp(16)).coerceAtMost(dp(380))
        } else dp(230)
        toggle.text = if (value) "发送测试 · 收起" else "发送测试 · 展开图库"
        status.visibility = if (value) View.VISIBLE else View.GONE
        grid.visibility = if (value) View.VISIBLE else View.GONE
        wm.updateViewLayout(root, params)
    }

    private fun defaultIme(): String =
        Settings.Secure.getString(service.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD).orEmpty()

    private fun attemptOwnImeSend(item: ImageItem) {
        TestLog.i("StandaloneSendTest", "图片点击：item=${item.displayName} closed=$closed")
        if (closed) return
        val attempt = attempts.begin()
        if (attempt == null) {
            status.text = "上一张图片仍在准备，请稍候；命令提交后即可继续点图。"
            TestLog.w("StandaloneSendTest", "上一张仍在准备；诊断等待不锁定点击")
            return
        }
        val ime = defaultIme()
        val ownIme = TestImageIME.instance
        val session = ownIme?.inputSessionVersion
        val before = ownIme?.overlayConnectionState() ?: "自家 IME 实例不存在"
        val targetPackage = "com.tencent.mobileqq"
        if (ownIme?.currentInputConnection == null) {
            status.text = "未递交命令：${if (ownIme == null) "自家 IME 实例不存在" else "自家输入连接为空"}。\n当前输入法：$ime\n此时原 IME 通道不可用；没有自动切换或改走其他路径。"
            TestLog.w("StandaloneSendTest", "连接预检失败：defaultIme=$ime $before；未准备图片、未授权、未递交")
            attempts.submitted(attempt)
            return
        }
        status.text = "准备调用自家 IME 原发送…\n$before"
        TestLog.i("StandaloneSendTest", "自家原发送开始：target=$targetPackage defaultIme=$ime $before")
        sender.executeH1b(
            item = item,
            targetPkg = targetPackage,
            editorInfoPackage = ownIme?.currentInputEditorInfo?.packageName,
            isolatedTest = true,
            icProvider = {
                val current = TestImageIME.instance
                TestLog.i("StandaloneSendTest", "取自家连接：${current?.overlayConnectionState() ?: "实例不存在"}")
                check(!closed) { "测试窗口已关闭" }
                check(defaultIme() == ime && current === ownIme && current?.inputSessionVersion == session) {
                    "准备图片期间输入会话发生变化，请重新点图"
                }
                val connection = current?.currentInputConnection
                if (connection != null) {
                    check(current.currentInputEditorInfo?.packageName == targetPackage) {
                        "自家连接不属于 QQ，取消发送（editor=${current.currentInputEditorInfo?.packageName}）"
                    }
                }
                // Do not require our IME to be selected. Observe what its real current connection does.
                connection
            },
            onReport = { message, finished ->
                // The first report follows submission/failure. The 5s observation is not a send lock.
                attempts.submitted(attempt)
                val currentIme = defaultIme()
                val after = TestImageIME.instance?.overlayConnectionState() ?: "自家 IME 实例不存在"
                TestLog.i("StandaloneSendTest", "$message；defaultIme before=$ime after=$currentIme；$after")
                if (!closed && attempts.isLatest(attempt)) {
                    status.text = "$message\n$after\n默认 IME ${if (ime == currentIme) "前后一致" else "已变化"}"
                }
            },
            onSuccess = {}
        )
    }

    fun destroy() {
        closed = true
        if (attached) {
            wm.removeView(root)
            attached = false
        }
        grid.adapter = null
        loader.destroy()
        sender.destroy()
    }

    private fun dp(value: Int) = (value * service.resources.displayMetrics.density).toInt()
}
