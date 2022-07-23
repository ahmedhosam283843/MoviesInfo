package com.example.movieinfo.onBoarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieinfo.onBoarding.Screens.FirstFragment
import com.example.movieinfo.onBoarding.Screens.SecondFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = OnBoardingPage.values().size

    override fun createFragment(position: Int): Fragment {
        return when (OnBoardingPage.values()[position]) {
            OnBoardingPage.Home -> FirstFragment()
            OnBoardingPage.Search -> SecondFragment()
        }
    }
}

enum class OnBoardingPage (val index: Int) {
    Home(0), 
    Search(1),
}