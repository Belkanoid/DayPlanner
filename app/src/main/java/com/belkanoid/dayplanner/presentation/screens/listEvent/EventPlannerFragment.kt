package com.belkanoid.dayplanner.presentation.screens.listEvent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.getTimeInLong
import com.belkanoid.dayplanner.databinding.FragmentEventPlanerBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectComponent
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.createEvent.CreateEventFragment
import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedEventFragment
import com.belkanoid.dayplanner.presentation.screens.listEvent.adapter.EventSlotAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import javax.inject.Inject

class EventPlannerFragment : Fragment(R.layout.fragment_event_planer) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel by injectViewModel<EventPlannerViewModel> { factory }

    private val binding by injectBinding(FragmentEventPlanerBinding::bind)
    private val component by injectComponent()

    private val slotAdapter: EventSlotAdapter = EventSlotAdapter()

    private var selectedTimestamp = System.currentTimeMillis()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvEvents.adapter = slotAdapter
            calendar.date = selectedTimestamp
        }
        slotAdapter.onEventClick = {event ->
            val fragment = DetailedEventFragment.newInstance(event.id)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
        component.inject(this)
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                when (state) {
                    is EventPlannerState.Empty -> Unit
                    is EventPlannerState.Loading -> Unit
                    is EventPlannerState.Success -> slotAdapter.submitList(state.timeSlots)
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                selectedTimestamp = selectedDateTime.getTimeInLong()
                viewModel.getEventsForDay(selectedDateTime)
            }

            fabAddEvent.setOnClickListener {
                val fragment = CreateEventFragment.newInstance(selectedTimestamp)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}