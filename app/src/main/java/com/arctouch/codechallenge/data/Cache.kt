package com.arctouch.codechallenge.data

import com.arctouch.codechallenge.entity.Genre
import java.util.*

object Cache {

    private val genres = ArrayList<Genre>()

    fun getGenres(): List<Genre> {
        return genres
    }

    fun setGenres(genres: List<Genre>) {
        Cache.genres.clear()
        Cache.genres.addAll(genres)
    }
}
