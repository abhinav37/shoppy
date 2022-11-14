package com.abhinav.shoppy.features.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav.shoppy.common.CoroutineDispatcherProvider
import com.abhinav.shoppy.common.navigation.Details.ARG_PRODUCT_ID
import com.abhinav.shoppy.features.ScreenState
import com.abhinav.shoppy.features.details.usecase.GetProductFromIdUseCase
import com.abhinav.shoppy.network.model.EmptyProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface DetailsViewModel {
    fun loadData()
    val state: StateFlow<DetailsState>
}

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val dispatchers: CoroutineDispatcherProvider,
    private val getProductFromIDUseCase: GetProductFromIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), DetailsViewModel {

    override val state = MutableStateFlow(
        DetailsState(productId = savedStateHandle.get<Int>(ARG_PRODUCT_ID) ?: 0)
    )

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatchers.IO) {
            state.update { it.copy(screenState = ScreenState.LOADING) }
            val product = getProductFromIDUseCase(state.value.productId)
            if (product == EmptyProduct) {
                state.update { it.copy(screenState = ScreenState.ERROR) }
            } else {
                state.update { it.copy(product = product, screenState = ScreenState.NORMAL) }
            }
        }
    }
}
