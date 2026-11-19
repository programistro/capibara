package com.example.capibara.presentation.shop

import com.example.capibara.domain.model.BackgroundSprite
import com.example.capibara.domain.model.ClothingSprite
import com.example.capibara.domain.model.MoodLevel
import com.example.capibara.domain.model.ShopPreview
import com.example.capibara.domain.model.previewSpriteName
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import com.example.capibara.domain.model.shopPreview
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Страж бага «в магазине одна картинка на всех».
 *
 * Каждая карточка обязана показывать свою картинку — и когда ничего не надето,
 * и когда надеты любые сочетания. Сравниваем имена drawable, а не id: в
 * JVM-тестах `R` — заглушка, и все id равны нулю.
 */
class ShopPreviewTest {

    private val catalog = GetShopItemsUseCase()()

    /** Карточки одежды; фоны и скины проверяются отдельными тестами. */
    private val items = catalog.filter { it.isClothing }

    @Test
    fun `каждая одежда рисуется своим слоем`() {
        val keys = items.map { shopPreview(it.id) }
            .filterIsInstance<ShopPreview.Clothing>()
            .map { it.key }
        assertEquals("все 9 карточек должны иметь свой слой: $keys", 9, keys.toSet().size)
        assertEquals(items.map { ClothingSprite.keyOf(it.id) }, keys)
    }

    @Test
    fun `каталог магазина ровно девять предметов и три фона`() {
        assertEquals((1..9).toList(), items.map { it.id })
        assertEquals((10..12).toList(), catalog.filter { it.isBackground }.map { it.id })
        assertEquals((13..15).toList(), catalog.filter { it.isSkin }.map { it.id })
    }

    @Test
    fun `карточка фона показывает сам фон, а не капибару`() {
        val backgrounds = catalog.filter { it.isBackground }
        backgrounds.forEach { item ->
            assertEquals(
                BackgroundSprite.nameOf(item.id),
                previewSpriteName(item.id, emptySet(), MoodLevel.NORMAL)
            )
        }
    }

    @Test
    fun `у надетого фона кнопка снять, у остальных надеть`() {
        val beach = catalog.first { it.id == 11 }
        val forest = catalog.first { it.id == 10 }

        val withBeach = ShopUiState(
            purchasedIds = setOf(11),
            selectedIds = setOf(beach.id)
        )
        assertEquals(11, withBeach.activeBackgroundId)
        assertTrue("пляж куплен", withBeach.owns(beach))
        assertTrue("поляна куплена всегда", withBeach.owns(forest))

        val afterRemove = ShopUiState(
            purchasedIds = setOf(11),
            selectedIds = emptySet()
        )
        // Пляж снят — на сцене пусто, у всех фонов кнопка «Надеть».
        // Это важно для поляны: она не должна вечно показывать «Снять».
        assertNull(afterRemove.activeBackgroundId)
        assertEquals(11, afterRemove.purchasedIds.single())

        val forestOn = ShopUiState(selectedIds = setOf(10))
        assertEquals(10, forestOn.activeBackgroundId)
    }

    @Test
    fun `id фона не попадает в набор одежды`() {
        val state = ShopUiState(selectedIds = setOf(1, 3))
        assertEquals(setOf(1, 3), state.selectedIds)
        assertFalse(10 in state.accessoryIds)
        assertTrue(1 in state.accessoryIds)
    }

    @Test
    fun `бесплатные скины куплены по умолчанию и не попадают в одежду`() {
        val state = ShopUiState()
        val skins = catalog.filter { it.isSkin }
        skins.forEach {
            assertTrue("${it.name} должен быть куплен", state.owns(it))
            assertFalse("${it.name} не должен стать одеждой", it.id in state.accessoryIds)
        }
    }

    @Test
    fun `надет ровно один скин и на нём нет одежды`() {
        // Так выглядит состояние после «Надеть» второго скина: надевая скин,
        // ShopViewModel снимает остальные скины и всю одежду.
        val state = ShopUiState(selectedIds = setOf(14))
        assertEquals(14, state.activeSkinId)
        assertTrue(state.accessorySelectedIds.isEmpty())
        assertNull(state.activeBackgroundId)
    }

    @Test
    fun `карточка скина показывает своего апельсина`() {
        catalog.filter { it.isSkin }.forEach {
            val name = previewSpriteName(it.id, emptySet(), MoodLevel.NORMAL)
            assertTrue("${it.name}: $name", name == "capibara_orange" ||
                name == "capibara_orange_2" || name == "capibara_orange_3")
        }
        val names = catalog.filter { it.isSkin }
            .map { previewSpriteName(it.id, emptySet(), MoodLevel.NORMAL) }
        assertEquals(3, names.toSet().size)
    }
}
