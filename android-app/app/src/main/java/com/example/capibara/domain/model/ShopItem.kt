package com.example.capibara.domain.model

/**
 * Товар магазина.
 *
 * [slot] — тоже идентификатор: одежда, фоны и бесплатные скины живут в
 * таблице `owned_items`. На капибару можно надеть несколько предметов
 * одежды и только один фон; скин же целиком заменяет внешность персонажа
 * и комбинируется с фоном. По слоту их и различаем.
 */
data class ShopItem(
    val id: Int,
    val name: String,
    val slot: String,
    val price: Int
) {
    /** Фон сцены, а не одежда капибары. */
    val isBackground: Boolean get() = slot == ShopSlot.BACKGROUND

    /** Скин — целиком другая капибара; одежда на нём не надевается. */
    val isSkin: Boolean get() = slot == ShopSlot.SKIN

    /** Товар, у которого не может быть нескольких надетых одновременно. */
    val isSingleton: Boolean get() = isBackground || isSkin
}

object ShopSlot {
    const val HEAD = "Голова"
    const val FACE = "Лицо"
    const val BODY = "Тело"
    const val ACCESSORY = "Аксессуар"
    const val BACKGROUND = "Фон"
    const val SKIN = "Скин"
}
