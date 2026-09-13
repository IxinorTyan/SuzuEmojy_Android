package com.suzu.test.floating

import com.suzu.test.floating.EdgeGestureController.Direction
import com.suzu.test.floating.EdgeGestureController.Side
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EdgeGestureControllerTest {

    @Test
    fun resolveDirection_recognizesHorizontalSwipes() {
        assertEquals(
            Direction.RIGHT,
            EdgeGestureController.resolveDirection(dx = 80f, dy = 10f, triggerDistance = 48f)
        )
        assertEquals(
            Direction.LEFT,
            EdgeGestureController.resolveDirection(dx = -80f, dy = -10f, triggerDistance = 48f)
        )
    }

    @Test
    fun resolveDirection_usesVerticalDirectionForDominantDiagonalSwipe() {
        assertEquals(
            Direction.UP,
            EdgeGestureController.resolveDirection(dx = 40f, dy = -50f, triggerDistance = 48f)
        )
        assertEquals(
            Direction.DOWN,
            EdgeGestureController.resolveDirection(dx = -40f, dy = 50f, triggerDistance = 48f)
        )
    }

    @Test
    fun resolveDirection_rejectsMovementBelowSelectedAxisThreshold() {
        assertNull(
            EdgeGestureController.resolveDirection(dx = 47f, dy = 0f, triggerDistance = 48f)
        )
        assertNull(
            EdgeGestureController.resolveDirection(dx = 0f, dy = -47f, triggerDistance = 48f)
        )
    }

    @Test
    fun resolveDirection_acceptsMovementAtThreshold() {
        assertEquals(
            Direction.RIGHT,
            EdgeGestureController.resolveDirection(dx = 48f, dy = 0f, triggerDistance = 48f)
        )
        assertEquals(
            Direction.UP,
            EdgeGestureController.resolveDirection(dx = 0f, dy = -48f, triggerDistance = 48f)
        )
    }

    @Test
    fun directionSupport_allowsOnlyInwardHorizontalSwipeForEachEdge() {
        assertTrue(EdgeGestureController.isDirectionSupported(Side.LEFT, Direction.RIGHT))
        assertFalse(EdgeGestureController.isDirectionSupported(Side.LEFT, Direction.LEFT))

        assertTrue(EdgeGestureController.isDirectionSupported(Side.RIGHT, Direction.LEFT))
        assertFalse(EdgeGestureController.isDirectionSupported(Side.RIGHT, Direction.RIGHT))
    }

    @Test
    fun directionSupport_allowsVerticalSwipesOnBothEdges() {
        for (side in Side.entries) {
            assertTrue(EdgeGestureController.isDirectionSupported(side, Direction.UP))
            assertTrue(EdgeGestureController.isDirectionSupported(side, Direction.DOWN))
        }
    }
}
