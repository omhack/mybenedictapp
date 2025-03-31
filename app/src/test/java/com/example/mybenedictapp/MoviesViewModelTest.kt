package com.example.mybenedictapp

import com.example.mybenedictapp.core.common.isNull
import com.example.mybenedictapp.core.network.ApiStatus
import com.example.mybenedictapp.domain.GetMoviesByPeopleIdUseCase
import com.example.mybenedictapp.ui.screens.MoviesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesViewModelTest {

    @MockK
    private lateinit var useCase: GetMoviesByPeopleIdUseCase

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    val rule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val testDispatcher = UnconfinedTestDispatcher(rule.testScheduler)

        viewModel = MoviesViewModel(
            getMoviesByPeopleIdUseCase = useCase,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `Invokes getMoviesByPeopleId, receives loading event, state shows loading with no error`() =
        runTest {
            coEvery { useCase(any()) } returns flowOf(
                ApiStatus.Loading()
            )

            viewModel.getMoviesByPeopleId("")
            val state = viewModel.state.first()
            assert(state.isLoading && state.apiError.isNull())
        }

    @Test
    fun `Invokes getMoviesByPeopleId,  receives an error, state shows error same as that which is received`() =
        runTest {
            val errorMessage = "custom error message"

            coEvery { useCase(any()) } returns flowOf(
                ApiStatus.Error(errorMessage, null)
            )

            viewModel.getMoviesByPeopleId("")
            val state = viewModel.state.first()
            assert(state.apiError?.message.contentEquals(errorMessage))
        }

    @Test
    fun `Invokes getMoviesByPeopleId,  receives data, state shows the proper data`() =
        runTest {
            val expectedTitle = "Avengers: Infinity War"
            val expectedOverview = "As the Avengers and their allies have continued to protect the world " +
                    "from threats too large for any one hero to handle, a new danger has " +
                    " from the cosmic shadows: Thanos. A despot of intergalactic infamy, " +
                    "his goal is to collect all six Infinity Stones, artifacts of unimaginable" +
                    " power, and use them to inflict his twisted will on all of reality. " +
                    "Everything the Avengers have fought for has led up to this moment - " +
                    "the fate of Earth and existence itself has never been more uncertain."
            val expectedPosterPath = "https://image.tmdb.org/t/p/original/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"

            coEvery { useCase(any()) } returns flowOf(
                ApiStatus.Success(moviesMock)
            )

            viewModel.getMoviesByPeopleId("")
            val state = viewModel.state.first()

            assert(state.movieItems?.first()?.movieTitle == expectedTitle)
            assert(state.movieItems?.first()?.movieOverView == expectedOverview)
            assert(state.movieItems?.first()?.moviePosterUrl == expectedPosterPath)
        }

    @Test
    fun `Invokes getMoviesByPeopleId and selects a movie, state shows the data of movie selected,`() =
        runTest {
            val expectedTitle = "Spider-Man: No Way Home"
            val expectedOverview = "Peter Parker is unmasked and no longer able to separate his normal" +
                    " life from the high-stakes of being a super-hero. When he asks for help" +
                    " from Doctor Strange the stakes become even more dangerous, forcing him " +
                    "to discover what it truly means to be Spider-Man."
            val expectedPosterPath = "https://image.tmdb.org/t/p/original/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"

            coEvery { useCase(any()) } returns flowOf(
                ApiStatus.Success(moviesMock)
            )

            viewModel.getMoviesByPeopleId("")
            viewModel.selectMovie(1)
            val state = viewModel.state.first()

            assert(state.selectedMovie?.movieTitle == expectedTitle)
            assert(state.selectedMovie?.movieOverView == expectedOverview)
            assert(state.selectedMovie?.moviePosterUrl == expectedPosterPath)
        }

}