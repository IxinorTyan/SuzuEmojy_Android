package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.BaseInputConnection
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.suzu.test.ime.ImageItem
import com.suzu.test.ime.config.KeyboardConfig
import com.suzu.test.ime.diag.DebugSendTestConfig
import com.suzu.test.ime.sender.ImageSender
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/** Isolated device check with a local recording connection. Never sends a message to QQ. */
@RunWith(AndroidJUnit4::class)
class OwnImeOverlaySendTest {
    @Test fun originalCommandAndNullConnectionWithoutFallback() {
        val app = ApplicationProvider.getApplicationContext<Context>()
        val launches = AtomicInteger()
        val context = object : ContextWrapper(app) {
            override fun startActivity(intent: Intent) { launches.incrementAndGet() }
            override fun startActivity(intent: Intent, options: Bundle?) { launches.incrementAndGet() }
        }
        val oldDebug = DebugSendTestConfig.isSkipProviderDiagnosticAndCleanupEnabled(app)
        val oldFallback = KeyboardConfig.isSendFailureForwardEnabled(app)
        val file = File(app.filesDir, "resources/overlay_test_${System.nanoTime()}.gif")
        file.parentFile?.mkdirs()
        file.writeBytes(Base64.decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7", Base64.DEFAULT))
        val item = ImageItem.SuzuResource(-1, file, "gif", true, file.name, "image/gif")
        val sender = ImageSender(context)
        try {
            DebugSendTestConfig.setSkipProviderDiagnosticAndCleanupEnabled(app, true)
            KeyboardConfig.setSendFailureForwardEnabled(app, true)
            for (accepted in listOf(true, false, null)) {
                var action: String? = null
                var submitted: Uri? = null
                val calls = AtomicInteger()
                val successes = AtomicInteger()
                val finished = CountDownLatch(1)
                var report = ""
                val connection = if (accepted == null) null else object : BaseInputConnection(View(app), true) {
                    override fun performPrivateCommand(command: String?, data: Bundle?): Boolean {
                        calls.incrementAndGet()
                        action = command
                        submitted = data?.getParcelable("EXP_PATH_URI")
                        return accepted
                    }
                }
                sender.executeH1b(item, app.packageName, icProvider = { connection },
                    isolatedTest = true,
                    onReport = { message, done -> if (done) { report = message; finished.countDown() } },
                    onSuccess = { successes.incrementAndGet() })
                assertTrue("Sender did not finish", finished.await(15, TimeUnit.SECONDS))
                assertEquals(0, launches.get())
                assertEquals(if (accepted == true) 1 else 0, successes.get())
                if (accepted != null) {
                    assertEquals(1, calls.get())
                    assertEquals("com.sogou.inputmethod.exp.commit", action)
                    assertEquals("com.tencent.qqpinyin.suzu.provider", submitted?.authority)
                    assertTrue(report, report.contains("原特化命令返回：$accepted"))
                    app.contentResolver.openInputStream(requireNotNull(submitted))!!.use {
                        assertArrayEquals(file.readBytes(), it.readBytes())
                    }
                } else {
                    assertTrue(report, report.contains("未递交命令"))
                    assertTrue(report, report.contains("InputConnection 为 null"))
                }
            }
        } finally {
            DebugSendTestConfig.setSkipProviderDiagnosticAndCleanupEnabled(app, oldDebug)
            KeyboardConfig.setSendFailureForwardEnabled(app, oldFallback)
            sender.destroy()
            file.delete()
        }
    }
}
