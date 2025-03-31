package com.example.mybenedictapp.core.di

import com.example.mybenedictapp.core.data.repositories.MoviesRepository
import com.example.mybenedictapp.core.data.repositories.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindMovieRepository(
        movieRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

}