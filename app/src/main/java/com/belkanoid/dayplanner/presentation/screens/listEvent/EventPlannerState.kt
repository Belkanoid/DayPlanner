package com.belkanoid.dayplanner.presentation.screens.listEvent

import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.TimeSlot

sealed class EventPlannerState {

    data object Empty : EventPlannerState()

    data object Loading : EventPlannerState()

    data class Error(val message: String) : EventPlannerState()

    data class EventsFromJson(val events: List<Event>): EventPlannerState()

    data class Success(val timeSlots: List<TimeSlot>) : EventPlannerState()
}
