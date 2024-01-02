package com.belkanoid.dayplanner.presentation.screens.detailedEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.databinding.FragmentDetailedEventBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.eventPlanner.EventPlannerViewModel
import javax.inject.Inject

class DetailedEventFragment : Fragment(R.layout.fragment_detailed_event) {


    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel by injectViewModel<EventPlannerViewModel> { factory }

    private val binding by injectBinding(FragmentDetailedEventBinding::bind)


    companion object {

        private const val EVENT_ID = "eventId"
        fun newInstance(eventId: Int) = DetailedEventFragment().apply {
            arguments = Bundle().apply {
                putInt(EVENT_ID, eventId)
            }
        }
    }
}