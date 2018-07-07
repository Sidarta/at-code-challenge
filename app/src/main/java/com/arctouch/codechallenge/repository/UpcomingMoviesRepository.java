package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.callback.OnGetUpcomingMovies;

public interface UpcomingMoviesRepository {
    void getUpcomingMovies(OnGetUpcomingMovies onGetUpcomingMovies);
    void getUpcomingMovies(int page, OnGetUpcomingMovies onGetUpcomingMovies);
}
