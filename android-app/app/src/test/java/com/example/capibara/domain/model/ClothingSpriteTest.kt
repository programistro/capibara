package com.example.capibara.domain.model

import com.example.capibara.domain.usecase.GetShopItemsUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Стражевая геометрия одежды.
 *
 * Одежда больше не приходит готовыми склейками — Compose рисует её слоем
 * поверх базовой капибары. Если координаты выйдут за холст 640×1000 или
 * потеряется предмет, на экране это будет «пропавшая» вещь.
 */
class ClothingSpriteTest {

    private val catalog = GetShopItemsUseCase()()

    @Test
    fun `у всей одежды из каталога есть ключ и координаты`() {
        catalog.filter { it.isClothing }.forEach { item ->
            val key = ClothingSprite.keyOf(item.id)
            assertNotNull("${item.name} без ключа одежды", key)
            assertNotNull("${item.name} без координат", ClothingSprite.fitOnCanvas(key!!))
            assertEquals(item.id, ClothingSprite.itemIdOf(key))
        }
        assertEquals(9, ClothingSprite.itemIds.size)
        assertEquals((1..9).toList(), ClothingSprite.itemIds.sorted())
    }

    @Test
    fun `одежда не вылезает за холст капибары`() {
        ClothingSprite.LAYER_ORDER.forEach { key ->
            val fit = ClothingSprite.fitOnCanvas(key)!!
            assertTrue("$key: x=${fit.x} < 0", fit.x >= 0f)
            assertTrue("$key: y=${fit.y} < 0", fit.y >= 0f)
            assertTrue("$key: правый край ${fit.x + fit.width} > 640",
                fit.x + fit.width <= ClothingSprite.CANVAS_WIDTH + 0.5f)
            assertTrue("$key: низ ${fit.y + fit.height} > 1000",
                fit.y + fit.height <= ClothingSprite.CANVAS_HEIGHT + 0.5f)
            assertTrue("$key: нулевой размер", fit.width > 0f && fit.height > 0f)
        }
    }

    @Test
    fun `шляпы стоят выше шеи, одежда ниже`() {
        val head = listOf("hat_1", "hat_2", "cap", "headband")
        val neck = ClothingSprite.FIGURE_BOTTOM - ClothingSprite.TEMPLATE_NECK * ClothingSprite.BASE_SCALE
        head.forEach { key ->
            val fit = ClothingSprite.fitOnCanvas(key)!!
            assertTrue("$key должен начинаться выше шеи: ${fit.y}", fit.y < neck)
        }
        listOf("scarf", "jacket", "bow").forEach { key ->
            val fit = ClothingSprite.fitOnCanvas(key)!!
            assertTrue("$key должен начинаться ниже шеи: ${fit.y}", fit.y > neck - 200f)
        }
    }

    @Test
    fun `слои идут снизу вверх в понятном порядке`() {
        val layers = ClothingSprite.layersOf(setOf(1, 2, 3, 5, 7))
        // Шея и тело под очками и шляпой.
        assertTrue(layers.indexOf("jacket") < layers.indexOf("glasses_1"))
        assertTrue(layers.indexOf("glasses_2") < layers.indexOf("hat_1"))
        assertTrue(layers.indexOf("bow") < layers.indexOf("hat_1"))
    }

    @Test
    fun `неизвестный предмет не становится одеждой`() {
        assertNull(ClothingSprite.keyOf(10))
        assertNull(ClothingSprite.keyOf(13))
        assertNull(ClothingSprite.fitOnCanvas("coat"))
        assertTrue(ClothingSprite.layersOf(setOf(10, 13, 1)) == listOf("hat_1"))
    }

    @Test
    fun `любая из 512 комбинаций даёт слои ровно для надетых предметов`() {
        val ids = ClothingSprite.itemIds.toList()
        repeat(1 shl ids.size) { mask ->
            val selected = ids.filterIndexed { i, _ -> mask and (1 shl i) != 0 }.toSet()
            val layers = ClothingSprite.layersOf(selected)
            assertEquals("набор $selected", selected.size, layers.size)
            assertEquals("порядок $selected", ClothingSprite.LAYER_ORDER.filter { it in layers }, layers)
        }
    }

    @Test
    fun `все базовые спрайты и вся одежда попадают в R`() {
        PetSprite.MOODS.forEach { assertTrue("нет спрайта для $it", PetSprite.base(it) > 0) }
        ClothingSprite.LAYER_ORDER.forEach {
            assertTrue("нет ресурса $it", ClothingSprite.drawable(it) > 0)
        }
    }

    @Test
    fun `в cells clothes лежит ровно то, что описано в коде`() {
        val dir = listOf(
            java.io.File("cells/clothes"),
            java.io.File("../cells/clothes")
        ).firstOrNull { it.isDirectory } ?: return
        val files = dir.listFiles()?.filter { it.extension == "png" }?.map { it.nameWithoutExtension }?.toSet() ?: emptySet()
        // coat нарисован, но в магазине его нет: не обещаем пользователю
        // вещь, которую нельзя надеть.
        assertEquals(setOf(*ClothingSprite.LAYER_ORDER.toTypedArray(), "coat"), files)
    }
}
