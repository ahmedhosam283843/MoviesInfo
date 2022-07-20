package com.example.movieinfo.onBoarding.Screens

import ParentAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfo.MainActivity
import com.example.movieinfo.search_ui.MoviesViewModel
import com.example.movieinfo.R
import com.example.movieinfo.api.MoviesResponse
import com.example.movieinfo.home_ui.ParentItem
import com.example.movieinfo.util.Constants.Companion.Query_Page_Size
import com.example.movieinfo.util.Resource


class SecondFragment : Fragment() {
    lateinit var parentRecyclerView: RecyclerView
    lateinit var parentAdapter: RecyclerView.Adapter<*>
    lateinit var parentLayoutManager: RecyclerView.LayoutManager
    var parentList: ArrayList<ParentItem> = ArrayList()
    lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getPopularMovies()
        addResponseListener()
        viewModel.getPopularMovies()
        viewModel.popularMoviesResponse?.let {
            addItems(it)
            setupParentRV(view, it)
        }
    }

    private fun addResponseListener() {
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { moviesResponse ->

                        val totalPages = moviesResponse.total_pages / Query_Page_Size + 2
                        isLastPage = viewModel.popularMoviesPage == totalPages
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("Home Fragment", "An error occured: $message")
                    }
                }
                else -> {
                }
            }

        })
    }


    private fun setupParentRV(
        view: View,
        _reponse: MoviesResponse
    ) {
        parentRecyclerView = view!!.findViewById(R.id.homeRv1)
        parentRecyclerView.setHasFixedSize(true)
        parentLayoutManager = LinearLayoutManager(requireContext())
        parentRecyclerView.layoutManager = parentLayoutManager
        parentAdapter = ParentAdapter(parentList, _reponse)
        parentRecyclerView.adapter = parentAdapter
        parentAdapter.notifyDataSetChanged()
    }

    private fun addItems(_reponse: MoviesResponse) {
        parentList.add(ParentItem("Popular", _reponse.results))
        parentList.add(ParentItem("topRated", _reponse.results))
        parentList.add(ParentItem("latest", _reponse.results))
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
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
                viewModel.getPopularMovies()
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


}