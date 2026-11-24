package com.suzu.test

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Base64
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.LanSyncStateEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.resource.LibraryRefurbish
import com.suzu.test.resource.ResourceImportService
import com.suzu.test.resource.clean.ResourceCleaner
import com.suzu.test.resource.hash.ResourceHasher
import com.suzu.test.resource.lansync.LanSyncAndroidLibrary
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class LibraryRefurbishTest {
    @get:Rule val oldDatabase = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
        SuzuDatabase::class.java, emptyList(), FrameworkSQLiteOpenHelperFactory())

    @Test fun regularImportNormalizesBeforeDuplicateLookup() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val dir = Files.createTempDirectory(context.cacheDir.toPath(), "normalize-").toFile()
        try {
            Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build().withDatabase { db ->
                val service = ResourceImportService(context, db, dir)
                val original = png(300, 1200)
                val added = service.import(original)
                val saved = db.resourceDao().getById(added.resourceId)!!
                assertEquals(180, saved.width)
                assertEquals(720, saved.height)
                assertEquals(added.resourceId, service.import(original).existingResourceId)
                val bytes = File(dir, saved.filename).readBytes()
                assertEquals(added.resourceId, service.import(bytes).existingResourceId)
                assertEquals(saved.syncKey, rawKey(bytes))

                val small = service.import(png(32, 18))
                assertEquals(32, db.resourceDao().getById(small.resourceId)!!.width)
                val gif = service.import(animated)
                assertArrayEquals(animated, File(dir, gif.filename).readBytes())
                assertTrue(service.import(animated).isDuplicate)

                val single = service.import(Base64.decode("R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAkQBADs=", Base64.DEFAULT))
                assertTrue(single.filename.endsWith(".png"))
            }
        } finally { dir.deleteRecursively() }
    }

    @Test fun upgradingOldLibraryPreservesRowsAndDoesNotRepeat() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val root = Files.createTempDirectory(context.cacheDir.toPath(), "refurbish-").toFile()
        val dir = File(root, "resources")
        val name = "refurbish-test-${UUID.randomUUID()}.db"
        fun open() = Room.databaseBuilder(context, SuzuDatabase::class.java, name).build()
        try {
            val original = png(1200, 600)
            val before = open().withDatabase { db ->
                val service = ResourceImportService(context, db, dir)
                val still = service.importSynced(original, rawKey(original))
                val gif = service.importSynced(animated, "f:${ResourceHasher.fileMd5(animated)}")
                val dao = db.resourceDao()
                dao.updateKeywords(still.resourceId, "keep")
                val category = db.categoryDao().insertCategory(CategoryEntity(name = "keep"))
                db.resourceCategoryDao().addResourceToCategory(ResourceCategoryEntity(still.resourceId, category))
                still to gif
            }
            val beforeBytes = File(dir, before.first.filename).readBytes()
            val after = open().withDatabase { db ->
                assertEquals(1200, db.resourceDao().getById(before.first.resourceId)!!.width)
                LibraryRefurbish(root).run(db)
                val migrated = db.resourceDao().getById(before.first.resourceId)!!
                assertEquals(720, migrated.width)
                assertEquals(360, migrated.height)
                assertEquals("keep", migrated.keywords)
                assertEquals("keep", db.resourceCategoryDao().getCategoriesForResource(migrated.id).single().name)
                assertEquals(before.second.filename, db.resourceDao().getById(before.second.resourceId)!!.filename)
                assertArrayEquals(animated, File(dir, before.second.filename).readBytes())
                assertArrayEquals(beforeBytes, File(dir, before.first.filename).readBytes())
                val service = ResourceImportService(context, db, dir)
                assertEquals(migrated.id, service.import(original).existingResourceId)
                // Protocol imports retain their supplied identity and are not resized on reopen.
                service.importSynced(original, rawKey(original))
            }
            open().withDatabase { db ->
                LibraryRefurbish(root).run(db)
                assertArrayEquals(beforeBytes, File(root, "library-refurbish-v1/original/${before.first.filename}").readBytes())
                assertFalse(File(dir, before.first.filename).exists())
                assertEquals(1200, db.resourceDao().getById(after.resourceId)!!.width)
                assertEquals(3, db.resourceDao().getAllResources().size)
                assertEquals(1, File(root, "library-refurbish-v1/original").listFiles()!!.size)
            }
        } finally {
            context.deleteDatabase(name)
            root.deleteRecursively()
        }
    }

    @Test fun firstReadAndCommittedRowsSurviveADeferredMigration() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val root = Files.createTempDirectory(context.cacheDir.toPath(), "refurbish-resume-").toFile()
        val dir = File(root, "resources")
        val name = "refurbish-test-${UUID.randomUUID()}.db"
        fun open() = Room.databaseBuilder(context, SuzuDatabase::class.java, name).build()
        try {
            val rows = open().withDatabase { db ->
                val service = ResourceImportService(context, db, dir)
                listOf(png(1200, 600), png(600, 1200)).map { service.importSynced(it, rawKey(it)) }
            }
            open().withDatabase { db ->
                val staging = CountDownLatch(1)
                val release = CountDownLatch(1)
                val migration = async(Dispatchers.IO) {
                    runCatching {
                        LibraryRefurbish(root, availableBytes = {
                            staging.countDown()
                            check(release.await(10, TimeUnit.SECONDS))
                            0
                        }).run(db)
                    }
                }
                try {
                    assertTrue(staging.await(10, TimeUnit.SECONDS))
                    withTimeout(3_000) {
                        assertEquals(1200, db.resourceDao().getById(rows[0].resourceId)!!.width)
                    }
                } finally { release.countDown() }
                assertTrue(migration.await().exceptionOrNull() is IOException)
                assertEquals(rows[0].filename, db.resourceDao().getById(rows[0].resourceId)!!.filename)
                assertEquals(rows[0].resourceId, ResourceImportService(context, db, dir).import(png(1200, 600)).existingResourceId)

                // Stop after the first database/file commit, before a second image is switched.
                val stop = IOException("stop after commit")
                val interrupted = runCatching { LibraryRefurbish(root).run(db) { throw stop } }
                assertSame(stop, interrupted.exceptionOrNull())
                assertEquals(720, db.resourceDao().getById(rows[0].resourceId)!!.width)
                assertEquals(1200, db.resourceDao().getById(rows[1].resourceId)!!.height)
                // Simulate a process killed after placing the next file but before the SQLite commit.
                File(dir, "refurbish-v1-${rows[1].resourceId}.png").writeText("interrupted before commit")
            }
            open().withDatabase { db ->
                val commits = mutableListOf<Long>()
                LibraryRefurbish(root).run(db) { commits += it }
                assertEquals(listOf(rows[1].resourceId), commits)
                assertEquals(720, db.resourceDao().getById(rows[1].resourceId)!!.height)
                assertTrue(File(root, "library-refurbish-v1/original/${rows[0].filename}").isFile)
                assertEquals(2, db.resourceDao().getAllResources().size)
            }
        } finally {
            context.deleteDatabase(name)
            root.deleteRecursively()
        }
    }

    @Test fun aPreviouslySyncedLibraryKeepsItsWireIdentity() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val root = Files.createTempDirectory(context.cacheDir.toPath(), "refurbish-pair-").toFile()
        val dir = File(root, "resources")
        try {
            Room.inMemoryDatabaseBuilder(context, SuzuDatabase::class.java).build().withDatabase { db ->
                val source = png(1200, 600)
                val importer = ResourceImportService(context, db, dir)
                val legacy = importer.importSynced(source, rawKey(source))
                val peer = "1".repeat(32)
                val revision = "2".repeat(32)
                db.lanSyncStateDao().write(LanSyncStateEntity("revision:$peer", revision))
                val library = LanSyncAndroidLibrary(object : ContextWrapper(context) {
                    override fun getFilesDir() = root
                }, db)
                val device = library.device()
                val before = library.capture(device, peer)
                LibraryRefurbish(root).run(db)
                val row = db.resourceDao().getById(legacy.resourceId)!!
                assertEquals(legacy.syncKey, row.syncKey)
                assertEquals(1200, row.width)
                assertEquals(before, library.capture(device, peer))
                assertEquals(legacy.resourceId, importer.import(source).existingResourceId)
                assertEquals(1, db.resourceDao().getAllResources().size)
            }
        } finally { root.deleteRecursively() }
    }

    @Test fun roomV4UpgradeThenBackgroundCleanPreservesResourceIds() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val root = Files.createTempDirectory(context.cacheDir.toPath(), "refurbish-v4-").toFile()
        val name = "refurbish-v4-${UUID.randomUUID()}.db"
        try {
            val bytes = png(1200, 600)
            val pixel = rawKey(bytes).substring(2)
            val filename = "legacy.png"
            val dir = File(root, "resources").apply { mkdirs() }
            File(dir, filename).writeBytes(bytes)
            oldDatabase.createDatabase(name, 4).use { db ->
                db.execSQL("INSERT INTO categories (id, name, sort_order, created_at) VALUES (7, '旧分类', 0, 1000)")
                db.execSQL("""INSERT INTO resources (id, filename, format, is_animated, sync_key, pixel_md5, file_md5, width, height,
                    byte_size, quality_score, use_count, created_at, keywords, sort_order)
                    VALUES (8, ?, 'PNG', 0, ?, ?, ?, 1200, 600, ?, 0, 3, 1000, '旧关键词', 2)""",
                    arrayOf(filename, "p:$pixel", pixel, ResourceHasher.fileMd5(bytes), bytes.size))
                db.execSQL("INSERT INTO resource_categories VALUES (8, 7, 3, 1000)")
            }
            Room.databaseBuilder(context, SuzuDatabase::class.java, name).addMigrations(SuzuDatabase.MIGRATION_4_5)
                .build().withDatabase { db ->
                    assertEquals(1200, db.resourceDao().getById(8)!!.width)
                    LibraryRefurbish(root).run(db)
                    val row = db.resourceDao().getById(8)!!
                    assertEquals(720, row.width)
                    assertEquals("旧关键词", row.keywords)
                    assertEquals(3, row.useCount)
                    assertEquals("旧分类", db.resourceCategoryDao().getCategoriesForResource(8).single().name)
                    assertEquals(8L, ResourceImportService(context, db, dir).import(bytes).existingResourceId)
                }
        } finally {
            context.deleteDatabase(name)
            root.deleteRecursively()
        }
    }

    private inline fun <T> SuzuDatabase.withDatabase(block: (SuzuDatabase) -> T): T =
        try { block(this) } finally { close() }

    private fun rawKey(bytes: ByteArray) = ResourceCleaner.decodeStatic(bytes)!!.use { "p:${it.md5}" }

    private fun png(width: Int, height: Int): ByteArray {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(width * height) { i ->
            val x = i % width
            val y = i / width
            (if (x < width / 5) 0x80000000.toInt() else 0xff000000.toInt()) or (x % 256 shl 16) or (y % 256 shl 8)
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return try { ByteArrayOutputStream().use { output ->
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output))
            output.toByteArray()
        } } finally { bitmap.recycle() }
    }

    private val animated = Base64.decode(
        "R0lGODlhAQABAIAAAAAAAP///yH5BAQAAAAALAAAAAABAAEAAAICRAEAIfkEBAAAAAAsAAAAAAEAAQAAAgJMAQA7", Base64.DEFAULT)
}
