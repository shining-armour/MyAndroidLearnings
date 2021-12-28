package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.google.android.material.snackbar.Snackbar

import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        // If screen rotated then the text views must be saved through onSaveInstance
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(PLAYER_1_SCORE_KEY)){
                _binding.textView1.text = getString(R.string.score, savedInstanceState.getInt(PLAYER_1_SCORE_KEY))
                _binding.textView1.visibility = View.VISIBLE

            }
            if (savedInstanceState.containsKey(PLAYER_2_SCORE_KEY)){
                _binding.textView2.text = getString(R.string.score, savedInstanceState.getInt(PLAYER_2_SCORE_KEY))
                _binding.textView2.visibility = View.VISIBLE
            }
        }

        // Create a single instance and use this instance as many times as you like
        val diceInstance = Dice(6)

        _binding.imageView1.setOnClickListener{
            val rolledValue = diceInstance.roll()
            _binding.textView1.text = getString(R.string.score, rolledValue)
            _binding.textView1.visibility = View.VISIBLE
            setDiceImage(rolledValue, _binding.imageView1)
            checkIfLuckyNumber(it,  rolledValue, "First")
        }

        _binding.imageView2.setOnClickListener{
            val rolledValue = diceInstance.roll()
            _binding.textView2.text = getString(R.string.score, rolledValue)
            _binding.textView2.visibility = View.VISIBLE
            setDiceImage(rolledValue, _binding.imageView2)
            checkIfLuckyNumber(it,  rolledValue, "Second")
        }
    }

    private fun checkIfLuckyNumber(view: View, rolledNumber: Int, playerNumber : String ){
        val luckyNumber = 6
        when(rolledNumber){
            luckyNumber -> customSnackBar(view,"Congratulations! $playerNumber player got a lucky number!")
            else -> customSnackBar(view, "Try again :(")
        }
    }

    private fun customSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    private fun setDiceImage(rolledNumber:Int, imageView: ImageView){
        val drawableResource = when(rolledNumber){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        imageView.setImageResource(drawableResource)
        //Content description is used by screen readers
        imageView.contentDescription = rolledNumber.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        Log.i("saved", _binding.textView1.text.toString() + "  " + _binding.textView2.text.toString() )
//        Filter String to check if contains Digit and convert that digit string to Int
        outState.putInt(PLAYER_1_SCORE_KEY, _binding.textView1.text.toString().filter { it.isDigit() }.toInt())
        outState.putInt(PLAYER_2_SCORE_KEY, _binding.textView2.text.toString().filter { it.isDigit() }.toInt())
        super.onSaveInstanceState(outState)
    }

    companion object{
        private const val PLAYER_1_SCORE_KEY: String = "PLAYER_1_SCORE_KEY"
        private const val PLAYER_2_SCORE_KEY: String = "PLAYER_2_SCORE_KEY"
    }

}