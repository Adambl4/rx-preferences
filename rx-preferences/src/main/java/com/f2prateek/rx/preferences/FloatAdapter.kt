package com.f2prateek.rx.preferences

import android.content.SharedPreferences

internal class FloatAdapter : Preference.Adapter<Float> {

    override fun get(key: String, preferences: SharedPreferences): Float {
        return preferences.getFloat(key, 0f)
    }

    override fun set(key: String, value: Float,
                     editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }

    companion object {
        val INSTANCE = FloatAdapter()
    }
}
