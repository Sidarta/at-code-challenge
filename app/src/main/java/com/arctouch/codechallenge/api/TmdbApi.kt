package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.entity.GenreResponse
import com.arctouch.codechallenge.entity.Movie
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    fun genres(): Observable<GenreResponse>
    //TODO should be maybe? all calls for genre are going to return values?

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("page") page: Long?
    ): Observable<UpcomingMoviesResponse>

    @GET("search/movie")
    fun searchedMovies(
            @Query("query") query: String,
            @Query("page") page: Long?
    ): Maybe<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
            @Path("id") id: Long?
    ): Observable<Movie>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
    }
}
