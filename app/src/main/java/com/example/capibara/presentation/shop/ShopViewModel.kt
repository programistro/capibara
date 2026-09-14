package com.example.capibara.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShopViewModel(getShopItems: GetShopItemsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState(items = getShopItems()))
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    fun onBuyClick(itemId: Int) {
        _uiState.update { it.copy(purchasedIds = it.purchasedIds + itemId) }
    }
}

class ShopViewModelFactory(
    private val getShopItemsUseCase: GetShopItemsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(getShopItemsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
