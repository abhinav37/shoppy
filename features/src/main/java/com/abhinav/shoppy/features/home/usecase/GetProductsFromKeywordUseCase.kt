package com.abhinav.shoppy.features.home.usecase

import com.abhinav.shoppy.features.ProductRepository
import com.abhinav.shoppy.network.model.Product
import javax.inject.Inject

interface GetProductsFromKeywordUseCase {
    suspend operator fun invoke(keyword: String): List<Product>?
}

class GetProductsFromKeywordUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    GetProductsFromKeywordUseCase {
    override suspend fun invoke(keyword: String): List<Product>? =
        productRepository.getProductsByKeyword(keyword)
}
