package com.suzu.test

import android.app.Application
import com.suzu.test.accessibility.AccessibilityStateMonitor
import com.suzu.test.log.TestLog

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        TestLog.init(this)
        AccessibilityStateMonitor.init(this)
    }
}
