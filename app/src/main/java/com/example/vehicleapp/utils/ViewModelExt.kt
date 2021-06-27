package com.example.vehicleapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun <T : ViewModel> Fragment.obtainViewModel(
    activity: Fragment,
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
) =
    ViewModelProvider(activity, viewModelFactory).get(viewModelClass)

fun <T : ViewModel> AppCompatActivity.obtainViewModel(
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
) =
    ViewModelProvider(this, viewModelFactory).get(viewModelClass)