package com.example.capibara.domain.model

data class PetStats(
    val coins: Int,
    val moodScore: Int,
    val moodMax: Int,
    val todayDone: Int,
    val todayTotal: Int,
    val moodLabel: String
)
