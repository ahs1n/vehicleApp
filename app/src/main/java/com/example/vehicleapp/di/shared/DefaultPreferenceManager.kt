package com.example.vehicleapp.di.shared

import android.content.SharedPreferences
import javax.inject.Inject

/*
* @author Mustufa.Ansari
* @update Ali.Azaz
* */

interface PrefManager {
    fun put(key: String?, objectValue: Any?)
    fun <T> get(key: String?, defaultObject: T?): T?
}

class DefaultPreferenceManager @Inject constructor(private val mSharedPreferences: SharedPreferences) :
    PrefManager {

    override fun put(key: String?, objectValue: Any?) {
        val editor = mSharedPreferences.edit()
        when (objectValue) {
            is String -> editor.putString(key, objectValue)
            is Int -> editor.putInt(key, objectValue)
            is Long -> editor.putLong(key, objectValue)
            is Boolean -> editor.putBoolean(key, objectValue)
            is Float -> editor.putFloat(key, objectValue)
            else -> editor.putString(key, objectValue.toString())
        }
        editor.apply()
    }

    override operator fun <T> get(key: String?, defaultObject: T?): T? {
        val item = when (defaultObject) {
            is String -> mSharedPreferences.getString(key, defaultObject)
            is Int -> mSharedPreferences.getInt(key, defaultObject)
            is Long -> mSharedPreferences.getLong(key, defaultObject)
            is Boolean -> mSharedPreferences.getBoolean(key, defaultObject)
            is Float -> mSharedPreferences.getFloat(key, defaultObject)
            else -> null
        }

        return item as T
    }

}