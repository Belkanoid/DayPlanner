package com.belkanoid.dayplanner.presentation.screens.eventPlanner

import com.belkanoid.dayplanner.domain.Event

sealed class EventPlannerState {

    data object Empty : EventPlannerState()

    data object Loading : EventPlannerState()

    data class Success(val events: List<Event>) : EventPlannerState()
}
