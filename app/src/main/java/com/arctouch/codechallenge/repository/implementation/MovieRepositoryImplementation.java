package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.callback.OnGetMovie;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.repository.MovieRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositoryImplementation implements MovieRepository{

    private TmdbApi service;

    public MovieRepositoryImplementation() {
        TmdbApiClient client = new TmdbApiClient();
        this.service = client.getClient().create(TmdbApi.class);
    }


    @Override
    public void getMovie(Long movieId, OnGetMovie onGetMovie) {
        service.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()){
                    onGetMovie.onGetMovieSuccessful(response.body());
                } else {
                    onGetMovie.onGetMovieFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                onGetMovie.onGetMovieFailed("Get movie failed: " + t.getMessage());
            }
        });
    }
}
