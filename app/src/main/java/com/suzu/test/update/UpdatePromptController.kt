package com.suzu.test.update

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.suzu.test.BuildConfig
import kotlinx.coroutines.launch

/** Retains checks in the ViewModel and reconnects the prompt after activity recreation. */
class UpdatePromptController(
    private val activity: AppCompatActivity,
    private val viewModel: UpdateCheckViewModel,
    private val onStateChanged: (UpdateCheckState) -> Unit = {}
) : DefaultLifecycleObserver {
    private var dialog: AlertDialog? = null

    init {
        activity.lifecycle.addObserver(this)
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    onStateChanged(state)
                    if (state.checking || state.handled || state.result == null) return@collect
                    val result = state.result
                    if (result is UpdateCheckResult.NewVersion) {
                        if (!state.manual && !viewModel.isAutomaticCheckEnabled()) {
                            viewModel.acknowledgeResult()
                        } else showUpdate(result.release)
                    } else {
                        if (state.manual) {
                            val message = when (result) {
                                UpdateCheckResult.UpToDate -> "当前已是最新版本：v${BuildConfig.VERSION_NAME}"
                                UpdateCheckResult.NoRelease -> "仓库暂未发布正式发行版"
                                is UpdateCheckResult.Failed -> when (result.reason) {
                                    FailureReason.RATE_LIMIT -> "GitHub 暂时限制了检查频率，请稍后重试"
                                    FailureReason.UNKNOWN_VERSION -> "无法识别发行版版本号，可前往仓库查看"
                                    FailureReason.INVALID_RESPONSE -> "发行版信息异常，请稍后重试"
                                    FailureReason.SERVER -> "GitHub 暂时无法响应，请稍后重试"
                                    FailureReason.NETWORK -> "无法检查更新，请检查网络后重试"
                                }
                                else -> null
                            }
                            if (message != null) Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                        }
                        viewModel.acknowledgeResult()
                    }
                }
            }
        }
    }

    private fun showUpdate(release: GitHubRelease) {
        if (dialog != null || activity.isFinishing || activity.isDestroyed) return
        val message = buildString {
            append("当前版本：v${BuildConfig.VERSION_NAME}\n最新版本：${release.tag}")
            if (release.notes.isNotBlank()) append("\n\n${release.notes}")
            append("\n\n前往 GitHub 仓库，在浏览器中查看并下载新版本。")
        }
        dialog = AlertDialog.Builder(activity)
            .setTitle("发现新版本 ${release.tag}")
            .setMessage(message)
            .setPositiveButton("前往更新") { _, _ ->
                viewModel.acknowledgeResult()
                ProjectBrowser.open(activity)
            }
            .setNegativeButton("稍后") { _, _ -> viewModel.acknowledgeResult() }
            .setOnCancelListener { viewModel.acknowledgeResult() }
            .create().also { prompt ->
                prompt.setOnDismissListener { dialog = null }
                prompt.show()
            }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        // Dismiss does not acknowledge the release: the recreated activity can restore it.
        dialog?.dismiss()
        dialog = null
        owner.lifecycle.removeObserver(this)
    }
}
