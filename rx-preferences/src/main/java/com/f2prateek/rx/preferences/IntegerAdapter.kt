package com.f2prateek.rx.preferences

import android.content.SharedPreferences

object IntegerAdapter : Preference.Adapter<Int> {

    override fun get(key: String, preferences: SharedPreferences): Int {
        return preferences.getInt(key, 0)
    }

    override fun set(key: String, value: Int,
                     editor: SharedPreferences.Editor) {
        editor.putInt(key, value)
    }
}
