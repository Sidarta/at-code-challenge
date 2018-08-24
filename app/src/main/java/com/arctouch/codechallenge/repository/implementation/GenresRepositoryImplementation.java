package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.entity.Genre;
import com.arctouch.codechallenge.entity.GenreResponse;
import com.arctouch.codechallenge.repository.GenresRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GenresRepositoryImplementation implements GenresRepository {

    private TmdbApi service;

    public GenresRepositoryImplementation() {
        service = TmdbApiClient.createService(TmdbApi.class);
    }

    @Override
    public Observable<List<Genre>> getGenres() {
        return service.genres()
                .subscribeOn(Schedulers.io())
                .map(GenreResponse::getGenres)
                .doOnNext(Cache::setGenres)
                .observeOn(AndroidSchedulers.mainThread());
    }
}

