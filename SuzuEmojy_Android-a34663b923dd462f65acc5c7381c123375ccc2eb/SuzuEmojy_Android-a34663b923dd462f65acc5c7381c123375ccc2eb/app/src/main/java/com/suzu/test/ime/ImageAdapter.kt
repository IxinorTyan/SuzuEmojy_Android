package com.suzu.test.ime

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.databinding.ItemImageGridBinding
import com.suzu.test.ime.theme.KeyboardTheme

class ImageAdapter(
    private val onItemClick: (ImageItem) -> Unit,
    private val onItemLongClick: (ImageItem, View) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val items = mutableListOf<ImageItem>()

    fun submitList(newItems: List<ImageItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.binding.ivThumbnail)
    }

    override fun getItemCount(): Int = items.size

    inner class ImageViewHolder(
        val binding: ItemImageGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem) {
            val theme = KeyboardTheme.current(itemView.context)
            val itemBgDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(theme.itemBgColor)
                val strokeWidthPx = (1 * itemView.resources.displayMetrics.density).toInt().coerceAtLeast(1)
                setStroke(strokeWidthPx, theme.itemBorderColor)
                cornerRadius = 4 * itemView.resources.displayMetrics.density
            }
            binding.flItemRoot.background = itemBgDrawable
            binding.flItemRoot.isSelected = false

            when (item) {
                is ImageItem.AssetSample -> {
                    binding.tvFormatBadge.visibility = View.VISIBLE
                    binding.tvFormatBadge.text = item.badgeText
                    try {
                        val inputStream = itemView.context.assets.open(item.assetFileName)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        binding.ivThumbnail.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Glide.with(itemView.context)
                            .load("file:///android_asset/${item.assetFileName}")
                            .override(250, 250)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding.ivThumbnail)
                    }
                }
                is ImageItem.MediaStoreImage -> {
                    binding.tvFormatBadge.visibility = View.GONE
                    Glide.with(itemView.context)
                        .asBitmap()
                        .load(item.uri)
                        .override(250, 250)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.ivThumbnail)
                }
                is ImageItem.SuzuResource -> {
                    val isGif = item.format.equals("gif", ignoreCase = true)
                    binding.tvFormatBadge.visibility = if (isGif) View.VISIBLE else View.GONE
                    Glide.with(itemView.context)
                        .asBitmap()
                        .load(item.file)
                        .override(250, 250)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.ivThumbnail)
                }
            }

            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(item)
                }
            }

            itemView.setOnLongClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemLongClick(item, itemView)
                }
                true
            }
        }
    }
}
