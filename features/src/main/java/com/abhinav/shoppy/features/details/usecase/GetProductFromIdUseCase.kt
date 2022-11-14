package com.abhinav.shoppy.features.details.usecase

import com.abhinav.shoppy.features.ProductRepository
import com.abhinav.shoppy.network.model.Product
import javax.inject.Inject

interface GetProductFromIdUseCase {
    suspend operator fun invoke(id: Int): Product
}

class GetProductFromIdUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    GetProductFromIdUseCase {
    override suspend operator fun invoke(id: Int): Product = productRepository.getProductById(id)
}
