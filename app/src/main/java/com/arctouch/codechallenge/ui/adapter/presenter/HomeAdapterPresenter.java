package com.arctouch.codechallenge.ui.adapter.presenter;

import com.arctouch.codechallenge.entity.Movie;

import java.util.List;

public interface HomeAdapterPresenter {

    //

    interface View {
        void refreshMoviesList(List<Movie> movies);
        void addMoviesToList(List<Movie> movies);
    }
}
