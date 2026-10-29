package com.example.capibara.domain.game

/**
 * Состояние игрового поля.
 *
 * [snake] — список клеток, голова всегда первая.
 * Координаты — пара (x, y) в диапазоне 0..[GameEngine.BOARD_SIZE]-1.
 */
data class SnakeState(
    val snake: List<Pair<Int, Int>>,
    val food: Pair<Int, Int>,
    val direction: SnakeDirection,
    val score: Int = 0,
    val isGameOver: Boolean = false
)
