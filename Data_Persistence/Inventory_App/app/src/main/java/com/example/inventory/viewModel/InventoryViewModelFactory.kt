package com.example.inventory.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventory.data.ItemDao
import java.lang.IllegalArgumentException

/**
 * To instantiate the InventoryViewModel instance
 */
class InventoryViewModelFactory(private val itemDao: ItemDao): ViewModelProvider.Factory {

    /**
     * create() method takes any class type as an argument and returns a ViewModel object.
     * Check if the modelClass is the same as the InventoryViewModel class and return an instance of it. Otherwise, throw an exception.
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}