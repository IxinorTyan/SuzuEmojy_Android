package com.suzu.test.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.graphics.Typeface
import com.suzu.test.databinding.ActivitySettingsBinding
import com.suzu.test.databinding.DialogLogViewerBinding
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.log.TestLog
import com.suzu.test.util.PermissionChecker

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val SP_NAME = "app_settings"
        private const val KEY_PERM_EXPANDED = "settings_perm_expanded"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var isPermissionExpanded = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        updatePermissionStates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupPermissionCollapse()
        setupPermissionActions()
        setupA11ySwitch()
        observeA11yState()
    }

    private fun setupPermissionCollapse() {
        val sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        // 默认折叠，节省屏幕空间，可通过点击随时展开
        isPermissionExpanded = sp.getBoolean(KEY_PERM_EXPANDED, false)
        applyPermissionExpandState(animate = false)

        binding.layoutPermissionHeader.setOnClickListener {
            isPermissionExpanded = !isPermissionExpanded
            sp.edit().putBoolean(KEY_PERM_EXPANDED, isPermissionExpanded).apply()
            applyPermissionExpandState(animate = true)
        }
    }

    private fun applyPermissionExpandState(animate: Boolean) {
        if (animate) {
            android.transition.TransitionManager.beginDelayedTransition(binding.cardPermissions)
        }
        binding.layoutPermissionContent.visibility = if (isPermissionExpanded) View.VISIBLE else View.GONE
        binding.ivPermissionExpandArrow.animate()
            .rotation(if (isPermissionExpanded) 180f else 0f)
            .setDuration(if (animate) 200 else 0)
            .start()
    }

    private fun observeA11yState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.collectLatest {
                    updatePermissionStates()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        com.suzu.test.accessibility.AccessibilityStateMonitor.refresh()
        updatePermissionStates()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnNavKeyboardAppearance.setOnClickListener {
            startActivity(Intent(this, SettingsAppearanceActivity::class.java))
        }

        binding.btnNavFloatingSettings.setOnClickListener {
            startActivity(Intent(this, SettingsFloatingActivity::class.java))
        }

        binding.btnNavShareWhitelist.setOnClickListener {
            startActivity(Intent(this, ShareWhitelistActivity::class.java))
        }

        binding.btnNavOtherSettings.setOnClickListener {
            startActivity(Intent(this, SettingsOtherActivity::class.java))
        }

        binding.btnNavAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        binding.btnNavViewLogs.setOnClickListener {
            showLogViewerDialog()
        }
    }

    private fun setupPermissionActions() {
        binding.btnPermImeAction.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        binding.btnPermA11yAction.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.btnPermOverlayAction.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                )
            }
        }

        binding.btnPermStorageAction.setOnClickListener {
            requestPermissionLauncher.launch(PermissionChecker.getStoragePermissions())
        }

        binding.btnPermBatteryAction.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    val intent = Intent(
                        Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                        Uri.parse("package:$packageName")
                    )
                    startActivity(intent)
                } catch (e: Exception) {
                    try {
                        startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
                    } catch (ignored: Exception) {}
                }
            }
        }

        binding.btnPermAutoStartAction.setOnClickListener {
            val opened = com.suzu.test.util.AutoStartHelper.openAutoStartSettings(this)
            if (opened) {
                binding.tvPermAutoStartSub.text = "开启后返回即可"
            } else {
                binding.tvPermAutoStartSub.text = "请前往系统「设置 - 应用管理」中开启自启动权限"
            }
        }

    }

    private fun setupA11ySwitch() {
        binding.swNotifyWhenA11yDisabled.isChecked = FloatingBallConfig.shouldNotifyWhenA11yDisabled(this)
        binding.swNotifyWhenA11yDisabled.setOnCheckedChangeListener { _, isChecked ->
            FloatingBallConfig.setNotifyWhenA11yDisabled(this, isChecked)
        }
    }

    private fun updatePermissionStates() {
        val colorActive = 0xFF16A34A.toInt() // Green
        val colorInactive = 0xFF0F172A.toInt() // Slate dark

        // 1. IME
        val isImeEnabled = PermissionChecker.isImeEnabled(this)
        binding.tvPermImeStatus.text = "输入法: " + if (isImeEnabled) "已启用 ✓" else "未启用"
        binding.tvPermImeStatus.setTextColor(if (isImeEnabled) colorActive else colorInactive)
        binding.btnPermImeAction.visibility = if (isImeEnabled) View.GONE else View.VISIBLE

        // 2. 无障碍
        val isA11yRunning = com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.value
        binding.tvPermA11yStatus.text = "无障碍服务: " + if (isA11yRunning) "已开启 ✓" else "已关闭"
        binding.tvPermA11yStatus.setTextColor(if (isA11yRunning) colorActive else colorInactive)
        binding.btnPermA11yAction.visibility = if (isA11yRunning) View.GONE else View.VISIBLE

        // 3. 悬浮窗
        val hasOverlay = PermissionChecker.hasOverlayPermission(this)
        binding.tvPermOverlayStatus.text = "悬浮窗: " + if (hasOverlay) "已授权 ✓" else "未授权"
        binding.tvPermOverlayStatus.setTextColor(if (hasOverlay) colorActive else colorInactive)
        binding.btnPermOverlayAction.visibility = if (hasOverlay) View.GONE else View.VISIBLE

        // 4. 相册读取
        val hasStorage = PermissionChecker.hasStoragePermission(this)
        binding.tvPermStorageStatus.text = "相册读取: " + if (hasStorage) "已授权 ✓" else "未授权"
        binding.tvPermStorageStatus.setTextColor(if (hasStorage) colorActive else colorInactive)
        binding.btnPermStorageAction.visibility = if (hasStorage) View.GONE else View.VISIBLE

        // 5. 忽略电池优化
        val isBatteryIgnored = PermissionChecker.isIgnoringBatteryOptimizations(this)
        binding.tvPermBatteryStatus.text = "忽略电池优化: " + if (isBatteryIgnored) "已开启 ✓" else "未开启"
        binding.tvPermBatteryStatus.setTextColor(if (isBatteryIgnored) colorActive else colorInactive)
        binding.btnPermBatteryAction.visibility = if (isBatteryIgnored) View.GONE else View.VISIBLE

        // 6. 自启动 (机型判断，未识别或不支持则整行隐藏)
        if (com.suzu.test.util.AutoStartHelper.isAutoStartSupported()) {
            binding.layoutPermAutoStart.visibility = View.VISIBLE
        } else {
            binding.layoutPermAutoStart.visibility = View.GONE
        }

        // 更新头部状态摘要胶囊
        val coreOk = isImeEnabled && hasStorage
        val extraOk = hasOverlay && isA11yRunning && isBatteryIgnored

        when {
            coreOk && extraOk -> {
                binding.tvPermissionSummaryBadge.text = "全部就绪 ✓"
                binding.tvPermissionSummaryBadge.setTextColor(0xFF16A34A.toInt())
                binding.tvPermissionSummaryBadge.setBackgroundResource(com.suzu.test.R.drawable.bg_home_tag_green)
            }
            coreOk -> {
                binding.tvPermissionSummaryBadge.text = "基础已就绪"
                binding.tvPermissionSummaryBadge.setTextColor(0xFF2563EB.toInt())
                binding.tvPermissionSummaryBadge.setBackgroundResource(com.suzu.test.R.drawable.bg_home_tag_blue)
            }
            else -> {
                val missingBasic = (if (!isImeEnabled) 1 else 0) + (if (!hasStorage) 1 else 0)
                binding.tvPermissionSummaryBadge.text = "需配置 ${missingBasic} 项"
                binding.tvPermissionSummaryBadge.setTextColor(0xFFEA580C.toInt())
                binding.tvPermissionSummaryBadge.setBackgroundResource(com.suzu.test.R.drawable.bg_home_tag_orange)
            }
        }
    }

    private fun showLogViewerDialog() {
        val dialogBinding = DialogLogViewerBinding.inflate(LayoutInflater.from(this))
        dialogBinding.tvDialogLogDisplay.movementMethod = ScrollingMovementMethod()
        dialogBinding.tvDialogLogDisplay.text = TestLog.getAllLogs()

        val dialog = AlertDialog.Builder(this)
            .setTitle("运行日志")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnDialogClearLog.setOnClickListener {
            TestLog.clear()
            dialogBinding.tvDialogLogDisplay.text = ""
            Toast.makeText(this, "日志已清空", Toast.LENGTH_SHORT).show()
        }

        dialogBinding.btnDialogCopyLog.setOnClickListener {
            val logs = TestLog.getAllLogs()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("SuzuEmojy_Logs", logs)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "全部日志已复制到剪贴板", Toast.LENGTH_SHORT).show()
        }

        dialogBinding.btnDialogClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
