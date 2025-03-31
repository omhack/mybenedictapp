package com.example.mybenedictapp.domain

import com.example.mybenedictapp.core.common.Constants
import com.example.mybenedictapp.core.data.repositories.MoviesRepository
import com.example.mybenedictapp.core.network.ApiStatus
import com.example.mybenedictapp.core.network.ErrorType
import com.example.mybenedictapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesByPeopleIdUseCase@Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(peopleId: String): Flow<ApiStatus<Movie>> = flow {
        try {
            emit(ApiStatus.Loading())
            val movie = repository.getMoviesByPeopleId(peopleId)
            emit(ApiStatus.Success(movie))
        } catch (e: NoNetworkException) {
            emit(ApiStatus.Error(message = Constants.NETWORK_EXCEPTION, errorType = ErrorType.NetworkError))
        } catch (e: HttpException) {
            emit(ApiStatus.Error(e.localizedMessage ?: Constants.EXCEPTION))
        } catch (e: IOException) {
            emit(ApiStatus.Error(Constants.IO_EXCEPTION))
        } catch (e: Exception) {
            emit(ApiStatus.Error(e.localizedMessage ?: Constants.EXCEPTION))
        }
    }
}

class NoNetworkException : IOException()