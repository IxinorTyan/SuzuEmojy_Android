package com.suzu.test.ui.library

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class LibraryDragHelper(
    private val scope: CoroutineScope,
    private val adapter: LibraryAdapter,
    private val isDragAllowed: () -> Boolean,
    private val isAllSelected: () -> Boolean,
    private val getSelectedCategoryId: () -> Long?
) {

    private var inMemoryItems: MutableList<ResourceEntity> = mutableListOf()
    lateinit var itemTouchHelper: ItemTouchHelper
        private set

    fun updateItems(items: List<ResourceEntity>) {
        inMemoryItems = items.toMutableList()
    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            0
        ) {
            override fun isLongPressDragEnabled(): Boolean {
                return false // 彻底禁用系统默认的 500ms 长按起拖，改用 1000ms 自定义触摸检测
            }

            override fun onMove(
                rv: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                if (fromPos < 0 || toPos < 0 || fromPos >= inMemoryItems.size || toPos >= inMemoryItems.size) {
                    return false
                }

                Collections.swap(inMemoryItems, fromPos, toPos)
                adapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.apply {
                        elevation = 16f
                        scaleX = 1.05f
                        scaleY = 1.05f
                    }
                } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    val orderedIds = inMemoryItems.map { it.id }
                    val context = recyclerView.context
                    val inAll = isAllSelected()
                    val categoryId = getSelectedCategoryId()

                    scope.launch {
                        withContext(Dispatchers.IO) {
                            val db = DatabaseProvider.getDatabase(context)
                            if (inAll) {
                                db.resourceDao().reorderAll(orderedIds)
                            } else if (categoryId != null && categoryId > 0) {
                                db.resourceCategoryDao().reorderResourcesInCategory(categoryId, orderedIds)
                            }
                        }
                    }
                }
            }

            override fun clearView(rv: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(rv, viewHolder)
                viewHolder.itemView.apply {
                    elevation = 0f
                    scaleX = 1.0f
                    scaleY = 1.0f
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && isCurrentlyActive) {
                    viewHolder.itemView.elevation = 16f
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun startDrag(viewHolder: RecyclerView.ViewHolder) {
        if (isDragAllowed()) {
            itemTouchHelper.startDrag(viewHolder)
        }
    }
}
