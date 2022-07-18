package com.example.movieinfo.repository
import com.example.movieinfo.api.RetrofitInstance

class MoviesRepository{
    suspend fun searchMovies(query: String, pageNumber: Int) =
        RetrofitInstance.api.searchMovies(query, pageNumber)

}