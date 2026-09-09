package com.suzu.test.floating

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ImeVisibilityBus {
    private val _isImeVisible = MutableStateFlow(false)
    val isImeVisible: StateFlow<Boolean> = _isImeVisible.asStateFlow()

    fun notifyImeVisibilityChanged(visible: Boolean) {
        _isImeVisible.value = visible
    }
}
