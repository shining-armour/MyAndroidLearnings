package com.example.cupcake

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModelTest1 {

    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigate_from_start_to_flavor_fragment(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        // fragment equivalent of ActivityScenarioRule
        val startFragmentScenario = launchFragmentInContainer<StartFragment>()
        startFragmentScenario.onFragment { fragment -> navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //Trigger the event that prompts the navigation.
        onView(withId(R.id.start_order)).perform(click())

        // Check if the navigation controller has the id of the correct destination.
        assertEquals(navController.currentDestination?.id, R.id.flavorFragment)
    }


    @Test

}