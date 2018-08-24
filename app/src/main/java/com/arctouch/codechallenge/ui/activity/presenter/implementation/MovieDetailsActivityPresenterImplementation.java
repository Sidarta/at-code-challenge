package com.arctouch.codechallenge.ui.activity.presenter.implementation;

import android.content.Intent;
import android.util.Log;

import com.arctouch.codechallenge.callback.OnGetMovie;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.repository.MovieRepository;
import com.arctouch.codechallenge.repository.implementation.MovieRepositoryImplementation;
import com.arctouch.codechallenge.ui.activity.presenter.MovieDetailsActivityPresenter;
import com.arctouch.codechallenge.util.Constants;

public class MovieDetailsActivityPresenterImplementation implements MovieDetailsActivityPresenter, OnGetMovie{

    private MovieDetailsActivityPresenter.View mView;

    private static final String TAG = "MovieDet Activity Pres.";

    public MovieDetailsActivityPresenterImplementation(View mView) {
        this.mView = mView;
    }

    @Override
    public void getMovie(int movieId) {
        MovieRepository repository = new MovieRepositoryImplementation();
        repository.getMovie((long) movieId, this);
    }

    @Override
    public void start(Intent intent) {
        int movieId = intent.getIntExtra(Constants.EXTRA_MOVIE_ID, -1);
        this.getMovie(movieId);
    }

    @Override
    public void onGetMovieSuccessful(Movie movie) {
        mView.showMovieDetails(movie);
    }

    @Override
    public void onGetMovieFailed(String errorMsg) {
        Log.e(TAG, errorMsg);
    }
}
