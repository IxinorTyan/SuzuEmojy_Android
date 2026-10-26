package com.suzu.test.ui.import

/** A source image and the single-level category assigned during folder import. */
data class FolderImportItem(
    val uri: android.net.Uri,
    val categoryName: String?
)

data class FolderImportCategory(
    val name: String?,
    val count: Int,
    val alreadyExists: Boolean = false
)

internal fun normalizeFolderCategoryName(name: String?): String? {
    val normalized = name?.trim()?.takeIf { it.isNotEmpty() } ?: return null
    return normalized.takeUnless {
        it.equals("ALL", ignoreCase = true) || it == "全部" || it == "最近"
    }
}

internal fun formatUnreadableDirectories(paths: List<String>): String {
    if (paths.isEmpty()) return ""
    val examples = paths.take(5).joinToString("\n")
    val remainder = if (paths.size > 5) "\n另有 ${paths.size - 5} 个文件夹" else ""
    return "有 ${paths.size} 个子文件夹读取失败，扫描结果不完整：\n$examples$remainder"
}
