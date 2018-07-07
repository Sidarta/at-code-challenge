package com.arctouch.codechallenge.api;

import com.arctouch.codechallenge.entity.GenreResponse;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    String URL = "https://api.themoviedb.org/3/";
    String API_KEY = "1f54bd990f1cdfb230adb312546d765d";
    String DEFAULT_LANGUAGE = "";
    String DEFAULT_REGION = ""; //TODO improve this - empty because localizing it diminishes the results

    @GET("genre/movie/list")
    Call<GenreResponse> genres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Call<UpcomingMoviesResponse> upcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Long page,
            @Query("region") String region
    );

    @GET("movie/{id}")
    Call<Movie> movie(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}