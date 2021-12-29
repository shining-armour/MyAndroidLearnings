package com.example.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.adapter.AffirmationAdapter
import com.example.affirmations.data.DataSource
import com.example.affirmations.databinding.ActivityMainBinding
import com.example.affirmations.model.Affirmation

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
//        setContentView(R.layout.activity_main)

        val myAffirmations: List<Affirmation> = DataSource().affirmations

        binding.recyclerView.adapter = AffirmationAdapter(this, myAffirmations)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.adapter = AffirmationAdapter(this, myAffirmations)
//        recyclerView.layoutManager = GridLayoutManager(this,2)
//        recyclerView.setHasFixedSize(true)
    }
}