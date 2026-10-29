package com.example.capibara.data.repository

import com.example.capibara.data.local.NewsDao
import com.example.capibara.data.local.toDomain
import com.example.capibara.data.local.toEntity
import com.example.capibara.domain.model.Article
import com.example.capibara.domain.model.NewsResponse
import com.example.capibara.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import java.util.concurrent.CancellationException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val newsDao: NewsDao
) : NewsRepository {

//    private val baseUrl = "https://gnews.io/api/v4/search?q=Медицина&lang=ru&apikey=${BuildConfig.GNEWS_API_KEY}"
    private val baseUrl = "http://j99514la.beget.tech/api/articles"

    override fun observeNews(): Flow<List<Article>> =
        newsDao.observeArticles().map { articles ->
            articles.map { it.toDomain() }
        }

    override fun observeArticle(id: String): Flow<Article?> =
        newsDao.observeArticle(id).map { it?.toDomain() }

    override suspend fun refreshNews(): Result<Unit> = try {
        val response = client.get(baseUrl)
        if (response.status != HttpStatusCode.OK) {
            Result.failure(Exception(response.bodyAsText()))
        } else {
            val body: NewsResponse = response.body()
            val cachedAt = System.currentTimeMillis()
            newsDao.replaceAll(
                body.articles.mapIndexed { index, article ->
                    article.toEntity(sortIndex = index, cachedAt = cachedAt)
                }
            )
            Result.success(Unit)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun refreshNewsIfStale(maxAgeMillis: Long): Result<Boolean> {
        val lastCachedAt = newsDao.lastCachedAt()
        val isFresh = lastCachedAt != null &&
            System.currentTimeMillis() - lastCachedAt <= maxAgeMillis
        return if (isFresh) {
            Result.success(false)
        } else {
            refreshNews().map { true }
        }
    }
}
