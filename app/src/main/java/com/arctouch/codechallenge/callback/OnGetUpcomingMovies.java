package com.arctouch.codechallenge.callback;

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

public interface OnGetUpcomingMovies {
    void onGetUpcomingMoviesSuccessful(UpcomingMoviesResponse upcomingMoviesResponse);
    void onGetUpcomingMoviesFailed(String errorMsg);
}
