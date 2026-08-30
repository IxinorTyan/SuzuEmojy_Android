package com.suzu.test.ui.import

import android.net.Uri

enum class ImportItemType {
    NEW_ADDED,
    DUPLICATE,
    FAILED
}

enum class ImportFailReason(val title: String) {
    UNSUPPORTED_ANIM_WEBP("不支持的格式（动态 WebP 暂不支持）"),
    UNSUPPORTED_OTHER("不支持的图片格式"),
    DECODE_FAILED("文件损坏或无法解码为图片"),
    READ_FAILED("文件读取失败或文件为空"),
    UNKNOWN("未知导入错误")
}

data class ImportItemRecord(
    val sourceUri: Uri,
    val type: ImportItemType,
    val syncKey: String?,
    val resourceId: Long?,
    val filename: String?,
    val existingResourceId: Long?,
    val existingFilename: String?,
    val failReason: ImportFailReason?,
    val previewFilePath: String? = null
)

sealed class ImportAggregateCard {
    data class DuplicateCard(
        val isTypeA: Boolean, // true: 库中已有(类型A) / false: 本批次内部重复(类型B)
        val count: Int,
        val mainFilename: String?,
        val mainUri: Uri?,
        val mainPreviewFilePath: String?,
        val itemUris: List<Uri>,
        val itemPreviewFilePaths: List<String?>,
        val syncKey: String
    ) : ImportAggregateCard()

    data class FailureCard(
        val reason: ImportFailReason,
        val count: Int,
        val itemUris: List<Uri>
    ) : ImportAggregateCard()
}

object ImportResultHolder {
    var records: List<ImportItemRecord> = emptyList()
    var aggregateCards: List<ImportAggregateCard> = emptyList()

    fun clear() {
        records = emptyList()
        aggregateCards = emptyList()
    }
}
