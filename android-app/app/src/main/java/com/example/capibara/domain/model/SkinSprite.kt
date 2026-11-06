package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Бесплатные скины: капибара целиком в другом окрасе.
 *
 * Скин не комбинируется с одеждой — одежда нарисована только на обычной
 * капибаре. Зато скины сочетаются с любым фоном сцены.
 *
 * Как и у фонов, храним имена drawable: в JVM-тестах `R` — заглушка.
 */
object SkinSprite {

    /** id товара -> имя drawable и отображаемое название. */
    private val SKINS: Map<Int, Pair<String, String>> = linkedMapOf(
        13 to ("capibara_orange" to "Апельсиновая капибара"),
        14 to ("capibara_orange_2" to "Апельсиновая капибара 2"),
        15 to ("capibara_orange_3" to "Апельсиновая капибара 3")
    )

    val itemIds: Set<Int> = SKINS.keys

    fun isSkin(itemId: Int): Boolean = itemId in SKINS

    fun nameOf(itemId: Int?): String? = SKINS[itemId]?.first

    /** Найденный среди [itemIds] скин. null — обычная капибара. */
    fun selectedAmong(itemIds: Collection<Int>): Int? =
        itemIds.firstOrNull { it in SKINS }

    /** Скины бесплатные и выдаются сразу — «Купить» у них нет. */
    fun isOwned(itemId: Int): Boolean = itemId in SKINS

    @DrawableRes
    fun drawable(itemId: Int?): Int? = when (nameOf(itemId)) {
        "capibara_orange" -> R.drawable.capibara_orange
        "capibara_orange_2" -> R.drawable.capibara_orange_2
        "capibara_orange_3" -> R.drawable.capibara_orange_3
        else -> null
    }
}
