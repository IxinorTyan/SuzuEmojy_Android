package com.suzu.test.ime

import android.net.Uri
import java.io.File

sealed class ImageItem {
    abstract val displayName: String
    abstract val mimeType: String

    data class AssetSample(
        override val displayName: String,
        override val mimeType: String,
        val assetFileName: String,
        val badgeText: String
    ) : ImageItem()

    data class MediaStoreImage(
        val id: Long,
        val uri: Uri,
        override val displayName: String,
        override val mimeType: String,
        val dateModified: Long
    ) : ImageItem()

    data class SuzuResource(
        val id: Long,
        val file: File,
        val format: String,
        val isAnimated: Boolean,
        override val displayName: String,
        override val mimeType: String
    ) : ImageItem()
}
