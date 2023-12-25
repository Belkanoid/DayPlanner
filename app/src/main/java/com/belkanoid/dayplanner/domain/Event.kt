package com.belkanoid.dayplanner.domain

data class Event(
    val id: Int,
    val startTime: Long,
    val endTime: Long,
    val name: String,
    val description: String,
)

