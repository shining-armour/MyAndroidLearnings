package com.example.tip_calculator_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tip_calculator_app.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        if (savedInstanceState!=null){
            if (savedInstanceState.containsKey(TIP_AMOUNT_KEY) ){
                val deletedTipAmount = savedInstanceState.getString(TIP_AMOUNT_KEY)
                Log.i("emitted", deletedTipAmount!!)
                // check if saved tip amount string contains digit
                if (deletedTipAmount.contains("[0-9]".toRegex())) {
                    _binding.tipResult.text = deletedTipAmount
                    _binding.tipResult.visibility = View.VISIBLE
                }
            }
        }

        _binding.apply {
            calculateButton.setOnClickListener {
                handleCases(it)
                // close keyboard (if open) when button is clicked
                closeKeyboard(it)
            }
        }

    }

    private fun handleCases(view: View) {
        val cost = _binding.costOfServiceEt.text.toString().toDoubleOrNull()

        when(cost){
            null -> {
                appSnackBar(view, "Enter a service amount")
                return
            }
            else -> {
                val tipAmount = calculateTip(cost)
                displayFormattedTipAmount(tipAmount)
            }
        }
    }

    private fun calculateTip(cost: Double): Double {
        val tipPercent = when (_binding.tipOptions.checkedRadioButtonId) {
            _binding.optionTwentyPercent.id -> 0.20
            _binding.optionTenPercent.id -> 0.10
            else -> 0.15
        }
        val roundUp = _binding.roundUpSwitch.isChecked
        if(roundUp){
            return kotlin.math.ceil(tipPercent.times(cost))
        }
        return tipPercent.times(cost)
    }

    private fun displayFormattedTipAmount(amount: Double){
        val tipAmount = NumberFormat.getCurrencyInstance(Locale("en","IN")).format(amount)
        _binding.tipResult.text = getString(R.string.tip_amount_text, tipAmount)
        _binding.tipResult.visibility = View.VISIBLE
    }

    private fun closeKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun appSnackBar(view: View, msg:String){
        Snackbar.make(view,msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val getDeletedTipAmount = _binding.tipResult.text.toString()
        Log.i("saved", getDeletedTipAmount)
        outState.putString(TIP_AMOUNT_KEY,getDeletedTipAmount)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val TIP_AMOUNT_KEY: String = "TIP_AMOUNT_KEY"
    }

}