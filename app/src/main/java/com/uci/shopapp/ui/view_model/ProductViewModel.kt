package com.uci.shopapp.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.uci.shopapp.data.model.database.ShopDatabase
import com.uci.shopapp.data.model.database.entities.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val shopDatabase: ShopDatabase) : ViewModel() {

    val productsModel = MutableLiveData<MutableList<ProductEntity>>()
    val productModel = MutableLiveData<ProductEntity>()

    suspend fun getAllProducts() {
        productsModel.postValue(shopDatabase.getProductDao().getAllProducts())
    }

    suspend fun addProduct(product:ProductEntity):Long {
        val success = shopDatabase.getProductDao().insertProduct(product)
        productsModel.postValue(shopDatabase.getProductDao().getAllProducts())
        return success
    }
    suspend fun addAllProducts(products:MutableList<ProductEntity>){
        shopDatabase.getProductDao().insertAll(products)
    }
    suspend fun addDummyProducts(){
        val list = mutableListOf<ProductEntity>()

        for (i in 0..50){
            val price = (150..300).random().toDouble()

            val description = "Producto nuevo de alta gama. Buena relación calidad-precio y ensamblado con materiales premium. Garantizada su durabilidad y buen desempeño"
            val product = ProductEntity(null,"Producto $i",description,price)
            list.add(product)
        }
        shopDatabase.getProductDao().insertAll(list)
    }
    suspend fun getProductById(id:Int){
        productModel.postValue(shopDatabase.getProductDao().getProductById(id))
    }

}