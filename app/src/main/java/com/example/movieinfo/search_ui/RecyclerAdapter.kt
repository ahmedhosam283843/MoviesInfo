package com.example.movieinfo.search_ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieinfo.R
import com.example.movieinfo.api.Movie
import com.example.movieinfo.util.Constants.Companion.POSTER_BASE_URL
import kotlinx.android.synthetic.main.movie_card.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_card, ////////////////////////
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        holder.img.setImageResource(imgs[position])
//        holder.title.text = s1[position]
        val movie = differ.currentList[position]
        holder.itemView.apply{
            Glide.with(this).load(POSTER_BASE_URL + movie.poster_path).into(search_img_view)
            search_title.text = movie.title

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}