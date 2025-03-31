package com.example.mybenedictapp.ui.screens

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class MoviePreviewParameter : PreviewParameterProvider<MovieItem> {
    override val values = sequenceOf(
                MovieItem(
                    movieTitle = "Avengers: Infinity War",
                    movieOverView = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                    moviePosterUrl = "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                ),
    )
}