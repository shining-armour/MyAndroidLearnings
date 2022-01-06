package com.example.cupcake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel : ViewModel() {

    private val _orderQuantity = MutableLiveData(0)
    val orderQuantity: LiveData<Int> = _orderQuantity

    private val _vanillaQuantity = MutableLiveData(0)
    val vanillaQuantity: LiveData<Int> = _vanillaQuantity

    private val _chocolateQuantity = MutableLiveData(0)
    val chocolateQuantity: LiveData<Int> = _chocolateQuantity

    private val _redVelvetQuantity = MutableLiveData(0)
    val redVelvetQuantity: LiveData<Int> = _redVelvetQuantity

    private val _cupcakeFlavoursList = MutableLiveData<MutableSet<String>>()
    val cupcakeFlavoursList: LiveData<Set<String>> =
        Transformations.map(_cupcakeFlavoursList) { it }

    private val _pickUpDate = MutableLiveData<String>()
    val pickUpDate: LiveData<String> = _pickUpDate

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(it)
    }

    private val summaryFlavourMap: MutableMap<String, Int> = mutableMapOf()
    var summaryFlavourString: String = ""
    val pickUpOptions = pickUpDateOptions()

    /**
     * Add and remove flavours from [cupcakeFlavoursList] depending upon checkbox selection
     */
    fun setCupcakeFlavour(flavour: String, operation: Flavour) {
        when (operation) {
            Flavour.ADD -> _cupcakeFlavoursList.value?.add(flavour)
            Flavour.REMOVE -> _cupcakeFlavoursList.value?.remove(flavour)
        }
        Log.i("Flavor", cupcakeFlavoursList.value.toString())
    }

    /**
     * Helper function to check if [cupcakeFlavoursList] is empty or null.
     * If it is, we can show a toast msg in the UI.
     */
    fun hasNoFlavourSet(): Boolean {
        return _cupcakeFlavoursList.value.isNullOrEmpty()
    }

    /**
     * Creates and return the list of 4 consecutive pickup dates.
     */
    private fun pickUpDateOptions(): List<String> {
        val pickUpOptions = mutableListOf<String>()
        // E MMM d = Tue Nov 30, Local depends on region/country/language
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        // This variable will contain the current date and time
        val calendar = Calendar.getInstance()
        Log.i("Calendar: ", calendar.toString())
        // Build up a list of dates starting with the current date and the following three dates.
        // Because you'll need 4 date options, repeat this block of code 4 times.
        // This repeat block will format a date, add it to the list of date options, and then increment the calendar by 1 day.
        repeat(4) {
            Log.i("Date $it: ", "${calendar.time} -> ${formatter.format(calendar.time)}")
            pickUpOptions.add(formatter.format(calendar.time))
            // Calendar.DATE = 5, Calender.MONTH = 2, Calendar.YEAR = 1
            // Add next DATE to the calendar instance to make it the current calendar instance
            calendar.add(Calendar.DATE, 1)
            //Log.i("Calendar Time: ", calendar.time.toString())
        }
        Log.i("Pick Up Options: ", pickUpOptions.toString())
        return pickUpOptions
    }

    /**
     * Set the [pickUpDate] based on the radio button selected for date.
     */
    fun setPickUpDate(date: String) {
        _pickUpDate.value = date
        Log.i("Pickup date", pickUpDate.value.toString())
        updatePrice()
    }


    /**
     * This function is called when add icon button for increasing the quantity of the selected cupcake flavour is clicked.
     * Increments the selected flavour by 1. No max limit is set.
     * Calculate the total order and price everytime a change in qty happens so that the UI values change accordingly
     */
    fun addCupcakes(flavour: String) {
        when (flavour) {
            "Vanilla" -> _vanillaQuantity.value = _vanillaQuantity.value?.inc()
            "Chocolate" -> _chocolateQuantity.value = _chocolateQuantity.value?.inc()
            "Red Velvet" -> _redVelvetQuantity.value = _redVelvetQuantity.value?.inc()
        }
        calculateTotalOrderQtyAndTotalPrice()
    }

    /**
     * This function is called when remove icon button for removing the quantity of the selected cupcake flavour is clicked.
     * Decrements the selected flavour by 1. Min limit for selected cupcake is at least 1.
     * Calculate the total order and price everytime a change in qty happens so that the UI values change accordingly
     */
    fun removeCupcakes(flavour: String) {
        when (flavour) {
            "Vanilla" -> _vanillaQuantity.value =
                if (_vanillaQuantity.value!! <= 1) 1 else (_vanillaQuantity.value)?.dec()
            "Chocolate" -> _chocolateQuantity.value =
                if (_chocolateQuantity.value!! <= 1) 1 else (_chocolateQuantity.value)?.dec()
            "Red Velvet" -> _redVelvetQuantity.value =
                if (_redVelvetQuantity.value!! <= 1) 1 else (_redVelvetQuantity.value)?.dec()
        }
        calculateTotalOrderQtyAndTotalPrice()
    }

    /**
     * Calculates the total order qty which is the summation of the quantities of all flavoured cupcakes.
     */
    private fun calculateOrderQty() {
        val totalOrder =
            _vanillaQuantity.value?.let { vanilla -> _chocolateQuantity.value?.let { choco -> _redVelvetQuantity.value?.let { redVelvet -> vanilla + choco + redVelvet } } }
        _orderQuantity.value = totalOrder
        Log.i("Order Quantity", orderQuantity.value.toString())
    }

    /**
     * Calculates the total price of the cupcakes based on the total quantity.
     *  check if the user selected the same day pickup or not
     *  If the user selected the first option (today) for pickup, add the additional charge.
     */
    private fun updatePrice() {
        var calPrice = (_orderQuantity.value ?: 0) * PRICE_PER_CUPCAKE

        if (pickUpDate.value == pickUpOptions[0]) {
            calPrice += SAME_DAY_PICK_UP_CHARGE
        }
        _price.value = calPrice
        Log.i("Price: ", price.value.toString())
    }

    /**
     * Helper function to calculate final qty and final price
     */
    fun calculateTotalOrderQtyAndTotalPrice() {
        calculateOrderQty()
        updatePrice()
    }

    /**
     * Specifically made to handle check box selected and deselected state for vanilla.
     */
    fun setVanillaQty(qty: Int) {
        _vanillaQuantity.value = qty
        calculateTotalOrderQtyAndTotalPrice()
    }

    /**
     * Specifically made to handle check box selected and deselected state for chocolate.
     */
    fun setChocolateQty(qty: Int) {
        _chocolateQuantity.value = qty
        calculateTotalOrderQtyAndTotalPrice()
    }

    /**
     * Specifically made to handle check box selected and deselected state for red velvet.
     */
    fun setRedVelvetQty(qty: Int) {
        _redVelvetQuantity.value = qty
        calculateTotalOrderQtyAndTotalPrice()
    }

    /**
     * Create a map that consists of keys (selected flavours) and values (corresponding quantities)
     */
    fun setFlavourSummary(){
        if(_cupcakeFlavoursList.value!!.contains("Vanilla")){
            summaryFlavourMap["Vanilla"] = _vanillaQuantity.value!!
        }
        if(_cupcakeFlavoursList.value!!.contains("Chocolate")){
            summaryFlavourMap["Chocolate"] = _chocolateQuantity.value!!
        }
        if(_cupcakeFlavoursList.value!!.contains("Red Velvet")){
            summaryFlavourMap["Red Velvet"] = _redVelvetQuantity.value!!
        }
        for ((k,v) in summaryFlavourMap){
            if (!summaryFlavourString.contains(k)){
            summaryFlavourString += "$k - $v\n"
        }}
    }

    /**
     * Called everytime a new order is started.
     * Set default pickupDate as tomorrow, default cupcake flavour as vanilla and quantity of vanilla as 1.
     */
    fun resetOrder() {
        _cupcakeFlavoursList.value = mutableSetOf("Vanilla")
        _orderQuantity.value = 0
        _pickUpDate.value = pickUpOptions[1]
        _price.value = 0.0
        _redVelvetQuantity.value = 0
        _chocolateQuantity.value = 0
        _vanillaQuantity.value = 1
    }



}


