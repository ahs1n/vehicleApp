package com.example.vehicleapp.di.shared

import com.example.vehicleapp.utils.CONSTANTS.LOGIN_FLAG
import com.example.vehicleapp.utils.CONSTANTS.LOGIN_USERNAME
import com.example.vehicleapp.utils.CONSTANTS.USER_LOCATION
import org.apache.commons.lang3.StringUtils

/*
* @author Ali.Azaz
* */
object SharedStorage {

    fun setLogInUserName(sharedPrefImpl: SharedStorageBase, username: String) {
        sharedPrefImpl.put(LOGIN_USERNAME, username)
        sharedPrefImpl.put(LOGIN_FLAG, true)
    }

    fun getLogInUserName(sharedPrefImpl: SharedStorageBase): String {
        return sharedPrefImpl[LOGIN_USERNAME, StringUtils.EMPTY] as String
    }

    fun setLogOutUser(sharedPrefImpl: SharedStorageBase) {
        sharedPrefImpl.put(LOGIN_FLAG, false)
        sharedPrefImpl.put(LOGIN_USERNAME, StringUtils.EMPTY)
    }

    fun getLogFlag(sharedPrefImpl: SharedStorageBase): Boolean {
        return sharedPrefImpl.get(LOGIN_FLAG, false) as Boolean
    }

    fun setUserLocation(sharedPrefImpl: SharedStorageBase, location: String) {
        sharedPrefImpl.put(USER_LOCATION, location)
        sharedPrefImpl.put(LOGIN_FLAG, true)
    }

    fun getUserLocation(sharedPrefImpl: SharedStorageBase): String {
        return sharedPrefImpl[USER_LOCATION, StringUtils.EMPTY] as String
    }

}