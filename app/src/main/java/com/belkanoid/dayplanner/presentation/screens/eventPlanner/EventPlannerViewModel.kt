package com.belkanoid.dayplanner.presentation.screens.eventPlanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventPlannerViewModel @Inject constructor(
    private val repository: PlannerRepository
): ViewModel() {

    private val loadingState = MutableSharedFlow<EventPlannerState>()
    private val _state = MutableStateFlow<EventPlannerState>(EventPlannerState.Empty)
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun getEventsForDay() {
        searchJob?.cancel("Started new events search")

        searchJob = viewModelScope.launch {
            loadingState.emit(EventPlannerState.Loading)

            repository.getEventsFlowForDay(0, 0)
                .map {
                    when(it.isEmpty()) {
                        true -> _state.value = EventPlannerState.Empty
                        false -> _state.value = EventPlannerState.Success(it)
                    }
                }
                .mergeWith(loadingState)
        }
    }

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> = merge(this, another)
}