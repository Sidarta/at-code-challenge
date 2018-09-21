package com.arctouch.codechallenge.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.ui.activity.presenter.MovieDetailsActivityPresenter;
import com.arctouch.codechallenge.ui.activity.presenter.implementation.MovieDetailsActivityPresenterImplementation;
import com.arctouch.codechallenge.util.Constants;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.reactivex.disposables.CompositeDisposable;

//TODO localize release date

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsActivityPresenter.View{

    @BindView(R.id.movie_backdrop)
    ImageView mImgViewMovieBackDrop;

    @BindView(R.id.nested_scroll_view_movie_detail)
    NestedScrollView mNsvDetailsBody;

    @BindView(R.id.movie_poster)
    ImageView mImgViewMoviePoster;

    @BindView(R.id.movie_name)
    TextView mTxtViewMovieName;

    @BindView(R.id.movie_genres)
    TextView mTxtViewMovieGenres;

    @BindView(R.id.movie_duration)
    TextView mTxtViewMovieDuration;

    @BindView(R.id.movie_tagline)
    TextView mTxtViewMovieTagline;

    @BindView(R.id.movie_overview)
    TextView mTxtMovieOverview;

    @BindView(R.id.movie_release_date)
    TextView mTxtViewMovieReleaseDate;

    @BindView(R.id.movie_imdb_link)
    TextView mTxtViewImdbLink;

    @BindView(R.id.progress_bar)
    SmoothProgressBar mProgressBar;

    MovieDetailsActivityPresenter mMovieDetailsActivityPresenter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        mMovieDetailsActivityPresenter = new MovieDetailsActivityPresenterImplementation(this);

        updateProgressBar(true);
        mMovieDetailsActivityPresenter.start(getIntent());
    }

    @Override
    public void showMovieDetails(Movie movie) {
        //texts

        mTxtViewMovieName.setText(movie.getTitle());
        mTxtViewMovieGenres.setText(TextUtils.join(", ", movie.getGenres()));
        String minutesDutarion = movie.getDuration() != null ? movie.getDuration() + getString(R.string.minutes_abbreviation) : ""; //there are some movies with null duration
        mTxtViewMovieDuration.setText(minutesDutarion);
        mTxtViewMovieTagline.setText(movie.getTagline());
        mTxtMovieOverview.setText(movie.getOverview());
        mTxtViewMovieReleaseDate.setText(movie.getReleaseDate());
        mTxtViewImdbLink.setText(String.format(Constants.IMDB_MOVIE_URL, movie.getImdbid()));

        //images
        MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

        String backdropPath = movie.getBackdropPath();
        if (!TextUtils.isEmpty(backdropPath)) {
            Glide.with(mImgViewMovieBackDrop)
                    .load(movieImageUrlBuilder.buildBackdropUrl(backdropPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder)) //TODO ugly ph and it blinks
                    .into(mImgViewMovieBackDrop);
        }

        String posterPath = movie.getPosterPath();
        if (!TextUtils.isEmpty(posterPath)) {
            Glide.with(mImgViewMoviePoster)
                    .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder)) //TODO ugly ph and it blinks
                    .into(mImgViewMoviePoster);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateProgressBar(false);
    }

    @Override
    public void updateProgressBar(boolean show) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            if (show) {
                mProgressBar.progressiveStart();
            } else {
                mProgressBar.progressiveStop();
            }
        }
    }

    // implement this if we want to handle more complex data on screen rotation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mMovieDetailsActivityPresenter.clearCompositeDisposable();
    }
}
