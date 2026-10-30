package com.example.capibara.presentation.shop

import com.example.capibara.domain.model.ShopItem

data class ShopUiState(
    val items: List<ShopItem> = emptyList(),
    val purchasedIds: Set<Int> = emptySet(),
    val error: String? = null,
    /** Все надетые предметы — можно комбинировать. */
    val selectedIds: Set<Int> = emptySet()
)
