package com.example.movieinfo.onBoarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfo.MainActivity
import com.example.movieinfo.search_ui.MoviesViewModel
import com.example.movieinfo.R
import com.example.movieinfo.search_ui.RecyclerAdapter
import com.example.movieinfo.util.Constants
import com.example.movieinfo.util.Constants.Companion.Query_Page_Size
import com.example.movieinfo.util.Constants.Companion.Search_Movies_Delay
import com.example.movieinfo.util.Resource
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {
    lateinit var viewModel: MoviesViewModel
    lateinit var recyclerAdapter: RecyclerAdapter
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    val scrollLister = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Query_Page_Size
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.searchMovies(textInputEditText.text.toString())
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view: View) {
        recyclerAdapter = RecyclerAdapter()
        view.searchRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
            addOnScrollListener(this@FirstFragment.scrollLister)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        var job: Job? = null
        addTextInputEditTextListeners(job)
        addTextInputLayoutListers(view, viewModel)
        viewModel.searchMovies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { moviesResponse ->
                        recyclerAdapter.differ.submitList(moviesResponse.results.toList())
                        val totalPages = moviesResponse.total_results / Query_Page_Size + 2
                        isLastPage = viewModel.searchMoviesPage == totalPages
                        if (isLastPage) {
                            view.searchRV.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addTextInputLayoutListers(view: View, viewModel: MoviesViewModel) {
        view.textInputLayout.setEndIconOnClickListener {
            if (view.textInputEditText.text.toString().isNotEmpty()) {
                view.textInputEditText.text!!.clear()
                viewModel.searchMovies(view.textInputEditText.text.toString())
            } else {
                Toast.makeText(context, "Please Enter A Movie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTextInputEditTextListeners(job: Job?) {
        var job1 = job
        textInputEditText.addTextChangedListener { editable ->
            job1?.cancel()
            job1 = MainScope().launch {
                delay(Search_Movies_Delay)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchMovies(editable.toString())
                    }
                }
            }
        }
    }
}