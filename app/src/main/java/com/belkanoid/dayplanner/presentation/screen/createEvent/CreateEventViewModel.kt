package com.belkanoid.dayplanner.presentation.screen.createEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.getTimeInLong
import com.belkanoid.dayplanner.data.repository.DateConverter.parse
import com.belkanoid.dayplanner.data.repository.ResourcesProvider
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class CreateEventViewModel @Inject constructor(
    private val repository: PlannerRepository,
    private val resourcesProvider: ResourcesProvider
): ViewModel() {

    private val _state = MutableStateFlow<CreateEventState>(CreateEventState.Empty)
    val state = _state.asStateFlow()

    fun addEvent(event: Event) {
        if (event.name.isEmpty()) {
            _state.value = CreateEventState.Error(message = resourcesProvider.getString(R.string.state_error_empty_tittle))
            return
        }

        if (event.startTime > event.endTime) {
            _state.value = CreateEventState.Error(message = resourcesProvider.getString(R.string.state_error_invalid_time_interval))
            return
        }

        viewModelScope.launch {
            repository.addEvent(event)
            _state.value = CreateEventState.Success(
                message = resourcesProvider.getString(R.string.state_success_new_event)
            )
        }

        _state.value = CreateEventState.Empty // такое решение из за того что stateFlow реализует distinctUntilChanged
    }

    fun convertTimeToTimestamp(date: String, hour: Int, minutes: Int): Long {
        val (year, month, day) = date.parse()
        val dateTime = LocalDateTime.of(year, month, day, hour, minutes)
        return dateTime.getTimeInLong()
    }
}



