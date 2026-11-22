package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import com.suzu.test.resource.lansync.LanPairing
import com.suzu.test.resource.lansync.LanSyncClient
import com.suzu.test.resource.lansync.LanTransfer
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class LanSyncWindowsTest {
    @Test fun exchangeActualPackagesWithWindows() = runBlocking {
        val args = InstrumentationRegistry.getArguments()
        val qr = args.getString("lanFixture")
        assumeTrue("Start the Windows --lan-android-fixture test host first.", qr != null)
        val parsed = LanPairing.qr(checkNotNull(qr))
        val pairing = if (args.getString("lanMode") == "manual")
            parsed.addresses.single().let { LanPairing.manual(parsed.code, "${it.host}:${it.port}") }
        else parsed
        val app = ApplicationProvider.getApplicationContext<Context>()
        val root = File(app.cacheDir, "lan_test_${UUID.randomUUID()}").apply { mkdirs() }
        val context = object : ContextWrapper(app) {
            override fun getFilesDir() = File(root, "files").apply { mkdirs() }
            override fun getCacheDir() = File(root, "cache").apply { mkdirs() }
        }
        val db = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build()
        try {
            LanSyncClient().use { client ->
                val verification = client.pair(pairing)
                assertTrue(verification.matches(Regex("[0-9]{6}")))
                client.confirm()
                val file = File(root, "download.zip")
                val upload = File(root, "upload.zip")
                val import = ResourcePackageImportService(context, db, ResourceImportService(context, db))
                var firstUseCount = 0
                repeat(2) { attempt ->
                    accept(client, LanTransfer.DOWNLOAD, fromComputer = attempt == 0)
                    client.download(file) { }
                    val summary = import.importReceivedZip(file) { _, _, _ -> }.summary
                    assertEquals(1 - attempt, summary.successCount)
                    assertEquals(attempt, summary.duplicateCount)
                    assertEquals(0, summary.failCount)
                    val image = db.resourceDao().getAllResourcesOrderedList().single()
                    if (attempt == 0) firstUseCount = image.useCount
                    assertEquals(firstUseCount, image.useCount)
                    assertEquals("同步 标签", image.keywords)
                    assertEquals("text:⭐", db.categoryDao().getCategoryByName("空收藏夹")?.iconPath)
                    client.writeStatus(1)
                }
                val exported = ResourcePackageExportService(context, db).exportToZip(Uri.fromFile(upload), "SuzuEmojy")
                assertEquals(1, exported.exportedCount)
                repeat(2) { attempt ->
                    accept(client, LanTransfer.UPLOAD, fromComputer = attempt == 0)
                    assertEquals(1, client.upload(upload) { })
                }
                client.writeStatus('Q'.code)
            }
        } finally {
            db.close()
            root.deleteRecursively()
        }
    }

    private fun accept(client: LanSyncClient, transfer: LanTransfer, fromComputer: Boolean) {
        val offer = checkNotNull(client.poll(if (fromComputer) null else transfer))
        assertEquals(transfer, offer.transfer)
        assertEquals(fromComputer, offer.fromComputer)
        if (fromComputer) client.writeStatus(1) else assertTrue(client.readDecision())
    }
}
