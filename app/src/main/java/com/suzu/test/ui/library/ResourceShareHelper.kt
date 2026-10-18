package com.suzu.test.ui.library

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.suzu.test.BuildConfig
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.log.TestLog
import java.io.File

internal object ResourceShareHelper {
    private const val MODULE = "ResourceShareHelper"

    fun share(activity: Activity, resource: ResourceEntity) {
        val file = File(File(activity.filesDir, "resources"), resource.filename)

        if (!file.isFile) {
            Toast.makeText(activity, "图片文件不存在，无法分享", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val uri = FileProvider.getUriForFile(
                activity,
                BuildConfig.FILE_PROVIDER_AUTHORITY,
                file
            )
            val mimeType = activity.contentResolver.getType(uri)
                ?: when {
                    resource.isAnimated || resource.format.equals("gif", ignoreCase = true) ->
                        "image/gif"
                    resource.format.equals("webp", ignoreCase = true) ->
                        "image/webp"
                    resource.format.equals("jpg", ignoreCase = true) ||
                        resource.format.equals("jpeg", ignoreCase = true) ->
                        "image/jpeg"
                    else -> "image/png"
                }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                clipData = ClipData.newRawUri("suzu_resource_image", uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            activity.startActivity(Intent.createChooser(shareIntent, "分享图片"))
        } catch (e: Exception) {
            TestLog.e(MODULE, "拉起系统分享选择器失败: ${e.message}", e)
            Toast.makeText(activity, "无法分享图片", Toast.LENGTH_SHORT).show()
        }
    }
}
