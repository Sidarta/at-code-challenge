package com.arctouch.codechallenge.ui.activity.presenter.implementation;

import android.content.Intent;

import com.arctouch.codechallenge.repository.MovieRepository;
import com.arctouch.codechallenge.repository.implementation.MovieRepositoryImplementation;
import com.arctouch.codechallenge.ui.activity.presenter.MovieDetailsActivityPresenter;
import com.arctouch.codechallenge.util.Constants;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsActivityPresenterImplementation implements MovieDetailsActivityPresenter{

    private MovieDetailsActivityPresenter.View mView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = "MovieDet Activity Pres.";

    public MovieDetailsActivityPresenterImplementation(View mView) {
        this.mView = mView;
    }

    @Override
    public void getMovie(int movieId) {
        MovieRepository repository = new MovieRepositoryImplementation();

        compositeDisposable.add(
            repository.getMovie((long)movieId)
                    .subscribe(movie -> mView.showMovieDetails(movie))
        );
    }

    @Override
    public void start(Intent intent) {
        int movieId = intent.getIntExtra(Constants.EXTRA_MOVIE_ID, -1);
        this.getMovie(movieId);
    }

    @Override
    public void clearCompositeDisposable() {
        compositeDisposable.clear();
    }
}
