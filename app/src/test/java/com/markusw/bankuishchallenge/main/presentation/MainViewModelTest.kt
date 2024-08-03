@file:OptIn(ExperimentalCoroutinesApi::class)

package com.markusw.bankuishchallenge.main.presentation

import com.markusw.bankuishchallenge.DispatcherProvider
import com.markusw.bankuishchallenge.MockResponses
import com.markusw.bankuishchallenge.TestDispatchers
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.main.data.GithubPaginationSource
import com.markusw.bankuishchallenge.network.domain.model.GithubRepository
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @RelaxedMockK
    private lateinit var githubRepository: GithubReposRepository

    @RelaxedMockK
    private lateinit var paginationSource: GithubPaginationSource
    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        testDispatchers = TestDispatchers()
        Dispatchers.setMain(testDispatchers.testDispatcher)
        viewModel = MainViewModel(
            githubRepository,
            paginationSource,
            testDispatchers as DispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should display the correct initial ui state`() {
        val expectedInitialUiState = MainState(
            isLoadingInitialRepositories = true
        )
        val viewModelState = viewModel.state.value

        assertEquals(expectedInitialUiState, viewModelState)
    }

    @Test
    fun `loadInitialRepositories should update state on success with the repositories`() = runTest {
        val mockRepos = MockResponses.fakeRepositories
        coEvery { githubRepository.getRepositories(any(), any(), any()) } returns Result.Success(
            mockRepos
        )

        viewModel.loadInitialRepositories()
        advanceUntilIdle()

        val expectedState = MainState(
            isLoadingInitialRepositories = false,
            repositories = mockRepos
        )
        println(viewModel.state.value)
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `loadInitialRepositories should update state on error with the error message`() = runTest {
        val errorMessage = "error message"
        coEvery { githubRepository.getRepositories(any(), any(), any()) } returns Result.Error(
            errorMessage
        )
        val expectedState = MainState(
            isLoadingInitialRepositories = false,
            loadError = errorMessage
        )

        viewModel.loadInitialRepositories()
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
        advanceUntilIdle()
        val event = viewModel.events.first()
        assertTrue(event is MainViewModelEvent.RepositoriesLoadFailed)
    }

    @Test
    fun `onBottomReached should load next repositories`() = runTest {
        val initialRepos = emptyList<GithubRepository>()
        val nextPageRepos = MockResponses.fakeRepositories

        coEvery { githubRepository.getRepositories(any(), any(), any()) } returns Result.Success(
            initialRepos
        )
        coEvery { paginationSource.loadNextPage() } returns Result.Success(nextPageRepos)

        viewModel.loadInitialRepositories()
        advanceUntilIdle()

        viewModel.onBottomReached()
        advanceUntilIdle()

        val expectedState = MainState(
            isLoadingInitialRepositories = false,
            isLoadingMoreRepositories = false,
            repositories = initialRepos + nextPageRepos
        )
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `loadNextRepositories with a success result should update the ui with the new fetched repositories`() =
        runTest {
            coEvery { paginationSource.loadNextPage() } returns Result.Success(MockResponses.fakeRepositories)

            val expectedState = MainState(
                isLoadingMoreRepositories = false,
                repositories = MockResponses.fakeRepositories,
                loadError = ""
            )

            viewModel.onBottomReached()
            advanceUntilIdle()

            assertEquals(expectedState, viewModel.state.value)
        }

    @Test
    fun `onRefresh should reset and reload repositories on success result`() = runTest {
        val refreshedRepos = MockResponses.fakeRepositories
        coEvery { githubRepository.getRepositories(any(), any(), any()) } returns Result.Success(
            refreshedRepos
        )
        coEvery { paginationSource.resetPager() } just Runs

        viewModel.onRefresh()
        advanceUntilIdle()

        val expectedState = MainState(
            isRefreshing = false,
            repositories = refreshedRepos
        )
        assertEquals(expectedState, viewModel.state.value)
        coVerify { paginationSource.resetPager() }
    }

    @Test
    fun `onRefresh should update the state with the error on error result`() = runTest {
        val errorMessage = "Error message"
        coEvery { githubRepository.getRepositories(any(), any(), any()) } returns Result.Error(
            errorMessage
        )

        viewModel.onRefresh()
        advanceUntilIdle()

        val expectedState = MainState(
            isRefreshing = false,
            loadError = errorMessage
        )
        assertEquals(expectedState, viewModel.state.value)
    }

}