package com.example.capibara.presentation.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.capibara.domain.game.GameEngine
import com.example.capibara.domain.game.SnakeState
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

/**
 * Игровое поле. Клетки рисуются как Box'ы, сдвинутые через offset.
 * Поле квадратное: сторона берётся из доступной ширины через aspectRatio.
 */
@Composable
fun SnakeBoard(
    state: SnakeState,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
    ) {
        val boardSize = maxWidth
        val tileSize = boardSize / GameEngine.BOARD_SIZE

        Box(
            modifier = Modifier
                .size(boardSize)
                .background(PrimaryGreen, RoundedCornerShape(12.dp))
                .border(2.dp, IconDark, RoundedCornerShape(12.dp))
        )

        // Еда
        Box(
            modifier = Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(IconDark, CircleShape)
        )

        // Тело змейки. Голова рисуется темнее, чтобы её было видно.
        state.snake.forEachIndexed { index, cell ->
            Box(
                modifier = Modifier
                    .offset(x = tileSize * cell.first, y = tileSize * cell.second)
                    .size(tileSize)
                    .padding(1.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (index == 0) IconDark else IconDark.copy(alpha = 0.75f)
                    )
            )
        }
    }
}
