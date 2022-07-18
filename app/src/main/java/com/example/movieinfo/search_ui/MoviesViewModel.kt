package com.example.movieinfo.search_ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfo.api.MoviesResponse
import com.example.movieinfo.repository.MoviesRepository
import com.example.movieinfo.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(val moviesRepository: MoviesRepository) : ViewModel() {
    val searchResult  : MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    var searchMoviesPage  =1

    init {
        searchMovies("wick")
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        searchResult.postValue(Resource.Loading())
        val response = moviesRepository.searchMovies(query , searchMoviesPage)
        searchResult.postValue(handleSearchMoviesResponse(response))
    }

    private fun handleSearchMoviesResponse(response: Response<MoviesResponse>) : Resource<MoviesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}