package com.arctouch.codechallenge.ui.activity.presenter.implementation;

import android.util.Log;

import com.arctouch.codechallenge.callback.OnGetUpcomingMovies;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.entity.Genre;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.entity.UpcomingMoviesResponse;
import com.arctouch.codechallenge.repository.UpcomingMoviesRepository;
import com.arctouch.codechallenge.repository.implementation.UpcomingMoviesRepositoryImplementation;
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter;

public class HomeActivityPresenterImplementation implements HomeActivityPresenter, OnGetUpcomingMovies {

    private HomeActivityPresenter.View mView;
    private int mPage = 0;

    public HomeActivityPresenterImplementation(View mView) {
        this.mView = mView;
    }

    @Override
    public void getUpcomingMovies() {
        UpcomingMoviesRepository repository = new UpcomingMoviesRepositoryImplementation();
        repository.getUpcomingMovies(this);
    }

    @Override
    public void getMoreUpcomingMovies() {
        UpcomingMoviesRepository repository = new UpcomingMoviesRepositoryImplementation();
        repository.getUpcomingMovies(this.mPage, this);
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
        //TODO need better message here?
        Log.e("Presenter", "Could not load movies");
    }
}
