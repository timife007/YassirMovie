package com.timife.yassirmovie.presentation.movies_trending.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.timife.yassirmovie.domain.model.TrendingMovie
import com.timife.yassirmovie.ui.theme.*
import com.timife.yassirmovie.utils.Constants.IMAGE_BASE_URL


@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: TrendingMovie
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageLink = IMAGE_BASE_URL + movie.poster

        AsyncImage(
            model = imageLink, contentDescription = "Poster Image",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(140.dp)
                .width(100.dp)
        )
        Column(modifier = Modifier.padding(start = 20.dp, top = 4.dp)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth(0.8f),
                color = MaterialTheme.colors.onBackground
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    text = "${movie.voteAverage}" + "/10" + " IMDb",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = if (movie.language == "fr") "French" else "English", modifier = Modifier
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = PurpleGrey80)
                    .scale(0.7f), color = Purple40, fontSize = 18.sp
            )

            Text(
                text = movie.releaseDate,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

        }
    }
}

