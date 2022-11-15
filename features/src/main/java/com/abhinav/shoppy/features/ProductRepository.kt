package com.abhinav.shoppy.features

import com.abhinav.shoppy.network.model.EmptyProduct
import com.abhinav.shoppy.network.model.Product
import com.abhinav.shoppy.network.services.ProductService
import com.abhinav.shoppy.persistence.dao.ProductDao
import com.abhinav.shoppy.persistence.entity.ProductEntity
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface ProductRepository {
    suspend fun getProductList(): List<Product>?
    suspend fun getProductsByKeyword(keyword: String): List<Product>?
    suspend fun getProductById(id: Int): Product
}

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val database: ProductDao,
) : ProductRepository {
    override suspend fun getProductList(): List<Product>? = try {
        val data = productService.getProducts().products
        database.insertItems(data.toProductEntityList())
        data
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        getProductListFromDB()
    }

    override suspend fun getProductsByKeyword(keyword: String): List<Product>? = try {
        val data = productService.getSearchProducts(keyword).products
        database.insertItems(data.toProductEntityList())
        data
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        getProductListFromDB(keyword)
    }

    override suspend fun getProductById(id: Int): Product = try {
        val data = productService.getProduct(id)
        database.insertItem(data.toProductEntity())
        data
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        getProductFromDB(id)
    }

    private fun getProductFromDB(id: Int) = try {
        database.findById(id)?.toProduct() ?: EmptyProduct
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        EmptyProduct
    }

    private fun getProductListFromDB() = try {
        database.getAll().toProductListResponse()
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        null
    }

    private fun getProductListFromDB(keyword: String) = try {
        database.search(keyword).toProductListResponse()
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        null
    }
}

private fun List<ProductEntity>.toProductListResponse() =
    if (isEmpty()) null else map { it.toProduct() }

private fun ProductEntity.toProduct(): Product = Product(id,
    title,
    description,
    price,
    discountPercentage,
    rating,
    stock,
    brand,
    category,
    thumbnail,
    images
)

private fun List<Product>.toProductEntityList() = this.map { it.toProductEntity() }

private fun Product.toProductEntity(): ProductEntity = ProductEntity(id,
    title,
    description,
    price,
    discountPercentage,
    rating,
    stock,
    brand,
    category,
    thumbnail,
    images
)
