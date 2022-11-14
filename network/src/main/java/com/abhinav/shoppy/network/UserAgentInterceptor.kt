package com.abhinav.shoppy.network

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

// Dummy User agent class
class UserAgentInterceptor : Interceptor {
    private val userAgent: String =
        "Shoppy/v1.1" + "(com.abhinav.shoppy;build:10000 Android SDK 33) Agent"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithUserAgent = request.newBuilder()
            .header(USER_AGENT, userAgent)
            .build()

        return chain.proceed(requestWithUserAgent)
    }

    companion object {
        private const val USER_AGENT = "User-Agent"
    }
}
