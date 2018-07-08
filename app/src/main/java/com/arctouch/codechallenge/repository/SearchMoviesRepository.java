package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.callback.OnGetSearchMovies;

public interface SearchMoviesRepository {
    void getSearchMovies(String query, OnGetSearchMovies onGetSearchMovies);
    void getSearchMovies(String query, int page, OnGetSearchMovies onGetSearchMovies);
}
