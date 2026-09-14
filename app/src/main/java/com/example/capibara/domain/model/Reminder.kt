package com.example.capibara.domain.model

data class Reminder(
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val periodicity: String = Periodicity.DAILY
)

object Periodicity {
    const val DAILY = "Ежедневно"
    const val TWICE_A_DAY = "Дважды в день"
    const val WEEKLY = "Еженедельно"

    val all = listOf(DAILY, TWICE_A_DAY, WEEKLY)

    fun intervalMillis(periodicity: String): Long = when (periodicity) {
        TWICE_A_DAY -> 12 * android.text.format.DateUtils.HOUR_IN_MILLIS
        WEEKLY -> android.text.format.DateUtils.WEEK_IN_MILLIS
        else -> android.text.format.DateUtils.DAY_IN_MILLIS
    }
}
