package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.entity.Genre;

import java.util.List;

import io.reactivex.Observable;

public interface GenresRepository {
    Observable<List<Genre>> getGenres();
}
