package com.example.vehicleapp.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.vehicleapp.R


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

}