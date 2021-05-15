package com.sunplacestudio.movieapplication.fragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sunplacestudio.movieapplication.databinding.MovieFragmentBinding
import com.sunplacestudio.movieapplication.fragment.view.adapters.MovieCategoryListAdapter
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModelImpl
import org.koin.java.KoinJavaComponent.inject

class MovieFragment: Fragment() {

    private val movieFragmentViewModel by inject(MovieFragmentViewModelImpl::class.java)
    private lateinit var binding: MovieFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieFragmentBinding.inflate(inflater, container, false)

        val movieCategoryListAdapter = MovieCategoryListAdapter()

        binding.mainRecyclerMovies.layoutManager = LinearLayoutManager(context)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.mainRecyclerMovies)
        binding.mainRecyclerMovies.adapter = movieCategoryListAdapter

        movieFragmentViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) return@Observer
            movieCategoryListAdapter.submitList(it)
        })

        return binding.root
    }

}