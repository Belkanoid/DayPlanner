package com.belkanoid.dayplanner.domain

data class Event(
    val id: Int = UNDEFINED_ID,
    val startTime: Long,
    val endTime: Long,
    val name: String,
    val description: String,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

