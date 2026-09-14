package com.suzu.test.ui.settings

import android.app.Activity
import android.view.MenuItem
import android.content.Intent
import androidx.appcompat.app.ActionBar
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.suzu.test.R
import com.suzu.test.databinding.ActivitySettingsFloatingBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.floating.FloatingBallConfig
import com.suzu.test.log.TestLog
import com.suzu.test.ui.view.CheckerboardDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupMasterSwitch()
        setupFloatingWindowSection()
        setupFloatingBallSection()
        setupFloatingAdjusters()
        setupSearchBarSection()
        setupShapeSelector()
        setupAppFilter()
        setupEdgeGestureSettings()
        setupCustomImageActions()
        observeA11yState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        binding.swMasterSwitch.isChecked = FloatingBallConfig.isFloatingMasterEnabled(this)
        binding.swEdgeGestureEnabled.isChecked = FloatingBallConfig.isEdgeGestureEnabled(this)
        binding.swBallMasterSwitch.isChecked = FloatingBallConfig.isBallEnabled(this)
        binding.swShowOnlyWithIme.isChecked = FloatingBallConfig.isShowOnlyWithImeEnabled(this)
        updateEdgeGestureUI()
        updateEdgeRegionPreview()
        updateShapeRadioUI()
        checkA11yStatus()
        updateCustomImageUI()
        updateSearchBarUI()
        updateFloatingAdjusterUI()
        updateSectionEnableStates()
        updateSectionCollapseStates()
    }

    private fun setupShapeSelector() {
        updateShapeRadioUI()
        binding.rgBallShape.setOnCheckedChangeListener { _, checkedId ->
            val targetShape = when (checkedId) {
                R.id.rbShapeRoundedRect -> FloatingBallConfig.SHAPE_ROUNDED_RECT
                R.id.rbShapeBorderless -> FloatingBallConfig.SHAPE_BORDERLESS
                else -> FloatingBallConfig.SHAPE_CIRCLE
            }
            FloatingBallConfig.setBallShape(this, targetShape)
            TestLog.i(MODULE, "修改悬浮球形态: $targetShape")
            binding.tvBorderlessHint.visibility = if (targetShape == FloatingBallConfig.SHAPE_BORDERLESS) View.VISIBLE else View.GONE
            updateCustomImageUI()
        }
    }

    private fun updateShapeRadioUI() {
        val currentShape = FloatingBallConfig.getBallShape(this)
        when (currentShape) {
            FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.rbShapeRoundedRect.isChecked = true
            FloatingBallConfig.SHAPE_BORDERLESS -> binding.rbShapeBorderless.isChecked = true
            else -> binding.rbShapeCircle.isChecked = true
        }
        binding.tvBorderlessHint.visibility = if (currentShape == FloatingBallConfig.SHAPE_BORDERLESS) View.VISIBLE else View.GONE
    }

    private fun checkPermissionAndServices(): Boolean {
        if (!android.provider.Settings.canDrawOverlays(this)) {
            android.widget.Toast.makeText(this, "请先授予悬浮窗权限", android.widget.Toast.LENGTH_SHORT).show()
            val intent = Intent(
                android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                android.net.Uri.parse("package:$packageName")
            )
            startActivity(intent)
            return false
        }

        val a11yEnabled = com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.value
        if (!a11yEnabled) {
            android.widget.Toast.makeText(this, "悬浮窗功能需要开启无障碍服务", android.widget.Toast.LENGTH_SHORT).show()
            val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            return false
        }
        return true
    }

    private fun syncServiceState() {
        val accessibility = com.suzu.test.accessibility.TestAccessibilityService.instance
        if (accessibility != null) {
            accessibility.syncBallState()
        }
    }

    private fun setupMasterSwitch() {
        binding.swMasterSwitch.isChecked = FloatingBallConfig.isFloatingMasterEnabled(this)
        binding.swMasterSwitch.setOnClickListener {
            val targetState = binding.swMasterSwitch.isChecked
            if (targetState) {
                if (!checkPermissionAndServices()) {
                    binding.swMasterSwitch.isChecked = false
                    return@setOnClickListener
                }
            }

            FloatingBallConfig.setFloatingMasterEnabled(this, targetState)
            syncServiceState()
            updateSectionEnableStates()
            updateSectionCollapseStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "悬浮窗功能总开关变更为: $targetState")
        }
    }

    private fun setupFloatingWindowSection() {
        binding.swEdgeGestureEnabled.isChecked = FloatingBallConfig.isEdgeGestureEnabled(this)
        binding.swEdgeGestureEnabled.setOnClickListener {
            val targetState = binding.swEdgeGestureEnabled.isChecked
            if (targetState) {
                if (!checkPermissionAndServices()) {
                    binding.swEdgeGestureEnabled.isChecked = false
                    return@setOnClickListener
                }
            }

            FloatingBallConfig.setEdgeGestureEnabled(this, targetState)
            syncServiceState()
            updateSectionEnableStates()
            updateSectionCollapseStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "悬浮窗(手势区域)开关变更为: $targetState")
        }
    }

    private fun setupFloatingBallSection() {
        binding.swBallMasterSwitch.isChecked = FloatingBallConfig.isBallEnabled(this)
        binding.swBallMasterSwitch.setOnClickListener {
            val targetState = binding.swBallMasterSwitch.isChecked
            if (targetState) {
                if (!checkPermissionAndServices()) {
                    binding.swBallMasterSwitch.isChecked = false
                    return@setOnClickListener
                }
            }

            FloatingBallConfig.setBallEnabled(this, targetState)
            syncServiceState()
            updateSectionEnableStates()
            updateSectionCollapseStates()
            TestLog.i(MODULE, "悬浮球开关变更为: $targetState")
        }
    }

    private fun updateSectionEnableStates() {
        val masterEnabled = FloatingBallConfig.isFloatingMasterEnabled(this)
        val edgeEnabled = masterEnabled && FloatingBallConfig.isEdgeGestureEnabled(this)
        val ballEnabled = masterEnabled && FloatingBallConfig.isBallEnabled(this)

        val leftEnabled = edgeEnabled && (
            FloatingBallConfig.isEdgeLeftEnabled(this) ||
                FloatingBallConfig.isEdgeLeftLowerEnabled(this)
            )
        val rightEnabled = edgeEnabled && (
            FloatingBallConfig.isEdgeRightEnabled(this) ||
                FloatingBallConfig.isEdgeRightLowerEnabled(this)
            )

        binding.swEdgeGestureEnabled.isEnabled = masterEnabled
        binding.layoutSectionEdgeGesture.alpha = if (masterEnabled) 1.0f else 0.5f
        binding.swShowEdgeRegion.isEnabled = edgeEnabled

        binding.swEdgeLeftEnabled.isEnabled = edgeEnabled
        binding.swEdgeLeftLowerEnabled.isEnabled = edgeEnabled
        binding.itemEdgeLeftUp.isEnabled = leftEnabled
        binding.itemEdgeLeftDown.isEnabled = leftEnabled
        binding.itemEdgeLeftRight.isEnabled = leftEnabled

        binding.swEdgeRightEnabled.isEnabled = edgeEnabled
        binding.swEdgeRightLowerEnabled.isEnabled = edgeEnabled
        binding.itemEdgeRightUp.isEnabled = rightEnabled
        binding.itemEdgeRightDown.isEnabled = rightEnabled
        binding.itemEdgeRightLeft.isEnabled = rightEnabled

        binding.sbEdgeLeftUpperWidth.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeLeftEnabled(this)
        binding.sbEdgeLeftUpperDistance.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeLeftEnabled(this)
        binding.sbEdgeLeftLowerWidth.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeLeftLowerEnabled(this)
        binding.sbEdgeLeftLowerDistance.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeLeftLowerEnabled(this)
        binding.sbEdgeRightUpperWidth.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeRightEnabled(this)
        binding.sbEdgeRightUpperDistance.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeRightEnabled(this)
        binding.sbEdgeRightLowerWidth.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeRightLowerEnabled(this)
        binding.sbEdgeRightLowerDistance.isEnabled =
            edgeEnabled && FloatingBallConfig.isEdgeRightLowerEnabled(this)
        binding.sbEdgeSafetyDistance.isEnabled = edgeEnabled && (
            FloatingBallConfig.isEdgeLeftEnabled(this) ||
                FloatingBallConfig.isEdgeRightEnabled(this)
            )
        binding.sbEdgeLowerSafetyDistance.isEnabled = edgeEnabled && (
            FloatingBallConfig.isEdgeLeftLowerEnabled(this) ||
                FloatingBallConfig.isEdgeRightLowerEnabled(this)
            )

        binding.swBallMasterSwitch.isEnabled = masterEnabled
        binding.layoutSectionFloatingBall.alpha = if (masterEnabled) 1.0f else 0.5f
        binding.sbBallSize.isEnabled = ballEnabled
        binding.sbBallAlpha.isEnabled = ballEnabled
        binding.sbBallAnimDuration.isEnabled = ballEnabled
        binding.rbShapeCircle.isEnabled = ballEnabled
        binding.rbShapeRoundedRect.isEnabled = ballEnabled
        binding.rbShapeBorderless.isEnabled = ballEnabled
        binding.btnSelectBallImage.isEnabled = ballEnabled
        binding.btnResetBallImage.isEnabled = ballEnabled
        binding.swShowOnlyWithIme.isEnabled = ballEnabled

        binding.layoutSectionSearchBar.alpha = if (masterEnabled) 1.0f else 0.5f
        binding.sbSearchBarTopMargin.isEnabled = masterEnabled
        binding.btnResetSearchBarTopMargin.isEnabled = masterEnabled
        binding.btnTestSearchBar.isEnabled = masterEnabled
    }

    /**
     * 未开启时自动折叠属性面板：
     * - 手势区域属性需总开关 + 手势开关均开启才展开
     * - 悬浮球属性需总开关 + 悬浮球开关均开启才展开
     * - 预览块为独立区块：仅跟随总开关显示，不随悬浮球/手势折叠
     */
    private fun updateSectionCollapseStates() {
        val masterEnabled = FloatingBallConfig.isFloatingMasterEnabled(this)
        val edgeEnabled = masterEnabled && FloatingBallConfig.isEdgeGestureEnabled(this)
        val ballEnabled = masterEnabled && FloatingBallConfig.isBallEnabled(this)

        TransitionManager.beginDelayedTransition(
            binding.root as android.view.ViewGroup,
            ChangeBounds().apply { duration = 200 }
        )
        binding.layoutEdgeGestureContent.visibility =
            if (edgeEnabled) View.VISIBLE else View.GONE
        binding.layoutBallContent.visibility =
            if (ballEnabled) View.VISIBLE else View.GONE
        binding.layoutPreviewBlock.visibility =
            if (masterEnabled) View.VISIBLE else View.GONE
        updatePreviewContentVisibility()
    }

    /**
     * 独立预览区块内部可见性：
     * - 悬浮球示意仅在悬浮球开启时展示
     * - 手势区域示意图可见性由 updateEdgeRegionPreview() 控制
     * - 两者皆不可见时展示占位提示
     */
    private fun updatePreviewContentVisibility() {
        val ballEnabled = FloatingBallConfig.isFloatingMasterEnabled(this) &&
            FloatingBallConfig.isBallEnabled(this)
        binding.flPreviewContainer.visibility =
            if (ballEnabled) View.VISIBLE else View.GONE

        val gesturePreviewVisible =
            binding.edgeGestureRegionView.visibility == View.VISIBLE
        binding.tvPreviewPlaceholder.visibility =
            if (!ballEnabled && !gesturePreviewVisible) View.VISIBLE else View.GONE
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
        val ballShape = FloatingBallConfig.getBallShape(this)
        val density = resources.displayMetrics.density

        // 1. 设置预览背景（无边框模式使用棋盘格背景以凸显透明度）
        if (ballShape == FloatingBallConfig.SHAPE_BORDERLESS) {
            val cellPx = (10 * density).toInt()
            binding.flPreviewBackground.background = CheckerboardDrawable(cellPx)
        } else {
            binding.flPreviewBackground.background = ColorDrawable(Color.parseColor("#F0F0F0"))
        }

        // 2. 设置 ShapeableImageView 的裁切与 ScaleType
        when (ballShape) {
            FloatingBallConfig.SHAPE_ROUNDED_RECT -> {
                val radiusPx = 12f * density
                binding.ivPreviewSkin.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(radiusPx)
                    .build()
                binding.ivPreviewSkin.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            FloatingBallConfig.SHAPE_BORDERLESS -> {
                binding.ivPreviewSkin.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(0f)
                    .build()
                binding.ivPreviewSkin.scaleType = ImageView.ScaleType.FIT_CENTER
            }
            else -> { // SHAPE_CIRCLE
                binding.ivPreviewSkin.shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCornerSizes(RelativeCornerSize(0.5f))
                    .build()
                binding.ivPreviewSkin.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        // 3. 无贴图时的处理
        if (resourceId == null) {
            binding.tvCustomImageStatus.text = "当前外观: 默认纯色球"
            binding.ivPreviewSkin.visibility = View.GONE
            binding.ivHitRegionOverlay.visibility = View.GONE
            when (ballShape) {
                FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                FloatingBallConfig.SHAPE_BORDERLESS -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball_square)
                else -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
            }
            return
        }

        // 4. 有贴图时的异步加载
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
                when (ballShape) {
                    FloatingBallConfig.SHAPE_BORDERLESS -> binding.flPreviewContainer.background = null
                    FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                    else -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
                }

                binding.ivPreviewSkin.visibility = View.VISIBLE
                Glide.with(this@SettingsFloatingActivity)
                    .load(file)
                    .into(binding.ivPreviewSkin)

                // 无边框模式：生成命中区可视化遮罩（死区半透明压暗，数据源复用首帧 alpha 位图）
                if (ballShape == FloatingBallConfig.SHAPE_BORDERLESS) {
                    val overlayBmp = withContext(Dispatchers.IO) {
                        createHitRegionMask(file.absolutePath)
                    }
                    if (overlayBmp != null) {
                        binding.ivHitRegionOverlay.visibility = View.VISIBLE
                        binding.ivHitRegionOverlay.setImageBitmap(overlayBmp)
                    } else {
                        binding.ivHitRegionOverlay.visibility = View.GONE
                    }
                } else {
                    binding.ivHitRegionOverlay.visibility = View.GONE
                }
            } else {
                binding.tvCustomImageStatus.text = "当前外观: 默认纯色球"
                binding.ivPreviewSkin.visibility = View.GONE
                binding.ivHitRegionOverlay.visibility = View.GONE
                when (ballShape) {
                    FloatingBallConfig.SHAPE_ROUNDED_RECT -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball_rounded)
                    FloatingBallConfig.SHAPE_BORDERLESS -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball_square)
                    else -> binding.flPreviewContainer.setBackgroundResource(R.drawable.bg_floating_ball)
                }
            }
        }
    }

    /**
     * 生成命中区可视化遮罩位图（透明死区半透明压暗，有效命中区完全透明）
     */
    private fun createHitRegionMask(filePath: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(filePath, options)
            val maxEdge = maxOf(options.outWidth, options.outHeight)
            var inSampleSize = 1
            if (maxEdge > 256) {
                while ((maxEdge / (inSampleSize * 2)) >= 256) {
                    inSampleSize *= 2
                }
            }
            val decodeOptions = BitmapFactory.Options().apply {
                this.inSampleSize = inSampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val srcBmp = BitmapFactory.decodeFile(filePath, decodeOptions) ?: return null
            val maskBmp = Bitmap.createBitmap(srcBmp.width, srcBmp.height, Bitmap.Config.ARGB_8888)
            val pixels = IntArray(srcBmp.width * srcBmp.height)
            srcBmp.getPixels(pixels, 0, srcBmp.width, 0, 0, srcBmp.width, srcBmp.height)

            for (i in pixels.indices) {
                val alpha = Color.alpha(pixels[i])
                if (alpha < 24) {
                    // 死区：用半透明黑色 (40% alpha) 压暗展示
                    pixels[i] = Color.argb(100, 0, 0, 0)
                } else {
                    // 有效触发区：完全透明
                    pixels[i] = Color.TRANSPARENT
                }
            }
            maskBmp.setPixels(pixels, 0, srcBmp.width, 0, 0, srcBmp.width, srcBmp.height)
            srcBmp.recycle()
            maskBmp
        } catch (e: Exception) {
            null
        }
    }

    private fun checkA11yStatus() {
        val isA11yEnabled = com.suzu.test.accessibility.AccessibilityStateMonitor.isEnabled.value
        binding.tvA11yWarning.visibility =
            if (isA11yEnabled) View.GONE else View.VISIBLE
    }

    private fun setupAppFilter() {
        binding.swShowOnlyWithIme.isChecked = FloatingBallConfig.isShowOnlyWithImeEnabled(this)
        binding.swShowOnlyWithIme.setOnCheckedChangeListener { _, isChecked ->
            FloatingBallConfig.setShowOnlyWithImeEnabled(this, isChecked)
            TestLog.i(MODULE, "仅在弹出键盘时显示开关: $isChecked")
        }
    }

    private fun setupEdgeGestureSettings() {
        binding.swShowEdgeRegion.setOnCheckedChangeListener { _, show ->
            FloatingBallConfig.setShowEdgeRegionEnabled(this, show)
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "显示边缘手势判定区域: $show")
        }

        binding.swEdgeLeftEnabled.setOnCheckedChangeListener { _, enabled ->
            FloatingBallConfig.setEdgeLeftEnabled(this, enabled)
            syncServiceState()
            updateSectionEnableStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "启用左上区域变更: $enabled")
        }

        binding.swEdgeLeftLowerEnabled.setOnCheckedChangeListener { _, enabled ->
            FloatingBallConfig.setEdgeLeftLowerEnabled(this, enabled)
            syncServiceState()
            updateSectionEnableStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "启用左下输入法覆盖区域变更: $enabled")
        }

        binding.swEdgeRightEnabled.setOnCheckedChangeListener { _, enabled ->
            FloatingBallConfig.setEdgeRightEnabled(this, enabled)
            syncServiceState()
            updateSectionEnableStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "启用右上区域变更: $enabled")
        }

        binding.swEdgeRightLowerEnabled.setOnCheckedChangeListener { _, enabled ->
            FloatingBallConfig.setEdgeRightLowerEnabled(this, enabled)
            syncServiceState()
            updateSectionEnableStates()
            updateEdgeRegionPreview()
            TestLog.i(MODULE, "启用右下输入法覆盖区域变更: $enabled")
        }

        val directionActionItems = listOf(
            Triple(binding.itemEdgeLeftUp, binding.tvEdgeLeftUpAction, FloatingBallConfig.KEY_EDGE_LEFT_UP_ACTION to "左侧上滑动作"),
            Triple(binding.itemEdgeLeftDown, binding.tvEdgeLeftDownAction, FloatingBallConfig.KEY_EDGE_LEFT_DOWN_ACTION to "左侧下滑动作"),
            Triple(binding.itemEdgeLeftRight, binding.tvEdgeLeftRightAction, FloatingBallConfig.KEY_EDGE_LEFT_RIGHT_ACTION to "左侧向内滑动作 (右滑)"),
            Triple(binding.itemEdgeRightUp, binding.tvEdgeRightUpAction, FloatingBallConfig.KEY_EDGE_RIGHT_UP_ACTION to "右侧上滑动作"),
            Triple(binding.itemEdgeRightDown, binding.tvEdgeRightDownAction, FloatingBallConfig.KEY_EDGE_RIGHT_DOWN_ACTION to "右侧下滑动作"),
            Triple(binding.itemEdgeRightLeft, binding.tvEdgeRightLeftAction, FloatingBallConfig.KEY_EDGE_RIGHT_LEFT_ACTION to "右侧向内滑动作 (左滑)")
        )
        directionActionItems.forEach { (item, tv, pair) ->
            val (key, title) = pair
            item.setOnClickListener {
                showGestureActionDialog(title, key, tv)
            }
        }

        setupEdgeRegionAdjuster(
            binding.sbEdgeLeftUpperWidth,
            binding.tvEdgeLeftUpperWidthValue,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_WIDTH_DP,
            isWidth = true
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeLeftUpperDistance,
            binding.tvEdgeLeftUpperDistanceValue,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_DISTANCE_DP,
            isWidth = false
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeLeftLowerWidth,
            binding.tvEdgeLeftLowerWidthValue,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_WIDTH_DP,
            isWidth = true
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeLeftLowerDistance,
            binding.tvEdgeLeftLowerDistanceValue,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_DISTANCE_DP,
            isWidth = false
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeRightUpperWidth,
            binding.tvEdgeRightUpperWidthValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_WIDTH_DP,
            isWidth = true
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeRightUpperDistance,
            binding.tvEdgeRightUpperDistanceValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_DISTANCE_DP,
            isWidth = false
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeRightLowerWidth,
            binding.tvEdgeRightLowerWidthValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_WIDTH_DP,
            isWidth = true
        )
        setupEdgeRegionAdjuster(
            binding.sbEdgeRightLowerDistance,
            binding.tvEdgeRightLowerDistanceValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_DISTANCE_DP,
            isWidth = false
        )

        binding.sbEdgeSafetyDistance.max =
            FloatingBallConfig.MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX -
                FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        val currentSafetyDistance = FloatingBallConfig.getEdgeKeyboardSafetyDistancePx(this)
        binding.sbEdgeSafetyDistance.progress = currentSafetyDistance - FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        binding.tvEdgeSafetyDistanceValue.text = "$currentSafetyDistance px"
        binding.sbEdgeSafetyDistance.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val safetyDistance = progress + FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
                binding.tvEdgeSafetyDistanceValue.text = "$safetyDistance px"
                FloatingBallConfig.setEdgeKeyboardSafetyDistancePx(this@SettingsFloatingActivity, safetyDistance)
                updateEdgeRegionPreview()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        binding.sbEdgeLowerSafetyDistance.max =
            FloatingBallConfig.MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX -
                FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
        val currentLowerSafetyDistance =
            FloatingBallConfig.getEdgeLowerKeyboardSafetyDistancePx(this)
        binding.sbEdgeLowerSafetyDistance.progress =
            currentLowerSafetyDistance -
                FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
        binding.tvEdgeLowerSafetyDistanceValue.text = "$currentLowerSafetyDistance px"
        binding.sbEdgeLowerSafetyDistance.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val safetyDistance =
                        progress +
                            FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
                    binding.tvEdgeLowerSafetyDistanceValue.text = "$safetyDistance px"
                    FloatingBallConfig.setEdgeLowerKeyboardSafetyDistancePx(
                        this@SettingsFloatingActivity,
                        safetyDistance
                    )
                    updateEdgeRegionPreview()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            }
        )

        updateEdgeGestureUI()
    }

    private fun setupEdgeRegionAdjuster(
        seekBar: SeekBar,
        valueView: TextView,
        key: String,
        isWidth: Boolean
    ) {
        val minimum = if (isWidth) {
            FloatingBallConfig.MIN_EDGE_WIDTH_DP
        } else {
            FloatingBallConfig.MIN_EDGE_TRIGGER_DISTANCE_DP
        }
        val maximum = if (isWidth) {
            FloatingBallConfig.MAX_EDGE_WIDTH_DP
        } else {
            FloatingBallConfig.MAX_EDGE_TRIGGER_DISTANCE_DP
        }
        seekBar.max = maximum - minimum
        val current = if (isWidth) {
            FloatingBallConfig.getEdgeRegionWidthDp(this, key)
        } else {
            FloatingBallConfig.getEdgeRegionTriggerDistanceDp(this, key)
        }
        seekBar.progress = current - minimum
        valueView.text = "$current dp"
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val value = progress + minimum
                valueView.text = "$value dp"
                if (isWidth) {
                    FloatingBallConfig.setEdgeRegionWidthDp(
                        this@SettingsFloatingActivity,
                        key,
                        value
                    )
                } else {
                    FloatingBallConfig.setEdgeRegionTriggerDistanceDp(
                        this@SettingsFloatingActivity,
                        key,
                        value
                    )
                }
                updateEdgeRegionPreview()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    private fun updateEdgeRegionAdjusterValue(
        seekBar: SeekBar,
        valueView: TextView,
        key: String,
        isWidth: Boolean
    ) {
        val minimum = if (isWidth) {
            FloatingBallConfig.MIN_EDGE_WIDTH_DP
        } else {
            FloatingBallConfig.MIN_EDGE_TRIGGER_DISTANCE_DP
        }
        val maximum = if (isWidth) {
            FloatingBallConfig.MAX_EDGE_WIDTH_DP
        } else {
            FloatingBallConfig.MAX_EDGE_TRIGGER_DISTANCE_DP
        }
        seekBar.max = maximum - minimum
        val value = if (isWidth) {
            FloatingBallConfig.getEdgeRegionWidthDp(this, key)
        } else {
            FloatingBallConfig.getEdgeRegionTriggerDistanceDp(this, key)
        }
        seekBar.progress = value - minimum
        valueView.text = "$value dp"
    }

    private fun updateGestureActionView(tv: TextView, action: FloatingBallConfig.EdgeGestureAction) {
        tv.text = action.title
        if (action == FloatingBallConfig.EdgeGestureAction.NONE) {
            tv.setTextColor(Color.parseColor("#999999"))
        } else {
            tv.setTextColor(Color.parseColor("#007AFF"))
        }
    }

    private fun showGestureActionDialog(title: String, actionKey: String, tvValue: TextView) {
        val actions = FloatingBallConfig.EdgeGestureAction.entries.toTypedArray()
        val items = actions.map { it.title }.toTypedArray()
        val currentAction = FloatingBallConfig.getEdgeGestureAction(this, actionKey)
        val currentIndex = actions.indexOf(currentAction).coerceAtLeast(0)

        AlertDialog.Builder(this)
            .setTitle(title)
            .setSingleChoiceItems(items, currentIndex) { dialog, which ->
                val selectedAction = actions[which]
                FloatingBallConfig.setEdgeGestureAction(this, actionKey, selectedAction)
                updateGestureActionView(tvValue, selectedAction)
                updateEdgeRegionPreview()
                TestLog.i(MODULE, "边缘手势动作变更: key=$actionKey, action=${selectedAction.name}")
                dialog.dismiss()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun updateEdgeGestureUI() {
        binding.swEdgeGestureEnabled.isChecked =
            FloatingBallConfig.isEdgeGestureEnabled(this)
        binding.swEdgeLeftEnabled.isChecked =
            FloatingBallConfig.isEdgeLeftEnabled(this)
        binding.swEdgeLeftLowerEnabled.isChecked =
            FloatingBallConfig.isEdgeLeftLowerEnabled(this)
        binding.swEdgeRightEnabled.isChecked =
            FloatingBallConfig.isEdgeRightEnabled(this)
        binding.swEdgeRightLowerEnabled.isChecked =
            FloatingBallConfig.isEdgeRightLowerEnabled(this)

        updateGestureActionView(
            binding.tvEdgeLeftUpAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_LEFT_UP_ACTION)
        )
        updateGestureActionView(
            binding.tvEdgeLeftDownAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_LEFT_DOWN_ACTION)
        )
        updateGestureActionView(
            binding.tvEdgeLeftRightAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_LEFT_RIGHT_ACTION)
        )
        updateGestureActionView(
            binding.tvEdgeRightUpAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_RIGHT_UP_ACTION)
        )
        updateGestureActionView(
            binding.tvEdgeRightDownAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_RIGHT_DOWN_ACTION)
        )
        updateGestureActionView(
            binding.tvEdgeRightLeftAction,
            FloatingBallConfig.getEdgeGestureAction(this, FloatingBallConfig.KEY_EDGE_RIGHT_LEFT_ACTION)
        )

        updateEdgeRegionAdjusterValue(
            binding.sbEdgeLeftUpperWidth,
            binding.tvEdgeLeftUpperWidthValue,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_WIDTH_DP,
            isWidth = true
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeLeftUpperDistance,
            binding.tvEdgeLeftUpperDistanceValue,
            FloatingBallConfig.KEY_EDGE_LEFT_UPPER_DISTANCE_DP,
            isWidth = false
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeLeftLowerWidth,
            binding.tvEdgeLeftLowerWidthValue,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_WIDTH_DP,
            isWidth = true
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeLeftLowerDistance,
            binding.tvEdgeLeftLowerDistanceValue,
            FloatingBallConfig.KEY_EDGE_LEFT_LOWER_DISTANCE_DP,
            isWidth = false
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeRightUpperWidth,
            binding.tvEdgeRightUpperWidthValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_WIDTH_DP,
            isWidth = true
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeRightUpperDistance,
            binding.tvEdgeRightUpperDistanceValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_DISTANCE_DP,
            isWidth = false
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeRightLowerWidth,
            binding.tvEdgeRightLowerWidthValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_WIDTH_DP,
            isWidth = true
        )
        updateEdgeRegionAdjusterValue(
            binding.sbEdgeRightLowerDistance,
            binding.tvEdgeRightLowerDistanceValue,
            FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_DISTANCE_DP,
            isWidth = false
        )

        binding.sbEdgeSafetyDistance.max =
            FloatingBallConfig.MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX -
                FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        val safetyDistance = FloatingBallConfig.getEdgeKeyboardSafetyDistancePx(this)
        binding.sbEdgeSafetyDistance.progress =
            safetyDistance - FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        binding.tvEdgeSafetyDistanceValue.text = "$safetyDistance px"

        binding.sbEdgeLowerSafetyDistance.max =
            FloatingBallConfig.MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX -
                FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
        val lowerSafetyDistance =
            FloatingBallConfig.getEdgeLowerKeyboardSafetyDistancePx(this)
        binding.sbEdgeLowerSafetyDistance.progress =
            lowerSafetyDistance -
                FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX
        binding.tvEdgeLowerSafetyDistanceValue.text = "$lowerSafetyDistance px"

        binding.swShowEdgeRegion.isChecked =
            FloatingBallConfig.isShowEdgeRegionEnabled(this)
        updateEdgeRegionPreview()
    }

    private fun updateEdgeRegionPreview() {
        val gestureEnabled =
            FloatingBallConfig.isFloatingMasterEnabled(this) &&
                FloatingBallConfig.isEdgeGestureEnabled(this)
        val showPreview =
            gestureEnabled && FloatingBallConfig.isShowEdgeRegionEnabled(this)
        binding.edgeGestureRegionView.visibility =
            if (showPreview) View.VISIBLE else View.GONE
        updatePreviewContentVisibility()

        binding.edgeGestureRegionView.update(
            leftUpperWidthDp = FloatingBallConfig.getEdgeRegionWidthDp(
                this, FloatingBallConfig.KEY_EDGE_LEFT_UPPER_WIDTH_DP
            ),
            leftUpperDistanceDp = FloatingBallConfig.getEdgeRegionTriggerDistanceDp(
                this, FloatingBallConfig.KEY_EDGE_LEFT_UPPER_DISTANCE_DP
            ),
            leftUpperEnabled = FloatingBallConfig.isEdgeLeftEnabled(this),
            leftLowerWidthDp = FloatingBallConfig.getEdgeRegionWidthDp(
                this, FloatingBallConfig.KEY_EDGE_LEFT_LOWER_WIDTH_DP
            ),
            leftLowerDistanceDp = FloatingBallConfig.getEdgeRegionTriggerDistanceDp(
                this, FloatingBallConfig.KEY_EDGE_LEFT_LOWER_DISTANCE_DP
            ),
            leftLowerEnabled = FloatingBallConfig.isEdgeLeftLowerEnabled(this),
            rightUpperWidthDp = FloatingBallConfig.getEdgeRegionWidthDp(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_WIDTH_DP
            ),
            rightUpperDistanceDp = FloatingBallConfig.getEdgeRegionTriggerDistanceDp(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_UPPER_DISTANCE_DP
            ),
            rightUpperEnabled = FloatingBallConfig.isEdgeRightEnabled(this),
            rightLowerWidthDp = FloatingBallConfig.getEdgeRegionWidthDp(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_WIDTH_DP
            ),
            rightLowerDistanceDp = FloatingBallConfig.getEdgeRegionTriggerDistanceDp(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_LOWER_DISTANCE_DP
            ),
            rightLowerEnabled = FloatingBallConfig.isEdgeRightLowerEnabled(this),
            upperSafetyDistancePx =
                FloatingBallConfig.getEdgeKeyboardSafetyDistancePx(this),
            lowerSafetyDistancePx =
                FloatingBallConfig.getEdgeLowerKeyboardSafetyDistancePx(this),
            gestureEnabled = gestureEnabled,
            leftUpEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_LEFT_UP_ENABLED
            ),
            leftDownEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_LEFT_DOWN_ENABLED
            ),
            leftRightEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_LEFT_RIGHT_ENABLED
            ),
            rightUpEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_UP_ENABLED
            ),
            rightDownEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_DOWN_ENABLED
            ),
            rightLeftEnabled = FloatingBallConfig.isEdgeGestureDirectionEnabled(
                this, FloatingBallConfig.KEY_EDGE_RIGHT_LEFT_ENABLED
            )
        )
    }

    private fun updateFloatingAdjusterUI() {
        val minSize = FloatingBallConfig.MIN_BALL_SIZE_DP
        val maxSize = FloatingBallConfig.MAX_BALL_SIZE_DP
        binding.sbBallSize.max = maxSize - minSize
        val currentSize = FloatingBallConfig.getSizeDp(this)
        binding.sbBallSize.progress = currentSize - minSize
        binding.tvSizeValue.text = "$currentSize dp"

        val minAlpha = FloatingBallConfig.MIN_BALL_ALPHA
        val maxAlpha = FloatingBallConfig.MAX_BALL_ALPHA
        binding.sbBallAlpha.max = maxAlpha - minAlpha
        val currentAlpha = FloatingBallConfig.getAlphaPercent(this)
        binding.sbBallAlpha.progress = currentAlpha - minAlpha
        binding.tvAlphaValue.text = "$currentAlpha %"

        val minDuration = FloatingBallConfig.MIN_ANIM_DURATION_MS
        val maxDuration = FloatingBallConfig.MAX_ANIM_DURATION_MS
        val maxDurationSteps = (maxDuration - minDuration) / 20
        binding.sbBallAnimDuration.max = maxDurationSteps
        val currentDuration = FloatingBallConfig.getAnimDurationMs(this)
        binding.sbBallAnimDuration.progress = (currentDuration / 20).coerceIn(0, maxDurationSteps)
        binding.tvAnimDurationValue.text = if (currentDuration == 0) "0 ms (关闭动画)" else "$currentDuration ms"

        updatePreview(currentSize, currentAlpha)
    }

    private fun setupFloatingAdjusters() {
        updateFloatingAdjusterUI()

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
                val durationMs = (progress * 20).coerceIn(
                    FloatingBallConfig.MIN_ANIM_DURATION_MS,
                    FloatingBallConfig.MAX_ANIM_DURATION_MS
                )
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

    private fun setupSearchBarSection() {
        updateSearchBarUI()

        binding.sbSearchBarTopMargin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val marginDp = progress + FloatingBallConfig.MIN_SEARCH_BAR_TOP_MARGIN_DP
                binding.tvSearchBarTopMarginValue.text = "$marginDp dp"
                FloatingBallConfig.setSearchBarTopMarginDp(this@SettingsFloatingActivity, marginDp)
                TestLog.i(MODULE, "修改搜索框顶边距: $marginDp dp")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        binding.btnResetSearchBarTopMargin.setOnClickListener {
            val defaultMargin = FloatingBallConfig.getDefaultSearchBarTopMarginDp(this)
            FloatingBallConfig.setSearchBarTopMarginDp(this, defaultMargin)
            updateSearchBarUI()
            TestLog.i(MODULE, "已恢复搜索框默认顶边距: $defaultMargin dp")
        }

        binding.btnTestSearchBar.setOnClickListener {
            if (!checkPermissionAndServices()) return@setOnClickListener
            val accessibility = com.suzu.test.accessibility.TestAccessibilityService.instance
            if (accessibility == null) {
                android.widget.Toast.makeText(this, "无障碍服务未启动", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            accessibility.syncBallState()
            accessibility.showSearchBar()
        }
    }

    private fun updateSearchBarUI() {
        val currentMargin = FloatingBallConfig.getSearchBarTopMarginDp(this)
        binding.sbSearchBarTopMargin.progress =
            currentMargin - FloatingBallConfig.MIN_SEARCH_BAR_TOP_MARGIN_DP
        binding.tvSearchBarTopMarginValue.text = "$currentMargin dp"
    }
}
