package com.sunplacestudio.movieapplication.fragment.movie.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val isLoaded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.colorDark))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
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
                    onSuccess = {
                        isLoaded.value = true
                        Log.i("test_app", "true2")
                    },
                    onError = {
                        isLoaded.value = true
                        Log.i("test_app", "true3")
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
                            modifier = Modifier.size(150.dp),
                            color = colorResource(id = R.color.colorPrimary),
                            strokeWidth = 10.dp
                        )
                    }
                }
            }
        }
        Text(
            text = movie.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight(800),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val starCount = movie.starCount()
            val left = 5 - starCount
            for (i in 0 until starCount) {
                Star(isEnabled = false)
            }
            for (i in 0 until left) {
                Star(isEnabled = true)
            }
        }
        InfoItem(info = stringResource(id = R.string.Release), text = movie.releaseDate)
        InfoItem(info = stringResource(id = R.string.Runtime), text = movie.runtime.toString())
        InfoItem(info = stringResource(id = R.string.Revenue), text = movie.revenue.toString())
        Text(
            text = movie.overview,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight(300),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun InfoItem(info: String, text: String) {
    Row(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        Text(
            text = info,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
        )
        Text(
            text = text,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight(400),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
private fun Star(isEnabled: Boolean) {
    Image(
        painter = painterResource(id = R.drawable.baseline_star_rate_24_gray),
        contentDescription = null,
        modifier = Modifier.size(50.dp),
        colorFilter = ColorFilter.tint(
            color = colorResource(
                id = if (isEnabled)
                    R.color.colorGray
                else
                    R.color.colorYellow
            )
        )
    )
}
