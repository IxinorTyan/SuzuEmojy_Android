package com.suzu.test.ime.ui

/** A null list means categories are still loading, not that the saved folder was deleted. */
internal fun restoredImeTab(current: String, lastRegular: String, available: List<String>?): String {
    val requested = if (current == "SEARCH" && available?.contains("SEARCH") != true) {
        lastRegular.takeUnless { it == "SEARCH" } ?: "RECENT"
    } else current
    return if (available == null || requested in available) requested else available.firstOrNull() ?: "ALL"
}
