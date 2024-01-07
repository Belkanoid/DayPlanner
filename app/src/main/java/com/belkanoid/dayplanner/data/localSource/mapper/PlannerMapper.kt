package com.belkanoid.dayplanner.data.localSource.mapper

import com.belkanoid.dayplanner.data.localSource.dto.EventDto
import com.belkanoid.dayplanner.domain.Event

object PlannerMapper {

    fun Event.mapToDbModel() : EventDto = EventDto(
        id = this.id,
        startTime = this.startTime,
        endTime = this.endTime,
        name = this.name,
        description = this.description,
    )

    fun EventDto.mapToEntity() : Event = Event(
        id = this.id,
        startTime = this.startTime.addPadToEnd(),
        endTime = this.endTime.addPadToEnd(),
        name = this.name,
        description = this.description,
    )

    fun List<EventDto>.mapToEntityList() = this.map { it.mapToEntity() }

    private fun Long.addPadToEnd(): Long {
        return this.toString().padEnd(13, '0').toLong()
    }

}