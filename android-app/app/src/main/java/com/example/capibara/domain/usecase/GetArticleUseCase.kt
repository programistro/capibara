package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.Article
import com.example.capibara.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(id: String): Flow<Article?> = repository.observeArticle(id)
}
