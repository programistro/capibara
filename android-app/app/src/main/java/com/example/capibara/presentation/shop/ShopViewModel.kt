package com.example.capibara.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capibara.domain.model.BackgroundSprite
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.model.SkinSprite
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

        viewModelScope.launch {
            inventoryRepository.observeSelectedIds().collect { ids ->
                // Фон держим в том же наборе: иначе состояние фона не реактивно
                // и «Снять/Надеть» застревает. В спрайты он не попадает.
                _uiState.update { it.copy(selectedIds = ids) }
            }
        }
    }
    fun onBuyClick(item: ShopItem) {
        viewModelScope.launch {
            if (item.id == BackgroundSprite.DEFAULT_ITEM_ID) {
                // Лесная поляна не продаётся: она есть у всех с первого
                // запуска. Действие кнопки — «Надеть/Снять», как у купленного.
                toggle(item)
                return@launch
            }
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

    /** Надеть или снять предмет — комбинации разрешены. */
    fun toggleItem(item: ShopItem) {
        viewModelScope.launch { toggle(item) }
    }

    /**
     * Надеть/снять товар.
     *
     * - одежда — независимое переключение каждого предмета;
     * - фон — ровно один, надевание снимает другие фоны;
     * - скин — ровно один, а при его надевании снимается одежда:
     *   нарисована она только на обычной капибаре.
     */
    private suspend fun toggle(item: ShopItem) {
        val state = _uiState.value
        // Снятие проверяем первым: лесная поляна считается надетой, даже
        // если её нет в таблице выбора.
        if (isEquipped(item, state)) {
            inventoryRepository.unselect(item.id)
            return
        }
        if (!state.owns(item)) return
        if (item.isBackground) {
            BackgroundSprite.itemIds
                .filter { it in state.selectedIds }
                .forEach { inventoryRepository.unselect(it) }
        }
        if (item.isSkin) {
            SkinSprite.itemIds
                .filter { it in state.selectedIds }
                .forEach { inventoryRepository.unselect(it) }
            state.selectedIds
                .filter { it in state.accessoryIds }
                .forEach { inventoryRepository.unselect(it) }
        }
        inventoryRepository.setSelected(item.id, true)
    }

    private fun isEquipped(item: ShopItem, state: ShopUiState): Boolean = when {
        item.isBackground -> state.activeBackgroundId == item.id
        item.isSkin -> state.activeSkinId == item.id
        else -> item.id in state.selectedIds
    }

    fun onClearError(){
        _uiState.update { it.copy(error = null) }
    }
}
