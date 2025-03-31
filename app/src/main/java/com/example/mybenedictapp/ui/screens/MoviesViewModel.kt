package com.example.mybenedictapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybenedictapp.BuildConfig
import com.example.mybenedictapp.core.di.IoDispatcher
import com.example.mybenedictapp.core.network.ApiStatus
import com.example.mybenedictapp.domain.GetMoviesByPeopleIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesByPeopleIdUseCase: GetMoviesByPeopleIdUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieState())

    val state: StateFlow<MovieState>
        get() = _state

    fun getMoviesByPeopleId(peopleId: String) {
        getMoviesByPeopleIdUseCase(peopleId)
            .flowOn(ioDispatcher)
            .onEach { result ->
                when (result) {
                    is ApiStatus.Success -> {
                        val results = result.data?.results
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                 movieItems = results?.map {
                                     MovieItem(
                                         movieTitle = it.title,
                                         movieOverView = it.overview,
                                         moviePosterUrl = BuildConfig.IMG_URL_API + it.posterPath,
                                     )
                                 },
                            )
                        }
                    }

                    is ApiStatus.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                apiError = ErrorState(
                                    errorType = result.errorType,
                                    message = result.message
                                )
                            )
                        }
                    }

                    is ApiStatus.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun selectMovie(index: Int){
        _state.update {
            it.copy(selectedMovieIndex = index)
        }
    }
}