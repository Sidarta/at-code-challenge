package com.arctouch.codechallenge.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter;
import com.arctouch.codechallenge.ui.activity.presenter.implementation.HomeActivityPresenterImplementation;
import com.arctouch.codechallenge.ui.adapter.HomeAdapter;
import com.arctouch.codechallenge.ui.listener.HomeRecyclerViewScrollListener;
import com.arctouch.codechallenge.util.Constants;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class HomeActivity extends AppCompatActivity implements HomeActivityPresenter.View, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView mMoviesRecyclerView;
    private HomeAdapter mHomeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.home_swipe_refresh)
    SwipeRefreshLayout mHomeSwipeRefresh;

    @BindView(R.id.fab_top)
    FloatingActionButton mFabGoToTop;

    //presenter
    private HomeActivityPresenter mHomeActivityPresenter;

    //scroll listener for infinite scrolling
    private HomeRecyclerViewScrollListener mScrollListener;

    //list item listener for tap action
    private MovieItemListener mMovieItemListener;

    //search view
    SearchView mSearchView;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ButterKnife.bind(this);

        mHomeSwipeRefresh.setOnRefreshListener(this);

        mHomeActivityPresenter = new HomeActivityPresenterImplementation(this, compositeDisposable);

        //listener for clicks/taps on a movie on the movies list
        mMovieItemListener = movieClicked -> {
            //open new activity on click
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_MOVIE_ID, movieClicked.getId());
            this.startActivity(intent);
            Log.d("HomeAct", "Movie tap action: " + movieClicked.toString());
        };

        mHomeAdapter = new HomeAdapter(new ArrayList<Movie>(), mMovieItemListener);
        mLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setAdapter(mHomeAdapter);
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);


        mScrollListener = new HomeRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            protected void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView) {
                CharSequence query = mSearchView.getQuery();

                //if there is some text on query
                if(query != null && query.length() > 0) {

                    //query needs to be bigger than 3 for the scrolling to happen
                    if(query.length() >= 3){
                        mHomeActivityPresenter.getMoreSearchMovies(query.toString());
                    }

                    //if no query, then normal infinite scrolling
                } else {
                    mHomeActivityPresenter.getMoreUpcomingMovies();
                }
            }

            @Override
            protected void showTopButton(boolean b) {
                if(b){
                    mFabGoToTop.setVisibility(View.VISIBLE);
                } else {
                    mFabGoToTop.setVisibility(View.GONE);
                }
            }
        };

        mFabGoToTop.setOnClickListener(v -> goToListTop());

        mMoviesRecyclerView.addOnScrollListener(mScrollListener);

        mHomeSwipeRefresh.setRefreshing(true);
        mHomeActivityPresenter.start();
        this.goToListTop();
    }

    //swipe refresh layout on refresh implementation
    @Override
    public void onRefresh() {
        mHomeSwipeRefresh.setRefreshing(true);
        mHomeActivityPresenter.getUpcomingMovies();
    }

    @Override
    public void refreshMoviesList(List<Movie> movies) {
        mScrollListener.resetState();
        mHomeAdapter.refreshMoviesList(movies);
        mHomeSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void addMoviesToList(List<Movie> movies) {
        mHomeAdapter.addMoviesToList(movies);
    }

    @Override
    public void goToListTop() {
        mMoviesRecyclerView.smoothScrollToPosition(0);
    }

    public interface MovieItemListener {
        void onMovieItemClick(Movie movieClicked);
    }

    //search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        //searchview
        mSearchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        //hint text
        mSearchView.setQueryHint(getString(R.string.search_hint));

        RxSearchView
                .queryTextChangeEvents(mSearchView)
                .skipInitialValue()
//                .observeOn(AndroidSchedulers.mainThread()) //TODO why no subscribe on and observe on?
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(textViewTextChanged -> textViewTextChanged.queryText().toString())
//                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<String>() { //TODO why lambda does not work here?
                    @Override
                    public void onNext(String s) {
                        mHomeActivityPresenter.getSearchMovies(s);
                        Log.d("search", "Text: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("search", "Error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("search", "completei rs");
                    }
                });

        // perform queries
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mSearchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                // we only perform queries bigger than 3 chars
//                if(newText.length() >= 3){
//
//                    //perform query here
//                    mHomeActivityPresenter.getSearchMovies(newText);
//                    Log.d("search", "Text: " + newText);
//
//                    return true;
//                }
//
//                return false;
//            }
//        });

        mSearchView.setOnSearchClickListener(v -> mHomeActivityPresenter.clearMoviesList());
        return true;
    }

    // implement this if we want to handle more complex data on screen rotation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeActivityPresenter.clearCompositeDisposables();
    }
}
