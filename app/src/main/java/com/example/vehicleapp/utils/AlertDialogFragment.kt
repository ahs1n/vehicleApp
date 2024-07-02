package com.example.vehicleapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.vehicleapp.R

/**
 * @author AliAzazAlam on 6/14/2021.
 */
class AlertDialogFragment(
    val title: String,
    val positiveBtnTxt: String?,
    val negativeBtnTxt: String?,
    val callBack: CallBack?
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage(title)

        if (!positiveBtnTxt.isNullOrEmpty())
            alertDialog.setPositiveButton(positiveBtnTxt) { _, _ ->
                callBack?.actionYes()
                dismiss()
            }

        if (!negativeBtnTxt.isNullOrEmpty())
            alertDialog.setNegativeButton(negativeBtnTxt) { _, _ ->
                callBack?.actionNo()
                dismiss()
            }

        return alertDialog.create()
    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
    }

    companion object {
        val TAG = AlertDialogFragment::class.java.name
    }
}