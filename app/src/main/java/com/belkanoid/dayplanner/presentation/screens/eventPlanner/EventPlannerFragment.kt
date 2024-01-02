package com.belkanoid.dayplanner.presentation.screens.eventPlanner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.endOfDay
import com.belkanoid.dayplanner.data.repository.DateConverter.startOfDay
import com.belkanoid.dayplanner.databinding.FragmentEventPlanerBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectComponent
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.createEvent.CreateEventFragment
import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedEventFragment
import com.belkanoid.dayplanner.presentation.screens.eventPlanner.adapter.EventSlotAdapter
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

    private val listAdapter: EventSlotAdapter = EventSlotAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventsCalendar.adapter = listAdapter
        component.inject(this)
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                when (state) {
                    is EventPlannerState.Empty -> Unit
                    is EventPlannerState.Loading -> Unit
                    is EventPlannerState.Success -> listAdapter.submitList(state.timeSlots)
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            rvEvents.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                viewModel.getEventsForDay(
                    selectedDateTime.startOfDay(),
                    selectedDateTime.endOfDay()
                )
            }

            fabAddEvent.setOnClickListener {
                val fragment = CreateEventFragment.newInstance()
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
            }
        }
    }
}