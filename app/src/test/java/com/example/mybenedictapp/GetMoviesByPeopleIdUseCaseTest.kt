package com.example.mybenedictapp

import com.example.mybenedictapp.core.data.repositories.MoviesRepository
import com.example.mybenedictapp.core.network.ApiStatus
import com.example.mybenedictapp.core.network.ErrorType
import com.example.mybenedictapp.domain.GetMoviesByPeopleIdUseCase
import com.example.mybenedictapp.domain.NoNetworkException
import com.example.mybenedictapp.domain.model.Movie
import com.example.mybenedictapp.domain.model.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class GetMoviesByPeopleIdUseCaseTest {

    @MockK
    private lateinit var repository: MoviesRepository

    private lateinit var useCase: GetMoviesByPeopleIdUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetMoviesByPeopleIdUseCase(repository)
    }

    @Test
    fun `GetMoviesByPeopleIdUseCase invoke() for success scenario`() =
        runTest {
            coEvery { repository.getMoviesByPeopleId(any()) } returns moviesMock

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Success)
            assert((listOfEmit[1] as ApiStatus.Success).data?.results?.size == 1)
            assert((listOfEmit[1] as ApiStatus.Success).data?.totalResults == 1L)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `GetMoviesByPeopleIdUseCase invoke() gets Error flow for any exception scenario`() =
        runTest {
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws Exception()

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Error)
        }

    @Test
    fun `GetBankingInfoUseCase invoke() gets Error flow for Exception without message scenario`() {
        runBlocking {
            val exception = mockk<Exception>()
            every { exception.localizedMessage } returns null
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws exception

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Error)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GetBankingInfoUseCase invoke() gets Error flow for IOException scenario`() =
        runTest {
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws IOException()

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Error)
        }

    @Test
    fun `GetBankingInfoUseCase invoke() gets Error flow for HttpException scenario`() {
        runBlocking {
            val httpException = mockk<HttpException>()
            every { httpException.localizedMessage } returns "http-error-message"
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws httpException

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Error)
        }
    }

    @Test
    fun `GetBankingInfoUseCase invoke() gets Error flow for HttpException without message scenario`() {
        runBlocking {
            val httpException = mockk<HttpException>()
            every { httpException.localizedMessage } returns null
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws httpException

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert(listOfEmit[1] is ApiStatus.Error)
        }
    }

    @Test
    fun `GetBankingInfoUseCase invoke() gets Error flow for network exception`() {
        runBlocking {
            coEvery {
                repository.getMoviesByPeopleId(any())
            } throws NoNetworkException()

            val listOfEmit = useCase("").toList()
            assert(listOfEmit.size == 2)
            assert(listOfEmit[0] is ApiStatus.Loading)
            assert((listOfEmit[1] as ApiStatus.Error).errorType == ErrorType.NetworkError)
        }
    }
}