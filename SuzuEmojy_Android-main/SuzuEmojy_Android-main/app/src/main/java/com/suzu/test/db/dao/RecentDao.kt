package com.suzu.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suzu.test.db.entity.RecentHistoryEntity
import kotlinx.coroutines.flow.Flow

import androidx.room.Transaction

@Dao
interface RecentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordUsage(recent: RecentHistoryEntity)

    @Query("DELETE FROM recent_history WHERE resource_id = :resourceId")
    suspend fun deleteByResourceId(resourceId: Long)

    @Query("DELETE FROM recent_history")
    suspend fun clearAll()

    @Query("SELECT * FROM recent_history ORDER BY used_at DESC LIMIT :limit")
    fun getRecentHistoryFlow(limit: Int): Flow<List<RecentHistoryEntity>>

    @Query("""
        DELETE FROM recent_history
        WHERE id NOT IN (
            SELECT id FROM recent_history ORDER BY used_at DESC LIMIT :keepLimit
        )
    """)
    suspend fun trimHistory(keepLimit: Int)

    @Transaction
    suspend fun recordUsageAndTrim(recent: RecentHistoryEntity, keepLimit: Int) {
        recordUsage(recent)
        trimHistory(keepLimit)
    }
}
