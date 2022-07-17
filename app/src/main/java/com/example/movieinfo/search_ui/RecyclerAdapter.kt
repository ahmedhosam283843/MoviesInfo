package com.example.movieinfo.search_ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfo.R
import kotlinx.android.synthetic.main.movie_card.view.*

class RecyclerAdapter (val ct: Context,  var s1: Array<String>, val imgs: List<Int>): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.search_title
        val img = itemView.search_img_view
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(ct)
        val view = inflater.inflate(R.layout.movie_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img.setImageResource(imgs[position])
        holder.title.text = s1[position]
    }

    override fun getItemCount(): Int {
        return s1.size
    }
}