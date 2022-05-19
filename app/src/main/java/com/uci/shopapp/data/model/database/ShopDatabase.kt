package com.uci.shopapp.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uci.shopapp.data.model.database.dao.ProductDao
import com.uci.shopapp.data.model.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1,)
abstract class ShopDatabase:RoomDatabase() {

    abstract fun getProductDao(): ProductDao
}