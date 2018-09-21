package com.arctouch.codechallenge.ui.activity.presenter;

import com.arctouch.codechallenge.entity.Movie;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public interface HomeActivityPresenter {

    void getUpcomingMovies();
    void getMoreUpcomingMovies();
    void start();
    void getSearchMovies(String query);
    void getMoreSearchMovies(String query);
    void clearMoviesList();

    void clearCompositeDisposables();

    interface View {
        void refreshMoviesList(List<Movie> movies);
        void addMoviesToList(List<Movie> movies);
        void goToListTop();
    }
}
