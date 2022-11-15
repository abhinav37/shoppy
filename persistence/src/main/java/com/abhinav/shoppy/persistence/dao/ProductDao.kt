package com.abhinav.shoppy.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.abhinav.shoppy.persistence.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products  ORDER BY id ASC")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id IS :productId")
    fun findById(productId: Int): ProductEntity?

    @Query("SELECT * FROM products WHERE title  LIKE '%' || :keyword || '%' OR description  LIKE '%' || :keyword || '%' ")
    fun search(keyword: String): List<ProductEntity>

    @Query("DELETE FROM products")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(products: ProductEntity)

    @Transaction
    fun deleteAndInsert(tasks: List<ProductEntity>) {
        deleteAll()
        insertItems(tasks)
    }
}

