package com.suzu.test.ui.view

/** Uses the rendered order, without wrapping or jumping from a missing selection. */
fun adjacentCategory(keys: List<String>, current: String, step: Int): String? {
    if (step != -1 && step != 1) return null
    val index = keys.indexOf(current)
    return if (index < 0) null else keys.getOrNull(index + step)
}
