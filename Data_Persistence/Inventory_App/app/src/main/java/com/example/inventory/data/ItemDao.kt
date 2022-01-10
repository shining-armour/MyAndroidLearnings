package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<Item>>

    /**
     * The argument OnConflict tells the Room what to do in case of a conflict.
     * The OnConflictStrategy.IGNORE strategy ignores a new item if it's primary key is already in the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item:Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item:Item)
}