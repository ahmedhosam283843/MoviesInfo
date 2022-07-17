package com.example.movieinfo.onBoarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieinfo.R
import com.example.movieinfo.search_ui.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*


class FirstFragment : Fragment() {
    val imgs = listOf<Int>(
        androidx.customview.R.drawable.notification_bg, androidx.constraintlayout.widget.R.drawable.abc_ab_share_pack_mtrl_alpha,
        androidx.viewpager.R.drawable.notification_bg, androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.appcompat.R.drawable.abc_ic_go_search_api_material,
        androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.transition.R.drawable.abc_btn_check_to_on_mtrl_000, androidx.constraintlayout.widget.R.drawable.abc_spinner_textfield_background_material,
        androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.appcompat.R.drawable.abc_edit_text_material)
    lateinit var s1 : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        s1 = resources.getStringArray(R.array.programming_languages)
        val adapter = RecyclerAdapter(requireContext(), s1, imgs)
        view.searchRV.adapter = adapter
        view.searchRV.layoutManager = LinearLayoutManager(requireContext())

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