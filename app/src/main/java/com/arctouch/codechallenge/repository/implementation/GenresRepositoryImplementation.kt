package com.arctouch.codechallenge.repository.implementation

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.TmdbApiClient
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.entity.Genre
import com.arctouch.codechallenge.repository.GenresRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GenresRepositoryImplementation : GenresRepository {

    private val service: TmdbApi = TmdbApiClient.createService(TmdbApi::class.java)

    override val genres: Observable<List<Genre>>
        get() = service.genres()
                .subscribeOn(Schedulers.io())
                .map {it.genres}
                .doOnNext { Cache.setGenres(it) }
                .observeOn(AndroidSchedulers.mainThread())

}

