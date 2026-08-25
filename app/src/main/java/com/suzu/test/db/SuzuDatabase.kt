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
    version = 2,
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

        @Volatile
        private var INSTANCE: SuzuDatabase? = null

        fun getInstance(context: Context): SuzuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SuzuDatabase::class.java,
                    DB_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
