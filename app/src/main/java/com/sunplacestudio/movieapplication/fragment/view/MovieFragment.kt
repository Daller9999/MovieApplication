package com.sunplacestudio.movieapplication.fragment.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.sunplacestudio.movieapplication.databinding.MovieFragmentBinding
import com.sunplacestudio.movieapplication.fragment.view.adapters.MovieCategoryListAdapter
import com.sunplacestudio.movieapplication.fragment.viewmodel.MovieFragmentViewModelImpl
import io.reactivex.Completable
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit

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

        binding.swipeRefresh.setOnRefreshListener {
            movieFragmentViewModel.sendRequests()
            Completable.fromAction {
                binding.swipeRefresh.isRefreshing = false
            }.delay(1500, TimeUnit.MILLISECONDS).subscribe()
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) return
                movieFragmentViewModel.searchMovie(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

}