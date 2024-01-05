package com.belkanoid.dayplanner.presentation.screens.createEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.data.repository.DateConverter.getTimeInLong
import com.belkanoid.dayplanner.data.repository.DateConverter.parse
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class CreateEventViewModel @Inject constructor(
    private val repository: PlannerRepository
): ViewModel() {

    private val _state = MutableStateFlow<CreateEventState>(CreateEventState.Empty)
    val state = _state.asStateFlow()
    fun addEvent(event: Event) {
        if (event.name.isEmpty()) {
            _state.value = CreateEventState.Error("Название не должно быть пустым")
        }
        else if (event.startTime > event.endTime) {
            _state.value = CreateEventState.Error("Неправильный временной интервал")
        }else {
            viewModelScope.launch {
                repository.addEvent(event)
                _state.value = CreateEventState.Success("Было добавлено новое событие")
            }
        }
        _state.value = CreateEventState.Empty // такое решение из за того что stateFlow реализует distinctUntilChanged
    }

    fun convertTimeToTimestamp(date: String, hour: Int, minutes: Int): Long {
        val (year, month, day) = date.parse()
        val dateTime = LocalDateTime.of(year, month, day, hour, minutes)
        return dateTime.getTimeInLong()
    }
}



