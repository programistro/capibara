package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Базовая капибара без одежды — по одному спрайту на эмоцию.
 *
 * Одежда больше не вшита в готовые склейки: её накладывает Compose слоями
 * поверх базы (см. [ClothingSprite]). Так любой набор из девяти предметов
 * рисуется без 512 предварительно нарисованных картинок.
 */
object PetSprite {

    /** Последний рубеж: спокойная капибара без одежды. */
    private const val NORMAL_BASE = "pet_norm_norm"

    /** Все эмоции, для которых есть базовая картинка. */
    val MOODS: List<MoodLevel> = listOf(
        MoodLevel.HAPPY, MoodLevel.NORMAL, MoodLevel.CRYING, MoodLevel.SURPRISE
    )

    /** Код эмоции в имени файла: `happy`, `norm`, `cry`, `surprise`. */
    fun codeOf(mood: MoodLevel): String = when (mood) {
        MoodLevel.HAPPY -> "happy"
        MoodLevel.CRYING -> "cry"
        MoodLevel.SURPRISE -> "surprise"
        // Отдельных картинок для NORMAL/DEFAULT нет — обе дают спокойную.
        MoodLevel.NORMAL, MoodLevel.DEFAULT -> "norm"
    }

    /** Имя drawable базовой капибары в [mood]. */
    internal fun nameOf(mood: MoodLevel): String = "pet_norm_${codeOf(mood)}"

    /** Спрайт капибары без одежды. */
    @DrawableRes
    fun base(mood: MoodLevel): Int = resource(nameOf(mood))

    /**
     * Спрайт капибары в [mood]. Одежда в него не входит: её рисует
     * [com.example.capibara.presentation.common.CapybaraFigure].
     */
    @DrawableRes
    @Deprecated("Одежда теперь слоями в Compose; передавай mood в CapybaraFigure")
    fun of(itemIds: Collection<Int>, mood: MoodLevel): Int = base(mood)

    @DrawableRes
    private fun resource(name: String): Int = when (name) {
        "pet_norm_happy" -> R.drawable.pet_norm_happy
        "pet_norm_cry" -> R.drawable.pet_norm_cry
        "pet_norm_surprise" -> R.drawable.pet_norm_surprise
        else -> R.drawable.pet_norm_norm
    }
}
