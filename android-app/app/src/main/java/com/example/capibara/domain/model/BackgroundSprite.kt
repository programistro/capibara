package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Фоны, которые можно купить в магазине и повесить за капибарой.
 *
 * Храним имена drawable, а не `R.drawable.*`: в JVM-тестах `R` — заглушка
 * и все id равны нулю. Здесь только отображение «id товара -> картинка»,
 * выбор купленного фона живёт в `MainUiState`/`MainViewModel`.
 */
object BackgroundSprite {

    /** id товара фона -> имя drawable без префикса. */
    private val NAMES: Map<Int, String> = mapOf(
        10 to "new_background",   // Лесная поляна
        11 to "new_beach",        // Пляж
        12 to "new_polished"      // Морское побережье
    )

    /** Фон по умолчанию: картинка, с которой начинается приложение. */
    val DEFAULT_NAME: String = NAMES.getValue(DEFAULT_ITEM_ID)

    /** id товара, чей фон показан, когда пользователь ничего не выбрал. */
    const val DEFAULT_ITEM_ID: Int = 10

    val itemIds: Set<Int> = NAMES.keys

    /**
     * id фонов для SQL-запросов в `InventoryDao`. Порядок совпадает с NAMES,
     * тест `BackgroundSpriteTest` сверяет, что список не отстал от карты.
     */
    val idsForSql: String = NAMES.keys.joinToString(", ")

    fun isBackground(itemId: Int): Boolean = itemId in NAMES

    /** Имя картинки фона для [itemId]; для чужого id — фон по умолчанию. */
    fun nameOf(itemId: Int?): String = NAMES[itemId] ?: DEFAULT_NAME

    /** Имя картинки при наборе [itemIds] — берётся любой фон из набора. */
    fun nameFor(itemIds: Collection<Int>): String {
        val id = itemIds.firstOrNull { it in NAMES }
        return nameOf(id)
    }

    /**
     * Показанный фон. null — ни один фон не надет: за капибарой пусто,
     * лесная поляна ничем не надела, и у неё кнопка «Надеть», как у всех.
     */
    fun resolveSelected(itemIds: Collection<Int>): Int? =
        selectedAmong(itemIds)

    /** Найденный среди [itemIds] фон. null — пользователь ничего не выбрал. */
    fun selectedAmong(itemIds: Collection<Int>): Int? =
        itemIds.firstOrNull { it in NAMES }

    /** Куплен ли фон. По умолчанию лесная поляна уже есть у всех. */
    fun isOwned(itemId: Int, ownedIds: Set<Int>): Boolean =
        itemId == DEFAULT_ITEM_ID || itemId in ownedIds

    /** Есть ли у пользователя хоть один купленный фон. */
    fun hasAnyOwned(ownedIds: Set<Int>): Boolean =
        ownedIds.any { it in NAMES }

    @DrawableRes
    fun drawable(itemId: Int?): Int = when (nameOf(itemId)) {
        "new_beach" -> R.drawable.new_beach
        "new_polished" -> R.drawable.new_polished
        else -> R.drawable.new_background
    }
}
