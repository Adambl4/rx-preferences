package com.f2prateek.rx.preferences

import android.content.SharedPreferences

object BooleanAdapter : Preference.Adapter<Boolean> {

    override fun get(key: String, preferences: SharedPreferences): Boolean {
        return preferences.getBoolean(key, false)
    }

    override fun set(key: String, value: Boolean,
                     editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}
