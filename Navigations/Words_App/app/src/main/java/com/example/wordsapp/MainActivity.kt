package com.example.wordsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wordsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get a reference to the nav_host_fragment and assign it to your navController property.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Ensures action bar buttons, like the menu option in LetterListFragment are visible.
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        /**
         * Short-circuit evaluation technique
         * || operator only requires one of the conditions to be true, so if navigateUp() returns true, the right side of the || expression is never executed.
         * If, however, navigateUp() is false, then the implementation in the parent class is called.
         */
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
