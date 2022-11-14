package com.abhinav.shoppy.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abhinav.shoppy.designsystem.components.AlbumContent
import com.abhinav.shoppy.designsystem.components.BasicButton
import com.abhinav.shoppy.designsystem.components.LoadingAlbumContent
import com.abhinav.shoppy.designsystem.components.LoadingDetailsContent
import com.abhinav.shoppy.designsystem.components.LoadingProductContent
import com.abhinav.shoppy.designsystem.components.ProductContent
import com.abhinav.shoppy.designsystem.components.ProductDetailsContent
import com.abhinav.shoppy.designsystem.getImageRequest
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
import com.valentinilk.shimmer.shimmer

@Composable
fun DetailsScreen(onBackClick: () -> Unit, viewModel: DetailsViewModel) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = {
        Surface(elevation = 4.dp, color = DSColors.primaryBackground) {
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = DSColors.primaryText)
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.details_title,
                        state.product.title ?: "Product"),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = DSTypography.headline,
                    color = DSColors.primaryText
                )
            }
        }
    }, bottomBar = {
        if (state.screenState == ERROR) {
            BasicButton(onClick = {
                viewModel.loadData()
            },
                text = stringResource(R.string.reload_from_server),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }) { rootPadding ->
        when (state.screenState) {
            LOADING -> DetailScreenLoading(modifier = Modifier.padding(rootPadding))
            NORMAL -> DetailScreenWithData(
                product = state.product,
                modifier = Modifier.padding(rootPadding)
            )
            ERROR, EMPTY -> Column(modifier = Modifier
                .fillMaxHeight()
                .padding(rootPadding),
                verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(id = R.string.error_text),
                    style = DSTypography.body1Medium,
                    color = DSColors.primaryText
                )
            }
        }

    }
}

@Composable
fun DetailScreenWithData(product: Product, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollableState = rememberScrollState()
    Column(modifier = modifier
        .verticalScroll(scrollableState)
        .wrapContentHeight()) {
        AsyncImage(
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(maxHeight = 200.dp),
            model = product.thumbnail?.getImageRequest(context),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = stringResource(id = R.string.image)
        )
        ProductContent(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            productTitle = product.title ?: stringResource(id = R.string.unknown),
            productBrand = product.brand ?: stringResource(id = R.string.unknown),
            productPrice = product.price ?: 0,
            productStock = product.stock ?: 0,
        )
        ProductDetailsContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            description = product.description ?: stringResource(id = R.string.unknown)
        )
        AlbumContent(
            modifier = Modifier.padding(top = 24.dp, start = 14.dp, end = 14.dp),
            imageList = product.images,
        )
    }
}

@Composable
fun DetailScreenLoading(modifier: Modifier = Modifier) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmer(shimmer)
                .background(DSColors.primaryForegroundText)
        )
        LoadingProductContent(
            shimmer = shimmer,
            modifier = Modifier.padding(vertical = 24.dp),
        )
        LoadingDetailsContent(
            shimmer = shimmer,
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        )
        LoadingAlbumContent(
            shimmer = shimmer,
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
        )
    }
}
