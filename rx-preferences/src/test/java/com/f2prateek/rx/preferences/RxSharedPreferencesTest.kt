package com.f2prateek.rx.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

import java.util.Collections

import android.preference.PreferenceManager.getDefaultSharedPreferences
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.fail

@RunWith(RobolectricTestRunner::class) //
@SuppressLint("CommitPrefEdits") //
//
class RxSharedPreferencesTest {
    private var rxPreferences: RxSharedPreferences? = null

    @Before
    fun setUp() {
        val preferences = getDefaultSharedPreferences(RuntimeEnvironment.application)
        preferences.edit().clear().commit()
        rxPreferences = RxSharedPreferences.create(preferences)
    }

    @Test
    fun createWithNullThrows() {
        try {
            RxSharedPreferences.create(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("preferences == null")
        }

    }

    @Test
    fun booleanNullKeyThrows() {
        try {
            rxPreferences!!.getBoolean(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getBoolean(null, false)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun enumNullKeyThrows() {
        try {
            rxPreferences!!.getEnum(null, Roshambo::class.java)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getEnum(null, Roshambo.ROCK, Roshambo::class.java)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun enumNullClassThrows() {
        try {
            rxPreferences!!.getEnum<Enum>("key", null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("enumClass == null")
        }

        try {
            rxPreferences!!.getEnum("key", Roshambo.ROCK, null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("enumClass == null")
        }

    }

    @Test
    fun floatNullKeyThrows() {
        try {
            rxPreferences!!.getFloat(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getFloat(null, 0f)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun integerNullKeyThrows() {
        try {
            rxPreferences!!.getInteger(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getInteger(null, 0)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun longNullKeyThrows() {
        try {
            rxPreferences!!.getLong(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getLong(null, 0L)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun objectNullKeyThrows() {
        try {
            rxPreferences!!.getObject(null, PointPreferenceAdapter())
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getObject(null, Point(1, 2), PointPreferenceAdapter())
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun objectNullAdapterThrows() {
        try {
            rxPreferences!!.getObject<Any>("key", null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("adapter == null")
        }

        try {
            rxPreferences!!.getObject("key", Point(1, 2), null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("adapter == null")
        }

    }

    @Test
    fun stringNullKeyThrows() {
        try {
            rxPreferences!!.getString(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getString(null, "default")
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }

    @Test
    fun stringSetNullKeyThrows() {
        try {
            rxPreferences!!.getStringSet(null)
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

        try {
            rxPreferences!!.getStringSet(null, emptySet<String>())
            fail()
        } catch (e: NullPointerException) {
            assertThat(e).hasMessage("key == null")
        }

    }
}
