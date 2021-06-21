package com.example.vehicleapp.di.shared

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.vehicleapp.MainApp
import javax.inject.Inject
import javax.inject.Singleton

/*
* @author Mustufa.Ansari
* @update Ali.Azaz
* */
@Singleton
class SharedStorageBase @Inject constructor(private val mSharedPreferences: SharedPreferences) {

    fun put(key: String?, objectValue: Any?) {
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

    operator fun get(key: String?, defaultObject: Any?): Any? {
        return when (defaultObject) {
            is String -> mSharedPreferences.getString(key, defaultObject)
            is Int -> mSharedPreferences.getInt(key, defaultObject)
            is Long -> mSharedPreferences.getLong(key, defaultObject)
            is Boolean -> mSharedPreferences.getBoolean(key, defaultObject)
            is Float -> mSharedPreferences.getFloat(key, defaultObject)
            else -> null
        }
    }

}