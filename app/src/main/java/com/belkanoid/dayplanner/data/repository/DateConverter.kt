package com.belkanoid.dayplanner.data.repository

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime


object DateConverter {
    fun getCurrentLocalDateTime(): LocalDateTime = LocalDateTime.now()

    fun Long.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    }

    fun LocalDateTime.startOfDay(): Long {
        val localStartDateTime = this.with(LocalTime.MIN)
        return getTimeInLong(localStartDateTime)
    }

    fun LocalDateTime.endOfDay(): Long {
        val localEndDateTime = this.with(LocalTime.MAX)
        return getTimeInLong(localEndDateTime)
    }

    private fun getTimeInLong(localDateTime: LocalDateTime): Long {
        val zone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
        return zone.toInstant().toEpochMilli()
    }




}