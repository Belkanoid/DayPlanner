package com.belkanoid.dayplanner.data.repository

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


object DateConverter {

    private const val DATE_PATTERN = "dd.MM.yyyy"
    fun getCurrentLocalDateTime(): LocalDateTime = LocalDateTime.now()

    fun Long.toSimpleDate(): String {
        return SimpleDateFormat(DATE_PATTERN).format(Date(this))
    }

    fun String.toLocalDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
        return LocalDate.parse(this, formatter)
    }

    fun String.parse(): List<Int> {
        val date = this.toLocalDate()
        return listOf(date.year, date.month.value, date.dayOfMonth)
    }

    fun Long.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    }

    fun Long.toHHmm(): String {
        return this.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    fun LocalDateTime.startOfDay(): Long {
        val localStartDateTime = this.with(LocalTime.MIN)
        return localStartDateTime.getTimeInLong()
    }


    fun LocalDateTime.endOfDay(): Long {
        val localEndDateTime = this.with(LocalTime.MAX)
        return localEndDateTime.getTimeInLong()
    }

    fun LocalDateTime.getTimeInLong(): Long {
        val zone = ZonedDateTime.of(this, ZoneId.systemDefault())
        return zone.toInstant().toEpochMilli()
    }
}