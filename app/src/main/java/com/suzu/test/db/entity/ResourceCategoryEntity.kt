package com.suzu.test.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "resource_categories",
    primaryKeys = ["resource_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            entity = ResourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["resource_id"]),
        Index(value = ["category_id"])
    ]
)
data class ResourceCategoryEntity(
    @ColumnInfo(name = "resource_id")
    val resourceId: Long,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @ColumnInfo(name = "sort_order")
    val sortOrder: Int = 0,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis()
)
