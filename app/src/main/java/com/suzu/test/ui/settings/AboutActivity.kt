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

        binding.btnBack.setOnClickListener {
            finish()
        }

        val versionName = runCatching {
            packageManager.getPackageInfo(packageName, 0).versionName
        }.getOrNull() ?: "未知"

        binding.tvVersion.text = "版本 $versionName"

        Glide.with(this)
            .load("https://github.com/IxinorTyan.png?size=256")
            .circleCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.ivAuthorAvatar)

        binding.tvProjectAddress.setOnClickListener {
            openUrl(PROJECT_URL)
        }

        binding.tvFeedbackGroup.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(
                ClipData.newPlainText("反馈与建议QQ群", FEEDBACK_QQ_GROUP)
            )
            Toast.makeText(this, "QQ群号已复制：$FEEDBACK_QQ_GROUP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: Exception) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PROJECT_URL = "https://github.com/IxinorTyan/SuzuEmojy_Android"
        private const val FEEDBACK_QQ_GROUP = "834586488"
    }
}
