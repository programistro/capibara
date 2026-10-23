package com.example.capibara.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capibara.domain.model.Article
import com.example.capibara.domain.model.Source

@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val lang: String,
    val sourceId: String,
    val sourceName: String,
    val sourceUrl: String,
    val sourceCountry: String,
    val sortIndex: Int,
    val cachedAt: Long
)

fun NewsArticleEntity.toDomain(): Article = Article(
    id = id,
    title = title,
    description = description,
    content = content,
    url = url,
    image = image,
    publishedAt = publishedAt,
    lang = lang,
    source = Source(
        id = sourceId,
        name = sourceName,
        url = sourceUrl,
        country = sourceCountry
    )
)

fun Article.toEntity(sortIndex: Int, cachedAt: Long): NewsArticleEntity = NewsArticleEntity(
    id = id,
    title = title,
    description = description,
    content = content,
    url = url,
    image = image,
    publishedAt = publishedAt,
    lang = lang,
    sourceId = source.id,
    sourceName = source.name,
    sourceUrl = source.url,
    sourceCountry = source.country,
    sortIndex = sortIndex,
    cachedAt = cachedAt
)
