package com.suzu.test.ime

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ImeWindowTrackerTest {
    private val tracker = ImeWindowTracker()
    private val state get() = tracker.windowState.value

    @Test fun thirdPartyKeyboardShowsAndHidesWithWindowGeometry() {
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        assertTrue(state.visible)
        assertEquals(640, state.topPx)
        assertFalse(state.ownImeShowing)
        assertTrue(state.allowsImeOnlyBall)

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = null, now = 100)
        assertFalse(state.visible)
        assertNull(state.topPx)
        assertFalse(state.allowsImeOnlyBall)
    }

    @Test fun emptySnapshotDoesNotDismissVisibleKeyboardOrLoseItsBounds() {
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        val visible = state
        tracker.onAccessibilitySnapshot(available = false, imeTopPx = null, now = 100)

        assertEquals(ImeWindowObservation.UNAVAILABLE, state.observation)
        assertTrue(state.visible)
        assertEquals(640, state.topPx)
        assertFalse(state.visibilitySignalSince(visible))
    }

    @Test fun failOpenIsOnlyForTheBallAndEndsWhenObservationRecovers() {
        val before = state
        assertEquals(ImeWindowObservation.AWAITING_SNAPSHOT, before.observation)
        assertFalse(before.allowsImeOnlyBall)

        tracker.onAccessibilitySnapshot(available = false, imeTopPx = null, now = 50)
        assertTrue(state.allowsImeOnlyBall)
        assertFalse(state.visible)
        assertFalse(state.visibilitySignalSince(before))

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = null, now = 100)
        assertFalse(state.allowsImeOnlyBall)
        assertEquals(ImeWindowObservation.AVAILABLE, state.observation)
    }

    @Test fun ownHideClearsBoundsAndSuppressesResidualWindowForTwoHundredMilliseconds() {
        tracker.onOwnImeLifecycle(visible = true, now = 30)
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        tracker.onOwnImeLifecycle(visible = false, now = 100)
        assertFalse(state.visible)
        assertFalse(state.ownImeShowing)
        assertNull(state.topPx)

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 299)
        assertFalse(state.visible)
        assertNull(state.topPx)

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 620, now = 300)
        assertTrue(state.visible)
        assertEquals(620, state.topPx)
        assertFalse(state.ownImeShowing)
    }

    @Test fun ownImeReopeningOverridesARecentHideWithoutWaitingForWindowScan() {
        tracker.onOwnImeLifecycle(visible = false, now = 100)
        val hidden = state

        tracker.onOwnImeLifecycle(visible = true, now = 150)
        assertTrue(state.visible)
        assertTrue(state.ownShowSignalSince(hidden))
        assertTrue(state.visibilitySignalSince(hidden))
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 610, now = 170)
        assertEquals(610, state.topPx)
    }

    @Test fun boundsOnlyUpdateDoesNotRestartSearchVisibilityOrTriggerOwnImeTakeover() {
        tracker.onOwnImeLifecycle(visible = true, now = 20)
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        val previous = state

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 620, now = 100)
        assertEquals(620, state.topPx)
        assertFalse(state.visibilitySignalSince(previous))
        assertFalse(state.ownShowSignalSince(previous))
    }

    @Test fun ownLifecycleStillSignalsSearchWhenGeneralVisibilityWasAlreadyTrue() {
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        val textKeyboard = state
        tracker.onOwnImeLifecycle(visible = true, now = 100)

        assertTrue(state.ownShowSignalSince(textKeyboard))
        assertTrue(state.visibilitySignalSince(textKeyboard))
        val ownKeyboard = state
        tracker.onOwnImeLifecycle(visible = false, now = 200)
        assertFalse(state.ownShowSignalSince(ownKeyboard))
        assertTrue(state.visibilitySignalSince(ownKeyboard))
    }

    @Test fun latestHideCannotDeliverAnObsoleteOwnImeTakeover() {
        val previous = state
        tracker.onOwnImeLifecycle(visible = true, now = 100)
        tracker.onOwnImeLifecycle(visible = false, now = 101)

        assertTrue(state.visibilitySignalSince(previous))
        assertFalse(state.ownShowSignalSince(previous))
        assertFalse(state.visible)
    }

    @Test fun reconnectCanSeedAlreadyVisibleOwnImeWithoutReusingOldBoundsOrHideGuard() {
        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 640, now = 50)
        tracker.onOwnImeLifecycle(visible = false, now = 100)
        tracker.reset(ownImeShowing = true)

        assertEquals(ImeWindowObservation.AWAITING_SNAPSHOT, state.observation)
        assertTrue(state.visible)
        assertTrue(state.ownImeShowing)
        assertNull(state.topPx)

        tracker.onAccessibilitySnapshot(available = true, imeTopPx = 610, now = 120)
        assertEquals(610, state.topPx)
    }
}
