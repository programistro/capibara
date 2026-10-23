package com.example.capibara.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WalletEntity::class,
        OwnedItemEntity::class,
        MoodEntity::class,
        NewsArticleEntity::class
    ],
    version = 4,
    exportSchema = true
)
abstract class CapibaraDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun moodDao(): MoodDao
    abstract fun newsDao(): NewsDao
}
