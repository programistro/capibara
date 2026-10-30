package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Спрайты капибары: «комплект одежды × эмоция».
 *
 * Имена файлов в `res/drawable` имеют вид `pet_<комплект>_<эмоция>`, что
 * напрямую отражает содержимое каталога `cells/`:
 *
 * ```
 * cells/веселая/norm_happy.png      -> pet_norm_happy
 * cells/грустная/cap_bow_cry.png    -> pet_cap_bow_cry
 * cells/нормальная/hat_1_glasses_2_jacket_norm.png
 *                                   -> pet_hat_1_glasses_2_jacket_norm
 * ```
 *
 * Комплект — это предметы через `_`. `norm` означает «одежды нет».
 * Порядок предметов в имени фиксированный, см. [CANONICAL_ORDER].
 *
 * Эмоции: `happy`, `norm`, `cry`, `surprise`.
 */
object PetSprite {

    /**
     * Спрайт для набора предметов [itemIds] в эмоции [mood].
     *
     * Если точной картинки нет (не все сочетания нарисованы), подбирается
     * ближайшая: сначала полный комплект, затем наборы без одного предмета
     * и так далее, в конце — капибара без одежды.
     */
    @DrawableRes
    fun of(itemIds: Collection<Int>, mood: MoodLevel): Int {
        val keys = accessoriesOf(itemIds)
        return lookup(keys, mood) ?: fallback(keys, mood)
    }

    /** Спрайт одного предмета в эмоции [mood]. */
    @DrawableRes
    fun of(itemId: Int?, mood: MoodLevel): Int = of(listOfNotNull(itemId), mood)

    /** Капибара без одежды в эмоции [mood]. */
    @DrawableRes
    fun base(mood: MoodLevel): Int = lookup(emptyList(), mood) ?: lookup(emptyList(), MoodLevel.NORMAL)!!

    /**
     * Ключи предметов в каноническом порядке.
     *
     * Порядок важен: имена файлов собирались именно так
     * (`cap_glasses_1_jacket`, а не `glasses_1_cap_jacket`).
     */
    fun accessoriesOf(itemIds: Collection<Int>): List<String> {
        val keys = itemIds.mapNotNull { ID_TO_KEY[it] }.toSet()
        return CANONICAL_ORDER.filter { it in keys }
    }

    /** Есть ли точная картинка ровно для этого набора предметов. */
    fun hasExact(itemIds: Collection<Int>, mood: MoodLevel): Boolean =
        lookup(accessoriesOf(itemIds), mood) != null

    // ---------- Внутреннее ----------

    /**
     * Пробует всё более короткие наборы, отбрасывая по одному предмету
     * с конца канонического порядка. Последний вариант — без одежды.
     *
     * Отбрасываем именно с конца, потому что головные предметы (шляпа, кепка,
     * повязка) заметнее и чаще есть в нарисованных комбинациях, а одежда
     * (пиджак, шарф) — реже.
     */
    @DrawableRes
    private fun fallback(keys: List<String>, mood: MoodLevel): Int {
        for (size in keys.size - 1 downTo 0) {
            lookup(keys.take(size), mood)?.let { return it }
        }
        // Совсем нет картинки для этой эмоции — берём нормальное настроение.
        for (size in keys.size downTo 0) {
            lookup(keys.take(size), MoodLevel.NORMAL)?.let { return it }
        }
        return R.drawable.pet_norm_norm
    }

    @DrawableRes
    private fun lookup(keys: List<String>, mood: MoodLevel): Int? {
        val key = if (keys.isEmpty()) NO_CLOTHES else keys.joinToString("_")
        return COMBINATIONS[key]?.get(mood.code)
    }

    /** Ключ комплекта без одежды. Совпадает с именем файла `norm_<эмоция>`. */
    private const val NO_CLOTHES = "norm"

    /** id предмета в магазине -> ключ в имени файла. */
    private val ID_TO_KEY: Map<Int, String> = mapOf(
        1 to "hat_1",       // Цилиндр
        2 to "glasses_1",   // Очкарик
        3 to "jacket",      // Пиджак
        4 to "scarf",       // Шарф
        5 to "bow",         // Бантик
        6 to "cap",         // Кепка
        7 to "glasses_2",   // Очкарик 2
        8 to "headband",    // Повязка
        9 to "hat_2",       // Ковбойская шляпа
    )

    /** Порядок предметов в имени файла — как в каталоге `cells/`. */
    private val CANONICAL_ORDER = listOf(
        "hat_1", "hat_2", "cap", "headband", "glasses_1", "glasses_2",
        "bow", "scarf", "jacket"
    )

    /** Все предметы магазина, для которых есть спрайты. */
    val ACCESSORY_IDS: List<Int> = ID_TO_KEY.keys.sorted()

    /** Комплект -> эмоция -> ресурс. Ровно то, что есть в `cells/`. */
    private val COMBINATIONS: Map<String, Map<String, Int>> = mapOf(
        // Без одежды — во всех четырёх эмоциях
        "norm" to mapOf(
            "happy" to R.drawable.pet_norm_happy,
            "norm" to R.drawable.pet_norm_norm,
            "cry" to R.drawable.pet_norm_cry,
            "surprise" to R.drawable.pet_norm_surprise
        ),

        // --- Одиночные предметы: 9 штук, все четыре эмоции ---
        "hat_1" to mapOf(
            "happy" to R.drawable.pet_hat_1_happy,
            "norm" to R.drawable.pet_hat_1_norm,
            "cry" to R.drawable.pet_hat_1_cry,
            "surprise" to R.drawable.pet_hat_1_surprise
        ),
        "hat_2" to mapOf(
            "happy" to R.drawable.pet_hat_2_happy,
            "norm" to R.drawable.pet_hat_2_norm,
            "cry" to R.drawable.pet_hat_2_cry,
            "surprise" to R.drawable.pet_hat_2_surprise
        ),
        "cap" to mapOf(
            "happy" to R.drawable.pet_cap_happy,
            "norm" to R.drawable.pet_cap_norm,
            "cry" to R.drawable.pet_cap_cry,
            "surprise" to R.drawable.pet_cap_surprise
        ),
        "glasses_1" to mapOf(
            "happy" to R.drawable.pet_glasses_1_happy,
            "norm" to R.drawable.pet_glasses_1_norm,
            "cry" to R.drawable.pet_glasses_1_cry,
            "surprise" to R.drawable.pet_glasses_1_surprise
        ),
        "glasses_2" to mapOf(
            "happy" to R.drawable.pet_glasses_2_happy,
            "norm" to R.drawable.pet_glasses_2_norm,
            "cry" to R.drawable.pet_glasses_2_cry,
            "surprise" to R.drawable.pet_glasses_2_surprise
        ),
        "headband" to mapOf(
            "happy" to R.drawable.pet_headband_happy,
            "norm" to R.drawable.pet_headband_norm,
            "cry" to R.drawable.pet_headband_cry,
            "surprise" to R.drawable.pet_headband_surprise
        ),
        "bow" to mapOf(
            "happy" to R.drawable.pet_bow_happy,
            "norm" to R.drawable.pet_bow_norm,
            "cry" to R.drawable.pet_bow_cry,
            "surprise" to R.drawable.pet_bow_surprise
        ),
        "scarf" to mapOf(
            "happy" to R.drawable.pet_scarf_happy,
            "norm" to R.drawable.pet_scarf_norm,
            "cry" to R.drawable.pet_scarf_cry,
            "surprise" to R.drawable.pet_scarf_surprise
        ),
        "jacket" to mapOf(
            "happy" to R.drawable.pet_jacket_happy,
            "norm" to R.drawable.pet_jacket_norm,
            "cry" to R.drawable.pet_jacket_cry,
            "surprise" to R.drawable.pet_jacket_surprise
        ),

        // --- Пары: только happy / norm / cry ---
        "cap_bow" to mapOf(
            "happy" to R.drawable.pet_cap_bow_happy,
            "norm" to R.drawable.pet_cap_bow_norm,
            "cry" to R.drawable.pet_cap_bow_cry
        ),
        "cap_jacket" to mapOf(
            "happy" to R.drawable.pet_cap_jacket_happy,
            "norm" to R.drawable.pet_cap_jacket_norm,
            "cry" to R.drawable.pet_cap_jacket_cry
        ),
        "cap_scarf" to mapOf(
            "happy" to R.drawable.pet_cap_scarf_happy,
            "norm" to R.drawable.pet_cap_scarf_norm,
            "cry" to R.drawable.pet_cap_scarf_cry
        ),
        "glasses_1_jacket" to mapOf(
            "happy" to R.drawable.pet_glasses_1_jacket_happy,
            "norm" to R.drawable.pet_glasses_1_jacket_norm,
            "cry" to R.drawable.pet_glasses_1_jacket_cry
        ),
        "glasses_2_jacket" to mapOf(
            "happy" to R.drawable.pet_glasses_2_jacket_happy,
            "norm" to R.drawable.pet_glasses_2_jacket_norm,
            "cry" to R.drawable.pet_glasses_2_jacket_cry
        ),
        "glasses_2_scarf" to mapOf(
            "happy" to R.drawable.pet_glasses_2_scarf_happy,
            "norm" to R.drawable.pet_glasses_2_scarf_norm,
            "cry" to R.drawable.pet_glasses_2_scarf_cry
        ),
        "hat_1_jacket" to mapOf(
            "happy" to R.drawable.pet_hat_1_jacket_happy,
            "norm" to R.drawable.pet_hat_1_jacket_norm,
            "cry" to R.drawable.pet_hat_1_jacket_cry
        ),
        "hat_1_scarf" to mapOf(
            "happy" to R.drawable.pet_hat_1_scarf_happy,
            "norm" to R.drawable.pet_hat_1_scarf_norm,
            "cry" to R.drawable.pet_hat_1_scarf_cry
        ),
        "hat_2_jacket" to mapOf(
            "happy" to R.drawable.pet_hat_2_jacket_happy,
            "norm" to R.drawable.pet_hat_2_jacket_norm,
            "cry" to R.drawable.pet_hat_2_jacket_cry
        ),
        "hat_2_scarf" to mapOf(
            "happy" to R.drawable.pet_hat_2_scarf_happy,
            "norm" to R.drawable.pet_hat_2_scarf_norm,
            "cry" to R.drawable.pet_hat_2_scarf_cry
        ),

        // --- Тройки и четвёрки ---
        "cap_glasses_1_jacket" to mapOf(
            "happy" to R.drawable.pet_cap_glasses_1_jacket_happy,
            "norm" to R.drawable.pet_cap_glasses_1_jacket_norm,
            "cry" to R.drawable.pet_cap_glasses_1_jacket_cry
        ),
        "cap_glasses_1_scarf" to mapOf(
            "happy" to R.drawable.pet_cap_glasses_1_scarf_happy,
            "norm" to R.drawable.pet_cap_glasses_1_scarf_norm,
            "cry" to R.drawable.pet_cap_glasses_1_scarf_cry
        ),
        "hat_1_glasses_2_jacket" to mapOf(
            "happy" to R.drawable.pet_hat_1_glasses_2_jacket_happy,
            "norm" to R.drawable.pet_hat_1_glasses_2_jacket_norm,
            "cry" to R.drawable.pet_hat_1_glasses_2_jacket_cry
        ),
        "hat_1_glasses_2_scarf" to mapOf(
            "happy" to R.drawable.pet_hat_1_glasses_2_scarf_happy,
            "norm" to R.drawable.pet_hat_1_glasses_2_scarf_norm,
            "cry" to R.drawable.pet_hat_1_glasses_2_scarf_cry
        ),
        "hat_2_glasses_1_jacket" to mapOf(
            "happy" to R.drawable.pet_hat_2_glasses_1_jacket_happy,
            "norm" to R.drawable.pet_hat_2_glasses_1_jacket_norm,
            "cry" to R.drawable.pet_hat_2_glasses_1_jacket_cry
        ),
        "headband_glasses_2_jacket" to mapOf(
            "happy" to R.drawable.pet_headband_glasses_2_jacket_happy,
            "norm" to R.drawable.pet_headband_glasses_2_jacket_norm,
            "cry" to R.drawable.pet_headband_glasses_2_jacket_cry
        )
    )
}

/** Код эмоции в имени файла. */
private val MoodLevel.code: String
    get() = when (this) {
        MoodLevel.CRYING -> "cry"
        // NORMAL и DEFAULT — одно и то же «спокойное» настроение: отдельных
        // файлов для них нет, в каталоге cells/ это `norm`.
        MoodLevel.NORMAL, MoodLevel.DEFAULT -> "norm"
        MoodLevel.HAPPY -> "happy"
        MoodLevel.SURPRISE -> "surprise"
    }
