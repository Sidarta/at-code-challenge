package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse

import io.reactivex.Maybe

interface SearchMoviesRepository {
    fun getSearchMovies(query: String): Maybe<UpcomingMoviesResponse>
    fun getSearchMovies(query: String, page: Int): Maybe<UpcomingMoviesResponse>
}
