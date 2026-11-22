package com.suzu.test.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.suzu.test.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suzu.test.databinding.ItemCategoryRowBinding
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope

internal data class CategoryRow(val name: String, val iconPath: String? = null, val detail: String? = null, val symbol: Int? = null) {
    constructor(category: CategoryEntity) : this(category.name, category.iconPath)
}

/** The same folder/image/emoji row is used for dialogs, drop-downs and previews. */
internal object CategoryRows {
    fun append(parent: ViewGroup, scope: CoroutineScope, row: CategoryRow, mediaChip: Boolean = false, onTap: (() -> Unit)? = null) {
        val binding = ItemCategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, true)
        if (mediaChip) {
            binding.root.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.categoryLabels.layoutParams = (binding.categoryLabels.layoutParams as LinearLayout.LayoutParams).apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                weight = 0f
            }
            binding.categoryTitle.setTextColor(parent.context.getColor(android.R.color.white))
            binding.categoryIcon.setCompactTint(parent.context.getColor(R.color.ui_media_muted))
        }
        bind(binding, scope, row, null)
        if (onTap != null) binding.root.setOnClickListener { onTap() }
    }

    fun pickOne(context: Context, scope: CoroutineScope, title: String, categories: List<CategoryEntity>, onChoose: (CategoryEntity) -> Unit) {
        MaterialAlertDialogBuilder(context).setTitle(title)
            .setAdapter(CategoryRowAdapter(scope, categories.map(::CategoryRow))) { _, index -> onChoose(categories[index]) }
            .setNegativeButton("取消", null).show()
    }

    fun pickMany(
        context: Context, scope: CoroutineScope, title: String, categories: List<CategoryEntity>,
        initiallyChecked: Set<Long> = emptySet(), positive: String = "保存", requireSelection: Boolean = false,
        onChoose: (Set<Long>) -> Unit
    ): AlertDialog {
        val checked = BooleanArray(categories.size) { categories[it].id in initiallyChecked }
        val adapter = CategoryRowAdapter(scope, categories.map(::CategoryRow), checked)
        val dialog = MaterialAlertDialogBuilder(context).setTitle(title)
            .setAdapter(adapter, null)
            .setNegativeButton("取消", null)
            .setPositiveButton(positive) { _, _ ->
                onChoose(categories.indices.filter { checked[it] }.mapTo(linkedSetOf()) { categories[it].id })
            }.create()
        dialog.show()
        val confirm = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        fun updateConfirm() { confirm.isEnabled = !requireSelection || checked.any { it } }
        dialog.listView.setOnItemClickListener { _, _, index, _ ->
            checked[index] = !checked[index]
            adapter.notifyDataSetChanged()
            updateConfirm()
        }
        updateConfirm()
        return dialog
    }

    internal fun bind(binding: ItemCategoryRowBinding, scope: CoroutineScope, row: CategoryRow, checked: Boolean?) = with(binding) {
        categoryTitle.text = row.name
        categoryDetail.text = row.detail
        categoryDetail.isVisible = !row.detail.isNullOrEmpty()
        if (row.symbol != null) categoryIcon.bindSymbol(row.symbol, compact = true)
        else categoryIcon.bindCategory(row.iconPath, compact = true, scope)
        categoryCheck.isVisible = checked != null
        categoryCheck.isChecked = checked == true
        ViewCompat.setStateDescription(root, checked?.let { if (it) "已选中" else "未选中" })
    }
}

internal class CategoryRowAdapter(
    private val scope: CoroutineScope, private val rows: List<CategoryRow>, private val checked: BooleanArray? = null
) : BaseAdapter() {
    override fun getCount(): Int = rows.size
    override fun getItem(position: Int): CategoryRow = rows[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView != null) ItemCategoryRowBinding.bind(convertView)
            else ItemCategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        CategoryRows.bind(binding, scope, getItem(position), checked?.get(position))
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View = getView(position, convertView, parent)
}
