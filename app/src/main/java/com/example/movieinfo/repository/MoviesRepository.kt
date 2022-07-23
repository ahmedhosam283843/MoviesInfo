package com.example.movieinfo.repository
import com.example.movieinfo.api.RetrofitInstance

class MoviesRepository{
    suspend fun searchMovies(query: String, pageNumber: Int) =
        RetrofitInstance.api.searchMovies(query, pageNumber)

    suspend fun getPopularMovies() =
        RetrofitInstance.api.getPopularMovies()

    suspend fun getTopRatedMovies() =
        RetrofitInstance.api.getPopularMovies()

    suspend fun getLatestMovies() =
        RetrofitInstance.api.getPopularMovies()
}