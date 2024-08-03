@file:OptIn(ExperimentalCoroutinesApi::class)

package com.markusw.bankuishchallenge.details.presentation

import androidx.lifecycle.SavedStateHandle
import com.markusw.bankuishchallenge.MockResponses
import com.markusw.bankuishchallenge.TestDispatchers
import com.markusw.bankuishchallenge.core.utils.Result
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    @RelaxedMockK
    private lateinit var githubReposRepository: GithubReposRepository
    @RelaxedMockK
    private lateinit var handle: SavedStateHandle
    private lateinit var dispatchers: TestDispatchers

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dispatchers = TestDispatchers()
        every { handle.get<String>(any()) } returns "test-id"
        Dispatchers.setMain(dispatchers.testDispatcher)
        viewModel = DetailsViewModel(
            githubReposRepository,
            handle,
            dispatchers
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should display the correct initial state`() {
        coEvery { githubReposRepository.getRepository(any()) } returns Result.Success(MockResponses.fakeRepositories[0])

        val expectedState = DetailsState(
            isLoadingRepository = true,
            loadError = null
        )

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `loadRepository with a success result should update the ui state with the repository`() = runTest {
        coEvery { githubReposRepository.getRepository(any()) } returns Result.Success(MockResponses.fakeRepositories[0])
        val expectedState = DetailsState(
            repository = MockResponses.fakeRepositories[0],
            isLoadingRepository = false
        )

        viewModel.loadRepository()
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `loadRepository with an error result should update the ui state with the error message`() = runTest {
        val errorMessage = "Error message"
        coEvery { githubReposRepository.getRepository(any()) } returns Result.Error(errorMessage)
        val expectedState = DetailsState(
            loadError = errorMessage,
            isLoadingRepository = false
        )

        viewModel.loadRepository()
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
    }


}