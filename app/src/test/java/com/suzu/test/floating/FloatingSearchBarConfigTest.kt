package com.suzu.test.floating

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FloatingSearchBarConfigTest {

    @Test
    fun constants_verifyBoundsAndKey() {
        assertEquals("floating_search_bar_top_margin_dp", FloatingBallConfig.KEY_SEARCH_BAR_TOP_MARGIN_DP)
        assertEquals(0, FloatingBallConfig.MIN_SEARCH_BAR_TOP_MARGIN_DP)
        assertEquals(1000, FloatingBallConfig.MAX_SEARCH_BAR_TOP_MARGIN_DP)
        assertTrue(FloatingBallConfig.MIN_SEARCH_BAR_TOP_MARGIN_DP < FloatingBallConfig.MAX_SEARCH_BAR_TOP_MARGIN_DP)
    }

    @Test
    fun marginClamping_coercesWithinValidRange() {
        val min = FloatingBallConfig.MIN_SEARCH_BAR_TOP_MARGIN_DP
        val max = FloatingBallConfig.MAX_SEARCH_BAR_TOP_MARGIN_DP

        // Test below min
        assertEquals(min, (-50).coerceIn(min, max))
        assertEquals(min, (-1).coerceIn(min, max))

        // Test normal values
        assertEquals(0, 0.coerceIn(min, max))
        assertEquals(40, 40.coerceIn(min, max))
        assertEquals(300, 300.coerceIn(min, max))
        assertEquals(1000, 1000.coerceIn(min, max))

        // Test above max
        assertEquals(max, 1001.coerceIn(min, max))
        assertEquals(max, 9999.coerceIn(min, max))
    }

    @Test
    fun searchScopeConstants_verifyValuesAndDefaults() {
        assertEquals("floating_search_bar_scope", FloatingBallConfig.KEY_SEARCH_BAR_SCOPE)
        assertEquals(0, FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY)
        assertEquals(1, FloatingBallConfig.SEARCH_SCOPE_CATEGORY_ONLY)
        assertEquals(2, FloatingBallConfig.SEARCH_SCOPE_TAG_AND_CATEGORY)
        assertEquals(FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY, FloatingBallConfig.DEFAULT_SEARCH_BAR_SCOPE)

        // Clamping logic
        val min = FloatingBallConfig.SEARCH_SCOPE_TAG_ONLY
        val max = FloatingBallConfig.SEARCH_SCOPE_TAG_AND_CATEGORY
        assertEquals(min, (-1).coerceIn(min, max))
        assertEquals(0, 0.coerceIn(min, max))
        assertEquals(1, 1.coerceIn(min, max))
        assertEquals(2, 2.coerceIn(min, max))
        assertEquals(max, 3.coerceIn(min, max))
    }
}
