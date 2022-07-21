package com.timife.yassirmovie.presentation.movie_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.timife.yassirmovie.domain.model.Cast
import com.timife.yassirmovie.domain.model.MovieDetails
import com.timife.yassirmovie.presentation.movie_details.MovieDetailsState
import com.timife.yassirmovie.presentation.movie_details.MovieDetailsViewModel
import com.timife.yassirmovie.ui.theme.*
import com.timife.yassirmovie.utils.Constants.IMAGE_BASE_URL

@ExperimentalMaterialApi
@Destination
@Composable
fun MovieDetailsScreen(
    id: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state

    if (state.error == null) {

        state.movieDetails?.let { details ->
            BackdropScaffold(
                appBar = { },
                backLayerContent = { MovieDetailBack(movie = details,navigator) },
                frontLayerContent = { MovieDetailFront(state) },
                backLayerBackgroundColor = Color.Transparent,
                frontLayerBackgroundColor = MaterialTheme.colors.background,
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
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}


@Composable
fun MovieDetailBack(movie: MovieDetails,navigator: DestinationsNavigator) {
    val imageLink = IMAGE_BASE_URL + movie.backdropPath
    Box {
        AsyncImage(
            model = imageLink, contentDescription = "backdropImage", modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f), contentScale = ContentScale.Crop
        )
        IconButton(onClick = { navigator.navigateUp()}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "ArrowBack",
                modifier = Modifier.size(30.dp),
                tint = Color.LightGray
            )
        }
    }
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
                maxLines = 3,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
        Row(modifier = Modifier.padding(top = 6.dp)) {
            Icon(
                imageVector = Icons.Default.StarRate,
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = "${state.movieDetails?.voteAverage}" + "/10" + " IMDb",
                style = Typography.body1,
                color = MaterialTheme.colors.onPrimary,
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
                Text(
                    text = "Duration",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(text = "${state.movieDetails?.runtime}" + "minutes", style = Typography.body2)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Language",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
                if (state.movieDetails?.language == "fr") {
                    Text(text = "French", style = Typography.subtitle2)
                } else {
                    Text(text = "English", style = Typography.subtitle2)
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(text = "PG-13", style = Typography.subtitle2)
            }
        }

        Text(
            text = "Description",
            style = Typography.h6,
            modifier = Modifier.padding(top = 10.dp),
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = state.movieDetails?.overview ?: "",
            style = Typography.subtitle2, color = MaterialTheme.colors.onPrimary,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cast",
                modifier = Modifier,
                color = MaterialTheme.colors.onBackground,
                style = Typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
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
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground
        )
    }

}

