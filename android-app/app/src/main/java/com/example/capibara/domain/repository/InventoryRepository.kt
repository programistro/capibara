package com.example.capibara.domain.repository

import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun observeOwnedIds(): Flow<Set<Int>>
    suspend fun isOwned(itemId: Int): Boolean
    suspend fun markOwned(itemId: Int)
    /** Все надетые предметы. Пустой набор — капибара без аксессуаров. */
    fun observeSelectedIds(): Flow<Set<Int>>

    /** Надеть/снять предмет, не трогая остальные. */
    suspend fun toggle(itemId: Int)

    /**
     * Надеть предмет, сняв все остальные одежды. Фоны — исключение: одежда
     * остаётся надетой, снимаются только другие фоны (см. реализацию).
     */
    suspend fun select(itemId: Int)

    /** Надеть ([selected] = true) или снять предмет явно, не переключая. */
    suspend fun setSelected(itemId: Int, selected: Boolean)

    /** Снять предмет, не меняя остальные. */
    suspend fun unselect(itemId: Int)
}
