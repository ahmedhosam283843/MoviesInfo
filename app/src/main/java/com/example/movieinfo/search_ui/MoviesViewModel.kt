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
    val searchMovies  : MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    var searchMoviesPage  =1
    var searchMoviesResponse : MoviesResponse? = null



    fun searchMovies(query: String) = viewModelScope.launch {
        searchMovies.postValue(Resource.Loading())
        val response = moviesRepository.searchMovies(query , searchMoviesPage)
        searchMovies.postValue(handleSearchMoviesResponse(response))
    }

    private fun handleSearchMoviesResponse(response: Response<MoviesResponse>) : Resource<MoviesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {resultResponse ->
                searchMoviesPage++
                if(searchMoviesResponse == null) {
                    searchMoviesResponse =  resultResponse
                } else {
                    val  oldMovies = searchMoviesResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(searchMoviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}