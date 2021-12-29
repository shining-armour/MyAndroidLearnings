package com.example.affirmations

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    @get:Rule
    var activity = ActivityScenarioRule(MainActivity::class.java)


    // testing scroll to item no. 7
    @Test
    fun scroll_to_item() {
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions
                .scrollToPosition<RecyclerView.ViewHolder>(6))

        onView(withText(R.string.affirmation7))
            .check(matches(isDisplayed()))
    }
}