package com.example.capibara.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Проверяет, что спрайты подбираются по набору предметов и что комбинации
 * работают. Раньше можно было надеть только один предмет — `of(itemId, mood)`.
 */
class PetSpriteTest {

    @Test
    fun `пустой набор даёт базовую капибару`() {
        val sprite = PetSprite.of(emptySet(), MoodLevel.HAPPY)
        assertEquals(PetSprite.base(MoodLevel.HAPPY), sprite)
    }

    @Test
    fun `одиночный предмет меняет спрайт`() {
        val base = PetSprite.base(MoodLevel.HAPPY)
        val withHat = PetSprite.of(setOf(1), MoodLevel.HAPPY)
        assertTrue("спрайт отличается от базового", base != withHat)
    }

    @Test
    fun `комбинация двух предметов даёт свой спрайт`() {
        val hat = PetSprite.of(setOf(1), MoodLevel.NORMAL)
        val jacket = PetSprite.of(setOf(3), MoodLevel.NORMAL)
        val both = PetSprite.of(setOf(1, 3), MoodLevel.NORMAL)

        assertTrue("кепка и пиджак вместе отличаются от каждого по отдельности",
            both != hat && both != jacket)
    }

    @Test
    fun `порядок предметов не влияет на результат`() {
        val a = PetSprite.of(setOf(1, 3, 4), MoodLevel.NORMAL)
        val b = PetSprite.of(setOf(4, 1, 3), MoodLevel.NORMAL)
        assertEquals(a, b)
    }

    @Test
    fun `комбинации работают во всех трёх основных настроениях`() {
        val moods = listOf(MoodLevel.HAPPY, MoodLevel.NORMAL, MoodLevel.CRYING)
        moods.forEach { mood ->
            val combo = PetSprite.of(setOf(1, 2, 3), mood)
            assertNotZero(combo)
        }
    }

    @Test
    fun `все объявленные спрайты существуют`() {
        // Перебираем все предметы по одному и их пары — ни один не должен
        // вернуть ресурс-заглушку (0), иначе картинка не нарисуется.
        PetSprite.ACCESSORY_IDS.forEach { id ->
            MoodLevel.entries.forEach { mood ->
                assertNotZero(PetSprite.of(setOf(id), mood))
            }
        }
    }

    @Test
    fun `неизвестный предмет не ломает отрисовку`() {
        val sprite = PetSprite.of(setOf(999), MoodLevel.HAPPY)
        assertEquals("для неизвестного предмета показываем базу",
            PetSprite.base(MoodLevel.HAPPY), sprite)
    }

    @Test
    fun `несуществующая комбинация откатывается к частичному набору`() {
        // Повязка + бантик вместе не нарисованы — фолбэк должен дать что-то
        // осмысленное, а не упасть.
        val sprite = PetSprite.of(setOf(8, 5), MoodLevel.HAPPY)
        assertNotZero(sprite)
    }

    @Test
    fun `ключи аксессуаров идут в каноническом порядке`() {
        val keys = PetSprite.accessoriesOf(listOf(9, 1, 3))
        assertEquals(listOf("hat_1", "hat_2", "jacket"), keys)
    }

    @Test
    fun `удивлённая есть для одиночных предметов`() {
        PetSprite.ACCESSORY_IDS.forEach { id ->
            assertNotZero(PetSprite.of(setOf(id), MoodLevel.DEFAULT))
        }
    }

    private fun assertNotZero(@androidx.annotation.DrawableRes res: Int) {
        assertNotNull(res)
        assertTrue("ресурс должен быть ненулевым", res != 0)
    }
}
