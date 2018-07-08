package com.arctouch.codechallenge.ui.activity.presenter.implementation;

import android.util.Log;

import com.arctouch.codechallenge.callback.OnGetGenres;
import com.arctouch.codechallenge.callback.OnGetSearchMovies;
import com.arctouch.codechallenge.callback.OnGetUpcomingMovies;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.entity.Genre;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.GenresRepository;
import com.arctouch.codechallenge.repository.SearchMoviesRepository;
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository;
import com.arctouch.codechallenge.repository.implementation.GenresRepositoryImplementation;
import com.arctouch.codechallenge.repository.implementation.SearchMoviesRepositoryImplementation;
import com.arctouch.codechallenge.repository.implementation.UpcomingMoviesRepositoryImplementation;
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityPresenterImplementation implements HomeActivityPresenter, OnGetUpcomingMovies, OnGetGenres, OnGetSearchMovies{

    private static final String TAG = "HomeActivity Pres.";

    private HomeActivityPresenter.View mView;
    private int mPage = 0;


    private UpcomingMoviesRepository upcomingMoviesRepository;
    private GenresRepository genresRepository;
    private SearchMoviesRepository searchMoviesRepository;

    public HomeActivityPresenterImplementation(View mView) {
        upcomingMoviesRepository = new UpcomingMoviesRepositoryImplementation();
        genresRepository = new GenresRepositoryImplementation();
        searchMoviesRepository = new SearchMoviesRepositoryImplementation();
        this.mView = mView;
    }

    @Override
    public void getUpcomingMovies() {
        upcomingMoviesRepository.getUpcomingMovies(this);
    }

    @Override
    public void getMoreUpcomingMovies() {
        upcomingMoviesRepository.getUpcomingMovies(this.mPage, this);
    }

    @Override
    public void start() {
        //get genres first to update cache
        genresRepository.getGenres(this);
    }

    @Override
    public void getSearchMovies(String query) {
        searchMoviesRepository.getSearchMovies(query, this);
    }

    @Override
    public void getMoreSearchMovies(String query) {
        searchMoviesRepository.getSearchMovies(query, this.mPage, this);
    }

    @Override
    public void clearMoviesList() {
        mView.refreshMoviesList(new ArrayList<Movie>());
    }

    @Override
    public void onGetUpcomingMoviesSuccessful(UpcomingMoviesResponse upcomingMoviesResponse) {
        mPage = upcomingMoviesResponse.getPage();

        for (Movie movie : upcomingMoviesResponse.getResults()) {
            for (Genre genre : Cache.getGenres()) {
                if (movie.getGenreIds().contains(genre.getId())) {
                    movie.getGenres().add(genre);
                }
            }
        }

        if(mPage == 1) {
            mView.refreshMoviesList(upcomingMoviesResponse.getResults());
        } else {
            mView.addMoviesToList(upcomingMoviesResponse.getResults());
        }
    }

    @Override
    public void onGetUpcomingMoviesFailed(String errorMsg) {
        Log.e(TAG, errorMsg);
    }

    @Override
    public void onGetGenresSuccessful(List<Genre> genres) {
        //after genres fetch, we keep the initialization going - fetch upcoming movies
        this.getUpcomingMovies();
    }

    @Override
    public void onGetGenresFailed(String errorMsg) {
        Log.e(TAG, errorMsg);
    }

    @Override
    public void onGetSearchMoviesSuccessful(UpcomingMoviesResponse upcomingMoviesResponse) {
        mPage = upcomingMoviesResponse.getPage();

        for (Movie movie : upcomingMoviesResponse.getResults()) {
            for (Genre genre : Cache.getGenres()) {
                if (movie.getGenreIds().contains(genre.getId())) {
                    movie.getGenres().add(genre);
                }
            }
        }

        if(mPage == 1) {
            mView.refreshMoviesList(upcomingMoviesResponse.getResults());
        } else {
            mView.addMoviesToList(upcomingMoviesResponse.getResults());
        }
    }

    @Override
    public void onGetSearchMoviesFailed(String errorMsg) {
        Log.e(TAG, errorMsg);
    }
}
