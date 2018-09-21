package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.SearchMoviesRepository;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchMoviesRepositoryImplementation implements SearchMoviesRepository{

    private TmdbApi service;

    public SearchMoviesRepositoryImplementation() {
        service = TmdbApiClient.createService(TmdbApi.class);
    }


    @Override
    public Maybe<UpcomingMoviesResponse> getSearchMovies(String query) {
        return service.searchedMovies(query, (long)1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Maybe<UpcomingMoviesResponse> getSearchMovies(String query, int page) {
        return service.searchedMovies(query, (long) page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
