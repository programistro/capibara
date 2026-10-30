package com.example.capibara.domain.model

/**
 * Настроение капибары. Каждому соответствует свой набор спрайтов
 * в каталоге `cells/`: `happy`, `norm`, `cry`, `surprise`.
 */
enum class MoodLevel {
    CRYING,
    NORMAL,
    DEFAULT,
    HAPPY,

    /** Удивлённая. Наступает на пике активности (90+). */
    SURPRISE;

    companion object {
        fun from(score: Int): MoodLevel = when {
            score < 25 -> CRYING
            score < 50 -> NORMAL
            score < 75 -> DEFAULT
            score < 90 -> HAPPY
            // Пик активности — удивлённая капибара.
            else -> SURPRISE
        }

        fun fromText(score: Int): String = when {
            score < 25 -> "Капибара грустит \uD83D\uDE14"
            score < 50 -> "Капибара в норме \uD83D\uDE10"
            score < 75 -> "Капибара счастлива \uD83D\uDE04"
            score < 90 -> "Капибара веселится \uD83D\uDE04"
            else -> "Капибара удивлена \uD83D\uDE2E"
        }
    }
}
