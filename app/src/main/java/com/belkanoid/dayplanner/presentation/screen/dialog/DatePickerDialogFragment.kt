package com.belkanoid.dayplanner.presentation.screen.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.data.repository.DateConverter.getTimeInLong
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

class DatePickerDialogFragment : DialogFragment() {

    private var onDatePicked : ((timestamp: Long) -> Unit)? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                val pickedDate = LocalDateTime.of(year, month + 1, day, 0, 0)
                onDatePicked?.invoke(pickedDate.getTimeInLong())
            }

        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay,
        )
    }

    companion object {
        fun newInstance(onDatePickerBlock: (timestamp: Long) -> Unit) = DatePickerDialogFragment().apply{
            onDatePicked = onDatePickerBlock
        }
    }
}