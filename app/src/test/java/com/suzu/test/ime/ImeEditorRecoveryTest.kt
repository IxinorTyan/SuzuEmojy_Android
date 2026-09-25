package com.suzu.test.ime

import org.junit.Assert.*
import org.junit.Test

class ImeEditorRecoveryTest {
    private val saved = ImeEditorRecovery.Identity("host:id/message", "android.widget.EditText")
    private val editor = ImeEditorRecovery.Candidate(saved.viewId, saved.className, true, true, true)

    @Test fun recoversOriginalEditorEvenAfterHostClearsInputFocus() {
        assertEquals(0, ImeEditorRecovery.choose(saved, null, listOf(editor)))
    }

    @Test fun doesNotStealFocusFromAnotherEditor() {
        val other = editor.copy(viewId = "host:id/search")
        assertNull(ImeEditorRecovery.choose(saved, other, listOf(editor)))
        assertNull(ImeEditorRecovery.choose(saved, other.copy(viewId = null), listOf(editor)))
    }

    @Test fun duplicateVisibleIdsAreAmbiguous() {
        assertNull(ImeEditorRecovery.choose(saved, null, listOf(editor, editor)))
        assertEquals(1, ImeEditorRecovery.choose(saved, null,
            listOf(editor.copy(visible = false), editor)))
    }

    @Test fun missingOriginalDoesNotFallBackToAnyEditableNode() {
        assertNull(ImeEditorRecovery.choose(saved, null, emptyList()))
        assertNull(ImeEditorRecovery.choose(saved, null,
            listOf(editor.copy(viewId = "host:id/other"))))
    }

    @Test fun hiddenDisabledOrReplacedControlsCannotReceiveRecoveryClick() {
        for (candidate in listOf(editor.copy(visible = false), editor.copy(enabled = false),
            editor.copy(editable = false), editor.copy(className = "android.widget.Button"))) {
            assertNull(ImeEditorRecovery.choose(saved, null, listOf(candidate)))
        }
    }

    @Test fun sameFocusedEditorRemainsEligible() {
        assertEquals(0, ImeEditorRecovery.choose(saved, editor, listOf(editor)))
    }
}
