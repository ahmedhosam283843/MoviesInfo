package com.example.movieinfo.home_ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieinfo.R
import com.example.movieinfo.api.Movie
import com.example.movieinfo.util.Constants
import kotlinx.android.synthetic.main.home_item_2.view.*


class ChildAdapter(var list: List<Movie>): RecyclerView.Adapter<ChildAdapter.Home2ViewHolder>() {
    inner class Home2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img : ImageView = itemView.home2_imageView
        var movieName : TextView = itemView.home2textView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Home2ViewHolder {
        return Home2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_item_2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Home2ViewHolder, position: Int) {
        val childItem = list.get(position)
        holder.itemView.apply {
            Glide.with(this).load(Constants.POSTER_BASE_URL + childItem.poster_path).into(home2_imageView)
            home2textView.text = childItem.title
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}