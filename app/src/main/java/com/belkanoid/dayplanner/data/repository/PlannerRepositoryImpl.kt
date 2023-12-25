package com.belkanoid.dayplanner.data.repository

import com.belkanoid.dayplanner.data.localSource.PlannerDao
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToDbModel
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToEntityList
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
     private val plannerDao: PlannerDao,
): PlannerRepository {

    override fun getEventsFlowForDay(startDay: Long, endDay: Long) =
        plannerDao
            .getEventFlowListForDay(startDay = startDay, endDay = endDay)
            .map { it.mapToEntityList() }

    override suspend fun addEvent(event: Event) = plannerDao.insertEvent(event.mapToDbModel())

    override suspend fun removeEvent(event: Event) = plannerDao.deleteEvent(event.id)

    override suspend fun editEvent(event: Event) = addEvent(event)

    override suspend fun loadEventsFromJson(jsonUrl: String) {

    }

    override suspend fun formJsonFromEvents(): String {
        return ""
    }

}