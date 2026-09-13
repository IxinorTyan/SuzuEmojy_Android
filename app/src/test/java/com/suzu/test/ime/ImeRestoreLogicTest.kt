package com.suzu.test.ime

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ImeRestoreLogicTest {

    private val packageName = "com.suzu.test"
    private val imeClassName = "com.suzu.test.ime.TestImageIME"
    private val fullId = "$packageName/$imeClassName"
    private val shortId = "$packageName/.ime.TestImageIME"

    private fun isSelfIme(imeId: String?, ownImeId: String? = fullId): Boolean {
        if (imeId.isNullOrEmpty()) return false
        return imeId == ownImeId || imeId == fullId || imeId == shortId || imeId.startsWith("$packageName/")
    }

    @Test
    fun testIsSelfIme_identifiesAllFormatsOfOwnIme() {
        assertTrue(isSelfIme(fullId))
        assertTrue(isSelfIme(shortId))
        assertTrue(isSelfIme("$packageName/com.suzu.test.ime.CustomIME"))
        assertTrue(isSelfIme("$packageName/.any"))
    }

    @Test
    fun testIsSelfIme_rejectsThirdPartyImes() {
        assertFalse(isSelfIme("com.tencent.qqpinyin/.QQPYInputMethodService"))
        assertFalse(isSelfIme("com.sohu.inputmethod.sogou/.SogouIME"))
        assertFalse(isSelfIme("com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME"))
        assertFalse(isSelfIme(null))
        assertFalse(isSelfIme(""))
    }

    @Test
    fun testCandidateSelection_skipsSelfAndPicksThirdParty() {
        val enabledList = listOf(
            fullId,
            shortId,
            "com.sohu.inputmethod.sogou/.SogouIME",
            "com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME"
        )
        val selected = enabledList.firstOrNull { !isSelfIme(it) }
        assertEquals("com.sohu.inputmethod.sogou/.SogouIME", selected)
    }
}
