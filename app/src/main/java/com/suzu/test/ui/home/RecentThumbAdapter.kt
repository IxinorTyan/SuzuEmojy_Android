package com.suzu.test.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.databinding.ItemHomeRecentThumbBinding
import com.suzu.test.db.entity.ResourceEntity
import java.io.File

class RecentThumbAdapter(
    private val onItemClick: () -> Unit
) : ListAdapter<ResourceEntity, RecentThumbAdapter.ThumbViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResourceEntity>() {
            override fun areItemsTheSame(oldItem: ResourceEntity, newItem: ResourceEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResourceEntity, newItem: ResourceEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbViewHolder {
        val binding = ItemHomeRecentThumbBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ThumbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ThumbViewHolder(
        private val binding: ItemHomeRecentThumbBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick()
            }
        }

        fun bind(resource: ResourceEntity) {
            val context = binding.root.context
            val file = File(context.filesDir, "resources/${resource.filename}")

            Glide.with(binding.ivRecentThumb)
                .load(file)
                .override(200, 200)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivRecentThumb)
        }
    }
}
