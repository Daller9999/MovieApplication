package com.sunplacestudio.movieapplication.fragment.movie.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sunplacestudio.movieapplication.R
import com.sunplacestudio.movieapplication.database.repository.Movie
import com.sunplacestudio.movieapplication.fragment.movie.MovieViewModel

@Composable
fun MovieView(viewModel: MovieViewModel) {
    val state = viewModel.viewStates().collectAsState()
    MovieScreen(state.value.movie)
}

@Composable
private fun MovieScreen(movie: Movie) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.background_movie),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageLoading(
                    image = movie.backdropPath,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, top = 30.dp)
                        .height(230.dp),
                    topStart = 115.dp,
                    bottomStart = 115.dp,
                    bottomEnd = 0.dp,
                    topEnd = 0.dp
                )
                Box(
                    modifier = Modifier.padding(top = 140.dp, start = 30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        ImageLoading(
                            image = movie.posterUrl,
                            modifier = Modifier
                                .width(100.dp)
                                .height(184.dp)
                        )
                        Column {
                            Text(
                                text = movie.name,
                                color = Color.White,
                                fontSize = 20.sp,
                                maxLines = 1,
                                fontWeight = FontWeight(700),
                                modifier = Modifier.padding(start = 14.dp, bottom = 8.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 14.dp, bottom = 10.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_star_rate_24_yellow),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = movie.voteAverage.toString(),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    fontWeight = FontWeight(400),
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                                Spacer(modifier = Modifier.width(24.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_clock),
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = movie.formatRuntime(LocalContext.current),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
            GenreRow(movie = movie)
            Spacer(modifier = Modifier.height(36.dp))
            InfoItem(
                info = stringResource(id = R.string.Release),
                text = "${movie.releaseDate} ${stringResource(id = R.string.Year)}"
            )
            InfoItem(
                info = stringResource(id = R.string.counties),
                text = movie.getCountryFormatted()
            )
            InfoItem(
                info = stringResource(id = R.string.Revenue),
                text = "$ ${movie.formatRevenue()}"
            )
            Text(
                text = stringResource(id = R.string.storyline),
                textAlign = TextAlign.Start,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp)
            )
            Text(
                text = movie.overview,
                textAlign = TextAlign.Justify,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight(300),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
        }
    }
}

@Composable
private fun GenreRow(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        movie.genres.forEach {
            GenreView(text = it.name)
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
private fun GenreView(text: String) {
    Card(
        modifier = Modifier.height(30.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = colorResource(id = R.color.colorGenre)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight(300)
            )
        }
    }
}

@Composable
private fun ImageLoading(
    image: String,
    modifier: Modifier,
    topStart: Dp = 20.dp,
    topEnd: Dp = 20.dp,
    bottomEnd: Dp = 20.dp,
    bottomStart: Dp = 20.dp
) {
    val isLoaded = remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart
        ),
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            onSuccess = {
                isLoaded.value = true
            },
            onError = {
                isLoaded.value = true
            },
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (!isLoaded.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 50.dp),
                    color = colorResource(id = R.color.colorPrimary),
                    strokeWidth = 10.dp
                )
            }
        }
    }
}

@Composable
private fun InfoItem(info: String, text: String) {
    Row(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 2.dp)
    ) {
        Text(
            text = "$info:",
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight(400),
        )
        Text(
            text = text,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight(400),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
