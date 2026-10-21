package com.example.capibara.domain.usecase

import com.example.capibara.domain.model.PetStats
import com.example.capibara.domain.repository.PetRepository
import javax.inject.Inject

class GetPetStatsUseCase @Inject constructor(
    private val repository: PetRepository
) {
    operator fun invoke(): PetStats = repository.getPetStats()
}
