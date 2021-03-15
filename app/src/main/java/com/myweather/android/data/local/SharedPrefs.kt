/*
 * Copyright (c) Ashish , 2020.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.myweather.android.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import java.lang.ref.WeakReference

class SharedPrefs constructor(private val context: Context) {

    private fun setString(key: String, value: String) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        val e = prefs.edit()
        e.putString(key, value)
        e.apply()
    }

    fun getString(key: String): String {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, "") ?: ""
    }

    fun getString(key: String, def: String): String {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, def) ?: def
    }

    fun getInt(key: String): Int {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(key, 0)
    }

    fun setBoolean(key: String, value: Boolean) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        val e = prefs.edit()
        e.putBoolean(key, value)
        e.apply()
    }

    fun getBoolean(key: String): Boolean {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(key, false)
    }

    fun setInt(
        key: String,
        value: Int,
        contextWeakReference: WeakReference<Context?>
    ) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(
                contextWeakReference.get()
            )
        val e = prefs.edit()
        e.putInt(key, value)
        e.apply()
    }

    fun putInt(key: String?, value: Int) {
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        val e = prefs.edit()
        e.putInt(key, value)
        e.apply()
    }
}