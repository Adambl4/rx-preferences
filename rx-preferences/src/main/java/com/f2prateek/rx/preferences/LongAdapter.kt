package com.f2prateek.rx.preferences

import android.content.SharedPreferences

object LongAdapter : Preference.Adapter<Long> {

    override fun get(key: String, preferences: SharedPreferences): Long {
        return preferences.getLong(key, 0L)
    }

    override fun set(key: String, value: Long,
                     editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}
