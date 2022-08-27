package com.sunplacestudio.movieapplication.fragment.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunplacestudio.movieapplication.MainActivity
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.databinding.MovieFragmentBinding
import com.sunplacestudio.movieapplication.fragment.main.view.adapters.MovieCategoryListAdapter
import com.sunplacestudio.movieapplication.fragment.main.viewmodel.MovieFragmentViewModel
import com.sunplacestudio.movieapplication.fragment.movie.MovieFragment
import io.reactivex.Completable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MoviesFragment : Fragment() {

    private val movieFragmentViewModel by viewModel<MovieFragmentViewModel>()
    private lateinit var binding: MovieFragmentBinding

    private val navController by lazy {
        Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)

        val movieCategoryListAdapter = MovieCategoryListAdapter({
            movieFragmentViewModel.searchMovie(it)
        }, {
            navController.navigate(
                R.id.action_moviesFragment_to_movieFragment,
                bundleOf(MovieFragment.MOVIE_ARGS to it)
            )
        })

        binding.mainRecyclerMovies.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerMovies.adapter = movieCategoryListAdapter

        movieFragmentViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) return@Observer
            movieCategoryListAdapter.submitList(it)
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