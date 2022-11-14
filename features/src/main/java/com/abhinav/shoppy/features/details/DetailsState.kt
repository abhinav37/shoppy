package com.abhinav.shoppy.features.details

import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product

data class DetailsState(
    val product: Product = EmptyProduct,
    val imageVisibility: Boolean = false,
    val productId: Int,
    val screenState: ScreenState = ScreenState.LOADING,
)
