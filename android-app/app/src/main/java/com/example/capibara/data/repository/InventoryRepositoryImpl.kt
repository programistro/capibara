package com.example.capibara.data.repository

import com.example.capibara.data.local.InventoryDao
import com.example.capibara.data.local.OwnedItemEntity
import com.example.capibara.domain.model.BackgroundSprite
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

    override fun observeSelectedIds(): Flow<Set<Int>> =
        dao.observeSelectedIds().map { it.toSet() }

    override suspend fun toggle(itemId: Int) {
        dao.toggleSelected(itemId)
    }

    override suspend fun select(itemId: Int) {
        // Старая семантика select() снимает всю одежду. Для фонов это
        // недопустимо: наряд должен остаться, снимаются только фоны.
        if (BackgroundSprite.isBackground(itemId)) dao.selectBackground(itemId)
        else dao.select(itemId)
    }

    override suspend fun setSelected(itemId: Int, selected: Boolean) {
        // persistMissing: у лесной поляны обычно ещё нет строки в owned_items
        // (она «куплена» кодом), и UPDATE по ней ничего не менял. INSERT
        // IGNORE допишет строку только если её действительно нет.
        dao.insert(OwnedItemEntity(itemId = itemId, selectedItem = selected))
        dao.setSelected(itemId, selected)
    }

    override suspend fun unselect(itemId: Int) {
        dao.unselect(itemId)
    }
}
