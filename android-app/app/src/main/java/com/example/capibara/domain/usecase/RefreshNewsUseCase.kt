package com.example.capibara.domain.usecase

import com.example.capibara.domain.repository.NewsRepository
import javax.inject.Inject

class RefreshNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(force: Boolean = false): Result<Boolean> =
        if (force) {
            repository.refreshNews().map { true }
        } else {
            repository.refreshNewsIfStale(DEFAULT_MAX_AGE_MILLIS)
        }

    companion object {
        const val DEFAULT_MAX_AGE_MILLIS = 15 * 60 * 1000L
    }
}
