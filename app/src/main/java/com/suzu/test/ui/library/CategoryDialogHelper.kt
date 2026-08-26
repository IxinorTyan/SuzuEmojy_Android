package com.suzu.test.ui.library

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.suzu.test.R
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.log.TestLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.suzu.test.resource.export.ResourceExportHelper

class CategoryDialogHelper(
    private val context: Context,
    private val scope: CoroutineScope,
    private val onEnterSortingMode: (() -> Unit)? = null
) {

    fun showCategoryMenuDialog(category: CategoryEntity, onDeleteSuccess: () -> Unit) {
        val options = arrayOf("调整位置", "设置文本图标", "重命名", "导出分类", "删除")
        AlertDialog.Builder(context)
            .setTitle(category.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> onEnterSortingMode?.invoke()
                    1 -> showSetTextIconDialog(category)
                    2 -> showRenameCategoryDialog(category)
                    3 -> exportCategoryResources(category)
                    4 -> showDeleteCategoryDialog(category, onDeleteSuccess)
                }
            }
            .show()
    }

    private fun exportCategoryResources(category: CategoryEntity) {
        scope.launch {
            val resources = withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(context)
                db.resourceCategoryDao().getResourcesForCategoryList(category.id)
            }
            ResourceExportHelper.exportResources(
                context = context,
                scope = scope,
                resources = resources,
                subFolder = category.name
            )
        }
    }

    private fun showSetTextIconDialog(category: CategoryEntity) {
        val currentText = when (val res = CategoryIconResolver.resolve(category.iconPath)) {
            is CategoryIconResult.Text -> res.content
            else -> ""
        }
        val editText = EditText(context).apply {
            hint = "输入文字或 emoji (留空恢复默认)"
            setText(currentText)
            setSingleLine()
        }
        AlertDialog.Builder(context)
            .setTitle("设置文本图标")
            .setView(editText)
            .setPositiveButton("保存") { _, _ ->
                val input = editText.text.toString().trim()
                val newIconPath = if (input.isEmpty()) null else "text:$input"
                updateCategoryIcon(category, newIconPath)
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun updateCategoryIcon(category: CategoryEntity, newIconPath: String?) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(context)
                db.categoryDao().updateCategory(category.copy(iconPath = newIconPath))
            }
        }
    }

    private fun showRenameCategoryDialog(category: CategoryEntity) {
        val container = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
        }
        val editText = EditText(context).apply {
            hint = "输入新名称"
            setText(category.name)
            setSingleLine()
        }
        val tvError = TextView(context).apply {
            setTextColor(0xFFD32F2F.toInt())
            textSize = 12f
            visibility = View.GONE
            setPadding(8, 4, 8, 0)
        }
        container.addView(editText)
        container.addView(tvError)

        val dialog = AlertDialog.Builder(context)
            .setTitle("重命名分类")
            .setView(container)
            .setPositiveButton("保存", null)
            .setNegativeButton("取消", null)
            .create()

        dialog.setOnShowListener {
            val saveBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            saveBtn.setOnClickListener {
                val name = editText.text.toString().trim()
                tvError.visibility = View.GONE

                if (name.isEmpty()) {
                    tvError.text = "分类名称不能为空"
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                if (name == "全部") {
                    tvError.text = "不能使用系统预留名称「全部」"
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                if (name == category.name) {
                    dialog.dismiss()
                    return@setOnClickListener
                }

                scope.launch {
                    val db = DatabaseProvider.getDatabase(context)
                    val existing = withContext(Dispatchers.IO) {
                        db.categoryDao().getCategoryByName(name)
                    }
                    if (existing != null && existing.id != category.id) {
                        tvError.text = "已存在同名分类「$name」"
                        tvError.visibility = View.VISIBLE
                        return@launch
                    }

                    val success = withContext(Dispatchers.IO) {
                        try {
                            db.categoryDao().updateCategory(category.copy(name = name))
                            true
                        } catch (e: Exception) {
                            TestLog.e("CategoryDialogHelper", "重命名分类失败: ${e.message}", e)
                            false
                        }
                    }
                    if (success) {
                        dialog.dismiss()
                    } else {
                        tvError.text = "重命名失败，名称可能已存在"
                        tvError.visibility = View.VISIBLE
                    }
                }
            }
        }
        dialog.show()
    }

    fun showCreateCategoryDialog() {
        val container = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
        }
        val editText = EditText(context).apply {
            hint = "请输入分类名称"
            setSingleLine()
        }
        val tvError = TextView(context).apply {
            setTextColor(0xFFD32F2F.toInt())
            textSize = 12f
            visibility = View.GONE
            setPadding(8, 4, 8, 0)
        }
        container.addView(editText)
        container.addView(tvError)

        val dialog = AlertDialog.Builder(context)
            .setTitle("新建分类")
            .setView(container)
            .setPositiveButton("创建", null)
            .setNegativeButton("取消", null)
            .create()

        dialog.setOnShowListener {
            val createBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            createBtn.setOnClickListener {
                val name = editText.text.toString().trim()
                tvError.visibility = View.GONE

                if (name.isEmpty()) {
                    tvError.text = "分类名称不能为空"
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                if (name == "全部") {
                    tvError.text = "不能使用系统预留名称「全部」"
                    tvError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                scope.launch {
                    val db = DatabaseProvider.getDatabase(context)
                    val existing = withContext(Dispatchers.IO) {
                        db.categoryDao().getCategoryByName(name)
                    }
                    if (existing != null) {
                        tvError.text = "已存在同名分类「$name」"
                        tvError.visibility = View.VISIBLE
                        return@launch
                    }

                    val success = withContext(Dispatchers.IO) {
                        try {
                            val maxSort = db.categoryDao().getMaxSortOrder() ?: 0
                            db.categoryDao().insertCategory(CategoryEntity(name = name, sortOrder = maxSort + 1))
                            true
                        } catch (e: Exception) {
                            TestLog.e("CategoryDialogHelper", "新建分类失败: ${e.message}", e)
                            false
                        }
                    }
                    if (success) {
                        dialog.dismiss()
                    } else {
                        tvError.text = "创建失败，分类名称已存在"
                        tvError.visibility = View.VISIBLE
                    }
                }
            }
        }
        dialog.show()
    }

    private fun showDeleteCategoryDialog(category: CategoryEntity, onDeleteSuccess: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("删除分类")
            .setMessage("确定删除分类「${category.name}」吗？\n(分类下的图片仍将保留在全部资源中)")
            .setPositiveButton("删除") { _, _ ->
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val db = DatabaseProvider.getDatabase(context)
                        db.categoryDao().deleteCategory(category)
                    }
                    onDeleteSuccess()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
