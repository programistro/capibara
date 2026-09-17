package com.example.capibara.domain.repository

import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun observeOwnedIds(): Flow<Set<Int>>
    suspend fun isOwned(itemId: Int): Boolean
    suspend fun markOwned(itemId: Int)
}
