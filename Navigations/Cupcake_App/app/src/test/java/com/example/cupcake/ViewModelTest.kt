package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

// Tests needs to be updated since some changes were made in code for adding counters for selecting the user desired quantity.

class ViewModelTest{

    /**
     * Before running tests, comment all log statements that may be called during testing functions.
     */

    /**
     * A unit test assumes that everything runs on the main thread. However, LiveData objects cannot access the main thread.
     * To specify that LiveData objects should not call the main thread we need to provide a specific test rule any time we are testing a LiveData object.
     * This should be set before initializing ViewModel.
     */
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: OrderViewModel
//    private val pickUpOptions = mutableListOf<String>()
//
//    /**
//     * Initialize a viewModel instance before every test
//     */
//    @Before
//    fun setup(){
//        viewModel = OrderViewModel()
//        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
//        val calendar = Calendar.getInstance()
//        repeat(4){
//            pickUpOptions.add(formatter.format(calendar.time))
//            calendar.add(Calendar.DATE, 1)
//        }
//    }
//
//    /**
//     * Test if the LiveData orderQuantity is updated with correct qty for 12 cupcakes
//     */
//    @Test
//    fun test_qty_of_cupcakes(){
//        viewModel.setOrderQuantity(12)
//        assertEquals(12, viewModel.orderQuantity.value)
//    }
//
//    /**
//     * Test if the LiveData cupcakeFlavour is updated with correct flavour Red Velvet
//     */
//    @Test
//    fun test_flavour_of_cupcakes(){
//        viewModel.setCupcakeFlavour("Red Velvet")
//        assertEquals("Red Velvet", viewModel.cupcakeFlavour.value)
//    }
//
//    /**
//     * Test if the LiveData pickUpDate is updated with correct selected date
//     */
//    @Test
//    fun test_pickup_date_for_cupcakes(){
//
//        viewModel.setPickUpDate(pickUpOptions[1])
//        assertEquals(pickUpOptions[1], viewModel.pickUpDate.value)
//    }
//
//    /**
//     * Test if the LiveData price is updated with correct price for 6 cupcakes for same day delivery
//     * We used a Transformation to set the value of price.
//     * When transforming a LiveData object, the code doesn't get called unless it is running on a mobile device.
//     * If we want observe the object for changes in testing then we need to apply observeForever to the LiveData object.
//     */
//    @Test
//    fun price_of_six_cupcakes_on_same_day_pickup(){
//        val viewModel = OrderViewModel()
//        viewModel.setOrderQuantity(6)
//        viewModel.price.observeForever{}
//        val expectedPrice = NumberFormat.getCurrencyInstance(Locale("en","IN")).format(15.00)
//        assertEquals(expectedPrice, viewModel.price.value)
//    }
//
//    /**
//     * Test if the LiveData price is updated with correct price for 6 cupcakes for different day delivery
//     */
//    @Test
//    fun price_of_six_cupcakes_on_different_day_pickup(){
//        val viewModel = OrderViewModel()
//        viewModel.setOrderQuantity(6)
//        viewModel.setPickUpDate(pickUpOptions[2])
//        viewModel.price.observeForever{}
//        val expectedPrice = NumberFormat.getCurrencyInstance(Locale("en","IN")).format(12.00)
//        assertEquals(expectedPrice, viewModel.price.value)
//    }




}