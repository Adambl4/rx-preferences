package com.f2prateek.rx.preferences

import android.content.SharedPreferences

internal class PointPreferenceAdapter : Preference.Adapter<Point> {
    override fun get(key: String, preferences: SharedPreferences): Point {
        val value = preferences.getString(key, null)!!
// Not called unless key is present.
        val parts = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size != 2) {
            throw IllegalStateException("Malformed point value: '$value'")
        }
        return Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]))
    }

    override fun set(key: String, value: Point,
                     editor: SharedPreferences.Editor) {
        editor.putString(key, value.x + "," + value.y)
    }
}
