package com.belkanoid.dayplanner.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


interface PlannerRepository {

    val eventsForDay: SharedFlow<List<Event>>

    suspend fun findEventsForDay(startDay: Long, endDay: Long)

    fun getEventById(id: Int): Flow<Event>

    suspend fun addEvent(event: Event)

    suspend fun removeEvent(event: Event)

    suspend fun editEvent(oldEvent: Event, newEvent: Event)

    suspend fun loadEventsFromJson(jsonUrl: String)

    suspend fun formJsonFromEvents(): String

}