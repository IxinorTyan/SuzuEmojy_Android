package com.suzu.test.ime

import com.suzu.test.ime.ui.restoredImeTab
import org.junit.Assert.assertEquals
import org.junit.Test

class TabSelectionTest {
    @Test fun savedFolderSurvivesLoadingAndReordering() {
        assertEquals("cat:42", restoredImeTab("cat:42", "cat:42", null))
        assertEquals("cat:42", restoredImeTab("cat:42", "cat:42", listOf("cat:8", "cat:42")))
    }

    @Test fun closingSearchRestoresLastFolder() {
        assertEquals("cat:42", restoredImeTab("SEARCH", "cat:42", null))
        assertEquals("cat:42", restoredImeTab("SEARCH", "cat:42", listOf("RECENT", "cat:42")))
        assertEquals("SEARCH", restoredImeTab("SEARCH", "cat:42", listOf("SEARCH", "cat:42")))
    }

    @Test fun deletedOrDisabledTabFallsBackOnlyAfterLoading() {
        assertEquals("cat:8", restoredImeTab("cat:42", "cat:42", listOf("cat:8")))
        assertEquals("cat:8", restoredImeTab("RECENT", "RECENT", listOf("cat:8")))
        assertEquals("ALL", restoredImeTab("cat:42", "cat:42", emptyList()))
    }
}
