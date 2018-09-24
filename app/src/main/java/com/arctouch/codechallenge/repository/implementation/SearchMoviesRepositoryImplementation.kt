package com.arctouch.codechallenge.repository.implementation

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiClient
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.SearchMoviesRepository

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchMoviesRepositoryImplementation : SearchMoviesRepository {

    private val service: TmdbApi = TmdbApiClient.createService(TmdbApi::class.java)


    override fun getSearchMovies(query: String): Maybe<UpcomingMoviesResponse> {
        return service.searchedMovies(query, 1.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSearchMovies(query: String, page: Int): Maybe<UpcomingMoviesResponse> {
        return service.searchedMovies(query, page.toLong() + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
