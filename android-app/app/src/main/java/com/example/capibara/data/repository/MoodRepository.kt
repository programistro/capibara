package com.example.capibara.data.repository

import androidx.room.Dao
import com.example.capibara.data.local.MoodDao
import com.example.capibara.data.local.MoodEntity
import com.example.capibara.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val dao: MoodDao
) : MoodRepository {
    override fun observeScore(): Flow<Int> =
        dao.observeScore().map { it ?: START_MOOD }

    override suspend fun getScore(): Int =
        dao.getScore() ?: START_MOOD

    override suspend fun increase(amount: Int) {
        ensureRow()
        dao.increase(amount)
    }

    override suspend fun decrease(amount: Int) {
        ensureRow()
        dao.decrease(amount)
    }

    override suspend fun reset() {
        dao.setMood(MoodEntity(score = START_MOOD))
    }

    /** Ленивая инициализация: строки нет — создаём при первом обращении. */
    private suspend fun ensureRow() {
        if (dao.getScore() == null) {
            dao.setMood(MoodEntity(score = START_MOOD))
        }
    }

    companion object {
        const val START_MOOD = 50
    }
}