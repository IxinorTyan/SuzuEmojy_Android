package com.suzu.test

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class LanSyncMigrationTest {
    @get:Rule val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(), SuzuDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test fun upgradesExistingLibraryWithoutRewritingIt() {
        val instrument = InstrumentationRegistry.getInstrumentation()
        val name = "lan-sync-migration-${UUID.randomUUID()}"
        try {
            helper.createDatabase(name, 4).use { db ->
                db.execSQL("INSERT INTO categories (id, name, sort_order, created_at) VALUES (7, '表情', 0, 1000)")
                db.execSQL("""INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height,
                    byte_size, quality_score, use_count, created_at, keywords, sort_order)
                    VALUES (8, 'old.png', 'PNG', 0, 'old-key', 'old-md5', 1, 1, 10, 0, 0, 1000, '标签', 2)""")
                db.execSQL("INSERT INTO resource_categories VALUES (8, 7, 3, 1000)")
            }
            helper.runMigrationsAndValidate(name, 5, true, SuzuDatabase.MIGRATION_4_5).use { db ->
                db.query("""SELECT r.keywords, c.name, rc.sort_order FROM resource_categories rc
                    JOIN resources r ON r.id = rc.resource_id JOIN categories c ON c.id = rc.category_id""").use {
                    assertTrue(it.moveToFirst())
                    assertEquals("标签", it.getString(0))
                    assertEquals("表情", it.getString(1))
                    assertEquals(3, it.getInt(2))
                }
                db.query("SELECT COUNT(*) FROM lan_sync_state").use {
                    assertTrue(it.moveToFirst())
                    assertEquals(0, it.getInt(0))
                }
            }
        } finally {
            instrument.targetContext.deleteDatabase(name)
        }
    }
}
