package com.belkanoid.dayplanner.domain

data class TimeSlot(
    val interval: String,
    val events: List<Event>
)