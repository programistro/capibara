package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Query("SELECT coins FROM wallet WHERE id = 0")
    fun observeCoins(): Flow<Int?>

    @Query("SELECT coins FROM wallet WHERE id = 0")
    suspend fun getCoins(): Int?

    @Upsert
    suspend fun setWallet(wallet: WalletEntity)

    @Query("UPDATE wallet SET coins = coins - :amount WHERE id = 0 AND coins >= :amount")
    suspend fun tryDeduct(amount: Int): Int

    @Query("UPDATE wallet SET coins = coins + :amount WHERE id = 0")
    suspend fun addCoins(amount: Int)
}
