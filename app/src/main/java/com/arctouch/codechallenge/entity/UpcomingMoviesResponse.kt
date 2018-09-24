package com.arctouch.codechallenge.entity

import com.squareup.moshi.Json

data class UpcomingMoviesResponse(var page: Int, var results: List<Movie>,
                             @Json(name = "total_pages") var totalPages: Int,
                             @Json(name = "total_results") var totalResults: Int)
