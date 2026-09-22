package com.suzu.test.ui.library

import android.content.Context
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.suzu.test.databinding.BottomSheetCategoryReorderBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryReorderBottomSheet(
    context: Context,
    private val scope: CoroutineScope,
    private val categories: List<CategoryEntity>,
    private val selectedCategoryId: String,
    private val onDismissCallback: () -> Unit
) : BottomSheetDialog(context) {
    private lateinit var binding: BottomSheetCategoryReorderBinding
    private lateinit var adapter: CategoryReorderAdapter
    private var saving = false
    private val sheetJob = Job(scope.coroutineContext[Job])
    private val sheetScope = CoroutineScope(scope.coroutineContext + sheetJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetCategoryReorderBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        // A bounded sheet gives the grid a real scrolling viewport even with hundreds of categories.
        binding.root.layoutParams = binding.root.layoutParams.apply {
            height = (context.resources.displayMetrics.heightPixels * 0.82f).toInt()
        }
        behavior.skipCollapsed = true
        behavior.isDraggable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        adapter = CategoryReorderAdapter(sheetScope, categories, selectedCategoryId.toLongOrNull())
        val grid = GridLayoutManager(context, 3)
        binding.rvCategoriesReorder.layoutManager = grid
        binding.rvCategoriesReorder.adapter = adapter
        binding.rvCategoriesReorder.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
            val widthDp = view.width / context.resources.displayMetrics.density
            val span = if (widthDp >= 380) 4 else 3
            if (grid.spanCount != span) grid.spanCount = span
        }

        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0
        ) {
            override fun getMovementFlags(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder): Int =
                if (saving || !adapter.isMovable(holder.bindingAdapterPosition)) 0
                else super.getMovementFlags(recyclerView, holder)

            override fun canDropOver(recyclerView: RecyclerView, current: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) =
                adapter.isMovable(target.bindingAdapterPosition)

            override fun onMove(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) =
                adapter.move(holder.bindingAdapterPosition, target.bindingAdapterPosition)

            override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) = Unit

            override fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(holder, actionState)
                val dragging = actionState == ItemTouchHelper.ACTION_STATE_DRAG
                binding.tvDone.isEnabled = !dragging && !saving
                if (dragging) holder?.itemView?.apply {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    scaleX = 1.06f
                    scaleY = 1.06f
                    alpha = 0.9f
                }
            }

            override fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, holder)
                holder.itemView.apply { scaleX = 1f; scaleY = 1f; alpha = 1f }
            }
        })
        touchHelper.attachToRecyclerView(binding.rvCategoriesReorder)
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvDone.setOnClickListener { saveOrder() }
        setOnDismissListener {
            touchHelper.attachToRecyclerView(null)
            binding.rvCategoriesReorder.adapter = null
            sheetJob.cancel()
            onDismissCallback()
        }
    }

    private fun saveOrder() {
        if (saving) return
        val orderedIds = adapter.orderedIds
        if (orderedIds == categories.map { it.id }) { dismiss(); return }
        saving = true
        binding.tvDone.isEnabled = false
        binding.tvCancel.isEnabled = false
        binding.tvDone.contentDescription = "保存中"
        binding.tvDone.tooltipText = "保存中"
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        sheetScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    DatabaseProvider.getDatabase(context).categoryDao().reorderCategories(orderedIds)
                }
                dismiss()
            } catch (error: Exception) {
                if (error is CancellationException) throw error
                saving = false
                binding.tvDone.isEnabled = true
                binding.tvCancel.isEnabled = true
                binding.tvDone.contentDescription = "完成"
                binding.tvDone.tooltipText = "完成"
                setCancelable(true)
                setCanceledOnTouchOutside(true)
                Toast.makeText(context, "保存失败，请重试", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
