package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

class DataSource {
   // A list is a collection of items with a specific order. List cannot be modified after you create it.
    val affirmations = listOf(
        Affirmation(R.string.affirmation1, R.drawable.image1),
        Affirmation(R.string.affirmation2, R.drawable.image2),
        Affirmation(R.string.affirmation3, R.drawable.image3),
        Affirmation(R.string.affirmation4, R.drawable.image4),
        Affirmation(R.string.affirmation5, R.drawable.image5),
        Affirmation(R.string.affirmation6, R.drawable.image6),
        Affirmation(R.string.affirmation7, R.drawable.image7),
        Affirmation(R.string.affirmation8, R.drawable.image8),
        Affirmation(R.string.affirmation9, R.drawable.image9),
        Affirmation(R.string.affirmation10, R.drawable.image10)
    )
}