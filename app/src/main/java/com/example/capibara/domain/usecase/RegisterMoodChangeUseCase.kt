package com.example.capibara.domain.usecase

import com.example.capibara.domain.repository.MoodRepository
import javax.inject.Inject

class RegisterMoodChangeUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    suspend operator fun invoke(taken: Boolean){
        if(taken){
            moodRepository.increase(MOOD_PER_MISS)
        } else {
            moodRepository.decrease(MOOD_PER_MISS)
        }
    }

    companion object{
        const val MOOD_PER_TAKE = 5
        const val MOOD_PER_MISS = 10
    }
}