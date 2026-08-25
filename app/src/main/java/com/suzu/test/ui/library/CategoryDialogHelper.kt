package com.suzu.test.ui.library

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.suzu.test.resource.export.ResourceExportHelper

class CategoryDialogHelper(
    private val context: Context,
    private val scope: CoroutineScope
) {

    fun showCategoryMenuDialog(category: CategoryEntity, onDeleteSuccess: () -> Unit) {
        val options = arrayOf("设置文本图标", "重命名", "导出分类", "删除")
        AlertDialog.Builder(context)
            .setTitle(category.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showSetTextIconDialog(category)
                    1 -> showRenameCategoryDialog(category)
                    2 -> exportCategoryResources(category)
                    3 -> showDeleteCategoryDialog(category, onDeleteSuccess)
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
        val editText = EditText(context).apply {
            hint = "输入新名称"
            setText(category.name)
            setSingleLine()
        }
        AlertDialog.Builder(context)
            .setTitle("重命名分类")
            .setView(editText)
            .setPositiveButton("保存") { _, _ ->
                val name = editText.text.toString().trim()
                if (name.isNotEmpty() && name != "全部") {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            val db = DatabaseProvider.getDatabase(context)
                            db.categoryDao().updateCategory(category.copy(name = name))
                        }
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    fun showCreateCategoryDialog() {
        val editText = EditText(context).apply {
            hint = "请输入分类名称"
            setSingleLine()
        }
        AlertDialog.Builder(context)
            .setTitle("新建分类")
            .setView(editText)
            .setPositiveButton("创建") { _, _ ->
                val name = editText.text.toString().trim()
                if (name.isNotEmpty() && name != "全部") {
                    createNewCategory(name)
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun createNewCategory(name: String) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(context)
                val maxSort = db.categoryDao().getMaxSortOrder() ?: 0
                db.categoryDao().insertCategory(CategoryEntity(name = name, sortOrder = maxSort + 1))
            }
        }
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
