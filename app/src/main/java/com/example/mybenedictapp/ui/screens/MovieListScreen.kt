package com.example.mybenedictapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mybenedictapp.R
import com.example.mybenedictapp.core.common.isNotNull
import com.example.mybenedictapp.ui.theme.DividerDarkGrayFullWidth
import com.example.mybenedictapp.ui.theme.TextRegular

@Composable
fun MovieListScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MovieScreens.valueOf(
        backStackEntry?.destination?.route ?: MovieScreens.List.name
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        // Benedict Hardcoded value, could come from pick list
        viewModel.getMoviesByPeopleId(peopleId = "71580")
    }
    Scaffold{ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieScreens.List.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(MovieScreens.List.name) {
                MoviesPage(state, viewModel, navController)
            }

            composable(MovieScreens.MovieDetail.name) {
                MovieDetail(state.selectedMovie)
            }
        }
    }

}

@Composable
private fun MoviesPage(
    state: MovieState,
    viewModel: MoviesViewModel,
    navController: NavHostController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> FullScreenProgressIndicator()

            state.movieItems.isNullOrEmpty().not() -> {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = 10.dp, end = 10.dp, top = 50.dp, bottom = 80.dp)
                        .fillMaxSize()
                ) {
                    MovieList(
                        content = state,
                    ) { index ->
                        viewModel.selectMovie(index)
                        navController.navigate(MovieScreens.MovieDetail.name)
                    }
                }
            }

            state.apiError.isNotNull() -> ErrorDialog(
                errorType = state.apiError?.errorType,
                onConfirmationAction = {
                    // handle back action?
                }
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, device = Devices.PIXEL_4)
@Composable
fun MovieList(
    @PreviewParameter(MovieListPreviewParameter::class) content: MovieState,
    onMovieSelected: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    LazyColumn(
        state = lazyListState,
        flingBehavior = flingBehavior,
        modifier = Modifier
            .height(600.dp)
            .fillMaxWidth()
    ) {
        if (content.movieItems != null) {
            itemsIndexed(content.movieItems) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable(
                            onClick = debounced { onMovieSelected(index) },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.padding(top = 4.dp)) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(item.moviePosterUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.ic_no_image_bg),
                            error = painterResource(id = R.drawable.ic_no_image_bg),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(75.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        text = item.movieTitle,
                        color = TextRegular,
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            )
                    )

                }
                DividerDarkGrayFullWidth(modifier = Modifier.fillMaxWidth().padding(top = 2.dp))
            }
        }
    }
}


/**
 *  Values that represent the screens in the app
 */
enum class MovieScreens(@StringRes val title: Int) {
    List(title = R.string.app_name),
    MovieDetail(title = R.string.movie_detail_title),
}