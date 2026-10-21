package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.repository.InventoryRepository
import com.example.capibara.domain.repository.WalletRepository
import javax.inject.Inject

sealed interface BuyResult {
    data object Success : BuyResult
    data object NotEnoughCoins : BuyResult
    data object AlreadyOwned : BuyResult
}

class BuyItemUseCase @Inject constructor(
    private val wallet: WalletRepository,
    private val inventory: InventoryRepository
) {
    suspend operator fun invoke(item: ShopItem): BuyResult {
        if (inventory.isOwned(item.id)) return BuyResult.AlreadyOwned
        if (!wallet.deduct(item.price)) return BuyResult.NotEnoughCoins
        inventory.markOwned(item.id)
        return BuyResult.Success
    }
}
