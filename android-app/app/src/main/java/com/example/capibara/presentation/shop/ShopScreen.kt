package com.example.capibara.presentation.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.R
import com.example.capibara.domain.model.BackgroundSprite
import com.example.capibara.domain.model.MoodLevel
import com.example.capibara.domain.model.ShopItem
import com.example.capibara.domain.model.ShopSlot
import com.example.capibara.domain.model.previewSprite
import com.example.capibara.presentation.common.GreenButton
import com.example.capibara.ui.theme.CapibaraTheme
import com.example.capibara.ui.theme.PrimaryGreen

/** Мягкий оранжевый фон выбранного скина. */
private val SkinSelectedBackground = Color(0xFFFFE0B2)

@Composable
fun ShopScreen(
    state: ShopUiState,
    mood: MoodLevel,
    onBuyClick: (ShopItem) -> Unit,
    onSelectClick: (ShopItem) -> Unit,
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
            .fillMaxSize()
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "На капибаре: ${mood.label()}",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            // fill = true обязателен: с `fill = false` сетка получает высоту
            // первого экрана и обрезает остальные предметы каталога.
            contentPadding = PaddingValues(bottom = 8.dp),
            modifier = Modifier.weight(1f, fill = true)
        ) {
            // Одна сетка с тремя секциями: одежда, фоны, бесплатные скины.
            listOf(Section.Outfit, Section.Backgrounds, Section.Skins).forEach { section ->
                val sectionItems = state.items.filter { section.matches(it) }
                if (sectionItems.isEmpty()) return@forEach
                item(key = "header_${section.name}", span = { GridItemSpan(maxLineSpan) }) {
                    SectionHeader(title = section.title, hint = section.hint)
                }
                items(sectionItems, key = { it.id }) { item ->
                    val equipped = when {
                        item.isBackground -> state.activeBackgroundId == item.id
                        item.isSkin -> state.activeSkinId == item.id
                        else -> item.id in state.selectedIds
                    }
                    ShopItemCard(
                        item = item,
                        owned = state.owns(item),
                        equipped = equipped,
                        canRemove = when {
                            // Снятый «Пляж» отдаёт сцену поляне: у неё
                            // «Снять», но жать нечего — другой фон не
                            // выбран, и кнопка заблокирована.
                            item.isBackground -> equipped && item.id in state.selectedIds
                            else -> equipped
                        },
                        selectedIds = state.accessorySelectedIds,
                        mood = mood,
                        onBuyClick = { onBuyClick(item) },
                        onToggleClick = { onSelectClick(item) }
                    )
                }
            }
        }
//        Spacer(modifier = Modifier.height(16.dp))
//        GreenButton(
//            text = "Назад",
//            onClick = onBackClick
//        )
    }
}

/** Секции витрины в порядке показа. */
private enum class Section(val title: String, val hint: String) {
    Outfit("Одежда", "Можно надеть несколько предметов сразу"),
    Backgrounds("Фон сцены", "Можно повесить один фон — одежда останется"),
    Skins("Скины", "Бесплатно. Скин надевается вместо одежды");

    fun matches(item: ShopItem): Boolean = when (this) {
        Outfit -> !item.isBackground && !item.isSkin
        Backgrounds -> item.isBackground
        Skins -> item.isSkin
    }
}

@Composable
private fun SectionHeader(title: String, hint: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = hint,
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun ShopItemCard(
    item: ShopItem,
    owned: Boolean,
    equipped: Boolean,
    canRemove: Boolean,
    mood: MoodLevel,
    selectedIds: Set<Int>,
    onBuyClick: () -> Unit,
    onToggleClick: () -> Unit
) {
    val isSelected = equipped
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            // Активный скин выделяем рамкой: у него, в отличие от одежды,
            // «надето» не видно по остальным карточкам.
            .then(
                if (isSelected && item.isSkin) {
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SkinSelectedBackground)
                        .padding(8.dp)
                } else Modifier
            )
    ) {
        // Превью показывает предмет в контексте надетого, но так, чтобы
        // карточка не повторила соседнюю: при надетых «цилиндр + пиджак»
        // любая третья вещь сворачивалась к этой же картинке, и вся витрина
        // показывала один и тот же спрайт. Логика — в previewSprite (SpritePreview.kt).
        if (item.isSkin) {
            // Скин — горизонтальная капибара без прозрачных полей;
            // превью 16:9, чтобы поместилась целиком.
            Image(
                painter = painterResource(id = previewSprite(item.id, emptySet(), mood)),
                contentDescription = item.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )
        } else if (item.isBackground) {
            // Фон — горизонтальная картинка, поэтому превью в рамке 16:9:
            // в квадрате 110 dp он обрезался бы до полоски.
            Image(
                painter = painterResource(id = BackgroundSprite.drawable(item.id)),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        } else {
            Image(
                painter = painterResource(
                    id = previewSprite(item.id, selectedIds, mood)
                ),
                contentDescription = item.name,
                modifier = Modifier.size(110.dp)
            )
        }
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
            text = when {
                !owned -> "${item.price} монет"
                isSelected -> "Надето"
                item.price == 0 -> "Бесплатно"
                else -> "Куплено"
            },
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        // enabled=true, кроме «Снять» у фона, который показан, но не
        // выбран явно (лесная поляна после снятия «Пляжа»). Compose
        // не реагировал на смену состояния кнопки, когда enabled был
        // константой, — поэтому цвет берём из состояния сами.
        Button(
            onClick = if (owned) onToggleClick else onBuyClick,
            enabled = canRemove || !isSelected,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color(0xFFE6E6E6) else PrimaryGreen,
                contentColor = if (isSelected) Color.Black else Color.White,
                disabledContainerColor = Color(0xFFF2F2F2),
                disabledContentColor = Color(0xFF9E9E9E)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = when {
                    !owned -> "Купить"
                    isSelected -> "Снять"
                    else -> "Надеть"
                },
                fontSize = 18.sp
            )
        }
    }
}

/**
 * Картинка для карточки магазина.
 *
 * Варианты по убыванию полезности:
 * 1. «Надетое + этот предмет» — идеально, но есть не для всех сочетаний;
 * 2. сам предмет надет в реале ([isSelected]) — рисуем реальный комплект;
 * 3. сам предмет один — карточки никогда не должны совпадать с чужими.
 *
 * Предмет обязан отличаться от остальных: при выборе между «комплектом
 * с этим предметом» и «одиночным предметом» сравниваем их ключи, а не
 * drawable id — в JVM-тестах и превью id может быть нулевым.
 */
/** Человекочитаемое название настроения для подписи над витриной. */
private fun MoodLevel.label(): String = when (this) {
    MoodLevel.CRYING -> "грустит"
    MoodLevel.NORMAL, MoodLevel.DEFAULT -> "в норме"
    MoodLevel.HAPPY -> "веселится"
    MoodLevel.SURPRISE -> "удивлена"
}

@Preview(showBackground = true, name = "Магазин: куплено и надето")
@Composable
private fun ShopScreenPreview() {
    CapibaraTheme {
        ShopScreen(
            state = ShopUiState(
                items = listOf(
                    ShopItem(1, "Шляпа", ShopSlot.HEAD, 100),
                    ShopItem(2, "Очки", ShopSlot.FACE, 150),
                    ShopItem(3, "Пиджак", ShopSlot.BODY, 80)
                ),
                purchasedIds = setOf(1, 2),
                selectedIds = setOf(1)
            ),
            mood = MoodLevel.HAPPY,
            onBuyClick = {},
            onSelectClick = {},
            onBackClick = {},
            onClearError = {}
        )
    }
}

@Preview(showBackground = true, name = "Магазин: ничего не куплено")
@Composable
private fun ShopScreenEmptyPreview() {
    CapibaraTheme {
        ShopScreen(
            state = ShopUiState(
                items = listOf(
                    ShopItem(4, "Шарф", ShopSlot.ACCESSORY, 120),
                    ShopItem(6, "Кепка", ShopSlot.HEAD, 90),
                    ShopItem(10, "Лесная поляна", ShopSlot.BACKGROUND, 300),
                    ShopItem(11, "Пляж", ShopSlot.BACKGROUND, 400),
                    ShopItem(13, "Апельсиновая капибара", ShopSlot.SKIN, 0)
                )
            ),
            mood = MoodLevel.CRYING,
            onBuyClick = {},
            onSelectClick = {},
            onBackClick = {},
            onClearError = {}
        )
    }
}
