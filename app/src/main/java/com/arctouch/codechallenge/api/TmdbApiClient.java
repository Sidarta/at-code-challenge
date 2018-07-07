package com.arctouch.codechallenge.api;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class TmdbApiClient {

    public Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
       return retrofit;
    }




}
