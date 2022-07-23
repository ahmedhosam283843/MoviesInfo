package com.example.movieinfo.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieinfo.R
import com.example.movieinfo.onBoarding.Screens.FirstFragment
import com.example.movieinfo.onBoarding.Screens.SecondFragment
import kotlinx.android.synthetic.main.fragment_view_pager.view.*


class ViewPagerFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val adapter = ViewPagerAdapter(requireActivity()
            .supportFragmentManager, lifecycle)
        view.viewPager.adapter = adapter


        view.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.firstMenuItem -> {
                    view.viewPager.currentItem = OnBoardingPage.Home.index
                    true
                }
                R.id.secondMenuItem -> {
                    view.viewPager.currentItem = OnBoardingPage.Search.index
                    true
                }
                else -> false
            }
        }
       return view
    }


}