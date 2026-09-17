package com.example.capibara.domain.repository

import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun observeCoins(): Flow<Int>
    suspend fun deduct(amount: Int): Boolean
    suspend fun add(amount: Int)
}
