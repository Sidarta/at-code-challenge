package com.arctouch.codechallenge.repository.implementation

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiClient
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingMoviesRepositoryImplementation : UpcomingMoviesRepository {

    private val service: TmdbApi = TmdbApiClient.createService(TmdbApi::class.java)


    override val upcomingMovies: Observable<UpcomingMoviesResponse>
        get() = service.upcomingMovies(1.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun getUpcomingMovies(page: Int): Observable<UpcomingMoviesResponse> {
        return service.upcomingMovies(page.toLong() + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
