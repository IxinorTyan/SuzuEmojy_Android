package com.suzu.test.floating

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ImeSearchStateHolderTest {

    @Before
    fun setUp() {
        ImeSearchStateHolder.clearSearch()
    }

    @Test
    fun setSearchQuery_validQuery_updatesFlow() {
        ImeSearchStateHolder.setSearchQuery("  测试搜索  ")
        assertEquals("测试搜索", ImeSearchStateHolder.searchQuery.value)
    }

    @Test
    fun setSearchQuery_emptyOrBlank_clearsFlow() {
        ImeSearchStateHolder.setSearchQuery("猫猫")
        assertEquals("猫猫", ImeSearchStateHolder.searchQuery.value)

        ImeSearchStateHolder.setSearchQuery("   ")
        assertNull(ImeSearchStateHolder.searchQuery.value)
    }

    @Test
    fun clearSearch_resetsQueryToNull() {
        ImeSearchStateHolder.setSearchQuery("狗狗")
        assertEquals("狗狗", ImeSearchStateHolder.searchQuery.value)

        ImeSearchStateHolder.clearSearch()
        assertNull(ImeSearchStateHolder.searchQuery.value)
    }

    @Test
    fun searchLaunchGuard_lifecycleFlow() {
        ImeSearchStateHolder.setSearchQuery("测试保护")
        org.junit.Assert.assertTrue(ImeSearchStateHolder.isSearchLaunching())

        ImeSearchStateHolder.onSearchImeShown()
        org.junit.Assert.assertFalse(ImeSearchStateHolder.isSearchLaunching())

        ImeSearchStateHolder.clearSearch()
        assertNull(ImeSearchStateHolder.searchQuery.value)
        org.junit.Assert.assertFalse(ImeSearchStateHolder.isSearchLaunching())
    }
}
