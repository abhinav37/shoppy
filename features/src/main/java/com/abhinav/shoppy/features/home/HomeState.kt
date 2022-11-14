package com.abhinav.shoppy.features.home

import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.network.model.Product

data class HomeState(
    val allProducts: List<Product> = emptyList(),
    val keyword: String = "",
    val screenState: ScreenState = ScreenState.LOADING,
)
