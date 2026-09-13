package com.suzu.test.ui.picker

import android.net.Uri

data class PickerMediaItem(
    val id: Long,
    val uri: Uri,
    val displayName: String,
    val bucketId: String?,
    val bucketDisplayName: String?,
    val dateAdded: Long,
    val mimeType: String,
    val size: Long,
    val isLikelyImported: Boolean = false
)

data class PickerBucket(
    val bucketId: String?,
    val displayName: String,
    val count: Int
)
