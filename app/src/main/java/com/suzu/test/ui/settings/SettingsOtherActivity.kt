package com.suzu.test.ui.settings

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.suzu.test.databinding.ActivitySettingsOtherBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.log.TestLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsOtherActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "SettingsOther"
        private const val SP_NAME = "app_settings"
        private const val KEY_RECENT_LIMIT = "recent_history_limit"
        private const val DEFAULT_LIMIT = 40
    }

    private lateinit var binding: ActivitySettingsOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val currentLimit = sp.getInt(KEY_RECENT_LIMIT, DEFAULT_LIMIT)
        binding.etRecentLimit.setText(currentLimit.toString())

        binding.btnSaveSettings.setOnClickListener {
            val inputStr = binding.etRecentLimit.text.toString().trim()
            val parsed = inputStr.toIntOrNull()

            if (parsed == null || parsed < 1 || parsed > 100) {
                Toast.makeText(this, "请输入 1 到 100 之间的整数", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sp.edit().putInt(KEY_RECENT_LIMIT, parsed).apply()
            TestLog.i(MODULE, "已保存常用表情上限: $parsed")

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val db = DatabaseProvider.getDatabase(applicationContext)
                    db.recentDao().trimHistory(parsed)
                    TestLog.i(MODULE, "已立即对数据库 recent_history 执行上限裁剪: $parsed")
                }
                Toast.makeText(this@SettingsOtherActivity, "设置已保存并生效", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
