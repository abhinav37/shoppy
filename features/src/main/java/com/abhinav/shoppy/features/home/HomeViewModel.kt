package com.abhinav.shoppy.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav.shoppy.common.CoroutineDispatcherProvider
import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.features.home.usecase.GetProductsFromKeywordUseCase
import com.abhinav.shoppy.features.home.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface HomeViewModel {
    val state: StateFlow<HomeState>
    fun searchWithKeyword()
    fun onSearchInputChange(keyword: String)
}

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val dispatchers: CoroutineDispatcherProvider,
    private val getProductsFromKeywordUseCase: GetProductsFromKeywordUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel(), HomeViewModel {
    override val state = MutableStateFlow(HomeState())

    init {
        searchWithKeyword()
    }

    override fun searchWithKeyword() {
        viewModelScope.launch(dispatchers.IO) {
            state.update { it.copy(screenState = ScreenState.LOADING) }

            val products = if (state.value.keyword.isBlank()) {
                getProductsUseCase()
            } else {
                getProductsFromKeywordUseCase(state.value.keyword)
            }
            if (products.isNullOrEmpty()) {
                val screenState = if (products == null) ScreenState.ERROR else ScreenState.EMPTY
                state.update { it.copy(allProducts = emptyList(), screenState = screenState) }
            } else {
                state.update { it.copy(allProducts = products, screenState = ScreenState.NORMAL) }
            }
        }
    }

    override fun onSearchInputChange(keyword: String) {
        state.update {
            it.copy(keyword = keyword.replaceIndentToSpace())
        }
    }

    private fun String.replaceIndentToSpace() = replace("\n", " ")
}
