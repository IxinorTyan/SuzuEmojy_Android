package com.suzu.test.ime.data

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import com.suzu.test.ime.ImageItem
import com.suzu.test.log.TestLog
import java.util.concurrent.Executors

class MediaStoreImageLoader(private val context: Context) {

    companion object {
        private const val MODULE = "SuzuEmojy"
    }

    private val mainHandler = Handler(Looper.getMainLooper())
    private val loadExecutor = Executors.newSingleThreadExecutor()

    fun loadImages(callback: (List<ImageItem>) -> Unit) {
        loadExecutor.execute {
            val list = mutableListOf<ImageItem>()

            try {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.DATE_MODIFIED
                )

                val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val bundle = Bundle().apply {
                        putInt(android.content.ContentResolver.QUERY_ARG_LIMIT, 100)
                        putStringArray(
                            android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS,
                            arrayOf(MediaStore.Images.Media.DATE_MODIFIED)
                        )
                        putInt(
                            android.content.ContentResolver.QUERY_ARG_SORT_DIRECTION,
                            android.content.ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                        )
                    }
                    context.contentResolver.query(uri, projection, bundle, null)
                } else {
                    val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
                    context.contentResolver.query(uri, projection, null, null, sortOrder)
                }

                cursor?.use { c ->
                    val idCol = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val nameCol = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                    val mimeCol = c.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
                    val dateCol = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

                    var count = 0
                    while (c.moveToNext() && count < 100) {
                        val id = c.getLong(idCol)
                        val name = c.getString(nameCol) ?: "image_$id"
                        val mime = c.getString(mimeCol) ?: "image/jpeg"
                        val date = c.getLong(dateCol)
                        val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                        list.add(ImageItem.MediaStoreImage(id, contentUri, name, mime, date))
                        count++
                    }
                }
                TestLog.i(MODULE, "相册图库加载完成: 共 ${list.size} 张用户图片")
            } catch (e: Exception) {
                TestLog.e(MODULE, "读取相册图片失败: ${e.message}", e)
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
