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

    /**
     * Шаг физики. Исходная игра напоминала Chrome-динозавра: скорость 9 px
     * за кадр 60 Гц и прыжок v=-40, g=3. Здесь шаги дискретны, но время
     * честное: при 120 Гц выполняется два шага, при 90 Гц — 1,5, поэтому
     * кактусы, прыжок и земля двигаются с одинаковой скоростью на любом
     * экране. Иначе игра на 60 Гц была в два раза быстрее, чем на 120 Гц.
     */
    const val STEP_SECONDS = 1f / 60f

    /**
     * Предельное число шагов за один кадр. Если приложение свернули на
     * несколько секунд, оно не должно «телепортировать» кактусы за спину
     * динозавра — просто пропустим лишнее время.
     */
    const val MAX_STEPS_PER_FRAME = 5

    /**
     * Во сколько раз «сжимаем» хитбоксы, чтобы игра прощала касания.
     *
     * В оригинале оба прямоугольника сжимались на 20 px по каждой стороне.
     * Снизу сжатие не делаем: кактус чуть утоплен под землю, и если срезать
     * низ, он «обрывается» раньше, чем динозавр приземлится после прыжка.
     */
    const val HITBOX_TOLERANCE = 20f

    /** Линия земли по Y в логических пикселях. */
    const val GROUND_Y = 500f

    /** Ширина линии земли. */
    const val GROUND_STROKE = 10f

    /** Минимальный Y динозавра — выше него прыжок не поднимает. */
    const val CEILING_Y = 0f

    /** Стартовый X динозавра. */
    const val DINO_X = 60f

    /** Скорость полёта облаков (пикселей за кадр 60 Гц). */
    const val CLOUDS_SPEED = 1

    /** Сколько облаков одновременно на экране. */
    const val MAX_CLOUDS = 3

    /** Смещение «бесконечной» земли. */
    const val EARTH_OFFSET = 200

    /** Скорость земли и кактусов (пикселей за кадр 60 Гц). */
    const val EARTH_SPEED = 9

    /** Сколько земли рисуем одновременно. */
    const val MAX_EARTH_BLOCKS = 2

    /** Доля ширины экрана между кактусами — как в оригинале (0.4). */
    const val CACTUS_GAP_RATIO = 0.4f

    /**
     * Верхняя граница «предельной» дистанции до следующего кактуса.
     *
     * При 1080 px дистанция в оригинале была бы 432–864 px, что на узком
     * телефоне выглядит как долгие паузы. Потолок держит игру оживлённой,
     * но он не ниже прыжка, так что кактусы не идут пачкой.
     */
    const val CACTUS_MAX_GAP = 640

    /** Стартовая скорость прыжка (px за шаг). */
    const val JUMP_VELOCITY = -52f

    /**
     * Ускорение свободного падения.
     *
     * С чуть более пологой дугой, чем -40/3 у оригинала: кактус в нашем
     * масштабе высокий, и при v=-40/g=3 его макушку невозможно перепрыгнуть
     * (зона в воздухе короче разбега кактуса). Прыжок длится ~87 шагов
     * (1.4 с), высота ~1130 px — с запасом над кактусом 132 px.
     */
    const val GRAVITY = 1.2f

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
     * Кактус рисуется с BASE_SCALE, поэтому 220 × 200 px исходного рисунка
     * становятся 165 × 150 px.
     */
    const val CACTUS_WIDTH = 165f
    const val CACTUS_HEIGHT = 150f
    const val CACTUS_MIN_SCALE = 0.85f
    const val CACTUS_MAX_SCALE = 1.2f

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

    /**
     * Один шаг физики. Кадры бега [keyframe] здесь не меняются: на 120 Гц
     * они бы проскакивали в два раза быстрее, поэтому анимация живёт в
     * [DinoGameState] и тоже считается по времени.
     */
    fun move() {
        if (!isJumping) return

        yPos += velocityY
        velocityY += gravity

        // Потолок: overkill для обычного прыжка, но не даёт динозавру
        // улететь за край Canvas, если физику когда-то усилят.
        if (yPos < DinoWorld.CEILING_Y) {
            yPos = DinoWorld.CEILING_Y
            velocityY = 0f
        }

        if (yPos >= DinoWorld.GROUND_Y) {
            yPos = DinoWorld.GROUND_Y
            gravity = 0f
            velocityY = 0f
            isJumping = false
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
    var scale: Float,
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
