package com.coderkube.movieapp.views.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.coderkube.movieapp.R
import com.coderkube.movieapp.Utils.utils
import com.coderkube.movieapp.databinding.ActivityMovieDetailsBinding
import com.coderkube.movieapp.viewModel.MovieDetailViewModel
import com.coderkube.movieapp.viewModelFactory.MovieDetailViewModelFactory
import kotlinx.android.synthetic.main.toolbar.*

//TODO MovieDetailsActivity
class MovieDetailsActivity : AppCompatActivity() {

    /**
     * use for a data binding
     * */
    private var binding: ActivityMovieDetailsBinding? = null

    /**
     * use for a set viewmodel factory
     * */
    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(application)
    }

    var genresList: ArrayList<String> = ArrayList()
    var movieLanguageList: ArrayList<String> = ArrayList()
    var movieProductionList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        binding?.lifecycleOwner = this
        binding?.movieDetailViewModel = movieDetailViewModel

        toolbar_title.text = intent.getSerializableExtra("originalTitle").toString()

        setUpObservers()
    }


    /**
     * This function use for observed
     * */
    private fun setUpObservers() {

        /**
         * set api calling
         * */
        movieDetailViewModel.MovieID.value = intent.getStringExtra("MovieID")
        utils.Util.ShowProgress(this)
        movieDetailViewModel.MovieDetailCall()

        /**
         * use for movie details response and set data in view
         * */
        movieDetailViewModel.MovieDetailResponse.observe(this, Observer { movieData ->
            utils.Util.HideProgress()

            movieDetailViewModel.movieName.value = movieData.originalTitle
            movieDetailViewModel.movieDate.value = intent.getStringExtra("releaseDate")
            movieDetailViewModel.movieRate.value = movieData.voteAverage.toString()
            movieDetailViewModel.moviedetail.value = movieData.overview

            Glide.with(application)
                .load(movieData.posterPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding!!.movieImageView)

            Glide.with(application)
                .load(movieData.backdropPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding!!.movieImage)

            for (i in 0..movieData.genres.size - 1) {
                genresList.add(movieData.genres.get(i).name)
            }

            for (i in 0..movieData.spokenLanguages.size - 1) {
                movieLanguageList.add(movieData.spokenLanguages.get(i).name)
            }

            for (i in 0..movieData.productionCompanies.size - 1) {
                movieProductionList.add(movieData.productionCompanies.get(i).name)
            }

            movieDetailViewModel.movieGenres.value = genresList.toString().replace("[", "").replace("]", "")

            movieDetailViewModel.movieLanguages.value = movieLanguageList.toString().replace("[", "").replace("]", "")

            movieDetailViewModel.movieProduction.value = movieProductionList.toString().replace("[", "").replace("]", "")

        })

        /**
         * use for set error
         * */
        movieDetailViewModel.errorMessage.observe(this, Observer { error ->
            utils.Util.HideProgress()
            utils.Util.DebugToast(this, error)
        })
    }
}