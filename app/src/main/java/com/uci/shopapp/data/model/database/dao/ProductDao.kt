package com.uci.shopapp.data.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.uci.shopapp.data.model.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("Select * from product_table")
    suspend fun getAllProducts():MutableList<ProductEntity>

    @Insert(onConflict = IGNORE)
    suspend fun insertAll(products:MutableList<ProductEntity>): List<Long>

    @Insert(onConflict = IGNORE)
    suspend fun insertProduct(product : ProductEntity):Long
}