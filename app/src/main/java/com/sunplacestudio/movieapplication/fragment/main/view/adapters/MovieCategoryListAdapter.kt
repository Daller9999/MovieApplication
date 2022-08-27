package com.sunplacestudio.movieapplication.fragment.main.view.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.databinding.ItemCategoryMovieBinding
import com.sunplacestudio.movieapplication.databinding.ItemHeaderBinding
import com.sunplacestudio.movieapplication.utils.apicall.json.CategoryMovie

class MovieCategoryListAdapter(
    private val onEditTextChanged: (string: String) -> Unit,
    private val onMovieClicked: (id: Int) -> Unit
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

    inner class DataHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var dataItem: ItemCategoryMovieBinding = ItemCategoryMovieBinding.bind(view)

        init {
            val movieCurrentCategoryAdapter = MovieCurrentCategoryAdapter(onMovieClicked)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(dataItem.categoryRecycler)
            dataItem.categoryRecycler.layoutManager = LinearLayoutManager(dataItem.root.context, LinearLayoutManager.HORIZONTAL, false)
            dataItem.categoryRecycler.adapter = movieCurrentCategoryAdapter
        }

        fun bind(movieCategoryList: MovieCategoryList) {
            val name: String = when (CategoryMovie.getCategory(movieCategoryList.category)) {
                CategoryMovie.RECOMMENDED -> dataItem.root.resources.getString(R.string.recommended_movies)
                CategoryMovie.POPULAR -> dataItem.root.resources.getString(R.string.popular_movies)
                CategoryMovie.FOUND -> dataItem.root.resources.getString(R.string.movie_found)
                else -> "error"
            }
            dataItem.categoryName.text = name
            (dataItem.categoryRecycler.adapter as MovieCurrentCategoryAdapter).submitList(
                movieCategoryList.list
            )
        }
    }

    class HeaderHolder(
        view: View,
        private val onEditTextChanged: (string: String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val dataHolder = ItemHeaderBinding.bind(view)

        init {
            dataHolder.editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    onEditTextChanged(s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    override fun submitList(list: List<MovieCategoryList>?) {
        val newList = mutableListOf<MovieCategoryList>()
        newList.add(MovieCategoryList(Int.MAX_VALUE, emptyList()))
        newList.addAll(list ?: mutableListOf())
        super.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == CATEGORY) {
            val binding = ItemCategoryMovieBinding.inflate(inflater, parent, false)
            DataHolder(binding.root)
        } else {
            val binding = ItemHeaderBinding.inflate(inflater, parent, false)
            HeaderHolder(binding.root, onEditTextChanged)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataHolder -> holder.bind(getItem(position))
        }
    }

}