package com.suzu.test

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.ui.components.CategoryRows
import com.suzu.test.ui.exchange.ExchangeActivity
import com.suzu.test.ui.export.ExportActivity
import com.suzu.test.ui.import.ImportActivity
import com.suzu.test.ui.lansync.LanSyncActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ExchangeUiTest {
    @Test fun syncIsUnderExchangeAndUsesSharedCards() {
        ActivityScenario.launch(MainActivity::class.java).use { home ->
            home.onActivity { it.findViewById<View>(R.id.cardExchange).performClick() }
            resumed<ExchangeActivity> { exchange ->
                assertEquals("导入导出", exchange.findViewById<TextView>(R.id.pageTitle).text.toString())
                assertNotNull(exchange.findViewById<View>(R.id.importStickers))
                assertNotNull(exchange.findViewById<View>(R.id.exportStickers))
            }
            captureIfRequested("exchange-home.png")
            resumed<ExchangeActivity> { it.findViewById<View>(R.id.lanSync).performClick() }
            resumed<LanSyncActivity> { sync ->
                assertEquals("局域网同步", sync.findViewById<TextView>(R.id.pageTitle).text.toString())
                assertNotNull(sync.findViewById<View>(R.id.scan))
                assertNotNull(sync.findViewById<View>(R.id.pairCode))
                assertNotNull(sync.findViewById<View>(R.id.address))
            }
            captureIfRequested("lan-sync.png")
            resumed<LanSyncActivity> { it.finish() }
            resumed<ExchangeActivity> { it.finish() }
        }

        ActivityScenario.launch(ImportActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val folder = activity.findViewById<View>(R.id.btnPickFromFolder)
                val title = folder.findViewById<TextView>(R.id.actionTitle)
                val icon = folder.findViewById<ImageView>(R.id.actionIcon)
                assertEquals("从文件夹批量导入", title.text.toString())
                assertEquals(activity.getColor(R.color.ui_accent), icon.imageTintList?.defaultColor)
            }
        }
    }

    @Test fun sharedSelectionShowsFolderAndEmojiAndKeepsSelections() {
        ActivityScenario.launch(ExportActivity::class.java).use { scenario ->
            var result: Set<Long>? = null
            lateinit var picker: AlertDialog
            scenario.onActivity { activity ->
                picker = CategoryRows.pickMany(activity, activity.lifecycleScope, "导出指定收藏夹", listOf(
                    CategoryEntity(id = 7, name = "默认收藏夹"),
                    CategoryEntity(id = 9, name = "自定义收藏夹", iconPath = "text:⭐")
                ), positive = "继续", requireSelection = true) { result = it }
                assertFalse(picker.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled)
                val list = picker.listView
                val adapter = list.adapter
                val folder = adapter.getView(0, null, list)
                val emoji = adapter.getView(1, null, list)
                assertEquals("默认收藏夹", folder.findViewById<TextView>(R.id.categoryTitle).text.toString())
                assertNotNull(folder.findViewById<View>(R.id.categoryIcon))
                val cover = emoji.findViewById<android.widget.FrameLayout>(R.id.categoryIcon)
                assertEquals("⭐", (cover.getChildAt(1) as TextView).text.toString())
                list.performItemClick(emoji, 1, adapter.getItemId(1))
                assertTrue(picker.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled)
            }
            captureIfRequested("category-picker.png")
            scenario.onActivity { picker.getButton(AlertDialog.BUTTON_POSITIVE).performClick() }
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()
            assertEquals(setOf(9L), result)
        }
    }

    private inline fun <reified T : Activity> resumed(crossinline block: (T) -> Unit) {
        val runner = InstrumentationRegistry.getInstrumentation()
        repeat(50) {
            runner.waitForIdleSync()
            var found = false
            runner.runOnMainSync {
                val activity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
                    .filterIsInstance<T>().singleOrNull()
                if (activity != null) { block(activity); found = true }
            }
            if (found) return
            Thread.sleep(30)
        }
        error("Did not open ${T::class.java.simpleName}")
    }

    private fun captureIfRequested(name: String) {
        if (InstrumentationRegistry.getArguments().getString("captureExchange") != "true") return
        val runner = InstrumentationRegistry.getInstrumentation()
        runner.waitForIdleSync()
        // Activity/dialog transitions can outlive the main message queue becoming idle.
        Thread.sleep(400)
        val image = runner.uiAutomation.takeScreenshot() ?: return
        File(runner.targetContext.cacheDir, name).outputStream().use { image.compress(Bitmap.CompressFormat.PNG, 100, it) }
        image.recycle()
    }
}
