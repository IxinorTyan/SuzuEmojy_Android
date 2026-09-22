package com.suzu.test.floating

import org.junit.Assert.*
import org.junit.Test

class SearchLaunchStateTest {
    @Test fun thirdPartySearchYieldsWhenOwnImeTakesOver() {
        val state = SearchLaunchState()
        state.begin(0)
        state.activate()
        assertTrue(state.shouldYieldToOwnIme())
        state.close()
        assertFalse(state.shouldYieldToOwnIme())
    }

    @Test fun openingFromOwnImeDoesNotCloseOnOldLifecycleCallback() {
        val state = SearchLaunchState()
        state.begin(0)
        state.waiting()
        assertFalse(state.shouldYieldToOwnIme())
        assertFalse(state.expired(2499))
        assertTrue(state.expired(2500))
        state.activate()
        assertTrue(state.shouldYieldToOwnIme())
        assertFalse(state.expired(3000))
    }

    @Test fun cancelledPreparationCannotReopenOverlay() {
        val state = SearchLaunchState()
        val old = state.begin(0)
        state.close()
        val current = state.begin(100)
        assertFalse(state.accepts(old))
        assertTrue(state.accepts(current))
    }
}
