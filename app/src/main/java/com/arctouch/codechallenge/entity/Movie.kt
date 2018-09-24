package com.arctouch.codechallenge.entity

import com.squareup.moshi.Json
import java.util.*

//convert to a dataclass

data class Movie (
    var id: Int = 0,
    var title: String? = null,
    var overview: String? = null,
    var genres: MutableList<Genre> = ArrayList(),
    @Json(name = "genre_ids")
    var genreIds: List<Int> = ArrayList(),
    @Json(name = "poster_path")
    var posterPath: String = "",
    @Json(name = "backdrop_path")
    var backdropPath: String = "",
    @Json(name = "release_date")
    var releaseDate: String = "",
    @Json(name = "runtime")
    var duration: String = "",
    var tagline: String = "",
    @Json(name = "imdb_id")
    var imdbid: String = ""
)
