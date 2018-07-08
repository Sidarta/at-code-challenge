package com.arctouch.codechallenge.ui.activity.presenter;

import android.content.Intent;

import com.arctouch.codechallenge.entity.Movie;

public interface MovieDetailsActivityPresenter {

    void getMovie(int movieId);
    void start(Intent intent);

    interface View {
        void showMovieDetails(Movie movie);
        void updateProgressBar(boolean show);
    }

}
