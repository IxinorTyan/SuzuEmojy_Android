package com.suzu.test

import com.suzu.test.ui.library.FilterState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FilterStateTest {

    @Test
    fun testAllUnchecked() {
        // A1: 全不勾
        val state = FilterState(noKeywords = false, uncategorized = false, isGif = false, isNonGif = false)
        assertEquals(0, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(0, state.activeFilterCount)
        assertFalse(state.isActive)
    }

    @Test
    fun testOnlyA() {
        // A2: 仅 A 无关键词
        val state = FilterState(noKeywords = true, uncategorized = false, isGif = false, isNonGif = false)
        assertEquals(1, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(1, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testOnlyB() {
        // A3: 仅 B 未分类
        val state = FilterState(noKeywords = false, uncategorized = true, isGif = false, isNonGif = false)
        assertEquals(0, state.noKwParam)
        assertEquals(1, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(1, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testAAndB() {
        // A4: A + B
        val state = FilterState(noKeywords = true, uncategorized = true, isGif = false, isNonGif = false)
        assertEquals(1, state.noKwParam)
        assertEquals(1, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(2, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testOnlyC() {
        // A5: 仅 C 动图
        val state = FilterState(noKeywords = false, uncategorized = false, isGif = true, isNonGif = false)
        assertEquals(0, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(1, state.animParam)
        assertEquals(1, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testOnlyD() {
        // A6: 仅 D 静图
        val state = FilterState(noKeywords = false, uncategorized = false, isGif = false, isNonGif = true)
        assertEquals(0, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(2, state.animParam)
        assertEquals(1, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testCAndD() {
        // A7: C + D (加法组同勾 = 该组不形成限制, animParam = 0)
        val state = FilterState(noKeywords = false, uncategorized = false, isGif = true, isNonGif = true)
        assertEquals(0, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(2, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testAAndC() {
        // A8: A + C
        val state = FilterState(noKeywords = true, uncategorized = false, isGif = true, isNonGif = false)
        assertEquals(1, state.noKwParam)
        assertEquals(0, state.noCatParam)
        assertEquals(1, state.animParam)
        assertEquals(2, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testAAndBAndC() {
        // A9: A + B + C
        val state = FilterState(noKeywords = true, uncategorized = true, isGif = true, isNonGif = false)
        assertEquals(1, state.noKwParam)
        assertEquals(1, state.noCatParam)
        assertEquals(1, state.animParam)
        assertEquals(3, state.activeFilterCount)
        assertTrue(state.isActive)
    }

    @Test
    fun testAllChecked() {
        // A10: A + B + C + D
        val state = FilterState(noKeywords = true, uncategorized = true, isGif = true, isNonGif = true)
        assertEquals(1, state.noKwParam)
        assertEquals(1, state.noCatParam)
        assertEquals(0, state.animParam)
        assertEquals(4, state.activeFilterCount)
        assertTrue(state.isActive)
    }
}
