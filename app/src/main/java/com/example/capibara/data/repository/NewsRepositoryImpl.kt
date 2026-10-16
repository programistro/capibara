package com.example.capibara.data.repository

import androidx.compose.ui.res.stringResource
import com.example.capibara.R
import com.example.capibara.domain.model.NewsResponse
import com.example.capibara.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import com.example.capibara.BuildConfig
import com.google.android.gms.cloudmessaging.RegisterRequest
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : NewsRepository {
    private val baseUrl = "https://gnews.io/api/v4/search?q=Медицина&lang=ru&apikey=${BuildConfig.GNEWS_API_KEY}"
    private val json = Json {
        encodeDefaults = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    override suspend fun getNews(): Result<NewsResponse> {
        return try {
            val response = client.get(baseUrl)
            val responseJson = response.bodyAsText()
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body())
            } else {1
                Result.failure(Exception(responseJson))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}