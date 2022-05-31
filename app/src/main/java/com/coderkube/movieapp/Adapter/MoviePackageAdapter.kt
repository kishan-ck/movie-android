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
import com.coderkube.movieapp.views.Activity.MovieDetailsActivity
import kotlinx.android.synthetic.main.recyclerview_movie_list.view.*

/**
 * use foe a set data for a movie list
* */
//TODO MoviePackageAdapter
class MoviePackageAdapter(
    var context: Context,
    var moviePackageArrayLiat: ArrayList<MovieList.Result>
) : RecyclerView.Adapter<MoviePackageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_movie_list, parent, false)
        return ViewHolder(view)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var movieDetailsTextView = itemView.movieDetailsTextView
        var movierateTextView = itemView.movierateTextView
        var movieDateTextView = itemView.movieDateTextView
        var movieNameTextView = itemView.movieNameTextView
        var moviePicImageView = itemView.moviePicImageView
        var movieListLayout = itemView.movieListLayout

        fun getData(items: MovieList.Result, position: Int, context: Context) {
            movieNameTextView.text = items.originalTitle
            movieDateTextView.text = items.releaseDate
            movierateTextView.text = items.voteAverage.toString()
            movieDetailsTextView.text = items.overview

            Glide.with(context)
                .load(items.posterPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(moviePicImageView)

            movieListLayout.setOnClickListener(View.OnClickListener {
                var intent = Intent(context , MovieDetailsActivity::class.java)
                intent.putExtra("MovieID",items.id.toString())
                intent.putExtra("releaseDate",items.releaseDate)
                intent.putExtra("originalTitle",items.originalTitle)
                context.startActivity(intent)
            })
        }
    }
}