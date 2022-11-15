package com.abhinav.shoppy.designsystem

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest

// Extension functions
fun Int.formatToPrice(): String {
    return "$${this.toString().reversed().chunked(3).joinToString(",").reversed()}"
}

fun Int.formatToStock(): String? = if (this > 0) toString() else null

fun String.getImageRequest(context: Context) = ImageRequest.Builder(context)
    .data(this)
    .memoryCacheKey(this)
    .diskCacheKey(this)
    .diskCachePolicy(CachePolicy.ENABLED)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .build()
