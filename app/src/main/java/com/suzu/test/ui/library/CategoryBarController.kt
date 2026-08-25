package com.suzu.test.ui.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CategoryBarController(
    private val context: Context,
    private val container: LinearLayout,
    private val scope: CoroutineScope,
    private val isSortingMode: () -> Boolean,
    private val onAllLongClick: () -> Unit,
    private val onCategoryReorderClick: () -> Unit,
    private val onCategorySelected: (selection: String) -> Unit
) {

    var currentSelection: String = "ALL"
        private set

    private var currentCategories: List<CategoryEntity> = emptyList()
    private val resourcesDir = File(context.filesDir, "resources")
    private val dialogHelper = CategoryDialogHelper(context, scope)

    fun render(categories: List<CategoryEntity>) {
        currentCategories = categories
        container.removeAllViews()

        // 1. [全部] Chip
        val isAllSelected = currentSelection == "ALL"
        val allChip = createSpecialChip(R.drawable.ic_tab_all, isAllSelected) {
            currentSelection = "ALL"
            render(currentCategories)
            onCategorySelected("ALL")
        }
        allChip.setOnLongClickListener {
            onAllLongClick()
            true
        }
        container.addView(allChip)

        // 2. 真实分类 Chips
        for (cat in categories) {
            val isSelected = currentSelection == cat.id.toString()
            val chipView = createCategoryChip(cat, isSelected) {
                currentSelection = cat.id.toString()
                render(currentCategories)
                onCategorySelected(cat.id.toString())
            }
            chipView.setOnLongClickListener {
                if (isSortingMode()) {
                    onCategoryReorderClick()
                } else {
                    dialogHelper.showCategoryMenuDialog(cat) {
                        if (currentSelection == cat.id.toString()) {
                            currentSelection = "ALL"
                            onCategorySelected("ALL")
                        }
                    }
                }
                true
            }
            container.addView(chipView)
        }

        // 3. [+] 新建按钮
        container.addView(createAddChip {
            dialogHelper.showCreateCategoryDialog()
        })
    }

    private fun createSpecialChip(iconResId: Int, isSelected: Boolean, onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_chip, container, false)
        val ivIcon = view.findViewById<ImageView>(R.id.ivChipIcon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(iconResId)
        view.isSelected = isSelected
        view.setOnClickListener { onClick() }
        return view
    }

    private fun createAddChip(onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_chip, container, false)
        val tvText = view.findViewById<TextView>(R.id.tvChipText)
        tvText.visibility = View.VISIBLE
        tvText.text = "+"
        tvText.textSize = 20f
        view.setOnClickListener { onClick() }
        return view
    }

    private fun createCategoryChip(category: CategoryEntity, isSelected: Boolean, onClick: () -> Unit): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_chip, container, false)
        val ivIcon = view.findViewById<ImageView>(R.id.ivChipIcon)
        val tvText = view.findViewById<TextView>(R.id.tvChipText)
        view.isSelected = isSelected

        when (val result = CategoryIconResolver.resolve(category.iconPath)) {
            is CategoryIconResult.Default -> {
                ivIcon.visibility = View.VISIBLE
                tvText.visibility = View.GONE
                ivIcon.setImageResource(R.drawable.ic_category_default)
            }
            is CategoryIconResult.Text -> {
                ivIcon.visibility = View.GONE
                tvText.visibility = View.VISIBLE
                tvText.text = result.content
            }
            is CategoryIconResult.Resource -> {
                ivIcon.visibility = View.VISIBLE
                tvText.visibility = View.GONE
                loadResourceIcon(ivIcon, result.resourceId)
            }
        }

        view.setOnClickListener { onClick() }
        return view
    }

    private fun loadResourceIcon(imageView: ImageView, resourceId: Long) {
        scope.launch {
            val resource = withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(context)
                db.resourceDao().getById(resourceId)
            }
            val file = if (resource != null) File(resourcesDir, resource.filename) else null
            if (file != null && file.exists()) {
                Glide.with(context)
                    .asBitmap()
                    .load(file)
                    .centerCrop()
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_category_default)
            }
        }
    }
}
