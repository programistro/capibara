package com.example.capibara.domain.model

import com.google.android.material.color.utilities.Score

enum class MoodLevel {
    CRYING,
    NORMAL,
    DEFAULT,
    HAPPY;

    companion object{
        fun from(score: Int): MoodLevel = when{
            score < 25 -> MoodLevel.CRYING
            score < 50 -> MoodLevel.NORMAL
            score < 75 -> MoodLevel.DEFAULT
            else -> MoodLevel.HAPPY
        }
    }
}