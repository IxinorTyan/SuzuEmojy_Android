package com.suzu.test.ime.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.suzu.test.db.DatabaseProvider
import com.suzu.test.ime.ImageItem
import com.suzu.test.log.TestLog
import java.io.File
import java.util.concurrent.Executors

class ResourceGridLoader(private val context: Context) {

    companion object {
        private const val MODULE = "ResourceGridLoader"
        private const val LOAD_LIMIT = 200
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private val loadExecutor = Executors.newSingleThreadExecutor()
    private val resourcesDir = File(context.filesDir, "resources").apply {
        if (!exists()) mkdirs()
    }

    fun loadResources(callback: (List<ImageItem>) -> Unit) {
        loadExecutor.execute {
            val list = mutableListOf<ImageItem>()
            try {
                val db = DatabaseProvider.getDatabase(context)
                val allEntities = kotlinx.coroutines.runBlocking {
                    db.resourceDao().getAllResourcesOrderedList()
                }

                val sorted = allEntities.take(LOAD_LIMIT)

                for (entity in sorted) {
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
                TestLog.i(MODULE, "Suzu 资源库加载完成: 共 ${list.size} 张表情")
            } catch (e: Exception) {
                TestLog.e(MODULE, "读取 Suzu 资源库失败: ${e.message}", e)
            }

            mainHandler.post {
                callback(list)
            }
        }
    }

    fun destroy() {
        loadExecutor.shutdown()
    }
}
