package com.example.capibara.domain.repository

import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    fun observeScore(): Flow<Int>
    suspend fun getScore(): Int
    suspend fun increase(amount: Int)
    suspend fun decrease(amount: Int)
    suspend fun reset()
}