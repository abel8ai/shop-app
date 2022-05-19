package com.uci.shopapp.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.uci.shopapp.data.model.database.ShopDatabase
import com.uci.shopapp.data.model.database.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val shopDatabase: ShopDatabase) : ViewModel() {

    val productModel = MutableLiveData<List<ProductEntity>>()

    suspend fun getAllProducts() {
        productModel.postValue(shopDatabase.getProductDao().getAllProducts())
    }

    suspend fun addProduct(product:ProductEntity):Long {
        val success = shopDatabase.getProductDao().insertProduct(product)
        productModel.postValue(shopDatabase.getProductDao().getAllProducts())
        return success
    }
    suspend fun addAllProducts(products:List<ProductEntity>){
        shopDatabase.getProductDao().insertAll(products)
    }
    suspend fun addDummyProducts(){
        val list = mutableListOf<ProductEntity>()
        for (i in 0..50){
            val product = ProductEntity(null,"Producto $i","Descripcion del producto $i",300.0)
            list.add(product)
        }
        shopDatabase.getProductDao().insertAll(list)
    }

}