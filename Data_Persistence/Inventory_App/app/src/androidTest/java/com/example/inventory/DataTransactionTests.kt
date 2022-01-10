package com.example.inventory

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataTransactionTests {

    @get: Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    private val testItemName = "Test Item"
    private val testItemPrice = "5.00"
    private val testItemInitialCount = "1"
    private val quantityHeader = "Quantity\nIn Stock"

    // Get currency symbol from resources
    private val targetContext: Context = ApplicationProvider.getApplicationContext()
    private val test: String =  targetContext.resources.getString(R.string.currency_symbol)

    @Test
    fun add_item_and_display_in_list(){
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.item_name)).perform(typeText(testItemName))
        onView(withId(R.id.item_price)).perform(typeText(testItemPrice))
        onView(withId(R.id.item_count)).perform(typeText(testItemInitialCount))
        onView(withId(R.id.save_action)).perform(click())

        // Make sure we are back in the list fragment by checking that a header is displayed
        onView(withText(quantityHeader)).check(matches(isDisplayed()))

        // Make sure item is displayed
        onView(withText(testItemName)).check(matches(isDisplayed()))
        onView(withText("$test$testItemPrice")).check(matches((isDisplayed())))
        onView(withText(testItemInitialCount)).check(matches(isDisplayed()))

    }

    @Test
    fun list_empty_after_item_deletion() {
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.item_name)).perform(typeText(testItemName))
        onView(withId(R.id.item_price)).perform(typeText(testItemPrice))
        onView(withId(R.id.item_count)).perform(typeText(testItemInitialCount))
        onView(withId(R.id.save_action)).perform(click())

        // Make sure we are back in the list fragment by checking that a header is displayed
        onView(withText(quantityHeader)).check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.delete_item)).perform(click())
        onView(withText("Yes")).perform(click())

        onView(withText(testItemName)).check(doesNotExist())
    }


}