package com.suzu.test.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.suzu.test.R
import com.suzu.test.databinding.ActivitySettingsAppearanceBinding
import com.suzu.test.ime.ImageAdapter
import com.suzu.test.ime.TestImageIME
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.ime.theme.KeyboardWallpaper
import com.suzu.test.ime.theme.WallpaperDrawable
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class SettingsAppearanceActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "SettingsAppearance"
    }

    private lateinit var binding: ActivitySettingsAppearanceBinding
    private var previewAdapter: ImageAdapter? = null
    private var previewLayoutManager: GridLayoutManager? = null
    private var wallpaperFrame: KeyboardWallpaper.Frame? = null
    private var wallpaperBusy = false
    private val wallpaperPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            changeWallpaper(R.string.ime_wallpaper_import_failed) { KeyboardWallpaper.replace(applicationContext, uri) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsAppearanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val navBar = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars())
            val baseBottom = (12 * resources.displayMetrics.density).toInt()
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, baseBottom + navBar.bottom)
            insets
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnNavShareWhitelist.setOnClickListener {
            startActivity(Intent(this, ShareWhitelistActivity::class.java))
        }

        setupThemeSelector()
        setupKeyboardAdjusters()
        setupRecentTabSwitch()
        setupAllTabSwitch()
        setupTabDropdownSwitch()
        setupDropdownSettings()
        setupExitButtonSwitch()
        setupSendFailureForwardSwitch()
        setupPreview()
        setupWallpaper()
    }

    private fun setupRecentTabSwitch() {
        binding.swShowRecentTab.isChecked = KeyboardConfig.isRecentTabEnabled(this)
        binding.swShowRecentTab.setOnCheckedChangeListener { _, isChecked ->
            KeyboardConfig.setRecentTabEnabled(this, isChecked)
            TestLog.i(MODULE, "切换显示「常用」开关: $isChecked")
        }
    }

    private fun setupAllTabSwitch() {
        binding.swShowAllTab.isChecked = KeyboardConfig.isAllTabEnabled(this)
        binding.swShowAllTab.setOnCheckedChangeListener { _, isChecked ->
            KeyboardConfig.setAllTabEnabled(this, isChecked)
            TestLog.i(MODULE, "切换显示「全部表情」开关: $isChecked")
        }
    }

    private fun setupTabDropdownSwitch() {
        val enabled = KeyboardConfig.isTabDropdownEnabled(this)
        binding.swShowTabDropdown.isChecked = enabled
        binding.layoutDropdownOptions.visibility = if (enabled) View.VISIBLE else View.GONE
        binding.swShowTabDropdown.setOnCheckedChangeListener { _, isChecked ->
            KeyboardConfig.setTabDropdownEnabled(this, isChecked)
            binding.layoutDropdownOptions.visibility = if (isChecked) View.VISIBLE else View.GONE
            TestImageIME.instance?.applyKeyboardConfigLayout()
            TestLog.i(MODULE, "切换显示「展开收藏夹」开关: $isChecked")
        }
    }

    private fun setupExitButtonSwitch() {
        binding.swShowExitButton.isChecked = KeyboardConfig.isExitButtonEnabled(this)
        binding.swShowExitButton.setOnCheckedChangeListener { _, isChecked ->
            KeyboardConfig.setExitButtonEnabled(this, isChecked)
            TestImageIME.instance?.applyKeyboardConfigLayout()
            TestLog.i(MODULE, "切换显示「收起键盘」开关: $isChecked")
        }
    }

    private fun setupSendFailureForwardSwitch() {
        binding.swSendFailureForward.isChecked = KeyboardConfig.isSendFailureForwardEnabled(this)
        binding.swSendFailureForward.setOnCheckedChangeListener { _, isChecked ->
            KeyboardConfig.setSendFailureForwardEnabled(this, isChecked)
            TestLog.i(MODULE, "切换「IME 发送失败拉起转发」开关: $isChecked")
        }
    }

    private fun setupDropdownSettings() {
        when (KeyboardConfig.getDropdownIconStyle(this)) {
            KeyboardConfig.DROPDOWN_ICON_MENU -> binding.rbDropdownIconMenu.isChecked = true
            KeyboardConfig.DROPDOWN_ICON_MORE -> binding.rbDropdownIconMore.isChecked = true
            else -> binding.rbDropdownIconArrow.isChecked = true
        }

        binding.rgDropdownIcon.setOnCheckedChangeListener { _, checkedId ->
            val style = when (checkedId) {
                binding.rbDropdownIconMenu.id -> KeyboardConfig.DROPDOWN_ICON_MENU
                binding.rbDropdownIconMore.id -> KeyboardConfig.DROPDOWN_ICON_MORE
                else -> KeyboardConfig.DROPDOWN_ICON_ARROW
            }
            KeyboardConfig.setDropdownIconStyle(this, style)
            TestImageIME.instance?.applyKeyboardConfigLayout()
            TestLog.i(MODULE, "切换展开分类按钮图标: $style")
        }

        if (KeyboardConfig.getDropdownPosition(this) == KeyboardConfig.DROPDOWN_POSITION_RIGHT) {
            binding.rbDropdownPosRight.isChecked = true
        } else {
            binding.rbDropdownPosLeft.isChecked = true
        }

        binding.rgDropdownPosition.setOnCheckedChangeListener { _, checkedId ->
            val pos = if (checkedId == binding.rbDropdownPosRight.id) {
                KeyboardConfig.DROPDOWN_POSITION_RIGHT
            } else {
                KeyboardConfig.DROPDOWN_POSITION_LEFT
            }
            KeyboardConfig.setDropdownPosition(this, pos)
            TestImageIME.instance?.applyKeyboardConfigLayout()
            TestLog.i(MODULE, "切换展开分类按钮位置: $pos")
        }
    }

    private fun setupThemeSelector() {
        val currentTheme = KeyboardTheme.current(this)
        if (currentTheme.isDark) {
            binding.rbThemeDark.isChecked = true
        } else {
            binding.rbThemeLight.isChecked = true
        }

        binding.rgKeyboardTheme.setOnCheckedChangeListener { _, checkedId ->
            val themeKey = if (checkedId == binding.rbThemeLight.id) {
                KeyboardTheme.THEME_LIGHT
            } else {
                KeyboardTheme.THEME_DARK
            }
            KeyboardTheme.setTheme(this, themeKey)
            TestLog.i(MODULE, "切换键盘主题: $themeKey")
            updatePreviewTheme()
        }
    }

    private fun updatePreviewTheme() {
        updatePreviewWallpaper()
        previewAdapter?.notifyDataSetChanged()
    }

    private fun updatePreviewWallpaper() {
        val theme = KeyboardTheme.current(this)
        val frame = wallpaperFrame
        if (frame == null) {
            binding.flPreviewContainer.setBackgroundColor(theme.gridBg)
        } else {
            binding.flPreviewContainer.background = WallpaperDrawable(frame, theme.gridBg)
        }
    }

    private fun setupWallpaper() {
        val tone = KeyboardWallpaper.tone(this)
        binding.sbWallpaperBlur.max = KeyboardWallpaper.MAX_BLUR
        bindWallpaperSlider(binding.sbWallpaperBlur, binding.tvWallpaperBlurValue, tone.blur, R.string.ime_wallpaper_blur_level) {
            KeyboardWallpaper.setBlur(this, it)
        }
        binding.sbWallpaperBrightness.max = KeyboardWallpaper.MAX_BRIGHTNESS
        binding.sbWallpaperBrightness.min = -KeyboardWallpaper.MAX_BRIGHTNESS
        bindWallpaperSlider(
            binding.sbWallpaperBrightness, binding.tvWallpaperBrightnessValue, tone.brightness,
            R.string.ime_wallpaper_brightness_level
        ) {
            KeyboardWallpaper.setBrightness(this, it)
        }
        binding.btnChooseWallpaper.setOnClickListener {
            wallpaperPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnRemoveWallpaper.setOnClickListener {
            changeWallpaper(R.string.ime_wallpaper_remove_failed) { KeyboardWallpaper.remove(applicationContext) }
        }
        lifecycleScope.launch {
            KeyboardWallpaper.frames(this@SettingsAppearanceActivity).collect { frame ->
                wallpaperFrame = frame
                previewAdapter?.setWallpaperVisible(frame != null)
                updateWallpaperControls()
                updatePreviewWallpaper()
            }
        }
    }

    private fun bindWallpaperSlider(slider: SeekBar, value: TextView, initial: Int, format: Int, save: (Int) -> Unit) {
        slider.progress = initial
        value.text = getString(format, initial)
        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                value.text = getString(format, progress)
                if (fromUser) save(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateWallpaperControls() {
        val hasImage = wallpaperFrame != null
        binding.btnChooseWallpaper.isEnabled = !wallpaperBusy
        binding.btnRemoveWallpaper.isEnabled = !wallpaperBusy && hasImage
        binding.layoutWallpaperTone.visibility = if (hasImage) View.VISIBLE else View.GONE
    }

    private fun changeWallpaper(errorMessage: Int, change: suspend () -> Unit) {
        if (wallpaperBusy) return
        wallpaperBusy = true
        updateWallpaperControls()
        lifecycleScope.launch {
            try {
                change()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (failure: Exception) {
                TestLog.e(MODULE, "更新键盘背景失败", failure)
                Toast.makeText(this@SettingsAppearanceActivity, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                wallpaperBusy = false
                updateWallpaperControls()
            }
        }
    }

    private fun setupKeyboardAdjusters() {
        val currentHeight = KeyboardConfig.getGridHeightDp(this)
        binding.sbKeyboardHeight.progress = currentHeight - KeyboardConfig.MIN_GRID_HEIGHT_DP
        binding.tvHeightValue.text = "$currentHeight dp"

        val currentSpan = KeyboardConfig.getSpanCount(this)
        binding.sbKeyboardSpan.progress = currentSpan - KeyboardConfig.MIN_SPAN_COUNT
        binding.tvSpanValue.text = "$currentSpan 列"

        val currentTabSize = KeyboardConfig.getTabIconSizeDp(this)
        binding.sbKeyboardTabSize.progress = currentTabSize - KeyboardConfig.MIN_TAB_ICON_SIZE_DP
        binding.tvTabSizeValue.text = "$currentTabSize dp"

        binding.sbKeyboardHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val heightDp = progress + KeyboardConfig.MIN_GRID_HEIGHT_DP
                binding.tvHeightValue.text = "$heightDp dp"
                KeyboardConfig.setGridHeightDp(this@SettingsAppearanceActivity, heightDp)
                updatePreviewLayout(heightDp, null)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.sbKeyboardSpan.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val span = progress + KeyboardConfig.MIN_SPAN_COUNT
                binding.tvSpanValue.text = "$span 列"
                KeyboardConfig.setSpanCount(this@SettingsAppearanceActivity, span)
                updatePreviewLayout(null, span)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.sbKeyboardTabSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val tabSize = progress + KeyboardConfig.MIN_TAB_ICON_SIZE_DP
                binding.tvTabSizeValue.text = "$tabSize dp"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val tabSize = (seekBar?.progress ?: 0) + KeyboardConfig.MIN_TAB_ICON_SIZE_DP
                KeyboardConfig.setTabIconSizeDp(this@SettingsAppearanceActivity, tabSize)
                TestLog.i(MODULE, "已保存分类栏图标大小: $tabSize dp")
            }
        })
    }

    private fun setupPreview() {
        val initialSpan = KeyboardConfig.getSpanCount(this)
        val initialHeight = KeyboardConfig.getGridHeightDp(this)

        val glm = GridLayoutManager(this, initialSpan)
        previewLayoutManager = glm
        binding.rvSettingsPreview.layoutManager = glm

        val adapter = ImageAdapter(
            onItemClick = {},
            onItemLongClick = { _, _ -> }
        )
        previewAdapter = adapter
        binding.rvSettingsPreview.adapter = adapter

        updatePreviewLayout(initialHeight, initialSpan)
        updatePreviewTheme()

        lifecycleScope.launch {
            val dataSource = KeyboardDataSource(this@SettingsAppearanceActivity)
            val list = dataSource.loadResources("ALL").take(16)
            adapter.submitList(list)
        }
    }

    private fun updatePreviewLayout(heightDp: Int?, spanCount: Int?) {
        heightDp?.let {
            val heightPx = (it * resources.displayMetrics.density).toInt()
            val lp = binding.flPreviewContainer.layoutParams
            lp.height = heightPx
            binding.flPreviewContainer.layoutParams = lp
            binding.flPreviewContainer.requestLayout()
        }
        spanCount?.let {
            previewLayoutManager?.spanCount = it
        }
    }
}
