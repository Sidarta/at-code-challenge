package com.arctouch.codechallenge.ui.activity.presenter;

import com.arctouch.codechallenge.entity.Movie;

import java.util.List;

public interface HomeActivityPresenter {

    void getUpcomingMovies();
    void getMoreUpcomingMovies();
    void start();

    interface View {
        void refreshMoviesList(List<Movie> movies);
        void addMoviesToList(List<Movie> movies);
        void goToListTop();
    }
}
