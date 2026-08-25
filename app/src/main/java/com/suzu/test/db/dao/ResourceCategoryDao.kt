package com.suzu.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.suzu.test.db.entity.CategoryEntity
import com.suzu.test.db.entity.ResourceCategoryEntity
import com.suzu.test.db.entity.ResourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResourceCategoryDao {

    @Query("""
        SELECT r.* FROM resources r
        INNER JOIN resource_categories rc ON r.id = rc.resource_id
        WHERE rc.category_id = :categoryId
        ORDER BY rc.sort_order ASC, rc.added_at DESC
    """)
    fun getResourcesForCategory(categoryId: Long): Flow<List<ResourceEntity>>

    @Query("""
        SELECT r.* FROM resources r
        INNER JOIN resource_categories rc ON r.id = rc.resource_id
        WHERE rc.category_id = :categoryId
        ORDER BY rc.sort_order ASC, rc.added_at DESC
    """)
    suspend fun getResourcesForCategoryList(categoryId: Long): List<ResourceEntity>

    @Query("""
        SELECT c.* FROM categories c
        INNER JOIN resource_categories rc ON c.id = rc.category_id
        WHERE rc.resource_id = :resourceId
        ORDER BY c.sort_order ASC, c.id ASC
    """)
    suspend fun getCategoriesForResource(resourceId: Long): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResourceToCategory(relation: ResourceCategoryEntity)

    @Query("DELETE FROM resource_categories WHERE resource_id = :resourceId AND category_id = :categoryId")
    suspend fun removeResourceFromCategory(resourceId: Long, categoryId: Long)

    @Query("DELETE FROM resource_categories WHERE category_id = :categoryId AND resource_id IN (:resourceIds)")
    suspend fun removeResourcesFromCategoryBatch(categoryId: Long, resourceIds: List<Long>): Int

    @Query("DELETE FROM resource_categories WHERE category_id = :categoryId")
    suspend fun clearCategoryResources(categoryId: Long)

    @Query("SELECT category_id FROM resource_categories WHERE resource_id = :resourceId")
    suspend fun getCategoryIdsByResourceId(resourceId: Long): List<Long>

    @Query("SELECT resource_id FROM resource_categories WHERE category_id = :categoryId AND resource_id IN (:resourceIds)")
    suspend fun getExistingResourceIdsInCategory(categoryId: Long, resourceIds: List<Long>): List<Long>

    @Query("SELECT MIN(sort_order) FROM resource_categories WHERE category_id = :categoryId")
    suspend fun getMinSortOrderForCategory(categoryId: Long): Int?

    @Query("SELECT MAX(sort_order) FROM resource_categories WHERE category_id = :categoryId")
    suspend fun getMaxSortOrderForCategory(categoryId: Long): Int?

    @Transaction
    suspend fun addResourcesToCategoryBatch(resourceIds: List<Long>, categoryId: Long): Int {
        if (resourceIds.isEmpty()) return 0
        val existing = getExistingResourceIdsInCategory(categoryId, resourceIds).toSet()
        val toAdd = resourceIds.filter { it !in existing }
        if (toAdd.isEmpty()) return 0

        var minSort = getMinSortOrderForCategory(categoryId) ?: 0
        val now = System.currentTimeMillis()
        for (resId in toAdd) {
            minSort--
            addResourceToCategory(ResourceCategoryEntity(
                resourceId = resId,
                categoryId = categoryId,
                sortOrder = minSort,
                addedAt = now
            ))
        }
        return toAdd.size
    }

    @Transaction
    suspend fun moveResourcesToFrontInCategory(categoryId: Long, resourceIds: List<Long>): Int {
        if (resourceIds.isEmpty()) return 0
        val existing = getExistingResourceIdsInCategory(categoryId, resourceIds).toSet()
        val validIds = resourceIds.filter { it in existing }
        if (validIds.isEmpty()) return 0
        val minSort = getMinSortOrderForCategory(categoryId) ?: 0
        validIds.forEachIndexed { index, resId ->
            updateSortOrder(resId, categoryId, minSort - 1 - index)
        }
        return validIds.size
    }

    @Transaction
    suspend fun reorderResourcesInCategory(categoryId: Long, orderedResourceIds: List<Long>) {
        orderedResourceIds.forEachIndexed { index, resourceId ->
            updateSortOrder(resourceId, categoryId, index)
        }
    }

    @Query("UPDATE resource_categories SET sort_order = :sortOrder WHERE resource_id = :resourceId AND category_id = :categoryId")
    suspend fun updateSortOrder(resourceId: Long, categoryId: Long, sortOrder: Int)
}
