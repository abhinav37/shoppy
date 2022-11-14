package com.abhinav.shoppy.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abhinav.shoppy.designsystem.R
import com.abhinav.shoppy.designsystem.theme.DSColors
import com.abhinav.shoppy.designsystem.theme.DSTypography
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductDetailsContent(
    modifier: Modifier = Modifier,
    description: String = stringResource(id = R.string.unknown),
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(id = R.string.product_description_title_label),
            style = DSTypography.headline,
            color = DSColors.primaryText
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = description,
            style = DSTypography.body2Regular,
            color = DSColors.primaryText
        )
    }
}

@Composable
fun LoadingDetailsContent(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(id = R.string.product_description_title_label),
            style = DSTypography.headline,
            color = DSColors.primaryText
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(48.dp)
                .padding(vertical = 8.dp)
                .shimmer(shimmer)
                .background(DSColors.primaryForegroundText)
        )
    }
}
