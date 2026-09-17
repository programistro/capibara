package com.example.capibara.data.repository

import com.example.capibara.data.local.InventoryDao
import com.example.capibara.data.local.OwnedItemEntity
import com.example.capibara.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dao: InventoryDao
) : InventoryRepository {

    override fun observeOwnedIds(): Flow<Set<Int>> =
        dao.observeOwnedIds().map { it.toSet() }

    override suspend fun isOwned(itemId: Int): Boolean =
        dao.exists(itemId)

    override suspend fun markOwned(itemId: Int) {
        dao.insert(OwnedItemEntity(itemId = itemId))
    }
}
