package com.suzu.test.ui.settings

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suzu.test.R
import com.suzu.test.databinding.ActivityShareWhitelistBinding
import com.suzu.test.ime.config.ShareWhitelistConfig
import com.suzu.test.log.TestLog

class ShareWhitelistActivity : AppCompatActivity() {

    companion object {
        private const val MODULE = "ShareWhitelist"
    }

    private lateinit var binding: ActivityShareWhitelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareWhitelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        loadShareTargets()
    }

    private fun loadShareTargets() {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
        }
        val packageManager = packageManager
        val apps = packageManager.queryIntentActivities(
            sendIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
            .asSequence()
            .map { it to it.activityInfo.packageName }
            .filter { (_, packageName) -> packageName != packageNameForThisApp() }
            .distinctBy { (_, packageName) -> packageName }
            .sortedWith(
                compareBy(
                    { it.first.loadLabel(packageManager).toString().lowercase() },
                    { it.second }
                )
            )
            .toList()

        binding.llAppList.removeAllViews()
        binding.tvEmptyHint.visibility = if (apps.isEmpty()) View.VISIBLE else View.GONE

        apps.forEachIndexed { index, (resolveInfo, packageName) ->
            addAppRow(resolveInfo, packageName)
            if (index < apps.lastIndex) {
                binding.llAppList.addView(createDivider())
            }
        }

        TestLog.i(MODULE, "已加载支持图片分享的应用: ${apps.size} 个")
    }

    private fun addAppRow(
        resolveInfo: android.content.pm.ResolveInfo,
        packageName: String
    ) {
        val packageManager = packageManager
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            minimumHeight = dp(68)
            setPadding(dp(16), dp(8), dp(12), dp(8))
            setBackgroundResource(android.R.drawable.list_selector_background)
        }

        val icon = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(dp(44), dp(44)).apply {
                marginEnd = dp(12)
            }
            setImageDrawable(resolveInfo.loadIcon(packageManager))
            contentDescription = resolveInfo.loadLabel(packageManager)
        }

        val textContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val appName = TextView(this).apply {
            text = resolveInfo.loadLabel(packageManager)
            textSize = 15f
            setTextColor(Color.rgb(51, 51, 51))
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
        }

        val packageNameView = TextView(this).apply {
            text = packageName
            textSize = 11f
            setTextColor(Color.rgb(136, 136, 136))
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
            setPadding(0, dp(2), 0, 0)
        }

        textContainer.addView(appName)
        textContainer.addView(packageNameView)

        val switch = Switch(this).apply {
            isChecked = ShareWhitelistConfig.isWhitelisted(this@ShareWhitelistActivity, packageName)
            contentDescription = "使用系统分享选择器"
            setOnCheckedChangeListener { _, enabled ->
                ShareWhitelistConfig.setWhitelisted(
                    this@ShareWhitelistActivity,
                    packageName,
                    enabled
                )
                TestLog.i(MODULE, "已${if (enabled) "加入" else "移出"}白名单: $packageName")
            }
        }

        row.addView(icon)
        row.addView(textContainer)
        row.addView(switch)
        binding.llAppList.addView(row)
    }

    private fun createDivider(): View =
        View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            )
            background = ColorDrawable(Color.rgb(240, 240, 240))
        }

    private fun packageNameForThisApp(): String = applicationContext.packageName

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
