package com.example.capibara.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WalletEntity::class, OwnedItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CapibaraDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
    abstract fun inventoryDao(): InventoryDao
}
