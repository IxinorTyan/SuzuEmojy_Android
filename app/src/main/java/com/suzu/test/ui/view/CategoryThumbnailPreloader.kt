package com.suzu.test.ui.view

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import android.graphics.Bitmap
import com.suzu.test.db.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import java.io.File

/** Warms only the first 24 thumbnails of each neighbour, using the grid's bitmap cache keys. */
class CategoryThumbnailPreloader(
    private val context: Context,
    private val scope: CoroutineScope,
    private val diskCacheStrategy: DiskCacheStrategy
) {
    // Resolve while the Activity is alive; cancel() also runs during onDestroy.
    private val requests = Glide.with(context)
    private var job: Job? = null
    private val targets = mutableListOf<Target<Bitmap>>()

    fun warm(keys: List<String>, current: String) {
        cancel()
        job = scope.launch {
            val db = DatabaseProvider.getDatabase(context)
            for (step in listOf(-1, 1)) {
                val key = adjacentCategory(keys, current, step) ?: continue
                val items = when {
                    key == "ALL" -> db.resourceDao().getThumbnailPreloadResources(24)
                    key == "RECENT" -> db.resourceDao().getRecentResourcesFlow(24).first()
                    key.removePrefix("cat:").toLongOrNull() != null ->
                        db.resourceCategoryDao().getThumbnailPreloadResources(key.removePrefix("cat:").toLong(), 24)
                    else -> continue
                }
                for (item in items) {
                    targets += requests.asBitmap()
                        .load(File(context.filesDir, "resources/${item.filename}"))
                        .override(250, 250).centerCrop()
                        .diskCacheStrategy(diskCacheStrategy)
                        .preload(250, 250)
                }
            }
        }
    }

    fun cancel() {
        job?.cancel()
        job = null
        targets.forEach { requests.clear(it) }
        targets.clear()
    }
}
