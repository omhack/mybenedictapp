package com.example.mybenedictapp.ui.screens

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class MovieListPreviewParameter : PreviewParameterProvider<MovieState> {
    override val values = sequenceOf(
        MovieState(
            isLoading = false,
            movieItems = listOf(
                MovieItem(
                    movieTitle = "Avengers: Infinity War",
                    movieOverView = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                    moviePosterUrl = "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                ),
                MovieItem(
                    movieTitle = "Spider-Man: No Way Home",
                    movieOverView = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                    moviePosterUrl = "/zD5v1E4joAzFvmAEytt7fM3ivyT.jpg",
                )
            )
        )
    )
}