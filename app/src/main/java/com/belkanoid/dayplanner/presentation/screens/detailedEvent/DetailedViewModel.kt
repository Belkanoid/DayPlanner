package com.belkanoid.dayplanner.presentation.screens.detailedEvent

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailedViewModel @Inject constructor(
    private val eventId: Int,
    private val repository: PlannerRepository
) : ViewModel() {

    val event: LiveData<Event> = repository.getEventById(eventId).asLiveData()

    fun edit(newEvent: Event) {
        viewModelScope.launch(NonCancellable) {
            repository.editEvent(event.value, newEvent)
        }
    }
}
