package com.sunplacestudio.movieapplication.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.databinding.MovieCurrentFragmentBinding
import com.sunplacestudio.movieapplication.fragment.movie.models.MovieViewState
import com.sunplacestudio.movieapplication.fragment.movie.view.MovieView
import com.sunplacestudio.movieapplication.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    companion object {
        const val MOVIE_ARGS_ID = "MOVIE_ARGS_ID"
    }

    private val viewModel by viewModel<MovieViewModel>()
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        composeView = ComposeView(requireContext())
        composeView.setContent {
            MovieView(viewModel)
        }
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val id = it.getInt(MOVIE_ARGS_ID, -1)
            if (id > 0) {
                viewModel.uploadMovie(id)
            }
        }
    }
}
