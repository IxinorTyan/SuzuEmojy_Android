package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.util.Base64
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.exportpkg.ResourcePackageExportService
import com.suzu.test.resource.importpkg.ResourcePackageImportService
import com.suzu.test.resource.lansync.LanPairing
import com.suzu.test.resource.lansync.LanReconciliation
import com.suzu.test.resource.lansync.LanSyncAndroidLibrary
import com.suzu.test.resource.lansync.LanSyncClient
import com.suzu.test.resource.lansync.LanTransfer
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.IOException
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class LanSyncWindowsTest {
    @Test fun largeGifsAndBrokenLocalFilesDoNotBlockSync() = runBlocking {
        val qr = InstrumentationRegistry.getArguments().getString("streamFixture")
        assumeTrue("Start the Windows streaming fixture first.", qr != null)
        val app = ApplicationProvider.getApplicationContext<Context>()
        val root = File(app.cacheDir, "lan_streaming_${UUID.randomUUID()}").apply { mkdirs() }
        val context = object : ContextWrapper(app) {
            override fun getFilesDir() = File(root, "files").apply { mkdirs() }
            override fun getCacheDir() = File(root, "cache").apply { mkdirs() }
        }
        val db = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build()
        try {
            LanSyncClient().use { client ->
                client.pair(LanPairing.qr(checkNotNull(qr)))
                assertTrue(client.streamingSync)
                client.confirm()
                val sync = LanReconciliation(LanSyncAndroidLibrary(context, db), context.cacheDir)
                suspend fun round(): String {
                    accept(client, LanTransfer.SYNC, fromComputer = false)
                    return sync.run(client) { _, _ -> }
                }
                val importer = ResourceImportService(context, db)
                val gif = Base64.decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7", Base64.DEFAULT)
                val small = importer.import(gif)
                val smallFile = File(context.filesDir, "resources/${small.filename}")
                assertEquals("两端同步完成。", round())
                val original = db.resourceDao().getAllResourcesOrderedList().single { !it.isAnimated }
                smallFile.writeBytes(byteArrayOf(5, 6, 7))
                val large = File(smallFile.parentFile, "lan-large.gif")
                large.outputStream().buffered().use {
                    with(it) {
                    write(gif, 0, gif.size - 1)
                    write(0x21); write(0xfe)
                    val block = ByteArray(256) { 0x56 }.apply { this[0] = 0xff.toByte() }
                    repeat(192 * 1024) { write(block) }
                    write(0); write(0x3b)
                    }
                }
                val md5 = large.inputStream().use(com.suzu.test.resource.hash.ResourceHasher::fileMd5)
                val addedKey = "f:$md5"
                db.resourceDao().insert(com.suzu.test.db.entity.ResourceEntity(filename = large.name, format = "GIF",
                    isAnimated = true, syncKey = addedKey, pixelMd5 = null, fileMd5 = md5, width = 1, height = 1, byteSize = large.length()))
                assertEquals("两端同步完成，跳过 14 个无法校验的表情。", round())
                assertEquals(original.id, db.resourceDao().getBySyncKey(original.syncKey)?.id)
                assertEquals(small.resourceId, db.resourceDao().getBySyncKey(small.syncKey)?.id)
                assertTrue(smallFile.readBytes().contentEquals(byteArrayOf(5, 6, 7)))
                val largeRows = db.resourceDao().getAllResourcesOrderedList().filter { it.byteSize > 48L * 1024 * 1024 }
                assertEquals(2, largeRows.size)
                assertTrue(largeRows.any { it.syncKey == addedKey })
                smallFile.writeBytes(gif)
                assertEquals("两端同步完成。", round())
                assertEquals(4, db.resourceDao().getAllResourcesOrderedList().size)
                client.writeStatus('Q'.code)
            }
        } finally {
            db.close()
            root.deleteRecursively()
        }
    }

    @Test fun reconcileChangesAndResumeWithWindows() = runBlocking {
        val args = InstrumentationRegistry.getArguments()
        val qr = args.getString("syncFixture")
        val retryQr = args.getString("retryFixture")
        assumeTrue("Start the Windows reconciliation fixture first.", qr != null && retryQr != null)
        val app = ApplicationProvider.getApplicationContext<Context>()
        val root = File(app.cacheDir, "lan_sync_test_${UUID.randomUUID()}").apply { mkdirs() }
        val context = object : ContextWrapper(app) {
            override fun getFilesDir() = File(root, "files").apply { mkdirs() }
            override fun getCacheDir() = File(root, "cache").apply { mkdirs() }
        }
        val db = Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build()
        try {
            val gif = Base64.decode("R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7", Base64.DEFAULT)
            val importer = ResourceImportService(context, db)
            val initial = importer.import(gif)
            val phoneGroup = db.categoryDao().insertCategory(CategoryEntity(name = "手机初始", iconPath = "res:${initial.resourceId}"))
            db.resourceCategoryDao().addResourceToCategory(ResourceCategoryEntity(initial.resourceId, phoneGroup))
            val sync = LanReconciliation(LanSyncAndroidLibrary(context, db), context.cacheDir)
            suspend fun round(client: LanSyncClient, windows: Boolean = false) {
                accept(client, LanTransfer.SYNC, fromComputer = windows)
                assertEquals("两端同步完成。", sync.run(client) { _, _ -> })
            }
            lateinit var stableKey: String
            LanSyncClient().use { client ->
                val parsed = LanPairing.qr(checkNotNull(qr))
                val pairing = if (args.getString("lanMode") == "qr") parsed else
                    parsed.addresses.single().let { LanPairing.manual(parsed.code, "${it.host}:${it.port}") }
                client.pair(pairing)
                assertTrue(client.canSync)
                client.confirm()
                round(client, windows = true)
                val first = db.resourceDao().getAllResourcesOrderedList()
                assertEquals(4, first.size)
                assertTrue(db.categoryDao().getCategoryByName("图标收藏夹")?.iconPath?.startsWith("file:category_icons/") == true)
                val folder = checkNotNull(db.categoryDao().getCategoryByName("电脑初始"))
                val tagged = first.single { it.keywords == "keep remove" }
                val gone = db.resourceCategoryDao().getResourcesForCategoryList(folder.id).single { it.id != tagged.id }
                val stable = first.single { it.syncKey.startsWith("p:") && it.id != tagged.id && it.id != gone.id }
                stableKey = stable.syncKey
                val beforeOrder = first.filter { it.id == tagged.id || it.id == stable.id }.map { it.id }
                db.resourceDao().updateKeywords(tagged.id, "keep phone")
                db.resourceCategoryDao().removeResourceFromCategory(tagged.id, folder.id)
                db.resourceDao().deleteById(initial.resourceId)
                // Keep the icon's separate v1 attachment even after removing its source resource.
                assertTrue(db.categoryDao().getCategoryById(phoneGroup)?.iconPath?.startsWith("file:") == true)
                val different = gif.dropLast(1).toByteArray() + byteArrayOf(0x21, 0xFE.toByte(), 1, 0x41, 0, 0x3B)
                val added = importer.import(different)
                assertTrue(added.syncKey != initial.syncKey)
                round(client)
                val second = db.resourceDao().getAllResourcesOrderedList()
                assertEquals(4, second.size)
                assertEquals(beforeOrder.reversed(), second.filter { it.id in beforeOrder }.map { it.id })
                assertEquals("desktop keep phone", db.resourceDao().getById(tagged.id)?.keywords)
                assertEquals(emptyList<Long>(), db.resourceCategoryDao().getCategoryIdsByResourceId(tagged.id))
                assertEquals(folder.id, db.categoryDao().getCategoryByName("电脑改名")?.id)
                assertEquals(null, db.resourceDao().getById(gone.id))
                assertEquals(null, db.resourceDao().getBySyncKey(initial.syncKey))
                assertTrue(second.any { it.syncKey == added.syncKey })
                try {
                    round(client)
                    error("The connection should drop after the computer commits.")
                } catch (_: IOException) { }
                assertEquals("", db.resourceDao().getBySyncKey(stableKey)?.keywords)
            }
            LanSyncClient().use { client ->
                client.pair(LanPairing.qr(checkNotNull(retryQr)))
                client.confirm()
                round(client)
                assertEquals("retry", db.resourceDao().getBySyncKey(stableKey)?.keywords)
                val synced = db.resourceDao().getAllResourcesOrderedList()
                round(client)
                assertEquals(synced, db.resourceDao().getAllResourcesOrderedList())
                client.writeStatus('Q'.code)
            }
        } finally {
            db.close()
            root.deleteRecursively()
        }
    }

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
