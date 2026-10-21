package com.example.capibara.data.repository

import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.repository.PetRepository
import javax.inject.Inject

class PetRepositoryStub @Inject constructor() : PetRepository {
    override fun getPetStats(): PetStats = PetStats(
        coins = 500,
        moodScore = 50,
        moodMax = 100,
        todayDone = 0,
        todayTotal = 0,
        moodLabel = "Капибара в норме"
    )
}
