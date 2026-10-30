package com.example.capibara.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.capibara.domain.model.Article
import com.example.capibara.presentation.common.MarkdownText
import com.example.capibara.domain.model.Source

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Новость") })
        }
    ) { paddingValues ->
        // Вертикальный скролл на случай длинного контента
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Карточка, содержащая всю информацию
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    // 1. Загрузка изображения через Coil
                    if (article.image.isNotEmpty()) {
                        AsyncImage(
                            model = article.image,
                            contentDescription = "Изображение новости",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop // Заполняет пространство срезом
                        )
                    }

                    // Контент внутри карточки под картинкой
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Основные текстовые поля
                        Text(text = article.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Дата публикации: ${article.publishedAt}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = article.description, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(12.dp))

                        // Контент приходит в Markdown — рендерим форматированно,
                        // а не сырым текстом с "#" и "**".
                        MarkdownText(
                            markdown = article.content,
                            modifier = Modifier.fillMaxWidth(),
                            textColor = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider() // Разделитель для технических данных
                        Spacer(modifier = Modifier.height(16.dp))

                        // Блок с метаданными из вложенного объекта Source
                        Text(text = "Источник: ${article.source.name} (${article.source.country})", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Ссылка на источник: ${article.source.url}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Оригинальный URL статьи: ${article.url}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "ID статьи: ${article.id} | Язык: ${article.lang}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsDetailDestination(viewModel: NewsDetailViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val current = state) {
        NewsDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        NewsDetailUiState.NotFound -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Новость не найдена")
            }
        }
        is NewsDetailUiState.Content -> {
            NewsDetailScreen(article = current.article)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsDetailScreenPreview() {
    val previewArticle = Article(
        id = "12345",
        title = "В Jetpack Compose завезли Type-Safe навигацию",
        description = "Разработчики Google значительно упростили передачу объектов между экранами.",
        content = "Теперь не нужно использовать громоздкий Parcelable или сериализовать объекты в JSON строку вручную. Достаточно пометить data class аннотацией @Serializable из библиотеки KotlinX Serialization, и навигация сможет принимать объект целиком.",
        url = "https://android.com",
        image = "https://android.com/images/social/android-master.png",
        publishedAt = "2026-10-23 12:00",
        lang = "ru",
        source = Source(
            id = "google-news",
            name = "Android Developers",
            url = "https://developer.android.com",
            country = "US"
        )
    )

    // Оборачиваем в тему вашего приложения (например, AppTheme), чтобы применились правильные цвета
    MaterialTheme {
        NewsDetailScreen(article = previewArticle)
    }
}
