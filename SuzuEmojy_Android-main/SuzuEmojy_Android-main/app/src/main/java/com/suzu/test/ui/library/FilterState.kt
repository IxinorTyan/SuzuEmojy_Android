package com.suzu.test.ui.library

data class FilterState(
    val noKeywords: Boolean = false,
    val uncategorized: Boolean = false,
    val isGif: Boolean = false,
    val isNonGif: Boolean = false
) {
    val noKwParam: Int get() = if (noKeywords) 1 else 0
    val noCatParam: Int get() = if (uncategorized) 1 else 0
    val animParam: Int get() = when {
        isGif && !isNonGif -> 1
        !isGif && isNonGif -> 2
        else -> 0
    }
    val activeFilterCount: Int get() =
        (if (noKeywords) 1 else 0) + (if (uncategorized) 1 else 0) +
        (if (isGif) 1 else 0) + (if (isNonGif) 1 else 0)
    val isActive: Boolean get() = activeFilterCount > 0
}
