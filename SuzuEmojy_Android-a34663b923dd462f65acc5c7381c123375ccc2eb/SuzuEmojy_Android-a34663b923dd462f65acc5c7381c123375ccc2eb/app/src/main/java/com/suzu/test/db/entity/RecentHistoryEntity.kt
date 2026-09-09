package com.suzu.test.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recent_history",
    foreignKeys = [
        ForeignKey(
            entity = ResourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["resource_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["resource_id"], unique = true),
        Index(value = ["used_at"])
    ]
)
data class RecentHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "resource_id")
    val resourceId: Long,

    @ColumnInfo(name = "used_at")
    val usedAt: Long = System.currentTimeMillis()
)
