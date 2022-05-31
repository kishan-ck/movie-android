package com.coderkube.movieapp.views.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coderkube.movieapp.Adapter.MoviePackageAdapter
import com.coderkube.movieapp.R
import com.coderkube.movieapp.Utils.utils
import com.coderkube.movieapp.databinding.ActivityMainBinding
import com.coderkube.movieapp.viewModel.MovieListViewModel
import com.coderkube.movieapp.viewModelFactory.MovieListViewModelFactory

//TODO MainActivity

class MainActivity : AppCompatActivity() {

    /**
     * use for a data binding
     * */
    private var binding: ActivityMainBinding? = null

    /**
     * use for a set viewmodel factory
     * */
    private val movieViewModel: MovieListViewModel by viewModels {
        MovieListViewModelFactory(application)
    }

    var moviePackageAdapter: MoviePackageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.lifecycleOwner = this
        binding?.movieViewModel = movieViewModel

        setUpObservers()
    }

    /**
     * This function use for observed
     * */
    private fun setUpObservers() {

        /**
         * set api calling
         * */
        utils.Util.ShowProgress(this)
        movieViewModel.MovieListCall()

        /**
         * use for movie list response
         * */
        movieViewModel.MovieListResponse.observe(this, Observer { movieData ->
            utils.Util.HideProgress()
            binding!!.recyclerviewMovieList.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
            moviePackageAdapter = MoviePackageAdapter(this, movieData)
            binding!!.recyclerviewMovieList.adapter = moviePackageAdapter
        })

        /**
         * use for set error
         * */
        movieViewModel.errorMessage.observe(this, Observer { error ->
            utils.Util.HideProgress()
            utils.Util.DebugToast(this, error)
        })
    }
}