package com.suzu.test.ui.export

import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.activity.viewModels
import androidx.activity.OnBackPressedCallback
import com.suzu.test.R
import com.suzu.test.databinding.ActivityExportBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.resource.exportpkg.PackageExportScope
import android.widget.ProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExportBinding
    private val taskModel: ExportTaskViewModel by viewModels()
    private var dialogMessage: TextView? = null
    private var dialogProgress: ProgressBar? = null

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

        observeExportTask()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = requestExit()
        })

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
        taskModel.export(targetUri, pendingPackageName, pendingSelectedCategoryIds.toList(), pendingExportScope)
    }

    private fun observeExportTask() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    taskModel.state.collect { state ->
                        binding.btnExportAll.isEnabled = !state.running
                        binding.btnExportSelected.isEnabled = !state.running
                        if (state.message.isNotEmpty()) {
                            binding.tvProgress.text = state.message
                            binding.tvSummary.text = state.summary
                        }
                        if (state.running) {
                            if (progressDialog == null) showProgressDialog()
                            dialogMessage?.text = state.message
                            dialogProgress?.max = state.total
                            dialogProgress?.progress = state.progress
                        } else dismissProgressDialog()
                    }
                } finally { dismissProgressDialog() }
            }
        }
    }

    private fun showProgressDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_export_progress, null)
        dialogMessage = view.findViewById(R.id.tvProgressMessage)
        dialogProgress = view.findViewById(R.id.progressBarExport)
        progressDialog = AlertDialog.Builder(this)
            .setTitle("正在导出资源包")
            .setView(view)
            .setNegativeButton("取消", null)
            .setCancelable(false)
            .create().also { dialog ->
                dialog.show()
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                    it.isEnabled = false
                    dialogMessage?.text = "正在取消，请稍候…"
                    taskModel.cancel()
                }
            }
    }

    private fun requestExit() {
        if (!taskModel.state.value.running) { finish(); return }
        AlertDialog.Builder(this)
            .setTitle("取消导出并退出？")
            .setMessage("未完成的资源包会被清理。切到其他应用无需取消，返回这里可以继续查看进度。")
            .setNegativeButton("继续导出", null)
            .setPositiveButton("取消并退出") { _, _ ->
                lifecycleScope.launch {
                    taskModel.cancelAndJoin()
                    finish()
                }
            }.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
        dialogMessage = null
        dialogProgress = null
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("pendingPackageName", pendingPackageName)
        outState.putLongArray("pendingSelectedCategoryIds", pendingSelectedCategoryIds.toLongArray())
        outState.putBoolean("exportCategories", pendingExportScope == PackageExportScope.CATEGORIES)
        outState.putString("selectedCategoriesSummary", binding.tvSelectedCategoriesSummary.text.toString())
        super.onSaveInstanceState(outState)
    }
}
