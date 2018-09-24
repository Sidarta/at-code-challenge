package com.arctouch.codechallenge.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class LanguageAndRegionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val language = Locale.getDefault().toString()
        val region = Locale.getDefault().country

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("language", language)
                .addQueryParameter("region", region)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {

        private val TAG = "LanguageInterceptor"
    }
}
