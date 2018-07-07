package com.arctouch.codechallenge.callback;

import com.arctouch.codechallenge.entity.Movie;

public interface OnGetMovie {
    void onGetMovieSuccessful(Movie movie);
    void onGetMovieFailed(String errorMsg);
}
