package com.suzu.test.ui.similar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.suzu.test.R
import com.suzu.test.db.entity.ResourceEntity
import java.io.File

class SimilarityAdapter(
    private val directory: File,
    private val preview: (List<ResourceEntity>, ResourceEntity) -> Unit,
    private val toggle: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private sealed interface Row {
        data class Header(val number: Int, val count: Int, val animated: Boolean) : Row
        data class Picture(val resource: ResourceEntity, val group: List<ResourceEntity>) : Row
    }
    private var groups: List<List<ResourceEntity>> = emptyList()
    private var rows: List<Row> = emptyList()
    private var selected: Set<Long> = emptySet()
    private var interactive = true
    private var durations: Map<Long, Long> = emptyMap()

    private class HeaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.tvHeaderLabel)
    }

    private class PictureHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.cardPictureRoot)
        val image: ImageView = view.findViewById(R.id.ivPicture)
        val check: CheckBox = view.findViewById(R.id.cbSelect)
        val info: TextView = view.findViewById(R.id.tvPictureInfo)
    }

    fun submit(value: List<List<ResourceEntity>>, selection: Set<Long>, enabled: Boolean, durations: Map<Long, Long>) {
        this.durations = durations
        val enabledChanged = interactive != enabled
        interactive = enabled
        if (groups !== value) {
            groups = value
            rows = value.flatMapIndexed { index, group ->
                listOf(Row.Header(index + 1, group.size, group.first().isAnimated)) + group.map { Row.Picture(it, group) }
            }
            selected = selection
            notifyDataSetChanged()
        } else if (selected != selection) {
            val changed = (selected - selection) + (selection - selected)
            selected = selection
            rows.forEachIndexed { index, row ->
                if (row is Row.Picture && row.resource.id in changed) notifyItemChanged(index)
            }
        }
        if (enabledChanged) notifyItemRangeChanged(0, rows.size)
    }

    fun isHeader(position: Int) = rows.getOrNull(position) is Row.Header
    override fun getItemCount() = rows.size
    override fun getItemViewType(position: Int) = if (isHeader(position)) 0 else 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            HeaderHolder(inflater.inflate(R.layout.item_similarity_header, parent, false))
        } else {
            PictureHolder(inflater.inflate(R.layout.item_similarity_picture, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val row = rows[position]) {
            is Row.Header -> {
                (holder as HeaderHolder).label.text = "第 ${row.number} 组 · ${if (row.animated) "动图" else "静态图"} · ${row.count} 张"
            }
            is Row.Picture -> {
                holder as PictureHolder
                val isChecked = row.resource.id in selected
                holder.info.text = "${row.resource.width} × ${row.resource.height}" +
                    (durations[row.resource.id]?.let { " · %.2f秒".format(it / 1000.0) } ?: "")
                Glide.with(holder.image).load(File(directory, row.resource.filename)).fitCenter().into(holder.image)
                holder.image.contentDescription = "查看图片，${row.resource.width} × ${row.resource.height}"
                holder.image.setOnClickListener { preview(row.group, row.resource) }
                holder.image.isEnabled = interactive

                // 选中高亮卡片边框
                if (isChecked) {
                    holder.card.strokeColor = Color.parseColor("#2563EB")
                    holder.card.strokeWidth = (holder.card.context.resources.displayMetrics.density * 1.5f).toInt().coerceAtLeast(2)
                } else {
                    holder.card.strokeColor = Color.parseColor("#E2E8F0")
                    holder.card.strokeWidth = (holder.card.context.resources.displayMetrics.density * 1f).toInt().coerceAtLeast(1)
                }

                holder.check.setOnCheckedChangeListener(null)
                holder.check.isChecked = isChecked
                holder.check.isEnabled = interactive
                holder.check.contentDescription = "选择删除图片 ${row.resource.id}"
                holder.check.setOnCheckedChangeListener { _, _ -> toggle(row.resource.id) }
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is PictureHolder) Glide.with(holder.image).clear(holder.image)
        super.onViewRecycled(holder)
    }
}
