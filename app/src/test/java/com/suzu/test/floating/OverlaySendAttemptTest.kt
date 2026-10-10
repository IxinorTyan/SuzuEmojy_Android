package com.suzu.test.floating

import org.junit.Assert.*
import org.junit.Test

class OverlaySendAttemptTest {
    @Test fun nextClickDoesNotWaitForDiagnosticsAndOldResultCannotUnlockNewPreparation() {
        val state = OverlaySendAttempt()
        val first = requireNotNull(state.begin())
        assertNull(state.begin()) // A conversion is still running.
        state.submitted(first)
        val second = requireNotNull(state.begin()) // No five-second wait.
        assertFalse(state.isLatest(first))
        state.submitted(first) // Old delayed observation must not release the new conversion.
        assertNull(state.begin())
        state.submitted(second)
        assertNotNull(state.begin())
    }

    @Test fun failedSubmissionAlsoReleasesNextClick() {
        val state = OverlaySendAttempt()
        val failed = requireNotNull(state.begin())
        state.submitted(failed)
        assertNotNull(state.begin())
    }
}
