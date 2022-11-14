package com.abhinav.shoppy.features

import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import com.abhinav.shoppy.network.model.ProductListResponse
import com.abhinav.shoppy.network.services.ProductService
import com.abhinav.shoppy.persistence.dao.ProductDao
import com.abhinav.shoppy.persistence.entity.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProductRepositoryTest {

    private val testProduct = Product(22)
    private val productList = listOf(EmptyProduct, EmptyProduct)

    private val testProductEntity = ProductEntity(22)
    private val productEntityList = listOf(testProductEntity, testProductEntity)

    private val response = ProductListResponse(products = productList)
    private val service = object : ProductService {
        override suspend fun getProducts(limit: Int, offset: Int) = response
        override suspend fun getProduct(productId: Int) = testProduct
        override suspend fun getSearchProducts(keyword: String, limit: Int, offset: Int) = response
    }

    private val database = object : ProductDao {
        override fun getAll() = productEntityList
        override fun findById(productId: Int) = testProductEntity
        override fun search(keyword: String) = productEntityList
        override fun deleteAll() = Unit
        override fun insertItems(products: List<ProductEntity>) = Unit
        override fun insertItem(products: ProductEntity) = Unit
    }

    private val errorService = object : ProductService {
        override suspend fun getProducts(limit: Int, offset: Int) = throw Exception()
        override suspend fun getProduct(productId: Int) = throw Exception()
        override suspend fun getSearchProducts(keyword: String, limit: Int, offset: Int) =
            throw Exception()
    }

    private val errorDatabase = object : ProductDao {
        override fun getAll() = throw Exception()
        override fun findById(productId: Int) = throw Exception()
        override fun search(keyword: String) = throw Exception()
        override fun deleteAll() = Unit
        override fun insertItems(products: List<ProductEntity>) = Unit
        override fun insertItem(products: ProductEntity) = Unit
    }

    @Test
    fun `getProducts returns list of products`() = runBlocking {
        val repository = ProductRepositoryImpl(service, database)
        val list = repository.getProductList()
        assertEquals(productList, list)
    }

    @Test
    fun `getProducts on error returns null`() = runBlocking {
        val repository = ProductRepositoryImpl(errorService, errorDatabase)
        val list = repository.getProductList()
        assertNull(list)
    }

    @Test
    fun `getProductDataById returns Products`() = runBlocking {
        val repository = ProductRepositoryImpl(service, database)
        val product = repository.getProductById(222)
        assertEquals(testProduct, product)
    }

    @Test
    fun `getProductDataById on error returns EmptyProduct`() = runBlocking {
        val repository = ProductRepositoryImpl(errorService, errorDatabase)
        val product = repository.getProductById(222)
        assertEquals(EmptyProduct, product)
    }
}
