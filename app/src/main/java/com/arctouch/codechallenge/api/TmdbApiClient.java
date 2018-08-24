package com.arctouch.codechallenge.api;

import com.arctouch.codechallenge.api.interceptors.ApiKeyInterceptor;
import com.arctouch.codechallenge.api.interceptors.LanguageAndRegionInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class TmdbApiClient {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(TmdbApi.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static ApiKeyInterceptor apiKey =
            new ApiKeyInterceptor();

    private static LanguageAndRegionInterceptor languageAndRegion =
            new LanguageAndRegionInterceptor();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {

        if(!httpClient.interceptors().contains(logging)){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        if(!httpClient.interceptors().contains(apiKey)){
            httpClient.addInterceptor(apiKey);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        /**
        Removing localization interceptor for testing purposes: localized queries return few results
        and makes it difficult to test the infinite scrolling.
        On ideal scenario, we would use this interceptor to add localization parameters to query
         */
//        if(!httpClient.interceptors().contains(languageAndRegion)){
//            httpClient.addInterceptor(languageAndRegion);
//            builder.client(httpClient.build());
//            retrofit = builder.build();
//        }

        return retrofit.create(serviceClass);
    }
}