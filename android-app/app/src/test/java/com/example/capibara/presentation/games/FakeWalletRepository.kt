package com.example.capibara.presentation.games

import com.example.capibara.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** Простая заглушка кошелька для тестов: запоминает начисления. */
class FakeWalletRepository(startCoins: Int = 0) : WalletRepository {

    private val coins = MutableStateFlow(startCoins)
    val added = mutableListOf<Int>()

    override fun observeCoins(): Flow<Int> = coins

    override suspend fun deduct(amount: Int): Boolean {
        if (coins.value < amount) return false
        coins.value -= amount
        return true
    }

    override suspend fun add(amount: Int) {
        added += amount
        coins.value += amount
    }
}
