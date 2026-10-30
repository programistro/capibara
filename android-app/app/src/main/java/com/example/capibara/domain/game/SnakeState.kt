package com.example.capibara.domain.game

/**
 * Состояние игрового поля.
 *
 * [snake] — список клеток, голова всегда первая.
 * Координаты — пара (x, y) в диапазоне 0..[GameEngine.BOARD_SIZE]-1.
 *
 * [rewardCoins] — сколько монет получено за последнюю съеденную еду (0, если
 * сейчас ничего не съедено). По нему UI показывает всплывающую надпись «+5».
 */
data class SnakeState(
    val snake: List<Pair<Int, Int>>,
    val food: Pair<Int, Int>,
    val direction: SnakeDirection,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val rewardCoins: Int = 0
)
