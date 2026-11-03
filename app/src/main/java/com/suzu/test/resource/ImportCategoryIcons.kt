package com.suzu.test.resource

import android.content.Context
import androidx.room.withTransaction
import com.suzu.test.db.SuzuDatabase

/** Assign once after category membership/sorting is committed; custom icons take priority. */
internal object ImportCategoryIcons {
    suspend fun assignMissing(context: Context, database: SuzuDatabase, categoryIds: Collection<Long>) {
        for (categoryId in categoryIds.distinct()) {
            database.withTransaction {
                val categoryDao = database.categoryDao()
                val category = categoryDao.getCategoryById(categoryId) ?: return@withTransaction
                if (PackageIcons.isValid(context, database, category.iconPath)) return@withTransaction
                val first = database.resourceCategoryDao()
                    .getThumbnailPreloadResources(categoryId, 1).firstOrNull() ?: return@withTransaction
                categoryDao.updateCategory(category.copy(iconPath = "res:${first.id}"))
            }
        }
    }
}
