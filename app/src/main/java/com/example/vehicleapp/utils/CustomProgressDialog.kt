package com.example.vehicleapp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.vehicleapp.R
import kotlinx.android.synthetic.main.custom_progress_dialog.view.*

/**
 * @author johncodeos on 6/17/2021.
 * @update ali.azaz
 */
object CustomProgressDialog {

    private lateinit var dialog: CustomDialog

    fun show(context: Context, title: CharSequence? = "Please Wait") {

        if (this::dialog.isInitialized && dialog.isShowing) return

        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)
        if (title != null) {
            view.cp_title.text = title
        }

        // Card Color
        view.cp_cardview.setCardBackgroundColor(
            ResourcesCompat.getColor(
                context.resources,
                R.color.purple_700,
                null
            )
        )

        // Progress Bar Color
        setColorFilter(
            view.cp_pbar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.teal_700, null)
        )

        // Text Color
        view.cp_title.setTextColor(Color.BLACK)

        dialog = CustomDialog(context)
        dialog.setContentView(view)
        dialog.show()
    }

    fun dismiss() {
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.purple_500)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}