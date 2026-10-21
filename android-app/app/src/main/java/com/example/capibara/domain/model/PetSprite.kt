package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Спрайты капибары: по одному на каждое сочетание «предмет × настроение».
 *
 * В `res/drawable` лежат 30 готовых картинок (`pet_<предмет>_<настроение>`), где
 * капибара уже нарисована вместе с надетым предметом. Отдельного «пустого» тела
 * для наложения аксессуаров нет, поэтому комплект всегда выбирается целиком.
 */
object PetSprite {

    /** Предметы, для которых нарисованы спрайты во всех трёх настроениях. */
    val ACCESSORY_IDS: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    /**
     * Спрайт предмета [itemId] в настроении [mood].
     *
     * Если для пары нет картинки (например, у предмета вне магазина), возвращается
     * базовая капибара в этом же настроении — лучше показать её, чем упасть.
     */
    @DrawableRes
    fun of(itemId: Int?, mood: MoodLevel): Int = when (itemId) {
        1 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_hat_1_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_hat_1_norm
            MoodLevel.HAPPY -> R.drawable.pet_hat_1_happy
        }
        2 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_glasses_1_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_glasses_1_norm
            MoodLevel.HAPPY -> R.drawable.pet_glasses_1_happy
        }
        3 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_jacket_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_jacket_norm
            MoodLevel.HAPPY -> R.drawable.pet_jacket_happy
        }
        4 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_scarf_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_scarf_norm
            MoodLevel.HAPPY -> R.drawable.pet_scarf_happy
        }
        5 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_bow_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_bow_norm
            MoodLevel.HAPPY -> R.drawable.pet_bow_happy
        }
        6 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_cap_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_cap_norm
            MoodLevel.HAPPY -> R.drawable.pet_cap_happy
        }
        7 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_glasses_2_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_glasses_2_norm
            MoodLevel.HAPPY -> R.drawable.pet_glasses_2_happy
        }
        8 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_headband_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_headband_norm
            MoodLevel.HAPPY -> R.drawable.pet_headband_happy
        }
        9 -> when (mood) {
            MoodLevel.CRYING -> R.drawable.pet_hat_2_cry
            MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_hat_2_norm
            MoodLevel.HAPPY -> R.drawable.pet_hat_2_happy
        }
        else -> base(mood)
    }

    /** Капибара без предметов в настроении [mood]. */
    @DrawableRes
    fun base(mood: MoodLevel): Int = when (mood) {
        MoodLevel.CRYING -> R.drawable.pet_crying
        MoodLevel.NORMAL, MoodLevel.DEFAULT -> R.drawable.pet_normal
        MoodLevel.HAPPY -> R.drawable.pet_happy
    }
}
