package com.example.movieinfo.onBoarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfo.MainActivity
import com.example.movieinfo.search_ui.MoviesViewModel
import com.example.movieinfo.R
import com.example.movieinfo.search_ui.RecyclerAdapter
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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        var job: Job? = null
        addTextInputEditTextListeners(job)
        addTextInputLayoutListers(view, viewModel)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        recyclerAdapter.differ.submitList(it.results)
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