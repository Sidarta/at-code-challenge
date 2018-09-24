package com.arctouch.codechallenge.repository.implementation

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiClient
import com.arctouch.codechallenge.entity.Movie
import com.arctouch.codechallenge.repository.MovieRepository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepositoryImplementation : MovieRepository {

    private val service: TmdbApi = TmdbApiClient.createService(TmdbApi::class.java)

    override fun getMovie(movieId: Long?): Observable<Movie> {
        return service
                .movie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
