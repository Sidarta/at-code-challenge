package com.arctouch.codechallenge.api.interceptors

import android.content.Context
import com.arctouch.codechallenge.util.InternetUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/***
 * TODO trying to handle network with an interceptor - question about using context here
 */
abstract class NetworkHandlingInterceptor(private val mContex: Context) : Interceptor {

    abstract fun onInternetUnavailable()
    abstract fun onCacheUnavailable()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (!InternetUtils.isInternetAvailable(mContex)) {
            val requestBuilder = original.newBuilder()
                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24)
            val request = requestBuilder.build()
            val response = chain.proceed(request)

            return response
        }

        return chain.proceed(original)
    }
}
