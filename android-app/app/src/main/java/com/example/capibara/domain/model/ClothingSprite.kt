package com.example.capibara.domain.model

import androidx.annotation.DrawableRes
import com.example.capibara.R

/**
 * Одежда капибары: предметы из `android-app/cells/clothes/`.
 *
 * Раньше одежда приходила готовыми склейками `pet_<одежда>_<эмоция>.png`
 * (91 картинка — только те сочетания, что успели нарисовать). Теперь
 * верхний слой рисует Compose: базовая капибара + одежда в координатах
 * её холста 640×1000.
 *
 * Координаты посчитаны для холста базовых спрайтов (`pet_norm_*`):
 * фигура высотой 800 px, низ на строке 924. Одежда — тот же рисунок, что
 * раньше рисовался в Lunacy в системе 408×588, пересчитанный по шагу
 * `BASE_SCALE` и сдвигу `BASE_LEFT` (левый край фигуры 150×218).
 *
 * Инвариант: [BASE_SCALE] / [TEMPLATE_FIGURE_HEIGHT] равно масштабу,
 * в котором базовые спрайты уже лежат в `res/drawable`, — поэтому одежда
 * совпадает с головой и лапами без подгонки под каждую эмоцию.
 */
object ClothingSprite {

    /** Высота фигуры шаблона (cells/нормальная/norm_norm.png). */
    const val TEMPLATE_FIGURE_HEIGHT = 218f

    /** Ширина фигуры шаблона. */
    const val TEMPLATE_FIGURE_WIDTH = 150f

    /** Холст базовых спрайтов капибары. */
    const val CANVAS_WIDTH = 640f
    const val CANVAS_HEIGHT = 1000f

    /** Фигура на холсте: высота и низ (строка, где стоят лапы). */
    const val FIGURE_HEIGHT = 800f
    const val FIGURE_BOTTOM = 924f

    /** Масштаб шаблона -> холст. */
    const val BASE_SCALE = FIGURE_HEIGHT / TEMPLATE_FIGURE_HEIGHT

    /** Левый край фигуры на холсте (общая точка отсчёта). */
    const val BASE_LEFT = (CANVAS_WIDTH - TEMPLATE_FIGURE_WIDTH * BASE_SCALE) / 2f

    /** Положение лап и головы на фигуре шаблона, в её единицах. */
    const val TEMPLATE_TOP = 0f
    const val TEMPLATE_NECK = 108f

    /** id предмета магазина -> ключ картинки. */
    private val KEY_BY_ID: Map<Int, String> = mapOf(
        1 to "hat_1",
        2 to "glasses_1",
        3 to "jacket",
        4 to "scarf",
        5 to "bow",
        6 to "cap",
        7 to "glasses_2",
        8 to "headband",
        9 to "hat_2"
    )

    /**
     * Позиция одежды на фигуре шаблона, единицы шаблона.
     *
     * [x] — левый край картинки, [y] — верхний край, [width] — ширина,
     * с которой рисуем. Высота считается по пропорции картинки, чтобы
     * одежду не расплющивало.
     */
    data class Fit(
        val x: Float,
        val y: Float,
        val width: Float,
        val height: Float
    )

    /** Ключи всех предметов — в порядке наложения снизу вверх. */
    val LAYER_ORDER: List<String> = listOf(
        "jacket", "scarf", "glasses_1", "glasses_2", "bow",
        "headband", "hat_1", "hat_2", "cap"
    )

    /** Положение на холсте 640×1000 для ключа [key]. */
    fun fitOnCanvas(key: String): Fit? = FITS[key]?.let { f ->
        Fit(
            x = BASE_LEFT + f.x * BASE_SCALE,
            y = (FIGURE_BOTTOM - TEMPLATE_FIGURE_HEIGHT * BASE_SCALE) +
                f.y * BASE_SCALE,
            width = f.width * BASE_SCALE,
            height = f.height * BASE_SCALE
        )
    }

    /** Ключ предмета магазина; null — это не одежда. */
    fun keyOf(itemId: Int): String? = KEY_BY_ID[itemId]

    fun itemIdOf(key: String): Int? = KEY_BY_ID.entries.firstOrNull { it.value == key }?.key

    /** id всех предметов одежды. */
    val itemIds: Set<Int> = KEY_BY_ID.keys

    /** Одежда из [itemIds] в порядке наложения: низ в начале списка. */
    fun layersOf(itemIds: Collection<Int>): List<String> {
        val keys = itemIds.mapNotNull { KEY_BY_ID[it] }.toSet()
        return LAYER_ORDER.filter { it in keys }
    }

    /**
     * Слой одежды: картинка и её размер в пикселях картинки — чтобы
     * Compose масштабировал по альфе, а не по пустым краям файла.
     */
    @DrawableRes
    fun drawable(key: String): Int = when (key) {
        "hat_1" -> R.drawable.clothes_hat_1
        "hat_2" -> R.drawable.clothes_hat_2
        "cap" -> R.drawable.clothes_cap
        "headband" -> R.drawable.clothes_headband
        "glasses_1" -> R.drawable.clothes_glasses_1
        "glasses_2" -> R.drawable.clothes_glasses_2
        "bow" -> R.drawable.clothes_bow
        "scarf" -> R.drawable.clothes_scarf
        "jacket" -> R.drawable.clothes_jacket
        else -> error("Неизвестная одежда: $key")
    }

    /**
     * Высота картинки одежды для ширины [width]. Источник — исходные
     * пропорции PNG из `cells/clothes`.
     */
    fun heightForWidth(key: String, width: Float): Float =
        width * (SOURCE_SIZES[key]?.second ?: 1f) / (SOURCE_SIZES[key]?.first ?: 1f)

    /** Исходные PNG, ширина × высота. */
    private val SOURCE_SIZES: Map<String, Pair<Float, Float>> = mapOf(
        "hat_1" to (249f to 214f),
        "hat_2" to (367f to 189f),
        "cap" to (180f to 159f),
        "headband" to (239f to 135f),
        "glasses_1" to (192f to 56f),
        "glasses_2" to (167f to 41f),
        "bow" to (142f to 75f),
        "scarf" to (289f to 236f),
        "jacket" to (358f to 312f)
    )

    /**
     * Положение предмета в единицах шаблона 150×218.
     *
     * Шляпы (hat_1, hat_2, cap, headband) выровнены по голове шаблона
     * `cells/нормальная/norm_norm.png`, а не перенесены 1:1 из Lunacy:
     * в Lunacy капибара сдвигалась вниз на разную величину под каждую
     * шляпу (`REF_OFF` в `.spritegen/gen_pairs.py`), поэтому там y=0 —
     * это верх уже сдвинутого тела. Так цилиндр въезжал полями в глаза
     * и ковбойская шляпа садилась на морду.
     *
     * Ориентиры головы в шаблоне: макушка y≈16, лоб до y≈33, глаза
     * y≈45..55, центр головы x=80 (фигура шириной 150).
     *
     * - поля шляпы должны лечь на лоб (y≈27–40), ниже — на глаза;
     * - у hat_1 тулья занимает 70% высоты файла, поэтому полей шириной
     *   110–120 шаблонных единиц (как у hat_2) можно добиться только
     *   при ширине около 240 — иначе шляпа уже головы;
     * - у hat_2 и cap макушка начинается сразу с верхнего края файла.
     *
     * Высота в [Fit] — из пропорций PNG ([heightForWidth]); на экране
     * Compose ещё раз масштабирует картинку строго в width × высоту,
     * поэтому поле должно быть реальным, а не нулём.
     */
    private val FITS: Map<String, Fit> = mapOf(
        "hat_1" to Fit(-40f, 70f, 240f, 206.3f),
        "hat_2" to Fit(45f, 0f, 70f, 36.0f),
        "cap" to Fit(47f, 0f, 66f, 58.3f),
        "headband" to Fit(43f, 18f, 74f, 41.8f),
        "glasses_1" to Fit(43.4f, 44.9f, 76.5f, 17.4f),
        "glasses_2" to Fit(37.5f, 44.5f, 89.3f, 20.3f),
        "bow" to Fit(48.9f, 109.0f, 69.8f, 32.3f),
        "scarf" to Fit(0.1f, 86.4f, 142.6f, 110.3f),
        "jacket" to Fit(5.9f, 83.8f, 144.1f, 120.6f)
    )
}
