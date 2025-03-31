package com.example.mybenedictapp.core.network

import com.example.mybenedictapp.core.common.Constants.LANGUAGE
import com.example.mybenedictapp.core.common.Constants.PAGE_NUMBER
import com.example.mybenedictapp.core.common.Constants.SORT_BY
import com.example.mybenedictapp.core.common.Constants.WITH_PEOPLE
import com.example.mybenedictapp.core.data.remote.MovieDTO
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_PAGE_NUMBER = 1
private const val DEFAULT_LANGUAGE = "en-US"
private const val DEFAULT_SORTING = "popularity.desc"

interface MovieDbApi {

    @GET("3/discover/movie")
    suspend fun getMoviesByPeopleId(
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(PAGE_NUMBER) page: Int = DEFAULT_PAGE_NUMBER,
        @Query(SORT_BY) sortBy: String = DEFAULT_SORTING,
        @Query(WITH_PEOPLE) withPeople: String
    ): MovieDTO

}