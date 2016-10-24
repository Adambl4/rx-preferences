package com.f2prateek.rx.preferences

import android.annotation.TargetApi
import android.content.SharedPreferences

import android.os.Build.VERSION_CODES.HONEYCOMB

@TargetApi(HONEYCOMB)
object StringSetAdapter : Preference.Adapter<Set<String>> {

    override fun get(key: String, preferences: SharedPreferences): Set<String> {
        return preferences.getStringSet(key, null)
    }

    override fun set(key: String, value: Set<String>,
                     editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value)
    }
}
