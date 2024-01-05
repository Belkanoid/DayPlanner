package com.belkanoid.dayplanner.presentation.screens.listEvent

import com.belkanoid.dayplanner.domain.TimeSlot

sealed class EventPlannerState {

    data object Empty : EventPlannerState()

    data object Loading : EventPlannerState()

    data class Success(val timeSlots: List<TimeSlot>) : EventPlannerState()
}
