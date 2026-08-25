package com.suzu.test.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.ResourceEntity
import com.suzu.test.resource.KeywordUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class LibraryViewModel(
    private val database: SuzuDatabase
) : ViewModel() {

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _categorySelection = MutableStateFlow("ALL")
    val categorySelection: StateFlow<String> = _categorySelection.asStateFlow()

    fun updateFilterState(state: FilterState) {
        _filterState.value = state
    }

    fun clearFilterState() {
        _filterState.value = FilterState()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(selection: String) {
        _categorySelection.value = selection
    }

    private val dbResourceFlow = combine(_filterState, _categorySelection) { filter, category ->
        val catId = if (category == "ALL") 0L else (category.toLongOrNull() ?: 0L)
        Triple(filter, catId, category)
    }.flatMapLatest { (filter, catId, _) ->
        database.resourceDao().getFilteredResourcesFlow(
            noKw = filter.noKwParam,
            noCat = filter.noCatParam,
            anim = filter.animParam,
            catId = catId
        )
    }

    val displayedItems: StateFlow<List<ResourceEntity>> = combine(dbResourceFlow, _searchQuery) { list, query ->
        if (query.isBlank()) {
            list
        } else {
            KeywordUtils.filterAndSort(list, query)
        }
    }
    .flowOn(Dispatchers.Default)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
