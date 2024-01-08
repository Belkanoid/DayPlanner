package com.belkanoid.dayplanner.presentation.screen.listEvent

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.belkanoid.dayplanner.presentation.extension.showErrorSnackbar
import com.belkanoid.dayplanner.presentation.factory.ViewModelFactory
import com.belkanoid.dayplanner.presentation.extension.showSnackbar
import com.belkanoid.dayplanner.presentation.extension.showSuccessSnackbar
import com.belkanoid.dayplanner.presentation.screen.createEvent.CreateEventFragment
import com.belkanoid.dayplanner.presentation.screen.detailedEvent.DetailedEventFragment
import com.belkanoid.dayplanner.presentation.screen.listEvent.adapter.EventSlotAdapter
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

    private val addJsonLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.addEventsFromJson(uri)
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.showSuccessSnackbar(resources.getString(R.string.permission_granted))
            } else {
                binding.showErrorSnackbar(resources.getString(R.string.permission_declined))
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvEvents.adapter = slotAdapter
        binding.calendar.date = selectedTimestamp
        slotAdapter.onEventClick = { event ->
            open(fragment = DetailedEventFragment.newInstance(event))
        }
        component.inject(this)

        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                when (state) {
                    is EventPlannerState.Success -> {
                        slotAdapter.submitList(state.timeSlots)
                    }

                    is EventPlannerState.EventsFromJson -> {
                        binding.showSuccessSnackbar(state.message)
                    }

                    is EventPlannerState.Error -> {
                        binding.showErrorSnackbar(state.message)
                    }

                    is EventPlannerState.Empty -> Unit
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

            fabAddEventFromJson.setOnClickListener {
                proceedWithPermission()
            }
            fabAddEvent.setOnClickListener {
                open(fragment = CreateEventFragment.newInstance(selectedTimestamp))
            }
        }
    }

    private fun proceedWithPermission() {
        val permission = ContextCompat
            .checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        viewModel.handlePermissionWithDialog(
            permission = permission,
            onGranted = { addJsonLauncher.launch(INTENT_TYPE) },
            onNotGranted = { permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) },
        ).show(childFragmentManager, null)
    }

    private fun open(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val INTENT_TYPE = "application/json"
    }
}