package com.suzu.test.ui.picker

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.R
import com.suzu.test.databinding.ItemMediaPickerGridBinding

class MediaPickerAdapter(
    private val onItemClick: (PickerMediaItem, Int) -> Unit
) : ListAdapter<PickerMediaItem, MediaPickerAdapter.MediaViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val PAYLOAD_SELECTION = "payload_selection"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PickerMediaItem>() {
            override fun areItemsTheSame(oldItem: PickerMediaItem, newItem: PickerMediaItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PickerMediaItem, newItem: PickerMediaItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val selectedUriOrderMap = HashMap<Uri, Int>()
    private var totalSelectedCount = 0

    fun updateSelectionState(orderMap: Map<Uri, Int>, totalCount: Int) {
        selectedUriOrderMap.clear()
        selectedUriOrderMap.putAll(orderMap)
        totalSelectedCount = totalCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewGroupResId: Int): MediaViewHolder {
        val binding = ItemMediaPickerGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_SELECTION)) {
            holder.bindSelectionOnly(getItem(position))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    inner class MediaViewHolder(
        private val binding: ItemMediaPickerGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(pos), pos)
                }
            }
        }

        fun bind(item: PickerMediaItem) {
            Glide.with(binding.ivThumbnail)
                .load(item.uri)
                .override(300, 300)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivThumbnail)

            binding.tvImportedBadge.visibility = if (item.isLikelyImported) View.VISIBLE else View.GONE
            bindSelectionOnly(item)
        }

        fun bindSelectionOnly(item: PickerMediaItem) {
            val order = selectedUriOrderMap[item.uri]
            if (order != null) {
                binding.viewMask.visibility = View.VISIBLE
                binding.tvSelectOrder.setBackgroundResource(R.drawable.bg_picker_select_indicator)
                if (totalSelectedCount > 200) {
                    binding.tvSelectOrder.text = "✓"
                } else {
                    binding.tvSelectOrder.text = order.toString()
                }
            } else {
                binding.viewMask.visibility = View.GONE
                binding.tvSelectOrder.setBackgroundResource(R.drawable.bg_picker_unselect_indicator)
                binding.tvSelectOrder.text = ""
            }
        }
    }
}
