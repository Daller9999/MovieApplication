package com.sunplacestudio.movieapplication.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunplacestudio.movieapplication.MainActivity
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.databinding.MovieFragmentBinding
import com.sunplacestudio.movieapplication.fragment.main.models.MovieListEvent
import com.sunplacestudio.movieapplication.fragment.main.models.MovieListViewState
import com.sunplacestudio.movieapplication.fragment.main.adapters.MovieCategoryListAdapter
import com.sunplacestudio.movieapplication.fragment.movie.MovieFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val viewModel by viewModel<MovieFragmentViewModel>()
    private lateinit var binding: MovieFragmentBinding

    private val navController by lazy {
        Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
    }

    private val scopeIO = CoroutineScope(Dispatchers.IO)
    private val scopeMain = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieCategoryListAdapter = MovieCategoryListAdapter({
            viewModel.searchMovie(it)
        }, {
            navController.navigate(
                R.id.action_moviesFragment_to_movieFragment,
                bundleOf(MovieFragment.MOVIE_ARGS_ID to it.idMovie)
            )
        })

        binding.mainRecyclerMovies.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerMovies.adapter = movieCategoryListAdapter

        viewModel.viewStates().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieListViewState.OnUploadMovie -> {
                    movieCategoryListAdapter.submitList(state.list)
                }
                null -> {}
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.sendRequests()
            scopeIO.launch {
                delay(1500)
                scopeMain.launch {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.obtainEvent(MovieListEvent.OnStartUI)
    }

}