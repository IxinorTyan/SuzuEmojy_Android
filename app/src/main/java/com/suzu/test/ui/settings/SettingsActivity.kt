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
import com.suzu.test.control.ControlTestActivity
import com.suzu.test.databinding.ActivitySettingsBinding
import com.suzu.test.databinding.DialogLogViewerBinding
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.log.TestLog
import com.suzu.test.util.PermissionChecker

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

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
        setupPermissionActions()
        setupA11ySwitch()
    }

    override fun onResume() {
        super.onResume()
        updatePermissionStates()
    }

    private fun setupNavigation() {
        binding.btnNavKeyboardAppearance.setOnClickListener {
            startActivity(Intent(this, SettingsAppearanceActivity::class.java))
        }

        binding.btnNavFloatingSettings.setOnClickListener {
            startActivity(Intent(this, SettingsFloatingActivity::class.java))
        }

        binding.btnNavOtherSettings.setOnClickListener {
            startActivity(Intent(this, SettingsOtherActivity::class.java))
        }

        binding.btnNavControlTest.setOnClickListener {
            startActivity(Intent(this, ControlTestActivity::class.java))
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
    }

    private fun setupA11ySwitch() {
        binding.swNotifyWhenA11yDisabled.isChecked = FloatingBallConfig.shouldNotifyWhenA11yDisabled(this)
        binding.swNotifyWhenA11yDisabled.setOnCheckedChangeListener { _, isChecked ->
            FloatingBallConfig.setNotifyWhenA11yDisabled(this, isChecked)
        }
    }

    private fun updatePermissionStates() {
        // 1. IME
        val isImeEnabled = PermissionChecker.isImeEnabled(this)
        binding.tvPermImeStatus.text = "输入法: " + if (isImeEnabled) "已启用 ✓" else "未启用"
        binding.btnPermImeAction.visibility = if (isImeEnabled) View.GONE else View.VISIBLE

        // 2. 无障碍
        val isA11yRunning = PermissionChecker.isAccessibilityServiceRunning()
        binding.tvPermA11yStatus.text = "无障碍服务: " + if (isA11yRunning) "已开启 ✓" else "已关闭"
        binding.btnPermA11yAction.visibility = if (isA11yRunning) View.GONE else View.VISIBLE

        // 3. 悬浮窗
        val hasOverlay = PermissionChecker.hasOverlayPermission(this)
        binding.tvPermOverlayStatus.text = "悬浮窗: " + if (hasOverlay) "已授权 ✓" else "未授权"
        binding.btnPermOverlayAction.visibility = if (hasOverlay) View.GONE else View.VISIBLE

        // 4. 相册读取
        val hasStorage = PermissionChecker.hasStoragePermission(this)
        binding.tvPermStorageStatus.text = "相册读取: " + if (hasStorage) "已授权 ✓" else "未授权"
        binding.btnPermStorageAction.visibility = if (hasStorage) View.GONE else View.VISIBLE
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
