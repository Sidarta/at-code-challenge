package com.arctouch.codechallenge.ui.adapter.presenter

import com.arctouch.codechallenge.entity.Movie

interface HomeAdapterPresenter {

    //

    interface View {
        fun refreshMoviesList(movies: List<Movie>)
        fun addMoviesToList(movies: List<Movie>)
    }
}
