package com.example.capibara.presentation.shop

import com.example.capibara.domain.model.MoodLevel
import com.example.capibara.domain.model.previewSpriteName
import com.example.capibara.domain.usecase.GetShopItemsUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Страж бага «в магазине одна картинка на всех».
 *
 * Каждая карточка обязана показывать свою картинку — и когда ничего не надето,
 * и когда надеты любые сочетания. Сравниваем имена drawable, а не id: в
 * JVM-тестах `R` — заглушка, и все id равны нулю.
 */
class ShopPreviewTest {

    private val items = GetShopItemsUseCase()()

    private fun previews(selected: Set<Int>, mood: MoodLevel): Map<Int, String> =
        items.associate { it.id to previewSpriteName(it.id, selected, mood) }

    @Test
    fun `пустая витрина показывает девять разных картинок`() {
        val images = previews(emptySet(), MoodLevel.NORMAL)
        assertEquals("все 9 карточек должны быть разными: $images", 9, images.values.toSet().size)
    }

    @Test
    fun `каталог магазина ровно девять предметов`() {
        assertEquals((1..9).toList(), items.map { it.id })
    }

    @Test
    fun `карточки не дублируются при любом надетом наборе`() {
        val moods = listOf(MoodLevel.CRYING, MoodLevel.NORMAL, MoodLevel.HAPPY, MoodLevel.SURPRISE)
        val failures = mutableListOf<String>()
        moods.forEach { mood ->
            subsets(items.map { it.id }.toSet()).forEach { selected ->
                val distinct = previews(selected, mood).values.toSet().size
                if (distinct != 9) failures.add("$mood, надето $selected: разных $distinct")
            }
        }
        assertEquals("карточки магазина дублируются: $failures", emptyList<String>(), failures)
    }

    private fun subsets(ids: Set<Int>): List<Set<Int>> {
        val list = ids.toList()
        return (0 until (1 shl list.size)).map { mask ->
            list.filterIndexed { index, _ -> mask and (1 shl index) != 0 }.toSet()
        }
    }
}
