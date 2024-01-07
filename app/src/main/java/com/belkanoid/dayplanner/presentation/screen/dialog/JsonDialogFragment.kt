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

    private val title by lazy { arguments?.getString(DIALOG_TITLE) }
    private val description by lazy { arguments?.getString(DIALOG_DESCRIPTION) }
    private val additionalInfo by lazy { arguments?.getString(DIALOG_EXTRA_INFO) }
    private val type by lazy { arguments?.getSerializable(DIALOG_TYPE) }

    var onClickListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).create()
        createCertainDialogType()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    private fun createCertainDialogType() {
        var title = this@JsonDialogFragment.title
        var description = this@JsonDialogFragment.description
        var additionalInfo = this@JsonDialogFragment.additionalInfo
        when (type) {
            DialogType.CUSTOM -> Unit

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
        dialogType(title, description, additionalInfo)
    }

    private fun dialogType(title: String?, description: String?, additionalInfo: String?) {
        binding.apply {
            tvDialogTitle.text = title
            tvDialogDescription.text = description
            tvAdditionalInfo.text = additionalInfo
            btnOkay.setOnClickListener {
                onClickListener?.invoke()
                dismiss()
            }
        }
    }

    companion object {

        enum class DialogType {
            ACCESS_INTERNAL_STORAGE, ADD_EVENTS_FROM_JSON, CUSTOM
        }

        private const val DIALOG_TYPE = "DialogType"
        private const val DIALOG_TITLE: String = "DialogTitle"
        private const val DIALOG_DESCRIPTION: String = "DialogDescription"
        private const val DIALOG_EXTRA_INFO: String = "DialogExtraInfo"
        fun newInstance(
            type: DialogType,
            title: String = "",
            description: String = "",
            additionalInfo: String = "",
        ) = JsonDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(DIALOG_TYPE, type)
                putString(DIALOG_TITLE, title)
                putString(DIALOG_DESCRIPTION, description)
                putString(DIALOG_EXTRA_INFO, additionalInfo)
            }
        }
    }
}