package com.arctouch.codechallenge.ui.activity.presenter.implementation

import android.content.Intent
import com.arctouch.codechallenge.repository.implementation.MovieRepositoryImplementation
import com.arctouch.codechallenge.ui.activity.presenter.MovieDetailsActivityPresenter
import com.arctouch.codechallenge.util.Constants
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsActivityPresenterImplementation(private val mView: MovieDetailsActivityPresenter.View) : MovieDetailsActivityPresenter {
    private val compositeDisposable = CompositeDisposable()

    override fun getMovie(movieId: Int) {
        val repository = MovieRepositoryImplementation()

        compositeDisposable.add(
                repository.getMovie(movieId.toLong())
                        .subscribe { movie -> mView.showMovieDetails(movie) }
        )
    }

    override fun start(intent: Intent) {
        val movieId = intent.getIntExtra(Constants.EXTRA_MOVIE_ID, -1)
        this.getMovie(movieId)
    }

    override fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

    companion object {

        private const val TAG = "MovieDet Activity Pres."
    }
}
