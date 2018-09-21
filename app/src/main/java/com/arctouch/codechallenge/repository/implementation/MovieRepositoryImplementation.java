package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.repository.MovieRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepositoryImplementation implements MovieRepository{

    private TmdbApi service;

    public MovieRepositoryImplementation() {
        service = TmdbApiClient.createService(TmdbApi.class);
    }

    @Override
    public Observable<Movie> getMovie(Long movieId) {
        return service
                .movie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
