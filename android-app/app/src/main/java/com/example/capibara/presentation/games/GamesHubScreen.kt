package com.example.capibara.presentation.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

/** Какая игра выбрана на вкладке «Игры». */
enum class GameKind(val title: String, val subtitle: String) {
    SNAKE("Змейка", "Ешьте еду и зарабатывайте монеты"),
    DINO("Динозавр", "Прыгайте через кактусы");
}

/**
 * Экран списка игр. Две карточки-кнопки, нажатие открывает игру
 * на весь экран.
 */
@Composable
fun GamesHubScreen(
    onGameSelected: (GameKind) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Игры",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = IconDark
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Выберите игру — она откроется на весь экран",
            fontSize = 13.sp,
            color = IconDark.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        GameKind.entries.forEach { game ->
            GameCard(
                game = game,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onGameSelected(game) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
private fun GameCard(
    game: GameKind,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(96.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(PrimaryGreen.copy(alpha = 0.22f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = game.icon(),
                    contentDescription = null,
                    tint = IconDark,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = game.title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = IconDark
                )
                Text(
                    text = game.subtitle,
                    fontSize = 12.sp,
                    color = IconDark.copy(alpha = 0.6f)
                )
            }

            Icon(
                imageVector = Icons.Filled.Fullscreen,
                contentDescription = "Открыть",
                tint = IconDark.copy(alpha = 0.5f)
            )
        }
    }
}

private fun GameKind.icon(): ImageVector = when (this) {
    GameKind.SNAKE -> Icons.Filled.SportsEsports
    // Динозавру своей иконки в наборе нет — берём ближайшую по смыслу.
    GameKind.DINO -> Icons.Filled.Pets
}

@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
private fun GamesHubPreview() {
    CapibaraTheme {
        GamesHubScreen(onGameSelected = {})
    }
}
