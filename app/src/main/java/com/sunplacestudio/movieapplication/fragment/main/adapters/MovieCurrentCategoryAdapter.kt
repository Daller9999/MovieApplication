package com.sunplacestudio.movieapplication.fragment.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.databinding.ItemCategoryCurrentMovieBinding
import com.sunplacestudio.movieapplication.utils.loadImage

class MovieCurrentCategoryAdapter(
    private val onMovieClicked: (movie: Movie) -> Unit
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

    class DataHolder(
        view: View,
        private val onMovieClicked: (movie: Movie) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private var dataItem: ItemCategoryCurrentMovieBinding =
            ItemCategoryCurrentMovieBinding.bind(view)

        fun bind(movie: Movie) {
            with(dataItem) {
                textViewMovieName.text = movie.name
                textViewMovieName.isSelected = true
                root.setOnClickListener {
                    onMovieClicked(movie)
                }

                imageView.loadImage(movie.posterUrl)
                val mas = listOf(star1, star2, star3, star4, star5)
                val count = movie.starCount()
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryCurrentMovieBinding.inflate(inflater, parent, false)
        return DataHolder(binding.root, onMovieClicked)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(getItem(position))
    }
}