package com.suzu.test.ui.import

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.suzu.test.R
import com.suzu.test.databinding.ActivityImportResultDetailBinding
import com.suzu.test.databinding.DialogFullImagePreviewBinding
import com.suzu.test.databinding.ItemImportDuplicateCardBinding
import com.suzu.test.databinding.ItemImportFailureCardBinding
import com.suzu.test.databinding.ItemImportSourceThumbBinding
import java.io.File

class ImportResultDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImportResultDetailBinding
    private lateinit var resourcesDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportResultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resourcesDir = File(filesDir, "resources")

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.rvCards.layoutManager = LinearLayoutManager(this)
        binding.rvCards.adapter = AggregateCardAdapter(
            cards = ImportResultHolder.aggregateCards,
            resourcesDir = resourcesDir,
            onThumbClick = { uri, file ->
                showFullScreenPreview(uri, file)
            }
        )
    }

    private fun showFullScreenPreview(uri: Uri?, file: File?) {
        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val dialogBinding = DialogFullImagePreviewBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        val target = file ?: uri
        Glide.with(this)
            .load(target)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(dialogBinding.ivFullPreview)

        dialogBinding.root.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}

class AggregateCardAdapter(
    private val cards: List<ImportAggregateCard>,
    private val resourcesDir: File,
    private val onThumbClick: (Uri?, File?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_DUPLICATE = 1
        private const val TYPE_FAILURE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (cards[position]) {
            is ImportAggregateCard.DuplicateCard -> TYPE_DUPLICATE
            is ImportAggregateCard.FailureCard -> TYPE_FAILURE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_DUPLICATE -> {
                val binding = ItemImportDuplicateCardBinding.inflate(inflater, parent, false)
                DuplicateCardViewHolder(binding)
            }
            else -> {
                val binding = ItemImportFailureCardBinding.inflate(inflater, parent, false)
                FailureCardViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = cards[position]) {
            is ImportAggregateCard.DuplicateCard -> (holder as DuplicateCardViewHolder).bind(item)
            is ImportAggregateCard.FailureCard -> (holder as FailureCardViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = cards.size

    inner class DuplicateCardViewHolder(private val binding: ItemImportDuplicateCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImportAggregateCard.DuplicateCard) {
            if (item.isTypeA) {
                binding.tvDuplicateDesc.text = "库中已有此表情，本次导入的 ${item.count} 张已全部合并"
                binding.tvMainBadge.text = "库中已有"
            } else {
                binding.tvDuplicateDesc.text = "本次选择的 ${item.count} 张完全相同，已导入 1 张"
                binding.tvMainBadge.text = "已入库"
            }

            val mainFile = resolvePreviewFile(item.mainPreviewFilePath, item.mainFilename)
            val mainLoadTarget: Any? = when {
                mainFile != null && mainFile.exists() -> mainFile
                else -> item.mainUri
            }

            Glide.with(itemView.context)
                .load(mainLoadTarget)
                .override(160, 160)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_image_item)
                .error(R.drawable.bg_image_item)
                .into(binding.ivMainImage)

            binding.ivMainImage.setOnClickListener {
                onThumbClick(item.mainUri, mainFile)
            }

            binding.rvSources.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvSources.adapter = SourceThumbAdapter(
                sources = item.itemUris.zip(item.itemPreviewFilePaths) { uri, path ->
                    ThumbSource(uri = uri, previewFile = resolvePreviewFile(path, null))
                }
            ) { clicked ->
                onThumbClick(clicked.uri, clicked.previewFile)
            }
        }

        private fun resolvePreviewFile(previewPath: String?, filename: String?): File? {
            return when {
                !previewPath.isNullOrBlank() -> File(previewPath)
                !filename.isNullOrBlank() -> File(resourcesDir, filename)
                else -> null
            }
        }
    }

    inner class FailureCardViewHolder(private val binding: ItemImportFailureCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImportAggregateCard.FailureCard) {
            binding.tvFailureTitle.text = item.reason.title
            binding.tvFailureCount.text = "共 ${item.count} 张"

            binding.rvSources.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvSources.adapter = SourceThumbAdapter(
                sources = item.itemUris.map { ThumbSource(uri = it, previewFile = null) }
            ) { clicked ->
                onThumbClick(clicked.uri, clicked.previewFile)
            }
        }
    }
}

data class ThumbSource(
    val uri: Uri,
    val previewFile: File?
)

class SourceThumbAdapter(
    private val sources: List<ThumbSource>,
    private val onItemClick: (ThumbSource) -> Unit
) : RecyclerView.Adapter<SourceThumbAdapter.ThumbViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbViewHolder {
        val binding = ItemImportSourceThumbBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThumbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbViewHolder, position: Int) {
        holder.bind(sources[position])
    }

    override fun getItemCount(): Int = sources.size

    inner class ThumbViewHolder(private val binding: ItemImportSourceThumbBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: ThumbSource) {
            val loadTarget: Any? = source.previewFile?.takeIf { it.exists() } ?: source.uri

            Glide.with(itemView.context)
                .load(loadTarget)
                .override(128, 128)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_image_item)
                .error(R.drawable.bg_image_item)
                .into(binding.ivThumb)

            binding.root.setOnClickListener {
                onItemClick(source)
            }
        }
    }
}
