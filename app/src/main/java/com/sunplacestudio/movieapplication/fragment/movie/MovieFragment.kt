package com.sunplacestudio.movieapplication.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.databinding.MovieCurrentFragmentBinding
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieViewState
import com.sunplacestudio.movieapplication.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    companion object {
        const val MOVIE_ARGS_ID = "MOVIE_ARGS_ID"
    }

    private lateinit var binding: MovieCurrentFragmentBinding
    private val viewModel by viewModel<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieCurrentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewStates().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieViewState.OnUploadMovie -> setMovie(state.movie)
                null -> {}
            }
        }
        arguments?.let {
            val id = it.getInt(MOVIE_ARGS_ID, -1)
            if (id > 0) {
                viewModel.uploadMovie(id)
            }
        }
    }

    private fun setMovie(movie: Movie) {
        with(binding) {
            imageView.loadImage(movie.posterUrl)
            textViewTitle.text = movie.name
            textViewOverview.text = movie.overview

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
