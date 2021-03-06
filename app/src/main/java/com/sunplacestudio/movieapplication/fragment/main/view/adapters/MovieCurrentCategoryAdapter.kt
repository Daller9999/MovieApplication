package com.sunplacestudio.movieapplication.fragment.main.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.databinding.ItemCategoryCurrentMovieBinding

class MovieCurrentCategoryAdapter(
    private val onMovieClicked: (id: Int) -> Unit
) : ListAdapter<Movie, MovieCurrentCategoryAdapter.DataHolder>(callBack) {

    companion object {
        private val callBack = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view) {
        private var dataItem: ItemCategoryCurrentMovieBinding = ItemCategoryCurrentMovieBinding.bind(view)

        fun bind(movie: Movie) {
            dataItem.textViewMovieName.text = movie.name
            dataItem.textViewMovieName.isSelected = true
            dataItem.root.setOnClickListener {
                onMovieClicked(movie.idMovie)
            }
            Glide.with(itemView).load(movie.posterUrl).into(dataItem.imageView)
            val mas = listOf(dataItem.star1, dataItem.star2, dataItem.star3, dataItem.star4, dataItem.star5)
            var count = (movie.voteAverage / 2f).toInt()
            count = if (count >= mas.size) mas.size - 1 else count
            for (i in 0..count) {
                mas[i].isActivated = true
            }
            if (count < 4) {
                for (i in count until mas.size) {
                    mas[i].isActivated = false
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryCurrentMovieBinding.inflate(inflater, parent, false)
        return DataHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(getItem(position))
    }
}