package com.f2prateek.rx.preferences

import android.content.SharedPreferences

object StringAdapter : Preference.Adapter<String> {

    override fun get(key: String, preferences: SharedPreferences): String {
        return preferences.getString(key, null)
    }

    override fun set(key: String, value: String,
                     editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}
