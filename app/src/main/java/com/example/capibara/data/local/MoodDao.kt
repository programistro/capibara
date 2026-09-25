package com.example.capibara.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Query("SELECT score FROM mood WHERE id = 0")
    fun observeScore(): Flow<Int?>

    @Query("SELECT score FROM mood WHERE id = 0")
    suspend fun getScore(): Int?

    @Query("SELECT max FROM mood WHERE id = 0")
    suspend fun getMax(): Int?

    @Upsert
    suspend fun setMood(mood: MoodEntity)

    @Query("UPDATE mood SET score = score + :amount WHERE id = 0")
    suspend fun increase(amount: Int)

    /** Уменьшает, но не ниже нуля — клампинг делает сам SQLite. */
    @Query("UPDATE mood SET score = MAX(0, score - :amount) WHERE id = 0")
    suspend fun decrease(amount: Int)
}

@Entity(tableName = "mood")
data class MoodEntity(
    @PrimaryKey val id: Int = 0,
    val score: Int,
    val max: Int = 100
)