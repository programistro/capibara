package com.example.capibara.domain.repository

import com.example.capibara.domain.model.NewsResponse

interface NewsRepository {
    suspend fun getNews(): Result<NewsResponse>
}