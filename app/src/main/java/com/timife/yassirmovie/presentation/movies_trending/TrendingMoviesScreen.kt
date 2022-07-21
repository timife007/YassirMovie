package com.timife.yassirmovie.presentation.movies_trending

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.timife.yassirmovie.presentation.destinations.MovieDetailsScreenDestination

@ExperimentalMaterialApi
@Composable
@Destination(start = true)
fun TrendingMoviesScreen(
    navigator:DestinationsNavigator,
    viewModel: TrendingMoviesViewModel = hiltViewModel()
){
    
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state
    
     androidx.compose.material.Scaffold(
        topBar = { TopAppBar() }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier) {
                Text(text = "Trending", style = MaterialTheme.typography.h5, modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 5.dp)
                    .fillMaxWidth(0.75f), color = MaterialTheme.colors.onBackground, fontWeight = FontWeight.ExtraBold
                )
            }

            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                viewModel.onMovieEvent(TrendingMoviesEvent.Refresh)
            }) {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(4.dp)){
                    items(state.movies.size){i ->
                        val movie = state.movies[i]
                        MovieItem(movie = movie, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(MovieDetailsScreenDestination(movie.id))
                            }
                            .padding(16.dp)
                        )
                        if (i < state.movies.size){
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }

                    }
                }

            }
        }

    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if(state.isLoading){
            CircularProgressIndicator()
        }
    }

}
@Preview
@Composable
fun TopAppBar(){
    Row(modifier = Modifier.background(Color.Transparent)) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.NotificationsPaused,
                contentDescription = "Notification",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}