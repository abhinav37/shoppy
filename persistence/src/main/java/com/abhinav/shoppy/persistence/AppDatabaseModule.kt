package com.abhinav.shoppy.persistence

import android.app.Application
import androidx.room.Room
import com.abhinav.shoppy.persistence.AppDatabase.Companion.DB_NAME
import com.abhinav.shoppy.persistence.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppDatabaseModule {
    @Provides
    @Reusable
    fun provideDatabase(context: Application): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .build()

    @Provides
    @Reusable
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()
}
