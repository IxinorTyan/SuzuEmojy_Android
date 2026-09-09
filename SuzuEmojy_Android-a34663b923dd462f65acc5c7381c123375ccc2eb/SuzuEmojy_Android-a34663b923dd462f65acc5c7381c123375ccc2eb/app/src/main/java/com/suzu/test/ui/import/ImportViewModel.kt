package com.suzu.test.ui.import

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ImportViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_PENDING_CLEANABLE_URIS = "key_pending_cleanable_uris"
        private const val KEY_UNHANDLED_COUNT = "key_unhandled_count"
        private const val KEY_DIALOG_SHOWN = "key_dialog_shown"
        private const val KEY_LAST_IMPORT_SUMMARY = "key_last_import_summary"
    }

    var pendingCleanableUris: List<Uri>
        get() {
            val list = savedStateHandle.get<ArrayList<String>>(KEY_PENDING_CLEANABLE_URIS) ?: arrayListOf()
            return list.map { Uri.parse(it) }
        }
        set(value) {
            savedStateHandle[KEY_PENDING_CLEANABLE_URIS] = ArrayList(value.map { it.toString() })
        }

    var unhandledCount: Int
        get() = savedStateHandle.get<Int>(KEY_UNHANDLED_COUNT) ?: 0
        set(value) {
            savedStateHandle[KEY_UNHANDLED_COUNT] = value
        }

    var isDialogShown: Boolean
        get() = savedStateHandle.get<Boolean>(KEY_DIALOG_SHOWN) ?: false
        set(value) {
            savedStateHandle[KEY_DIALOG_SHOWN] = value
        }

    var lastImportSummary: ImportSummary?
        get() = savedStateHandle.get<ImportSummary>(KEY_LAST_IMPORT_SUMMARY)
        set(value) {
            savedStateHandle[KEY_LAST_IMPORT_SUMMARY] = value
        }

    fun setPendingCleanup(cleanable: List<Uri>, unhandled: Int, summary: ImportSummary) {
        pendingCleanableUris = cleanable
        unhandledCount = unhandled
        lastImportSummary = summary
        isDialogShown = false
    }

    fun clearCleanupState() {
        pendingCleanableUris = emptyList()
        unhandledCount = 0
        isDialogShown = true
        lastImportSummary = null
    }
}

data class ImportSummary(
    val successCount: Int,
    val duplicateCount: Int,
    val failCount: Int
) : java.io.Serializable
