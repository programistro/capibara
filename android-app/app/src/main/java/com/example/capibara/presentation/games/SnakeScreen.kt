package com.example.capibara.presentation.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
 *
 * [fullscreen] — игра занимает весь экран: шапка с капибарой и нижнее меню
 * скрыты, поле растягивается на максимум доступного места.
 */
@Composable
fun SnakeScreen(
    state: SnakeState,
    gameOver: GameOverResult?,
    onDirectionChange: (SnakeDirection) -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
    earnedCoins: Int = 0,
    fullscreen: Boolean = false,
    onToggleFullscreen: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (gameOver != null) {
            GameOverContent(
                result = gameOver,
                onRestart = onRestart,
                fullscreen = fullscreen,
                onToggleFullscreen = onToggleFullscreen
            )
        } else {
            PlayContent(
                state = state,
                earnedCoins = earnedCoins,
                onDirectionChange = onDirectionChange,
                fullscreen = fullscreen,
                onToggleFullscreen = onToggleFullscreen
            )
        }
    }
}

@Composable
private fun PlayContent(
    state: SnakeState,
    earnedCoins: Int,
    onDirectionChange: (SnakeDirection) -> Unit,
    fullscreen: Boolean,
    onToggleFullscreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            score = state.score,
            coins = earnedCoins,
            fullscreen = fullscreen,
            onToggleFullscreen = onToggleFullscreen
        )

        // В полноэкранном режиме поле забирает всё свободное место:
        // BoxWithConstraints + weight ограничивают его меньшей стороной,
        // чтобы оно осталось квадратным и крестовина влезла без прокрутки.
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            val side = if (maxWidth < maxHeight) maxWidth else maxHeight
            SnakeBoard(
                state = state,
                modifier = Modifier.size(side)
            )
        }

        SnakeController(onDirectionChange = onDirectionChange)
        Spacer(modifier = Modifier.height(if (fullscreen) 12.dp else 8.dp))
    }
}

@Composable
private fun Header(
    score: Int,
    coins: Int,
    fullscreen: Boolean,
    onToggleFullscreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Кнопка сворачивания слева, заголовок по центру, справа — пустышка
        // того же размера, чтобы текст не съезжал.
        if (fullscreen) {
            IconButton(
                onClick = onToggleFullscreen,
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryGreen.copy(alpha = 0.18f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Свернуть игру",
                    tint = IconDark
                )
            }
        } else {
            Spacer(modifier = Modifier.size(40.dp))
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Змейка",
                fontSize = if (fullscreen) 22.sp else 26.sp,
                fontWeight = FontWeight.Bold,
                color = IconDark
            )
            Text(
                text = "Очки: $score",
                fontSize = if (fullscreen) 16.sp else 18.sp,
                color = IconDark
            )
            if (coins > 0) {
                Text(
                    text = "+$coins монет",
                    fontSize = if (fullscreen) 13.sp else 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryGreen
                )
            }
        }

        if (fullscreen) {
            Spacer(modifier = Modifier.size(40.dp))
        } else {
            IconButton(
                onClick = onToggleFullscreen,
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryGreen.copy(alpha = 0.18f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Fullscreen,
                    contentDescription = "Развернуть на весь экран",
                    tint = IconDark
                )
            }
        }
    }
}

@Composable
private fun GameOverContent(
    result: GameOverResult,
    onRestart: () -> Unit,
    fullscreen: Boolean,
    onToggleFullscreen: () -> Unit
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
        if (fullscreen) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Нажмите × вверху, чтобы свернуть",
                fontSize = 14.sp,
                color = IconDark.copy(alpha = 0.6f)
            )
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

@Preview(showBackground = true, name = "Змейка: полный экран", widthDp = 400, heightDp = 800)
@Composable
private fun SnakeFullscreenPreview() {
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
            onRestart = {},
            fullscreen = true
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
