package com.abhinav.shoppy.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhinav.shoppy.persistence.dao.ProductDao
import com.abhinav.shoppy.persistence.entity.Converters
import com.abhinav.shoppy.persistence.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val DB_NAME = "shoppy_products"
    }
}
