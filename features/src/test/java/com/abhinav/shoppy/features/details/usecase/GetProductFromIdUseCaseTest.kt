package com.abhinav.shoppy.features.details.usecase

import com.abhinav.shoppy.features.ProductRepository
import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductFromIdUseCaseTest {

    private val fakeProduct = EmptyProduct.copy(222)
    private val productList = listOf(fakeProduct, fakeProduct)
    private val incorrectProductList = listOf(EmptyProduct, EmptyProduct)
    private val fakeRepository = object : ProductRepository {
        override suspend fun getProductList(): List<Product>? = null
        override suspend fun getProductsByKeyword(keyword: String): List<Product> = productList
        override suspend fun getProductById(id: Int): Product = fakeProduct
    }

    private val useCase = GetProductFromIdUseCaseImpl(fakeRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `invoke in Success case`() = runTest {
        val result = useCase.invoke(222)
        assertEquals(fakeProduct, result)
    }
}
