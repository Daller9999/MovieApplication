package com.sunplacestudio.movieapplication.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sunplacestudio.movieapplication.databinding.MovieCurrentFragmentBinding
import com.sunplacestudio.movieapplication.utils.loadImage
import kotlinx.android.synthetic.main.item_category_current_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

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
        movieViewModel.movie.observe(viewLifecycleOwner) {
            with(binding) {
                imageView.loadImage(it.posterUrl)
                textViewTitle.text = it.name
                textViewOverview.text = it.overview
            }
            val mas = listOf(star1, star2, star3, star4, star5)
            val count = it.starCount()
            for (i in 0..count) {
                mas[i].isActivated = true
            }
            if (count < 4) {
                for (i in count until mas.size) {
                    mas[i].isActivated = false
                }
            }
        }
        movieViewModel.uploadMovie()
    }
}