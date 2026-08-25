package com.suzu.test.ui.settings

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.suzu.test.databinding.ActivitySettingsAppearanceBinding
import com.suzu.test.ime.ImageAdapter
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.data.KeyboardDataSource
import com.suzu.test.ime.theme.KeyboardTheme
import com.suzu.test.log.TestLog
import kotlinx.coroutines.launch

class SettingsAppearanceActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "SettingsAppearance"
    }

    private lateinit var binding: ActivitySettingsAppearanceBinding
    private var previewAdapter: ImageAdapter? = null
    private var previewLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsAppearanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupThemeSelector()
        setupKeyboardAdjusters()
        setupPreview()
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
        val theme = KeyboardTheme.current(this)
        binding.flPreviewContainer.setBackgroundColor(theme.gridBg)
        previewAdapter?.notifyDataSetChanged()
    }

    private fun setupKeyboardAdjusters() {
        val currentHeight = KeyboardConfig.getGridHeightDp(this)
        binding.sbKeyboardHeight.progress = currentHeight - KeyboardConfig.MIN_GRID_HEIGHT_DP
        binding.tvHeightValue.text = "$currentHeight dp"

        val currentSpan = KeyboardConfig.getSpanCount(this)
        binding.sbKeyboardSpan.progress = currentSpan - KeyboardConfig.MIN_SPAN_COUNT
        binding.tvSpanValue.text = "$currentSpan 列"

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
