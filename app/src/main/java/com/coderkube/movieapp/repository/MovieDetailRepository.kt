package com.coderkube.movieapp.repository

import com.coderkube.events.WebService.ApiClient
import com.coderkube.movieapp.RequestModel.MovieDetailRequestModel


/**
 * This class is use for a movie details repository
 * */
//TODO MovieDetailRepository
object MovieDetailRepository {

    suspend fun MovieDetails(movieDetailRequestModel: MovieDetailRequestModel, apikey: String) = ApiClient.getClient().MovieDetails(
        movieDetailRequestModel.MovieID!!,
        apikey
    )
}