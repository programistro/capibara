package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT itemId FROM owned_items")
    fun observeOwnedIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM owned_items WHERE itemId = :itemId)")
    suspend fun exists(itemId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: OwnedItemEntity)
}
