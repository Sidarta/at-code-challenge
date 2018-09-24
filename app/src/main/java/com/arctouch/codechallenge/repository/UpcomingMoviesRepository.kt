package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.entity.UpcomingMoviesResponse

import io.reactivex.Observable

interface UpcomingMoviesRepository {
    val upcomingMovies: Observable<UpcomingMoviesResponse>
    fun getUpcomingMovies(page: Int): Observable<UpcomingMoviesResponse>
}
