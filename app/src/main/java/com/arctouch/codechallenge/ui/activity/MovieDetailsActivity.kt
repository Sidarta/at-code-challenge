package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.entity.Movie
import com.arctouch.codechallenge.ui.activity.presenter.MovieDetailsActivityPresenter
import com.arctouch.codechallenge.ui.activity.presenter.implementation.MovieDetailsActivityPresenterImplementation
import com.arctouch.codechallenge.ui.adapter.genreNames
import com.arctouch.codechallenge.util.Constants
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.util.*

//TODO localize release date

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsActivityPresenter.View {

    private lateinit var mMovieDetailsActivityPresenter: MovieDetailsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        mMovieDetailsActivityPresenter = MovieDetailsActivityPresenterImplementation(this)

        updateProgressBar(true)
        mMovieDetailsActivityPresenter.start(intent)
    }

    override fun showMovieDetails(movie: Movie) {

        movie_name.text = movie.title
        movie_genres.text = TextUtils.join(", ", movie.genreNames())
        movie_duration.text = movie.duration
        movie_tagline.text = movie.tagline
        movie_overview.text = movie.overview
        movie_release_date.text = movie.releaseDate
        movie_imdb_link.text = String.format(Constants.IMDB_MOVIE_URL, movie.imdbid)

        //images
        val movieImageUrlBuilder = MovieImageUrlBuilder()

        val backdropPath = movie.backdropPath
        if (!TextUtils.isEmpty(backdropPath)) {
            Glide.with(movie_backdrop)
                    .load(movieImageUrlBuilder.buildBackdropUrl(backdropPath))
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder)) //TODO ugly ph and it blinks
                    .into(movie_backdrop)
        }

        val posterPath = movie.posterPath
        if (!TextUtils.isEmpty(posterPath)) {
            Glide.with(movie_poster)
                    .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder)) //TODO ugly ph and it blinks
                    .into(movie_poster)
        }

        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        updateProgressBar(false)
    }

    override fun updateProgressBar(show: Boolean) {
        progress_bar.visibility = if (show) View.VISIBLE else View.INVISIBLE
        if (show) {
            progress_bar.progressiveStart()
        } else {
            progress_bar.progressiveStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mMovieDetailsActivityPresenter.clearCompositeDisposable()
    }
}
