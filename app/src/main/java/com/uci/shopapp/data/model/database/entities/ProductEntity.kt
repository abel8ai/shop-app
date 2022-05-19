package com.uci.shopapp.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import javax.annotation.Nullable

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id") val id : Int?,
    @ColumnInfo(name = "product_name") val name: String,
    @ColumnInfo(name = "product_description") val description: String,
    @ColumnInfo(name = "product_price") val price: Double
): Serializable