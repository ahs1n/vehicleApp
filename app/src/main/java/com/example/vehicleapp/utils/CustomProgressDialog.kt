package com.example.vehicleapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.vehicleapp.R
import com.example.vehicleapp.databinding.CustomProgressDialogBinding

/**
 * @author johncodeos on 6/17/2021.
 * @update ali.azaz
 */
object CustomProgressDialog {

    private lateinit var dialog: CustomDialog

    fun show(context: Context, title: CharSequence? = "Please Wait") {

        if (this::dialog.isInitialized && dialog.isShowing) return

        val bi: CustomProgressDialogBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.custom_progress_dialog,
                null,
                false
            )


        if (title != null) {
            bi.cpTitle.text = title
        }

        // Card Color
        bi.cpCardview.setCardBackgroundColor(
            ResourcesCompat.getColor(
                context.resources,
                R.color.purple_700,
                null
            )
        )

        // Progress Bar Color
        setColorFilter(
            bi.cpPbar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.teal_700, null)
        )

        // Text Color
        bi.cpTitle.setTextColor(Color.BLACK)

        dialog = CustomDialog(context)
        dialog.setContentView(bi.root)
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