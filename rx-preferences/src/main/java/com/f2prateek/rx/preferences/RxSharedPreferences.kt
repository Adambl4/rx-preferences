package com.f2prateek.rx.preferences

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.support.annotation.CheckResult

import java.util.Collections

import rx.Observable
import rx.Subscriber
import rx.functions.Action0
import rx.subscriptions.Subscriptions

import android.os.Build.VERSION_CODES.HONEYCOMB
import com.f2prateek.rx.preferences.Preconditions.checkNotNull

/**
 * A factory for reactive [Preference] objects.
 */
class RxSharedPreferences private constructor(private val preferences: SharedPreferences) {
    private val keyChanges: Observable<String>

    init {
        this.keyChanges = Observable.create(Observable.OnSubscribe<kotlin.String> { subscriber ->
            val listener = OnSharedPreferenceChangeListener { preferences, key -> subscriber.onNext(key) }

            preferences.registerOnSharedPreferenceChangeListener(listener)

            subscriber.add(Subscriptions.create { preferences.unregisterOnSharedPreferenceChangeListener(listener) })
        }).share()
    }

    /**
     * Create a boolean preference for `key` with a default of `defaultValue`.
     */
    @CheckResult
    @JvmOverloads fun getBoolean(key: String, defaultValue: Boolean? = DEFAULT_BOOLEAN): Preference<Boolean> {
        return Preference(preferences, key, defaultValue, BooleanAdapter, keyChanges)
    }

/*    *//**
     * Create an enum preference for `key`. Default is `null`.
     *//*
    @CheckResult
    fun <T : Enum<T>> getEnum(key: String,
                              enumClass: Class<T>): Preference<T> {
        return getEnum(key, null, enumClass)
    }

    *//**
     * Create an enum preference for `key` with a default of `defaultValue`.
     *//*
    @CheckResult
    fun <T : Enum<T>> getEnum(key: String, defaultValue: T?,
                              enumClass: Class<T>): Preference<T> {
        checkNotNull(key, "key == null")
        checkNotNull(enumClass, "enumClass == null")
        val adapter = EnumAdapter(enumClass)
        return Preference(preferences, key, defaultValue, adapter, keyChanges)
    }*/

    /**
     * Create a float preference for `key` with a default of `defaultValue`.
     */
    @CheckResult
    @JvmOverloads fun getFloat(key: String, defaultValue: Float? = DEFAULT_FLOAT): Preference<Float> {
        return Preference(preferences, key, defaultValue, FloatAdapter.INSTANCE, keyChanges)
    }

    /**
     * Create an integer preference for `key` with a default of `defaultValue`.
     */
    @CheckResult
    @JvmOverloads fun getInteger(key: String, defaultValue: Int? = DEFAULT_INTEGER): Preference<Int> {
        return Preference(preferences, key, defaultValue, IntegerAdapter, keyChanges)
    }

    /**
     * Create a long preference for `key` with a default of `defaultValue`.
     */
    @CheckResult
    @JvmOverloads fun getLong(key: String, defaultValue: Long? = DEFAULT_LONG): Preference<Long> {
        return Preference(preferences, key, defaultValue, LongAdapter, keyChanges)
    }

    /**
     * Create a preference of type `T` for `key`. Default is `null`.
     */
    @CheckResult
    fun <T> getObject(key: String, adapter: Preference.Adapter<T>): Preference<T> {
        return getObject(key, null, adapter)
    }

    /**
     * Create a preference for type `T` for `key` with a default of `defaultValue`.
     */
    @CheckResult
    fun <T> getObject(key: String, defaultValue: T?,
                      adapter: Preference.Adapter<T>): Preference<T> {
        return Preference(preferences, key, defaultValue, adapter, keyChanges)
    }

    /**
     * Create a string preference for `key` with a default of `defaultValue`.
     */
    @CheckResult
    @JvmOverloads fun getString(key: String, defaultValue: String? = null): Preference<String> {
        return Preference(preferences, key, defaultValue, StringAdapter, keyChanges)
    }

    /**
     * Create a string set preference for `key` with a default of `defaultValue`.
     */
    @TargetApi(HONEYCOMB)
    @CheckResult
    @JvmOverloads fun getStringSet(key: String,
                                   defaultValue: Set<String> = emptySet<String>()): Preference<Set<String>> {
        return Preference(preferences, key, defaultValue, StringSetAdapter, keyChanges)
    }

    companion object {
        private val DEFAULT_FLOAT = 0f
        private val DEFAULT_INTEGER = 0
        private val DEFAULT_BOOLEAN = false
        private val DEFAULT_LONG = 0L

        /**
         * Create an instance of [RxSharedPreferences] for `preferences`.
         */
        @CheckResult
        fun create(preferences: SharedPreferences): RxSharedPreferences {
            return RxSharedPreferences(preferences)
        }
    }
}
