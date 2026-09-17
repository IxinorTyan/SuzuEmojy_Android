package com.suzu.test.ui.export

import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.suzu.test.R
import com.suzu.test.databinding.ActivityExportBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.resource.exportpkg.PackageExportStage
import com.suzu.test.resource.exportpkg.PackageExportScope
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import com.suzu.test.log.TestLog
import android.widget.ProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExportBinding
    private lateinit var exportService: ResourcePackageExportService

    private var pendingPackageName: String = ""
    private var pendingSelectedCategoryIds: List<Long> = emptyList()
    private var pendingExportScope = PackageExportScope.ALL
    private var progressDialog: AlertDialog? = null

    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/zip")
    ) { documentUri ->
        if (documentUri != null) {
            exportToDocument(documentUri)
        } else {
            binding.tvProgress.text = "已取消导出"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let { state ->
            pendingPackageName = state.getString("pendingPackageName").orEmpty()
            pendingSelectedCategoryIds = state.getLongArray("pendingSelectedCategoryIds")?.toList().orEmpty()
            pendingExportScope = if (state.getBoolean("exportCategories")) {
                PackageExportScope.CATEGORIES
            } else {
                PackageExportScope.ALL
            }
            binding.tvSelectedCategoriesSummary.text = state.getString("selectedCategoriesSummary").orEmpty()
            binding.layoutSelectedCategoriesCard.visibility = if (pendingExportScope == PackageExportScope.CATEGORIES) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }

        exportService = ResourcePackageExportService(this, DatabaseProvider.getDatabase(this))

        binding.btnExportAll.setOnClickListener {
            pendingExportScope = PackageExportScope.ALL
            pendingSelectedCategoryIds = emptyList()
            binding.layoutSelectedCategoriesCard.visibility = android.view.View.GONE
            binding.tvSelectedCategoriesSummary.text = ""
            binding.tvProgress.text = "已选择：导出全部表情"
            openFileCreator()
        }

        binding.btnExportSelected.setOnClickListener {
            showCategorySelectDialog()
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tvProgress.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: android.text.Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.layoutStatusSection.visibility = android.view.View.VISIBLE
                }
            }
        })

        binding.tvSummary.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: android.text.Editable?) {
                val hasText = !s.isNullOrBlank()
                binding.tvSummary.visibility = if (hasText) android.view.View.VISIBLE else android.view.View.GONE
                if (hasText) {
                    binding.layoutStatusSection.visibility = android.view.View.VISIBLE
                }
            }
        })
    }

    private fun showCategorySelectDialog() {
        lifecycleScope.launch {
            val categories = withContext(Dispatchers.IO) {
                DatabaseProvider.getDatabase(this@ExportActivity).categoryDao().getAllCategories()
            }

            if (categories.isEmpty()) {
                Toast.makeText(this@ExportActivity, "当前没有可导出的收藏夹", Toast.LENGTH_SHORT).show()
                return@launch
            }

            showCategoryMultiSelectDialog(categories)
        }
    }

    private fun showCategoryMultiSelectDialog(categories: List<CategoryEntity>) {
        val checked = BooleanArray(categories.size)
        val density = resources.displayMetrics.density
        fun dp(v: Int) = (v * density).toInt()

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(12), dp(20), 0)
        }

        val hint = TextView(this).apply {
            text = "可同时勾选多个收藏夹，最终合并打包为一个资源包"
            textSize = 12f
            setTextColor(0xFF64748B.toInt())
            setPadding(0, 0, 0, dp(10))
        }
        container.addView(hint)

        val scrollView = ScrollView(this)
        val listContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        categories.forEachIndexed { index, category ->
            val checkBox = CheckBox(this).apply {
                text = category.name
                isChecked = false
                textSize = 14f
                setTextColor(0xFF0F172A.toInt())
                setPadding(dp(8), dp(10), dp(8), dp(10))
                buttonTintList = android.content.res.ColorStateList.valueOf(0xFF2563EB.toInt())
                setOnCheckedChangeListener { _, isChecked ->
                    checked[index] = isChecked
                }
            }
            listContainer.addView(checkBox)
        }

        scrollView.addView(listContainer)
        container.addView(scrollView)

        AlertDialog.Builder(this)
            .setTitle("导出指定收藏夹")
            .setView(container)
            .setPositiveButton("继续") { _, _ ->
                val selected = categories.filterIndexed { index, _ -> checked[index] }.map { it.id }
                if (selected.isEmpty()) {
                    Toast.makeText(this, "请至少选择一个收藏夹", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                pendingSelectedCategoryIds = selected
                pendingExportScope = PackageExportScope.CATEGORIES
                val selectedNames = categories.filter { it.id in pendingSelectedCategoryIds }.joinToString("、") { it.name }
                binding.layoutSelectedCategoriesCard.visibility = android.view.View.VISIBLE
                binding.tvSelectedCategoriesSummary.text = selectedNames
                binding.tvProgress.text = "已选择 ${pendingSelectedCategoryIds.size} 个收藏夹"
                openFileCreator()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun openFileCreator() {
        pendingPackageName = "suzuemojy_${System.currentTimeMillis()}"
        binding.tvProgress.text = "请选择保存位置和文件名"
        createDocumentLauncher.launch("$pendingPackageName.zip")
    }

    private fun exportToDocument(targetUri: Uri) {
        lifecycleScope.launch {
            binding.btnExportAll.isEnabled = false
            binding.btnExportSelected.isEnabled = false

            val dialogView = layoutInflater.inflate(R.layout.dialog_export_progress, null)
            val tvProgressMessage = dialogView.findViewById<TextView>(R.id.tvProgressMessage)
            val progressBarExport = dialogView.findViewById<ProgressBar>(R.id.progressBarExport)

            progressBarExport.isIndeterminate = false
            progressBarExport.max = 100
            progressBarExport.progress = 0
            tvProgressMessage.text = "正在准备导出..."

            val currentJob = coroutineContext[Job]
            val dialog = AlertDialog.Builder(this@ExportActivity)
                .setTitle("正在导出资源包")
                .setView(dialogView)
                .setNegativeButton("取消") { _, _ ->
                    currentJob?.cancel()
                }
                .setCancelable(false)
                .create()

            progressDialog = dialog
            dialog.show()

            var displayName = "$pendingPackageName.zip"
            try {
                val result = withContext(Dispatchers.IO) {
                    displayName = queryDocumentName(targetUri)?.takeIf { it.isNotBlank() } ?: displayName
                    pendingPackageName = if (displayName.endsWith(".zip", ignoreCase = true)) {
                        displayName.dropLast(4)
                    } else {
                        displayName
                    }

                    exportService.exportToZip(
                        targetUri = targetUri,
                        packageName = pendingPackageName,
                        selectedCategoryIds = pendingSelectedCategoryIds,
                        exportScope = pendingExportScope
                    ) { stage, progress, total ->
                        runOnUiThread {
                            if (isFinishing || isDestroyed) return@runOnUiThread
                            progressBarExport.max = total
                            progressBarExport.progress = progress
                            when (stage) {
                                PackageExportStage.PACKING -> {
                                    tvProgressMessage.text = "正在准备: $progress / $total"
                                }
                                PackageExportStage.WRITING -> {
                                    tvProgressMessage.text = "正在写入: $progress / $total"
                                }
                            }
                        }
                    }
                }
                binding.tvProgress.text = "导出完成"
                binding.tvSummary.text = "导出 ${result.exportedCount} 张，跳过 ${result.skippedCount} 张"
                val warningCount = result.manifest.optJSONArray("warnings")?.length() ?: 0
                if (warningCount > 0) binding.tvSummary.append("，$warningCount 个图标未导出")
                Toast.makeText(
                    this@ExportActivity,
                    "已导出资源包：$displayName",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: kotlinx.coroutines.CancellationException) {
                binding.tvProgress.text = "导出被取消"
                binding.tvSummary.text = ""
                Toast.makeText(this@ExportActivity, "导出已被取消", Toast.LENGTH_SHORT).show()
                targetUri.let { uri ->
                    withContext(NonCancellable + Dispatchers.IO) {
                        try {
                            DocumentsContract.deleteDocument(contentResolver, uri)
                        } catch (ex: Exception) {
                            TestLog.w("ExportActivity", "清理取消后的 SAF 半成品失败: ${ex.message}")
                        }
                    }
                }
                throw e
            } catch (e: Exception) {
                binding.tvProgress.text = "导出失败"
                binding.tvSummary.text = ""
                Toast.makeText(this@ExportActivity, e.message ?: "导出失败", Toast.LENGTH_LONG).show()
                targetUri.let { uri ->
                    withContext(NonCancellable + Dispatchers.IO) {
                        try {
                            DocumentsContract.deleteDocument(contentResolver, uri)
                        } catch (ex: Exception) {
                            TestLog.w("ExportActivity", "清理失败后的 SAF 半成品失败: ${ex.message}")
                        }
                    }
                }
            } finally {
                binding.btnExportAll.isEnabled = true
                binding.btnExportSelected.isEnabled = true
                if (!isFinishing && !isDestroyed) {
                    progressDialog?.dismiss()
                }
                progressDialog = null
            }
        }
    }

    override fun onDestroy() {
        progressDialog?.dismiss()
        progressDialog = null
        super.onDestroy()
    }

    private fun queryDocumentName(documentUri: Uri): String? {
        return try {
            val projection = arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            contentResolver.query(documentUri, projection, null, null, null)?.use {
                if (it.moveToFirst()) {
                    val idx = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                    if (idx != -1) it.getString(idx) else null
                } else null
            }
        } catch (_: Exception) {
            null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("pendingPackageName", pendingPackageName)
        outState.putLongArray("pendingSelectedCategoryIds", pendingSelectedCategoryIds.toLongArray())
        outState.putBoolean("exportCategories", pendingExportScope == PackageExportScope.CATEGORIES)
        outState.putString("selectedCategoriesSummary", binding.tvSelectedCategoriesSummary.text.toString())
        super.onSaveInstanceState(outState)
    }
}
