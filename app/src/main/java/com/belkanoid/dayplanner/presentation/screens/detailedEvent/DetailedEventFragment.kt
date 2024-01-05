package com.belkanoid.dayplanner.presentation.screens.detailedEvent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.databinding.FragmentDetailedEventBinding
import com.belkanoid.dayplanner.di.DetailedFragmentSubcomponent
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectComponent
import com.belkanoid.dayplanner.di.injectSubcomponent
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.listEvent.EventPlannerViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DetailedEventFragment : Fragment(R.layout.fragment_detailed_event) {


    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel by injectViewModel<DetailedViewModel> { factory }

    private val binding by injectBinding(FragmentDetailedEventBinding::bind)
    private val eventId by lazy { arguments?.getInt(EVENT_ID) ?: Event.UNDEFINED_ID }
    private val component: DetailedFragmentSubcomponent by injectSubcomponent { eventId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        viewModel.event
            .onEach {event ->
                with(binding) {

                }
            }
            .launchIn(lifecycleScope)

    }

    companion object {

        private const val EVENT_ID = "eventId"

        fun newInstance(eventId: Int) = DetailedEventFragment().apply {
            arguments = Bundle().apply {
                putInt(EVENT_ID, eventId)
            }
        }
    }
}


