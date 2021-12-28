package com.example.birthdaycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Explicitly Inflating a layout
         */
//        val inflater = LayoutInflater.from(this)
//        val view = inflater.inflate(R.layout.activity_main, null)
//        setContentView(view)

        /**
         * Implicitly Inflating a layout
         */
        setContentView(R.layout.activity_main)
    }
}