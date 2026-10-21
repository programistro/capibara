package com.example.capibara.domain.model

data class Reminder(
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val periodicity: String = Periodicity.DAILY,
    val notifyBeforeMinutes: Int = DEFAULT_NOTIFY_BEFORE_MINUTES,
    val isTaken: Boolean = false,
    val lastNotifiedAt: Long? = null,
    val isHandled: Boolean = false
)

object Periodicity {
    const val DAILY = "Ежедневно"
    const val EVERY_12_HOURS = "Каждые 12 часов"
    const val WEEKLY = "Еженедельно"

    val all = listOf(DAILY, EVERY_12_HOURS, WEEKLY)

    fun intervalMillis(periodicity: String): Long = when (periodicity) {
        EVERY_12_HOURS -> 12 * android.text.format.DateUtils.HOUR_IN_MILLIS
        WEEKLY -> android.text.format.DateUtils.WEEK_IN_MILLIS
        else -> android.text.format.DateUtils.DAY_IN_MILLIS
    }
}

const val DEFAULT_NOTIFY_BEFORE_MINUTES = 180
