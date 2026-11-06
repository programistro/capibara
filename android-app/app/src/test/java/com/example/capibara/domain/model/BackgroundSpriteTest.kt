package com.example.capibara.domain.model

import com.example.capibara.domain.usecase.GetShopItemsUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Проверяет, что у каждого фона из каталога есть картинка, а id фонов
 * не пересекаются с одеждой — иначе фон стал бы шляпой на капибаре.
 */
class BackgroundSpriteTest {

    private val items = GetShopItemsUseCase()()
    private val backgrounds = items.filter { it.isBackground }

    @Test
    fun `у каждого фона в каталоге есть картинка`() {
        assertEquals("фоны в каталоге", setOf(10, 11, 12), backgrounds.map { it.id }.toSet())
        backgrounds.forEach {
            assertTrue("фон ${it.name} не найден", BackgroundSprite.isBackground(it.id))
            assertNotNull(BackgroundSprite.nameOf(it.id))
        }
    }

    @Test
    fun `картинки фонов разные`() {
        val names = backgrounds.map { BackgroundSprite.nameOf(it.id) }
        assertEquals(names.size, names.toSet().size)
        assertEquals(
            listOf("new_background", "new_beach", "new_polished"),
            names
        )
    }

    @Test
    fun `id фонов не попадают в спрайты одежды`() {
        backgrounds.forEach {
            assertFalse("${it.id} должен быть фоном, а не одеждой",
                it.id in PetSprite.ACCESSORY_IDS)
        }
    }

    @Test
    fun `ничего не выбрано — фон по умолчанию`() {
        assertNull(BackgroundSprite.selectedAmong(setOf(1, 3)))
        assertEquals("new_background", BackgroundSprite.nameFor(setOf(1, 3)))
    }

    @Test
    fun `лесная поляна куплена по умолчанию, остальные фоны нет`() {
        assertTrue(BackgroundSprite.isOwned(10, emptySet()))
        assertFalse(BackgroundSprite.isOwned(11, emptySet()))
        assertTrue(BackgroundSprite.isOwned(11, setOf(11)))
    }

    @Test
    fun `ничего не надето — фона на сцене нет`() {
        // Пейзаж по умолчанию — просто подложка сцены: надеть тёплую
        // лесную поляну можно и её карточке, пока она не выбрана. Она
        // считается надетой только после явного «Надеть».
        assertNull(BackgroundSprite.resolveSelected(emptySet()))
        assertNull(BackgroundSprite.resolveSelected(setOf(1, 3)))
        assertEquals(10, BackgroundSprite.resolveSelected(setOf(10)))
        assertEquals(11, BackgroundSprite.resolveSelected(setOf(1, 11)))
    }

    @Test
    fun `sql-список фонов совпадает с картой`() {
        assertEquals("10, 11, 12", BackgroundSprite.idsForSql)
    }

    @Test
    fun `выбранный фон находится рядом с надетой одеждой`() {
        val selected = setOf(1, 11, 3)
        assertEquals(11, BackgroundSprite.selectedAmong(selected))
        assertEquals("new_beach", BackgroundSprite.nameFor(selected))
    }
}
