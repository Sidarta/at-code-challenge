package com.arctouch.codechallenge.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.entity.GenreResponse;
import com.arctouch.codechallenge.ui.activity.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    //TODO get rid of this splash screen - this is just to test

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Context mContext = this;

        TmdbApi api = new TmdbApiClient().getClient().create(TmdbApi.class);
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                Cache.setGenres(response.body().getGenres());
                startActivity(new Intent(mContext, HomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Log.e("Splash", "Failed to load genres");
            }
        });

    }
}
