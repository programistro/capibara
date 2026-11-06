package com.example.capibara.presentation.games.minesweeper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.capibara.domain.minesweeper.MinesweeperEngine
import com.example.capibara.domain.minesweeper.model.MineCell
import com.example.capibara.domain.minesweeper.model.MineDifficulty
import kotlin.random.Random

/** Режим касания поля: открывать клетки или ставить флажки. */
enum class MineInputMode { REVEAL, FLAG }

/**
 * Состояние экрана сапёра.
 *
 * Движок [MinesweeperEngine] не знает про Compose, поэтому его решётка
 * копируется в наблюдаемый список: каждый `Revealed` превращается в
 * `CellUi`, и `LazyVerticalGrid` перерисовывает только изменённые клетки.
 *
 * Как в «Змейке» и «Динозавре», счёт игровых секунд ведёт UI
 * (`withFrameNanos`), а не сам класс.
 */
class MinesweeperState(
    initialDifficulty: MineDifficulty = MineDifficulty.EASY,
    private val random: Random = Random.Default,
) {

    var difficulty: MineDifficulty = initialDifficulty
        private set

    /** Плоский список клеток в порядке слева-направо, сверху-вниз. */
    val cells = mutableStateListOf<CellUi>()

    var isGameOver by mutableStateOf(false)
        private set

    var isVictory by mutableStateOf(false)
        private set

    var inputMode by mutableStateOf(MineInputMode.REVEAL)
        private set

    /** Открытые безопасные клетки / все безопасные клетки. */
    var openedSafe by mutableIntStateOf(0)
        private set

    var flagsLeft by mutableIntStateOf(0)
        private set

    var elapsedSeconds by mutableIntStateOf(0)
        private set

    private var engine = MinesweeperEngine(
        size = difficulty.size,
        totalMines = difficulty.mines,
        random = random,
    )

    init {
        publish()
    }

    val totalMines: Int get() = engine.totalMines
    val isFinished: Boolean get() = isGameOver || isVictory

    fun setDifficulty(value: MineDifficulty) {
        if (difficulty == value) return
        difficulty = value
        restart()
    }

    fun toggleInputMode() {
        inputMode = if (inputMode == MineInputMode.REVEAL) {
            MineInputMode.FLAG
        } else {
            MineInputMode.REVEAL
        }
    }

    fun restart() {
        engine = MinesweeperEngine(
            size = difficulty.size,
            totalMines = difficulty.mines,
            random = random,
        )
        isGameOver = false
        isVictory = false
        elapsedSeconds = 0
        publish()
    }

    /** Короткое касание: открыть или поставить флажок — зависит от [inputMode]. */
    fun tap(row: Int, column: Int) {
        if (inputMode == MineInputMode.FLAG) {
            flag(row, column)
            return
        }
        val cell = engine.cells.getOrNull(row)?.getOrNull(column) ?: return
        if (cell is MineCell.Revealed && cell.value > 0) {
            engine.revealAround(row, column)
        } else {
            engine.reveal(row, column)
        }
        afterChange()
    }

    /** Долгое касание всегда переключает флажок. */
    fun flag(row: Int, column: Int) {
        engine.toggleFlag(row, column)
        afterChange()
    }

    /**
     * Начислить время игры. Вызывается из UI каждый кадр с прошедшими
     * наносекундами; таймер стоит, пока не открыта первая клетка, и
     * замирает после конца партии.
     */
    fun tick(elapsedNanos: Long) {
        if (isFinished || !engine.minesPlaced) return
        accrued += elapsedNanos
        if (accrued >= NANOS_PER_SECOND) {
            elapsedSeconds += (accrued / NANOS_PER_SECOND).toInt()
            accrued %= NANOS_PER_SECOND
        }
    }

    private var accrued = 0L

    private fun afterChange() {
        isGameOver = engine.isGameOver
        isVictory = engine.isVictory
        publish()
    }

    private fun publish() {
        openedSafe = engine.openedSafeCount
        flagsLeft = (engine.totalMines - engine.flagCount).coerceAtLeast(0)
        val exploded = engine.explodedAt
        val flat = engine.cells.flatten().map { cell ->
            CellUi.from(cell, failCause = exploded == (cell.row to cell.column))
        }
        if (cells.size == flat.size) {
            flat.forEachIndexed { index, cell ->
                if (cells[index] != cell) cells[index] = cell
            }
        } else {
            cells.clear()
            cells.addAll(flat)
        }
    }

    private companion object {
        const val NANOS_PER_SECOND = 1_000_000_000L
    }
}

/**
 * Клетка для UI. Отдельный плоский тип вместо движковых `MineCell`: Compose
 * сравнивает значения по `equals`, и неизменившиеся клетки не перерисовываются.
 */
data class CellUi(
    val row: Int,
    val column: Int,
    val state: State,
    val value: Int = 0,
    /** Это та клетка-мина, на которую нажали (для крестика после взрыва). */
    val isFailCause: Boolean = false,
) {
    enum class State { CLOSED, FLAGGED, EMPTY, VALUE, MINE }

    companion object {
        fun from(cell: MineCell, failCause: Boolean = false): CellUi = when (cell) {
            is MineCell.Closed -> CellUi(cell.row, cell.column, State.CLOSED)
            is MineCell.Flagged -> CellUi(cell.row, cell.column, State.FLAGGED)
            is MineCell.Revealed -> when {
                cell.isMine -> CellUi(cell.row, cell.column, State.MINE, isFailCause = failCause)
                cell.value == 0 -> CellUi(cell.row, cell.column, State.EMPTY)
                else -> CellUi(cell.row, cell.column, State.VALUE, value = cell.value)
            }
        }
    }
}
