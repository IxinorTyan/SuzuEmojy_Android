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

    @Test
    fun testFilenameNotMatched() {
        val res = ResourceEntity(
            id = 10,
            filename = "cat_in_box_funny.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:10",
            pixelMd5 = "10",
            fileMd5 = "10",
            width = 100,
            height = 100,
            byteSize = 1000,
            keywords = "kitten cute",
            sortOrder = 0
        )

        // Filename contains "cat" and "funny", but keywords don't
        assertFalse(KeywordUtils.matches(res, "cat"))
        assertFalse(KeywordUtils.matches(res, "funny"))
        assertFalse(KeywordUtils.isPrefixMatch(res, "cat"))

        // Searching keywords matches
        assertTrue(KeywordUtils.matches(res, "kitten"))
        assertTrue(KeywordUtils.matches(res, "cute"))
    }

    @Test
    fun testSearchScopes() {
        val res = ResourceEntity(
            id = 20,
            filename = "img20.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:20",
            pixelMd5 = "20",
            fileMd5 = "20",
            width = 100,
            height = 100,
            byteSize = 1000,
            keywords = "happy smile",
            sortOrder = 0
        )
        val categories = listOf("MemeCategory", "Anime")

        // 1. SEARCH_SCOPE_TAG_ONLY
        val scopeTag = com.suzu.test.floating.FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY
        assertTrue(KeywordUtils.matches(res, "happy", categoryNames = categories, searchScope = scopeTag))
        assertFalse(KeywordUtils.matches(res, "meme", categoryNames = categories, searchScope = scopeTag))

        // 2. SEARCH_SCOPE_CATEGORY_ONLY
        val scopeCat = com.suzu.test.floating.FloatingBallConfig.SEARCH_SCOPE_CATEGORY_ONLY
        assertFalse(KeywordUtils.matches(res, "happy", categoryNames = categories, searchScope = scopeCat))
        assertTrue(KeywordUtils.matches(res, "meme", categoryNames = categories, searchScope = scopeCat))
        assertTrue(KeywordUtils.matches(res, "anime", categoryNames = categories, searchScope = scopeCat))

        // 3. SEARCH_SCOPE_TAG_AND_CATEGORY
        val scopeBoth = com.suzu.test.floating.FloatingBallConfig.SEARCH_SCOPE_TAG_AND_CATEGORY
        assertTrue(KeywordUtils.matches(res, "happy", categoryNames = categories, searchScope = scopeBoth))
        assertTrue(KeywordUtils.matches(res, "meme", categoryNames = categories, searchScope = scopeBoth))
        assertTrue(KeywordUtils.matches(res, "anime", categoryNames = categories, searchScope = scopeBoth))
        assertFalse(KeywordUtils.matches(res, "sad", categoryNames = categories, searchScope = scopeBoth))
    }

    @Test
    fun testFilterAndSortWithCategoryMap() {
        val res1 = ResourceEntity(
            id = 1,
            filename = "1.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:1",
            pixelMd5 = "1",
            fileMd5 = "1",
            width = 100,
            height = 100,
            byteSize = 100,
            keywords = "unrelated",
            sortOrder = 10
        )
        val res2 = ResourceEntity(
            id = 2,
            filename = "2.png",
            format = "PNG",
            isAnimated = false,
            syncKey = "p:2",
            pixelMd5 = "2",
            fileMd5 = "2",
            width = 100,
            height = 100,
            byteSize = 100,
            keywords = "dog",
            sortOrder = 5
        )

        val catMap = mapOf(1L to listOf("DogeFolder"))
        val scopeBoth = com.suzu.test.floating.FloatingBallConfig.SEARCH_SCOPE_TAG_AND_CATEGORY
        val result = KeywordUtils.filterAndSort(listOf(res1, res2), "dog", categoryMap = catMap, searchScope = scopeBoth)

        // Both should match: res2 via tag "dog", res1 via category "DogeFolder"
        assertEquals(2, result.size)
        // Both are prefix matches ("dog" and "dogefolder"), ordered by sortOrder (res2 has 5, res1 has 10)
        assertEquals(2L, result[0].id)
        assertEquals(1L, result[1].id)
    }
}
