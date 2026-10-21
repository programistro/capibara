package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT itemId FROM owned_items")
    fun observeOwnedIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM owned_items WHERE itemId = :itemId)")
    suspend fun exists(itemId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: OwnedItemEntity)

    @Transaction
    suspend fun select(itemId: Int) {
        clearSelection()
        markSelected(itemId)
    }

    @Query("UPDATE owned_items SET selectedItem = 0")
    suspend fun clearSelection()

    @Query("UPDATE owned_items SET selectedItem = 1 WHERE itemId = :itemId")
    suspend fun markSelected(itemId: Int)

    @Query("SELECT itemId FROM owned_items WHERE selectedItem = 1 LIMIT 1")
    fun observeSelectedId(): Flow<Int?>
}
