package com.example.movieinfo.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfo.search_ui.MoviesViewModel

class MoviesViewModelFactory (val moviesRepository: MoviesRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }
}

