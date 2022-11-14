package com.abhinav.shoppy.features.home.usecase

import com.abhinav.shoppy.features.ProductRepository
import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductsUseCaseTest {

    private val fakeProduct = EmptyProduct.copy(222)
    private val productList = listOf(fakeProduct, fakeProduct)
    private val incorrectProductList = listOf(EmptyProduct, EmptyProduct)
    private val fakeRepository = object : ProductRepository {
        override suspend fun getProductList(): List<Product> = productList
        override suspend fun getProductsByKeyword(keyword: String): List<Product>? = null
        override suspend fun getProductById(id: Int): Product = EmptyProduct
    }

    private val useCase = GetProductsUseCaseImpl(fakeRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `invoke in Success case`() = runTest {
        val result = useCase.invoke()
        assertEquals(result, productList)
        assertThat(result, not(incorrectProductList))
    }
}
