package com.arctouch.codechallenge.api;

import com.arctouch.codechallenge.entity.GenreResponse;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    String BASE_URL = "https://api.themoviedb.org/3/";
    String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    @GET("genre/movie/list")
    Observable<GenreResponse> genres();
    //TODO should be maybe? all calls for genre are going to return values?

    @GET("movie/upcoming")
    Observable<UpcomingMoviesResponse> upcomingMovies(
            @Query("page") Long page
    );

    @GET("search/movie")
    Maybe<UpcomingMoviesResponse> searchedMovies(
            @Query("query") String query,
            @Query("page") Long page
    );

    @GET("movie/{id}")
    Observable<Movie> movie(
            @Path("id") Long id
    );
}
