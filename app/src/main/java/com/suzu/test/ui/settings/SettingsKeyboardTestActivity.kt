package com.suzu.test.ui.settings

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suzu.test.databinding.ActivitySettingsKeyboardTestBinding
import com.suzu.test.log.TestLog

class SettingsKeyboardTestActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "SettingsKeyboardTest"
    }

    private lateinit var binding: ActivitySettingsKeyboardTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsKeyboardTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etTestInputBox.onCommitContentListener = { uri: Uri, mime: String ->
            runOnUiThread {
                binding.ivReceivedPreview.visibility = View.VISIBLE
                Glide.with(this)
                    .load(uri)
                    .fitCenter()
                    .into(binding.ivReceivedPreview)

                binding.tvSendResultStatus.visibility = View.VISIBLE
                val statusText = "接收成功: (MIME=$mime)"
                binding.tvSendResultStatus.text = statusText
                TestLog.i(MODULE, "键盘测试接收图片: $statusText, URI=$uri")
            }
            true
        }
    }
}
