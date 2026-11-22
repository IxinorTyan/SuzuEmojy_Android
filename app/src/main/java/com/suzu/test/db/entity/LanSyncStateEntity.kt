package com.suzu.test.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lan_sync_state")
data class LanSyncStateEntity(@PrimaryKey val key: String, val value: String)
