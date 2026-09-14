package com.example.capibara.presentation.shop

import androidx.lifecycle.ViewModel
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    getShopItems: GetShopItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState(items = getShopItems()))
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    fun onBuyClick(itemId: Int) {
        _uiState.update { it.copy(purchasedIds = it.purchasedIds + itemId) }
    }
}
