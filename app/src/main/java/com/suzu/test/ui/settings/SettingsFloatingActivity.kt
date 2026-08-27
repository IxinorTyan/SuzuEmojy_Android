package com.suzu.test.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.databinding.ActivitySettingsFloatingBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.log.TestLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SettingsFloatingActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "SettingsFloating"
    }

    private lateinit var binding: ActivitySettingsFloatingBinding

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resId = result.data?.getLongExtra("selected_resource_id", -1L) ?: -1L
            if (resId > 0) {
                FloatingBallConfig.setImageResourceId(this, resId)
                TestLog.i(MODULE, "已选择悬浮球贴图资源: ID=$resId")
                updateCustomImageUI()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsFloatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMasterSwitch()
        setupFloatingAdjusters()
        setupAppFilter()
        setupCustomImageActions()
        observeA11yState()
    }

    private fun observeA11yState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.collectLatest {
                    checkA11yStatus()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        com.suzu.test.accessibility.AccessibilityStateMonitor.refresh()
        binding.swBallMasterSwitch.isChecked = FloatingBallConfig.isBallEnabled(this)
        binding.swShowOnlyWithIme.isChecked = FloatingBallConfig.isShowOnlyWithImeEnabled(this)
        checkA11yStatus()
        updateCustomImageUI()
    }

    private fun setupMasterSwitch() {
        binding.swBallMasterSwitch.setOnClickListener {
            val targetState = binding.swBallMasterSwitch.isChecked

            if (targetState) {
                // 开启前置校验
                if (!android.provider.Settings.canDrawOverlays(this)) {
                    binding.swBallMasterSwitch.isChecked = false
                    android.widget.Toast.makeText(this, "请先授予悬浮窗权限", android.widget.Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        android.net.Uri.parse("package:$packageName")
                    )
                    startActivity(intent)
                    return@setOnClickListener
                }

                if (!com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.value) {
                    binding.swBallMasterSwitch.isChecked = false
                    android.widget.Toast.makeText(this, "悬浮球需要开启无障碍服务", android.widget.Toast.LENGTH_SHORT).show()
                    val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    startActivity(intent)
                    return@setOnClickListener
                }
            }

            FloatingBallConfig.setBallEnabled(this, targetState)
            com.suzu.test.accessibility.TestAccessibilityService.instance?.syncBallState()
            TestLog.i(MODULE, "悬浮球总开关变更为: $targetState")
        }
    }

    private fun setupCustomImageActions() {
        binding.btnSelectBallImage.setOnClickListener {
            pickImageLauncher.launch(Intent(this, FloatingBallPickerActivity::class.java))
        }

        binding.btnResetBallImage.setOnClickListener {
            FloatingBallConfig.setImageResourceId(this, null)
            TestLog.i(MODULE, "已恢复悬浮球默认纯色外观")
            updateCustomImageUI()
        }
    }

    private fun updateCustomImageUI() {
        val resourceId = FloatingBallConfig.getImageResourceId(this)
        if (resourceId == null) {
            binding.tvCustomImageStatus.text = "当前外观: 默认纯色球"
            binding.ivPreviewSkin.visibility = View.GONE
            binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
        } else {
            binding.tvCustomImageStatus.text = "当前外观: 自定义贴图 (ID=$resourceId)"
            lifecycleScope.launch {
                val resource = withContext(Dispatchers.IO) {
                    try {
                        val db = DatabaseProvider.getDatabase(this@SettingsFloatingActivity)
                        db.resourceDao().getById(resourceId)
                    } catch (e: Exception) {
                        null
                    }
                }

                val file = if (resource != null) File(filesDir, "resources/${resource.filename}") else null
                if (file != null && file.exists()) {
                    binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
                    binding.ivPreviewSkin.visibility = View.VISIBLE
                    Glide.with(this@SettingsFloatingActivity)
                        .load(file)
                        .centerCrop()
                        .circleCrop()
                        .into(binding.ivPreviewSkin)
                } else {
                    binding.tvCustomImageStatus.text = "当前外观: 默认纯色球"
                    binding.ivPreviewSkin.visibility = View.GONE
                    binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
                }
            }
        }
    }

    private fun checkA11yStatus() {
        val isA11yEnabled = com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.value
        binding.tvA11yWarning.visibility = if (isA11yEnabled) View.GONE else View.VISIBLE
    }

    private fun setupAppFilter() {
        binding.swShowOnlyWithIme.isChecked = FloatingBallConfig.isShowOnlyWithImeEnabled(this)
        binding.swShowOnlyWithIme.setOnCheckedChangeListener { _, isChecked ->
            FloatingBallConfig.setShowOnlyWithImeEnabled(this, isChecked)
            TestLog.i(MODULE, "仅在弹出键盘时显示开关: $isChecked")
        }
    }

    private fun setupFloatingAdjusters() {
        val currentSize = FloatingBallConfig.getSizeDp(this)
        binding.sbBallSize.progress = currentSize - FloatingBallConfig.MIN_BALL_SIZE_DP
        binding.tvSizeValue.text = "$currentSize dp"

        val currentAlpha = FloatingBallConfig.getAlphaPercent(this)
        binding.sbBallAlpha.progress = currentAlpha - FloatingBallConfig.MIN_BALL_ALPHA
        binding.tvAlphaValue.text = "$currentAlpha %"

        val currentDuration = FloatingBallConfig.getAnimDurationMs(this)
        binding.sbBallAnimDuration.progress = (currentDuration / 20).coerceIn(0, 10)
        binding.tvAnimDurationValue.text = if (currentDuration == 0) "0 ms (关闭动画)" else "$currentDuration ms"

        updatePreview(currentSize, currentAlpha)

        binding.sbBallSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val sizeDp = progress + FloatingBallConfig.MIN_BALL_SIZE_DP
                binding.tvSizeValue.text = "$sizeDp dp"
                FloatingBallConfig.setSizeDp(this@SettingsFloatingActivity, sizeDp)
                updatePreview(sizeDp, null)
                TestLog.i(MODULE, "修改悬浮球大小: $sizeDp dp")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.sbBallAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val alphaPct = progress + FloatingBallConfig.MIN_BALL_ALPHA
                binding.tvAlphaValue.text = "$alphaPct %"
                FloatingBallConfig.setAlphaPercent(this@SettingsFloatingActivity, alphaPct)
                updatePreview(null, alphaPct)
                TestLog.i(MODULE, "修改悬浮球透明度: $alphaPct %")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.sbBallAnimDuration.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val durationMs = (progress * 20).coerceIn(0, 200)
                binding.tvAnimDurationValue.text = if (durationMs == 0) "0 ms (关闭动画)" else "$durationMs ms"
                FloatingBallConfig.setAnimDurationMs(this@SettingsFloatingActivity, durationMs)
                TestLog.i(MODULE, "修改悬浮球动画时长: $durationMs ms")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updatePreview(sizeDp: Int?, alphaPct: Int?) {
        sizeDp?.let {
            val sizePx = (it * resources.displayMetrics.density).toInt()
            val lp = binding.flPreviewContainer.layoutParams
            lp.width = sizePx
            lp.height = sizePx
            binding.flPreviewContainer.layoutParams = lp
            binding.flPreviewContainer.requestLayout()
        }
        alphaPct?.let {
            binding.flPreviewContainer.alpha = it / 100f
        }
    }
}
