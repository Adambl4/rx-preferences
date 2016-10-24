package com.f2prateek.rx.preferences

import android.content.SharedPreferences
import android.support.annotation.CheckResult

import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

/**
 * A preference of type [T]. Instances can be created from [RxSharedPreferences].
 */
class Preference<T> internal constructor(private val preferences: SharedPreferences, private val key: String, private val defaultValue: T, private val adapter: Preference.Adapter<T>,
                                         keyChanges: Observable<String>) {
    private val values: Observable<T>

    init {
        this.values = keyChanges
                .filter { changedKey -> key == changedKey }
                .startWith("<init>") // Dummy value to trigger initial load.
                .onBackpressureLatest()
                .map { get() }
    }

    /**
     * The key for which this preference will store and retrieve values.
     */
    fun key(): String {
        return key
    }

    /**
     * The value used if none is stored. May be `null`.
     */
    fun defaultValue(): T? {
        return defaultValue
    }

    /**
     * Retrieve the current value for this preference. Returns [.defaultValue] if no value is
     * set.
     */
    fun get(): T? {
        if (!preferences.contains(key)) {
            return defaultValue
        }
        return adapter[key, preferences]
    }

    /**
     * Change this preference's stored value to `value`. A value of `null` will delete the
     * preference.
     */
    fun set(value: T?) {
        val editor = preferences.edit()
        if (value == null) {
            editor.remove(key)
        } else {
            adapter[key, value] = editor
        }
        editor.apply()
    }

    /**
     * Returns true if this preference has a stored value.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Delete the stored value for this preference, if any.
     */
    fun delete() {
        set(null)
    }

    /**
     * Observe changes to this preference. The current value or [.defaultValue] will be
     * emitted on first subscribe.
     */
    @CheckResult
    fun asObservable(): Observable<T> {
        return values
    }

    /**
     * An action which stores a new value for this preference. Passing `null` will delete the
     * preference.
     */
    @CheckResult
    fun asAction(): Action1<in T> {
        return Action1 { value -> set(value) }
    }

    /**
     * Stores and retrieves instances of `T` in [SharedPreferences].
     */
    interface Adapter<T> {
        /**
         * Retrieve the value for `key` from `preferences`.
         */
        operator fun get(key: String, preferences: SharedPreferences): T

        /**
         * Store non-null `value` for `key` in `editor`.
         *
         *
         * Note: Implementations **must not** call `commit()` or `apply()` on
         * `editor`.
         */
        operator fun set(key: String, value: T, editor: SharedPreferences.Editor)
    }
}
