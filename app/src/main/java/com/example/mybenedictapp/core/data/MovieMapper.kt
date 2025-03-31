package com.example.mybenedictapp.core.data

import com.example.mybenedictapp.core.data.remote.MovieDTO
import com.example.mybenedictapp.core.data.remote.ResultDTO
import com.example.mybenedictapp.domain.model.Movie
import com.example.mybenedictapp.domain.model.Result
import javax.inject.Inject

class MovieMapper@Inject constructor() : Mapper<MovieDTO, Movie> {

    override fun map(dto: MovieDTO): Movie {
        return Movie(
            page = dto.page,
            results = dto.results.map { it.toResult() },
            totalPages = dto.totalPages,
            totalResults = dto.totalResults,
        )
    }

    private fun ResultDTO.toResult(): Result{
        return Result(
            adult = adult,
            backdropPath = backdropPath,
            genreIds = genreIds,
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
        )
    }
}