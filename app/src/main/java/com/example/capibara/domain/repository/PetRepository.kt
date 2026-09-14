package com.example.capibara.domain.repository

import com.example.capibara.domain.model.PetStats

interface PetRepository {
    fun getPetStats(): PetStats
}
