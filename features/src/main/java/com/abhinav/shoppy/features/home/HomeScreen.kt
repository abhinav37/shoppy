package com.abhinav.shoppy.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav.shoppy.designsystem.components.BasicButton
import com.abhinav.shoppy.designsystem.components.LoadingProductContentWithThumbnail
import com.abhinav.shoppy.designsystem.components.ProductContentWithThumbnail
import com.abhinav.shoppy.designsystem.components.SearchInput
import com.abhinav.shoppy.designsystem.theme.DSColors
import com.abhinav.shoppy.designsystem.theme.DSTypography
import com.abhinav.shoppy.features.R
import com.abhinav.shoppy.features.ScreenState.EMPTY
import com.abhinav.shoppy.features.ScreenState.ERROR
import com.abhinav.shoppy.features.ScreenState.LOADING
import com.abhinav.shoppy.features.ScreenState.NORMAL
import com.abhinav.shoppy.network.model.Product
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun HomeScreen(onItemClick: (Int) -> Unit, viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = {
        Surface(elevation = 4.dp, color = DSColors.primaryBackground) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.app_name),
                    style = DSTypography.headline,
                    color = DSColors.primaryText,
                )
                SearchInput(
                    value = state.keyword,
                    onValueChange = viewModel::onSearchInputChange,
                    onClearIconClick = {
                        viewModel.onSearchInputChange("")
                    },
                    placeholder = stringResource(R.string.search_by_keyword),
                    showLeadingIcon = true,
                    onSearch = { viewModel.searchWithKeyword() },
                )
            }
        }
    }) { rootPadding ->
        Column(
            modifier = Modifier
                .padding(rootPadding)
                .fillMaxSize(),
        ) {
            when (state.screenState) {
                LOADING -> LoadingProductListContent(10)
                ERROR -> ErrorScreen(viewModel::searchWithKeyword)
                NORMAL -> ProductListContent(state.allProducts, onItemClick)
                EMPTY -> Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(R.string.no_results_found),
                    style = DSTypography.body1Medium,
                    color = DSColors.primaryText
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(reload: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        BasicButton(
            text = stringResource(id = R.string.reload_from_server),
            onClick = reload,
        )
    }
}

@Composable
fun ProductListContent(products: List<Product>, onItemClick: (Int) -> Unit) {
    LazyColumn {
        items(items = products, key = { item -> item.hashCode() }) { product ->
            ProductContentWithThumbnail(
                productTitle = product.title ?: stringResource(id = R.string.unknown),
                productBrand = product.brand ?: stringResource(id = R.string.unknown),
                productPrice = product.price ?: 0,
                productStock = product.stock ?: 0,
                productImageUrl = product.thumbnail ?: "",
                onItemClick = { onItemClick(product.id) })
        }
    }
}

@Preview
@Composable
fun LoadingProductListContent(count: Int = 10) {
    val items = List(count) { it }
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    LazyColumn {
        itemsIndexed(items) { _, _ ->
            LoadingProductContentWithThumbnail(shimmer = shimmer)
        }
    }
}
