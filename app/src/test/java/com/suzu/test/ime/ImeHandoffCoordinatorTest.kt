package com.suzu.test.ime

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImeHandoffCoordinatorTest {
    private val chat = ImeHost(7, "example.chat")
    private val textIme = "example.text/.Keyboard"
    private val appIme = "example.suzu/.Keyboard"
    private val savedEditor = ImeEditorRecovery.Identity("example.chat:id/editor", "EditText")

    @Test fun searchSurvivesOverlayFocusAndReleasesGuardAfterStableHostVisibility() = runTest {
        val platform = FakePlatform().apply { focused = ImeHost(8, ownPackage) }
        val handoff = coordinator(platform)

        assertTrue(handoff.openFromSearch(chat))
        advanceTimeBy(350)
        assertTrue(handoff.isPending())
        assertEquals(0, platform.clicks)
        platform.focused = chat
        platform.ownVisible = true
        advanceTimeBy(300)
        assertTrue(handoff.isPending())
        runCurrent()

        assertFalse(handoff.isPending())
        assertEquals(1, platform.guardsReleased)
        assertEquals(0, platform.searchClears)
        assertEquals(0, platform.selectionRestores)
    }

    @Test fun timeoutClearsSearchAndRestoresSelectionWithoutReopeningTextKeyboard() = runTest {
        val platform = FakePlatform()
        val handoff = coordinator(platform)
        handoff.openFromSearch(chat)
        advanceUntilIdle()

        assertFalse(handoff.isPending())
        assertEquals(textIme, platform.selected)
        assertEquals(2, platform.clicks)
        assertEquals(1, platform.selectionRestores)
        assertEquals(0, platform.explicitTextSelections)
        assertEquals(1, platform.searchClears)
    }

    @Test fun explicitSwitchBackRequiresDestinationWindowAndUsesCapturedEditor() = runTest {
        val platform = FakePlatform().apply { selected = appIme; ownVisible = true }
        val handoff = coordinator(platform)
        handoff.restoreAndShow(textIme)
        advanceTimeBy(550)

        assertTrue(handoff.isPending())
        assertEquals(listOf(chat), platform.editorCaptures)
        assertEquals(listOf(savedEditor), platform.clickedEditors)
        assertEquals(setOf("example.text"), platform.observedImePackages)
        platform.textWindow = 99
        advanceUntilIdle()

        assertEquals(textIme, platform.selected)
        assertEquals(1, platform.explicitTextSelections)
        assertEquals(0, platform.selectionRestores)
        assertEquals(0, platform.searchClears)
    }

    @Test fun boundImeGetsRecoveryRequestBeforeAnyEditorClick() = runTest {
        val platform = FakePlatform().apply { ownBound = true }
        val handoff = coordinator(platform)
        handoff.openFromEditor()
        advanceTimeBy(160)

        assertEquals(1, platform.showRequests)
        assertEquals(0, platform.clicks)
        platform.ownVisible = true
        advanceUntilIdle()
        assertEquals(0, platform.selectionRestores)
    }

    @Test fun lateNodeLookupCannotClickAfterRequestIsCancelled() = runTest {
        val platform = FakePlatform()
        val handoff = coordinator(platform)
        platform.onEditorLookup = handoff::cancel
        handoff.openFromEditor()
        advanceUntilIdle()

        assertFalse(handoff.isPending())
        assertEquals(0, platform.clicks)
        assertEquals(0, platform.selectionRestores)
    }

    @Test fun replacementDoesNotRunOldSearchCleanupAgainstNewRequest() = runTest {
        val platform = FakePlatform()
        val handoff = coordinator(platform)
        handoff.openFromSearch(chat)
        runCurrent()

        platform.ownVisible = true
        handoff.openFromEditor()
        advanceUntilIdle()

        assertFalse(handoff.isPending())
        assertEquals(0, platform.searchClears)
        assertEquals(0, platform.guardsReleased)
        assertEquals(0, platform.selectionRestores)
    }

    @Test fun userSelectionDuringHandoffIsPreserved() = runTest {
        val platform = FakePlatform()
        val handoff = coordinator(platform)
        handoff.openFromSearch(chat)
        runCurrent()
        platform.selected = "example.other/.Keyboard"
        advanceUntilIdle()

        assertEquals("example.other/.Keyboard", platform.selected)
        assertEquals(1, platform.searchClears)
        assertEquals(0, platform.selectionRestores)
        assertEquals(0, platform.clicks)
    }

    @Test fun missingSavedSearchHostDoesNotStartAnUnrelatedHandoff() = runTest {
        val platform = FakePlatform()
        val handoff = coordinator(platform)
        assertFalse(handoff.openFromSearch(null))
        advanceUntilIdle()
        assertEquals(textIme, platform.selected)
        assertEquals(1, platform.searchClears)
    }

    private fun TestScope.coordinator(platform: FakePlatform) =
        ImeHandoffCoordinator(this, StandardTestDispatcher(testScheduler), { testScheduler.currentTime }, platform)

    private inner class FakePlatform : ImeHandoffCoordinator.Platform {
        override val ownPackage = "example.suzu"
        var selected: String? = textIme
        var focused: ImeHost? = chat
        var ownBound = false
        var ownVisible = false
        var textWindow: Int? = null
        var clicks = 0
        var showRequests = 0
        var explicitTextSelections = 0
        var selectionRestores = 0
        var searchClears = 0
        var guardsReleased = 0
        var onEditorLookup: () -> Unit = {}
        val observedImePackages = mutableSetOf<String>()
        val editorCaptures = mutableListOf<ImeHost>()
        val clickedEditors = mutableListOf<ImeEditorRecovery.Identity?>()

        override fun ownImeId() = appIme
        override fun selectedImeId() = selected
        override fun isOwnIme(id: String?) = id == appIme
        override fun selectOwnIme(): Boolean { selected = appIme; return true }
        override fun selectPreviousIme(id: String): Boolean { explicitTextSelections++; selected = id; return true }
        override fun restoreSelection() { selectionRestores++; selected = textIme }
        override fun focusedHost(known: ImeHost?) = focused
        override fun captureEditor(host: ImeHost): ImeEditorRecovery.Identity {
            editorCaptures += host
            return savedEditor
        }
        override fun visibleImeWindow(packageName: String, knownId: Int?): Int? {
            observedImePackages += packageName
            return textWindow
        }
        override fun ownImeShowing() = ownVisible
        override fun ownImeShowingFor(host: ImeHost) = ownVisible && focused == host
        override fun canShowOwnImeFor(host: ImeHost) = ownBound && focused == host
        override fun showOwnImeFor(host: ImeHost): Boolean { showRequests++; return true }
        override fun clickEditor(host: ImeHost, editor: ImeEditorRecovery.Identity?, stillAllowed: () -> Boolean) {
            onEditorLookup()
            if (stillAllowed()) { clicks++; clickedEditors += editor }
        }
        override fun clearSearch() { searchClears++ }
        override fun releaseSearchGuard() { guardsReleased++ }
        override fun log(message: String, failure: Throwable?) { if (failure != null) throw AssertionError(message, failure) }
    }
}
