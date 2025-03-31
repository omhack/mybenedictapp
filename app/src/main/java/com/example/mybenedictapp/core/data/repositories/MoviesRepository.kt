package com.example.mybenedictapp.core.data.repositories

import com.example.mybenedictapp.core.data.MovieMapper
import com.example.mybenedictapp.core.network.MovieDbApi
import com.example.mybenedictapp.domain.model.Movie
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getMoviesByPeopleId(peopleId: String): Movie
}

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieDbApi,
    private val mapper: MovieMapper,
) : MoviesRepository {

    override suspend fun getMoviesByPeopleId(peopleId: String): Movie {
        println("Creepy getMoviesByPeopleId")
        return mapper.map(api.getMoviesByPeopleId(withPeople = peopleId))
    }
}