package com.belkanoid.dayplanner.presentation.screen.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.belkanoid.dayplanner.R
import com.belkanoid.dayplanner.databinding.FragmentJsonDialogBinding
import com.belkanoid.dayplanner.di.injectBinding


class JsonDialogFragment : DialogFragment() {

    private val binding by injectBinding(FragmentJsonDialogBinding::inflate)
    private val type by lazy { arguments?.getSerializable(DIALOG_TYPE) }

    private var onClick: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).create()
        initializeCertainDialogType()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    private fun initializeCertainDialogType() {
        var title: String? = null
        var description: String? = null
        var additionalInfo: String? = null

        when (type) {
            DialogType.ACCESS_INTERNAL_STORAGE -> {
                title = resources.getString(R.string.dialog_access_storage_tittle)
                description = resources.getString(R.string.dialog_access_storage_description)
                additionalInfo = null
            }

            DialogType.ADD_EVENTS_FROM_JSON -> {
                title = resources.getString(R.string.dialog_add_event_title)
                description = resources.getString(R.string.dialog_add_event_description)
                additionalInfo =  resources.getString(R.string.event_json_example).trimIndent()
            }
        }
        fillDialog(title, description, additionalInfo)
    }

    private fun fillDialog(title: String?, description: String?, additionalInfo: String?) {
        binding.apply {
            tvDialogTitle.text = title
            tvDialogDescription.text = description
            tvAdditionalInfo.text = additionalInfo
            btnOkay.setOnClickListener {
                onClick?.invoke()
                dismiss()
            }
        }
    }

    companion object {
        enum class DialogType {
            ACCESS_INTERNAL_STORAGE, ADD_EVENTS_FROM_JSON
        }

        private const val DIALOG_TYPE = "DialogType"
        fun newInstance(type: DialogType, onClickBlock: () -> Unit) = JsonDialogFragment().apply {
            onClick = onClickBlock
            arguments = Bundle().apply {
                putSerializable(DIALOG_TYPE, type)
            }
        }
    }
}