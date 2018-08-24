package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.entity.Movie;

import io.reactivex.Observable;

public interface MovieRepository {
    Observable<Movie> getMovie(Long movieId);
}
