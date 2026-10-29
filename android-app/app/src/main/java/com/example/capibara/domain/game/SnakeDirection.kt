package com.example.capibara.domain.game

/** Направления движения змейки. */
enum class SnakeDirection(val dx: Int, val dy: Int) {
    RIGHT(1, 0),
    LEFT(-1, 0),
    UP(0, -1),
    DOWN(0, 1);

    /** Нельзя развернуться на 180 градусов. */
    fun isOpposite(other: SnakeDirection): Boolean =
        dx + other.dx == 0 && dy + other.dy == 0
}
