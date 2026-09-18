package com.suzu.test.floating

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BallAppAccessPolicyTest {
    @Test fun disabledWhitelistLeavesExistingRulesInControl() {
        assertTrue(BallAppAccessPolicy.allows(false, emptySet(), null))
        assertTrue(BallAppAccessPolicy.allows(false, setOf("chat"), "browser"))
    }

    @Test fun onlyAnExplicitlySelectedKnownAppPasses() {
        assertTrue(BallAppAccessPolicy.allows(true, setOf("chat"), "chat"))
        assertFalse(BallAppAccessPolicy.allows(true, setOf("chat"), "browser"))
        assertFalse(BallAppAccessPolicy.allows(true, setOf("chat"), "self"))
        assertFalse(BallAppAccessPolicy.allows(true, setOf("chat"), null))
        assertFalse(BallAppAccessPolicy.allows(true, setOf(""), ""))
    }

    @Test fun emptySelectionDeniesEveryApp() {
        assertFalse(BallAppAccessPolicy.allows(true, emptySet(), "chat"))
        assertFalse(BallAppAccessPolicy.allows(true, emptySet(), null))
    }

    @Test fun removingAppRevokesAccessImmediately() {
        assertTrue(BallAppAccessPolicy.allows(true, setOf("chat"), "chat"))
        assertFalse(BallAppAccessPolicy.allows(true, setOf("browser"), "chat"))
    }
}
