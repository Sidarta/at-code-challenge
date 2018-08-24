package com.arctouch.codechallenge.repository.implementation;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.api.TmdbApiClient;
import com.arctouch.codechallenge.callback.OnGetUpcomingMovies;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingMoviesRepositoryImplementation implements UpcomingMoviesRepository {

    private TmdbApi service;

    public UpcomingMoviesRepositoryImplementation() {
        service = TmdbApiClient.createService(TmdbApi.class);
    }


    @Override
    public Observable<UpcomingMoviesResponse> getUpcomingMovies() {
        return service.upcomingMovies((long)1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UpcomingMoviesResponse> getUpcomingMovies(int page) {
        return service.upcomingMovies((long)page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
