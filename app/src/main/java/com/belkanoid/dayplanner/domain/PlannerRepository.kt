package com.belkanoid.dayplanner.domain

import kotlinx.coroutines.flow.SharedFlow


interface PlannerRepository {

    val eventsForDay: SharedFlow<List<Event>>

    suspend fun findEventsForDay(startDay: Long, endDay: Long)

    suspend fun addEvent(event: Event)

    suspend fun removeEvent(event: Event)

    suspend fun editEvent(event: Event)

    suspend fun loadEventsFromJson(jsonUrl: String)

    suspend fun formJsonFromEvents(): String

}