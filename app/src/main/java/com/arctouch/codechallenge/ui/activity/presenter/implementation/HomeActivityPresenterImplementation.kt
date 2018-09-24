package com.arctouch.codechallenge.ui.activity.presenter.implementation

import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.GenresRepository
import com.arctouch.codechallenge.repository.SearchMoviesRepository
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository
import com.arctouch.codechallenge.repository.implementation.GenresRepositoryImplementation
import com.arctouch.codechallenge.repository.implementation.SearchMoviesRepositoryImplementation
import com.arctouch.codechallenge.repository.implementation.UpcomingMoviesRepositoryImplementation
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class HomeActivityPresenterImplementation(private val mView: HomeActivityPresenter.View, compositeDisposable: CompositeDisposable) : HomeActivityPresenter {
    private var mPage = 0


    private val upcomingMoviesRepository: UpcomingMoviesRepository
    private val genresRepository: GenresRepository
    private val searchMoviesRepository: SearchMoviesRepository

    private val compositeDisposable = CompositeDisposable()

    init {
        upcomingMoviesRepository = UpcomingMoviesRepositoryImplementation()
        genresRepository = GenresRepositoryImplementation()
        searchMoviesRepository = SearchMoviesRepositoryImplementation()
    }

    override fun getUpcomingMovies() {
        compositeDisposable.add(
                upcomingMoviesRepository.upcomingMovies
                        .subscribe { this.handleMoviesResponse(it) }
        )
    }

    override fun getMoreUpcomingMovies() {
        compositeDisposable.add(
                upcomingMoviesRepository.getUpcomingMovies(this.mPage)
                        .subscribe { this.handleMoviesResponse(it) }
        )
    }

    override fun start() {
        //get genres then movies, to make sure genres cache is filled
        compositeDisposable.add(
                genresRepository.genres
                        .concatMap { upcomingMoviesRepository.upcomingMovies }
                        .subscribe { this.handleMoviesResponse(it) }
        )
    }

    override fun getSearchMovies(query: String) {
        compositeDisposable.add(
                searchMoviesRepository.getSearchMovies(query)
                        .onErrorComplete()
                        .subscribe { this.handleMoviesResponse(it) }
        )
    }

    override fun getMoreSearchMovies(query: String) {
        compositeDisposable.add(
                searchMoviesRepository.getSearchMovies(query, this.mPage)
                        .onErrorComplete()
                        .subscribe { this.handleMoviesResponse(it) }
        )
    }

    override fun clearMoviesList() {
        mView.refreshMoviesList(ArrayList())
    }

    override fun clearCompositeDisposables() {
        compositeDisposable.clear()
    }

    private fun handleMoviesResponse(upcomingMoviesResponse: UpcomingMoviesResponse) {
        mPage = upcomingMoviesResponse.page

        for (movie in upcomingMoviesResponse.results!!) {
            for (genre in Cache.getGenres()) {
                if (movie.genreIds.contains(genre.id)) {
                    movie.genres.add(genre)
                }
            }
        }

        if (mPage == 1) {
            mView.refreshMoviesList(upcomingMoviesResponse.results!!)
        } else {
            mView.addMoviesToList(upcomingMoviesResponse.results!!)
        }
    }

    companion object {

        private const val TAG = "HomeActivity Pres."
    }
}
