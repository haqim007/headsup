package dev.haqim.headsup.data.remote.util

import dev.haqim.headsup.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request
        val originalRequest = chain.request()

        // Add the token to the request URL
        val url = originalRequest
            .url
            .newBuilder()
            .addQueryParameter("apiKey", BuildConfig.TOKEN)
            .build()
        val request = originalRequest.newBuilder().url(url).build()

        // Proceed with the request
        return chain.proceed(request)
    }
}