package com.suzu.test

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.suzu.test.databinding.ActivityMainBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.log.TestLog
import com.suzu.test.ui.home.RecentThumbAdapter
import com.suzu.test.ui.import.ImportActivity
import com.suzu.test.ui.library.LibraryActivity
import com.suzu.test.ui.settings.SettingsActivity
import com.suzu.test.util.PermissionChecker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "MainActivity"
        private const val SP_NAME = "app_settings"
        private const val KEY_GUIDE_DISMISSED = "home_guide_dismissed"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var recentAdapter: RecentThumbAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TestLog.init(applicationContext)
        TestLog.i(MODULE, "onCreate: MainActivity 启动")

        setupUI()
        observeData()
        triggerAutoCacheClean()
    }

    private fun triggerAutoCacheClean() {
        lifecycleScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            val result = com.suzu.test.storage.CacheCleanManager.cleanExpired(applicationContext)
            TestLog.i(MODULE, "MainActivity 自动清理完成: 删除了 ${result.deletedCount} 个过期暂存文件, 释放 ${com.suzu.test.storage.CacheCleanManager.formatSize(result.freedBytes)}")
        }
    }

    override fun onResume() {
        super.onResume()
        checkAndUpdateState()
    }

    private fun setupUI() {
        binding.btnTopSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.cardLibrary.setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }
        binding.btnOpenLibrary.setOnClickListener {
            startActivity(Intent(this, LibraryActivity::class.java))
        }

        binding.cardImport.setOnClickListener {
            startActivity(Intent(this, ImportActivity::class.java))
        }

        recentAdapter = RecentThumbAdapter {
            startActivity(Intent(this, LibraryActivity::class.java))
        }
        binding.rvRecentAdded.layoutManager = GridLayoutManager(this, 3)
        binding.rvRecentAdded.adapter = recentAdapter

        // 引导卡片按钮绑定
        binding.btnGuideIme.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }
        binding.btnGuideA11y.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        binding.btnGuideOverlay.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                )
            }
        }
        binding.btnGuideDismiss.setOnClickListener {
            val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sp.edit().putBoolean(KEY_GUIDE_DISMISSED, true).apply()
            checkAndUpdateState()
        }
    }

    private fun observeData() {
        val db = DatabaseProvider.getDatabase(this)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    db.resourceDao().getResourceCountFlow().collectLatest { count ->
                        binding.tvResourceCount.text = "$count 个资源"
                    }
                }
                launch {
                    db.resourceDao().getRecentAddedResourcesFlow(6).collectLatest { list ->
                        if (list.isEmpty()) {
                            binding.rvRecentAdded.visibility = View.GONE
                            binding.tvRecentEmptyHint.visibility = View.VISIBLE
                        } else {
                            binding.rvRecentAdded.visibility = View.VISIBLE
                            binding.tvRecentEmptyHint.visibility = View.GONE
                            recentAdapter.submitList(list)
                        }
                    }
                }
            }
        }
    }

    private fun checkAndUpdateState() {
        val isImeEnabled = PermissionChecker.isImeEnabled(this)
        val isA11yRunning = PermissionChecker.isAccessibilityServiceRunning()
        val hasOverlay = PermissionChecker.hasOverlayPermission(this)

        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val guideDismissed = sp.getBoolean(KEY_GUIDE_DISMISSED, false)

        val showGuide = !isImeEnabled && !guideDismissed

        if (showGuide) {
            binding.layoutGuideContainer.visibility = View.VISIBLE
            binding.layoutMainHomeContainer.visibility = View.GONE
            binding.layoutPermissionWarning.visibility = View.GONE

            updateGuideStatus(isImeEnabled, isA11yRunning, hasOverlay)
        } else {
            binding.layoutGuideContainer.visibility = View.GONE
            binding.layoutMainHomeContainer.visibility = View.VISIBLE

            updateWarningBar(isImeEnabled, isA11yRunning, hasOverlay)
        }
    }

    private fun updateGuideStatus(ime: Boolean, a11y: Boolean, overlay: Boolean) {
        if (ime) {
            binding.tvGuideImeDot.text = "✓"
            binding.tvGuideImeDot.setTextColor(0xFF4CAF50.toInt())
            binding.btnGuideIme.isEnabled = false
            binding.btnGuideIme.text = "已启用"
        } else {
            binding.tvGuideImeDot.text = "●"
            binding.tvGuideImeDot.setTextColor(0xFF2196F3.toInt())
            binding.btnGuideIme.isEnabled = true
            binding.btnGuideIme.text = "去开启"
        }

        if (a11y) {
            binding.tvGuideA11yDot.text = "✓"
            binding.tvGuideA11yDot.setTextColor(0xFF4CAF50.toInt())
            binding.btnGuideA11y.isEnabled = false
            binding.btnGuideA11y.text = "已开启"
        } else {
            binding.tvGuideA11yDot.text = "○"
            binding.tvGuideA11yDot.setTextColor(0xFF888888.toInt())
            binding.btnGuideA11y.isEnabled = true
            binding.btnGuideA11y.text = "去开启"
        }

        if (overlay) {
            binding.tvGuideOverlayDot.text = "✓"
            binding.tvGuideOverlayDot.setTextColor(0xFF4CAF50.toInt())
            binding.btnGuideOverlay.isEnabled = false
            binding.btnGuideOverlay.text = "已授权"
        } else {
            binding.tvGuideOverlayDot.text = "○"
            binding.tvGuideOverlayDot.setTextColor(0xFF888888.toInt())
            binding.btnGuideOverlay.isEnabled = true
            binding.btnGuideOverlay.text = "去授权"
        }
    }

    private fun updateWarningBar(ime: Boolean, a11y: Boolean, overlay: Boolean) {
        val notifyA11y = FloatingBallConfig.shouldNotifyWhenA11yDisabled(this)
        val ballEnabled = FloatingBallConfig.isBallEnabled(this)

        if (!ime) {
            // 优先级 1: IME 未启用
            binding.layoutPermissionWarning.visibility = View.VISIBLE
            binding.tvWarningText.text = "输入法未启用，无法发送表情"
            binding.btnWarningAction.text = "去开启"
            binding.btnWarningAction.setOnClickListener {
                startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            }
        } else if (!a11y && notifyA11y) {
            // 优先级 2: 无障碍已关闭 且 开启了提示开关
            binding.layoutPermissionWarning.visibility = View.VISIBLE
            binding.tvWarningText.text = "无障碍服务已关闭，悬浮球不可用"
            binding.btnWarningAction.text = "去开启"
            binding.btnWarningAction.setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        } else if (!overlay && ballEnabled) {
            // 优先级 3: 悬浮窗未授权 且 开启了悬浮球
            binding.layoutPermissionWarning.visibility = View.VISIBLE
            binding.tvWarningText.text = "悬浮窗权限未授予，悬浮球无法显示"
            binding.btnWarningAction.text = "去授权"
            binding.btnWarningAction.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startActivity(
                        Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:$packageName")
                        )
                    )
                }
            }
        } else {
            binding.layoutPermissionWarning.visibility = View.GONE
        }
    }
}
