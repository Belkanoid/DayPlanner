package com.belkanoid.dayplanner.domain

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


interface PlannerRepository {

    val eventsForDay: SharedFlow<List<Event>>

    suspend fun findEventsForDay(startDay: Long, endDay: Long)

    fun getEventById(id: Int): Flow<Event>

    suspend fun addEvent(event: Event)

    suspend fun removeEvent(event: Event)

    suspend fun editEvent(newEvent: Event)

    suspend fun loadEventsFromJson(uri: Uri): List<Event>

    suspend fun formEventsToJson(): String

}