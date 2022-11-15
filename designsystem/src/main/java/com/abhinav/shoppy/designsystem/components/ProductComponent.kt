package com.abhinav.shoppy.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abhinav.shoppy.designsystem.R
import com.abhinav.shoppy.designsystem.formatToPrice
import com.abhinav.shoppy.designsystem.formatToStock
import com.abhinav.shoppy.designsystem.getImageRequest
import com.abhinav.shoppy.designsystem.theme.DSColors
import com.abhinav.shoppy.designsystem.theme.DSTypography
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

// Reusable Components
@Composable
fun ProductContentWithThumbnail(
    modifier: Modifier = Modifier,
    productTitle: String = stringResource(id = R.string.unknown),
    productBrand: String = stringResource(id = R.string.unknown),
    productImageUrl: String = "",
    productPrice: Int = 0,
    productStock: Int = 0,
    onItemClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(vertical = 12.dp)) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp),
                model = productImageUrl.getImageRequest(context),
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.image))
            ProductContent(
                modifier = Modifier.padding(start = 12.dp, end = 16.dp),
                productTitle,
                productBrand,
                productPrice,
                productStock
            )
        }
        Divider(thickness = 1.dp, color = DSColors.border)
    }
}

@Composable
fun ProductContent(
    modifier: Modifier = Modifier,
    productTitle: String = stringResource(id = R.string.unknown),
    productBrand: String = stringResource(id = R.string.unknown),
    productPrice: Int = 0,
    productStock: Int = 0,
) {
    Column(modifier = modifier) {
        Text(
            text = productTitle,
            style = DSTypography.body1Medium,
            color = DSColors.primaryText,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.brand_name, productBrand),
            style = DSTypography.body2Regular,
            color = DSColors.secondaryText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Row {
            Column(modifier = Modifier
                .padding(end = 8.dp, top = 2.dp)
                .wrapContentHeight()) {
                Icon(
                    tint = DSColors.primary,
                    painter = painterResource(id = R.drawable.ic_price),
                    modifier = Modifier
                        .width(16.dp)
                        .padding(bottom = 8.dp),
                    contentDescription = stringResource(id = R.string.price)
                )

                Icon(
                    tint = DSColors.primary,
                    painter = painterResource(id = R.drawable.ic_stock),
                    modifier = Modifier.size(16.dp),
                    contentDescription = stringResource(id = R.string.stock_remaining)
                )
            }
            Column {
                Text(
                    text = stringResource(id = R.string.price),
                    style = DSTypography.body2Medium,
                    color = DSColors.primaryText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.stock_remaining),
                    style = DSTypography.body2Regular,
                    color = DSColors.primaryText,
                )
            }
            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    text = productPrice.formatToPrice(),
                    style = DSTypography.body2Medium,
                    color = DSColors.primaryText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = productStock.formatToStock()
                        ?: stringResource(id = R.string.out_of_stock),
                    style = DSTypography.body2Regular,
                    color = DSColors.primaryText,
                )
            }
        }
    }
}

@Composable
fun LoadingProductContentWithThumbnail(shimmer: Shimmer, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(vertical = 12.dp, horizontal = 16.dp)
        .fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .shimmer(shimmer)
                .background(DSColors.primaryForegroundText)
        )
        LoadingProductContent(shimmer = shimmer)
    }
}

@Composable
fun LoadingProductContent(
    shimmer: Shimmer,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(start = 12.dp, end = 16.dp)) {
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(20.dp)
                .padding(bottom = 2.dp)
                .shimmer(shimmer)
                .background(DSColors.primaryForegroundText)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .padding(bottom = 8.dp)
                .shimmer(shimmer)
                .background(DSColors.primaryForegroundText)
        )
        Row {
            Column(modifier = Modifier
                .padding(end = 8.dp, top = 2.dp)
                .wrapContentHeight()) {
                Icon(
                    tint = DSColors.primary,
                    painter = painterResource(id = R.drawable.ic_price),
                    modifier = Modifier
                        .width(16.dp)
                        .padding(bottom = 8.dp),
                    contentDescription = stringResource(id = R.string.price)
                )

                Icon(
                    tint = DSColors.primary,
                    painter = painterResource(id = R.drawable.ic_stock),
                    modifier = Modifier.size(16.dp),
                    contentDescription = stringResource(id = R.string.stock_remaining)
                )
            }
            Column {
                Text(
                    text = stringResource(id = R.string.price),
                    style = DSTypography.body2Medium,
                    color = DSColors.primaryText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.stock_remaining),
                    style = DSTypography.body2Medium,
                    color = DSColors.primaryText,
                )
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(bottom = 0.dp)
                        .shimmer(shimmer)
                        .background(DSColors.primaryForegroundText)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(top = 8.dp)
                        .shimmer(shimmer)
                        .background(DSColors.primaryForegroundText)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductPreview() {
    ProductContentWithThumbnail()
}
