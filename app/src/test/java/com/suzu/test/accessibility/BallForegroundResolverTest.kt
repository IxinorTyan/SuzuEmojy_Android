package com.suzu.test.accessibility

import com.suzu.test.accessibility.BallWindowSnapshot.Kind
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class BallForegroundResolverTest {
    private fun app(pkg: String? = "chat", focused: Boolean = true, active: Boolean = true) =
        BallWindowSnapshot(Kind.APPLICATION, pkg, focused, active)

    @Test fun keyboardDoesNotReplaceHostApp() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(
            app(active = false), BallWindowSnapshot(Kind.IME, "keyboard", false, true)
        )))
    }

    @Test fun ownOverlayDoesNotReplaceHostApp() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(
            app(), BallWindowSnapshot(Kind.OWN_OVERLAY, "self", false, true)
        )))
    }

    @Test fun systemSurfaceTakingFocusDeniesAccess() {
        assertNull(BallForegroundResolver.resolve(listOf(
            app(), BallWindowSnapshot(Kind.OTHER, "system", true, true)
        )))
    }

    @Test fun searchOverlayTakingFocusKeepsUniqueHost() {
        val host = BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false),
            BallWindowSnapshot(Kind.OWN_OVERLAY, "self", true, true),
            BallWindowSnapshot(Kind.IME, "keyboard", false, false)
        ))
        assertEquals("chat", host)
        org.junit.Assert.assertTrue(com.suzu.test.floating.BallAppAccessPolicy.allows(
            true, setOf("self", "chat"), host
        ))
        org.junit.Assert.assertFalse(com.suzu.test.floating.BallAppAccessPolicy.allows(
            true, setOf("self"), host
        ))
    }

    @Test fun searchOverlayDoesNotBypassUnknownOrMultipleHosts() {
        val overlay = BallWindowSnapshot(Kind.OWN_OVERLAY, "self", true, true)
        assertNull(BallForegroundResolver.resolve(listOf(overlay)))
        assertNull(BallForegroundResolver.resolve(listOf(app(null, false, false), overlay)))
        assertNull(BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), app("video", false, false), overlay
        )))
        assertNull(BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), overlay,
            BallWindowSnapshot(Kind.OTHER, "system", true, true)
        )))
    }

    @Test fun passiveSystemBarsDoNotHideBall() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(
            app(), BallWindowSnapshot(Kind.OTHER, "system", false, false)
        )))
    }

    @Test fun splitScreenAndPipFollowFocusedApp() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(app(), app("video", false, false))))
        assertEquals("video", BallForegroundResolver.resolve(listOf(app(focused = false, active = false), app("video"))))
    }

    @Test fun dialogWithinSameAppIsAllowed() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(app(), app(focused = false))))
    }

    @Test fun missingOrAmbiguousWindowsNeverReusePreviousApp() {
        assertNull(BallForegroundResolver.resolve(emptyList()))
        assertNull(BallForegroundResolver.resolve(listOf(app(null))))
        assertNull(BallForegroundResolver.resolve(listOf(app(focused = false, active = false))))
        assertEquals("chat", BallForegroundResolver.resolve(listOf(app(), app(null, false, false))))
    }

    @Test fun focusWinsOverOldActiveWindowRegardlessOfListOrder() {
        val windows = listOf(app("old", false, true), app("new", true, false))
        assertEquals("new", BallForegroundResolver.resolve(windows, "old"))
        assertEquals("new", BallForegroundResolver.resolve(windows.reversed(), "old"))
    }

    @Test fun activeAppIsUsedOnlyWhenNoAppHasFocus() {
        assertEquals("video", BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), app("video", false, true)
        )))
        assertNull(BallForegroundResolver.resolve(listOf(app(), app("video"))))
        assertNull(BallForegroundResolver.resolve(listOf(app(null), app("video", false, true))))
        assertNull(BallForegroundResolver.resolve(listOf(app(focused = false), app("video", false, true))))
    }

    @Test fun overlayAndKeyboardRetainVisibleHostInSplitScreen() {
        for (kind in listOf(Kind.OWN_OVERLAY, Kind.IME)) {
            val windows = listOf(app(focused = false, active = false),
                app("video", false, false), BallWindowSnapshot(kind, "helper", true, true))
            assertEquals("chat", BallForegroundResolver.resolve(windows, "chat"))
            assertEquals("video", BallForegroundResolver.resolve(windows, "video"))
            assertNull(BallForegroundResolver.resolve(windows, "removed"))
            assertNull(BallForegroundResolver.resolve(windows))
        }
    }

    @Test fun priorHostCannotOverrideNewFocusOrSystemSurface() {
        val overlay = BallWindowSnapshot(Kind.OWN_OVERLAY, "self", false, true)
        assertEquals("video", BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), app("video"), overlay
        ), "chat"))
        assertNull(BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), app("video", false, false)
        ), "chat"))
        assertNull(BallForegroundResolver.resolve(listOf(app(), overlay,
            BallWindowSnapshot(Kind.OTHER, "system", true, true)
        ), "chat"))
    }

    @Test fun switchingSplitScreenFocusRevokesWhitelistAccess() {
        val allowed = setOf("self", "chat")
        val first = BallForegroundResolver.resolve(listOf(app(), app("video", false, false)))
        val next = BallForegroundResolver.resolve(listOf(
            app(focused = false, active = false), app("video")
        ), first)
        org.junit.Assert.assertTrue(com.suzu.test.floating.BallAppAccessPolicy.allows(true, allowed, first))
        org.junit.Assert.assertFalse(com.suzu.test.floating.BallAppAccessPolicy.allows(true, allowed, next))
    }

    @Test fun switchingAppsReturnsNewAppInsteadOfCachedHost() {
        assertEquals("chat", BallForegroundResolver.resolve(listOf(app())))
        assertEquals("browser", BallForegroundResolver.resolve(listOf(app("browser"))))
    }
}
