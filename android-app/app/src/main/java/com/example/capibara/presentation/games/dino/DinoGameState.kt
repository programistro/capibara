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

    private var deviceWidth = 1080

    /**
     * Номер партии. Увеличивается при каждом рестарте — по нему UI понимает,
     * что нужно заново запустить игровой цикл.
     */
    var generation: Int = 0
        private set

    init {
        reset()
    }

    /** Пересоздать мир под новую ширину экрана. */
    fun configure(deviceWidth: Int) {
        if (this.deviceWidth == deviceWidth && clouds.isNotEmpty()) return
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

    /** Один кадр игры. */
    fun update() {
        if (isGameOver) return

        currentScore++
        moveClouds()
        moveEarth()
        moveCacti()
        dino.move()

        // Столкновение проверяем по сжатым хитбоксам — как в оригинале.
        val dinoBox = dino.bounds().shrink(DinoWorld.HITBOX_TOLERANCE)
        for (cactus in cacti) {
            if (dinoBox.overlaps(cactus.bounds().shrink(DinoWorld.HITBOX_TOLERANCE))) {
                isGameOver = true
                return
            }
        }
    }

    private fun reset() {
        currentScore = 0
        isGameOver = false
        dino = DinoState()
        initClouds()
        initEarth()
        initCacti()
    }

    private fun initClouds() {
        val list = mutableListOf<CloudModel>()
        var startX = 150
        repeat(DinoWorld.MAX_CLOUDS) {
            list.add(CloudModel(xPos = startX, yPos = Random.nextInt(0, 100)))
            startX += Random.nextInt(150, deviceWidth + 1)
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
            list.add(
                CactusModel(
                    scale = Random.nextDouble(0.85, 1.2).toFloat(),
                    xPos = startX,
                    yPos = DinoWorld.GROUND_Y.toInt() + DinoWorld.CACTUS_FOOT_OFFSET
                )
            )
            startX += DinoWorld.CACTUS_GAP + Random.nextInt(0, DinoWorld.CACTUS_GAP + 1)
        }
        cacti = list
    }

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
            list.add(
                CactusModel(
                    scale = Random.nextDouble(0.85, 1.2).toFloat(),
                    xPos = nextCactusX(list.last().xPos),
                    yPos = DinoWorld.GROUND_Y.toInt() + DinoWorld.CACTUS_FOOT_OFFSET
                )
            )
        }
        cacti = list
    }

    private fun nextCactusX(lastX: Int): Int {
        var nextX = lastX + DinoWorld.CACTUS_GAP + Random.nextInt(0, DinoWorld.CACTUS_GAP + 1)
        if (nextX < deviceWidth) nextX += deviceWidth - nextX
        return nextX
    }

    private companion object {
        const val CACTUS_COUNT = 3
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
