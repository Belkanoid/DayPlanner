package com.belkanoid.dayplanner.presentation.screens.listEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.data.repository.DateConverter
import com.belkanoid.dayplanner.data.repository.DateConverter.endOfDay
import com.belkanoid.dayplanner.data.repository.DateConverter.startOfDay
import com.belkanoid.dayplanner.data.repository.DateConverter.toLocalDateTime
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import com.belkanoid.dayplanner.domain.TimeSlot
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class EventPlannerViewModel @Inject constructor(
    private val repository: PlannerRepository,
) : ViewModel() {

    private var searchJob: Job? = null
    private val loadingState = MutableSharedFlow<EventPlannerState>()

    val state = repository.eventsForDay
        .map { EventPlannerState.Success(createTimeSlots(it)) as EventPlannerState}
        .mergeWith(loadingState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = EventPlannerState.Empty
        )

    init {
        val currentDateTime = DateConverter.getCurrentLocalDateTime()
        getEventsForDay(currentDateTime)
    }

    private fun createTimeSlots(events: List<Event>): List<TimeSlot> {
        val timeSlots = mutableListOf<TimeSlot>()

        (0..23).forEach {hour ->
            val eventsInInterval = events.filter {event ->
                val hourOfEvent = event.startTime.toLocalDateTime().hour
                hour <= hourOfEvent && hourOfEvent < hour+1
            }.sortedBy { it.startTime }
            timeSlots.add(TimeSlot("%02d:00".format(hour), eventsInInterval))
        }
        return timeSlots
    }

    fun getEventsForDay(date: LocalDateTime) {
        searchJob?.cancel("Started new events search")
        searchJob = viewModelScope.launch {
            loadingState.emit(EventPlannerState.Loading)
            repository.findEventsForDay(date.startOfDay(), date.endOfDay())
        }
    }

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> = merge(this, another)
}