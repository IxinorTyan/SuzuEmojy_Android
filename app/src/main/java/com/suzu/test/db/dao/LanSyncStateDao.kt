package com.suzu.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suzu.test.db.entity.LanSyncStateEntity

@Dao
interface LanSyncStateDao {
    @Query("SELECT value FROM lan_sync_state WHERE `key` = :key")
    suspend fun read(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun write(value: LanSyncStateEntity)
}
