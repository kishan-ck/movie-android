package com.coderkube.movieapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.coderkube.movieapp.Models.MovieDetailModel
import com.coderkube.movieapp.Models.MovieList
import com.coderkube.movieapp.R
import com.coderkube.movieapp.RequestModel.MovieDetailRequestModel
import com.coderkube.movieapp.Utils.App
import com.coderkube.movieapp.Utils.utils
import com.coderkube.movieapp.repository.MovieDetailRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

//TODO MovieDetailViewModel

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage = MutableLiveData<String>()
    val MovieID = MutableLiveData<String>()
    val movieName = MutableLiveData<String>()
    val movieDate = MutableLiveData<String>()
    val movieRate = MutableLiveData<String>()
    val movieGenres = MutableLiveData<String>()
    val movieLanguages = MutableLiveData<String>()
    val movieProduction = MutableLiveData<String>()
    val moviedetail = MutableLiveData<String>()
    val detailUser = MutableLiveData<MovieDetailRequestModel>()
    val MovieDetailResponse = MutableLiveData<MovieDetailModel>()
    var movieDetailModel: MovieDetailModel? = null


    /**
     * this function is use for Api calling and check response
     * */
    fun MovieDetailCall() {
        if (utils.Util.isConnect(getApplication())) {

            detailUser.value = MovieDetailRequestModel(MovieID.value!!)

            CoroutineScope(Dispatchers.IO).launch {
                val response = MovieDetailRepository.MovieDetails(detailUser.value!!, utils.Util.api_key)

                withContext(Dispatchers.Main) {

                    movieDetailModel = response.body()

                    if (response.isSuccessful) {
                        when (response.code()) {
                            200 -> {
                                if (response.body() != null) {
                                    MovieDetailResponse.value = movieDetailModel
                                }
                            }
                        }
                    } else
                        when (response.code()) {
                            401 -> {
                                try {
                                    val errorBody = response.errorBody()!!.string()
                                    val gson = Gson()

                                    movieDetailModel = gson.fromJson(errorBody, MovieDetailModel::class.java)
                                    if (errorBody.contains("status_message")) {
                                        setError(movieDetailModel!!.statusMessage)
                                    }
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                            500 -> setError(R.string.server_error)
                            else -> setError(R.string.something_went_wrong)
                        }
                }
            }
        }
    }

    /**
     * this function is use for check error
     * */
    private fun setError(text: Any) {
        if (text is Int)
            errorMessage.postValue(getApplication<App>().getString(text))
        else
            errorMessage.postValue(text.toString())
    }
}