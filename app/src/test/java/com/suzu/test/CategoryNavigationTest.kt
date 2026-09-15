package com.suzu.test

import com.suzu.test.ui.view.adjacentCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CategoryNavigationTest {
    @Test fun followsVisibleOrderRatherThanCategoryId() {
        val keys = listOf("ALL", "42", "3", "19")
        assertEquals("3", adjacentCategory(keys, "42", 1))
        assertEquals("42", adjacentCategory(keys, "3", -1))
        assertEquals("19", adjacentCategory(listOf("ALL", "42", "19", "3"), "42", 1))
    }

    @Test fun respectsImeVisibleSpecialTabs() {
        assertEquals("cat:9", adjacentCategory(listOf("RECENT", "cat:9"), "RECENT", 1))
        assertEquals("RECENT", adjacentCategory(listOf("SEARCH", "RECENT", "ALL"), "SEARCH", 1))
    }

    @Test fun doesNotWrapOrJumpFromDeletedCategory() {
        val keys = listOf("ALL", "7")
        assertNull(adjacentCategory(keys, "ALL", -1))
        assertNull(adjacentCategory(keys, "7", 1))
        assertNull(adjacentCategory(keys, "deleted", 1))
        assertNull(adjacentCategory(emptyList(), "ALL", 1))
        assertNull(adjacentCategory(keys, "ALL", 2))
    }
}
