package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.callback.OnGetUpcomingMovies;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingMoviesRepositoryImplementation implements UpcomingMoviesRepository {

    private TmdbApi service;

    public UpcomingMoviesRepositoryImplementation() {
        TmdbApiClient client = new TmdbApiClient();
        this.service = client.getClient().create(TmdbApi.class);
    }

    @Override
    public void getUpcomingMovies(OnGetUpcomingMovies onGetUpcomingMovies) {
        service.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, (long) 1, TmdbApi.DEFAULT_REGION).enqueue(new Callback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                if(response.isSuccessful()){
                    onGetUpcomingMovies.onGetUpcomingMoviesSuccessful(response.body());
                } else {
                    onGetUpcomingMovies.onGetUpcomingMoviesFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesResponse> call, Throwable t) {
                onGetUpcomingMovies.onGetUpcomingMoviesFailed("Get upcoming movies failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void getUpcomingMovies(int page, OnGetUpcomingMovies onGetUpcomingMovies) {
        service.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, (long) page + 1, TmdbApi.DEFAULT_REGION).enqueue(new Callback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                if(response.isSuccessful()){
                    onGetUpcomingMovies.onGetUpcomingMoviesSuccessful(response.body());
                } else {
                    onGetUpcomingMovies.onGetUpcomingMoviesFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesResponse> call, Throwable t) {
                onGetUpcomingMovies.onGetUpcomingMoviesFailed("Get upcoming movies failed: " + t.getMessage());
            }
        });
    }
}
