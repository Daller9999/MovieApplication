package com.sunplacestudio.movieapplication.fragment.movie.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.fragment.movie.MovieViewModel

@Composable
fun MovieView(viewModel: MovieViewModel) {
    val state = viewModel.viewStates().collectAsState()
    MovieScreen(state.value.movie)
}

@Composable
private fun MovieScreen(movie: Movie) {
    LazyColumn {
        item {
            Box(
                modifier = Modifier.fillMaxWidth().height(400.dp)
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(size = 20.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.posterUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = com.sunplacestudio.movieapplication.R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}