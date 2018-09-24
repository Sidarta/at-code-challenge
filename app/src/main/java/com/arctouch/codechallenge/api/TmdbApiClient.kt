package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.api.interceptors.ApiKeyInterceptor
import com.arctouch.codechallenge.api.interceptors.LanguageAndRegionInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object TmdbApiClient {

    private val builder = Retrofit.Builder()
            .baseUrl(TmdbApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val apiKey = ApiKeyInterceptor()

    private val languageAndRegion = LanguageAndRegionInterceptor()

    private val httpClient = OkHttpClient.Builder()

    //getting context on createService so we can create a cache dir
    fun <S> createService(serviceClass: Class<S>): S {

        httpClient.interceptors().clear() //so we dont add duplicates

        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(apiKey)

        builder.client(httpClient.build())

        /**
         * Removing localization interceptor for testing purposes: localized queries return few results
         * and makes it difficult to test the infinite scrolling.
         * On ideal scenario, we would use this interceptor to add localization parameters to query
         */
        //        if(!httpClient.interceptors().contains(languageAndRegion)){
        //            httpClient.addInterceptor(languageAndRegion);
        //            builder.client(httpClient.build());
        //            retrofit = builder.build();
        //        }

        return builder.build().create(serviceClass)
    }
}