package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun explicit_intent_test(){
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click()))
        onView(withText("Words That Start With P")).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        // Cannot test an activity state outside of app's scope
        // onView(withText("Google")).check(matches(isDisplayed()))
    }

    /**
     * When we test Navigation Components, we don't actually make the device or emulator visibly navigate.
     * Actual navigation is not possible because the container is not aware of other fragments or activities that we might be navigating to.
     * It only knows the fragment that we specified to launch in it.
     * Therefore, when you run this test on a device or emulator, you will not see any actual navigation.
     * Instead of looking for a UI component that we know displays on a particular screen, we can check to make sure that the current navigation controller's destination has the ID of the fragment we expect to be in.
     */
    @Test()
    fun navigate_to_words_nav_component(){

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        /**
         * There is a fragment equivalent of ActivityScenarioRule that can be used to isolate a fragment for testing.
         * Here we specify that we want to launch the LetterListFragment.
         * We have to pass the app's theme so that the UI components know which theme to use or the test may crash.
         */
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId = R.style.Theme_Words)

        /**
         * Explicitly declare which navigation graph we want the nav controller to use for the fragment launched.
         */
        letterListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        /**
         * Trigger the event that prompts the navigation.
         */
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        /**
         * Check if the navigation controller has the id of the correct destination.
         */
        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)


    }


}