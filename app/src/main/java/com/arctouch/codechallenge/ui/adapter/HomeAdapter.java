package com.arctouch.codechallenge.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.entity.Movie;
import com.arctouch.codechallenge.ui.activity.HomeActivity;
import com.arctouch.codechallenge.ui.adapter.presenter.HomeAdapterPresenter;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements HomeAdapterPresenter.View{

    private List<Movie> movies;
    private HomeActivity.MovieItemListener mMovieItemListener;

    public HomeAdapter(List<Movie> movies, HomeActivity.MovieItemListener mMovieItemListener) {
        this.movies = movies;
        this.mMovieItemListener = mMovieItemListener;
    }

    @Override
    public void refreshMoviesList(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public void addMoviesToList(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

        private final TextView titleTextView;
        private final TextView genresTextView;
        private final TextView releaseDateTextView;
        private final ImageView posterImageView;
        private final CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genresTextView = itemView.findViewById(R.id.genresTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            cardView = itemView.findViewById(R.id.movie_card_view);
        }

        public void bind(Movie movie) {
            titleTextView.setText(movie.getTitle());
            genresTextView.setText(TextUtils.join(", ", movie.getGenres()));
            releaseDateTextView.setText(movie.getReleaseDate());

            String posterPath = movie.getPosterPath();
            if (!TextUtils.isEmpty(posterPath)) {
                Glide.with(itemView)
                        .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                        .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(posterImageView);
            }

            cardView.setOnClickListener(v -> mMovieItemListener.onMovieItemClick(movie));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }
}
