package com.example.capibara.data.repository

import com.example.capibara.data.local.WalletDao
import com.example.capibara.data.local.WalletEntity
import com.example.capibara.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val dao: WalletDao
) : WalletRepository {

    override fun observeCoins(): Flow<Int> =
        dao.observeCoins().map { it ?: START_COINS }

    override suspend fun deduct(amount: Int): Boolean {
        ensureRow()
        return dao.tryDeduct(amount) > 0
    }

    override suspend fun add(amount: Int) {
        ensureRow()
        dao.addCoins(amount)
    }

    private suspend fun ensureRow() {
        if (dao.getCoins() == null) {
            dao.setWallet(WalletEntity(coins = START_COINS))
        }
    }

    companion object {
        const val START_COINS = 500
    }
}
