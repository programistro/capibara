package com.example.capibara.presentation.shop

import com.example.capibara.domain.model.BackgroundSprite
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.model.SkinSprite
import com.example.capibara.domain.usecase.GetShopItemsUseCase

data class ShopUiState(
    val items: List<ShopItem> = GetShopItemsUseCase()(),
    /** id купленных товаров; лесная поляна куплена по умолчанию. */
    val purchasedIds: Set<Int> = emptySet(),
    val error: String? = null,
    /**
     * Все надетые товары: одежда (комбинируется), выбранный фон и скин.
     * Спрайты считаются только по одежде, фон и скин — отдельно.
     */
    val selectedIds: Set<Int> = emptySet()
) {
    /** Только одежда: фоны и скины в спрайтовый набор не попадают. */
    val accessoryIds: Set<Int>
        get() = items.filterNot { it.isBackground || it.isSkin }.map { it.id }.toSet()

    /**
     * Куплен ли товар. Бесплатные скины не хранятся в owned_items: они
     * выдаются кодом и «Купить» у них нет.
     */
    fun owns(item: ShopItem): Boolean = when {
        item.isSkin -> SkinSprite.isOwned(item.id)
        else -> item.id in purchasedIds
    }

    /** Надетые одежды без фонов и скинов — для превью наряда. */
    val accessorySelectedIds: Set<Int>
        get() = selectedIds.filter { it in accessoryIds }.toSet()

    /** Выбранный скин. null — обычная капибара. */
    val selectedSkinId: Int?
        get() = SkinSprite.selectedAmong(selectedIds)

    /**
     * Фон, надетый явно. null — пользователь его снял (или не надевал).
     * Карточка поляны рисует его как «надето», в таблицу выбора он при
     * этом не пишется: другого фона нет, а «Снять» после «Пляжа» нужно
     * и для него.
     */
    val activeBackgroundId: Int?
        get() = BackgroundSprite.resolveSelected(selectedIds)

    /** Надетый скин; одновременно надевается только один. */
    val activeSkinId: Int?
        get() = SkinSprite.selectedAmong(selectedIds)
}
