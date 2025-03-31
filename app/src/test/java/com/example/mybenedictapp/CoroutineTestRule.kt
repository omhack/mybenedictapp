package com.example.mybenedictapp

import com.example.mybenedictapp.domain.model.Movie
import com.example.mybenedictapp.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    internal val testScheduler: TestCoroutineScheduler =  TestCoroutineScheduler()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

val moviesMock = Movie(
    page = 1,
    results = listOf(
        Result(
            adult = false,
            backdropPath = "/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg",
            genreIds = listOf(12, 28, 878),
            id = 299536,
            originalLanguage = "en",
            originalTitle = "Avengers: Infinity War",
            overview = "As the Avengers and their allies have continued to protect the world " +
                    "from threats too large for any one hero to handle, a new danger has " +
                    " from the cosmic shadows: Thanos. A despot of intergalactic infamy, " +
                    "his goal is to collect all six Infinity Stones, artifacts of unimaginable" +
                    " power, and use them to inflict his twisted will on all of reality. " +
                    "Everything the Avengers have fought for has led up to this moment - " +
                    "the fate of Earth and existence itself has never been more uncertain.",
            popularity = 37.8484,
            posterPath = "7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            releaseDate = "2018-04-25",
            title = "Avengers: Infinity War",
            video = false,
            voteAverage = 8.236,
            voteCount = 30357
        ),
        Result(
            adult = false,
            genreIds = listOf(12, 28, 878),
            id = 299536,
            originalLanguage = "en",
            originalTitle = "Spider-Man: No Way Home",
            title = "Spider-Man: No Way Home",
            overview = "Peter Parker is unmasked and no longer able to separate his normal" +
                    " life from the high-stakes of being a super-hero. When he asks for help" +
                    " from Doctor Strange the stakes become even more dangerous, forcing him " +
                    "to discover what it truly means to be Spider-Man.",
            backdropPath = "",
            popularity = 37.8484,
            posterPath = "1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            releaseDate = "",
            video = false,
            voteAverage = 8.236,
            voteCount = 30357
        )
    ),
    totalPages = 1,
    totalResults = 1,
)