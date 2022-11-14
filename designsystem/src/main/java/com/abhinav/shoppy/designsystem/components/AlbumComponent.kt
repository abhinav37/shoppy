package com.abhinav.shoppy.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abhinav.shoppy.designsystem.R
import com.abhinav.shoppy.designsystem.getImageRequest
import com.abhinav.shoppy.designsystem.theme.DSColors
import com.abhinav.shoppy.designsystem.theme.DSTypography
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun AlbumContent(
    modifier: Modifier = Modifier,
    imageList: List<String> = emptyList(),
) {
    val context = LocalContext.current
    Column(modifier) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(id = R.string.product_photos_title_label),
            style = DSTypography.headline,
            color = DSColors.primaryText
        )
        if (imageList.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(2.dp),
                    model = imageList.getOrNull(0)?.getImageRequest(context),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = stringResource(id = R.string.image)
                )
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(2.dp),
                    model = imageList.getOrNull(1)?.getImageRequest(context),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = stringResource(id = R.string.image)
                )
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(2.dp),
                    model = imageList.getOrNull(2)?.getImageRequest(context),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = stringResource(id = R.string.image)
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.no_photos_found),
                style = DSTypography.body2Regular,
                color = DSColors.secondaryText
            )
        }
    }
}

@Composable
fun LoadingAlbumContent(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
    count: Int = 2,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.product_photos_title_label),
            style = DSTypography.headline,
            color = DSColors.primaryText
        )
        LazyVerticalGrid(columns = GridCells.Fixed(integerResource(id = R.integer.album_columns))) {
            items(count) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .padding(2.dp)
                        .shimmer(shimmer)
                        .background(DSColors.primaryForegroundText)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAlbumListContent(count: Int = 4) {
    val items = List(count) { it }
    LazyColumn {
        itemsIndexed(items) { _, _ ->
            AlbumContent()
        }
    }
}
