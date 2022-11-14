package com.abhinav.shoppy.network.services

import com.abhinav.shoppy.network.model.Product
import com.abhinav.shoppy.network.model.ProductListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit")
        limit: Int = 30,
        @Query("skip")
        offset: Int = 0,
    ): ProductListResponse

    @GET("products/{product_id}?")
    suspend fun getProduct(
        @Path(value = "product_id")
        productId: Int,
    ): Product

    @GET("products/search?")
    suspend fun getSearchProducts(
        @Query("q")
        keyword: String,
        @Query("limit")
        limit: Int = 30,
        @Query("skip")
        offset: Int = 0,
    ): ProductListResponse
}
