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

class CategoryReorderAdapter(
    private val scope: CoroutineScope,
    categories: List<CategoryEntity>
) : RecyclerView.Adapter<CategoryReorderAdapter.ViewHolder>() {
    private val categories = categories.toMutableList()
    val orderedIds: List<Long> get() = categories.map { it.id }

    init { setHasStableIds(true) }

    fun isMovable(position: Int): Boolean = position in categories.indices

    fun move(from: Int, to: Int): Boolean {
        if (!isMovable(from) || !isMovable(to) || from == to) return false
        // Insert, rather than swap: crossing a row must shift every intervening tile.
        categories.add(to, categories.removeAt(from))
        notifyItemMoved(from, to)
        return true
    }

    override fun getItemId(position: Int): Long = categories[position].id
    override fun getItemCount(): Int = categories.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCategoryReorderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories[position])
    override fun onViewRecycled(holder: ViewHolder) { holder.clear(); super.onViewRecycled(holder) }

    inner class ViewHolder(private val binding: ItemCategoryReorderBinding) : RecyclerView.ViewHolder(binding.root) {
        private var iconJob: Job? = null
        private var boundId: Long? = null

        init {
            // Square thumbnails follow the actual cell width, including dense library grids.
            binding.categoryIconFrame.addOnLayoutChangeListener { view, left, _, right, _, _, _, _, _ ->
                val size = right - left
                if (size > 0 && view.layoutParams.height != size) {
                    view.layoutParams = view.layoutParams.apply { height = size }
                }
            }
        }

        fun clear() {
            iconJob?.cancel()
            iconJob = null
            Glide.with(binding.ivCategoryIcon).clear(binding.ivCategoryIcon)
        }

        fun bind(category: CategoryEntity) {
            clear()
            boundId = category.id
            binding.tvCategoryName.text = category.name
            binding.root.contentDescription = category.name
            binding.ivCategoryIcon.visibility = View.VISIBLE
            binding.tvCategoryTextIcon.visibility = View.GONE
            binding.ivCategoryIcon.imageTintList = null
            binding.ivCategoryIcon.setImageResource(R.drawable.ic_category_default)

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
