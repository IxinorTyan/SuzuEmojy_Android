package com.suzu.test.floating

import org.junit.Assert.assertEquals
import org.junit.Test

class FloatingSearchWindowBoundsTest {
    @Test fun imeDisappearingDuringSwitchShrinksToCard() {
        assertEquals(1700, FloatingSearchBarController.resolveHeight(1800, 100, 64, 2400))
        assertEquals(64, FloatingSearchBarController.resolveHeight(null, 100, 64, 2400))
        assertEquals(1600, FloatingSearchBarController.resolveHeight(1700, 100, 64, 2400))
    }

    @Test fun invalidBoundsCannotExtendPastScreen() {
        assertEquals(64, FloatingSearchBarController.resolveHeight(0, 100, 64, 2400))
        assertEquals(64, FloatingSearchBarController.resolveHeight(120, 100, 64, 2400))
        assertEquals(2300, FloatingSearchBarController.resolveHeight(4000, 100, 64, 2400))
        assertEquals(64, FloatingSearchBarController.resolveHeight(null, 2336, 64, 2400))
    }
}
