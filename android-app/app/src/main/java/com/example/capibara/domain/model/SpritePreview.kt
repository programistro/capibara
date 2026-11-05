package com.example.capibara.domain.model

import androidx.annotation.DrawableRes

/**
 * Имя drawable карточки магазина — по умолчанию то, что вернул бы
 * [PetSprite.of]. Отдельно от `R.drawable`, чтобы проверять превью
 * в JVM-тестах, где `R` — заглушка.
 */
fun previewSpriteName(itemId: Int, selectedIds: Set<Int>, mood: MoodLevel): String {
    val isSelected = itemId in selectedIds
    if (!isSelected) {
        val withItem = selectedIds + itemId
        PetSprite.exactName(withItem, mood)?.let { return it }
        return PetSprite.exactName(setOf(itemId), mood) ?: PetSprite.keyOf(setOf(itemId))
    }
    // Надето «всё» — рисуем предмет без остальных одежд: наряд и так виден
    // на карточках соседей, а здесь нужно узнаваемое превью.
    if (selectedIds.size > 1) {
        return PetSprite.exactName(setOf(itemId), mood) ?: PetSprite.keyOf(setOf(itemId))
    }
    return PetSprite.exactName(selectedIds, mood) ?: PetSprite.keyOf(selectedIds)
}

/** Спрайт карточки магазина: то же решение, что в [previewSpriteName]. */
@DrawableRes
fun previewSprite(itemId: Int, selectedIds: Set<Int>, mood: MoodLevel): Int =
    PetSprite.of(
        itemIds = if (itemId in selectedIds || PetSprite.hasExact(selectedIds + itemId, mood)) {
            selectedIds + itemId
        } else {
            setOf(itemId)
        },
        mood = mood
    )
