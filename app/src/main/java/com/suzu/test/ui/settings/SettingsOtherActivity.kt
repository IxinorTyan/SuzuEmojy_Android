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
        private const val KEY_CONVERT_PNG_TO_GIF = "convert_png_to_gif_on_send"
    }

    private lateinit var binding: ActivitySettingsOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val currentLimit = sp.getInt(KEY_RECENT_LIMIT, DEFAULT_LIMIT)
        binding.etRecentLimit.setText(currentLimit.toString())

        val isConvertPngToGif = sp.getBoolean(KEY_CONVERT_PNG_TO_GIF, false)
        binding.switchConvertPngToGif.isChecked = isConvertPngToGif
        binding.switchConvertPngToGif.setOnCheckedChangeListener { _, isChecked ->
            sp.edit().putBoolean(KEY_CONVERT_PNG_TO_GIF, isChecked).apply()
            TestLog.i(MODULE, "已更新静态 PNG 转 GIF 开关: $isChecked")
        }

        setupCacheCleaner()

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

    override fun onResume() {
        super.onResume()
        refreshCacheSizeDisplay()
    }

    private fun setupCacheCleaner() {
        binding.btnCleanCache.setOnClickListener {
            lifecycleScope.launch {
                binding.btnCleanCache.isEnabled = false
                binding.tvCacheSizeDisplay.text = "当前缓存占用: 正在清理..."

                val result = withContext(Dispatchers.IO) {
                    com.suzu.test.storage.CacheCleanManager.cleanAll(applicationContext)
                }

                Toast.makeText(
                    this@SettingsOtherActivity,
                    "清理完成，已释放 ${com.suzu.test.storage.CacheCleanManager.formatSize(result.freedBytes)}",
                    Toast.LENGTH_SHORT
                ).show()

                refreshCacheSizeDisplay()
                binding.btnCleanCache.isEnabled = true
            }
        }
    }

    private fun refreshCacheSizeDisplay() {
        lifecycleScope.launch {
            binding.tvCacheSizeDisplay.text = "当前缓存占用: 正在计算..."
            val bytes = withContext(Dispatchers.IO) {
                com.suzu.test.storage.CacheCleanManager.getCacheSizeBytes(applicationContext)
            }
            binding.tvCacheSizeDisplay.text = "当前缓存占用: ${com.suzu.test.storage.CacheCleanManager.formatSize(bytes)}"
        }
    }
}
