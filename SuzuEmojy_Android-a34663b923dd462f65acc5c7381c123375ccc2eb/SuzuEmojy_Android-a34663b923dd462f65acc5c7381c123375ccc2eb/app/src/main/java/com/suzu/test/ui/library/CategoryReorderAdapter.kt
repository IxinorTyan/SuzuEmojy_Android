package com.suzu.test.ui.library

import android.view.LayoutInflater
import android.view.MotionEvent
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Collections

class CategoryReorderAdapter(
    private val scope: CoroutineScope,
    private val onStartDrag: (RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<CategoryReorderAdapter.ViewHolder>() {

    val items = mutableListOf<CategoryEntity>()

    fun setData(newItems: List<CategoryEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition in 0 until items.size && toPosition in 0 until items.size) {
            Collections.swap(items, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryReorderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: ItemCategoryReorderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivDragHandle.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    onStartDrag(this)
                }
                false
            }
        }

        fun bind(category: CategoryEntity) {
            binding.tvCategoryName.text = category.name

            when (val result = CategoryIconResolver.resolve(category.iconPath)) {
                is CategoryIconResult.Default -> {
                    binding.ivCategoryIcon.visibility = View.VISIBLE
                    binding.tvCategoryTextIcon.visibility = View.GONE
                    binding.ivCategoryIcon.setImageResource(R.drawable.ic_category_default)
                }
                is CategoryIconResult.Text -> {
                    binding.ivCategoryIcon.visibility = View.GONE
                    binding.tvCategoryTextIcon.visibility = View.VISIBLE
                    binding.tvCategoryTextIcon.text = result.content
                }
                is CategoryIconResult.Resource -> {
                    binding.ivCategoryIcon.visibility = View.VISIBLE
                    binding.tvCategoryTextIcon.visibility = View.GONE
                    loadResourceIcon(result.resourceId)
                }
            }
        }

        private fun loadResourceIcon(resourceId: Long) {
            val context = binding.root.context
            scope.launch {
                val resource = withContext(Dispatchers.IO) {
                    val db = DatabaseProvider.getDatabase(context)
                    db.resourceDao().getById(resourceId)
                }
                val file = if (resource != null) File(context.filesDir, "resources/${resource.filename}") else null
                if (file != null && file.exists()) {
                    Glide.with(context)
                        .asBitmap()
                        .load(file)
                        .centerCrop()
                        .into(binding.ivCategoryIcon)
                } else {
                    binding.ivCategoryIcon.setImageResource(R.drawable.ic_category_default)
                }
            }
        }
    }
}
