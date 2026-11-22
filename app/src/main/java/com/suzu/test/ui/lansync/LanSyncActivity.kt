package com.suzu.test.ui.lansync

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.suzu.test.databinding.ActivityLanSyncBinding
import com.suzu.test.resource.lansync.LanTransfer
import kotlinx.coroutines.launch

class LanSyncActivity : AppCompatActivity() {
    private val model: LanSyncViewModel by viewModels()
    private val scanner = registerForActivityResult(ScanContract()) { result ->
        result.contents?.let(model::scan)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLanSyncBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.header.onBack { finish() }
        binding.scan.setOnClickListener {
            scanner.launch(ScanOptions().setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                .setPrompt("扫电脑上的配对二维码").setBeepEnabled(false))
        }
        binding.connect.setOnClickListener {
            getSystemService(InputMethodManager::class.java)?.hideSoftInputFromWindow(binding.root.windowToken, 0)
            model.manual(binding.pairCode.text.toString(), binding.address.text.toString())
        }
        binding.confirm.setOnClickListener {
            if (model.state.value.phase == LanSyncPhase.OFFER) model.answer(true) else model.confirm()
        }
        binding.decline.setOnClickListener {
            if (model.state.value.phase == LanSyncPhase.OFFER) model.answer(false) else model.disconnect()
        }
        binding.download.setOnClickListener { model.request(LanTransfer.DOWNLOAD) }
        binding.upload.setOnClickListener { model.request(LanTransfer.UPLOAD) }
        binding.disconnect.setOnClickListener { model.disconnect() }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.collect { state ->
                    render(binding, state)
                }
            }
        }
    }

    private fun render(binding: ActivityLanSyncBinding, state: LanSyncState) = with(binding) {
        status.text = state.status
        statusPanel.isVisible = state.status.isNotBlank()
        pairingControls.isVisible = state.phase == LanSyncPhase.ENTRY
        transferControls.isVisible = state.phase == LanSyncPhase.CONNECTED
        decisionControls.isVisible = state.phase == LanSyncPhase.VERIFY || state.phase == LanSyncPhase.OFFER
        confirm.text = when (state.incoming) {
            LanTransfer.DOWNLOAD -> "允许接收"
            LanTransfer.UPLOAD -> "允许发送"
            null -> "校验码一致"
        }
        verification.isVisible = state.verification != null
        verification.text = state.verification?.let { "校验码：$it" }.orEmpty()
        activityProgress.isVisible = state.phase == LanSyncPhase.BUSY && state.progress == null
        transferProgress.isVisible = state.progress != null
        state.progress?.let { transferProgress.setProgressCompat(it, false) }
        disconnect.isVisible = state.phase != LanSyncPhase.ENTRY
    }
}
