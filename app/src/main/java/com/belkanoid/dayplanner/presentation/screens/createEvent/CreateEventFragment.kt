package com.belkanoid.dayplanner.presentation.screens.createEvent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.databinding.FragmentCreateEventBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectComponent
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.eventPlanner.EventPlannerViewModel
import javax.inject.Inject


class CreateEventFragment : Fragment(R.layout.fragment_create_event) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel by injectViewModel<EventPlannerViewModel> { factory }

    private val component by injectComponent()
    private val binding by injectBinding(FragmentCreateEventBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
    }

    companion object {
        fun newInstance() = CreateEventFragment()
    }
}