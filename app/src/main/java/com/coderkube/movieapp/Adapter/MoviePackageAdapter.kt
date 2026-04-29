package com.coderkube.movieapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderkube.movieapp.Models.MovieList
import com.coderkube.movieapp.R
import com.coderkube.movieapp.databinding.RecyclerviewMovieListBinding
import com.coderkube.movieapp.views.Activity.MovieDetailsActivity

/**
 * use foe a set data for a movie list
* */
//TODO MoviePackageAdapter
class MoviePackageAdapter(
    var context: Context,
    var moviePackageArrayLiat: ArrayList<MovieList.Result>
) : RecyclerView.Adapter<MoviePackageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * use for a set data binding and set data in view
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getData(moviePackageArrayLiat.get(position),position, context)
    }

    override fun getItemCount(): Int {
        return moviePackageArrayLiat.size
    }

    class ViewHolder(val binding: RecyclerviewMovieListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun getData(items: MovieList.Result, position: Int, context: Context) {
            binding.movieNameTextView.text = items.originalTitle
            binding.movieDateTextView.text = items.releaseDate
            binding.movierateTextView.text = items.voteAverage.toString()
            binding.movieDetailsTextView.text = items.overview

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + items.backdropPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding.moviePicImageView)

            binding.movieListLayout.setOnClickListener(View.OnClickListener {
                var intent = Intent(context , MovieDetailsActivity::class.java)
                intent.putExtra("MovieID",items.id.toString())
                intent.putExtra("releaseDate",items.releaseDate)
                intent.putExtra("originalTitle",items.originalTitle)
                context.startActivity(intent)
            })
        }
    }
}