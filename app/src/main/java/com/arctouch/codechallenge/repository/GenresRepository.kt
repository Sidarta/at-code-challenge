package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.entity.Genre

import io.reactivex.Observable

interface GenresRepository {
    val genres: Observable<List<Genre>>
}
