package com.example.movieinfo.onBoarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieinfo.R
import kotlinx.android.synthetic.main.fragment_first.view.*


class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        view.textInputLayout.setEndIconOnClickListener{
            if (view.textInputEditText.text.toString().isNotEmpty()) {
                view.textInputEditText.text!!.clear()
            }
            else{
                Toast.makeText(context, "Please Enter A Movie", Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }


}