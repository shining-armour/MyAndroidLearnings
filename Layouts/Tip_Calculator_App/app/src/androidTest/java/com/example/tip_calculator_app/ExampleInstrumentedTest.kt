package com.example.tip_calculator_app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_default_tip() {
        onView(withId(R.id.cost_of_service_et))
            .perform(typeText("100.00"), closeSoftKeyboard())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("15.00"))))
    }


    @Test
    fun calculate_20_percent_tip_round_up() {
        onView(withId(R.id.cost_of_service_et))
            .perform(typeText("100.00"), closeSoftKeyboard())
        onView(withId(R.id.option_twenty_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("20.00"))))
    }

    @Test
    fun calculate_10_percent_tip_no_rounding() {
        onView(withId(R.id.cost_of_service_et))
            .perform(typeText("125.00"), closeSoftKeyboard())
        onView(withId(R.id.option_ten_percent))
            .perform(click())
        onView(withId(R.id.round_up_switch))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("12.50"))))
    }
}