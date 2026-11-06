package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.model.ShopSlot
import javax.inject.Inject

class GetShopItemsUseCase @Inject constructor() {
    operator fun invoke(): List<ShopItem> = listOf(
        ShopItem(1, "Цилиндр", ShopSlot.HEAD, 100),
        ShopItem(2, "Очкарик", ShopSlot.FACE, 150),
        ShopItem(3, "Пиджак", ShopSlot.BODY, 80),
        ShopItem(4, "Шарф", ShopSlot.ACCESSORY, 120),
        ShopItem(5, "Бантик", ShopSlot.ACCESSORY, 150),
        ShopItem(6, "Кепка", ShopSlot.HEAD, 90),
        ShopItem(7, "Очкарик 2", ShopSlot.FACE, 200),
        ShopItem(8, "Повязка", ShopSlot.HEAD, 250),
        ShopItem(9, "Ковбойская шляпа", ShopSlot.HEAD, 180),
        // Фоны сцены. Ключи id не должны пересечься с [PetSprite]: 10–12
        // в карту одежды не входят, поэтому на капибару они не влияют.
        ShopItem(10, "Лесная поляна", ShopSlot.BACKGROUND, 300),
        ShopItem(11, "Пляж", ShopSlot.BACKGROUND, 400),
        ShopItem(12, "Морское побережье", ShopSlot.BACKGROUND, 500),
        // Бесплатные скины: price = 0 и ownedIds считает их купленными,
        // поэтому у карточки сразу «Надеть».
        ShopItem(13, "Апельсиновая капибара", ShopSlot.SKIN, 0),
        ShopItem(14, "Апельсиновая капибара 2", ShopSlot.SKIN, 0),
        ShopItem(15, "Апельсиновая капибара 3", ShopSlot.SKIN, 0)
    )
}
