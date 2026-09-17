package com.example.capibara.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owned_items")
data class OwnedItemEntity(
    @PrimaryKey val itemId: Int,
    val purchasedAt: Long = System.currentTimeMillis()
)
