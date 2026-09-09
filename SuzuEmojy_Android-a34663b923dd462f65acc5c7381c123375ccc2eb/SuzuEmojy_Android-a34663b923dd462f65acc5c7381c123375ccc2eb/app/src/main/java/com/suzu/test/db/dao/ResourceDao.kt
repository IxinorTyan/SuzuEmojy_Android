package com.suzu.test.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(resource: ResourceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(resource: ResourceEntity): Long

    @Update
    suspend fun update(resource: ResourceEntity)

    @Delete
    suspend fun delete(resource: ResourceEntity)

    @Query("DELETE FROM resources WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM resources WHERE id = :id")
    suspend fun getById(id: Long): ResourceEntity?

    @Query("SELECT * FROM resources WHERE filename = :filename")
    suspend fun getByFilename(filename: String): ResourceEntity?

    @Query("SELECT * FROM resources WHERE sync_key = :syncKey")
    suspend fun getBySyncKey(syncKey: String): ResourceEntity?

    @Query("SELECT * FROM resources WHERE pixel_md5 = :pixelMd5")
    suspend fun getByPixelMd5(pixelMd5: String): ResourceEntity?

    @Query("SELECT * FROM resources WHERE file_md5 = :fileMd5")
    suspend fun getByFileMd5(fileMd5: String): ResourceEntity?

    @Query("SELECT * FROM resources ORDER BY sort_order ASC, id ASC")
    fun getAllResourcesOrdered(): Flow<List<ResourceEntity>>

    @Query("SELECT * FROM resources ORDER BY sort_order ASC, id ASC")
    suspend fun getAllResourcesOrderedList(): List<ResourceEntity>

    @Query("SELECT * FROM resources ORDER BY created_at DESC, id DESC")
    fun getAllResourcesFlow(): Flow<List<ResourceEntity>>

    @Query("SELECT * FROM resources ORDER BY created_at DESC, id DESC")
    suspend fun getAllResources(): List<ResourceEntity>

    @Query("UPDATE resources SET keywords = :keywords WHERE id = :id")
    suspend fun updateKeywords(id: Long, keywords: String)

    @Query("SELECT * FROM resources WHERE id IN (:ids)")
    suspend fun getResourcesByIds(ids: List<Long>): List<ResourceEntity>

    @Query("DELETE FROM resources WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>): Int

    @Query("UPDATE resources SET keywords = :keywords WHERE id IN (:ids)")
    suspend fun updateKeywordsBatch(ids: List<Long>, keywords: String)

    @androidx.room.Transaction
    suspend fun batchAppendKeywords(ids: List<Long>, appendRaw: String): Int {
        if (ids.isEmpty() || appendRaw.isBlank()) return 0
        val resources = getResourcesByIds(ids)
        var changedCount = 0
        for (res in resources) {
            val merged = com.suzu.test.resource.KeywordUtils.mergeTags(res.keywords, appendRaw)
            val normalized = com.suzu.test.resource.KeywordUtils.normalize(merged)
            if (normalized != res.keywords) {
                updateKeywords(res.id, normalized)
                changedCount++
            }
        }
        return changedCount
    }

    @androidx.room.Transaction
    suspend fun batchRemoveKeywords(ids: List<Long>, removeRaw: String): Int {
        if (ids.isEmpty() || removeRaw.isBlank()) return 0
        val resources = getResourcesByIds(ids)
        var changedCount = 0
        for (res in resources) {
            val remaining = com.suzu.test.resource.KeywordUtils.removeTags(res.keywords, removeRaw)
            val normalized = com.suzu.test.resource.KeywordUtils.normalize(remaining)
            if (normalized != res.keywords) {
                updateKeywords(res.id, normalized)
                changedCount++
            }
        }
        return changedCount
    }

    @Query("SELECT MIN(sort_order) FROM resources")
    suspend fun getMinSortOrder(): Int?

    @Query("UPDATE resources SET sort_order = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, sortOrder: Int)

    @androidx.room.Transaction
    suspend fun reorderAll(orderedIds: List<Long>) {
        orderedIds.forEachIndexed { index, id ->
            updateSortOrder(id, index)
        }
    }

    @androidx.room.Transaction
    suspend fun moveResourcesToFront(ids: List<Long>): Int {
        if (ids.isEmpty()) return 0
        val minSort = getMinSortOrder() ?: 0
        ids.forEachIndexed { index, id ->
            updateSortOrder(id, minSort - 1 - index)
        }
        return ids.size
    }

    @Query("""
        SELECT * FROM resources 
        WHERE lower(keywords) LIKE '%' || :kw || '%' OR lower(filename) LIKE '%' || :kw || '%'
        ORDER BY sort_order ASC, id ASC
    """)
    fun searchResourcesSubstring(kw: String): Flow<List<ResourceEntity>>

    @Query("""
        SELECT * FROM resources 
        WHERE lower(keywords) LIKE :kw || '%' OR lower(keywords) LIKE '% ' || :kw || '%' OR lower(filename) LIKE :kw || '%'
        ORDER BY sort_order ASC, id ASC
    """)
    fun searchResourcesPrefix(kw: String): Flow<List<ResourceEntity>>

    fun searchResources(kw: String): Flow<List<ResourceEntity>> {
        val cleanKw = kw.trim().lowercase()
        return if (com.suzu.test.resource.KeywordUtils.KEYWORD_MATCH_MODE == com.suzu.test.resource.MatchMode.SUBSTRING) {
            searchResourcesSubstring(cleanKw)
        } else {
            searchResourcesPrefix(cleanKw)
        }
    }

    @Query("""
        SELECT r.* FROM resources r
        INNER JOIN resource_categories rc ON r.id = rc.resource_id
        WHERE rc.category_id = :categoryId
        ORDER BY rc.sort_order ASC, rc.added_at DESC
    """)
    fun getResourcesByCategoryIdFlow(categoryId: Long): Flow<List<ResourceEntity>>

    @Query("""
        SELECT r.* FROM resources r
        INNER JOIN recent_history rh ON r.id = rh.resource_id
        ORDER BY rh.used_at DESC
        LIMIT :limit
    """)
    fun getRecentResourcesFlow(limit: Int): Flow<List<ResourceEntity>>

    @Query("SELECT DISTINCT byte_size FROM resources")
    suspend fun getAllByteSizes(): List<Long>

    @Query("SELECT COUNT(*) FROM resources")
    fun getResourceCountFlow(): Flow<Int>

    @Query("SELECT * FROM resources ORDER BY created_at DESC, id DESC LIMIT :limit")
    fun getRecentAddedResourcesFlow(limit: Int): Flow<List<ResourceEntity>>

    @Query("""
        SELECT r.* FROM resources r
        WHERE (:noKw = 0 OR r.keywords IS NULL OR TRIM(r.keywords) = '')
          AND (:noCat = 0 OR NOT EXISTS(
              SELECT 1 FROM resource_categories rc WHERE rc.resource_id = r.id))
          AND (:anim = 0 OR (:anim = 1 AND r.filename LIKE '%.gif')
                         OR (:anim = 2 AND r.filename NOT LIKE '%.gif'))
          AND (:catId = 0 OR EXISTS(
              SELECT 1 FROM resource_categories rc2 WHERE rc2.resource_id = r.id AND rc2.category_id = :catId))
        ORDER BY
          CASE WHEN :catId > 0 THEN (SELECT rc.sort_order FROM resource_categories rc
                WHERE rc.resource_id = r.id AND rc.category_id = :catId)
               ELSE r.sort_order END IS NULL ASC,
          CASE WHEN :catId > 0 THEN (SELECT rc.sort_order FROM resource_categories rc
                WHERE rc.resource_id = r.id AND rc.category_id = :catId)
               ELSE r.sort_order END ASC,
          CASE WHEN :catId > 0 THEN (SELECT rc.added_at FROM resource_categories rc
                WHERE rc.resource_id = r.id AND rc.category_id = :catId)
               ELSE r.id END DESC,
          r.id ASC
    """)
    fun getFilteredResourcesFlow(noKw: Int, noCat: Int, anim: Int, catId: Long): Flow<List<ResourceEntity>>
}
