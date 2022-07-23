package com.example.movieinfo.api

import com.example.movieinfo.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY

    ):Response<com.example.movieinfo.api.MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = API_KEY


    ):Response<com.example.movieinfo.api.MoviesResponse>


}