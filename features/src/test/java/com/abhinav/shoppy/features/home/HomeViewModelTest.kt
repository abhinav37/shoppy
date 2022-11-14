package com.abhinav.shoppy.features.home

import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.features.TestDispatcherProvider
import com.abhinav.shoppy.features.home.usecase.GetProductsFromKeywordUseCase
import com.abhinav.shoppy.features.home.usecase.GetProductsUseCase
import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest {
    private val fakeProduct = EmptyProduct.copy(222, images = listOf("", ""))
    private val fakeProductList1 = listOf(fakeProduct, fakeProduct)
    private val fakeProductList2 = listOf(fakeProduct)

    private val dispatchers = TestDispatcherProvider()
    private val useCaseNull = object : GetProductsUseCase {
        override suspend fun invoke(): List<Product>? = null
    }
    private val useCaseKeywordNull = object : GetProductsFromKeywordUseCase {
        override suspend fun invoke(keyword: String): List<Product>? = null
    }
    private val useCaseFull = object : GetProductsUseCase {
        override suspend fun invoke() = fakeProductList1
    }
    private val useCaseKeywordFull = object : GetProductsFromKeywordUseCase {
        override suspend fun invoke(keyword: String) = fakeProductList2
    }
    private val useCaseEmpty = object : GetProductsUseCase {
        override suspend fun invoke() = emptyList<Product>()
    }
    private val useCaseKeywordEmpty = object : GetProductsFromKeywordUseCase {
        override suspend fun invoke(keyword: String) = emptyList<Product>()
    }
    private val viewModel = HomeViewModelImpl(dispatchers, useCaseKeywordFull, useCaseFull)

    @Test
    fun `on search input change update Keyword`() {
        val verifyKey = "verify"
        viewModel.onSearchInputChange(verifyKey)
        val keyword = viewModel.state.value.keyword
        assertEquals(verifyKey, keyword)
    }

    @Test
    fun `on searchInputChange Keyword should be trimmed of enter char with whitespace`() {
        val key = "verify\n"
        val verifyKey = "verify "
        viewModel.onSearchInputChange(key)
        val keyword = viewModel.state.value.keyword
        assertEquals(verifyKey, keyword)
    }

    @Test
    fun `searchWithKeyword if keyword is Empty get all`() {
        viewModel.onSearchInputChange("")
        viewModel.searchWithKeyword()
        val scState = viewModel.state.value.screenState
        assertEquals(ScreenState.NORMAL, scState)
        assertEquals(fakeProductList1, viewModel.state.value.allProducts)
    }

    @Test
    fun `searchWithKeyword if keyword is Not Empty get search Keyword`() {
        viewModel.onSearchInputChange("test")
        viewModel.searchWithKeyword()
        val scState = viewModel.state.value.screenState
        assertEquals(ScreenState.NORMAL, scState)
        assertEquals(fakeProductList2, viewModel.state.value.allProducts)
    }

    @Test
    fun `searchWithKeyword set Normal state if productList is NOT EMPTY OR NULL`() {
        viewModel.searchWithKeyword()
        val scState = viewModel.state.value.screenState
        val products = viewModel.state.value.allProducts
        assertEquals(scState, ScreenState.NORMAL)
        assertEquals(fakeProductList1, products)
    }

    @Test
    fun `searchWithKeyword set EMPTY state if productList is Empty`() {
        val vm = HomeViewModelImpl(dispatchers, useCaseKeywordEmpty, useCaseEmpty)
        vm.searchWithKeyword()
        val scState = vm.state.value.screenState
        assertEquals(ScreenState.EMPTY, scState)
    }

    @Test
    fun `searchWithKeyword set ERROR state if productList is Null`() {
        val vm = HomeViewModelImpl(dispatchers, useCaseKeywordNull, useCaseNull)
        vm.searchWithKeyword()
        val scState = vm.state.value.screenState
        assertEquals(ScreenState.ERROR, scState)
    }
}
