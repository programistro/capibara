package com.example.capibara.presentation.games.dino.model

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.example.capibara.presentation.games.dino.DinoPath
import com.example.capibara.presentation.games.dino.DinoPath2

/**
 * Константы игрового мира динозавра.
 *
 * Все координаты — в пикселях внутри `Canvas`. Раньше это были глобальные
 * `var` на уровне файла, из-за чего игра не могла работать дважды за сессию
 * и ломала предпросмотры. Теперь они собраны в объект.
 */
object DinoWorld {
    /** Масштаб отрисовки относительно исходной картинки. */
    const val SCALE = 0.75f

    /** Во сколько раз «сжимаем» хитбоксы, чтобы игра прощала касания. */
    const val HITBOX_TOLERANCE = 20f

    /** Линия земли по Y в логических пикселях. */
    const val GROUND_Y = 500f

    /** Ширина линии земли. */
    const val GROUND_STROKE = 10f

    /** Стартовый X динозавра. */
    const val DINO_X = 60f

    /** Скорость полёта облаков (пикселей за кадр). */
    const val CLOUDS_SPEED = 1

    /** Сколько облаков одновременно на экране. */
    const val MAX_CLOUDS = 3

    /** Смещение «бесконечной» земли. */
    const val EARTH_OFFSET = 200

    /** Скорость земли и кактусов (пикселей за кадр). */
    const val EARTH_SPEED = 9

    /** Сколько земли рисуем одновременно. */
    const val MAX_EARTH_BLOCKS = 2

    /** Минимальная дистанция между кактусами. */
    const val CACTUS_GAP = 100

    /** Стартовая высота прыжка. */
    const val JUMP_VELOCITY = -40f

    /** Ускорение свободного падения. */
    const val GRAVITY = 3f

    /**
     * Логические размеры хитбокса динозавра.
     *
     * Размеры заданы константами, а не через `Path.getBounds()`: в JVM-тестах
     * `android.graphics` не реализован и возвращает нули, из-за чего коллизии
     * нельзя проверить. Числа подобраны под исходный рисунок с BASE_SCALE.
     */
    const val DINO_WIDTH = 131f
    const val DINO_HEIGHT = 130f

    /**
     * Логический размер кактуса при scale = 1.
     *
     * Высота подобрана под силу прыжка: динозавр поднимается на
     * `JUMP_VELOCITY² / (2 * GRAVITY)` ≈ 267 px, а вершина его хитбокса
     * на пике находится на `GROUND_Y - 267 - DINO_HEIGHT` ≈ 103. Чтобы кактус
     * реально можно было перепрыгнуть, его макушка должна быть ниже этого
     * уровня — отсюда 110 при максимальном scale 1.2.
     */
    const val CACTUS_WIDTH = 110f
    const val CACTUS_HEIGHT = 110f

    /**
     * Насколько низ кактуса ниже линии земли.
     *
     * Кактус стоит на земле, а не висит над ней. Небольшой сдвиг вниз
     * скрывает «ножку» рисунка под линией.
     */
    const val CACTUS_FOOT_OFFSET = 20
}

/**
 * Состояние динозавра: позиция, прыжок и чередование кадров бега.
 */
class DinoState(
    var xPos: Float = DinoWorld.DINO_X,
    var yPos: Float = DinoWorld.GROUND_Y,
    var velocityY: Float = 0f,
    var gravity: Float = 0f,
    var keyframe: Int = 0,
    var isJumping: Boolean = false
) {
    // Два кадра анимации бега. Строятся один раз, а не на каждый кадр.
    private val pathList: List<Path> = listOf(DinoPath(), DinoPath2())

    val path: Path
        get() = if (keyframe <= 5) pathList[0] else pathList[1]

    fun reset() {
        xPos = DinoWorld.DINO_X
        yPos = DinoWorld.GROUND_Y
        velocityY = 0f
        gravity = 0f
        isJumping = false
        keyframe = 0
    }

    fun jump() {
        // Прыжок только с земли — иначе можно «летать».
        if (yPos >= DinoWorld.GROUND_Y) {
            isJumping = true
            velocityY = DinoWorld.JUMP_VELOCITY
            gravity = DinoWorld.GRAVITY
        }
    }

    fun move() {
        yPos += velocityY
        velocityY += gravity

        if (yPos > DinoWorld.GROUND_Y) {
            yPos = DinoWorld.GROUND_Y
            gravity = 0f
            velocityY = 0f
            isJumping = false
        }

        // Кадры бега меняем только на земле.
        if (!isJumping) {
            keyframe = (keyframe + 1) % 10
        }
    }

    /** Прямоугольник динозавра для проверки столкновений. */
    fun bounds(): Rect = Rect(
        left = xPos,
        top = yPos - DinoWorld.DINO_HEIGHT,
        right = xPos + DinoWorld.DINO_WIDTH,
        bottom = yPos
    )
}

/** Облако на фоне. */
data class CloudModel(
    var xPos: Int = 0,
    var yPos: Int = 0
)

/** Кактус-препятствие. */
class CactusModel(
    val scale: Float,
    var xPos: Int = 0,
    var yPos: Int = 0,
    val path: Path = com.example.capibara.presentation.games.dino.CactusPath()
) {
    fun bounds(): Rect = Rect(
        left = xPos.toFloat(),
        top = yPos.toFloat() - DinoWorld.CACTUS_HEIGHT * scale,
        right = xPos + DinoWorld.CACTUS_WIDTH * scale,
        bottom = yPos.toFloat()
    )
}

/** Область бесконечной земли, которая прокручивается. */
data class EarthModel(
    var xPos: Float = 0f,
    var size: Float = 0f
)
