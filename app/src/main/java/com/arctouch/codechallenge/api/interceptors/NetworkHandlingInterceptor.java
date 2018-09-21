package com.arctouch.codechallenge.api.interceptors;

import android.content.Context;

import com.arctouch.codechallenge.util.InternetUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/***
 * TODO trying to handle network with an interceptor - question about using context here
 */
public abstract class NetworkHandlingInterceptor implements Interceptor {

    public abstract void onInternetUnavailable();
    public abstract void onCacheUnavailable();
    private Context mContex;


    public NetworkHandlingInterceptor(Context context) {
        mContex = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if(!InternetUtils.isInternetAvailable(mContex)){
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24);
            Request request = requestBuilder.build();
            Response response = chain.proceed(request);

            return response;
        }

        return chain.proceed(original);
    }
}
