package com.example.capibara.presentation.games.dino

import androidx.compose.ui.geometry.Rect
import com.example.capibara.presentation.games.dino.model.CactusModel
import com.example.capibara.presentation.games.dino.model.CloudModel
import com.example.capibara.presentation.games.dino.model.DinoState
import com.example.capibara.presentation.games.dino.model.DinoWorld
import com.example.capibara.presentation.games.dino.model.EarthModel
import kotlin.random.Random

/**
 * Состояние одной партии в «Динозавре».
 *
 * Класс намеренно изменяемый: игра обновляется каждый кадр, и создавать новый
 * объект 60 раз в секунду нет смысла. UI читает поля внутри `Canvas` —
 * перерисовку запускает [DinoGameScreen] через `withFrameNanos`.
 *
 * Раньше все эти поля были глобальными `var` в файлах и `LiveData` внутри
 * `GameState`. Теперь состояние принадлежит экземпляру и живёт в ViewModel.
 */
class DinoGameState {

    var dino = DinoState()
        private set

    var clouds: List<CloudModel> = emptyList()
        private set

    var cacti: List<CactusModel> = emptyList()
        private set

    var earth: List<EarthModel> = emptyList()
        private set

    var currentScore: Int = 0
        private set

    // Рекорд живёт в пределах сессии: для сохранения между запусками нужна
    // отдельная таблица в Room с миграцией, чем сейчас не занимался.
    var highScore: Int = 0
        private set

    var isGameOver: Boolean = false
        private set

    // Ширина Canvas. До первого configure() мир не создаётся: иначе
    // кактусы раскладывались бы по ширине по умолчанию.
    private var deviceWidth = 0

    /**
     * Номер партии. Увеличивается при каждом рестарте — по нему UI понимает,
     * что нужно заново запустить игровой цикл.
     */
    var generation: Int = 0
        private set

    // Время мира. Хранится в шагах физики, а не в кадрах: экраны бывают
    // 120 Гц, и одна и та же партия должна выглядеть одинаково везде.
    private var animationStep = 0
    private var stepDebt = 0f

    init {
        reset()
    }

    /**
     * Подогнать мир под ширину Canvas.
     *
     * Старое значение по умолчанию (1080) совпадало с шириной Samsung:
     * первый же вызов считал, что ширина «та же», но раскладка кактусов
     * могла быть сделана до получения настоящей ширины. Теперь мир
     * создаётся заново при первом вызове и при реальной смене ширины
     * (поворот, превью), а не на каждом кадре.
     */
    fun configure(deviceWidth: Int) {
        if (deviceWidth <= 0) return
        if (this.deviceWidth == deviceWidth) return
        this.deviceWidth = deviceWidth
        reset()
    }

    fun jump() {
        if (!isGameOver) dino.jump()
    }

    fun restart() {
        if (currentScore > highScore) highScore = currentScore
        reset()
        generation++
    }

    /**
     * Продвинуть игру на [elapsedNanos] прошедшего времени.
     *
     * Исходник после каждого кадра считал кадры «как получится»: на 60 Гц
     * всё ехало в два раза быстрее, чем на 120 Гц. Здесь время разбивается
     * на фиксированные шаги [DinoWorld.STEP_SECONDS]; оставшаяся дробь
     * переносится в следующий вызов, поэтому скорость не зависит от
     * частоты экрана.
     */
    fun update(elapsedNanos: Long) {
        if (isGameOver) return

        val seconds = elapsedNanos.coerceIn(0L, MAX_FRAME_NANOS) / 1_000_000_000f
        stepDebt += seconds / DinoWorld.STEP_SECONDS
        var steps = minOf(stepDebt.toInt(), DinoWorld.MAX_STEPS_PER_FRAME)
        // Излишек времени выбрасываем, иначе после сворачивания приложения
        // игра «догонит» его пачкой шагов и кактус окажется на динозавре.
        stepDebt = (stepDebt - steps).coerceIn(0f, 1f)

        while (steps-- > 0) {
            // Ширину знает только Canvas; до первого configure() мир не шагаем,
            // иначе облака переносились бы по нулевой ширине.
            if (deviceWidth <= 0) return
            if (step()) return
        }
    }

    /** Один шаг физики. Возвращает true, если игра закончилась. */
    private fun step(): Boolean {
        currentScore++
        animationStep++
        dino.keyframe = (animationStep / FRAMES_PER_KEYFRAME) % KEYFRAMES
        moveClouds()
        moveEarth()
        moveCacti()
        dino.move()

        // Столкновение проверяем по хитбоксам — как в оригинале (DOUBT_FACTOR).
        // По ширине прощаем касания с обеих сторон, по высоте — только сверху.
        val dinoBox = dino.bounds().shrink(DinoWorld.HITBOX_TOLERANCE)
        for (cactus in cacti) {
            if (dinoBox.overlaps(cactus.bounds().shrinkCactus(DinoWorld.HITBOX_TOLERANCE))) {
                isGameOver = true
                return true
            }
        }
        return false
    }

    private fun reset() {
        currentScore = 0
        isGameOver = false
        animationStep = 0
        stepDebt = 0f
        dino = DinoState()
        initClouds()
        initEarth()
        // configure() пересоздаёт мир под ширину экрана: превью и старт
        // экрана сначала дают его, потом Canvas — ширина. До этого раскладка
        // кактусов была заведомо неверной, поэтому ждём Canvas.
        initCacti()
    }

    private fun initClouds() {
        val list = mutableListOf<CloudModel>()
        var startX = 150
        repeat(DinoWorld.MAX_CLOUDS) {
            list.add(CloudModel(xPos = startX, yPos = Random.nextInt(0, 100)))
            // До первого configure() ширины нет: `nextInt(150, deviceWidth + 1)`
            // с deviceWidth = 0 давал границы 150..1 и роняла конструктор.
            startX += deviceWidth + if (deviceWidth > CLOUD_GAP_MIN) {
                Random.nextInt(CLOUD_GAP_MIN, deviceWidth + 1)
            } else {
                CLOUD_GAP_MIN
            }
        }
        clouds = list
    }

    private fun initEarth() {
        val blockSize = deviceWidth + DinoWorld.EARTH_OFFSET * 2f
        earth = List(DinoWorld.MAX_EARTH_BLOCKS) { i ->
            EarthModel(
                xPos = -DinoWorld.EARTH_OFFSET + blockSize * i,
                size = blockSize
            )
        }
    }

    private fun initCacti() {
        val list = mutableListOf<CactusModel>()
        var startX = deviceWidth + 150
        repeat(CACTUS_COUNT) {
            list.add(newCactus(startX))
            startX += nextCactusGap()
        }
        cacti = list
    }

    /** Новый кактус. Числа — из оригинала: scale 0.85…1.2, низ у земли. */
    private fun newCactus(xPos: Int): CactusModel = CactusModel(
        scale = Random.nextDouble(
            DinoWorld.CACTUS_MIN_SCALE.toDouble(),
            DinoWorld.CACTUS_MAX_SCALE.toDouble()
        ).toFloat(),
        xPos = xPos,
        yPos = DinoWorld.GROUND_Y.toInt() + DinoWorld.CACTUS_FOOT_OFFSET
    )

    /**
     * Дистанция до следующего кактуса. В оригинале это `ширина * 0.4` плюс
     * случайное до той же величины. Раньше здесь стояли жёсткие 100–200 px:
     * на телефоне кактусы шли почти вплотную, и второй всегда был на пути
     * после приземления.
     */
    private fun nextCactusGap(): Int {
        val base = (deviceWidth * DinoWorld.CACTUS_GAP_RATIO)
            .toInt()
            .coerceIn(MIN_CACTUS_GAP, DinoWorld.CACTUS_MAX_GAP)
        return base + Random.nextInt(0, base + 1)
    }

    /** Один кактус не должен «наезжать» на следующий. */
    private fun spacingOk(previous: CactusModel, nextX: Int): Boolean =
        nextX >= previous.xPos + previous.bounds().width + MIN_CACTUS_GAP

    private fun moveClouds() {
        clouds.forEach { cloud ->
            cloud.xPos -= DinoWorld.CLOUDS_SPEED
            if (cloud.xPos < -100) {
                cloud.xPos = Random.nextInt(deviceWidth, deviceWidth * 2 + 1)
                cloud.yPos = Random.nextInt(0, 100)
            }
        }
    }

    private fun moveEarth() {
        val lastRight = earth.last().xPos + earth.last().size
        earth.forEach { block ->
            block.xPos -= DinoWorld.EARTH_SPEED
            if (block.xPos + block.size < -DinoWorld.EARTH_OFFSET) {
                block.xPos = lastRight
            }
        }
    }

    private fun moveCacti() {
        cacti.forEach { it.xPos -= DinoWorld.EARTH_SPEED }

        val list = cacti.toMutableList()
        if (list.first().xPos < -250) {
            list.removeAt(0)
            val next = newCactus(nextCactusX(list.last()))
            // Случайный scale мог сжать следующий кактус левее предыдущего:
            // сдвигаем, чтобы между ними оставалась дырка.
            if (!spacingOk(list.last(), next.xPos)) {
                next.xPos = (list.last().xPos + list.last().bounds().width + nextCactusGap()).toInt()
            }
            list.add(next)
        }
        cacti = list
    }

    private fun nextCactusX(last: CactusModel): Int {
        val previousRight = last.xPos + last.bounds().width
        var nextX = (previousRight + nextCactusGap()).toInt()
        // Высота кактуса зависит от scale, а дистанция — от его края,
        // иначе высокий кактус визуально «догоняет» предыдущий.
        if (nextX < deviceWidth + 150) nextX = deviceWidth + 150
        return nextX
    }

    private companion object {
        const val CACTUS_COUNT = 3

        /** Минимальный зазор между облаками при переносе из `initClouds`. */
        const val CLOUD_GAP_MIN = 150

        /** Потолок частоты экрана, который учитываем (8 Гц — явно зависший кадр). */
        const val MAX_FRAME_NANOS = 125_000_000L

        /** Ниже этого кактусы не успевают «выйти» из-за края экрана. */
        const val MIN_CACTUS_GAP = 260

        /** Сколько шагов физики длится один кадр бега (600 мс на цикл). */
        const val FRAMES_PER_KEYFRAME = 6
        const val KEYFRAMES = 10
    }
}

/**
 * Уменьшает прямоугольник со всех сторон, но никогда не делает его
 * вывернутым: если прямоугольник меньше 2 * amount, отдаём центр нулевого
 * размера. Иначе [overlaps] сравнивает отрицательные ширины и «находит»
 * столкновение там, где его нет.
 */
private fun Rect.shrink(amount: Float): Rect {
    val dx = minOf(amount, width / 2f)
    val dy = minOf(amount, height / 2f)
    return Rect(
        left = left + dx,
        top = top + dy,
        right = right - dx,
        bottom = bottom - dy
    )
}

/**
 * Хитбокс кактуса. Низ намеренно не сжимаем: кактус утоплен под землю на
 * [DinoWorld.CACTUS_FOOT_OFFSET], и без нижней «ноги» столкновение
 * заканчивалось раньше, чем динозавр успевал приземлиться после прыжка.
 */
private fun Rect.shrinkCactus(amount: Float): Rect {
    val dx = minOf(amount, width / 2f)
    val dy = minOf(amount, height / 2f)
    return Rect(
        left = left + dx,
        top = top + dy,
        right = right - dx,
        bottom = bottom
    )
}
