package com.suzu.test.ui.library

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.suzu.test.databinding.BottomSheetCategoryReorderBinding
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryReorderBottomSheet(
    context: Context,
    private val scope: CoroutineScope,
    private val categories: List<CategoryEntity>,
    private val onDismissCallback: () -> Unit
) : BottomSheetDialog(context) {

    private lateinit var binding: BottomSheetCategoryReorderBinding
    private lateinit var adapter: CategoryReorderAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetCategoryReorderBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        adapter = CategoryReorderAdapter(scope) { viewHolder ->
            itemTouchHelper.startDrag(viewHolder)
        }
        adapter.setData(categories)

        binding.rvCategoriesReorder.layoutManager = LinearLayoutManager(context)
        binding.rvCategoriesReorder.adapter = adapter

        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun isLongPressDragEnabled(): Boolean = true

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                adapter.onItemMove(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    val orderedIds = adapter.items.map { it.id }
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            val db = DatabaseProvider.getDatabase(context)
                            db.categoryDao().reorderCategories(orderedIds)
                        }
                    }
                }
            }
        }

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvCategoriesReorder)

        binding.tvDone.setOnClickListener {
            dismiss()
        }

        setOnDismissListener {
            onDismissCallback()
        }
    }
}
