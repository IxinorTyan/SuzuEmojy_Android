package com.suzu.test.ui.exchange

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suzu.test.databinding.ActivityExchangeBinding
import com.suzu.test.ui.export.ExportActivity
import com.suzu.test.ui.import.ImportActivity
import com.suzu.test.ui.lansync.LanSyncActivity

class ExchangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityExchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.header.onBack { finish() }
        binding.importStickers.setOnClickListener { startActivity(Intent(this, ImportActivity::class.java)) }
        binding.exportStickers.setOnClickListener { startActivity(Intent(this, ExportActivity::class.java)) }
        binding.lanSync.setOnClickListener { startActivity(Intent(this, LanSyncActivity::class.java)) }
    }
}
