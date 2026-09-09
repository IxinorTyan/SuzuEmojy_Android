package com.suzu.test

import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.resource.KeywordUtils
import com.suzu.test.resource.MatchMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KeywordUtilsTest {

    @Test
    fun testParseAndNormalize() {
        val raw = "  猫  猫   开心  "
        val parsed = KeywordUtils.parse(raw)
        assertEquals(listOf("猫", "开心"), parsed)

        val normalized = KeywordUtils.normalize(raw)
        assertEquals("猫 开心", normalized)
    }

    @Test
    fun testPreserveCase() {
        val raw = "Cat Dog CAT"
        val normalized = KeywordUtils.normalize(raw)
        assertEquals("Cat Dog CAT", normalized) // distinct case-sensitive during parse
    }

    @Test
    fun testMergeTags() {
        val existing = "猫 开心"
        val newTags = "开心 难过"
        val merged = KeywordUtils.mergeTags(existing, newTags)
        assertEquals("猫 开心 难过", merged)
    }

    @Test
    fun testRemoveTags() {
        val existing = "猫 开心 难过 CAT"
        val removeTags = "开心 其它 cat"
        val remaining = KeywordUtils.removeTags(existing, removeTags)
        assertEquals("猫 难过", remaining)
    }

    @Test
    fun testRemoveTagsCaseInsensitivePreservesOriginalCase() {
        val existing = "Cute Cat Dog"
        val removeTags = "dog"
        val remaining = KeywordUtils.removeTags(existing, removeTags)
        assertEquals("Cute Cat", remaining)
    }

    @Test
    fun testMatchesSubstring() {
        val res = ResourceEntity(
            id = 1,
            filename = "sample_cat.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:123",
            pixelMd5 = "123",
            fileMd5 = "123",
            width = 100,
            height = 100,
            byteSize = 1000,
            keywords = "CAT funny"
        )

        // 搜索 c -> 命中 CAT
        assertTrue(KeywordUtils.matches(res, "c", MatchMode.SUBSTRING))
        // 搜索 at -> 命中 CAT
        assertTrue(KeywordUtils.matches(res, "at", MatchMode.SUBSTRING))
        // 搜索 CaT -> 与 cat 相同
        assertTrue(KeywordUtils.matches(res, "CaT", MatchMode.SUBSTRING))
        // 搜索 cat -> 命中 filename / keywords
        assertTrue(KeywordUtils.matches(res, "cat", MatchMode.SUBSTRING))
        // 搜索 dog -> 不命中
        assertFalse(KeywordUtils.matches(res, "dog", MatchMode.SUBSTRING))
    }

    @Test
    fun testPrefixPriorityRanking() {
        val resPrefix = ResourceEntity(
            id = 1,
            filename = "img1.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:1",
            pixelMd5 = "1",
            fileMd5 = "1",
            width = 100,
            height = 100,
            byteSize = 1000,
            keywords = "cat lovely",
            sortOrder = 2
        )

        val resInfix = ResourceEntity(
            id = 2,
            filename = "img2.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:2",
            pixelMd5 = "2",
            fileMd5 = "2",
            width = 100,
            height = 100,
            byteSize = 1000,
            keywords = "scat meme",
            sortOrder = 0
        )

        val list = listOf(resInfix, resPrefix)
        val sorted = KeywordUtils.filterAndSort(list, "cat")

        // resPrefix has prefix match "cat" in "cat lovely", resInfix has infix match "cat" in "scat"
        // prefix hit must come before infix hit even if resInfix has smaller sortOrder
        assertEquals(2, sorted.size)
        assertEquals(resPrefix.id, sorted[0].id)
        assertEquals(resInfix.id, sorted[1].id)
    }
}
