package com.suzu.test.db

sealed class CategoryIconResult {
    object Default : CategoryIconResult()
    data class Text(val content: String) : CategoryIconResult()
    data class Resource(val resourceId: Long) : CategoryIconResult()
    data class ImageFile(val relativePath: String) : CategoryIconResult()
}

object CategoryIconResolver {

    fun resolve(iconPath: String?): CategoryIconResult {
        if (iconPath.isNullOrBlank()) {
            return CategoryIconResult.Default
        }
        val trimmed = iconPath.trim()
        return when {
            trimmed.startsWith("file:") -> {
                val path = trimmed.removePrefix("file:")
                if (path.startsWith("category_icons/") && !path.contains("\\") && path.split('/').none { it == ".." }) {
                    CategoryIconResult.ImageFile(path)
                } else CategoryIconResult.Default
            }
            trimmed.startsWith("text:") -> {
                val content = trimmed.removePrefix("text:")
                if (content.isBlank()) CategoryIconResult.Default else CategoryIconResult.Text(content)
            }
            trimmed.startsWith("res:") -> {
                val idStr = trimmed.removePrefix("res:")
                val id = idStr.toLongOrNull()
                if (id != null && id > 0) CategoryIconResult.Resource(id) else CategoryIconResult.Default
            }
            else -> {
                CategoryIconResult.Text(trimmed)
            }
        }
    }
}
