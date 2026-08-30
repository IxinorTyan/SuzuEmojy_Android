package com.suzu.test.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.databinding.ItemLibraryGridBinding
import com.suzu.test.db.entity.ResourceEntity
import java.io.File

import com.suzu.test.R

class LibraryAdapter(
    private val resourcesDir: File,
    private val onItemClick: (ResourceEntity) -> Unit = {},
    private val onItemSelectToggle: (ResourceEntity) -> Unit = {},
    private val onItemLongClick: (ResourceEntity) -> Unit
) : ListAdapter<ResourceEntity, LibraryAdapter.LibraryViewHolder>(ResourceDiffCallback()) {

    companion object {
        const val PAYLOAD_SELECTION = "PAYLOAD_SELECTION"
    }

    var isSelectionMode: Boolean = false
        private set

    var selectedIds: Set<Long> = emptySet()
        private set

    fun setSelectionState(isSelectionMode: Boolean, selectedIds: Set<Long>) {
        this.isSelectionMode = isSelectionMode
        this.selectedIds = selectedIds
        notifyItemRangeChanged(0, itemCount, PAYLOAD_SELECTION)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemLibraryGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_SELECTION)) {
            holder.bindSelection(getItem(position))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onViewRecycled(holder: LibraryViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.binding.ivThumbnail)
    }

    inner class LibraryViewHolder(
        val binding: ItemLibraryGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResourceEntity) {
            val file = File(resourcesDir, item.filename)
            Glide.with(itemView.context)
                .asBitmap()
                .load(file)
                .override(250, 250)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivThumbnail)

            val isGif = item.format.equals("gif", ignoreCase = true)
            binding.tvBadge.visibility = if (isGif) View.VISIBLE else View.GONE

            bindSelection(item)

            itemView.setOnClickListener {
                if (isSelectionMode) {
                    onItemSelectToggle(item)
                } else {
                    onItemClick(item)
                }
            }

            itemView.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }

        fun bindSelection(item: ResourceEntity) {
            val isSelected = selectedIds.contains(item.id)
            if (isSelectionMode) {
                binding.viewSelectionMask.visibility = if (isSelected) View.VISIBLE else View.GONE
                binding.ivCheckIndicator.visibility = View.VISIBLE
                binding.ivCheckIndicator.setImageResource(
                    if (isSelected) R.drawable.ic_check_circle_selected
                    else R.drawable.ic_check_circle_unselected
                )
            } else {
                binding.viewSelectionMask.visibility = View.GONE
                binding.ivCheckIndicator.visibility = View.GONE
            }
        }
    }

    class ResourceDiffCallback : DiffUtil.ItemCallback<ResourceEntity>() {
        override fun areItemsTheSame(oldItem: ResourceEntity, newItem: ResourceEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResourceEntity, newItem: ResourceEntity): Boolean {
            return oldItem == newItem
        }
    }
}
