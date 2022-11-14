package com.abhinav.shoppy.features

import com.abhinav.shoppy.features.details.usecase.GetProductFromIdUseCase
import com.abhinav.shoppy.features.details.usecase.GetProductFromIdUseCaseImpl
import com.abhinav.shoppy.features.home.usecase.GetProductsFromKeywordUseCase
import com.abhinav.shoppy.features.home.usecase.GetProductsFromKeywordUseCaseImpl
import com.abhinav.shoppy.features.home.usecase.GetProductsUseCase
import com.abhinav.shoppy.features.home.usecase.GetProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
* Each Screen should have its own separate module but in this case as only the 1 repos were required
* I went with a single one
* */
@InstallIn(ViewModelComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun bindTaskRepository(repository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindGetProductsUseCase(useCase: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    abstract fun bindGetProductFromIdUseCase(useCase: GetProductFromIdUseCaseImpl): GetProductFromIdUseCase

    @Binds
    abstract fun bindGetProductsFromKeywordUseCase(useCase: GetProductsFromKeywordUseCaseImpl): GetProductsFromKeywordUseCase
}

