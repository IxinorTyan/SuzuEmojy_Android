package com.suzu.test

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suzu.test.db.SuzuDatabase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseMigrationTest {

    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        SuzuDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun testMigration1To2() {
        var db = helper.createDatabase(TEST_DB, 1).apply {
            // 插入 v1 数据
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (1, 'img1.png', 'PNG', 0, 'k1', 'm1', 100, 100, 1000, 0.0, 0, 1000)")
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (2, 'img2.png', 'PNG', 0, 'k2', 'm2', 100, 100, 1000, 0.0, 0, 2000)")
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (3, 'img3.png', 'PNG', 0, 'k3', 'm3', 100, 100, 1000, 0.0, 0, 3000)")
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (4, 'img4.png', 'PNG', 0, 'k4', 'm4', 100, 100, 1000, 0.0, 0, 4000)")
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (5, 'img5.png', 'PNG', 0, 'k5', 'm5', 100, 100, 1000, 0.0, 0, 5000)")
            execSQL("INSERT INTO resources (id, filename, format, is_animated, sync_key, file_md5, width, height, byte_size, quality_score, use_count, created_at) VALUES (6, 'img6.png', 'PNG', 0, 'k6', 'm6', 100, 100, 1000, 0.0, 0, 5000)")
            close()
        }

        // 执行迁移到 v2
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, SuzuDatabase.MIGRATION_1_2)

        // 验证数据条数、sort_order 与顺序
        val cursor = db.query("SELECT id, sort_order, keywords FROM resources ORDER BY sort_order ASC, id ASC")
        val ids = mutableListOf<Long>()
        val sortOrders = mutableListOf<Int>()
        while (cursor.moveToNext()) {
            ids.add(cursor.getLong(0))
            sortOrders.add(cursor.getInt(1))
            assertEquals("", cursor.getString(2))
        }
        cursor.close()

        assertEquals(6, ids.size)
        // 原降序顺序：id=6 (5000ms, id 6), id=5 (5000ms, id 5), id=4 (4000ms), id=3 (3000ms), id=2 (2000ms), id=1 (1000ms)
        assertEquals(listOf(6L, 5L, 4L, 3L, 2L, 1L), ids)
        assertEquals(listOf(0, 1, 2, 3, 4, 5), sortOrders)
    }
}
