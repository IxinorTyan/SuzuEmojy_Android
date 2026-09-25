package com.suzu.test.ui.settings

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.suzu.test.R
import com.suzu.test.databinding.ActivityShareWhitelistBinding
import com.suzu.test.log.TestLog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AppWhitelistActivity : AppCompatActivity() {
    protected abstract val pageTitle: String
    protected abstract val pageSubtitle: String
    protected abstract val explanation: String
    protected abstract val emptyHint: String
    protected abstract val switchDescription: String
    protected abstract fun queryIntent(): Intent
    protected open val queryFlags = 0
    protected open val excludeSelf = false
    protected open val retainUnavailable = false
    protected open fun isSelectionLocked(packageName: String): Boolean = false
    private data class AppEntry(
        val packageName: String,
        val label: String,
        val icon: android.graphics.drawable.Drawable
    )
    protected abstract fun selectedPackages(): Set<String>
    protected abstract fun saveSelection(packageName: String, enabled: Boolean)


    companion object {
        private const val MODULE = "AppWhitelist"
    }

    private lateinit var binding: ActivityShareWhitelistBinding
    private var loadedApps: List<AppEntry>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareWhitelistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvPageTitle.text = pageTitle
        binding.tvPageSubtitle.text = pageSubtitle
        binding.tvExplanation.text = explanation
        binding.tvEmptyHint.text = emptyHint

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val navBar = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars())
            val baseBottom = (12 * resources.displayMetrics.density).toInt()
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, baseBottom + navBar.bottom)
            insets
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.etAppSearch.doAfterTextChanged { renderApps() }
        loadShareTargets()
    }

    private fun loadShareTargets() {
        // Snapshot once on entry. Persist changes without moving rows during this visit.
        val selected = selectedPackages()
        binding.tvEmptyHint.text = "正在加载应用…"
        binding.tvEmptyHint.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val apps = withContext(Dispatchers.IO) {
                    val installed = packageManager.queryIntentActivities(queryIntent(), queryFlags)
                        .filter { !excludeSelf || it.activityInfo.packageName != packageName }
                        .distinctBy { it.activityInfo.packageName }
                        .map {
                            // Rows represent applications, not share activities such as “发送到好友”.
                            val applicationInfo = it.activityInfo.applicationInfo
                            AppEntry(
                                it.activityInfo.packageName,
                                applicationInfo.loadLabel(packageManager).toString(),
                                applicationInfo.loadIcon(packageManager)
                            )
                        }
                    val missing = if (retainUnavailable) {
                        (selected - installed.map { it.packageName }.toSet()).map {
                            AppEntry(it, "$it（当前不可用）", packageManager.defaultActivityIcon)
                        }
                    } else emptyList()
                    (installed + missing).sortedWith(compareBy<AppEntry>(
                        { it.packageName !in selected },
                        { it.label.lowercase() },
                        { it.packageName }
                    ))
                }
                loadedApps = apps
                renderApps()
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                binding.tvEmptyHint.text = "无法读取应用列表，请返回后重试"
                binding.tvEmptyHint.visibility = View.VISIBLE
                TestLog.e(MODULE, "读取应用列表失败", e)
            }
        }
    }

    private fun renderApps() {
        // Keep loading/error hints until the application list is available.
        val apps = loadedApps ?: return
        val query = binding.etAppSearch.text?.toString().orEmpty().trim()
        val visibleApps = apps.filter {
            it.label.contains(query, ignoreCase = true) ||
                it.packageName.contains(query, ignoreCase = true)
        }
        // Read current selections rather than the snapshot used for initial ordering.
        val selected = selectedPackages()
        binding.llAppList.removeAllViews()
        binding.tvEmptyHint.text = if (apps.isNotEmpty() && query.isNotEmpty()) {
            getString(R.string.whitelist_search_no_results)
        } else emptyHint
        binding.tvEmptyHint.visibility = if (visibleApps.isEmpty()) View.VISIBLE else View.GONE
        visibleApps.forEachIndexed { index, app ->
            addAppRow(app, app.packageName in selected)
            if (index < visibleApps.lastIndex) binding.llAppList.addView(createDivider())
        }
    }

    private fun addAppRow(app: AppEntry, selected: Boolean) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            minimumHeight = dp(64)
            setPadding(dp(16), dp(10), dp(16), dp(10))
            setBackgroundResource(android.R.drawable.list_selector_background)
        }

        val icon = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(dp(40), dp(40)).apply {
                marginEnd = dp(12)
            }
            setImageDrawable(app.icon)
            contentDescription = app.label
        }

        val textContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val appName = TextView(this).apply {
            text = app.label
            textSize = 15f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(0xFF0F172A.toInt())
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
        }

        val packageNameView = TextView(this).apply {
            text = app.packageName
            textSize = 11f
            setTextColor(0xFF64748B.toInt())
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
            setPadding(0, dp(2), 0, 0)
        }

        textContainer.addView(appName)
        textContainer.addView(packageNameView)

        val switch = Switch(this).apply {
            isChecked = selected
            isEnabled = !isSelectionLocked(app.packageName)
            contentDescription = "${app.label}：$switchDescription" +
                if (isEnabled) "" else "（固定开启）"
            setOnCheckedChangeListener { _, enabled ->
                saveSelection(app.packageName, enabled)
                TestLog.i(MODULE, "已${if (enabled) "加入" else "移出"}白名单: ${app.packageName}")
            }
        }

        if (isSelectionLocked(app.packageName)) {
            appName.text = "${app.label}（固定开启）"
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
            ).apply {
                marginStart = dp(68)
            }
            background = ColorDrawable(0xFFF1F5F9.toInt())
        }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
