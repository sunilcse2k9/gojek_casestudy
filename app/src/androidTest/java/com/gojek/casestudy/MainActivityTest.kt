package com.gojek.casestudy

import android.content.Context
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.gojek.casestudy.repository.DataRepository
import com.gojek.casestudy.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class MainActivityTest {
    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        val sharedPreferences: SharedPreferences = rule.activity.applicationContext
            .getSharedPreferences(DataRepository.PACKAGE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
    }

    @Test
    fun validateTitle() {
        onView(CoreMatchers.allOf(withText(R.string.repositories))).check(matches(isDisplayed()))
    }

    @Test
    fun validateMenuSortByNames() {
        Thread.sleep(1000)
        onView(CoreMatchers.allOf(withId(R.id.iv_menu))).perform(click())
        onView(CoreMatchers.allOf(withText(R.string.sort_name))).check(matches(isDisplayed()))
    }

    @Test
    fun validateMenuSortByStars() {
        Thread.sleep(1000)
        onView(CoreMatchers.allOf(withId(R.id.iv_menu))).perform(click())
        onView(CoreMatchers.allOf(withText(R.string.sort_stars))).check(matches(isDisplayed()))
    }
}