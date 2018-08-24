package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

import io.reactivex.Maybe;

public interface SearchMoviesRepository {
    Maybe<UpcomingMoviesResponse> getSearchMovies(String query);
    Maybe<UpcomingMoviesResponse> getSearchMovies(String query, int page);
}
