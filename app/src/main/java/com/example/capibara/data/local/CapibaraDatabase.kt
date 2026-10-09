package com.example.capibara.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [WalletEntity::class, OwnedItemEntity::class, MoodEntity::class],
    version = 3,
    exportSchema = true
)
abstract class CapibaraDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun moodDao(): MoodDao
}
