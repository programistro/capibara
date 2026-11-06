package com.example.capibara.domain.minesweeper.model

/**
 * Клетка поля и её видимое состояние.
 *
 * Движок хранит только то, что нужно для правил: мина или число, открыта или
 * закрыта, стоит ли флажок. Оформление живёт в UI и сюда не протекает.
 */
sealed interface MineCell {

    val row: Int
    val column: Int

    sealed interface Unrevealed : MineCell

    data class Closed(override val row: Int, override val column: Int) : Unrevealed

    data class Flagged(override val row: Int, override val column: Int) : Unrevealed

    /** Открытая клетка. [value] — число мин вокруг, [-1] — мина. */
    data class Revealed(
        override val row: Int,
        override val column: Int,
        val value: Int,
    ) : MineCell {

        val isMine: Boolean get() = value < 0
    }
}

/** Ширина и высота поля в клетках. */
data class BoardSize(val rows: Int, val columns: Int) {

    init {
        require(rows > 0 && columns > 0) { "Поле должно быть непустым" }
    }

    val total: Int get() = rows * columns
}

/**
 * Уровень сложности.
 *
 * Числа взяты из оригинального Minesweeper-j-compose (GameDifficulty):
 * easy 10×10/10 мин, medium 16×16/35, hard 16 столбцов × 30 строк/80.
 * Поле вписывается в доступную площадь экрана, клетки не обрезаются.
 */
enum class MineDifficulty(
    val title: String,
    val subtitle: String,
    val size: BoardSize,
    val mines: Int,
) {
    EASY("Лёгкий", "10 × 10, 10 мин", BoardSize(10, 10), 10),
    MEDIUM("Средний", "16 × 16, 35 мин", BoardSize(16, 16), 35),
    HARD("Сложный", "16 × 30, 80 мин", BoardSize(30, 16), 80),
}

/** Сколько уже открыто из безопасных клеток. */
data class MineProgress(
    val openedSafe: Int,
    val safeTotal: Int,
) {
    val fraction: Float
        get() = if (safeTotal == 0) 0f else openedSafe.toFloat() / safeTotal
}
