package com.example.capibara.presentation.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.R
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.model.ShopSlot
import com.example.capibara.presentation.common.GreenButton
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.PrimaryGreen

@Composable
fun ShopScreen(
    state: ShopUiState,
    onBuyClick: (ShopItem) -> Unit,
    onBackClick: () -> Unit,
    onClearError:() -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            onClearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Магазин кастомизации",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(state.items) { item ->
                ShopItemCard(
                    item = item,
                    purchased = item.id in state.purchasedIds,
                    onBuyClick = { onBuyClick(item) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        GreenButton(
            text = "Назад",
            onClick = onBackClick
        )
    }
}

@Composable
private fun ShopItemCard(
    item: ShopItem,
    purchased: Boolean,
    onBuyClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = shopImage(item.id)),
            contentDescription = item.name,
            modifier = Modifier.size(110.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = item.slot,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${item.price} монет",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onBuyClick,
            enabled = !purchased,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = Color.White,
                disabledContainerColor = PrimaryGreen.copy(alpha = 0.5f),
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = if (purchased) "Куплено" else "Купить",
                fontSize = 18.sp
            )
        }
    }
}

private fun shopImage(itemId: Int): Int = when (itemId) {
    1 -> R.drawable.hat_1
    2 -> R.drawable.glasses_1
    3 -> R.drawable.jacket
    4 -> R.drawable.coat
    5 -> R.drawable.bow
    6 -> R.drawable.cap
    7 -> R.drawable.glasses_2
    8 -> R.drawable.headband
    9 -> R.drawable.scarf
    10 -> R.drawable.hat_2
    else -> R.drawable.pet
}

@Preview(showBackground = true)
@Composable
private fun ShopScreenPreview() {
    CapibaraTheme {
        ShopScreen(
            state = ShopUiState(
                items = listOf(
                    ShopItem(1, "Шляпа", ShopSlot.HEAD, 100),
                    ShopItem(2, "Очки", ShopSlot.FACE, 150)
                )
            ),
            onBuyClick = {},
            onBackClick = {},
            onClearError = {}
        )
    }
}
