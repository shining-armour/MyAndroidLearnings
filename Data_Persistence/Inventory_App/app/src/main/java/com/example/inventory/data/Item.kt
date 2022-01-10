package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val itemName: String,
    @ColumnInfo(name = "price") val itemPrice: Double,
    @ColumnInfo(name = "quantity") val quantityInStock: Int
)

//extension function
fun Item.formatPrice(): String = NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(itemPrice)
