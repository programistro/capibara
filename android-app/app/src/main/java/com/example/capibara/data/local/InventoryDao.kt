package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.capibara.domain.model.BackgroundSprite
import kotlinx.coroutines.flow.Flow

/** id фонов в SQL, синхронизирован с BackgroundSprite.NAMES. */
internal val BACKGROUND_IDS: String = BackgroundSprite.idsForSql

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

    /** Снимает все фоны и ставит [itemId]. Одежду не трогает. */
    @Transaction
    suspend fun selectBackground(itemId: Int) {
        clearBackgrounds(BACKGROUND_IDS)
        markSelected(itemId)
    }

    /** Снять фон: строка может быть выбрана без оформления «куплено». */
    @Query("UPDATE owned_items SET selectedItem = 0 WHERE itemId = :itemId")
    suspend fun unselect(itemId: Int): Int

    @Query("UPDATE owned_items SET selectedItem = 0 WHERE itemId IN (:backgroundIds)")
    suspend fun clearBackgrounds(backgroundIds: String)

    @Query("SELECT itemId FROM owned_items WHERE selectedItem = 1")
    fun observeSelectedIds(): Flow<List<Int>>

    /**
     * Ставит выбранность явно, а не переключает.
     *
     * Toggle на строке без selectedItem молча ничего не делает: лончер
     * показывает «Надеть», а повторный тап снова шлёт «надеть», и фон
     * никогда не выбирается. Явное значение устойчиво к устаревшему UI.
     */
    @Query("UPDATE owned_items SET selectedItem = :selected WHERE itemId = :itemId")
    suspend fun setSelected(itemId: Int, selected: Boolean): Int

    /** Переключает выбранность одного предмета, не трогая остальные. */
    @Query("UPDATE owned_items SET selectedItem = CASE WHEN selectedItem = 1 THEN 0 ELSE 1 END WHERE itemId = :itemId")
    suspend fun toggleSelected(itemId: Int): Int

    /** Оставляет ровно один предмет выбранным — используется старым экраном. */
    @Query("SELECT itemId FROM owned_items WHERE selectedItem = 1 LIMIT 1")
    fun observeSelectedId(): Flow<Int?>
}
