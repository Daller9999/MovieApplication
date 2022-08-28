package com.sunplacestudio.movieapplication.fragment.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.*
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.databinding.ItemCategoryMovieBinding
import com.sunplacestudio.movieapplication.databinding.ItemHeaderBinding
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie

class MovieCategoryListAdapter(
    private val onEditTextChanged: (string: String) -> Unit,
    private val onMovieClicked: (movie: Movie) -> Unit
) : ListAdapter<MovieCategoryList, RecyclerView.ViewHolder>(callBack) {

    companion object {
        private val callBack = object : DiffUtil.ItemCallback<MovieCategoryList>() {
            override fun areItemsTheSame(
                oldItem: MovieCategoryList,
                newItem: MovieCategoryList
            ): Boolean {
                return oldItem.category == newItem.category
            }

            override fun areContentsTheSame(
                oldItem: MovieCategoryList,
                newItem: MovieCategoryList
            ): Boolean {
                return oldItem == newItem
            }
        }

        const val HEADER = 0
        const val CATEGORY = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return HEADER
        return CATEGORY
    }

    class DataHolder(
        view: View,
        onMovieClicked: (movie: Movie) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private var dataItem: ItemCategoryMovieBinding = ItemCategoryMovieBinding.bind(view)

        init {
            with(dataItem) {
                val movieCurrentCategoryAdapter = MovieCurrentCategoryAdapter(onMovieClicked)
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(categoryRecycler)
                categoryRecycler.layoutManager = LinearLayoutManager(
                    root.context, LinearLayoutManager.HORIZONTAL, false
                )
                categoryRecycler.adapter = movieCurrentCategoryAdapter
            }
        }

        fun bind(movieCategoryList: MovieCategoryList) {
            with(dataItem) {
                val name: String = when (CategoryMovie.getCategory(movieCategoryList.category)) {
                    CategoryMovie.RECOMMENDED -> root.context.getString(R.string.recommended_movies)
                    CategoryMovie.POPULAR -> root.context.getString(R.string.popular_movies)
                    CategoryMovie.FOUND -> root.context.getString(R.string.movie_found)
                    else -> "error"
                }
                categoryName.text = name
                (categoryRecycler.adapter as MovieCurrentCategoryAdapter).submitList(
                    movieCategoryList.list
                )
            }
        }
    }

    class HeaderHolder(
        view: View,
        private val onEditTextChanged: (string: String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val dataHolder = ItemHeaderBinding.bind(view)

        init {
            dataHolder.editTextSearch.addTextChangedListener {
                onEditTextChanged(it.toString())
            }
        }
    }

    override fun submitList(list: List<MovieCategoryList>?) {
        val newList = mutableListOf<MovieCategoryList>()
        newList.add(MovieCategoryList(Int.MAX_VALUE, emptyList()))
        newList.addAll(list ?: emptyList())
        super.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CATEGORY -> DataHolder(
                ItemCategoryMovieBinding.inflate(inflater, parent, false).root,
                onMovieClicked
            )
            else -> HeaderHolder(
                ItemHeaderBinding.inflate(inflater, parent, false).root,
                onEditTextChanged
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataHolder -> holder.bind(getItem(position))
        }
    }

}