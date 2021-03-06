package com.uci.shopapp.ui.view_model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.*
import android.os.Build.VERSION_CODES.Q
import com.uci.shopapp.data.model.database.ShopDatabase
import com.uci.shopapp.data.model.database.dao.ProductDao
import com.uci.shopapp.data.model.database.entities.ProductEntity
import com.uci.shopapp.getorAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class ProductViewModelTest {

    private lateinit var shopDatabase:ShopDatabase
    private lateinit var productViewModel: ProductViewModel
    @RelaxedMockK
    lateinit var productDao : ProductDao

    @Before
    fun createDb() {
        MockKAnnotations.init(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        shopDatabase = Room.inMemoryDatabaseBuilder(context, ShopDatabase::class.java).build()
        productViewModel = ProductViewModel(shopDatabase)
    }
    @Test
    fun whenDatabaseIsEmptyReturnsEmptyList() = runBlocking {
        // given
        coEvery { productDao.getAllProducts() } returns mutableListOf<ProductEntity>()
        // when
        productViewModel.getAllProducts()
        val result = productViewModel.productsModel.getorAwaitValue()
        // then
        coVerify(exactly = 1) { productDao.getAllProducts() }
        assertThat(result == mutableListOf<ProductEntity>()).isTrue()
    }

    @Test
    fun whenDatabaseHasProductsReturnsListOfProducts() =  runBlocking{
        // given
        val products = mutableListOf(ProductEntity(null,"producto 1","descripcion",300.0))
        coEvery { productDao.getAllProducts() }returns products

        // when
        productViewModel.getAllProducts()
        val result = productViewModel.productsModel.getorAwaitValue()
        // then
        coVerify(exactly = 1) { productDao.getAllProducts() }
        assertThat(result == products).isTrue()
    }
    @Test
    fun whenGivenExistingIdReturnsProduct() =  runBlocking{
        // given
        val product = ProductEntity(0,"producto 1","descripcion",300.0)
        coEvery { productDao.getProductById(any()) } returns product

        // when
        productViewModel.getProductById(0)
        val result = productViewModel.productModel.getorAwaitValue()
        // then
        coVerify(exactly = 1) { productDao.getProductById(0) }
        assertThat(result == product).isTrue()
    }
    @Test
    fun whenGivenNonExistingIdReturnsNull() =  runBlocking{
        // given
        coEvery { productDao.getProductById(any()) } returns null as ProductEntity

        // when
        productViewModel.getProductById(55555)
        val result = productViewModel.productModel.getorAwaitValue()

        // then
        coVerify(exactly = 1) { shopDatabase.getProductDao().getProductById(55555) }
        assertThat(result == null).isTrue()
    }
}