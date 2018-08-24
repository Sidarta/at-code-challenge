package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

import io.reactivex.Observable;

public interface UpcomingMoviesRepository {
    Observable<UpcomingMoviesResponse> getUpcomingMovies();
    Observable<UpcomingMoviesResponse> getUpcomingMovies(int page);
}
