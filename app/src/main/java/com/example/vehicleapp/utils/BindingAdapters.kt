package com.example.vehicleapp.utils

import android.annotation.SuppressLint
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vehicleapp.R
import com.example.vehicleapp.model.Attendance
import com.validatorcrawler.aliazaz.utils.getString


object BindingAdapters : BaseObservable() {

    @BindingAdapter("loadGroupImage")
    @JvmStatic
    fun loadGroupImage(imageView: AppCompatImageView, url: String?) {
        if (!url.isNullOrEmpty()) {

            val circleProgress = CircularProgressDrawable(imageView.context)
            circleProgress.strokeWidth = 5f
            circleProgress.centerRadius = 40f
            circleProgress.start()

            Glide.with(imageView.context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(url)
                .placeholder(circleProgress)
                .error(R.drawable.loading)
                .into(imageView)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @BindingAdapter("loadStatusImage")
    @JvmStatic
    fun loadStatusImage(imageView: AppCompatImageView, flag: Boolean) {
        var drawable = R.drawable.ic_incomplete_star
        if (flag) drawable = R.drawable.ic_complete_star

        val circleProgress = CircularProgressDrawable(imageView.context)
        circleProgress.strokeWidth = 5f
        circleProgress.centerRadius = 40f
        circleProgress.start()

        Glide.with(imageView.context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load("")
            .placeholder(drawable)
            .into(imageView)
    }


    @BindingAdapter("loadShortString")
    @JvmStatic
    fun loadShortString(txt: AppCompatTextView, name: String?) {
        txt.text = name?.shortStringLength()?.convertStringToUpperCase()
    }

    @BindingAdapter("attendanceButtonText")
    @JvmStatic
    fun attendanceBtnTxt(btn: Button, attendance: Attendance?) {
        when {
            attendance == null -> btn.text = btn.context.getString(R.string.time_in)
            attendance.meter_out != null -> {
                btn.text = btn.context.getString(R.string.done)
                btn.isEnabled = false
                btn.setBackgroundColor(btn.resources.getColor(R.color.whiteOverlay))
            }
            else -> btn.text = btn.context.getString(R.string.time_out)
        }

    }

}