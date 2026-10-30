package com.example.capibara.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Сверяет карту спрайтов с реальным содержимым каталога `cells/`.
 *
 * Каталог лежит в репозитории, поэтому тест читает его напрямую: если кто-то
 * добавит или переименует PNG, тест это поймает.
 */
class PetSpriteCoverageTest {

    private val cellsDir = listOf(
        java.io.File("cells"),
        java.io.File("../cells")
    ).firstOrNull { it.isDirectory }

    private val moodFolderToCode = mapOf(
        "веселая" to "happy",
        "грустная" to "cry",
        "нормальная" to "norm",
        "удивленная" to "surprise"
    )

    /** Все файлы каталога как пары «комплект -> эмоция». */
    private fun catalog(): Pair<Set<String>, Map<String, Set<String>>> {
        val dir = assertNotNull("каталог cells не найден", cellsDir).let { cellsDir!! }
        val combos = mutableSetOf<String>()
        val byCombo = mutableMapOf<String, MutableSet<String>>()

        moodFolderToCode.forEach { (folder, code) ->
            val files = java.io.File(dir, folder).listFiles { f -> f.extension == "png" }.orEmpty()
            files.forEach { file ->
                val name = file.nameWithoutExtension
                // Имя = "<комплект>_<эмоция>", эмоция всегда в конце.
                assertTrue(
                    "файл $name не заканчивается на _$code",
                    name.endsWith("_$code")
                )
                val combo = name.removeSuffix("_$code")
                combos.add(combo)
                byCombo.getOrPut(combo) { mutableSetOf() }.add(code)
            }
        }
        return combos to byCombo
    }

    @Test
    fun `каждый файл каталога имеет запись в карте спрайтов`() {
        val (combos, byCombo) = catalog()
        val missing = mutableListOf<String>()

        byCombo.forEach { (combo, moods) ->
            moods.forEach { code ->
                val mood = moodOf(code)
                val items = itemsOf(combo)
                if (!PetSprite.hasExact(items, mood)) {
                    missing.add("$combo ($code)")
                }
            }
        }

        assertTrue("в карте не хватает спрайтов: $missing", missing.isEmpty())
        assertTrue("каталог должен содержать 26 комплектов", combos.size == 26)
    }

    @Test
    fun `все девять предметов имеют спрайт в каждой эмоции`() {
        MoodLevel.entries.forEach { mood ->
            PetSprite.ACCESSORY_IDS.forEach { id ->
                assertTrue(
                    "нет спрайта для предмета $id в эмоции $mood",
                    PetSprite.hasExact(setOf(id), mood)
                )
            }
        }
    }

    @Test
    fun `базовая капибара есть во всех четырёх эмоциях`() {
        listOf(MoodLevel.CRYING, MoodLevel.NORMAL, MoodLevel.HAPPY, MoodLevel.SURPRISE)
            .forEach { mood ->
                assertTrue(
                    "нет базового спрайта для $mood",
                    PetSprite.hasExact(emptySet(), mood)
                )
            }
    }

    @Test
    fun `surprise достижим через MoodLevel from`() {
        val mood = MoodLevel.from(95)
        assertEquals("на пике активности — удивление", MoodLevel.SURPRISE, mood)
        assertTrue(PetSprite.of(emptySet(), mood) != 0)
    }

    @Test
    fun `каждая эмоция достижима из score`() {
        val reached = (0..100 step 5).map { MoodLevel.from(it) }.toSet()
        assertEquals(
            "из очков должны получаться все четыре эмоции",
            setOf(MoodLevel.CRYING, MoodLevel.NORMAL, MoodLevel.DEFAULT, MoodLevel.HAPPY, MoodLevel.SURPRISE),
            reached
        )
    }

    @Test
    fun `комбинация в весёлой эмоции не сваливается в базовый спрайт`() {
        // Цилиндр + пиджак нарисованы вместе — это должна быть точная картинка.
        assertTrue(PetSprite.hasExact(setOf(1, 3), MoodLevel.HAPPY))
    }

    @Test
    fun `фолбэк для ненарисованного сочетания даёт осмысленный спрайт`() {
        // Повязка + бантик + шарф вместе не нарисованы.
        val sprite = PetSprite.of(setOf(5, 8, 4), MoodLevel.HAPPY)
        assertTrue("не должно быть нулевого ресурса", sprite != 0)
        assertTrue(
            "фолбэк не должен совпасть с точным спрайтом отсутствующего набора",
            !PetSprite.hasExact(setOf(5, 8, 4), MoodLevel.HAPPY)
        )
    }

    private fun moodOf(code: String): MoodLevel = when (code) {
        "happy" -> MoodLevel.HAPPY
        "norm" -> MoodLevel.NORMAL
        "cry" -> MoodLevel.CRYING
        "surprise" -> MoodLevel.SURPRISE
        else -> error("неизвестная эмоция $code")
    }

    /** Обратное преобразование ключа комплекта в id предметов магазина. */
    private fun itemsOf(combo: String): Set<Int> {
        if (combo == "norm") return emptySet()
        val keyToId = mapOf(
            "hat_1" to 1, "glasses_1" to 2, "jacket" to 3, "scarf" to 4,
            "bow" to 5, "cap" to 6, "glasses_2" to 7, "headband" to 8, "hat_2" to 9
        )
        // Ключ составной: разбираем жадно, от длинных ключей к коротким.
        // Порядок важен — "glasses_2" должен сработать раньше, чем "2".
        val result = mutableSetOf<Int>()
        var rest = combo
        while (rest.isNotEmpty()) {
            val match = keyToId.keys
                .sortedByDescending { it.length }
                .firstOrNull { rest == it || rest.startsWith("${it}_") }
                ?: error("не удалось разобрать ключ комплекта: $combo (осталось: $rest)")
            result.add(keyToId.getValue(match))
            rest = rest.removePrefix(match).removePrefix("_")
        }
        return result
    }
}
