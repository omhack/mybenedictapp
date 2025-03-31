package com.example.mybenedictapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mybenedictapp.R
import com.example.mybenedictapp.ui.theme.Purple40
import com.example.mybenedictapp.ui.theme.TextRegular

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, device = Devices.PIXEL_4)
@Composable
fun MovieDetail(
    @PreviewParameter(MoviePreviewParameter::class) movie: MovieItem? = null
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 10.dp, end = 10.dp, top = 80.dp, bottom = 80.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = movie?.movieTitle.orEmpty(),
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )

            Box(modifier = Modifier.padding(top = 4.dp)) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(movie?.moviePosterUrl.orEmpty())
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_no_image_bg),
                    error = painterResource(id = R.drawable.ic_no_image_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 15.dp,
                            bottom = 15.dp
                        ),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = movie?.movieOverView.orEmpty(),
                color = TextRegular,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}