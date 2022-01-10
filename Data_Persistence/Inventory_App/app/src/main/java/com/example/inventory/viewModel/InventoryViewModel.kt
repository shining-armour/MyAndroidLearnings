package com.example.inventory.viewModel

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

/**
 * [InventoryViewModel] will interact with the database via the DAO, and provide data to the UI.
 * All database operations will have to be run away from the main UI thread, you'll do that using coroutines and viewModelScope.
 */
class InventoryViewModel(private val itemDao: ItemDao): ViewModel() {

    val getAllItems: LiveData<List<Item>> = itemDao.getAllItems().asLiveData()

    fun getAnItem(id:Int) : LiveData<Item> = itemDao.getItemById(id).asLiveData()

    fun isEntryValid(name: String, price:String, quantity:String):Boolean{
        if (name.isBlank() || price.isBlank() || quantity.isBlank()){
            return false
        }
        return true
    }

    private fun createNewItem(name: String, price:String, quantity:String): Item{
        return Item(
            itemName = name,
            itemPrice = price.toDouble(),
            quantityInStock = quantity.toInt()
        )
    }

    private fun updateEntryItem(id: Int, name: String, price: String, quantity: String): Item {
        return Item(
            id = id,
            itemName = name,
            itemPrice = price.toDouble(),
            quantityInStock = quantity.toInt()
        )
    }

    fun sellItem(item: Item){
        if (isStockAvailable(item)){
            val updatedItem = item.copy(quantityInStock =  item.quantityInStock - 1)
            updateItemInDB(updatedItem)
        }
    }

    fun isStockAvailable(item:Item): Boolean{
        return (item.quantityInStock > 0)
    }

    /**
     * Notice that you did not use viewModelScope.launch for addNewItem(), but it is needed above in insertItem() when you call a DAO method.
     * The reason is that the suspend functions are only allowed to be called from a coroutine or another suspend function.
     */

    fun addNewItem(name: String, price: String, qty: String){
        val newItem = createNewItem(name, price, qty)
        addItemToDB(newItem)
    }

    fun updateExistingItem(id: Int, name: String, price: String, qty: String){
        val updatedItem = updateEntryItem(id,name,price,qty)
        updateItemInDB(updatedItem)
    }



    // Coroutine scope functions
    private fun addItemToDB(item: Item){
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    private fun updateItemInDB(item: Item){
        viewModelScope.launch {
            itemDao.updateItem(item)
        }
    }

    fun deleteItemFromDB(item: Item){
        viewModelScope.launch {
            itemDao.deleteItem(item)
        }
    }
}
