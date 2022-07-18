package com.example.movieinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movieinfo.repository.MoviesRepository
import com.example.movieinfo.repository.MoviesViewModelFactory
import com.example.movieinfo.search_ui.MoviesViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val moviesRepository = MoviesRepository()
        val viewModelProviderFactory = MoviesViewModelFactory(moviesRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MoviesViewModel::class.java)
    }
}