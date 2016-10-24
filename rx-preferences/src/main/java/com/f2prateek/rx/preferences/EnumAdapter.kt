package com.f2prateek.rx.preferences

import android.content.SharedPreferences

/*class EnumAdapter<T : Enum<T>> (private val enumClass: Class<T>) : Preference.Adapter<T> {

    override fun get(key: String, preferences: SharedPreferences): T {
        val value = preferences.getString(key, null)!!
        // Not called unless key is present.
        return Enum.valueOf<T>(enumClass, value)
    }

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, value.name)
    }
}*/
