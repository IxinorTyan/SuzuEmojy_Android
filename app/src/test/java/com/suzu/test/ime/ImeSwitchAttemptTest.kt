package com.suzu.test.ime

import org.junit.Assert.*
import org.junit.Test

class ImeSwitchAttemptTest {
    @Test fun lifecycleProtectionEndsAtDeadlineEvenBeforeTimeoutCallbackRuns() {
        val attempt = ImeSwitchAttempt(1000)
        assertTrue(attempt.isActive(3499))
        assertFalse(attempt.isActive(3500))
        attempt.stable(3400, true)
        assertFalse(attempt.stable(3500, true))
    }

    @Test fun explicitExitImmediatelyReleasesLifecycleProtection() {
        val attempt = ImeSwitchAttempt(1000)
        assertTrue(attempt.isActive(1120))
        attempt.cancel()
        assertFalse(attempt.isActive(1121))
        assertFalse(attempt.takeClick(1350, true, false))
    }

    @Test fun unrelatedKeyboardNeverCompletesOrTriggersClick() {
        val attempt = ImeSwitchAttempt(1000)
        assertFalse(attempt.stable(1120, false))
        assertFalse(attempt.takeClick(1120, false, false))
        assertFalse(attempt.stable(1350, false))
    }

    @Test fun retriesAreBoundedAndStopAtDeadline() {
        val attempt = ImeSwitchAttempt(1000)
        assertFalse(attempt.takeClick(1499, true, false))
        assertTrue(attempt.takeClick(1500, true, false))
        assertFalse(attempt.takeClick(1799, true, false))
        assertTrue(attempt.takeClick(1800, true, false))
        assertFalse(attempt.takeClick(2200, true, false))
        assertFalse(attempt.expired(3499))
        assertTrue(attempt.expired(3500))
        assertFalse(ImeSwitchAttempt(1000).takeClick(3500, true, false))
    }

    @Test fun transientVisibilityDoesNotCompleteAttempt() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.stable(100, true))
        assertFalse(attempt.stable(150, false))
        assertFalse(attempt.stable(180, true))
        assertFalse(attempt.stable(479, true))
        assertTrue(attempt.stable(480, true))
        assertFalse(attempt.takeClick(500, true, true))
    }

    @Test fun lateFocusDoesNotCauseBackToBackClicks() {
        val attempt = ImeSwitchAttempt(0)
        assertTrue(attempt.takeClick(600, true, false))
        assertFalse(attempt.takeClick(650, true, false))
        assertFalse(attempt.takeClick(750, true, false))
        assertTrue(attempt.takeClick(900, true, false))
    }

    @Test fun cancelledRequestCannotClickOrComplete() {
        val old = ImeSwitchAttempt(0)
        old.stable(0, true)
        old.cancel()
        assertFalse(old.takeClick(350, true, false))
        assertFalse(old.stable(350, true))
        val replacement = ImeSwitchAttempt(350)
        assertFalse(replacement.takeClick(350, true, false))
        assertTrue(replacement.takeClick(850, true, false))
    }

    @Test fun hideBetweenPollsInvalidatesEarlierVisibleTime() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.stable(100, true))
        // The next poll sees visible again, but the IME finished and restarted.
        attempt.onHidden(150)
        assertFalse(attempt.stable(200, true))
        assertFalse(attempt.stable(400, true))
        assertTrue(attempt.stable(500, true))
    }

    @Test fun boundImeRecoversBeforeSlowAccessibilityClick() {
        val attempt = ImeSwitchAttempt(0)
        attempt.onHidden(170)
        assertFalse(attempt.takeShowRequest(249, true, false))
        assertTrue(attempt.takeShowRequest(250, true, false))
        assertFalse(attempt.takeClick(500, true, false))
        assertFalse(attempt.takeShowRequest(549, true, false))
        assertTrue(attempt.takeShowRequest(550, true, false))
        assertFalse(attempt.takeClick(849, true, false))
        assertTrue(attempt.takeClick(850, true, false))
        assertFalse(attempt.takeShowRequest(1000, true, false))
    }

    @Test fun showRecoveryRequiresBoundHostAndStopsOnSuccessOrExit() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.takeShowRequest(200, false, false))
        assertFalse(attempt.takeShowRequest(200, true, true))
        assertTrue(attempt.takeShowRequest(200, true, false))
        attempt.cancel()
        assertFalse(attempt.takeShowRequest(600, true, false))
        assertFalse(ImeSwitchAttempt(0).takeShowRequest(2500, true, false))
    }

    @Test fun anotherHideDelaysRecoveryWithoutRenewingRetryBudget() {
        val attempt = ImeSwitchAttempt(0)
        assertTrue(attempt.takeShowRequest(120, true, false))
        attempt.onHidden(400)
        assertFalse(attempt.takeShowRequest(420, true, false))
        assertTrue(attempt.takeShowRequest(480, true, false))
        attempt.onHidden(800)
        assertFalse(attempt.takeShowRequest(900, true, false))
        assertFalse(attempt.takeClick(2500, true, false))
    }

    @Test fun suppressedHideStillRestoresSelectionWhenHandoffTimesOut() {
        val attempt = ImeSwitchAttempt(0)
        attempt.onHidden(150)
        assertTrue(attempt.expired(2500))
        // Timeout cancels callbacks first; there will be no second finish event.
        attempt.cancel()
        assertTrue(attempt.shouldRestoreOnFinish(true, true, false))
    }

    @Test fun failedSwitchBackNeverSwitchesForwardAgain() {
        val attempt = ImeSwitchAttempt(0)
        attempt.onHidden(150)
        assertFalse(attempt.shouldRestoreOnFinish(false, true, false))
        assertTrue(attempt.takeClick(500, true, false))
        assertFalse(attempt.takeClick(799, true, false))
        assertTrue(attempt.takeClick(800, true, false))
        assertFalse(attempt.takeClick(1100, true, false))
    }

    @Test fun failureCleanupPreservesManualSelectionAndVisibleKeyboard() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.shouldRestoreOnFinish(true, false, false))
        assertFalse(attempt.shouldRestoreOnFinish(true, true, true))
    }

    @Test fun completedHandoffDoesNotClaimSubsequentUserDismissal() {
        val attempt = ImeSwitchAttempt(0)
        assertFalse(attempt.stable(100, true))
        assertTrue(attempt.stable(400, true))
        assertFalse(attempt.shouldRestoreOnFinish(true, true, false))
    }
}
