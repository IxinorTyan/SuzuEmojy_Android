package com.suzu.test.control

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suzu.test.databinding.ActivityControlTestBinding
import com.suzu.test.log.TestLog

class ControlTestActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ControlTestActivity"
    }

    private lateinit var binding: ActivityControlTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TestLog.i(MODULE, "onCreate: 进入标准对照组测试页面")

        binding.etControlInput.onCommitContentListener = { uri: Uri, mime: String ->
            runOnUiThread {
                binding.tvEmptyHint.visibility = View.GONE
                Glide.with(this)
                    .load(uri)
                    .fitCenter()
                    .into(binding.ivReceivedImage)

                val statusText = "接收状态: ✅ 成功接收并在界面渲染！(MIME=$mime, URI=$uri)"
                binding.tvResultStatus.text = statusText
                TestLog.i(MODULE, statusText)
            }
            true
        }

        binding.btnBackToMain.setOnClickListener {
            finish()
        }
    }
}
