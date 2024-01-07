package com.belkanoid.dayplanner.presentation.screen.detailedEvent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.toHHmm
import com.belkanoid.dayplanner.data.repository.DateConverter.toSimpleDate
import com.belkanoid.dayplanner.databinding.FragmentDetailedEventBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.domain.Event

class DetailedEventFragment : Fragment(R.layout.fragment_detailed_event) {

    private val binding by injectBinding(FragmentDetailedEventBinding::bind)
    private val event by lazy { arguments?.getParcelable<Event>(EVENT) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event?.let { event ->
            with(binding) {
                tvTittle.text = event.name
                tvDescription.text = event.description
                tvDate.text = event.startTime.toSimpleDate()
                tvStartTime.text = event.startTime.toHHmm()
                tvEndTime.text = event.endTime.toHHmm()
            }
        }

        binding.fabContinue.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {

        private const val EVENT = "event"
        fun newInstance(event: Event) = DetailedEventFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EVENT, event)
            }
        }
    }
}


