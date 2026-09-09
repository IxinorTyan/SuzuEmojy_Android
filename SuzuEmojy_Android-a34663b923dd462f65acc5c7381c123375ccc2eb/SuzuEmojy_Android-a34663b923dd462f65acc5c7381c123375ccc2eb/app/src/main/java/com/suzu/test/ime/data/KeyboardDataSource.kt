package com.suzu.test.ime.data

import android.content.Context
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.ime.ImageItem
import com.suzu.test.log.TestLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File

class KeyboardDataSource(private val context: Context) {

    companion object {
        private const val MODULE = "KeyboardDataSource"
        private const val QUERY_LIMIT = 200
    }

    private val resourcesDir = File(context.filesDir, "resources").apply {
        if (!exists()) mkdirs()
    }

    suspend fun loadResources(tabKey: String): List<ImageItem> = withContext(Dispatchers.IO) {
        val list = mutableListOf<ImageItem>()
        try {
            val db = DatabaseProvider.getDatabase(context)
            val entities: List<ResourceEntity> = when {
                tabKey == "RECENT" -> {
                    db.resourceDao().getRecentResourcesFlow(QUERY_LIMIT).first()
                }
                tabKey == "ALL" -> {
                    db.resourceDao().getAllResourcesOrdered().first().take(QUERY_LIMIT)
                }
                tabKey.startsWith("cat:") -> {
                    val catId = tabKey.removePrefix("cat:").toLongOrNull() ?: 0L
                    db.resourceCategoryDao().getResourcesForCategory(catId).first().take(QUERY_LIMIT)
                }
                else -> emptyList()
            }

            for (entity in entities) {
                val file = File(resourcesDir, entity.filename)
                if (file.exists()) {
                    val mime = if (entity.isAnimated) "image/gif" else "image/png"
                    list.add(
                        ImageItem.SuzuResource(
                            id = entity.id,
                            file = file,
                            format = entity.format,
                            isAnimated = entity.isAnimated,
                            displayName = entity.filename,
                            mimeType = mime
                        )
                    )
                }
            }
            TestLog.i(MODULE, "loadResources: tabKey=$tabKey, 加载到 ${list.size} 张表情")
        } catch (e: Exception) {
            TestLog.e(MODULE, "loadResources 异常: ${e.message}", e)
        }
        list
    }
}
