package com.abhinav.shoppy.features.details

import androidx.lifecycle.SavedStateHandle
import com.abhinav.shoppy.common.navigation.Details.ARG_PRODUCT_ID
import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.features.TestDispatcherProvider
import com.abhinav.shoppy.features.details.usecase.GetProductFromIdUseCase
import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailsViewModelTest {
    private val fakeProduct = EmptyProduct.copy(222, images = listOf("", ""))
    private val dispatchers = TestDispatcherProvider()
    private val useCase = object : GetProductFromIdUseCase {
        override suspend fun invoke(id: Int): Product = EmptyProduct
    }
    private val useCaseFakeProduct = object : GetProductFromIdUseCase {
        override suspend fun invoke(id: Int): Product = fakeProduct
    }
    private val testArg = 222
    private val savedStateHandle = SavedStateHandle().apply {
        set(ARG_PRODUCT_ID, testArg)
    }
    private val viewModel =
        DetailsViewModelImpl(dispatchers, useCaseFakeProduct, savedStateHandle)

    @Test
    fun `check if arguments are correct`() {
        val id = viewModel.state.value.productId
        assertEquals(testArg, id)
    }

    @Test
    fun `on initialization fetch data from server`() {
        val product = viewModel.state.value.product
        assertEquals(fakeProduct, product)
    }

    @Test
    fun `loadData set Normal state if product is Not Empty`() {
        val scState = viewModel.state.value.screenState
        val product = viewModel.state.value.product
        viewModel.loadData()
        assertEquals(ScreenState.NORMAL, scState)
        assertEquals(fakeProduct, product)
    }

    @Test
    fun `loadData set error state if product is Empty`() {
        val vm = DetailsViewModelImpl(dispatchers, useCase, savedStateHandle)
        vm.loadData()
        val scState = vm.state.value.screenState
        assertEquals(ScreenState.ERROR, scState)
    }
}
