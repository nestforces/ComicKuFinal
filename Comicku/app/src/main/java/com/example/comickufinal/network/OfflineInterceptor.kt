package com.example.comickufinal.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

abstract class OfflineInterceptor : Interceptor {
    private val HEADER_CACHE_CONTROL = "Cache-Control"
    private val HEADER_PRAGMA = "Pragma"
    abstract fun isInternetAvailable(): Int
    abstract fun onInternetUnavailable()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (isInternetAvailable() == 0) {
            onInternetUnavailable()

            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }
        return chain.proceed(request)
    }
}