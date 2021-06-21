package com.example.vehicleapp.di.shared

import android.content.Context
import com.example.vehicleapp.utils.CONSTANTS.LOGIN_FLAG
import com.example.vehicleapp.utils.CONSTANTS.LOGIN_USERNAME
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

/*
* @author Ali.Azaz
* */
object SharedStorage {

    fun setLogInUserName(sharedPrefImpl: SharedStorageBase, username: String) {
        sharedPrefImpl.put(LOGIN_USERNAME, username)
        sharedPrefImpl.put(LOGIN_FLAG, true)
    }

    fun getLogInUserName(sharedPrefImpl: SharedStorageBase): String {
        return sharedPrefImpl.get(LOGIN_USERNAME, StringUtils.EMPTY) as String
    }

    fun setLogOutUser(sharedPrefImpl: SharedStorageBase) {
        sharedPrefImpl.put(LOGIN_FLAG, false)
        sharedPrefImpl.put(LOGIN_USERNAME, StringUtils.EMPTY)
    }

    fun getLogFlag(sharedPrefImpl: SharedStorageBase): Boolean {
        return sharedPrefImpl.get(LOGIN_FLAG, false) as Boolean
    }

}