package com.sunplacestudio.movieapplication.fragment.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.sunplacestudio.movieapplication.database.repository.MovieCategoryList
import com.sunplacestudio.movieapplication.databinding.MovieFragmentBinding
import com.sunplacestudio.movieapplication.fragment.view.adapters.MovieCategoryListAdapter
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModelImpl
import io.reactivex.Completable
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit

class  MovieFragment: Fragment() {

    private val movieFragmentViewModel by viewModel<MovieFragmentViewModelImpl>()
    private lateinit var binding: MovieFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieFragmentBinding.inflate(inflater, container, false)

        val movieCategoryListAdapter = MovieCategoryListAdapter {
            movieFragmentViewModel.searchMovie(it)
        }

        binding.mainRecyclerMovies.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerMovies.adapter = movieCategoryListAdapter

        movieFragmentViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) return@Observer
            movieCategoryListAdapter.submitList(it as MutableList<MovieCategoryList>?)
        })

        binding.swipeRefresh.setOnRefreshListener {
            movieFragmentViewModel.sendRequests()
            Completable.fromAction {
                binding.swipeRefresh.isRefreshing = false
            }.delay(1500, TimeUnit.MILLISECONDS).subscribe()
        }

        return binding.root
    }

}