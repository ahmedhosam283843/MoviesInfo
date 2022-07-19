package com.example.movieinfo.api

import com.example.movieinfo.api.Movie

data class MoviesResponse(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
)