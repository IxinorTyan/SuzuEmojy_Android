package com.suzu.test.ui.export

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.Gravity
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.suzu.test.databinding.ActivityExportBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExportBinding
    private lateinit var exportService: ResourcePackageExportService

    private var pendingPackageName: String = ""
    private var pendingSelectedCategoryIds: List<Long> = emptyList()

    private val openDocumentTreeLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { treeUri ->
        if (treeUri != null) {
            handleFolderSelected(treeUri)
        } else {
            binding.tvProgress.text = "已取消选择文件夹"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exportService = ResourcePackageExportService(this, DatabaseProvider.getDatabase(this))

        binding.btnExportAll.setOnClickListener {
            pendingSelectedCategoryIds = emptyList()
            binding.layoutSelectedCategoriesCard.visibility = android.view.View.GONE
            binding.tvSelectedCategoriesSummary.text = ""
            binding.tvProgress.text = "已选择：导出全部表情"
            showRenameDialog()
        }

        binding.btnExportSelected.setOnClickListener {
            showCategorySelectDialog()
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
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
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }

        val hint = TextView(this).apply {
            text = "可同时勾选多个收藏夹，最终只导出为一个资源包"
            textSize = 13f
            setTextColor(0xFF666666.toInt())
            setPadding(0, 0, 0, 16)
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
                textSize = 15f
                setPadding(0, 12, 0, 12)
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
                val selectedNames = categories.filter { it.id in pendingSelectedCategoryIds }.joinToString("、") { it.name }
                binding.layoutSelectedCategoriesCard.visibility = android.view.View.VISIBLE
                binding.tvSelectedCategoriesSummary.text = selectedNames
                binding.tvProgress.text = "已选择 ${pendingSelectedCategoryIds.size} 个收藏夹"
                showRenameDialog()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showRenameDialog() {
        val input = EditText(this).apply {
            setText(pendingPackageName.ifBlank { "suzuemojy_${System.currentTimeMillis()}" })
            setSelectAllOnFocus(true)
            hint = "资源包名称"
        }

        AlertDialog.Builder(this)
            .setTitle("自定义资源包名称")
            .setMessage("请输入导出文件名（不含扩展名）")
            .setView(input)
            .setPositiveButton("继续") { _, _ ->
                val raw = input.text?.toString().orEmpty().trim()
                pendingPackageName = if (raw.isBlank()) {
                    "suzuemojy_${System.currentTimeMillis()}"
                } else {
                    raw
                }
                binding.tvProgress.text = "名称已设置：$pendingPackageName"
                openFolderPicker()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun openFolderPicker() {
        binding.tvProgress.text = "请选择导出文件夹"
        openDocumentTreeLauncher.launch(null)
    }

    private fun handleFolderSelected(treeUri: Uri) {
        try {
            contentResolver.takePersistableUriPermission(
                treeUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        } catch (_: SecurityException) {
        }

        val folderName = queryFolderName(treeUri)
        binding.tvProgress.text = "正在导出到：${folderName ?: "所选文件夹"}"

        lifecycleScope.launch {
            binding.btnExportAll.isEnabled = false
            binding.btnExportSelected.isEnabled = false
            try {
                val result = withContext(Dispatchers.IO) {
                    val zipName = sanitizeZipName(pendingPackageName) + ".zip"
                    val targetUri = createTargetDocument(treeUri, zipName)
                    exportService.exportToZip(
                        targetUri = targetUri,
                        packageName = pendingPackageName,
                        selectedCategoryIds = pendingSelectedCategoryIds
                    )
                }
                binding.tvProgress.text = "导出完成"
                binding.tvSummary.text = "导出 ${result.exportedCount} 张，跳过 ${result.skippedCount} 张"
                Toast.makeText(
                    this@ExportActivity,
                    "已导出资源包：${result.packageName}.zip",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                binding.tvProgress.text = "导出失败"
                binding.tvSummary.text = ""
                Toast.makeText(this@ExportActivity, e.message ?: "导出失败", Toast.LENGTH_LONG).show()
            } finally {
                binding.btnExportAll.isEnabled = true
                binding.btnExportSelected.isEnabled = true
            }
        }
    }

    private fun createTargetDocument(treeUri: Uri, displayName: String): Uri {
        val docId = DocumentsContract.getTreeDocumentId(treeUri)
        val dirUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, docId)
        val mimeType = "application/zip"

        val existing = findExistingDocument(treeUri, displayName)
        if (existing != null) {
            try {
                DocumentsContract.deleteDocument(contentResolver, existing)
            } catch (_: Exception) {
            }
        }

        return DocumentsContract.createDocument(contentResolver, dirUri, mimeType, displayName)
            ?: throw IllegalStateException("无法在所选文件夹中创建导出文件")
    }

    private fun findExistingDocument(treeUri: Uri, displayName: String): Uri? {
        return try {
            val docId = DocumentsContract.getTreeDocumentId(treeUri)
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, docId)
            val projection = arrayOf(
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME
            )
            contentResolver.query(childrenUri, projection, null, null, null)?.use { cursor ->
                val idIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
                val nameIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    val name = if (nameIndex != -1) cursor.getString(nameIndex) else null
                    if (name == displayName && idIndex != -1) {
                        val childDocId = cursor.getString(idIndex)
                        return DocumentsContract.buildDocumentUriUsingTree(treeUri, childDocId)
                    }
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    private fun queryFolderName(treeUri: Uri): String? {
        return try {
            val treeDocId = DocumentsContract.getTreeDocumentId(treeUri)
            val docUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, treeDocId)
            val projection = arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
            contentResolver.query(docUri, projection, null, null, null)?.use {
                if (it.moveToFirst()) {
                    val idx = it.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                    if (idx != -1) it.getString(idx) else null
                } else null
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun sanitizeZipName(input: String): String {
        return input.trim()
            .replace(Regex("""[\\/:*?"<>|]"""), "_")
            .replace(Regex("""\s+"""), "_")
            .trim('_', '.', ' ')
            .ifBlank { "suzuemojy_${System.currentTimeMillis()}" }
    }
}
