package com.suzu.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.suzu.test.db.dao.CategoryDao
import com.suzu.test.db.dao.RecentDao
import com.suzu.test.db.dao.ResourceCategoryDao
import com.suzu.test.db.dao.ResourceDao
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.RecentHistoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.db.entity.ResourceEntity

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        ResourceEntity::class,
        CategoryEntity::class,
        ResourceCategoryEntity::class,
        RecentHistoryEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class SuzuDatabase : RoomDatabase() {

    abstract fun resourceDao(): ResourceDao
    abstract fun categoryDao(): CategoryDao
    abstract fun resourceCategoryDao(): ResourceCategoryDao
    abstract fun recentDao(): RecentDao

    companion object {
        private const val DB_NAME = "suzu_emojy.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE resources ADD COLUMN keywords TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE resources ADD COLUMN sort_order INTEGER NOT NULL DEFAULT 0")
                db.execSQL("""
                    UPDATE resources SET sort_order = (
                        SELECT COUNT(*) FROM resources r2
                        WHERE r2.created_at > resources.created_at
                           OR (r2.created_at = resources.created_at AND r2.id > resources.id)
                    )
                """.trimIndent())
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 方案 A: 存量 resource_categories 按原显示顺序 (resources.sort_order ASC, resources.id ASC) 回填连续递增的 sort_order
                db.execSQL("""
                    UPDATE resource_categories SET sort_order = (
                        SELECT COUNT(*) FROM resource_categories rc2
                        INNER JOIN resources r2 ON rc2.resource_id = r2.id
                        INNER JOIN resources r1 ON resource_categories.resource_id = r1.id
                        WHERE rc2.category_id = resource_categories.category_id
                          AND (r2.sort_order < r1.sort_order OR (r2.sort_order = r1.sort_order AND r2.id < r1.id))
                    )
                """.trimIndent())
            }
        }

        @Volatile
        private var INSTANCE: SuzuDatabase? = null

        fun getInstance(context: Context): SuzuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SuzuDatabase::class.java,
                    DB_NAME
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
