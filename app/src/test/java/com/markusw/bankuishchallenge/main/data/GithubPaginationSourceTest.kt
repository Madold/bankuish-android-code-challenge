package com.markusw.bankuishchallenge.main.data

import com.markusw.bankuishchallenge.MockResponses
import com.markusw.bankuishchallenge.network.domain.repository.GithubReposRepository
import com.markusw.bankuishchallenge.core.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GithubPaginationSourceTest {

    private lateinit var paginationSource: GithubPaginationSource
    @RelaxedMockK
    private lateinit var githubReposRepository: GithubReposRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        paginationSource = GithubPaginationSource(githubReposRepository)
    }

    @Test
    fun `Paginator initial page should be 2`() {
        val expectedPage = 2
        val paginatorPage = paginationSource.getCurrentPage()

        assertEquals(paginatorPage, expectedPage)
    }

    @Test
    fun `When loading next page and repository returns an error result then current page should not increment`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Error("Error")

        val expectedPage = 2
        paginationSource.loadNextPage()
        val paginatorPage = paginationSource.getCurrentPage()
        assertEquals(expectedPage, paginatorPage)
    }

    @Test
    fun `When loading next page and repository returns a success result then current page should increment`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Success(emptyList())
        val expectedPage = 3
        paginationSource.loadNextPage()
        val paginatorPage = paginationSource.getCurrentPage()

        assertEquals(expectedPage, paginatorPage)
    }

    @Test
    fun `should reset pager current page`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Success(emptyList())
        val expectedPage = 2
        paginationSource.loadNextPage()
        paginationSource.loadNextPage()
        paginationSource.loadNextPage()
        paginationSource.resetPager()
        val paginatorPage = paginationSource.getCurrentPage()

        assertEquals(expectedPage, paginatorPage)
    }

    @Test
    fun `loadNextPage should return the result from githubReposRepository`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Success(MockResponses.fakeRepositories)
        val result = paginationSource.loadNextPage()
        val expectedRepositories = MockResponses.fakeRepositories


        assertTrue(result is Result.Success)
        assertEquals(expectedRepositories, result.data)
    }

    @Test
    fun `consecutive successful page loads should increment current page`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Success(emptyList())
        val expectedPaginatorPage = 6

        paginationSource.loadNextPage()
        paginationSource.loadNextPage()
        paginationSource.loadNextPage()
        paginationSource.loadNextPage()

        assertEquals(expectedPaginatorPage, paginationSource.getCurrentPage())
    }

    @Test
    fun `loadNextPage should be called with the correct initial parameters`() = runTest {
        coEvery { githubReposRepository.getRepositories(any(), any(), any()) } returns Result.Success(emptyList())
        paginationSource.loadNextPage()

        coVerify {
            githubReposRepository.getRepositories(
                language = GithubPaginationSource.LANGUAGE,
                count = GithubPaginationSource.PAGE_COUNT,
                page = 2
            )
        }
    }

}