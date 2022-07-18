package com.example.movieinfo.onBoarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfo.MainActivity
import com.example.movieinfo.search_ui.MoviesViewModel
import com.example.movieinfo.R
import com.example.movieinfo.search_ui.RecyclerAdapter
import com.example.movieinfo.util.Resource
import kotlinx.android.synthetic.main.fragment_first.view.*


class FirstFragment : Fragment() {
    lateinit var viewModel: MoviesViewModel
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        setUpRecyclerView(view)
        addTextInputListers(view)
        return view
    }

    private fun setUpRecyclerView(view: View) {
        recyclerAdapter = RecyclerAdapter()
        view.searchRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    private fun addTextInputListers(view: View) {
        view.textInputLayout.setEndIconOnClickListener {
            if (view.textInputEditText.text.toString().isNotEmpty()) {
                view.textInputEditText.text!!.clear()
            } else {
                Toast.makeText(context, "Please Enter A Movie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
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


}