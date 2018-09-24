package com.arctouch.codechallenge.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.entity.Movie
import com.arctouch.codechallenge.ui.activity.presenter.HomeActivityPresenter
import com.arctouch.codechallenge.ui.activity.presenter.implementation.HomeActivityPresenterImplementation
import com.arctouch.codechallenge.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.ui.listener.HomeRecyclerViewScrollListener
import com.arctouch.codechallenge.util.Constants
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.home_activity.*
import java.util.*
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), HomeActivityPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    private var mHomeAdapter: HomeAdapter? = null
    private val mLayoutManager = LinearLayoutManager(this)

    //presenter
    private var mHomeActivityPresenter: HomeActivityPresenter? = null

    //scroll listener for infinite scrolling
    private var mScrollListener: HomeRecyclerViewScrollListener? = null

    //list item listener for tap action
    private lateinit var mMovieItemListener: MovieItemListener

    //search view
    internal lateinit var mSearchView: SearchView

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        home_swipe_refresh.setOnRefreshListener(this)

        mHomeActivityPresenter = HomeActivityPresenterImplementation(this, compositeDisposable)

        //listener for clicks/taps on a movie on the movies list
//        mMovieItemListener = { movieClicked ->
//            //open new activity on click
//            val intent = Intent(this, MovieDetailsActivity::class.java)
//            intent.putExtra(Constants.EXTRA_MOVIE_ID, movieClicked.id)
//            this.startActivity(intent)
//            Log.d("HomeAct", "Movie tap action: " + movieClicked.toString())
//        }

        mMovieItemListener = object : MovieItemListener {
            override fun onMovieItemClick(movieClicked: Movie) {
                val intent = Intent(baseContext, MovieDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_MOVIE_ID, movieClicked.id)
                baseContext.startActivity(intent)
                Log.d("HomeAct", "Movie tap action: " + movieClicked.toString())
            }
        }


        mHomeAdapter = HomeAdapter(ArrayList(), mMovieItemListener)
        recyclerView.adapter = mHomeAdapter
        recyclerView.layoutManager = mLayoutManager


        mScrollListener = object : HomeRecyclerViewScrollListener(mLayoutManager) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int, recyclerView: RecyclerView?) {
                val query = mSearchView.query

                //if there is some text on query
                if (query != null && query.length > 0) {

                    //query needs to be bigger than 3 for the scrolling to happen
                    if (query.length >= 3) {
                        mHomeActivityPresenter!!.getMoreSearchMovies(query.toString())
                    }

                    //if no query, then normal infinite scrolling
                } else {
                    mHomeActivityPresenter!!.getMoreUpcomingMovies()
                }
            }

            override fun showTopButton(b: Boolean) {
                if (b) {
                    fab_top.visibility = View.VISIBLE
                } else {
                    fab_top.visibility = View.GONE
                }
            }
        }

        fab_top.setOnClickListener { goToListTop() }

        recyclerView.addOnScrollListener(mScrollListener)

        home_swipe_refresh.isRefreshing = true
        mHomeActivityPresenter!!.start()
        this.goToListTop()
    }

    //swipe refresh layout on refresh implementation
    override fun onRefresh() {
        home_swipe_refresh.isRefreshing = true
        mHomeActivityPresenter!!.getUpcomingMovies()
    }

    override fun refreshMoviesList(movies: List<Movie>) {
        mScrollListener!!.resetState()
        mHomeAdapter!!.refreshMoviesList(movies)
        home_swipe_refresh.isRefreshing = false
    }

    override fun addMoviesToList(movies: List<Movie>) {
        mHomeAdapter!!.addMoviesToList(movies)
    }

    override fun goToListTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    interface MovieItemListener {
        fun onMovieItemClick(movieClicked: Movie)
    }

    //search menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        //inflate
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        //searchview
        mSearchView = menu.findItem(R.id.search)
                .actionView as SearchView

        //hint text
        mSearchView.queryHint = getString(R.string.search_hint)

        RxSearchView
                .queryTextChangeEvents(mSearchView)
                .skipInitialValue()
                //                .observeOn(AndroidSchedulers.mainThread()) //TODO why no subscribe on and observe on?
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { textViewTextChanged -> textViewTextChanged.queryText().toString() }
                //                .subscribeOn(Schedulers.io())
                .subscribe(object : DisposableObserver<String>() { //TODO why lambda does not work here?
                    override fun onNext(s: String) {
                        mHomeActivityPresenter!!.getSearchMovies(s)
                        Log.d("search", "Text: $s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("search", "Error: " + e.message)
                    }

                    override fun onComplete() {
                        Log.d("search", "completed")
                    }
                })

        mSearchView.setOnSearchClickListener { v -> mHomeActivityPresenter!!.clearMoviesList() }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomeActivityPresenter!!.clearCompositeDisposables()
    }
}
