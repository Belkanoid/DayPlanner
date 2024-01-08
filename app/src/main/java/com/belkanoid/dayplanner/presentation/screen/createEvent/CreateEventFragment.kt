package com.belkanoid.dayplanner.presentation.screen.createEvent

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
import com.belkanoid.dayplanner.presentation.extension.showErrorSnackbar
import com.belkanoid.dayplanner.presentation.extension.showSuccessSnackbar
import com.belkanoid.dayplanner.presentation.factory.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screen.dialog.DatePickerDialogFragment
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
                    is CreateEventState.Error -> {
                        binding.showErrorSnackbar(state.message)
                    }

                    is CreateEventState.Success -> {
                        binding.showSuccessSnackbar(state.message)
                        requireActivity().supportFragmentManager.popBackStack()
                    }

                    CreateEventState.Empty -> Unit
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
                    DatePickerDialogFragment.newInstance { timestamp ->
                        selectedDate = timestamp.toSimpleDate()
                        text = selectedDate
                    }.show(childFragmentManager, null)
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