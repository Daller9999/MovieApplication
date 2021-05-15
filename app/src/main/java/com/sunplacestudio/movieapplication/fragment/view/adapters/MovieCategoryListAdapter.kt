package com.sunplacestudio.movieapplication.fragment.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.databinding.ItemCategoryMovieBinding
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie

class MovieCategoryListAdapter: ListAdapter<MovieCategoryList, MovieCategoryListAdapter.DataHolder>(callBack) {

    companion object {
        private val callBack = object : DiffUtil.ItemCallback<MovieCategoryList>() {
            override fun areItemsTheSame(oldItem: MovieCategoryList, newItem: MovieCategoryList): Boolean {
                return oldItem.category == newItem.category
            }

            override fun areContentsTheSame(oldItem: MovieCategoryList, newItem: MovieCategoryList): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class DataHolder(view: View): RecyclerView.ViewHolder(view) {
        private var dataItem: ItemCategoryMovieBinding = ItemCategoryMovieBinding.bind(view)

        init {
            val movieCurrentCategoryAdapter = MovieCurrentCategoryAdapter()
            dataItem.categoryRecycler.layoutManager = LinearLayoutManager(dataItem.root.context, LinearLayoutManager.HORIZONTAL, false)
            dataItem.categoryRecycler.adapter = movieCurrentCategoryAdapter
        }

        fun bind(movieCategoryList: MovieCategoryList, position: Int) {
            val name: String = when (CategoryMovie.getCategory(movieCategoryList.category)) {
                CategoryMovie.RECOMMENDED -> dataItem.root.resources.getString(R.string.recommended_movies)
                CategoryMovie.POPULAR -> dataItem.root.resources.getString(R.string.new_movies)
                else -> "error"
            }
            dataItem.categoryName.text = name
            (dataItem.categoryRecycler.adapter as MovieCurrentCategoryAdapter).submitList(movieCategoryList.list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryMovieBinding.inflate(inflater, parent, false)
        return DataHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}