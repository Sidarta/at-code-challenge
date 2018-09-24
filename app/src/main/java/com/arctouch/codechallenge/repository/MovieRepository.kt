package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.entity.Movie

import io.reactivex.Observable

interface MovieRepository {
    fun getMovie(movieId: Long?): Observable<Movie>
}
