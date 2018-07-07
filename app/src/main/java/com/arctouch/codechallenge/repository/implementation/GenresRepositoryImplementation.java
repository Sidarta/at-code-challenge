package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.callback.OnGetGenres;
import com.arctouch.codechallenge.entity.GenreResponse;
import com.arctouch.codechallenge.repository.GenresRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenresRepositoryImplementation implements GenresRepository {

    private TmdbApi service;

    public GenresRepositoryImplementation() {
        TmdbApiClient client = new TmdbApiClient();
        this.service = client.getClient().create(TmdbApi.class);
    }

    @Override
    public void getGenres(OnGetGenres onGetGenres) {
        service.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful()) {
                    GenreResponse gr = response.body();
                    onGetGenres.onGetGenresSuccessful(gr.getGenres());
                } else {
                    onGetGenres.onGetGenresFailed("Response not successful");
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                onGetGenres.onGetGenresFailed("Get genres failed: " + t.getMessage());
            }
        });
    }
}
