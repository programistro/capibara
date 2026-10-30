package com.example.capibara.domain.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Игровой движок «Змейка».
 *
 * Цикл запускается через [start] и живёт в переданном [scope].
 * Состояние отдаётся наружу как [StateFlow] — UI подписывается и перерисовывается.
 */
class GameEngine(
    private val scope: CoroutineScope,
    private val stepDelayMillis: Long = DEFAULT_STEP_DELAY,
    private val onGameOver: (score: Int) -> Unit = {},
    /** Вызывается на каждую съеденную еду. Монеты начисляем сразу, а не в конце. */
    private val onFoodEaten: (score: Int) -> Unit = {}
) {

    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<SnakeState> = _state.asStateFlow()

    private var loop: Job? = null

    /** Направление, которое применится на следующем шаге. */
    private var pendingDirection: SnakeDirection = SnakeDirection.RIGHT

    fun start() {
        if (loop?.isActive == true) return
        loop = scope.launch {
            while (isActive) {
                delay(stepDelayMillis)
                if (_state.value.isGameOver) break
                step()
            }
        }
    }

    fun reset() {
        loop?.cancel()
        loop = null
        pendingDirection = SnakeDirection.RIGHT
        _state.value = initialState()
    }

    /** Перезапуск без создания нового движка. */
    fun restart() {
        reset()
        start()
    }

    fun changeDirection(newDirection: SnakeDirection) {
        // Игнорируем движение после проигрыша.
        if (_state.value.isGameOver) return
        // Сверяемся с НАМЕРЕНИЕМ, а не с последним шагом: иначе два быстрых
        // нажатия «вверх», «вниз» при движении вправо развернули бы змейку в себя.
        if (newDirection.isOpposite(pendingDirection)) return
        pendingDirection = newDirection
    }

    private fun step() {
        val current = _state.value

        val head = current.snake.first()
        val nextX = (head.first + pendingDirection.dx + BOARD_SIZE) % BOARD_SIZE
        val nextY = (head.second + pendingDirection.dy + BOARD_SIZE) % BOARD_SIZE
        val newHead = nextX to nextY

        // Столкновение с телом. Хвост уходит на этом же шаге, поэтому его проверять не нужно.
        val bodyToCheck = current.snake.dropLast(1)
        if (bodyToCheck.contains(newHead)) {
            val gameOver = current.copy(direction = pendingDirection, isGameOver = true)
            _state.value = gameOver
            onGameOver(current.score)
            return
        }

        val hasEaten = newHead == current.food
        val newSnake = if (hasEaten) {
            listOf(newHead) + current.snake
        } else {
            listOf(newHead) + current.snake.dropLast(1)
        }

        val newScore = if (hasEaten) current.score + 1 else current.score

        _state.value = current.copy(
            snake = newSnake,
            food = if (hasEaten) spawnFood(newSnake) else current.food,
            direction = pendingDirection,
            score = newScore
        )

        // Начисляем монеты за каждую съеденную еду сразу. Раньше награда
        // выдавалась только в onGameOver, а игра заканчивается лишь при
        // столкновении с собой — до этого момента очки никуда не шли.
        if (hasEaten) {
            onFoodEaten(newScore)
        }
    }

    private fun spawnFood(snake: List<Pair<Int, Int>>): Pair<Int, Int> {
        // Пробуем случайные клетки, чтобы еда не оказалась внутри змейки.
        repeat(FREE_CELL_ATTEMPTS) {
            val candidate = Random.nextInt(BOARD_SIZE) to Random.nextInt(BOARD_SIZE)
            if (candidate !in snake) return candidate
        }
        // Если свободных клеток не нашлось — берём первую попавшуюся.
        return 0 to 0
    }

    private fun initialState() = SnakeState(
        snake = listOf(START_X to START_Y),
        food = FOOD_X to FOOD_Y,
        direction = SnakeDirection.RIGHT
    )

    companion object {
        const val BOARD_SIZE = 32
        const val DEFAULT_STEP_DELAY = 200L

        private const val START_X = 16
        private const val START_Y = 16
        private const val FOOD_X = 5
        private const val FOOD_Y = 5
        private const val FREE_CELL_ATTEMPTS = 50
    }
}
