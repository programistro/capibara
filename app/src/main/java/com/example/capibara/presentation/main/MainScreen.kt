package com.example.capibara.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.capibara.R
import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.presentation.shop.ShopScreen
import com.example.capibara.presentation.shop.ShopUiState
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.MoodCardYellow
import com.example.capibara.ui.theme.MoodProgressYellow
import com.example.capibara.ui.theme.PrimaryGreen
import com.example.capibara.ui.theme.ScreenBackground
import com.example.capibara.ui.theme.StarYellow

@Composable
fun MainScreen(
    state: MainUiState,
    shopState: ShopUiState,
    formState: ReminderFormUiState,
    onTabSelected: (Int) -> Unit,
    onAddClick: () -> Unit,
    onTodayClick: () -> Unit,
    onBuyClick: (ShopItem) -> Unit,
    onShopBackClick: () -> Unit,
    onClearError: () -> Unit,
    onFormTitleChange: (String) -> Unit,
    onFormDateChange: (String) -> Unit,
    onFormTimeChange: (String) -> Unit,
    onFormPeriodicityChange: (String) -> Unit,
    onFormSaveClick: () -> Unit,
    onFormClose: () -> Unit
) {
    RequestNotificationPermission()

    val stats = state.petStats
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_forest),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.pet),
                contentDescription = "Капибара",
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.BottomCenter)
            )
            if (stats != null) {
                CoinPill(
                    coins = stats.coins,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                        .padding(12.dp)
                )
                MoodCard(
                    stats = stats,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(12.dp)
                        .width(130.dp)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 5.dp, start = 12.dp, end = 12.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            when {
                state.isFormOpen -> ReminderFormContent(
                    state = formState,
                    onTitleChange = onFormTitleChange,
                    onDateChange = onFormDateChange,
                    onTimeChange = onFormTimeChange,
                    onPeriodicityChange = onFormPeriodicityChange,
                    onSaveClick = onFormSaveClick,
                    onClose = onFormClose
                )
                state.selectedTab == MainTab.SHOP -> ShopScreen(
                    state = shopState,
                    onBuyClick = onBuyClick,
                    onBackClick = onShopBackClick,
                    onClearError = onClearError
                )
                state.selectedTab == MainTab.GAMES -> StubTab(
                    title = "Игры",
                    text = "Тетрис скоро появится"
                )
                state.selectedTab == MainTab.DOCTOR -> StubTab(
                    title = "Врач",
                    text = "Раздел скоро появится"
                )
                else -> HomeTab(
                    state = state,
                    onAddClick = onAddClick,
                    onTodayClick = onTodayClick
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        BottomMenu(
            selectedTab = state.selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.navigationBarsPadding()
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun RequestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
private fun HomeTab(
    state: MainUiState,
    onAddClick: () -> Unit,
    onTodayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Мои напоминания",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(PrimaryGreen, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Добавить",
                    tint = IconDark
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Дата:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = state.date,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onTodayClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Сегодня", fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (state.reminders.isEmpty()) {
            Text(
                text = "На ${state.date} лекарств нет",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.reminders) { reminder ->
                    Text(
                        text = "${reminder.time} — ${reminder.title}",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun StubTab(title: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CoinPill(coins: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = StarYellow,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = coins.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun MoodCard(stats: PetStats, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MoodCardYellow)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Настроение капибары",
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Сегодня: ${stats.todayDone}/${stats.todayTotal}",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            LinearProgressIndicator(
                progress = { 0f },
                modifier = Modifier.fillMaxWidth(),
                color = MoodProgressYellow,
                trackColor = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stats.moodLabel} \uD83D\uDE10",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MoodProgressYellow
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { stats.moodScore.toFloat() / stats.moodMax },
                modifier = Modifier.fillMaxWidth(),
                color = MoodProgressYellow,
                trackColor = Color.LightGray
            )
            Text(
                text = "${stats.moodScore}/${stats.moodMax}",
                fontSize = 12.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BottomMenu(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val icons: List<ImageVector> = listOf(
        Icons.Filled.SportsEsports,
        Icons.Filled.Checkroom,
        Icons.Filled.Medication,
        Icons.Filled.MedicalServices
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        icons.forEachIndexed { index, icon ->
            IconButton(
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .size(68.dp)
                    .background(
                        if (index == selectedTab) PrimaryGreen else PrimaryGreen.copy(alpha = 0.5f),
                        RoundedCornerShape(22.dp)
                    )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = IconDark,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    CapibaraTheme {
        MainScreen(
            state = MainUiState(
                date = "14.09.2026",
                petStats = PetStats(
                    coins = 500,
                    moodScore = 50,
                    moodMax = 100,
                    todayDone = 0,
                    todayTotal = 0,
                    moodLabel = "Капибара в норме"
                )
            ),
            shopState = ShopUiState(),
            formState = ReminderFormUiState(),
            onTabSelected = {},
            onAddClick = {},
            onTodayClick = {},
            onBuyClick = {},
            onShopBackClick = {},
            onFormTitleChange = {},
            onFormDateChange = {},
            onFormTimeChange = {},
            onFormPeriodicityChange = {},
            onFormSaveClick = {},
            onFormClose = {},
            onClearError = {}
        )
    }
}
