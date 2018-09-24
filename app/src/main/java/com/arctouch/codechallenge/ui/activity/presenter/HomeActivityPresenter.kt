package com.arctouch.codechallenge.ui.activity.presenter

import com.arctouch.codechallenge.entity.Movie

interface HomeActivityPresenter {

    fun getUpcomingMovies()
    fun getMoreUpcomingMovies()
    fun start()
    fun getSearchMovies(query: String)
    fun getMoreSearchMovies(query: String)
    fun clearMoviesList()

    fun clearCompositeDisposables()

    interface View {
        fun refreshMoviesList(movies: List<Movie>)
        fun addMoviesToList(movies: List<Movie>)
        fun goToListTop()
    }
}
