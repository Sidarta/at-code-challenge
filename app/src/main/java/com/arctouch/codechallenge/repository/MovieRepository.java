package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.callback.OnGetMovie;

public interface MovieRepository {
    void getMovie(Long movieId, OnGetMovie onGetMovie);
}
