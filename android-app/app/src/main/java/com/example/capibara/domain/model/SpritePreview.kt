package com.example.capibara.domain.model

import androidx.annotation.DrawableRes

/**
 * Что показывать в карточке магазина.
 *
 * Карточка одежды должна отличаться от соседних, даже если надеты все
 * девять предметов: раньше её превью собиралось из готовой склейки, и при
 * частичном совпадении карточки схлопывались в одну картинку. Теперь одежда
 * рисуется слоем поверх базы, и карточка показывает просто свой предмет.
 */
sealed interface ShopPreview {

    /** Одежда: базовая капибара + этот предмет слоем. */
    data class Clothing(val key: String) : ShopPreview

    /** Фон сцены. */
    data class Background(val itemId: Int) : ShopPreview

    /** Скин целиком. */
    data class Skin(val itemId: Int) : ShopPreview
}

/** Описание карточки: что за товар и на какой слой смотреть. */
fun shopPreview(itemId: Int): ShopPreview = when {
    BackgroundSprite.isBackground(itemId) -> ShopPreview.Background(itemId)
    SkinSprite.isSkin(itemId) -> ShopPreview.Skin(itemId)
    else -> ShopPreview.Clothing(ClothingSprite.keyOf(itemId) ?: "")
}

/** Имя drawable карточки — для тестов на JVM, где `R` — заглушка. */
fun previewSpriteName(itemId: Int, selectedIds: Set<Int>, mood: MoodLevel): String =
    when (val preview = shopPreview(itemId)) {
        is ShopPreview.Clothing -> PetSprite.nameOf(mood)
        is ShopPreview.Background -> BackgroundSprite.nameOf(itemId)
        is ShopPreview.Skin -> SkinSprite.nameOf(itemId) ?: "pet_norm_norm"
    }

/** Изображение-подложка карточки (не включая слой одежды). */
@DrawableRes
fun previewSprite(itemId: Int, selectedIds: Set<Int>, mood: MoodLevel): Int =
    when (val preview = shopPreview(itemId)) {
        is ShopPreview.Clothing -> PetSprite.base(mood)
        is ShopPreview.Background -> BackgroundSprite.drawable(itemId)
        is ShopPreview.Skin -> SkinSprite.drawable(itemId) ?: PetSprite.base(mood)
    }
