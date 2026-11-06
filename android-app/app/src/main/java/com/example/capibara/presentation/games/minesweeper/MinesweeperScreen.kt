package com.example.capibara.presentation.games.minesweeper

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import com.example.capibara.domain.minesweeper.model.MineDifficulty
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

private val MineCellClosed = Color(0xFFB7CFB9)
private val MineCellOpen = Color(0xFFF4F7F2)
private val MineBoardBorder = Color(0xFF8FA992)
private val MineChipBackground = Color(0xFFE8F0E4)

private val NumberColors = listOf(
    Color(0xFF388E3C),
    Color(0xFF1976D2),
    Color(0xFFD32F2F),
    Color(0xFF7B1FA2),
    Color(0xFFB26A00),
    Color(0xFF00838F),
    Color(0xFF37474F),
    Color(0xFF6D4C41),
)

/**
 * Экран «Сапёр».
 *
 * Короткое касание открывает клетку (или ставит флажок в режиме флажка),
 * долгое — всегда переключает флажок. Всё состояние в [MinesweeperState],
 * экран только размечает его и передаёт касания.
 */
@Composable
fun MinesweeperScreen(
    state: MinesweeperState,
    onToggleFullscreen: () -> Unit,
    fullscreen: Boolean,
    modifier: Modifier = Modifier,
) {
    // Таймер в UI, как у «Динозавра»: пока не открыта первая клетка и пока
    // партия жива. Перезапускается вместе с партией через разовый тик.
    LaunchedEffect(state.isFinished, state.openedSafe) {
        if (state.isFinished) return@LaunchedEffect
        var last = 0L
        while (true) {
            withFrameNanos { now ->
                if (last != 0L) state.tick(now - last)
                last = now
            }
            if (state.isFinished) break
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        MinesweeperHeader(
            state = state,
            fullscreen = fullscreen,
            onToggleFullscreen = onToggleFullscreen,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            MineBoard(state = state)

            if (state.isFinished) {
                MineResultOverlay(
                    victory = state.isVictory,
                    onRestart = state::restart,
                )
            }
        }

        MineFooter(state = state)
    }
}

@Composable
private fun MinesweeperHeader(
    state: MinesweeperState,
    fullscreen: Boolean,
    onToggleFullscreen: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (fullscreen) {
            IconButton(
                onClick = onToggleFullscreen,
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryGreen.copy(alpha = 0.18f), CircleShape)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Свернуть игру", tint = IconDark)
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Сапёр",
                fontSize = if (fullscreen) 22.sp else 26.sp,
                fontWeight = FontWeight.Bold,
                color = IconDark
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Флаги: ", fontSize = 15.sp, color = IconDark.copy(alpha = 0.6f))
                Text("${state.flagsLeft}", fontSize = 15.sp, color = IconDark)
                Text("   Время: ", fontSize = 15.sp, color = IconDark.copy(alpha = 0.6f))
                Text(formatTime(state.elapsedSeconds), fontSize = 15.sp, color = IconDark)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            MineDifficulty.entries.forEach { difficulty ->
                FilterChip(
                    selected = state.difficulty == difficulty,
                    onClick = { state.setDifficulty(difficulty) },
                    label = { Text(difficulty.title, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryGreen,
                        selectedLabelColor = IconDark,
                    ),
                )
            }
        }
    }
}

@Composable
private fun MineBoard(state: MinesweeperState) {
    val columns = state.difficulty.size.columns
    val rows = state.difficulty.size.rows
    // Поле всегда квадратное: одна клетка берёт долю исходной ширины, а
    // не «как влезет» — иначе текст в клетках прыгает при смене сложности.
    val sideModifier = Modifier
        .fillMaxWidth()
        .widthIn(max = 560.dp)
        .padding(vertical = 4.dp)

    Column(
        modifier = sideModifier
            .aspectRatio(columns / rows.toFloat())
            .clip(RoundedCornerShape(12.dp))
            .background(MineCellOpen)
            .border(2.dp, MineBoardBorder, RoundedCornerShape(12.dp)),
    ) {
        repeat(rows) { row ->
            Row(modifier = Modifier.weight(1f)) {
                repeat(columns) { column ->
                    val index = row * columns + column
                    MineCellView(
                        cell = state.cells.getOrNull(index),
                        columns = columns,
                        onTap = { state.tap(row, column) },
                        onLongPress = { state.flag(row, column) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun MineCellView(
    cell: CellUi?,
    columns: Int,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isClosed = cell == null || cell.state == CellUi.State.CLOSED || cell.state == CellUi.State.FLAGGED
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(if (isClosed) MineCellClosed else MineCellOpen)
            .then(
                if (isClosed) {
                    Modifier.border(1.dp, MineBoardBorder.copy(alpha = 0.55f), RoundedCornerShape(3.dp))
                } else {
                    Modifier
                }
            )
            .combinedClickableWithoutRipple(onClick = onTap, onLongClick = onLongPress),
        contentAlignment = Alignment.Center,
    ) {
        when (cell?.state) {
            CellUi.State.VALUE -> Text(
                text = cell.value.toString(),
                fontSize = (22 / columns.coerceAtLeast(1) + 7).sp,
                fontWeight = FontWeight.Black,
                color = NumberColors[(cell.value - 1).coerceIn(0, NumberColors.lastIndex)],
            )
            CellUi.State.MINE -> Canvas(modifier = Modifier.fillMaxSize(0.55f)) {
                drawMine()
                if (cell.isFailCause) drawLine(
                    color = Color(0xFFD32F2F),
                    start = Offset(size.width * 0.15f, size.height * 0.15f),
                    end = Offset(size.width * 0.85f, size.height * 0.85f),
                    strokeWidth = 3f,
                )
            }
            CellUi.State.FLAGGED -> Icon(
                imageVector = Icons.Filled.Flag,
                contentDescription = "Флажок",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.fillMaxSize(0.5f),
            )
            else -> Unit
        }
    }
}

private fun DrawScope.drawMine() {
    drawCircle(color = IconDark, radius = size.minDimension * 0.32f)
    repeat(8) { i ->
        val angle = Math.PI / 4 * i
        val dx = kotlin.math.cos(angle).toFloat() * size.minDimension * 0.5f
        val dy = kotlin.math.sin(angle).toFloat() * size.minDimension * 0.5f
        drawLine(
            color = IconDark,
            start = Offset(size.width / 2 - dx, size.height / 2 - dy),
            end = Offset(size.width / 2 + dx, size.height / 2 + dy),
            strokeWidth = 2.5f,
        )
    }
    drawCircle(color = Color.White.copy(alpha = 0.8f), radius = size.minDimension * 0.08f,
        center = Offset(size.width * 0.42f, size.height * 0.42f))
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.combinedClickableWithoutRipple(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
): Modifier {
    val interaction = remember { MutableInteractionSource() }
    return this.then(
        Modifier.combinedClickable(
            interactionSource = interaction,
            indication = null,
            onLongClick = onLongClick,
            onClick = onClick,
        )
    )
}

@Composable
private fun MineFooter(state: MinesweeperState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AssistChip(
            onClick = state::toggleInputMode,
            label = {
                Text(
                    if (state.inputMode == MineInputMode.REVEAL) "Открыть" else "Флажок",
                    fontSize = 13.sp,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = if (state.inputMode == MineInputMode.REVEAL) {
                        Icons.Filled.RemoveCircleOutline
                    } else {
                        Icons.Filled.Flag
                    },
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = PrimaryGreen,
                labelColor = IconDark,
                leadingIconContentColor = IconDark,
            ),
        )
        Text(
            text = "Открыто ${state.openedSafe} из ${state.difficulty.size.total - state.totalMines}",
            fontSize = 13.sp,
            color = IconDark.copy(alpha = 0.65f),
        )
        Text(
            text = "Тап — открыть, удержание — флажок",
            fontSize = 12.sp,
            color = IconDark.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MineResultOverlay(
    victory: Boolean,
    onRestart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (victory) "ПОЛЕ ОЧИЩЕНО" else "ВЗРЫВ",
            fontSize = 20.sp,
            letterSpacing = 4.sp,
            fontWeight = FontWeight.SemiBold,
            color = IconDark,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRestart,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = IconDark,
            ),
            modifier = Modifier.height(48.dp),
        ) {
            Text(text = "Ещё партию", fontSize = 17.sp)
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    return "%02d:%02d".format(minutes, seconds % 60)
}
