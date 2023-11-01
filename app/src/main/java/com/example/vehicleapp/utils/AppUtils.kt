package com.example.vehicleapp.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vehicleapp.MainApp
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.convertStringToUpperCase(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    val calStr = this.split(" ").map { it.lowercase(Locale.ENGLISH)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() } }
    return calStr.joinToString(separator = " ")
}

fun String.shortStringLength(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    var calStr = this
    if (this.length > 15)
        calStr = this.substring(0, 15).plus("...")
    return calStr
}

fun View.showSnackBar(
    message: String,
    action: String = "",
    actionListener: () -> Unit = {}
): Snackbar {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    if (action != "") {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.setAction(action) {
            actionListener()
            snackbar.dismiss()
        }
    }
    snackbar.show()
    return snackbar
}

fun EditText.hideKeyboard() {
    val keyboard: InputMethodManager? =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    keyboard?.hideSoftInputFromWindow(windowToken, 0)
}

fun <T : AppCompatActivity> AppCompatActivity.gotoActivity(targetActivityClass: Class<T>) {
    val intent = Intent(this, targetActivityClass)
    startActivity(intent)
}

fun <T : AppCompatActivity> Fragment.gotoActivityWithNoBackUp(targetActivityClass: Class<T>) {
    val intent = Intent(requireActivity(), targetActivityClass)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    requireActivity().finish()
    startActivity(intent)
}

fun isNetworkConnected(context: Context): Boolean {
    var result = false
    val connMgr =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connMgr.activeNetwork ?: return false
        val networkInfo = connMgr.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connMgr.run {
            connMgr.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }

    return result
}

fun String.toastUtil(): Toast {
    return Toast.makeText(MainApp.applicationContext(), this, Toast.LENGTH_LONG)
}

/*
* Date Utils
* */
fun Calendar.addDays(day: Int): String {
    this.add(Calendar.DAY_OF_YEAR, day)
    return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(this.time) //"dd-MM-yyyy HH:mm"
}

fun String.getCalendarDate(): Calendar {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = sdf.parse(this)
        date?.let {
            calendar.time = date
        }
        return calendar
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}