package com.suzu.test.ime

import org.junit.Assert.*
import org.junit.Test

class ImeSwitchAttemptTest {
    @Test fun unrelatedKeyboardNeverCompletesOrTriggersClick() {
        val attempt = ImeSwitchAttempt(1000)
        assertFalse(attempt.stable(1120, false))
        assertFalse(attempt.takeClick(1120, false, false))
        assertFalse(attempt.stable(1350, false))
    }

    @Test fun retriesAreBoundedAndStopAtDeadline() {
        val attempt = ImeSwitchAttempt(1000)
        assertFalse(attempt.takeClick(1119, true, false))
        assertTrue(attempt.takeClick(1120, true, false))
        assertFalse(attempt.takeClick(1349, true, false))
        assertTrue(attempt.takeClick(1350, true, false))
        assertFalse(attempt.takeClick(1700, true, false))
        assertTrue(attempt.expired(2000))
        assertFalse(ImeSwitchAttempt(1000).takeClick(2000, true, false))
    }

    @Test fun transientVisibilityDoesNotCompleteAttempt() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.stable(100, true))
        assertFalse(attempt.stable(150, false))
        assertFalse(attempt.stable(180, true))
        assertFalse(attempt.stable(279, true))
        assertTrue(attempt.stable(280, true))
        assertFalse(attempt.takeClick(280, true, true))
    }

    @Test fun lateFocusDoesNotCauseBackToBackClicks() {
        val attempt = ImeSwitchAttempt(0)
        assertTrue(attempt.takeClick(600, true, false))
        assertFalse(attempt.takeClick(650, true, false))
        assertTrue(attempt.takeClick(750, true, false))
    }

    @Test fun cancelledRequestCannotClickOrComplete() {
        val old = ImeSwitchAttempt(0)
        old.stable(0, true)
        old.cancel()
        assertFalse(old.takeClick(350, true, false))
        assertFalse(old.stable(350, true))
        val replacement = ImeSwitchAttempt(350)
        assertFalse(replacement.takeClick(350, true, false))
        assertTrue(replacement.takeClick(470, true, false))
    }
}
