package com.abhinav.shoppy.features.home.usecase

import com.abhinav.shoppy.features.ProductRepository
import com.abhinav.shoppy.network.model.Product
import javax.inject.Inject

interface GetProductsUseCase {
    suspend operator fun invoke(): List<Product>?
}

open class GetProductsUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    GetProductsUseCase {
    override suspend operator fun invoke(): List<Product>? = productRepository.getProductList()
}
