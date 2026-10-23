package com.example.capibara.domain.repository

import com.example.capibara.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun observeNews(): Flow<List<Article>>
    fun observeArticle(id: String): Flow<Article?>
    suspend fun refreshNews(): Result<Unit>
    suspend fun refreshNewsIfStale(maxAgeMillis: Long): Result<Boolean>
}
