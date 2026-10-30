package com.example.capibara.presentation.games.dino

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.presentation.games.dino.model.CactusModel
import com.example.capibara.presentation.games.dino.model.CloudModel
import com.example.capibara.presentation.games.dino.model.DinoState
import com.example.capibara.presentation.games.dino.model.DinoWorld
import com.example.capibara.presentation.games.dino.model.EarthModel
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen

/** Цвета в стиле исходной игры: всё в оттенках серого. */
private val DinoInk = Color(0xFF535353)
private val DinoMuted = Color(0xFF9E9E9E)
private val DinoSky = Color(0xFFFAFAFA)

/**
 * Экран «Динозавра».
 *
 * Тап по полю — прыжок. После проигрыша тап перезапускает партию.
 * Игровой цикл крутится в `LaunchedEffect` через `withFrameNanos`,
 * поэтому анимация привязана к частоте обновления экрана.
 */
@Composable
fun DinoGameScreen(
    state: DinoGameState,
    onToggleFullscreen: () -> Unit,
    fullscreen: Boolean,
    modifier: Modifier = Modifier,
) {
    // Зеркало состояния в Compose: сам DinoGameState — обычный изменяемый
    // класс, и без этого UI не узнает ни о проигрыше, ни о рестарте.
    var frameTick by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(state.isGameOver) }
    var generation by remember { mutableIntStateOf(state.generation) }
    var score by remember { mutableIntStateOf(state.currentScore) }
    var highScore by remember { mutableIntStateOf(state.highScore) }

    // Ключ — номер партии: при рестарте эффект перезапускается заново.
    LaunchedEffect(generation) {
        while (true) {
            // Ждём кадр и шагаем состоянием. Цикл выходим, когда игра кончилась.
            withFrameNanos { _ ->
                state.update()
                frameTick++
                score = state.currentScore
                if (state.isGameOver) {
                    isGameOver = true
                    highScore = state.highScore
                }
            }
            if (isGameOver) break
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        DinoHeader(
            score = score,
            highScore = highScore,
            fullscreen = fullscreen,
            onToggleFullscreen = onToggleFullscreen
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(DinoSky)
                .clickable(
                    // Интерактивность без ripple: он мигал бы под каждым тапом.
                    indication = null,
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                ) {
                    if (isGameOver) {
                        state.restart()
                        // Сбрасываем локальное зеркало и запускаем цикл заново.
                        isGameOver = false
                        score = 0
                        generation = state.generation
                    } else {
                        state.jump()
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Читаем тикер, чтобы Canvas перерисовывался каждый кадр.
                @Suppress("UNUSED_EXPRESSION")
                frameTick

                // Мир должен знать ширину экрана — подгоняем перед отрисовкой.
                state.configure(size.width.toInt())

                drawEarth(state.earth)
                drawClouds(state.clouds)
                drawCacti(state.cacti)
                drawDino(state.dino)
            }

            if (isGameOver) {
                GameOverOverlay(
                    onRestart = {
                        state.restart()
                        isGameOver = false
                        score = 0
                        generation = state.generation
                    }
                )
            }
        }

        if (!isGameOver) {
            Text(
                text = "Нажмите на поле, чтобы прыгнуть",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                color = DinoMuted
            )
        }
    }
}

@Composable
private fun DinoHeader(
    score: Int,
    highScore: Int,
    fullscreen: Boolean,
    onToggleFullscreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
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
                text = "Динозавр",
                fontSize = if (fullscreen) 22.sp else 26.sp,
                fontWeight = FontWeight.Bold,
                color = IconDark
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Счёт: ", fontSize = 15.sp, color = DinoMuted)
                Text(
                    text = score.toString().padStart(5, '0'),
                    fontSize = 15.sp,
                    color = IconDark
                )
                Text(text = "   Рекорд: ", fontSize = 15.sp, color = DinoMuted)
                Text(
                    text = highScore.toString().padStart(5, '0'),
                    fontSize = 15.sp,
                    color = DinoMuted
                )
            }
        }

        if (!fullscreen) {
            IconButton(
                onClick = onToggleFullscreen,
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryGreen.copy(alpha = 0.18f), CircleShape)
            ) {
                Icon(
                    Icons.Filled.Fullscreen,
                    contentDescription = "Развернуть на весь экран",
                    tint = IconDark
                )
            }
        }
    }
}

@Composable
private fun GameOverOverlay(onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ИГРА ОКОНЧЕНА",
            fontSize = 20.sp,
            letterSpacing = 5.sp,
            fontWeight = FontWeight.SemiBold,
            color = DinoInk
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRestart,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = IconDark
            ),
            modifier = Modifier
                .height(48.dp)
                .widthIn(min = 180.dp)
        ) {
            Text(text = "Играть снова", fontSize = 17.sp)
        }
    }
}

// ---------- Отрисовка ----------

private fun DrawScope.drawEarth(blocks: List<EarthModel>) {
    drawLine(
        color = DinoInk,
        start = Offset(0f, DinoWorld.GROUND_Y),
        end = Offset(size.width, DinoWorld.GROUND_Y),
        strokeWidth = DinoWorld.GROUND_STROKE
    )

    blocks.forEach { block ->
        drawLine(
            color = DinoInk,
            start = Offset(block.xPos, DinoWorld.GROUND_Y + 20),
            end = Offset(block.xPos + block.size, DinoWorld.GROUND_Y + 20),
            strokeWidth = DinoWorld.GROUND_STROKE / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 40f), 0f)
        )
        drawLine(
            color = DinoInk,
            start = Offset(block.xPos, DinoWorld.GROUND_Y + 30),
            end = Offset(block.xPos + block.size, DinoWorld.GROUND_Y + 30),
            strokeWidth = DinoWorld.GROUND_STROKE / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 50f), 40f)
        )
    }
}

private fun DrawScope.drawClouds(clouds: List<CloudModel>) {
    // Путь облака одинаковый для всех — строим один раз на весь модуль.
    val template = DinoPaths.cloudPath
    clouds.forEach { cloud ->
        withTransform({
            translate(left = cloud.xPos.toFloat(), top = cloud.yPos.toFloat())
        }) {
            drawPath(path = template, color = DinoMuted, style = Stroke(width = 2f))
        }
    }
}

private fun DrawScope.drawCacti(cacti: List<CactusModel>) {
    cacti.forEach { cactus ->
        withTransform({
            // Порядок важен: сначала масштаб, потом перенос. Если переносить
            // в масштабированной системе, кактус уезжает вверх от земли.
            // Рисунок и хитбокс опираются на одну точку — yPos (низ).
            translate(
                left = cactus.xPos.toFloat(),
                top = cactus.yPos.toFloat() - DinoWorld.CACTUS_HEIGHT * cactus.scale
            )
            scale(cactus.scale, cactus.scale, pivot = Offset.Zero)
        }) {
            drawPath(path = cactus.path, color = DinoInk)
        }
    }
}

private fun DrawScope.drawDino(dino: DinoState) {
    withTransform({
        translate(
            left = dino.xPos,
            // Высота из той же константы, что и хитбокс, — рисунок
            // совпадает с зоной столкновения.
            top = dino.yPos - DinoWorld.DINO_HEIGHT
        )
    }) {
        drawPath(path = dino.path, color = DinoInk)
    }
}

/** Кэш путей — строить их на каждом кадре дорого. */
private object DinoPaths {
    val cloudPath = CloudPath()
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
private fun DinoGameScreenPreview() {
    CapibaraTheme {
        DinoGameScreen(
            state = remember { DinoGameState() },
            onToggleFullscreen = {},
            fullscreen = false
        )
    }
}
