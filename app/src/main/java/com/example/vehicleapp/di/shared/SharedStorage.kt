package com.example.vehicleapp.di.shared

import android.content.Context
import com.example.vehicleapp.utils.CONSTANTS.LOGIN_FLAG
import com.example.vehicleapp.utils.CONSTANTS.LOGIN_USERNAME
import org.apache.commons.lang3.StringUtils

/*
* @author Ali.Azaz
* */
object SharedStorage : SharedStorageBase() {

    fun setLogInUserName(context: Context, username: String) {
        put(context, LOGIN_USERNAME, username)
        put(context, LOGIN_FLAG, true)
    }

    fun getLogInUserName(context: Context): String {
        return get(context, LOGIN_USERNAME, StringUtils.EMPTY) as String
    }

    fun setLogOutUser(context: Context) {
        put(context, LOGIN_FLAG, false)
        put(context, LOGIN_USERNAME, StringUtils.EMPTY)
    }

    fun getLogFlag(context: Context): Boolean {
        return get(context, LOGIN_FLAG, false) as Boolean
    }

}