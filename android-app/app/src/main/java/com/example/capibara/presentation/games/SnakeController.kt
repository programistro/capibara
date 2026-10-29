package com.example.capibara.presentation.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.capibara.domain.game.SnakeDirection
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

/** Крестовина управления. */
@Composable
fun SnakeController(
    onDirectionChange: (SnakeDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DirectionButton(Icons.Default.KeyboardArrowUp, "Вверх") { onDirectionChange(SnakeDirection.UP) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            DirectionButton(Icons.Default.KeyboardArrowLeft, "Влево") { onDirectionChange(SnakeDirection.LEFT) }
            Spacer(modifier = Modifier.size(56.dp))
            DirectionButton(Icons.Default.KeyboardArrowRight, "Вправо") { onDirectionChange(SnakeDirection.RIGHT) }
        }
        DirectionButton(Icons.Default.KeyboardArrowDown, "Вниз") { onDirectionChange(SnakeDirection.DOWN) }
    }
}

@Composable
private fun DirectionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = PrimaryGreen,
            contentColor = IconDark
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(32.dp)
        )
    }
}
