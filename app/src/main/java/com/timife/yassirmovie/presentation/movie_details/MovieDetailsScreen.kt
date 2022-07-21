package com.timife.yassirmovie.presentation.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.timife.yassirmovie.domain.model.Cast
import com.timife.yassirmovie.domain.model.MovieDetails
import com.timife.yassirmovie.presentation.movies_trending.IMAGE_BASE_URL
import com.timife.yassirmovie.ui.theme.*

@ExperimentalMaterialApi
@Destination
@Composable
fun MovieDetailsScreen(
    id: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.error == null) {

        state.movieDetails?.let { details ->
            BackdropScaffold(
                appBar = { /*TODO*/ },
                backLayerContent = { MovieDetailBack(movie = details) },
                frontLayerContent = { MovieDetailFront(state) },
                backLayerBackgroundColor = Color.Transparent,
                stickyFrontLayer = true,
                peekHeight = 260.dp
            ) {

            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AppBar() {

}

@Composable
fun MovieDetailBack(movie: MovieDetails) {
    val imageLink = IMAGE_BASE_URL + movie.backdropPath
    AsyncImage(
        model = imageLink, contentDescription = "backdropImage", modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f), contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieDetailFront(
    state: MovieDetailsState
) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = state.movieDetails?.title ?: "",
                modifier = Modifier.fillMaxWidth(0.8f),
                style = MaterialTheme.typography.h5,
                maxLines = 3
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(top = 6.dp)) {
            Icon(
                imageVector = Icons.Default.StarRate,
                contentDescription = "",
                tint = DeepYellow,
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = "${state.movieDetails?.voteAverage}" + "/10" + " IMDb",
                style = Typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        LazyRow(
            modifier = Modifier.padding(top = 10.dp),
            contentPadding = PaddingValues(end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.movieDetails?.genres!!.size) { j ->
                val genre = state.movieDetails.genres[j]
                Text(
                    text = genre.name.uppercase(), modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = PurpleGrey80)
                        .scale(0.7f), color = Purple40, fontSize = 18.sp
                )
            }
        }


        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Duration", style = Typography.subtitle2, color = Color.Gray)
                Text(text = "${state.movieDetails?.runtime}" + "minutes", style = Typography.body2)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Language", style = Typography.subtitle2, color = Color.Gray)
                if (state.movieDetails?.language == "fr") {
                    Text(text = "English", style = Typography.subtitle2)
                } else {
                    Text(text = "English", style = Typography.subtitle2)
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Rating", style = Typography.subtitle2, color = Color.Gray)
                Text(text = "PG-13", style = Typography.subtitle2)
            }
        }

        Text(
            text = "Description",
            style = Typography.h6,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = state.movieDetails?.overview ?: "",
            style = Typography.subtitle2, color = Color.Gray,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cast",
                style = Typography.h6,
                modifier = Modifier.fillMaxWidth(0.75f)
            )

            Text(
                text = "See more", modifier = Modifier
                    .padding(top = 5.dp, end = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.Gray)
                    .scale(0.7f), color = Color.White, fontSize = 18.sp
            )
        }

        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.castsList.size) { j ->
                val cast = state.castsList[j]
                CastItem(cast = cast, modifier = Modifier.fillMaxWidth())
            }
        }

    }

}

@Composable
fun CastItem(modifier: Modifier = Modifier, cast: Cast) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val imageLink = IMAGE_BASE_URL + cast.profilePath
        AsyncImage(
            model = imageLink, contentDescription = "Cast Profile", modifier = Modifier
                .size(80.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                ), contentScale = ContentScale.Crop
        )
        Text(
            text = cast.name,
            modifier = Modifier
                .paddingFromBaseline(top = 5.dp, bottom = 2.dp)
                .widthIn(max = 80.dp),
            style = Typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewAll() {
//    BackdropScaffold(appBar = { /*TODO*/ }, backLayerContent = { MovieDetailBack() }, frontLayerContent = { MovieDetailFront()}, backLayerBackgroundColor = Color.Transparent, stickyFrontLayer = true, peekHeight = 250.dp)
}

