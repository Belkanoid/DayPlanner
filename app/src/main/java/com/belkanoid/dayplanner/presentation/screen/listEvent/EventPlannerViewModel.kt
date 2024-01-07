package com.belkanoid.dayplanner.presentation.screen.listEvent

import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter
import com.belkanoid.dayplanner.data.repository.DateConverter.endOfDay
import com.belkanoid.dayplanner.data.repository.DateConverter.startOfDay
import com.belkanoid.dayplanner.data.repository.DateConverter.toLocalDateTime
import com.belkanoid.dayplanner.data.repository.ResourcesProvider
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.domain.PlannerRepository
import com.belkanoid.dayplanner.domain.TimeSlot
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import com.belkanoid.dayplanner.presentation.extension.mergeWith
import com.belkanoid.dayplanner.presentation.screen.dialog.JsonDialogFragment
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import com.belkanoid.dayplanner.presentation.screen.dialog.JsonDialogFragment.Companion.DialogType

class EventPlannerViewModel @Inject constructor(
    private val repository: PlannerRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private var searchJob: Job? = null
    private val loadingState = MutableSharedFlow<EventPlannerState>()

    val state = repository.eventsForDay
        .map { EventPlannerState.Success(createTimeSlots(it)) as EventPlannerState }
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

        (0..23).forEach { hour ->
            val eventsInInterval = events.filter { event ->
                val hourOfEvent = event.startTime.toLocalDateTime().hour
                hour <= hourOfEvent && hourOfEvent < hour + 1
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

    fun addEventsFromJson(uri: Uri?) {
        viewModelScope.launch {
            if (uri == null) {
                loadingState.emit(EventPlannerState.Error(
                    message = resourcesProvider.getString(R.string.state_error_something_goes_wrong))
                )
                return@launch
            }
            if (uri.path?.contains(".json") == false) {
                loadingState.emit(EventPlannerState.Error(
                    message = resourcesProvider.getString(R.string.state_error_not_json)
                ))
                return@launch
            }
            val events = repository.loadEventsFromJson(uri)
            loadingState.emit(EventPlannerState.EventsFromJson(events))
        }

    }

    fun handlePermissionWithDialog(
        permission: Int,
        onGranted: () -> Unit,
        onNotGranted: () -> Unit
    ): JsonDialogFragment {
        return if (permission == PackageManager.PERMISSION_GRANTED) {
            createDialog(DialogType.ADD_EVENTS_FROM_JSON) { onGranted() }
        } else {
            createDialog(DialogType.ACCESS_INTERNAL_STORAGE) { onNotGranted() }
        }
    }

    private fun createDialog(
        type: DialogType,
        onClick: () -> Unit
    ): JsonDialogFragment =
        JsonDialogFragment.newInstance(type = type).apply { onClickListener = onClick }


}
