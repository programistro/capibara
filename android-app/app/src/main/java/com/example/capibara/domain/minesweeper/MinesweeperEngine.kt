package com.example.capibara.domain.minesweeper

import com.example.capibara.domain.minesweeper.model.BoardSize
import com.example.capibara.domain.minesweeper.model.MineCell
import kotlin.random.Random

/**
 * Правила сапёра в чистом виде: без Compose, корутин и Android.
 *
 * Мины расставляются при первом открытии клетки, а не заранее: первый клик
 * никогда не попадает на мину и в его окрестности 3×3 мин тоже нет. Так
 * сделано в исходной игры Minesweeper-j-compose (безопасный первый клик),
 * и так игроку не приходится начинать вслепую.
 *
 * [random] можно подменить в тестах, чтобы расстановка была предсказуемой.
 */
class MinesweeperEngine(
    val size: BoardSize,
    val totalMines: Int,
    private val random: Random = Random.Default,
) {

    init {
        require(totalMines in 1 until size.total) {
            "Мины должны помещаться на поле"
        }
    }

    /** Мин ещё нет — поле ждёт первого клика. */
    var minesPlaced: Boolean = false
        private set

    val cells: List<List<MineCell>> = List(size.rows) { row ->
        List(size.columns) { column -> MineCell.Closed(row, column) }
    }

    var isGameOver: Boolean = false
        private set

    var isVictory: Boolean = false
        private set

    val safeTotal: Int get() = size.total - totalMines

    val flagCount: Int
        get() = cells.sumOf { row -> row.count { it is MineCell.Flagged } }

    /** Сколько безопасных клеток уже открыто — для индикатора прогресса. */
    val openedSafeCount: Int
        get() = cells.sumOf { row -> row.count { it is MineCell.Revealed && !it.isMine } }

    private var mines: List<Pair<Int, Int>> = emptyList()

    /** Координаты мин после первого клика; пусто, пока мин нет. */
    val minePositions: Set<Pair<Int, Int>> get() = mines.toSet()

    /** Клетка, на которой взорвались; null, пока мина не тронута. */
    var explodedAt: Pair<Int, Int>? = null
        private set

    /**
     * Открыть клетку. Возвращает true, если состояние изменилось.
     *
     * Заранее расставленные флажки не отменяют открытие: игрок вправе
     * передумать. Сработала мина — открываем всё поле.
     */
    fun reveal(row: Int, column: Int): Boolean {
        if (isGameOver || isVictory) return false
        if (!inBounds(row, column)) return false
        if (cells[row][column] !is MineCell.Unrevealed) return false

        if (!minesPlaced) {
            placeMines(exceptRow = row, exceptColumn = column)
            minesPlaced = true
        }

        if (isMineAt(row, column)) {
            revealAll()
            explodedAt = row to column
            isGameOver = true
            return true
        }

        floodReveal(row, column)
        if (openedSafeCount == safeTotal) {
            isVictory = true
        }
        return true
    }

    /** Открыть вокруг числа ровно столько закрытых клеток, сколько флажков. */
    fun revealAround(row: Int, column: Int): Boolean {
        if (isGameOver || isVictory) return false
        val cell = cells.getOrNull(row)?.getOrNull(column) as? MineCell.Revealed ?: return false
        if (cell.isMine || cell.value == 0) return false

        val neighbours = neighbours(row, column)
        val flags = neighbours.count { (r, c) -> cells[r][c] is MineCell.Flagged }
        if (flags != cell.value) return false

        var changed = false
        for ((r, c) in neighbours) {
            if (cells[r][c] is MineCell.Revealed) continue
            if (reveal(r, c)) changed = true
            if (isGameOver) break
        }
        return changed
    }

    /** Поставить или снять флажок на закрытой клетке. */
    fun toggleFlag(row: Int, column: Int): Boolean {
        if (isGameOver || isVictory) return false
        if (!inBounds(row, column)) return false
        val cell = cells[row][column]
        val updated = when (cell) {
            is MineCell.Closed -> MineCell.Flagged(row, column)
            is MineCell.Flagged -> MineCell.Closed(row, column)
            is MineCell.Revealed -> return false
        }
        setCell(updated)
        return true
    }

    private fun placeMines(exceptRow: Int, exceptColumn: Int) {
        val forbidden = neighbours(exceptRow, exceptColumn) + (exceptRow to exceptColumn)
        val available = ArrayList<Pair<Int, Int>>(size.total)
        for (row in 0 until size.rows) {
            for (column in 0 until size.columns) {
                if ((row to column) !in forbidden) available += row to column
            }
        }
        require(available.size >= totalMines) {
            "Недостаточно места для мин вокруг первой открытой клетки"
        }
        available.shuffle(random)
        mines = available.take(totalMines)
    }

    private fun isMineAt(row: Int, column: Int): Boolean = (row to column) in mines

    /** Число мин вокруг. Для клетки-мины значение −1. */
    private fun valueAt(row: Int, column: Int): Int {
        if (isMineAt(row, column)) return -1
        return neighbours(row, column).count { (r, c) -> isMineAt(r, c) }
    }

    /**
     * Открывает клетку; если мин вокруг нет — соседей тоже, по очереди, чтобы
     * не рекурсировать на поле 15×15 вглубь стека.
     */
    private fun floodReveal(startRow: Int, startColumn: Int) {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(startRow to startColumn)
        while (queue.isNotEmpty()) {
            val (row, column) = queue.removeFirst()
            if (cells[row][column] is MineCell.Revealed) continue
            val value = valueAt(row, column)
            if (value < 0) continue
            setCell(MineCell.Revealed(row, column, value))
            if (value == 0) queue.addAll(neighbours(row, column))
        }
    }

    /** Показать всё поле после проигрыша: мины и ошибочные флажки. */
    private fun revealAll() {
        for (row in 0 until size.rows) {
            for (column in 0 until size.columns) {
                val cell = cells[row][column]
                if (cell is MineCell.Revealed) continue
                setCell(MineCell.Revealed(row, column, valueAt(row, column)))
            }
        }
    }

    private fun neighbours(row: Int, column: Int): List<Pair<Int, Int>> = buildList(8) {
        for (r in (row - 1)..(row + 1)) {
            for (c in (column - 1)..(column + 1)) {
                if (r == row && c == column) continue
                if (inBounds(r, c)) add(r to c)
            }
        }
    }

    private fun inBounds(row: Int, column: Int): Boolean =
        row in 0 until size.rows && column in 0 until size.columns

    private fun setCell(cell: MineCell) {
        (cells[cell.row] as MutableList<MineCell>)[cell.column] = cell
    }
}
