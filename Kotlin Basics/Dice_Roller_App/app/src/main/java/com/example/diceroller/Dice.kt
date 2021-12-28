package com.example.diceroller

class Dice (var sides: Int) {

    fun roll():Int{
        return (1..sides).random()
    }
}