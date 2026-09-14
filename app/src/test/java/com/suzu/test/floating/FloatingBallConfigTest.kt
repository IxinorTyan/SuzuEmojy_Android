package com.suzu.test.floating

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FloatingBallConfigTest {

    @Test
    fun ballSize_verifyBoundsAndClamping() {
        assertEquals(20, FloatingBallConfig.MIN_BALL_SIZE_DP)
        assertEquals(160, FloatingBallConfig.MAX_BALL_SIZE_DP)
        assertEquals(56, FloatingBallConfig.DEFAULT_BALL_SIZE_DP)
        assertTrue(FloatingBallConfig.MIN_BALL_SIZE_DP < FloatingBallConfig.DEFAULT_BALL_SIZE_DP)
        assertTrue(FloatingBallConfig.DEFAULT_BALL_SIZE_DP < FloatingBallConfig.MAX_BALL_SIZE_DP)

        val min = FloatingBallConfig.MIN_BALL_SIZE_DP
        val max = FloatingBallConfig.MAX_BALL_SIZE_DP
        assertEquals(min, 0.coerceIn(min, max))
        assertEquals(min, 19.coerceIn(min, max))
        assertEquals(20, 20.coerceIn(min, max))
        assertEquals(56, 56.coerceIn(min, max))
        assertEquals(160, 160.coerceIn(min, max))
        assertEquals(max, 161.coerceIn(min, max))
        assertEquals(max, 300.coerceIn(min, max))
    }

    @Test
    fun ballAlpha_verifyBoundsAndClamping() {
        assertEquals(10, FloatingBallConfig.MIN_BALL_ALPHA)
        assertEquals(100, FloatingBallConfig.MAX_BALL_ALPHA)
        assertEquals(80, FloatingBallConfig.DEFAULT_BALL_ALPHA)

        val min = FloatingBallConfig.MIN_BALL_ALPHA
        val max = FloatingBallConfig.MAX_BALL_ALPHA
        assertEquals(min, 0.coerceIn(min, max))
        assertEquals(min, 9.coerceIn(min, max))
        assertEquals(10, 10.coerceIn(min, max))
        assertEquals(80, 80.coerceIn(min, max))
        assertEquals(100, 100.coerceIn(min, max))
        assertEquals(max, 101.coerceIn(min, max))
    }

    @Test
    fun animDuration_verifyBoundsAndClamping() {
        assertEquals(0, FloatingBallConfig.MIN_ANIM_DURATION_MS)
        assertEquals(1000, FloatingBallConfig.MAX_ANIM_DURATION_MS)
        assertEquals(100, FloatingBallConfig.DEFAULT_ANIM_DURATION_MS)

        val min = FloatingBallConfig.MIN_ANIM_DURATION_MS
        val max = FloatingBallConfig.MAX_ANIM_DURATION_MS
        assertEquals(min, (-10).coerceIn(min, max))
        assertEquals(0, 0.coerceIn(min, max))
        assertEquals(100, 100.coerceIn(min, max))
        assertEquals(500, 500.coerceIn(min, max))
        assertEquals(1000, 1000.coerceIn(min, max))
        assertEquals(max, 1200.coerceIn(min, max))
    }

    @Test
    fun edgeWidth_verifyBoundsAndClamping() {
        assertEquals(8, FloatingBallConfig.MIN_EDGE_WIDTH_DP)
        assertEquals(100, FloatingBallConfig.MAX_EDGE_WIDTH_DP)

        val min = FloatingBallConfig.MIN_EDGE_WIDTH_DP
        val max = FloatingBallConfig.MAX_EDGE_WIDTH_DP
        assertEquals(min, 0.coerceIn(min, max))
        assertEquals(min, 7.coerceIn(min, max))
        assertEquals(8, 8.coerceIn(min, max))
        assertEquals(24, 24.coerceIn(min, max))
        assertEquals(100, 100.coerceIn(min, max))
        assertEquals(max, 120.coerceIn(min, max))
    }

    @Test
    fun edgeTriggerDistance_verifyBoundsAndClamping() {
        assertEquals(20, FloatingBallConfig.MIN_EDGE_TRIGGER_DISTANCE_DP)
        assertEquals(300, FloatingBallConfig.MAX_EDGE_TRIGGER_DISTANCE_DP)

        val min = FloatingBallConfig.MIN_EDGE_TRIGGER_DISTANCE_DP
        val max = FloatingBallConfig.MAX_EDGE_TRIGGER_DISTANCE_DP
        assertEquals(min, 0.coerceIn(min, max))
        assertEquals(min, 19.coerceIn(min, max))
        assertEquals(20, 20.coerceIn(min, max))
        assertEquals(80, 80.coerceIn(min, max))
        assertEquals(300, 300.coerceIn(min, max))
        assertEquals(max, 500.coerceIn(min, max))
    }

    @Test
    fun keyboardSafetyDistance_verifyBoundsAndClamping() {
        assertEquals(0, FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX)
        assertEquals(2000, FloatingBallConfig.MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX)
        assertEquals(0, FloatingBallConfig.MIN_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX)
        assertEquals(2000, FloatingBallConfig.MAX_EDGE_LOWER_KEYBOARD_SAFETY_DISTANCE_PX)

        val min = FloatingBallConfig.MIN_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        val max = FloatingBallConfig.MAX_EDGE_KEYBOARD_SAFETY_DISTANCE_PX
        assertEquals(min, (-100).coerceIn(min, max))
        assertEquals(0, 0.coerceIn(min, max))
        assertEquals(150, 150.coerceIn(min, max))
        assertEquals(2000, 2000.coerceIn(min, max))
        assertEquals(max, 2500.coerceIn(min, max))
    }
}