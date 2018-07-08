package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.callback.OnGetSearchMovies;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.SearchMoviesRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMoviesRepositoryImplementation implements SearchMoviesRepository{

    private TmdbApi service;

    public SearchMoviesRepositoryImplementation() {
        service = TmdbApiClient.createService(TmdbApi.class);
    }

    @Override
    public void getSearchMovies(String query, OnGetSearchMovies onGetSearchMovies) {
        service.searchedMovies(query, (long) 1).enqueue(new Callback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                if(response.isSuccessful()){
                    onGetSearchMovies.onGetSearchMoviesSuccessful(response.body());
                } else {
                    onGetSearchMovies.onGetSearchMoviesFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesResponse> call, Throwable t) {
                onGetSearchMovies.onGetSearchMoviesFailed(t.getMessage());
            }
        });
    }

    @Override
    public void getSearchMovies(String query, int page, OnGetSearchMovies onGetSearchMovies) {
        service.searchedMovies(query, (long) page + 1).enqueue(new Callback<UpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesResponse> call, Response<UpcomingMoviesResponse> response) {
                if(response.isSuccessful()){
                    onGetSearchMovies.onGetSearchMoviesSuccessful(response.body());
                } else {
                    onGetSearchMovies.onGetSearchMoviesFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesResponse> call, Throwable t) {
                onGetSearchMovies.onGetSearchMoviesFailed(t.getMessage());
            }
        });
    }
}
