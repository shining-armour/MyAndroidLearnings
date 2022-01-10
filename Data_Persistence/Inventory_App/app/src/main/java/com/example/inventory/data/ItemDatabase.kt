package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Abstract class you defined acts as a database holder.
 * The class you defined is abstract, because Room creates the implementation for you.
 */

/**
 * Set the version as 1. Whenever you change the schema of the database table, you'll have to increase the version number.
 * Set exportSchema to false, so as not to keep schema version history backups.
 */

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase(){

    /**
     * Define an abstract method or property that returns an ItemDao Instance and the Room will generate the implementation for you.
     * You can have multiple DAOs.
     */
    abstract fun getItemDao(): ItemDao

    /**
     * You only need one instance of the RoomDatabase for the whole app, so make the RoomDatabase a singleton.
     */
    companion object{

        /**
         * The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
         * This make sure that the value of INSTANCE is always up-to-date and the same for all execution threads.
         * It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
         */
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context) : ItemDatabase {
            /**
             * Multiple threads can potentially run into a race condition and ask for a database instance at the same time, resulting in two databases instead of one.
             * Wrapping the code to get the database inside a synchronized block means that only one thread of execution at a time can enter this block of code, which makes sure the database only gets initialized once.
             */
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, ItemDatabase::class.java, "item_db")
                    .fallbackToDestructiveMigration()
                    .build()

                /**
                 * fallbackToDestructiveMigration()
                 * Normally, you would have to provide a migration object with a migration strategy for when the schema changes.
                 * A migration object is an object that defines how you take all rows with the old schema and convert them to rows in the new schema, so that no data is lost.
                 * Here, For simplicity, we are destroying and rebuilding the database, which means that the data is lost.
                  */
                INSTANCE = instance
                return instance
            }
        }
    }
}