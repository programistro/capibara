package com.example.capibara.presentation.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.domain.game.GameEngine
import com.example.capibara.domain.game.SnakeDirection
import com.example.capibara.domain.game.SnakeState
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

/**
 * Экран змейки. Состояние и колбэки приходят снаружи —
 * сам экран ничего не знает про ViewModel.
 */
@Composable
fun SnakeScreen(
    state: SnakeState,
    gameOver: GameOverResult?,
    onDirectionChange: (SnakeDirection) -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (gameOver != null) {
            GameOverContent(result = gameOver, onRestart = onRestart)
        } else {
            PlayContent(state = state, onDirectionChange = onDirectionChange)
        }
    }
}

@Composable
private fun PlayContent(
    state: SnakeState,
    onDirectionChange: (SnakeDirection) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Змейка",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = IconDark
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Очки: ${state.score}",
            fontSize = 18.sp,
            color = IconDark
        )
        Spacer(modifier = Modifier.height(8.dp))

        SnakeBoard(
            state = state,
            modifier = Modifier.widthIn(max = 250.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        SnakeController(onDirectionChange = onDirectionChange)

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun GameOverContent(
    result: GameOverResult,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Игра окончена",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = IconDark,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Очки: ${result.score}", fontSize = 20.sp, color = IconDark)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (result.coins > 0) {
                "Награда: ${result.coins} монет"
            } else {
                "Съешьте хоть одну еду, чтобы заработать монеты"
            },
            fontSize = 18.sp,
            color = IconDark,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRestart,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = IconDark
            ),
            modifier = Modifier
                .height(48.dp)
                .widthIn(min = 200.dp)
        ) {
            Text(text = "Играть снова", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true, name = "Змейка: игра", widthDp = 400, heightDp = 800)
@Composable
private fun SnakeScreenPreview() {
    CapibaraTheme {
        SnakeScreen(
            state = SnakeState(
                snake = listOf(16 to 16, 15 to 16, 14 to 16),
                food = 5 to 5,
                direction = SnakeDirection.RIGHT,
                score = 3
            ),
            gameOver = null,
            onDirectionChange = {},
            onRestart = {}
        )
    }
}

@Preview(showBackground = true, name = "Змейка: конец игры", widthDp = 400, heightDp = 800)
@Composable
private fun SnakeGameOverPreview() {
    CapibaraTheme {
        SnakeScreen(
            state = SnakeState(
                snake = listOf(16 to 16),
                food = 5 to 5,
                direction = SnakeDirection.RIGHT,
                score = 7,
                isGameOver = true
            ),
            gameOver = GameOverResult(score = 7, coins = 7 * 5),
            onDirectionChange = {},
            onRestart = {}
        )
    }
}
