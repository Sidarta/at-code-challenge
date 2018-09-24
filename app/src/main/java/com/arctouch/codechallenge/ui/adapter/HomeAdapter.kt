package com.arctouch.codechallenge.ui.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.entity.Movie
import com.arctouch.codechallenge.ui.activity.HomeActivity
import com.arctouch.codechallenge.ui.adapter.presenter.HomeAdapterPresenter
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HomeAdapter(private var movies: MutableList<Movie>?, private val mMovieItemListener: HomeActivity.MovieItemListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(), HomeAdapterPresenter.View {

    override fun refreshMoviesList(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }
//
//    override fun refreshMoviesList(movies: MutableList<Movie>) {
//        this.movies = movies
//        notifyDataSetChanged()
//    }

    override fun addMoviesToList(movies: List<Movie>) {
        this.movies!!.addAll(movies)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val genresTextView: TextView = itemView.findViewById(R.id.genresTextView)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDateTextView)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        private val cardView: CardView = itemView.findViewById(R.id.movie_card_view)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            genresTextView.text = TextUtils.join(", ", movie.genreNames())
            releaseDateTextView.text = movie.releaseDate

            val posterPath = movie.posterPath
            if (!TextUtils.isEmpty(posterPath)) {
                Glide.with(itemView)
                        .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                        .into(posterImageView)
            }

            cardView.setOnClickListener { mMovieItemListener.onMovieItemClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies!![position])
    }
}

fun Movie.genreNames(): List<String> {
    return this.genres.map { it.name ?: "" }

}
