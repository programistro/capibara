package com.example.capibara.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.repository.InventoryRepository
import com.example.capibara.domain.usecase.BuyItemUseCase
import com.example.capibara.domain.usecase.BuyResult
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getShopItems: GetShopItemsUseCase,
    private val buyItem: BuyItemUseCase,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState(items = getShopItems()))
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            inventoryRepository.observeOwnedIds().collect { ids ->
                _uiState.update { it.copy(purchasedIds = ids) }
            }
        }
    }
    fun onBuyClick(item: ShopItem) {
        viewModelScope.launch {
            val result = buyItem.invoke(item)
            when (result) {
                BuyResult.Success -> {
                    // purchasedIds приедет сам через Flow из базы
                }
                BuyResult.NotEnoughCoins -> _uiState.update { it.copy(error = "Не хватает монет") }
                BuyResult.AlreadyOwned -> _uiState.update { it.copy(error = "Уже куплено") }
            }
        }
    }

    fun onClearError(){
        _uiState.update { it.copy(error = null) }
    }
}
