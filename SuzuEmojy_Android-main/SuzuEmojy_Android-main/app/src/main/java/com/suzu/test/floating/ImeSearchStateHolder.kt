package com.suzu.test.floating

import com.suzu.test.log.TestLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ImeSearchStateHolder {
    private const val MODULE = "ImeSearchState"

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()

    @Volatile
    private var searchLaunchGuardUntil: Long = 0L

    fun setSearchQuery(query: String?) {
        val trimmed = query?.trim()
        val finalQuery = if (trimmed.isNullOrEmpty()) null else trimmed
        TestLog.i(MODULE, "设置临时搜索词: '$finalQuery'")
        if (finalQuery != null) {
            searchLaunchGuardUntil = System.currentTimeMillis() + 2500L
        } else {
            searchLaunchGuardUntil = 0L
        }
        _searchQuery.value = finalQuery
    }

    fun isSearchLaunching(): Boolean {
        return System.currentTimeMillis() < searchLaunchGuardUntil
    }

    fun onSearchImeShown() {
        searchLaunchGuardUntil = 0L
    }

    fun clearSearch() {
        searchLaunchGuardUntil = 0L
        if (_searchQuery.value != null) {
            TestLog.i(MODULE, "销毁临时搜索状态 (原搜索词: '${_searchQuery.value}')")
            _searchQuery.value = null
        }
    }
}
