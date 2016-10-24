package com.f2prateek.rx.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

import rx.Subscription
import rx.functions.Action1
import rx.observers.TestSubscriber

import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.f2prateek.rx.preferences.Roshambo.ROCK
import java.util.Collections.singleton
import org.assertj.core.api.Assertions.assertThat

@RunWith(RobolectricTestRunner::class) //
@SuppressLint("NewApi", "CommitPrefEdits") //
class PreferenceTest {
    private val pointAdapter = PointPreferenceAdapter()

    private var preferences: SharedPreferences? = null
    private var rxPreferences: RxSharedPreferences? = null

    @Before
    fun setUp() {
        preferences = getDefaultSharedPreferences(RuntimeEnvironment.application)
        preferences!!.edit().clear().commit()
        rxPreferences = RxSharedPreferences.create(preferences!!)
    }

    @Test
    fun key() {
        val preference = rxPreferences!!.getString("foo")
        assertThat(preference.key()).isEqualTo("foo")
    }

    @Test
    fun defaultDefaultValue() {
        assertThat(rxPreferences!!.getBoolean("foo1").defaultValue()).isFalse()
        assertThat<Roshambo>(rxPreferences!!.getEnum("foo2", Roshambo::class.java).defaultValue()).isNull()
        assertThat(rxPreferences!!.getFloat("foo3").defaultValue()).isZero()
        assertThat(rxPreferences!!.getInteger("foo4").defaultValue()).isZero()
        assertThat(rxPreferences!!.getLong("foo5").defaultValue()).isZero()
        assertThat(rxPreferences!!.getString("foo6").defaultValue()).isNull()
        assertThat(rxPreferences!!.getStringSet("foo7").defaultValue()).isEmpty()
        assertThat<Point>(rxPreferences!!.getObject("foo8", pointAdapter).defaultValue()).isNull()
    }

    @Test
    fun defaultValue() {
        assertThat(rxPreferences!!.getBoolean("foo1", false).defaultValue()).isEqualTo(false)
        assertThat<Roshambo>(rxPreferences!!.getEnum("foo2", ROCK, Roshambo::class.java).defaultValue()).isEqualTo(ROCK)
        assertThat(rxPreferences!!.getFloat("foo3", 1f).defaultValue()).isEqualTo(1f)
        assertThat(rxPreferences!!.getInteger("foo4", 1).defaultValue()).isEqualTo(1)
        assertThat(rxPreferences!!.getLong("foo5", 1L).defaultValue()).isEqualTo(1L)
        assertThat(rxPreferences!!.getString("foo6", "bar").defaultValue()).isEqualTo("bar")
        assertThat(rxPreferences!!.getStringSet("foo7", setOf("bar")).defaultValue()) //
                .isEqualTo(setOf("bar"))
        assertThat<Point>(rxPreferences!!.getObject("foo8", Point(1, 2), pointAdapter).defaultValue()) //
                .isEqualTo(Point(1, 2))
    }

    @Test
    fun getWithNoValueReturnsDefaultValue() {
        assertThat(rxPreferences!!.getBoolean("foo1", false).get()).isEqualTo(false)
        assertThat<Roshambo>(rxPreferences!!.getEnum("foo2", ROCK, Roshambo::class.java).get()).isEqualTo(ROCK)
        assertThat(rxPreferences!!.getFloat("foo3", 1f).get()).isEqualTo(1f)
        assertThat(rxPreferences!!.getInteger("foo4", 1).get()).isEqualTo(1)
        assertThat(rxPreferences!!.getLong("foo5", 1L).get()).isEqualTo(1L)
        assertThat(rxPreferences!!.getString("foo6", "bar").get()).isEqualTo("bar")
        assertThat(rxPreferences!!.getStringSet("foo7", setOf("bar")).get()) //
                .isEqualTo(setOf("bar"))
        assertThat<Point>(rxPreferences!!.getObject("foo8", Point(1, 2), pointAdapter).get()) //
                .isEqualTo(Point(1, 2))
    }

    @Test
    fun getWithStoredValue() {
        preferences!!.edit().putBoolean("foo1", false).commit()
        assertThat(rxPreferences!!.getBoolean("foo1").get()).isEqualTo(false)
        preferences!!.edit().putString("foo2", "ROCK").commit()
        assertThat<Roshambo>(rxPreferences!!.getEnum("foo2", Roshambo::class.java).get()).isEqualTo(ROCK)
        preferences!!.edit().putFloat("foo3", 1f).commit()
        assertThat(rxPreferences!!.getFloat("foo3").get()).isEqualTo(1f)
        preferences!!.edit().putInt("foo4", 1).commit()
        assertThat(rxPreferences!!.getInteger("foo4").get()).isEqualTo(1)
        preferences!!.edit().putLong("foo5", 1L).commit()
        assertThat(rxPreferences!!.getLong("foo5").get()).isEqualTo(1L)
        preferences!!.edit().putString("foo6", "bar").commit()
        assertThat(rxPreferences!!.getString("foo6").get()).isEqualTo("bar")
        preferences!!.edit().putStringSet("foo7", setOf("bar")).commit()
        assertThat(rxPreferences!!.getStringSet("foo7").get()).isEqualTo(setOf("bar"))
        preferences!!.edit().putString("foo8", "1,2").commit()
        assertThat<Point>(rxPreferences!!.getObject("foo8", pointAdapter).get()).isEqualTo(Point(1, 2))
    }

    @Test
    fun set() {
        rxPreferences!!.getBoolean("foo1").set(false)
        assertThat(preferences!!.getBoolean("foo1", true)).isFalse()
        rxPreferences!!.getEnum("foo2", Roshambo::class.java).set(ROCK)
        assertThat(preferences!!.getString("foo2", null)).isEqualTo("ROCK")
        rxPreferences!!.getFloat("foo3").set(1f)
        assertThat(preferences!!.getFloat("foo3", 0f)).isEqualTo(1f)
        rxPreferences!!.getInteger("foo4").set(1)
        assertThat(preferences!!.getInt("foo4", 0)).isEqualTo(1)
        rxPreferences!!.getLong("foo5").set(1L)
        assertThat(preferences!!.getLong("foo5", 0L)).isEqualTo(1L)
        rxPreferences!!.getString("foo6").set("bar")
        assertThat(preferences!!.getString("foo6", null)).isEqualTo("bar")
        rxPreferences!!.getStringSet("foo7").set(setOf("bar"))
        assertThat(preferences!!.getStringSet("foo7", null)).isEqualTo(setOf("bar"))
        rxPreferences!!.getObject("foo8", pointAdapter).set(Point(1, 2))
        assertThat(preferences!!.getString("foo8", null)).isEqualTo("1,2")
    }

    @Test
    fun setNullDeletes() {
        preferences!!.edit().putBoolean("foo1", true).commit()
        rxPreferences!!.getBoolean("foo1").set(null)
        assertThat(preferences!!.contains("foo1")).isFalse()

        preferences!!.edit().putString("foo2", "ROCK").commit()
        rxPreferences!!.getEnum("foo2", Roshambo::class.java).set(null)
        assertThat(preferences!!.contains("foo2")).isFalse()

        preferences!!.edit().putFloat("foo3", 1f).commit()
        rxPreferences!!.getFloat("foo3").set(null)
        assertThat(preferences!!.contains("foo3")).isFalse()

        preferences!!.edit().putInt("foo4", 1).commit()
        rxPreferences!!.getInteger("foo4").set(null)
        assertThat(preferences!!.contains("foo4")).isFalse()

        preferences!!.edit().putLong("foo5", 1L).commit()
        rxPreferences!!.getLong("foo5").set(null)
        assertThat(preferences!!.contains("foo5")).isFalse()

        preferences!!.edit().putString("foo6", "bar").commit()
        rxPreferences!!.getString("foo6").set(null)
        assertThat(preferences!!.contains("foo6")).isFalse()

        preferences!!.edit().putStringSet("foo7", setOf("bar")).commit()
        rxPreferences!!.getStringSet("foo7").set(null)
        assertThat(preferences!!.contains("foo7")).isFalse()

        preferences!!.edit().putString("foo8", "1,2").commit()
        rxPreferences!!.getObject("foo8", pointAdapter).set(null)
        assertThat(preferences!!.contains("foo8")).isFalse()
    }

    @Test
    fun isSet() {
        val preference = rxPreferences!!.getString("foo")

        assertThat(preferences!!.contains("foo")).isFalse()
        assertThat(preference.isSet).isFalse()

        preferences!!.edit().putString("foo", "2,3").commit()
        assertThat(preference.isSet).isTrue()

        preferences!!.edit().remove("foo").commit()
        assertThat(preference.isSet).isFalse()
    }

    @Test
    fun delete() {
        val preference = rxPreferences!!.getString("foo")

        preferences!!.edit().putBoolean("foo", true).commit()
        assertThat(preferences!!.contains("foo")).isTrue()

        preference.delete()
        assertThat(preferences!!.contains("foo")).isFalse()
    }

    @Test
    fun asObservable() {
        val preference = rxPreferences!!.getString("foo", "bar")

        val o = TestSubscriber<String>()
        val subscription = preference.asObservable().subscribe(o)
        o.assertValues("bar")

        preferences!!.edit().putString("foo", "baz").commit()
        o.assertValues("bar", "baz")

        preferences!!.edit().remove("foo").commit()
        o.assertValues("bar", "baz", "bar")

        subscription.unsubscribe()
        preferences!!.edit().putString("foo", "foo").commit()
        o.assertValues("bar", "baz", "bar")
    }

    @Test
    fun asObservableHonorsBackpressure() {
        val preference = rxPreferences!!.getString("foo", "bar")

        val o = TestSubscriber<String>(2) // Request only 2 values.
        preference.asObservable().subscribe(o)
        o.assertValues("bar")

        preferences!!.edit().putString("foo", "baz").commit()
        o.assertValues("bar", "baz")

        preferences!!.edit().putString("foo", "foo").commit()
        o.assertValues("bar", "baz") // No new item due to backpressure.

        o.requestMore(1)
        o.assertValues("bar", "baz", "foo")

        for (i in 0..999) {
            preferences!!.edit().putString("foo", "foo" + i).commit()
        }
        o.assertValues("bar", "baz", "foo") // No new items due to backpressure.

        o.requestMore(java.lang.Long.MAX_VALUE) // Request everything...
        o.assertValues("bar", "baz", "foo", "foo999") // ...but only get latest.
    }

    @Test
    fun asAction() {
        val preference = rxPreferences!!.getString("foo")
        val action = preference.asAction()

        action.call("bar")
        assertThat(preferences!!.getString("foo", null)).isEqualTo("bar")

        action.call("baz")
        assertThat(preferences!!.getString("foo", null)).isEqualTo("baz")

        action.call(null)
        assertThat(preferences!!.contains("foo")).isFalse()
    }
}
