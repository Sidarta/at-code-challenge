package com.arctouch.codechallenge.ui.activity.presenter

import android.content.Intent

import com.arctouch.codechallenge.entity.Movie

interface MovieDetailsActivityPresenter {

    fun getMovie(movieId: Int)
    fun start(intent: Intent)

    fun clearCompositeDisposable()

    interface View {
        fun showMovieDetails(movie: Movie)
        fun updateProgressBar(show: Boolean)
    }

}
