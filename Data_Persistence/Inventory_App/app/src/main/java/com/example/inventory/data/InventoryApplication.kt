package com.example.inventory.data

import android.app.Application

class InventoryApplication : Application(){

    /**
     *  Database instance is lazily created when you first need/access the reference (rather than when the app starts).
     *  This will create the database (the physical database on the disk) on the first access.
     */
    val itemDatabase: ItemDatabase by lazy{ ItemDatabase.getDatabase(this)}
}
