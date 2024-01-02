package com.belkanoid.dayplanner.presentation.screens.createEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateEventViewModel @Inject constructor(
    private val repository: PlannerRepository
): ViewModel() {

    fun addEvent(event: Event) {
        viewModelScope.launch {
            repository.addEvent(event)
        }
    }
}