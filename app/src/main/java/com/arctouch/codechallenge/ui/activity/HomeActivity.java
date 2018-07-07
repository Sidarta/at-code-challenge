package com.arctouch.codechallenge.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter;
import com.arctouch.codechallenge.ui.activity.presenter.implementation.HomeActivityPresenterImplementation;
import com.arctouch.codechallenge.ui.adapter.HomeAdapter;
import com.arctouch.codechallenge.ui.listener.HomeRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeActivityPresenter.View, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView mMoviesRecyclerView;
    private HomeAdapter mHomeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.home_swipe_refresh)
    SwipeRefreshLayout mHomeSwipeRefresh;

    @BindView(R.id.fab_top)
    FloatingActionButton mFabGoToTop;

    private HomeActivityPresenter mHomeActivityPresenter;

    private HomeRecyclerViewScrollListener mScrollListener;

    private MovieItemListener mMovieItemListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ButterKnife.bind(this);

        mHomeSwipeRefresh.setOnRefreshListener(this);

        mHomeActivityPresenter = new HomeActivityPresenterImplementation(this);

        //listener for clicks/taps on a movie on the movies list
        mMovieItemListener = movieClicked -> {
            //open new activity on click
            Log.d("HomeAct", "Movie tap action: " + movieClicked.toString());
        };

        mHomeAdapter = new HomeAdapter(new ArrayList<Movie>(), mMovieItemListener);
        mLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setAdapter(mHomeAdapter);
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);


        mScrollListener = new HomeRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            protected void onLoadMore(int currentPage, int totalItemCount, RecyclerView recyclerView) {
                mHomeActivityPresenter.getMoreUpcomingMovies();
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
        mHomeActivityPresenter.getUpcomingMovies();
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
}
