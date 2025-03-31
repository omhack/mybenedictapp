package com.example.mybenedictapp.ui.screens

import com.example.mybenedictapp.core.network.ErrorType

data class MovieState (
    val movieItems: List<MovieItem>? =  null,
    val selectedMovieIndex: Int = -1,
    val isLoading: Boolean = false,
    val apiError: ErrorState? = null,
){
    val selectedMovie: MovieItem?
        get() = movieItems?.getOrNull(selectedMovieIndex)
}

data class MovieItem(
    val movieTitle: String = "",
    val movieOverView: String = "",
    val moviePosterUrl: String = "",
)

data class ErrorState(
    val errorType: ErrorType? = null,
    val message: String? = null
)