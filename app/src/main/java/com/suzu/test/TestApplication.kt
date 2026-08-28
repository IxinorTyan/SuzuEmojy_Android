package com.suzu.test

import android.app.Application
import com.bumptech.glide.Glide
import com.suzu.test.accessibility.AccessibilityStateMonitor
import com.suzu.test.log.TestLog

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TestLog.init(this)
        AccessibilityStateMonitor.init(this)
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
