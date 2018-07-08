package com.arctouch.codechallenge.api.interceptors;

import java.io.IOException;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LanguageAndRegionInterceptor implements Interceptor{

    private static final String TAG = "LanguageInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        String language = Locale.getDefault().toString();
        String region = Locale.getDefault().getCountry();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("language", language)
                .addQueryParameter("region", region)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
