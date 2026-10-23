package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_articles ORDER BY sortIndex ASC")
    fun observeArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE id = :id LIMIT 1")
    fun observeArticle(id: String): Flow<NewsArticleEntity?>

    @Upsert
    suspend fun upsertAll(articles: List<NewsArticleEntity>)

    @Query("DELETE FROM news_articles")
    suspend fun clear()

    @Transaction
    suspend fun replaceAll(articles: List<NewsArticleEntity>) {
        clear()
        upsertAll(articles)
    }

    @Query("SELECT MAX(cachedAt) FROM news_articles")
    suspend fun lastCachedAt(): Long?
}
