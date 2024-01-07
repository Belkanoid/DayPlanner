package com.belkanoid.dayplanner.presentation.screens.createEvent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.toSimpleDate
import com.belkanoid.dayplanner.databinding.FragmentCreateEventBinding
import com.belkanoid.dayplanner.di.injectBinding
import com.belkanoid.dayplanner.di.injectComponent
import com.belkanoid.dayplanner.di.injectViewModel
import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.extensions.showSnackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class CreateEventFragment : Fragment(R.layout.fragment_create_event) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel by injectViewModel<CreateEventViewModel> { factory }

    private val component by injectComponent()
    private val binding by injectBinding(FragmentCreateEventBinding::bind)
    private lateinit var selectedDate: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        selectedDate =
            (arguments?.getLong(SELECTED_DATE) ?: System.currentTimeMillis()).toSimpleDate()
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                when (state) {
                    CreateEventState.Empty -> Unit
                    is CreateEventState.Error -> binding.showSnackbar(state.message, R.color.error)
                    is CreateEventState.Success -> {
                        binding.showSnackbar(state.message, R.color.success)
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            startHour.configure(23)
            startMin.configure(59)
            endHour.configure(23)
            endMin.configure(59)

            tvDate.apply {
                text = selectedDate
                setOnClickListener {

                }
            }

            btnCreate.setOnClickListener {
                val event = Event(
                    name = etTittle.text.toString(),
                    description = etDescription.text.toString(),
                    startTime = viewModel.convertTimeToTimestamp(
                        selectedDate,
                        startHour.value,
                        startMin.value
                    ),
                    endTime = viewModel.convertTimeToTimestamp(
                        selectedDate,
                        endHour.value,
                        endMin.value
                    ),
                )
                viewModel.addEvent(event)
            }
        }
    }

    companion object {
        private const val SELECTED_DATE = "selectedDate"
        fun newInstance(timestamp: Long) = CreateEventFragment().apply {
            arguments = Bundle().apply {
                putLong(SELECTED_DATE, timestamp)
            }
        }
    }
}