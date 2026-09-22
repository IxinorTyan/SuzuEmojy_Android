package com.suzu.test.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.databinding.ItemCategoryReorderBinding
import com.suzu.test.db.CategoryIconResolver
import com.suzu.test.db.CategoryIconResult
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/** Position zero is the fixed All tile; all other positions are editable categories. */
class CategoryReorderAdapter(
    private val scope: CoroutineScope,
    categories: List<CategoryEntity>,
    private val selectedId: Long?
) : RecyclerView.Adapter<CategoryReorderAdapter.ViewHolder>() {
    private val categories = categories.toMutableList()
    val orderedIds: List<Long> get() = categories.map { it.id }

    init { setHasStableIds(true) }

    fun isMovable(position: Int): Boolean = position in 1..categories.size

    fun move(from: Int, to: Int): Boolean {
        if (!isMovable(from) || !isMovable(to) || from == to) return false
        // Insert, rather than swap: crossing a row must shift every intervening tile.
        categories.add(to - 1, categories.removeAt(from - 1))
        notifyItemMoved(from, to)
        return true
    }

    override fun getItemId(position: Int): Long = if (position == 0) Long.MIN_VALUE else categories[position - 1].id
    override fun getItemCount(): Int = categories.size + 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCategoryReorderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories.getOrNull(position - 1))
    override fun onViewRecycled(holder: ViewHolder) { holder.clear(); super.onViewRecycled(holder) }

    inner class ViewHolder(private val binding: ItemCategoryReorderBinding) : RecyclerView.ViewHolder(binding.root) {
        private var iconJob: Job? = null
        private var boundId: Long? = null

        fun clear() {
            iconJob?.cancel()
            iconJob = null
            Glide.with(binding.ivCategoryIcon).clear(binding.ivCategoryIcon)
        }

        fun bind(category: CategoryEntity?) {
            clear()
            boundId = category?.id
            binding.root.isSelected = selectedId == category?.id
            binding.tvCategoryName.text = category?.name ?: "全部"
            binding.tvCategoryStatus.text = when {
                category == null -> "固定位置"
                category.id == selectedId -> "当前分类"
                else -> ""
            }
            binding.root.contentDescription = "${category?.name ?: "全部"}，${binding.tvCategoryStatus.text}"
            binding.ivCategoryIcon.visibility = View.VISIBLE
            binding.tvCategoryTextIcon.visibility = View.GONE
            binding.ivCategoryIcon.imageTintList = null
            binding.ivCategoryIcon.setImageResource(if (category == null) R.drawable.ic_tab_all else R.drawable.ic_category_default)
            if (category == null) return

            when (val icon = CategoryIconResolver.resolve(category.iconPath)) {
                is CategoryIconResult.Text -> {
                    binding.ivCategoryIcon.visibility = View.GONE
                    binding.tvCategoryTextIcon.visibility = View.VISIBLE
                    binding.tvCategoryTextIcon.text = icon.content
                }
                is CategoryIconResult.ImageFile -> loadFile(File(binding.root.context.filesDir, icon.relativePath))
                else -> {
                    val context = binding.root.context
                    iconJob = scope.launch {
                        val file = withContext(Dispatchers.IO) {
                            val db = DatabaseProvider.getDatabase(context)
                            val resource = if (icon is CategoryIconResult.Resource) {
                                db.resourceDao().getById(icon.resourceId)
                            } else {
                                db.resourceCategoryDao().getThumbnailPreloadResources(category.id, 1).firstOrNull()
                            }
                            resource?.let { File(context.filesDir, "resources/${it.filename}") }
                        }
                        if (boundId == category.id && file != null) loadFile(file)
                    }
                }
            }
        }

        private fun loadFile(file: File) {
            Glide.with(binding.ivCategoryIcon).asBitmap().load(file)
                .placeholder(R.drawable.ic_category_default).error(R.drawable.ic_category_default)
                .centerCrop().into(binding.ivCategoryIcon)
        }
    }
}
