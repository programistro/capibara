package com.example.capibara.domain.usecase

import android.util.Log
import com.example.capibara.domain.model.Article
import com.example.capibara.domain.model.NewsResponse
import com.example.capibara.domain.model.Reminder
import com.example.capibara.domain.repository.NewsRepository
import com.example.capibara.domain.repository.ReminderRepository
import javax.inject.Inject
import kotlin.collections.emptyList

class GetAllRemindersUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator suspend fun invoke(): List<Article> = repository.getNews().map { response -> response
        .articles }.getOrElse { error ->
            Log.e("cat", error.message ?: "error")
            emptyList()
        }
}
