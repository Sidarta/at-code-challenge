package com.arctouch.codechallenge.callback;

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

public interface OnGetSearchMovies {
    void onGetSearchMoviesSuccessful(UpcomingMoviesResponse upcomingMoviesResponse);
    void onGetSearchMoviesFailed(String errorMsg);
}
