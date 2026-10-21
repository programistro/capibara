package com.example.capibara.domain.model

data class ShopItem(
    val id: Int,
    val name: String,
    val slot: String,
    val price: Int
)

object ShopSlot {
    const val HEAD = "Голова"
    const val FACE = "Лицо"
    const val BODY = "Тело"
    const val ACCESSORY = "Аксессуар"
}
