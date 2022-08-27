package com.sunplacestudio.movieapplication.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.databinding.MovieCurrentFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    companion object {
        const val MOVIE_ARGS = "MOVIE_ARGS"
    }

    private lateinit var binding: MovieCurrentFragmentBinding
    private val movieViewModel by viewModel<MovieViewModel>()

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
        arguments?.let { args ->
            val id = args.getInt(MOVIE_ARGS, -1)
            if (id > 0) {
                movieViewModel.uploadMovie(id)
            }
        }

        movieViewModel.movie.observe(viewLifecycleOwner, Observer {
            with(binding) {
                Glide.with(imageView).load(it.posterUrl).into(imageView)
                textViewTitle.text = it.name
                textViewOverview.text = it.overview
            }
        })
    }


}