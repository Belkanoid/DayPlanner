package com.belkanoid.dayplanner.domain

import kotlinx.coroutines.flow.Flow


interface PlannerRepository {

    fun getEventsFlowForDay(startDay: Long, endDay: Long): Flow<List<Event>>

    suspend fun addEvent(event: Event)

    suspend fun removeEvent(event: Event)

    suspend fun editEvent(event: Event)

    suspend fun loadEventsFromJson(jsonUrl: String)

    suspend fun formJsonFromEvents(): String

}