package com.belkanoid.dayplanner.data.repository

import com.belkanoid.dayplanner.data.localSource.PlannerDao
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToDbModel
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToEntity
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToEntityList
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
     private val plannerDao: PlannerDao,
): PlannerRepository {

    private val _eventsForDay = MutableSharedFlow<List<Event>>()
    override val eventsForDay = _eventsForDay.asSharedFlow()

    override suspend fun findEventsForDay(startDay: Long, endDay: Long) =
        plannerDao.getEventFlowListForDay(startDay = startDay, endDay = endDay).collect{
            _eventsForDay.emit(it.mapToEntityList())
        }

    override fun getEventById(id: Int) = plannerDao.getEvent(id).map { it.mapToEntity() }

    override suspend fun addEvent(event: Event) = plannerDao.insertEvent(event.mapToDbModel())

    override suspend fun removeEvent(event: Event) = plannerDao.deleteEvent(event.id)
    override suspend fun editEvent(oldEvent: Event, newEvent: Event) {
        removeEvent(oldEvent)
        addEvent(newEvent)
    }

    override suspend fun loadEventsFromJson(jsonUrl: String) {

    }

    override suspend fun formJsonFromEvents(): String {
        return ""
    }

}