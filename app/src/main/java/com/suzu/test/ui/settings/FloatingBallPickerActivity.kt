package com.suzu.test.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suzu.test.R
import com.suzu.test.databinding.ActivityFloatingBallPickerBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FloatingBallPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFloatingBallPickerBinding
    private val items = mutableListOf<ResourceEntity>()
    private lateinit var adapter: PickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloatingBallPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resourcesDir = File(filesDir, "resources")
        adapter = PickerAdapter(resourcesDir, items) { selectedItem ->
            val resultIntent = Intent().apply {
                putExtra("selected_resource_id", selectedItem.id)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.rvResourceGrid.layoutManager = GridLayoutManager(this, 4)
        binding.rvResourceGrid.adapter = adapter

        loadResourcesAsync()
    }

    private fun loadResourcesAsync() {
        binding.pbLoading.visibility = View.VISIBLE
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                val db = DatabaseProvider.getDatabase(this@FloatingBallPickerActivity)
                db.resourceDao().getAllResources()
            }

            items.clear()
            items.addAll(list)
            adapter.notifyDataSetChanged()
            binding.pbLoading.visibility = View.GONE

            if (items.isEmpty()) {
                binding.tvEmptyHint.visibility = View.VISIBLE
            } else {
                binding.tvEmptyHint.visibility = View.GONE
            }
        }
    }

    class PickerAdapter(
        private val resourcesDir: File,
        private val list: List<ResourceEntity>,
        private val onItemClick: (ResourceEntity) -> Unit
    ) : RecyclerView.Adapter<PickerAdapter.ViewHolder>() {

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val ivThumbnail: ImageView = v.findViewById(R.id.ivThumbnail)
            val tvBadge: TextView = v.findViewById(R.id.tvBadge)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_library_grid, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list[position]
            val file = File(resourcesDir, item.filename)
            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(file)
                .centerCrop()
                .into(holder.ivThumbnail)

            val isGif = item.format.equals("gif", ignoreCase = true)
            holder.tvBadge.visibility = if (isGif) View.VISIBLE else View.GONE

            holder.itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        override fun getItemCount(): Int = list.size
    }
}
