package com.suzu.test.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val navBar = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars())
            val baseBottom = (12 * resources.displayMetrics.density).toInt()
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, baseBottom + navBar.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        val versionName = runCatching {
            packageManager.getPackageInfo(packageName, 0).versionName
        }.getOrNull() ?: "未知"

        binding.tvVersion.text = "v$versionName"
        binding.tvVersion.setOnClickListener {
            Toast.makeText(this, "当前已是最新版本：v$versionName", Toast.LENGTH_SHORT).show()
        }

        // 加载作者头像
        Glide.with(this)
            .load(AUTHOR_AVATAR_URL)
            .circleCrop()
            .placeholder(R.drawable.ic_about_author)
            .error(R.drawable.ic_about_author)
            .into(binding.ivAuthorAvatar)

        // 开发者主页跳转
        binding.layoutAuthor.setOnClickListener {
            openUrl(AUTHOR_URL)
        }

        // 开源主页跳转与长按复制
        binding.layoutProject.setOnClickListener {
            openUrl(PROJECT_URL)
        }
        binding.layoutProject.setOnLongClickListener {
            copyToClipboard("开源主页地址", PROJECT_URL)
            Toast.makeText(this, "项目链接已复制到剪贴板", Toast.LENGTH_SHORT).show()
            true
        }

        // 反馈交流群点击与复制
        val copyGroupAction = {
            copyToClipboard("反馈与建议QQ群", FEEDBACK_QQ_GROUP)
            Toast.makeText(this, "QQ群号已复制：$FEEDBACK_QQ_GROUP", Toast.LENGTH_SHORT).show()
        }
        binding.layoutFeedback.setOnClickListener { copyGroupAction() }
        binding.btnCopyFeedback.setOnClickListener { copyGroupAction() }
    }

    private fun copyToClipboard(label: String, text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Exception) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val AUTHOR_URL = "https://github.com/IxinorTyan"
        private const val AUTHOR_AVATAR_URL = "https://github.com/IxinorTyan.png?size=256"
        private const val PROJECT_URL = "https://github.com/IxinorTyan/SuzuEmojy_Android"
        private const val FEEDBACK_QQ_GROUP = "834586488"
    }
}
