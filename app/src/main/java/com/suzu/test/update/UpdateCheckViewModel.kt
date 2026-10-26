package com.suzu.test.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UpdateCheckState(
    val checking: Boolean = false,
    val manual: Boolean = false,
    val result: UpdateCheckResult? = null,
    val handled: Boolean = false
)

class UpdateCheckViewModel(application: Application) : AndroidViewModel(application) {
    private val checker = GitHubUpdateChecker(application)
    private val mutableState = MutableStateFlow(UpdateCheckState())
    val state = mutableState.asStateFlow()

    fun isAutomaticCheckEnabled(): Boolean = checker.isAutomaticCheckEnabled()

    fun setAutomaticCheckEnabled(enabled: Boolean) = checker.setAutomaticCheckEnabled(enabled)

    fun check(manual: Boolean) {
        if (mutableState.value.checking) return
        // Keep an unacknowledged update available across activity recreation and foreground changes.
        if (!manual && mutableState.value.result is UpdateCheckResult.NewVersion && !mutableState.value.handled) return
        mutableState.value = UpdateCheckState(checking = true, manual = manual)
        viewModelScope.launch {
            val result = try {
                checker.check(manual)
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                UpdateCheckResult.Failed(FailureReason.NETWORK)
            }
            mutableState.value = UpdateCheckState(manual = manual, result = result)
        }
    }

    fun acknowledgeResult() {
        mutableState.value = mutableState.value.copy(handled = true)
    }
}
