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

        fun fromText(score: Int): String = when{
            score < 25 -> "Капибара грустит \uD83D\uDE14"
            score < 50 -> "Капибара в норме \uD83D\uDE10"
            score < 75 -> "Капибара счастлива \uD83D\uDE04"
            else -> "Капибара в норме"
        }
    }
}