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

import io.reactivex.disposables.CompositeDisposable;

public class HomeActivityPresenterImplementation implements HomeActivityPresenter{

    private static final String TAG = "HomeActivity Pres.";

    private HomeActivityPresenter.View mView;
    private int mPage = 0;


    private UpcomingMoviesRepository upcomingMoviesRepository;
    private GenresRepository genresRepository;
    private SearchMoviesRepository searchMoviesRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomeActivityPresenterImplementation(View mView, CompositeDisposable compositeDisposable) {
        upcomingMoviesRepository = new UpcomingMoviesRepositoryImplementation();
        genresRepository = new GenresRepositoryImplementation();
        searchMoviesRepository = new SearchMoviesRepositoryImplementation();
        this.mView = mView;
    }

    @Override
    public void getUpcomingMovies() {
        compositeDisposable.add(
                upcomingMoviesRepository.getUpcomingMovies()
                        .subscribe(this::handleMoviesResponse)
        );
    }

    @Override
    public void getMoreUpcomingMovies() {
        compositeDisposable.add(
                upcomingMoviesRepository.getUpcomingMovies(this.mPage)
                .subscribe(this::handleMoviesResponse)
        );
    }

    @Override
    public void start() {
        //get genres then movies, to make sure genres cache is filled
        compositeDisposable.add(
            genresRepository.getGenres()
                    .concatMap(genres -> upcomingMoviesRepository.getUpcomingMovies()) //ugly because we're not using genres
                    .subscribe(this::handleMoviesResponse)
        );
    }

    @Override
    public void getSearchMovies(String query) {
        compositeDisposable.add(
            searchMoviesRepository.getSearchMovies(query)
                    .onErrorComplete()
                    .subscribe(this::handleMoviesResponse)
        );
    }

    @Override
    public void getMoreSearchMovies(String query) {
        compositeDisposable.add(
                searchMoviesRepository.getSearchMovies(query, this.mPage)
                        .onErrorComplete()
//                        .doOnError(Throwable::printStackTrace) //does not work because the onErrorComplete swallows the throwable
                        .subscribe(this::handleMoviesResponse)
        );
    }

    @Override
    public void clearMoviesList() {
        mView.refreshMoviesList(new ArrayList<Movie>());
    }

    @Override
    public void clearCompositeDisposables() {
        compositeDisposable.clear();
    }

    private void handleMoviesResponse(UpcomingMoviesResponse upcomingMoviesResponse){
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
}
