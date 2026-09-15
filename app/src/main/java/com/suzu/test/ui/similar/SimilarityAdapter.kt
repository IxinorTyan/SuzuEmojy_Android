package com.suzu.test.ui.similar

import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    private class HeaderHolder(val label: TextView) : RecyclerView.ViewHolder(label)
    private class PictureHolder(view: FrameLayout, val image: ImageView, val check: CheckBox, val info: TextView) : RecyclerView.ViewHolder(view)

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
        val context = parent.context
        fun dp(value: Int) = (value * context.resources.displayMetrics.density).toInt()
        if (viewType == 0) return HeaderHolder(TextView(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1, -2)
            setPadding(dp(4), dp(16), dp(4), dp(8))
            textSize = 15f
        })
        val frame = FrameLayout(context).apply {
            layoutParams = RecyclerView.LayoutParams(-1, dp(184))
            setPadding(dp(3), dp(3), dp(3), dp(3))
        }
        val image = ImageView(context).apply { scaleType = ImageView.ScaleType.FIT_CENTER }
        frame.addView(image, FrameLayout.LayoutParams(-1, -1).apply { bottomMargin = dp(72) })
        val info = TextView(context).apply { textSize = 11f; gravity = android.view.Gravity.CENTER }
        frame.addView(info, FrameLayout.LayoutParams(-1, dp(24), android.view.Gravity.BOTTOM).apply { bottomMargin = dp(48) })
        val check = CheckBox(context).apply { text = "选择删除"; textSize = 12f }
        frame.addView(check, FrameLayout.LayoutParams(-1, dp(48), android.view.Gravity.BOTTOM))
        return PictureHolder(frame, image, check, info)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val row = rows[position]) {
            is Row.Header -> (holder as HeaderHolder).label.text = "第 ${row.number} 组 · ${if (row.animated) "动图" else "静态图"} · ${row.count} 张"
            is Row.Picture -> {
                holder as PictureHolder
                holder.info.text = "${row.resource.width} × ${row.resource.height}" +
                    (durations[row.resource.id]?.let { " · %.2f 秒".format(it / 1000.0) } ?: "")
                Glide.with(holder.image).load(File(directory, row.resource.filename)).fitCenter().into(holder.image)
                holder.image.contentDescription = "查看图片，${row.resource.width} × ${row.resource.height}"
                holder.image.setOnClickListener { preview(row.group, row.resource) }
                holder.image.isEnabled = interactive
                holder.check.setOnCheckedChangeListener(null)
                holder.check.isChecked = row.resource.id in selected
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
