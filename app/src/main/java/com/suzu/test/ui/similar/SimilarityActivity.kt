package com.suzu.test.ui.similar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.suzu.test.databinding.ActivitySimilarityBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.resource.delete.ResourceDeleteHelper
import com.suzu.test.ui.library.ResourceDetailActivity
import kotlinx.coroutines.launch
import java.io.File

class SimilarityActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimilarityBinding
    private val model: SimilarityViewModel by viewModels()
    private lateinit var adapter: SimilarityAdapter
    private var displayedCategories: List<CategoryEntity>? = null
    private val preview = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        model.refreshExisting()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimilarityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener { finish() }
        adapter = SimilarityAdapter(File(filesDir, "resources"), { group, resource ->
            if (!model.state.value.busy && !model.state.value.deleting) {
                preview.launch(Intent(this, ResourceDetailActivity::class.java)
                    .putExtra(ResourceDetailActivity.EXTRA_RESOURCE_IDS, group.map { it.id }.toLongArray())
                    .putExtra(ResourceDetailActivity.EXTRA_START_POSITION, group.indexOf(resource)))
            }
        }, { model.toggle(it) })
        val columns = (resources.configuration.screenWidthDp / 110).coerceIn(3, 6)
        binding.rvGroups.layoutManager = GridLayoutManager(this, columns).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = if (adapter.isHeader(position)) columns else 1
            }
        }
        binding.rvGroups.adapter = adapter
        binding.btnScan.setOnClickListener { model.scan() }
        binding.btnCancel.setOnClickListener { model.cancel() }
        binding.spinnerMedia.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            SimilarityMedia.values().map { it.label })
        binding.spinnerMedia.setSelection(model.state.value.media.ordinal)
        binding.spinnerMedia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.chooseMedia(SimilarityMedia.values()[position])
            }
        }
        binding.seekThreshold.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Accessibility and keyboard adjustments do not send a touch-stop callback.
                if (fromUser && !seekBar.isPressed) model.setThreshold(progress)
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) { model.setThreshold(seekBar.progress) }
        })
        binding.spinnerScope.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = displayedCategories?.getOrNull(position - 1)?.id ?: 0L
                model.chooseCategory(category)
            }
        }
        binding.btnDelete.setOnClickListener { confirmDelete() }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { model.state.collect { render(it) } }
        }
    }

    private fun render(state: SimilarityState) {
        val deleting = state.deleting
        binding.spinnerMedia.isEnabled = !state.busy && !deleting
        if (displayedCategories != state.categories) {
            displayedCategories = state.categories
            binding.spinnerScope.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                listOf("全部资源") + state.categories.map { "分类：${it.name}" })
            binding.spinnerScope.setSelection(state.categories.indexOfFirst { it.id == state.categoryId } + 1)
        }
        binding.spinnerScope.isEnabled = !state.busy && !deleting
        binding.seekThreshold.isEnabled = !state.busy && !deleting
        if (!binding.seekThreshold.isPressed) binding.seekThreshold.progress = state.threshold
        binding.btnScan.isEnabled = !state.busy && !deleting
        binding.btnScan.text = if (state.scanned) "重新扫描" else "开始扫描"
        binding.btnCancel.visibility = if (state.busy) View.VISIBLE else View.GONE
        binding.progress.visibility = if (state.busy) View.VISIBLE else View.GONE
        binding.tvStatus.text = state.message
        binding.btnDelete.text = "删除所选（${state.selected.size}）"
        binding.btnDelete.isEnabled = state.selected.isNotEmpty() && !state.busy && !deleting
        adapter.submit(state.groups, state.selected, !state.busy && !deleting, state.durations)
    }

    private fun confirmDelete() {
        val state = model.state.value
        val selected = state.groups.flatten().filter { it.id in state.selected }.distinctBy { it.id }
        if (selected.isEmpty() || state.busy || state.deleting) return
        AlertDialog.Builder(this)
            .setTitle("删除所选图片")
            .setMessage("将从资源库及所有相关分类中永久删除所选的 ${selected.size} 张图片，此操作不可恢复。\n疑似相似不代表内容相同，请确认已逐张比较。")
            .setNegativeButton("取消", null)
            .setPositiveButton("删除") { _, _ ->
                if (!model.beginDeletion()) return@setPositiveButton
                val deletion = ResourceDeleteHelper.deleteResources(this, lifecycleScope, DatabaseProvider.getDatabase(this), selected) { result ->
                    Toast.makeText(this, "已删除 ${result.deletedCount} 张，失败 ${result.failedCount} 张" +
                        if (result.isCancelled) "（已取消）" else "", Toast.LENGTH_LONG).show()
                }
                // Reconcile even when rotation cancels the Activity's deletion scope.
                deletion.invokeOnCompletion { runOnUiThread { model.finishDeletion() } }
            }.show()
    }
}
