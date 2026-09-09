package com.suzu.test

import android.app.Application
import com.bumptech.glide.Glide
import com.suzu.test.accessibility.AccessibilityStateMonitor
import com.suzu.test.log.TestLog
import com.suzu.test.storage.CacheCleanManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TestApplication : Application() {

    private val maintenanceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        TestLog.init(this)
        AccessibilityStateMonitor.init(this)

        // 回收上次进程异常结束后遗留的资源包导入暂存文件。
        maintenanceScope.launch {
            CacheCleanManager.cleanImportPreviewFiles(this@TestApplication)
            CacheCleanManager.cleanImportTempZipFiles(this@TestApplication)
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}
